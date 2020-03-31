/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/25/20 1:49 PM
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
