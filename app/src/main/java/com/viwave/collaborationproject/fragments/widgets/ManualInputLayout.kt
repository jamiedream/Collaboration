package com.viwave.collaborationproject.fragments.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.viwave.collaborationproject.R

class ManualInputLayout: ConstraintLayout {

    constructor(context: Context): super(context){
        init(context, null)
    }

    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet){
        init(context, attributeSet)
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int): super(context, attributeSet, defStyleAttr){
        init(context, attributeSet)
    }

    private val titleMeasure by lazy { findViewById<TextView>(R.id.title_measurement) }
    private val unitMeasure by lazy { findViewById<TextView>(R.id.unit_measurement) }
    private val editMeasure by lazy { findViewById<EditText>(R.id.value_measurement) }
    private fun init(context: Context, attributesSet: AttributeSet?){
        View.inflate(context, R.layout.view_manual_input, this)

        when(attributesSet != null){ true -> {
            context.theme.obtainStyledAttributes(
                attributesSet, R.styleable.ManualInput, 0, 0).apply {
                val title = getString(R.styleable.ManualInput_manualInputTitle)
                val unit = getString(R.styleable.ManualInput_manualInputUnit)
                val ime = getInt(R.styleable.ManualInput_manualInputEditIme, 0)
                val type = getInt(R.styleable.ManualInput_manualInputEditType, 0)
                titleMeasure.text = title
                unitMeasure.text = unit
                editMeasure.imeOptions = ime
                editMeasure.inputType = type
                recycle()
            }
        }}
    }

    fun enableEdit(isEnable: Boolean){
        if(isEnable){
            titleMeasure.setTextColor(ContextCompat.getColor(context, R.color.silver_light))
            unitMeasure.setTextColor(ContextCompat.getColor(context, R.color.silver_light))
            editMeasure.isEnabled = true

        }else{
            titleMeasure.setTextColor(ContextCompat.getColor(context, R.color.silver))
            unitMeasure.setTextColor(ContextCompat.getColor(context, R.color.silver))
            editMeasure.isEnabled = false
        }
    }
}