/*
 * Copyright (c) viWave 2019.
 * Create by J.Y Yen 20/ 8/ 2019.
 * Last modified 8/14/19 1:30 PM
 */

package com.viwave.RossmaxConnect.Measurement.chart

import android.content.Context
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.calXIndex
import com.viwave.collaborationproject.data.bios.BioLiveData
import com.viwave.collaborationproject.data.bios.BioViewModel

class JMarkdown: MarkerView {

    constructor(context: Context?, layoutResource: Int) : super(context, layoutResource)

    override fun getOffsetForDrawingAtPoint(posX: Float, posY: Float): MPPointF {
        val offset = offset
//        val chart = chartView
        val width = width.toFloat()
        val height = height.toFloat()

        offset.y = -height * 1.5f
        offset.x = 0f
        if (posX > width / 2) {
            offset.x = -(width / 2)
        }

        return offset
    }

    private lateinit var model: BioViewModel
    fun setModel(model: BioViewModel) {
        this.model = model
    }


    override fun refreshContent(e: Entry?, highlight: Highlight?) {

        e?.let {
            getBioData(e.x, e.y)
        }
//        LogUtil.logD("refreshContent", "${e?.x} ${e?.y}")

        super.refreshContent(e, highlight)

    }

    private fun getBioData(x: Float, y: Float) {

        model.getSelectedType().value?.let {
            when(it){
                BioLiveData.Companion.BioType.Temperature -> {
                    model.getTempListData().value?.forEach {
                        if(calXIndex(it.takenAt) == x && it.temperature == y){
                            model.getMarkerData().value = it
                            return
                        }
                    }
                }
                else -> {}
            }
        }

//
//        when(model.getCurrentType().value){
//            HealthLiveData.Companion.TYPE.BP -> {
//                model.getBPListData().value?.forEach {
////                    LogUtil.logD("getBPData", "${it.tagId == x} ${it.tagId}")
//                    if (it.tagId == x) {
//                        val dataSYS = SettingDataUtil.formatMMHGByUnit(it.data.Systolic)
//                        if(dataSYS == y){
//                            model.getMarkerVital().value = it
//                            return
//                        }
//                    }
//                }
//
//            }
//            HealthLiveData.Companion.TYPE.Temp -> {
//                model.getTempListData().value?.forEach {
////                    LogUtil.logD("getTempData", "${it.tagId == x} ${it.tagId}")
//                    if (it.tagId == x) {
//                        val dataTemp = SettingDataUtil.formatTempByUnit(it.data.BodyTemperature)
//                        if(dataTemp == y){
//                            model.getMarkerVital().value = it
//                            return
//                        }
//                    }
//                }
//            }
//            HealthLiveData.Companion.TYPE.BG -> {
//                model.getGlucoseListData().value?.forEach {
////                    LogUtil.logD("getGlucoseData", "${it.tagId == x} ${it.tagId}")
//                    if (it.tagId == x) {
//                        if(SettingDataUtil.formatGlucoseByUnit(it.data.BloodGlucose) == y){
//                            model.getMarkerVital().value = it
//                            return
//                        }
//                    }
//                }
//            }
//            HealthLiveData.Companion.TYPE.Oxygen -> {
//                when(isHourlyOxygenDetail){
////                    true -> {
////                        model.getOxygenDetailListData().value?.forEach {
////                            //                    LogUtil.logD("getWeightData", "${it.tagId == x} ${it.tagId}")
////                            if (it.index == x) {
////                                if(it.spo2.toFloat() == y) {
////                                    model.getMarkerDetailSpO2().value = it
////                                    return
////                                }
////                            }
////                        }
////                    }
//
//                    false -> {
//                        when(timeFormat == DAY){
//                            true -> {
//                                model.getOxygenHourlyData().value?.forEach {
//                                    val dataIndex = Math.floor(x.toDouble())
//                                    val hour = Math.floor((x - dataIndex).times(24)).div(24)
////                            LogUtil.logD("getOxygenData", "${(dataIndex + hour).toFloat()} ${it.index}")
//                                    if (it.index == (dataIndex + hour).toFloat()) {
//                                        model.getMarkerSpO2().value = it
//                                        return
//                                    }
//                                }
//                            }
//                            false -> {
//                                model.getOxygenDayData().value?.forEach {
//                                    //                            LogUtil.logD("getOxygenData", "${Math.floor(x.toDouble())} ${it.index}")
//                                    if (it.index.toDouble() == Math.floor(x.toDouble())) {
//                                        model.getMarkerSpO2().value = it
//                                        return
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//            HealthLiveData.Companion.TYPE.Weight -> {
//                model.getWeightListData().value?.forEach {
////                    LogUtil.logD("getWeightData", "${it.tagId == x} ${it.tagId}")
//                    if (it.tagId == x) {
//                        val dataW = SettingDataUtil.formatWeightByUnit(it.data.Weight)
//                        if(dataW == y) {
//                            model.getMarkerVital().value = it
//                            return
//                        }
//                    }
//                }
//            }
//        }

    }

}