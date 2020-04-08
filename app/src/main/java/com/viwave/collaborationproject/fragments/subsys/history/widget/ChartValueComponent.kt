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

class ChartValueComponent: RelativeLayout {

    private val title by lazy { this.findViewById<TextView>(R.id.chart_component_title) }
    private val subTitle by lazy { this.findViewById<TextView>(R.id.chart_component_subtitle) }
    private val value by lazy { this.findViewById<TextView>(R.id.chart_component_value) }

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
        View.inflate(context, R.layout.view_chart_component, this)
        when(attributesSet != null){ true -> {
            context.theme.obtainStyledAttributes(
                attributesSet, R.styleable.ChartValueComponent, 0, 0).apply {
                val titleStr = getString(R.styleable.ChartValueComponent_chartComponentTitle)
                val subTitleStr = getString(R.styleable.ChartValueComponent_chartComponentSubTitle)
                val contentStr = getString(R.styleable.ChartValueComponent_chartComponentContent)
                value.text = contentStr
                setTitle(titleStr)
                setSubtitle(subTitleStr)
                recycle()
            }
        }}
    }

    private fun setTitle(title: String?){
        if(title != null)
            this.title.text = title
    }

    private fun setSubtitle(subTitle: String?){
        if(subTitle != null)
            this.subTitle.text = subTitle
    }

    fun setValue(value: String?, nullStr: String = "--"){
        if(value == null)
            this.value.text = nullStr
        else
            this.value.text = value
    }
}