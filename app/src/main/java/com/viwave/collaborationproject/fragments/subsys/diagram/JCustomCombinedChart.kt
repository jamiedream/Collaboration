/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/25/20 1:49 PM
 */

package com.viwave.RossmaxConnect.Measurement.chart

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RectF
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.CombinedChart
import com.github.mikephil.charting.components.YAxis

class JCustomCombinedChart: CombinedChart {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    init {

        this.setBackgroundColor(Color.WHITE)
        this.isScaleYEnabled = false
        this.isScaleXEnabled = false
        this.description.isEnabled = false
        this.legend.isEnabled = false
        this.setXAxisRenderer(JXAxisRenderer(this.viewPortHandler, this.xAxis, this.getTransformer(YAxis.AxisDependency.LEFT)))
        this.drawOrder = arrayOf(DrawOrder.BUBBLE, DrawOrder.CANDLE,DrawOrder.LINE, DrawOrder.BAR, DrawOrder.SCATTER)

        this.setDragOffsetX(0f)

    }

    /**
     * set this to draw background with custom rectF
     */
    private var mDrawCustomgridBackground = false

    private var safeAreaLow = 0f
    private var safeAreaHigh = 0f
    private var yStart = 0f
    private var customGridColor = 0
    private var yBottom = 0f
    private var yTop = 0f
    fun setDrawCustomGridBackground(enable: Boolean, low: Float, high: Float, ys: Float, yMin: Float, yMax: Float, gridColor: Int) {
        mDrawCustomgridBackground = enable
        safeAreaLow = low
        safeAreaHigh = high
        yStart = ys
        yTop = yMax
        yBottom = yMin
        customGridColor = gridColor
    }

    private var secondSafeAreaLow = 0f
    private var secondSafeAreaHigh = 0f
    fun setSecondDrawCustomGridBackground(low: Float, high: Float) {
        secondSafeAreaLow = low
        secondSafeAreaHigh = high
    }

    override fun drawGridBackground(c: Canvas?) {

        if (mDrawCustomgridBackground) {

            mGridBackgroundPaint.color = ContextCompat.getColor(context, customGridColor)

            val left = mViewPortHandler.contentLeft()
            val top = mViewPortHandler.contentTop()
            val right = mViewPortHandler.contentRight()
            val bottom = mViewPortHandler.contentBottom()
            val yScale = (bottom - top) / (yTop - yBottom)

            val rectF = RectF(
                left,
                bottom  - (safeAreaHigh - yStart) * yScale,
                right,
                bottom - (safeAreaLow - yStart) * yScale
            )

//            LogUtil.logD("mDrawCustomgridBackground", left)
//            LogUtil.logD("mDrawCustomgridBackground", right)
//            LogUtil.logD("mDrawCustomgridBackground", top)
//            LogUtil.logD("mDrawCustomgridBackground", bottom)

            c?.drawRect(rectF, mGridBackgroundPaint)

            val secondRectF = RectF(
                left,
                bottom  - (secondSafeAreaHigh - yStart) * yScale,
                right,
                bottom - (secondSafeAreaLow - yStart) * yScale
            )
            c?.drawRect(secondRectF, mGridBackgroundPaint)
        }

        super.drawGridBackground(c)
    }

}

