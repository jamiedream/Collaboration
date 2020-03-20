/*
 * Copyright (c) viWave 2019.
 * Create by J.Y Yen 6/ 9/ 2019.
 * Last modified 9/6/19 1:32 PM
 */

package com.viwave.RossmaxConnect.Measurement.yaxis

import androidx.core.content.ContextCompat
import com.viwave.RossmaxConnect.Measurement.chart.JCustomCombinedChart
import com.viwave.RossmaxConnect.Measurement.chart.JYAxisValueFormatter
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.utils.LogUtil

class YAxisTemperature(private val chart: JCustomCombinedChart) {

    private val TAG = this::class.java.simpleName

    private val leftAxis by lazy { chart.axisLeft }

    var defaultMax = 38f
    var defaultMin = 36f

    private val minStandardValue = defaultMin
    private val maxStandardValue = defaultMax
    private var standardInterval = .5f

    var minY = minStandardValue
    var maxY = maxStandardValue

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
            true -> maxStandardValue
            false -> {
                val maxValue = if(calYMax == -1f) chart.data.yMax else calYMax
                val difference = maxValue - if(calYMin == -1f) chart.data.yMin else calYMin
                standardInterval = when {
                    difference in 4f..8f -> 1f
                    difference > 8f -> 2f
                    else -> .5f
                }

                when(maxValue > maxStandardValue){
                    true -> standardInterval * Math.ceil(chart.data.yMax / standardInterval.toDouble()).toFloat()
                    false -> maxStandardValue
                }

            }
        }
    }

    private fun getDataMinValue(): Float?{

        return when (chart.data == null) {
            true -> minStandardValue
            false -> {
                val minValue = if(calYMin == -1f) chart.data.yMin else calYMin
                val difference = if(calYMax == -1f) chart.data.yMax else calYMax - minValue
                LogUtil.logD(TAG, "$minValue $difference")
                standardInterval = when {
                    difference in 4f..8f -> 1f
                    difference > 8f -> 2f
                    else -> .5f
                }

                when(minValue < minStandardValue){
                    true -> standardInterval * Math.floor(chart.data.yMin / standardInterval.toDouble()).toFloat()
                    false -> minStandardValue
                }

            }
        }
    }

    private var calYMin = -1f
    fun setYMin(value: Any?){
        if(value != null)
            calYMin = value as Float
    }

    private var calYMax = -1f
    fun setYMax(value: Any?){
        if(value != null)
            calYMax = value as Float
    }

    private fun setYAxisCount(count: Int) {
        setLeftAxisCount(count)
    }

    private fun setLeftAxisCount(count: Int){
        val yAxisValueFormatter = JYAxisValueFormatter(count, standardInterval == .5f)
        leftAxis.valueFormatter = yAxisValueFormatter
    }

    private fun setYAxisMin(min: Float?) {
        // this replaces setStartAtZero(true)
        if (min == null) return
        setLeftAxisMin(min)
        minY = min
    }

    private fun setLeftAxisMin(min: Float) {
        leftAxis.axisMinimum = min
    }

    private fun setYAxisMax(max: Float?) {
        // this replaces setStartAtZero(true)
        if (max == null) return
        setLeftAxisMax(max)
        maxY = max
    }

    private fun setLeftAxisMax(max: Float) {
        leftAxis.axisMaximum = max
    }

    private fun updateGrid(){
        val initCount = ((leftAxis.axisMaximum - leftAxis.axisMinimum) - standardInterval) / standardInterval
        setYAxisCount(initCount.toInt() + 1)
    }
}