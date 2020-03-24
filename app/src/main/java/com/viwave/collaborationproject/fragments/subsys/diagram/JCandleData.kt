/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 23/ 3/ 2020.
 * Last modified 3/23/20 5:43 PM
 */

package com.viwave.collaborationproject.fragments.subsys.diagram

import android.graphics.Paint
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.calXIndex
import com.viwave.collaborationproject.CollaborationApplication
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.data.bios.Bio

object JCandleData{

    private val context by lazy { CollaborationApplication.context.applicationContext }

    private val arrayList = mutableListOf<CandleEntry>()
    private val arrayListDanger = mutableListOf<CandleEntry>()
    private val arrayListWarning = mutableListOf<CandleEntry>()

    /**
     * Data start from highest, close array means data range(highest to lowest)
     * @param barSpace: default 0.1f (10%), max 0.45f, min 0f
     * */
    fun getCandleData(barSpace: Float, bpList: MutableList<Bio.BloodPressure>?): CandleData?{

        if(arrayList.isNotEmpty()) arrayList.clear()
        if(arrayListDanger.isNotEmpty()) arrayListDanger.clear()

        bpList?.forEach { bpIndex ->

            val sys = bpIndex.sys.toFloat()
            val dia = bpIndex.dia.toFloat()
            val pulsePressureDiff = sys - dia

            when{

                pulsePressureDiff >= 60f || pulsePressureDiff <= 20f -> {

                    arrayListDanger.add(CandleEntry(
                        calXIndex(bpIndex.takenAt),
                        sys, sys, sys,
                        dia,
                        null
                    ))
                }

                pulsePressureDiff < 60f -> {

                    arrayList.add(CandleEntry(
                        calXIndex(bpIndex.takenAt),
                        sys, sys, sys,
                        dia,
                        null
                    ))
                }
            }

        }

        return when(arrayList.isNotEmpty()){
            true -> {
                val normalDataSet = candleDataNormalStyle(arrayList)
                normalDataSet.barSpace = barSpace
                when(arrayListDanger.isNotEmpty()){
                    true -> {
                        val unusualDataSet =  candleDataUnusualStyle(arrayListDanger)
                        unusualDataSet.barSpace = barSpace
                        CandleData(normalDataSet, unusualDataSet)
                    }
                    false -> CandleData(normalDataSet)
                }
            }

            false -> {
                when(arrayListDanger.isNotEmpty()){
                    true -> {
                        val unusualDataSet =  candleDataUnusualStyle(arrayListDanger)
                        unusualDataSet.barSpace = barSpace
                        CandleData(unusualDataSet)
                    }
                    false -> null
                }
            }

        }
    }

//    fun getSpO2CandleData(barSpace: Float, pulseOximetryList: MutableList<Bio.Oxygen>?, timeformat: String): CandleData?{
//
//        if(arrayList.isNotEmpty()) arrayList.clear()
//        if(arrayListDanger.isNotEmpty()) arrayListDanger.clear()
//        if(arrayListWarning.isNotEmpty()) arrayListWarning.clear()
//
//        if(!pulseOximetryList.isNullOrEmpty()){
//            for (i in 0 until pulseOximetryList.size) {
//
////                val spo2Lowest =
////                    when(pulseOximetryList[i].data.SpO2Highest.toFloat() == pulseOximetryList[i].data.SpO2Lowest.toFloat()){
////                        false -> pulseOximetryList[i].data.SpO2Lowest.toFloat()
////                        true -> pulseOximetryList[i].data.SpO2Highest.toFloat() - .1f
////                    }
//
//                val spo2Highest =
//                    when(pulseOximetryList[i].data.SpO2Highest.toFloat() == pulseOximetryList[i].data.SpO2Lowest.toFloat()){
//                        false -> pulseOximetryList[i].data.SpO2Highest.toFloat()
//                        true -> pulseOximetryList[i].data.SpO2Highest.toFloat() + .3f
//                    }
//
////                LogUtil.logD("getSpO2CandleData", pulseOximetryList[i].data.SpO2Highest.toFloat())
////                LogUtil.logD("getSpO2CandleData", pulseOximetryList[i].data.SpO2Lowest.toFloat())
//
//                when{
//
//                    pulseOximetryList[i].data.SpO2Highest >= 95 && pulseOximetryList[i].data.SpO2Lowest >= 95 -> {
//
//                        arrayList.add(CandleEntry(
//                            pulseOximetryList[i].index +
//                                    when(timeformat) {
//                                        DAY -> (.5f / 24)
//                                        HOUR -> (.5f / 24 / 12)
//                                        else -> .5f
//                                    },
//                            spo2Highest,
//                            spo2Highest,
//                            spo2Highest,
//                            pulseOximetryList[i].data.SpO2Lowest.toFloat(),
//                            null
//                        ))
//                    }
//
//                    pulseOximetryList[i].data.SpO2Highest >= 90 && pulseOximetryList[i].data.SpO2Lowest >= 90 -> {
//
//                        arrayListWarning.add(CandleEntry(
//                            pulseOximetryList[i].index +
//                                    when(timeformat) {
//                                        DAY -> (.5f / 24)
//                                        HOUR -> (.5f / 24 / 12)
//                                        else -> .5f
//                                    },
//                            spo2Highest,
//                            spo2Highest,
//                            spo2Highest,
//                            pulseOximetryList[i].data.SpO2Lowest.toFloat(),
//                            null
//                        ))
//                    }
//
//                    else -> {
//
//                        arrayListDanger.add(CandleEntry(
//                            pulseOximetryList[i].index +
//                                    when(timeformat) {
//                                        DAY -> (.5f / 24)
//                                        HOUR -> (.5f / 24 / 12)
//                                        else -> .5f
//                                    },
//                            spo2Highest,
//                            spo2Highest,
//                            spo2Highest,
//                            pulseOximetryList[i].data.SpO2Lowest.toFloat(),
//                            null
//                        ))
//                    }
//
//                }
//            }
//        }
//
//        val normalDataSet: CandleDataSet?
//        val unusualDataSet: CandleDataSet?
//        val warningDataSet: CandleDataSet?
//
//        return when(arrayList.isNotEmpty()){
//            true -> {
//
//                normalDataSet = candleDataSpO2NormalStyle(arrayList)
//                normalDataSet.barSpace = barSpace
//
//                when(arrayListDanger.isNotEmpty()){
//                    true -> {
//                        unusualDataSet =  candleDataSpO2DangerStyle(arrayListDanger)
//                        unusualDataSet.barSpace = barSpace
//                        when(arrayListWarning.isNotEmpty()){
//                            true -> {
//                                warningDataSet =  candleDataSpO2WarningStyle(arrayListWarning)
//                                warningDataSet.barSpace = barSpace
//                                CandleData(normalDataSet, unusualDataSet, warningDataSet)
//                            }
//                            false -> {
//                                CandleData(normalDataSet, unusualDataSet)
//                            }
//                        }
//                    }
//                    false -> {
//                        when(arrayListWarning.isNotEmpty()){
//                            true -> {
//                                warningDataSet =  candleDataSpO2WarningStyle(arrayListWarning)
//                                warningDataSet.barSpace = barSpace
//                                CandleData(normalDataSet, warningDataSet)
//                            }
//                            false -> {
//                                CandleData(normalDataSet)
//                            }
//                        }
//                    }
//                }
//            }
//
//            false -> {
//                when(arrayListDanger.isNotEmpty()){
//                    true -> {
//                        unusualDataSet =  candleDataSpO2DangerStyle(arrayListDanger)
//                        unusualDataSet.barSpace = barSpace
//                        when(arrayListWarning.isNotEmpty()){
//                            true -> {
//                                warningDataSet =  candleDataSpO2WarningStyle(arrayListWarning)
//                                warningDataSet.barSpace = barSpace
//                                CandleData(unusualDataSet, warningDataSet)
//                            }
//                            false -> {
//                                CandleData(unusualDataSet)
//                            }
//                        }
//                    }
//                    false -> {
//                        when(arrayListWarning.isNotEmpty()){
//                            true -> {
//                                warningDataSet =  candleDataSpO2WarningStyle(arrayListWarning)
//                                warningDataSet.barSpace = barSpace
//                                CandleData(warningDataSet)
//                            }
//                            false -> null
//                        }
//                    }
//                }
//            }
//        }
//    }

    /**
     * BP Candle chart style
     * */
    private fun candleDataNormalStyle(data: MutableList<CandleEntry>): CandleDataSet{

        val set = CandleDataSet(data, "Data Set Normal")
        set.decreasingColor = ContextCompat.getColor(context, R.color.nandor_70)

        return candleSetStyle(set)
    }

    private fun candleDataUnusualStyle(data: MutableList<CandleEntry>): CandleDataSet{

        val set = CandleDataSet(data, "Data Set Unusual")
        set.decreasingColor = ContextCompat.getColor(context, R.color.mexican_red_70)

        return candleSetStyle(set)
    }
    /**
     * SpO2 Candle chart style
     * */
//    private fun candleDataSpO2NormalStyle(data: MutableList<CandleEntry>): CandleDataSet{
//
//        val set = CandleDataSet(data, "SpO2 Data Set Normal")
//        set.decreasingColor = ContextCompat.getColor(context, R.color.picton_70)
//
//        return candleSetStyle(set)
//    }
//
//    private fun candleDataSpO2WarningStyle(data: MutableList<CandleEntry>): CandleDataSet{
//
//        val set = CandleDataSet(data, "SpO2 Data Set Warning")
//        set.decreasingColor = ContextCompat.getColor(context, R.color.selective_yellow_70)
//
//        return candleSetStyle(set)
//    }
//
//    private fun candleDataSpO2DangerStyle(data: MutableList<CandleEntry>): CandleDataSet{
//
//        val set = CandleDataSet(data, "SpO2 Data Set Danger")
//        set.decreasingColor = ContextCompat.getColor(context, R.color.mexican_red_70)
//
//        return candleSetStyle(set)
//    }
//
    private fun candleSetStyle(set: CandleDataSet): CandleDataSet{
        set.setDrawIcons(false)
        set.axisDependency = YAxis.AxisDependency.LEFT
        set.decreasingPaintStyle = Paint.Style.FILL
        set.setDrawValues(false)
        set.setDrawIcons(false)
        return set
    }
}