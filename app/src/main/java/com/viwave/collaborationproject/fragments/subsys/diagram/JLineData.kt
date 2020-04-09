/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/25/20 1:49 PM
 */

package com.viwave.RossmaxConnect.Measurement.chart

import androidx.core.content.ContextCompat
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.calXIndex
import com.viwave.collaborationproject.CollaborationApplication
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.data.bios.Bio
import java.util.*


object JLineData {

    private val context by lazy { CollaborationApplication.context.applicationContext }

    private val entries = ArrayList<Entry>()

    fun getTempLineData(tempList: MutableList<Bio.Temperature>?): LineData {

        if(entries.isNotEmpty()) entries.clear()

        tempList?.forEach {
            val xVal = calXIndex(it.takenAt)
            val yVal = it.temperature
            entries.add(Entry(xVal, yVal))
        }

        return LineData(lineDataTempStyle(entries))
    }

    fun getPulseLineData(pulseList: MutableList<Bio.Pulse>?): LineData {

        if(entries.isNotEmpty()) entries.clear()

        pulseList?.forEach {
            val xVal = calXIndex(it.takenAt)
            val yVal = it.pulse.toFloat()
            entries.add(Entry(xVal, yVal))
        }

        return LineData(lineDataPulseStyle(entries))
    }

    fun getWeightLineData(weightList: MutableList<Bio.Weight>?): LineData {

        if(entries.isNotEmpty()) entries.clear()

        weightList?.forEach {
            val xVal = calXIndex(it.takenAt)
            val yVal = it.weight
            entries.add(Entry(xVal, yVal))
        }

        return LineData(lineDataWeightStyle(entries))
    }

    fun getGlucoseLineData(glucoseList: MutableList<Bio.BloodGlucose>?): LineData {

        val fasting = mutableListOf<Bio.BloodGlucose>()
        val before = mutableListOf<Bio.BloodGlucose>()
        val after = mutableListOf<Bio.BloodGlucose>()
        glucoseList?.forEach {
            when(it.meal){
                context.getString(R.string.fasting) -> fasting.add(it)
                context.getString(R.string.before_meal) -> before.add(it)
                context.getString(R.string.after_meal) -> after.add(it)
            }
        }

        //fasting
        val fastingEntries = ArrayList<Entry>()
        fasting.forEach {
            val xVal = calXIndex(it.takenAt)
            val yVal = it.glucose.toFloat()
            fastingEntries.add(Entry(xVal, yVal))
        }

        //before
        val beforeEntries = ArrayList<Entry>()
        before.forEach {
            val xVal = calXIndex(it.takenAt)
            val yVal = it.glucose.toFloat()
            beforeEntries.add(Entry(xVal, yVal))
        }

        //after
        val afterEntries = ArrayList<Entry>()
        after.forEach {
            val xVal = calXIndex(it.takenAt)
            val yVal = it.glucose.toFloat()
            afterEntries.add(Entry(xVal, yVal))
        }

        return LineData(lineDataGlucoseFastingStyle(fastingEntries), lineDataGlucoseBeforeStyle(beforeEntries), lineDataGlucoseAfterStyle(afterEntries))
    }

    fun getRespireLineData(respireList: MutableList<Bio.Respire>?): LineData {

        if(entries.isNotEmpty()) entries.clear()

        respireList?.forEach {
            val xVal = calXIndex(it.takenAt)
            val yVal = it.respire.toFloat()
            entries.add(Entry(xVal, yVal))
        }

        return LineData(lineDataRespireStyle(entries))
    }

    fun getHeightLineData(heightList: MutableList<Bio.Height>?): LineData {

        if(entries.isNotEmpty()) entries.clear()

        heightList?.forEach {
            val xVal = calXIndex(it.takenAt)
            val yVal = it.height
            entries.add(Entry(xVal, yVal))
        }

        return LineData(lineDataHeightStyle(entries))
    }


    /**
     * Line Chart Style
     * */
    private fun lineDataTempStyle(data: MutableList<Entry>): LineDataSet {
        val set = LineDataSet(data, "Temperature Data set")
        set.setCircleColor(ContextCompat.getColor(context, R.color.egg_blue))
        set.circleHoleColor = ContextCompat.getColor(context, android.R.color.white)
        set.setDrawCircleHole(true)
        set.circleRadius = 6f
        set.circleHoleRadius = 3f
        set.color = ContextCompat.getColor(context, R.color.egg_blue)
        set.lineWidth = 2f
        set.setDrawValues(false)
        return set
    }

    private fun lineDataPulseStyle(data: MutableList<Entry>): LineDataSet {
        val set = LineDataSet(data, "Pulse Data set")
        set.setCircleColor(ContextCompat.getColor(context, R.color.salmon_pink))
        set.setDrawCircleHole(false)
        set.circleRadius = 6f
        set.color = ContextCompat.getColor(context, R.color.salmon_pink)
        set.lineWidth = 2f
        set.setDrawValues(false)
        return set
    }

    private fun lineDataWeightStyle(data: MutableList<Entry>): LineDataSet {
        val set = LineDataSet(data, "Weight Data set")
        set.setCircleColor(ContextCompat.getColor(context, R.color.supernova))
        set.setDrawCircleHole(false)
        set.circleRadius = 6f
        set.lineWidth = 2f
        set.color = ContextCompat.getColor(context, R.color.supernova)
        set.setDrawValues(false)
        return set
    }

    private fun lineDataGlucoseFastingStyle(data: MutableList<Entry>): LineDataSet {
        val set = LineDataSet(data, "GlucoseFasting Data set")
        set.setCircleColor(ContextCompat.getColor(context, R.color.turbo))
        set.setDrawCircleHole(false)
        set.circleRadius = 6f
        set.lineWidth = 0f
        set.color = ContextCompat.getColor(context, android.R.color.transparent)
        set.setDrawValues(false)
        return set
    }

    private fun lineDataGlucoseBeforeStyle(data: MutableList<Entry>): LineDataSet {
        val set = LineDataSet(data, "GlucoseBefore Data set")
        set.setCircleColor(ContextCompat.getColor(context, R.color.lucky))
        set.setDrawCircleHole(false)
        set.circleRadius = 6f
        set.lineWidth = 0f
        set.color = ContextCompat.getColor(context, android.R.color.transparent)
        set.setDrawValues(false)
        return set
    }

    private fun lineDataGlucoseAfterStyle(data: MutableList<Entry>): LineDataSet {
        val set = LineDataSet(data, "GlucoseAfter Data set")
        set.setCircleColor(ContextCompat.getColor(context, R.color.orange))
        set.setDrawCircleHole(false)
        set.circleRadius = 6f
        set.lineWidth = 0f
        set.color = ContextCompat.getColor(context, android.R.color.transparent)
        set.setDrawValues(false)
        return set
    }

    private fun lineDataRespireStyle(data: MutableList<Entry>): LineDataSet {
        val set = LineDataSet(data, "Respire Data set")
        set.setCircleColor(ContextCompat.getColor(context, R.color.salmon_pink))
        set.setDrawCircleHole(false)
        set.circleRadius = 6f
        set.color = ContextCompat.getColor(context, R.color.salmon_pink)
        set.lineWidth = 2f
        set.setDrawValues(false)
        return set
    }

    private fun lineDataHeightStyle(data: MutableList<Entry>): LineDataSet {
        val set = LineDataSet(data, "Height Data set")
        set.setCircleColor(ContextCompat.getColor(context, R.color.salmon_pink))
        set.setDrawCircleHole(false)
        set.circleRadius = 6f
        set.color = ContextCompat.getColor(context, R.color.salmon_pink)
        set.lineWidth = 2f
        set.setDrawValues(false)
        return set
    }

}