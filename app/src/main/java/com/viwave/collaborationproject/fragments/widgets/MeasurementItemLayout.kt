/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/25/20 1:49 PM
 */

package com.viwave.collaborationproject.fragments.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.utils.DataFormatUtil

class MeasurementItemLayout: ConstraintLayout {

    constructor(context: Context): super(context){
        init(context, null)
    }

    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet){
        init(context, attributeSet)
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int): super(context, attributeSet, defStyleAttr){
        init(context, attributeSet)
    }

    private val titleMeasure by lazy { findViewById<TextView>(R.id.item_title) }
    private val unitMeasure by lazy { findViewById<TextView>(R.id.item_unit) }
    private val valueMeasure by lazy { findViewById<TextView>(R.id.item_value) }
    private fun init(context: Context, attributesSet: AttributeSet?){
        View.inflate(context, R.layout.view_measure_item, this)

        when(attributesSet != null){ true -> {
            context.theme.obtainStyledAttributes(
                attributesSet, R.styleable.MeasureItem, 0, 0).apply {
                val title = getString(R.styleable.MeasureItem_measureItemTitle)
                val unit = getString(R.styleable.MeasureItem_measureItemUnit)
                val defaultValue = getString(R.styleable.MeasureItem_measureItemValue)
                titleMeasure.text = title
                unitMeasure.text = unit
                if(defaultValue != null) valueMeasure.text = defaultValue
                recycle()
            }
        }}
    }

    fun setValue(value: Any?){
        if(value != null) {
            when (value) {
                is Float -> valueMeasure.text = DataFormatUtil.formatString(value)
                is Int -> valueMeasure.text = value.toString()
                else -> valueMeasure.text = value.toString()
            }
        }
    }

    fun setValues(firstValue: Any?, secondValue: Any?){
        if(firstValue != null && secondValue != null) 
            valueMeasure.text = String.format("%s / %s", firstValue.toString(), secondValue.toString())

    }
}