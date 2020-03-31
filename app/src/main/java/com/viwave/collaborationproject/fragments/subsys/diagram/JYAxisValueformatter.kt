/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/25/20 1:49 PM
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