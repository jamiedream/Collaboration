/*
 * Copyright (c) viWave 2019.
 * Create by J.Y Yen 20/ 8/ 2019.
 * Last modified 8/12/19 3:02 PM
 */

package com.viwave.RossmaxConnect.Measurement.chart

import androidx.core.content.ContextCompat
import com.viwave.collaborationproject.R

class JCustomYAxis(
    private val chart: JCustomCombinedChart,
    private val valueList: MutableList<Float>
    ) {

    private val TAG = this::class.java.simpleName

    private val rightAxis by lazy { chart.axisRight }
    private val leftAxis by lazy { chart.axisLeft }

    private val minStandardValue = valueList[0]
    private val maxStandardValue = valueList[1]
    private val retainY = valueList[2]
    private val standardInterval = valueList[3]
    private val adjustInterval = valueList[4]

    private val isTemperature = maxStandardValue < 40 && minStandardValue > 30

    init {

        rightAxis.yOffset = 0f
        rightAxis.setDrawAxisLine(false)
        rightAxis.setDrawGridLines(false)
        rightAxis.textColor = ContextCompat.getColor(chart.context, R.color.nandor)

        leftAxis.yOffset = 0f
        leftAxis.setDrawAxisLine(false)
        leftAxis.gridLineWidth = 1f
        leftAxis.gridColor = ContextCompat.getColor(chart.context, R.color.humming_bird)
        leftAxis.textColor = ContextCompat.getColor(chart.context, R.color.nandor)

        setYAxisMin(minStandardValue)
        setYAxisMax(maxStandardValue)

    }

    fun invisibleRightAxis(){
        rightAxis.isEnabled = false
    }

    fun updateYAxis(){

//        LogUtil.logD(TAG, getDataMinValue())
        setYAxisMax(getDataMaxValue() + retainY)
        setYAxisMin(getDataMinValue())
        updateGrid()
    }

    private fun getDataMaxValue(): Float{
        return when(chart.data == null){
            true-> maxStandardValue
            false -> {
                when(chart.data.yMax < maxStandardValue + retainY){
                    true -> maxStandardValue
                    false -> {
                        val max = adjustInterval * Math.ceil(chart.data.yMax / adjustInterval.toDouble())
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
                true -> minStandardValue
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

    private fun setYAxisCount(count: Int){
        setLeftAxisCount(count)
        setRightAxisCount(count)
    }

    private fun setRightAxisCount(count: Int){
        val yAxisValueFormatter = JYAxisValueFormatter(count, isTemperature)
        rightAxis.valueFormatter = yAxisValueFormatter
    }

    private fun setLeftAxisCount(count: Int){
        val yAxisValueFormatter = JYAxisValueFormatter(count, isTemperature)
        leftAxis.valueFormatter = yAxisValueFormatter
    }

    private fun setYAxisMin(min: Float?){
        // this replaces setStartAtZero(true)
        if(min == null) return
        setLeftAxisMin(min)
        setRightAxisMin(min)
    }

    private fun setRightAxisMin(min: Float){
        rightAxis.axisMinimum = min
    }

    private fun setLeftAxisMin(min: Float) {
        leftAxis.axisMinimum = min
    }

    private fun setYAxisMax(max: Float){
        // this replaces setStartAtZero(true)
        setLeftAxisMax(max)
        setRightAxisMax(max)
    }

    private fun setRightAxisMax(max: Float){
        rightAxis.axisMaximum = max
    }

    private fun setLeftAxisMax(max: Float) {
        leftAxis.axisMaximum = max
    }

    private fun updateGrid(){

//        LogUtil.logD(TAG, leftAxis.axisMaximum )
//        LogUtil.logD(TAG, maxStandardValue + retainY)
        val initCount = when(leftAxis.axisMaximum > maxStandardValue + retainY || leftAxis.axisMinimum < minStandardValue){
            true -> ((leftAxis.axisMaximum - leftAxis.axisMinimum) - adjustInterval) / adjustInterval
            false -> ((leftAxis.axisMaximum - leftAxis.axisMinimum) - standardInterval) / standardInterval
        }

        setYAxisCount(initCount.toInt() + 1)
//        LogUtil.logD(TAG, initCount)
    }

}