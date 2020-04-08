/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/25/20 1:49 PM
 */

package com.viwave.collaborationproject.fragments.subsys.diagram

import android.view.MotionEvent
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.listener.ChartTouchListener
import com.github.mikephil.charting.listener.OnChartGestureListener
import com.viwave.RossmaxConnect.Measurement.chart.JCustomCombinedChart
import com.viwave.RossmaxConnect.Measurement.chart.JCustomXAxis
import com.viwave.RossmaxConnect.Measurement.chart.JMarkdown
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.MONTH
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.switchPress
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.data.bios.Bio
import com.viwave.collaborationproject.fragments.widgets.TabView
import java.lang.ref.WeakReference

abstract class DiagramView(fragment: WeakReference<out Fragment>) {

    protected val TAG = this::class.java.simpleName
    protected val view by lazy { fragment.get()?.view!! }

    protected var togglePeriod: String
    protected val tabPeriod by lazy { view.findViewById<TabView>(R.id.chart_auto_fit) }

    protected val chart by lazy { view.findViewById<JCustomCombinedChart>(R.id.combine_chart) }
    protected val xAxis by lazy { JCustomXAxis(chart) }
    private val mv by lazy {  JMarkdown(view.context, R.layout.view_triangle) }

    protected val markerTime by lazy { view.findViewById<TextView>(R.id.chart_range_date) }
    protected val arrowPrevious by lazy { view.findViewById<ImageView>(R.id.arrow_previous) }
    protected val arrowNext by lazy { view.findViewById<ImageView>(R.id.arrow_next) }

    init {
        togglePeriod = view.context.getString(R.string.month)
        switchPress(MONTH)

        chart.setExtraOffsets(
            0f, 0f, 0f,
            view.context.resources.getDimension(R.dimen.health_chart_offset_bottom)
        )

        mv.chartView = chart
        chart.marker = mv

        markerTime.text = "--"
    }

    fun initView(){

        initPeriod()
        tabPeriod.setSelectedTab(togglePeriod)

        setData()

        xAxis.updateXAxis()
        updateYAxis()

        initChartDateArrow()

        chart.onChartGestureListener = customOnChartGestureListener
    }

    abstract fun setMarkerData(data: Bio?)
    abstract fun initPeriod()
    abstract fun setData()


    /**
     * Safe area
     * */
    fun setSafeAreaGrid(enable: Boolean, low: Float, high: Float, ys: Float, yMin: Float, yMax: Float, gridColor: Int){
        chart.setDrawCustomGridBackground(enable, low, high, ys, yMin, yMax, gridColor)
    }

    fun setSecondSafeArea(low: Float, high: Float){
        chart.setSecondDrawCustomGridBackground(low, high)
    }

    private fun initChartDateArrow(){

        markerTime.text = JTimeSwitcher.getTimeDateFormat(
            xAxis.getBackXValue(),
            xAxis.getBackXValue() + JTimeSwitcher.backXCount
        )
        arrowPrevious.setOnClickListener {
            xAxis.setPreLocX(xAxis.getBackXValue(),
                xAxis.getBackXValue() + JTimeSwitcher.backXCount)
            xAxis.arrowBackward()
            markerTime.text =
                JTimeSwitcher.getTimeDateFormat(
                    xAxis.getBackXValue(),
                    xAxis.getBackXValue() + JTimeSwitcher.backXCount
                )
            updateXAxis()
            updateYAxis()
            emptyMarker()
        }
        arrowNext.setOnClickListener {
            xAxis.setPreLocX(xAxis.getBackXValue(),
                xAxis.getBackXValue() + JTimeSwitcher.backXCount)
            xAxis.arrowForward()
            markerTime.text =
                JTimeSwitcher.getTimeDateFormat(
                    xAxis.getBackXValue(),
                    xAxis.getBackXValue() + JTimeSwitcher.backXCount
                )
            updateXAxis()
            updateYAxis()
            emptyMarker()
        }
    }

    abstract fun updateXAxis()
    abstract fun updateYAxis()
    abstract fun emptyMarker()
    abstract fun updateTranslateData()

    private val customOnChartGestureListener = object : OnChartGestureListener {

        var isGestureEnd = false

        override fun onChartGestureStart(me: MotionEvent?, lastPerformedGesture: ChartTouchListener.ChartGesture?) {
//                LogUtil.logD(TAG + "_onChartGestureStart", me?.action)
//                LogUtil.logD(TAG + "_onChartGestureStart", lastPerformedGesture)
            chart.dragDecelerationFrictionCoef = .9f
        }

        override fun onChartGestureEnd(me: MotionEvent?, lastPerformedGesture: ChartTouchListener.ChartGesture?) {
//                LogUtil.logD(TAG + "_onChartGestureEnd", me?.action)
//                LogUtil.logD(TAG + "_onChartGestureEnd", lastPerformedGesture)
            when(me?.action) {
                MotionEvent.ACTION_UP -> {
                    isGestureEnd = true
                    updateTranslateData()
                }
            }
        }

        override fun onChartLongPressed(me: MotionEvent?) {
//                LogUtil.logD(TAG + "_onChartLongPressed", me?.action)

        }

        override fun onChartDoubleTapped(me: MotionEvent?) {
//                LogUtil.logD(TAG + "_onChartDoubleTapped", me?.action)

        }

        override fun onChartSingleTapped(me: MotionEvent?) {
//                LogUtil.logD(TAG + "_onChartSingleTapped", me?.action)
//            when(me?.action) {
//                MotionEvent.ACTION_UP -> emptyMarker()
//            }
        }

        override fun onChartFling(me1: MotionEvent?, me2: MotionEvent?, velocityX: Float, velocityY: Float) {
//                LogUtil.logD(TAG + "_onChartFling", me1?.action)

        }

        override fun onChartScale(me: MotionEvent?, scaleX: Float, scaleY: Float) {
//                LogUtil.logD(TAG + "_onChartScale", me?.action)

        }

        override fun onChartTranslate(me: MotionEvent?, dX: Float, dY: Float) {
//            LogUtil.logD(TAG, me?.action)
            when (me?.action) {
                MotionEvent.ACTION_MOVE -> {
                    //init top info area
                    emptyMarker()

                    if(isGestureEnd){
                        if(chart.dragDecelerationFrictionCoef > .06f) {
                            chart.dragDecelerationFrictionCoef -= .06f
                        }else {
                            chart.dragDecelerationFrictionCoef = 0f
                            updateTranslateData()
                            isGestureEnd = false
                        }
                    }

                }

            }
        }
    }
}