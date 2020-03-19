/*
 * Copyright (c) viWave 2019.
 * Create by J.Y Yen 6/ 9/ 2019.
 * Last modified 9/6/19 10:35 AM
 */

package com.viwave.RossmaxConnect.Measurement.chart

import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.CombinedChart
import com.github.mikephil.charting.components.XAxis
import com.viwave.collaborationproject.R

class JCustomDistributionXAxis(private val chart: CombinedChart) {

    private val TAG = this::class.java.simpleName

    private val xAxis by lazy { chart.xAxis }


    private var xAxisValueFormatter: JCustomDistributionXValueFormatter = JCustomDistributionXValueFormatter()

    private val dashLength = 8f

    init {

        xAxis.textColor = ContextCompat.getColor(chart.context, R.color.nandor)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawAxisLine(false)
        xAxis.axisMinimum = -.5f
        xAxis.axisMaximum = 100.5f

        setXValueFormatter()
        setXParam()

        xAxis.setDrawLimitLinesBehindData(true)

        xAxis.gridColor = ContextCompat.getColor(chart.context, R.color.humming_bird)
        xAxis.enableGridDashedLine(dashLength, dashLength, 0f)

    }

    private fun setXMoveBackward(backX: Float){
        chart.moveViewToX(backX)
        chart.dragDecelerationFrictionCoef = .9f
    }

    private fun setXValueFormatter(){
        xAxis.valueFormatter = xAxisValueFormatter
    }

    private fun setXParam(){

        xAxis.granularity = 1f
        xAxis.labelCount = 101
        chart.setVisibleXRange(11f, 11f)
        setXMoveBackward(-.5f)

    }

}
