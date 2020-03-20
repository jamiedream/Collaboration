/*
 * Copyright (c) viWave 2019.
 * Create by J.Y Yen 11/ 7/ 2019.
 * Last modified 7/11/19 2:53 PM
 */

package com.viwave.collaborationproject.fragments.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.View.OnTouchListener
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.fragments.ITogglePressedListener

class TabView: ConstraintLayout {

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

    private val tabFirst by lazy { findViewById<TextView>(R.id.tab_first) }
    private val tabSecond by lazy { findViewById<TextView>(R.id.tab_second) }
    private val tabThird by lazy { findViewById<TextView>(R.id.tab_third) }
    @SuppressLint("ClickableViewAccessibility")
    private fun init(context: Context, attributesSet: AttributeSet?){
        View.inflate(context, R.layout.view_tab, this)

        when(attributesSet != null){ true -> {
            context.theme.obtainStyledAttributes(
                attributesSet, R.styleable.TabView, 0, 0).apply {
                val first = getString(R.styleable.TabView_autoFitTabFirst)
                val second = getString(R.styleable.TabView_autoFitTabSecond)
                val third = getString(R.styleable.TabView_autoFitTabThird)
                tabFirst.text = first
                tabSecond.text = second
                tabThird.text = third
                recycle()
            }
        }}

        tabFirst.setOnTouchListener(tabTouchListener)
        tabSecond.setOnTouchListener(tabTouchListener)
        tabThird.setOnTouchListener(tabTouchListener)
    }

    private lateinit var toggleListener: ITogglePressedListener
    fun setToggleListener(listener: ITogglePressedListener){
        toggleListener = listener
    }

    private val tabTouchListener =
        OnTouchListener { v, event ->
            v as TextView
            switchTabStatus(v)
            toggleListener.pressedToggle(v.text.toString())
            return@OnTouchListener true
        }

    private fun switchTabStatus(view: TextView){
        when(view.text){
            tabFirst.text -> {
                tabFirst.isPressed = true
                tabSecond.isPressed = false
                tabThird.isPressed = false
            }
            tabSecond.text -> {
                tabFirst.isPressed = false
                tabSecond.isPressed = true
                tabThird.isPressed = false
            }
            tabThird.text -> {
                tabFirst.isPressed = false
                tabSecond.isPressed = false
                tabThird.isPressed = true
            }
        }
    }

    fun setSelectedTab(tabText: String) {
        val selectedTextView =
            when(tabText){
                tabFirst.text.toString() -> tabFirst
                tabSecond.text.toString() -> tabSecond
                else -> tabThird
            }
        switchTabStatus(selectedTextView)
    }


}