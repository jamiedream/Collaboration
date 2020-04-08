/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 8/ 4/ 2020.
 * Last modified 2/27/20 2:40 PM
 */

package com.viwave.RossmaxConnect.Measurement.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.viwave.collaborationproject.R

class ChartValueComponent2: RelativeLayout {

    private val unit by lazy { this.findViewById<TextView>(R.id.chart_component2_unit) }
    private val value by lazy { this.findViewById<TextView>(R.id.chart_component2_value) }

    constructor(context: Context): super(context){
        init(context, null)
    }

    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet){
        init(context, attributeSet)
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int): super(context, attributeSet, defStyleAttr){
        init(context, attributeSet)
    }

    private fun init(context: Context, attributesSet: AttributeSet?){
        View.inflate(context, R.layout.view_chart_component_2, this)
        when(attributesSet != null){ true -> {
            context.theme.obtainStyledAttributes(
                attributesSet, R.styleable.ChartValueComponent2, 0, 0).apply {
                val subTitleStr = getString(R.styleable.ChartValueComponent2_chartComponent2Unit)
                setUnit(subTitleStr)
                recycle()
            }
        }}
    }

    private fun setUnit(unit: String?){
        if(unit != null)
            this.unit.text = unit
    }

    fun setValue(value: String?){
        if(value == null)
            this.value.text = "--"
        else
            this.value.text = value
    }

}