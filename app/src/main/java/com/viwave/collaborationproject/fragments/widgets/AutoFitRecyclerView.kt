/*
 * Copyright (c) viWave 2019.
 * Create by J.Y Yen 11/ 7/ 2019.
 * Last modified 7/11/19 2:53 PM
 */

package com.viwave.collaborationproject.fragments.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.utils.LogUtil

class AutoFitRecyclerView: RecyclerView {

    private var columnWidth = -1
    private var columnCount = -1
    private var paddingWidth = 0
    private var manager: GridLayoutManager? = null


    constructor(context: Context): super(context){
        init(context, null)
    }

    constructor(context: Context, attributesSet: AttributeSet): super(context, attributesSet){
        init(context, attributesSet)
    }

    constructor(context: Context, attributesSet: AttributeSet, defStyle: Int): super(context, attributesSet, defStyle){
        init(context, attributesSet)
    }


    private fun init(context: Context, attributesSet: AttributeSet?){

        when(attributesSet != null) {
            true -> {
                context.theme.obtainStyledAttributes(
                    attributesSet, R.styleable.AutoFitRecyclerView, 0, 0
                ).apply {
                    val count = getInt(R.styleable.AutoFitRecyclerView_autoFitItemColumnCount, 0)
                    val padding = getInt(R.styleable.AutoFitRecyclerView_autoFitItemPaddingWidth, 0)
                    columnCount = count
                    paddingWidth = padding
                    recycle()
                }
            }
        }
        manager = GridLayoutManager(context, 1)
        layoutManager = manager
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return super.dispatchTouchEvent(ev)
    }

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {

        manager?.spanCount = columnCount
        columnWidth = (measuredWidth - paddingWidth) / columnCount + 1
        LogUtil.logD("AutoFitRecyclerView", "$measuredWidth $columnCount $columnWidth $paddingWidth")
        super.onMeasure(columnWidth, heightSpec)

    }

}