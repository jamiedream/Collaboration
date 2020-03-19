/*
 * Copyright (c) viWave 2019.
 * Create by J.Y Yen 6/ 9/ 2019.
 * Last modified 9/6/19 10:41 AM
 */

package com.viwave.RossmaxConnect.Measurement.chart

import com.github.mikephil.charting.formatter.ValueFormatter

class JCustomDistributionXValueFormatter: ValueFormatter() {

    private val TAG = this::class.java.simpleName
    private val valueStrList = mutableListOf<String>()

    init {
        for(num in 0 .. 100){
            valueStrList.add(num.toString())
        }
        valueStrList.reverse()
    }

    override fun getFormattedValue(value: Float): String {
        val index = Math.round(value)
        return if(index >= 0f && index < valueStrList.size)
            valueStrList[index]
        else ""
    }

}
