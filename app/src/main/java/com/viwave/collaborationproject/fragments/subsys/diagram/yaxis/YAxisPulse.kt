package com.viwave.collaborationproject.fragments.subsys.diagram.yaxis

import androidx.core.content.ContextCompat
import com.viwave.RossmaxConnect.Measurement.chart.JCustomCombinedChart
import com.viwave.collaborationproject.R

class YAxisPulse(private val chart: JCustomCombinedChart){

    private val TAG = this::class.java.simpleName

    private val leftAxis by lazy { chart.axisLeft }

    var defaultMax = 210f
    var defaultMin = 30f

    private val minStandardValue = defaultMin
    private val maxStandardValue = defaultMax
    private val retainY = 10f
    private val standardInterval = 30f

    var minY = minStandardValue
    var maxY = maxStandardValue + retainY

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
        chart.notifyDataSetChanged()
    }

    private fun getDataMaxValue(): Float {
        return when (chart.data == null) {
            true -> maxStandardValue
            false -> {
                val maxValue = if(calYMax == -1f) chart.data.yMax else calYMax
                when (maxValue < maxStandardValue) {
                    true -> maxStandardValue
                    false -> {
                        val max = standardInterval * Math.ceil(maxValue / standardInterval.toDouble())
                        max.toFloat()
                    }
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

    private fun setYAxisMin(min: Float?){
        // this replaces setStartAtZero(true)
        if(min == null) return
        leftAxis.axisMinimum = min
    }

    private fun setYAxisMax(max: Float?){
        // this replaces setStartAtZero(true)
        if(max == null) return
        leftAxis.axisMaximum = max
        maxY = max + retainY
    }

}