/*
 * Copyright (c) viWave 2019.
 * Create by J.Y Yen 20/ 8/ 2019.
 * Last modified 8/1/19 6:09 PM
 */

package com.viwave.RossmaxConnect.Measurement.chart

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.DecimalFormat

class JYAxisValueFormatter(private val count: Int, private val isDecimalShow: Boolean?): ValueFormatter() {

    override fun getFormattedValue(value: Float): String {
        return when(isDecimalShow){
            true -> DecimalFormat("####.0").format(value)
            else -> DecimalFormat("####").format(value)
        }
    }

    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        axis?.labelCount = count
        return super.getAxisLabel(value, axis)
    }


}