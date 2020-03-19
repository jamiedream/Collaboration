/*
 * Copyright (c) viWave 2019.
 * Create by J.Y Yen 20/ 8/ 2019.
 * Last modified 8/15/19 6:33 PM
 */

package com.viwave.RossmaxConnect.Measurement.chart

import androidx.core.content.ContextCompat
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
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
            val xVal = it.takenAt //todo, index
            val yVal = it.temperature
//            entries.add(Entry(xVal, yVal))
        }

        return LineData(lineDataTempStyle(entries))
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

}