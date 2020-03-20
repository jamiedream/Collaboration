package com.viwave.collaborationproject.fragments.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.viwave.collaborationproject.R

class MarkerInfoLayout: ConstraintLayout {

    private val TAG = this::class.java.simpleName

    constructor(context: Context): super(context){
        init(context, null)
    }

    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet){
        init(context, attributeSet)
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int): super(context, attributeSet, defStyleAttr){
        init(context, attributeSet)
    }

    private val txtTitle by lazy { findViewById<TextView>(R.id.title) }
    private val txtValue by lazy { findViewById<TextView>(R.id.value) }
    private val txtUnit by lazy { findViewById<TextView>(R.id.unit) }
    private fun init(context: Context, attributesSet: AttributeSet?){
        View.inflate(context, R.layout.view_marker_info, this)

        when(attributesSet != null){ true -> {
            context.theme.obtainStyledAttributes(
                attributesSet, R.styleable.MarkerInfo, 0, 0).apply {
                txtTitle.text = getString(R.styleable.MarkerInfo_markerInfoTitle)
                txtUnit.text = getString(R.styleable.MarkerInfo_markerInfoUnit)
                val initValue = getString(R.styleable.MarkerInfo_markerInfoValue)
                if(initValue != null) txtValue.text = initValue
                recycle()
            }
        }}
    }

    fun setValue(value: String?){
        if(value == null)
            txtValue.text = "--"
        else
            txtValue.text = value

    }
}