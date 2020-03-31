/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/25/20 1:49 PM
 */

package com.viwave.RossmaxConnect.Measurement.yaxis

import androidx.core.content.ContextCompat
import com.viwave.RossmaxConnect.Measurement.chart.JCustomCombinedChart
import com.viwave.RossmaxConnect.Measurement.chart.JYAxisValueFormatter
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.utils.LogUtil

class YAxisOxygen(private val chart: JCustomCombinedChart) {

    private val TAG = this::class.java.simpleName

    private val leftAxis by lazy { chart.axisLeft }

    private val minStandardValue = 90f
    private val maxStandardValue = 100f
    private val retainY = .5f
    private val standardInterval = 2
    private val adjustInterval = 10

    init {
        chart.axisRight.isEnabled = false

        leftAxis.yOffset = 0f
        leftAxis.setDrawAxisLine(false)
        leftAxis.gridLineWidth = 1f
        leftAxis.gridColor = ContextCompat.getColor(chart.context, R.color.humming_bird)
        leftAxis.textColor = ContextCompat.getColor(chart.context, R.color.nandor)

        setYAxisMin(minStandardValue)
        setYAxisMax(maxStandardValue)

    }

    fun updateYAxis(){

//        LogUtil.logD(TAG, getDataMinValue())
        setYAxisMax(getDataMaxValue())
        setYAxisMin(getDataMinValue())
        updateGrid()
        chart.notifyDataSetChanged()
    }

    private fun getDataMaxValue(): Float?{

        return when (chart.data == null) {
            true -> null
            false -> {
                val maxValue = if(calYMax == -1f) chart.data.yMax else calYMax
                when (maxValue < maxStandardValue) {
                    true -> null
                    false -> {
                        val max = adjustInterval * Math.ceil(maxValue / adjustInterval.toDouble())
                        max.toFloat()
                    }
                }
            }
        }
    }

    private fun getDataMinValue(): Float?{

        if(calYMin == -1f){
            return null
        }else {
            return when (chart.data == null) {
                true -> null
                false -> {
                    when (calYMin > minStandardValue) {
                        true -> minStandardValue
                        false -> {
                            val min = adjustInterval * Math.floor(calYMin / adjustInterval.toDouble())
//                            LogUtil.logD(TAG, adjustInterval)
//                            LogUtil.logD(TAG, Math.floor(calYMin / adjustInterval.toDouble()))
                            min.toFloat()
                        }
                    }
                }
            }
        }
    }

    private var calYMin = -1f
    fun setYMin(value: Any?){
        if(value != null)
            calYMin = value.toString().toFloat()
    }

    private var calYMax = -1f
    fun setYMax(value: Any?){
        if(value != null)
            calYMax = value.toString().toFloat()
    }

    private fun setYAxisCount(count: Int){
        setLeftAxisCount(count)
    }

    private fun setLeftAxisCount(count: Int){
        val yAxisValueFormatter = JYAxisValueFormatter(count, false)
        leftAxis.valueFormatter = yAxisValueFormatter
    }

    private fun setYAxisMin(min: Float?){
        // this replaces setStartAtZero(true)
        if(min == null) return
        setLeftAxisMin(min)
    }

    private fun setLeftAxisMin(min: Float) {
        leftAxis.axisMinimum = min
    }

    private fun setYAxisMax(max: Float?){
        // this replaces setStartAtZero(true)
        if(max == null) return
        setLeftAxisMax(max + retainY)
    }

    private fun setLeftAxisMax(max: Float) {
        leftAxis.axisMaximum = max
    }

    private fun updateGrid(){

        val initCount = when(leftAxis.axisMaximum > maxStandardValue + retainY || leftAxis.axisMinimum < minStandardValue){
            true -> ((leftAxis.axisMaximum - leftAxis.axisMinimum) - adjustInterval) / adjustInterval
            false -> (leftAxis.axisMaximum - leftAxis.axisMinimum) / standardInterval
        }

        LogUtil.logD(TAG, leftAxis.axisMaximum)
        LogUtil.logD(TAG, leftAxis.axisMinimum)
        LogUtil.logD(TAG, standardInterval)
        LogUtil.logD(TAG, initCount)

        setYAxisCount(initCount.toInt())
    }

}