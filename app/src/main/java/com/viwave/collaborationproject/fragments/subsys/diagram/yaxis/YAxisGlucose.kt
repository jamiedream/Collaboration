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

class YAxisGlucose(
    private val chart: JCustomCombinedChart
) {

    private val TAG = this::class.java.simpleName

    private val leftAxis by lazy { chart.axisLeft }

    var defaultMax = 300f
    var defaultMin = 0f
    var defaultInterval = 50f
    var defaultAdjustInterval = 100f

    private val minStandardValue = defaultMin
    private val maxStandardValue = defaultMax
    private val retainY = 0f
    private var standardInterval = defaultInterval

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
        updateGrid()
        chart.notifyDataSetChanged()
    }

    private fun getDataMaxValue(): Float{

        return when(chart.data == null){
            true-> {
                standardInterval = defaultInterval
                maxStandardValue
            }
            false -> {
                val maxValue = if(calYMax == -1f) chart.data.yMax else calYMax
                when(maxValue < maxStandardValue){
                    true -> {
                        standardInterval = defaultInterval
                        maxStandardValue
                    }
                    false -> {
                        standardInterval = defaultAdjustInterval
                        val max = standardInterval * Math.ceil(maxValue / standardInterval.toDouble())
                        max.toFloat()
                    }
                }
            }
        }
    }

//    private var calYMin = -1
//    fun setYMin(value: Any?){
//        if(value != null)
//            calYMin = value as Int
//    }

    private var calYMax = -1f
    fun setYMax(value: Any?){
        if(value != null)
            calYMax = value as Float
    }

    private fun setYAxisCount(count: Int) {
        setLeftAxisCount(count)
    }

    private fun setLeftAxisCount(count: Int){
        val yAxisValueFormatter = JYAxisValueFormatter(count, false)
        leftAxis.valueFormatter = yAxisValueFormatter
    }

    private fun setYAxisMin(min: Float?) {
        // this replaces setStartAtZero(true)
        if (min == null) return
        setLeftAxisMin(min)
    }

    private fun setLeftAxisMin(min: Float) {
        leftAxis.axisMinimum = min
    }

    private fun setYAxisMax(max: Float?) {
        // this replaces setStartAtZero(true)
        if(max == null) return
        setLeftAxisMax(max + retainY)
        maxY = max
    }

    private fun setLeftAxisMax(max: Float) {
        leftAxis.axisMaximum = max
    }

    private fun updateGrid(){

        val initCount = (leftAxis.axisMaximum - leftAxis.axisMinimum) / standardInterval

        setYAxisCount(initCount.toInt())
    }

}