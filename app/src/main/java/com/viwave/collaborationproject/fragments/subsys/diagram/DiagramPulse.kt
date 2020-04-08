/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/25/20 1:49 PM
 */

package com.viwave.collaborationproject.fragments.subsys.diagram

import com.github.mikephil.charting.data.CombinedData
import com.viwave.RossmaxConnect.Measurement.chart.JLineData
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.calXIndex
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.getScaledHighLow
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.getTimeDateFormat
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.switchPress
import com.viwave.RossmaxConnect.Measurement.widgets.ChartValueComponent2
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.data.bios.Bio
import com.viwave.collaborationproject.fragments.ITogglePressedListener
import com.viwave.collaborationproject.fragments.subsys.caseList.CaseListFragment.Companion.bioViewModel
import com.viwave.collaborationproject.fragments.subsys.diagram.yaxis.YAxisPulse
import com.viwave.collaborationproject.fragments.subsys.history.HistoryChartFragment
import com.viwave.collaborationproject.utils.DateUtil
import java.lang.ref.WeakReference

class DiagramPulse(fragment: WeakReference<HistoryChartFragment>): DiagramView(fragment), ITogglePressedListener {

    private val yAxis by lazy { YAxisPulse(chart) }

    private val markerPulseValue by lazy { view.findViewById<ChartValueComponent2>(R.id.pulse_marker_value) }

    //setting safe area
    private val pulseSafeLow = 80f
    private val pulseSafeHigh = 100f

    override fun pressedToggle(toggleName: String) {

        (toggleName != togglePeriod).let {

            togglePeriod = toggleName

            chart.dragDecelerationFrictionCoef = 0f
            xAxis.setPreLocX(chart.lowestVisibleX, chart.highestVisibleX)

            (bioViewModel.getMarkerData().value)?.let {
                it as Bio.Pulse
                xAxis.setMarkerDataX(calXIndex(it.takenAt))
            }

            when (toggleName) {
                view.context.getString(R.string.month) -> switchPress(JTimeSwitcher.MONTH)
                view.context.getString(R.string.week) -> switchPress(JTimeSwitcher.WEEK)
                view.context.getString(R.string.day) -> switchPress(JTimeSwitcher.DAY)
            }

            xAxis.updateXAxis()
            updateYAxis()

            setData()

        }

    }

    override fun initPeriod() {
        tabPeriod.setToggleListener(this)
    }

    override fun setData() {

        val combineData = CombinedData()
        combineData.setData(JLineData.getPulseLineData(bioViewModel.getPulseListData().value))
        chart.data = combineData
        chart.invalidate()

    }

    override fun setMarkerData(data: Bio?) {
        data as Bio.Pulse?
        when (data == null) {
            false -> {
                //date
                markerTime.text = DateUtil.getMeasurementTime(data.takenAt * 1000L)
                //marker data
                markerPulseValue.setValue(data.pulse.toString())
            }
        }
    }

    override fun updateXAxis() {
        xAxis.setBackXValue(getScaledHighLow(xAxis.getBackXValue())[0])
    }

    override fun updateYAxis() {
        val newX = getScaledHighLow(xAxis.getBackXValue())
        val dynamicY = getYMinMax(newX[0], newX[1])
        yAxis.setYMax(dynamicY[0])
        yAxis.setYMin(dynamicY[1])
        yAxis.updateYAxis()

        setSafeAreaGrid(
            !(pulseSafeHigh < yAxis.minY || pulseSafeLow > yAxis.maxY),
            if (pulseSafeLow < yAxis.minY) yAxis.minY else pulseSafeLow,
            if (pulseSafeHigh > yAxis.maxY) yAxis.maxY else pulseSafeHigh,
            yAxis.minY,
            yAxis.minY,
            yAxis.maxY,
            R.color.cornflower_blue
        )
    }

    private fun getYMinMax(start: Float, end: Float): MutableList<Float> {

        val inRangeList = mutableListOf<Bio.Pulse>()
        bioViewModel.getPulseListData().value?.forEach {
            val index = calXIndex(it.takenAt)
            if (index in start..end) {
                inRangeList.add(it)
            }

        }

        var pulseHigh = yAxis.defaultMax
        var pulseLow = yAxis.defaultMin
        if (inRangeList.isEmpty()) return mutableListOf(pulseHigh, pulseLow)
        for (num in 0 until inRangeList.size) {
            val dateTemp = inRangeList[num].pulse.toFloat()
            pulseHigh = Math.max(pulseHigh, dateTemp)
            pulseLow = Math.min(pulseLow, dateTemp)
        }

        return mutableListOf(pulseHigh, pulseLow)
    }

    override fun emptyMarker() {
        bioViewModel.getMarkerData().value = null
        chart.highlightValue(null)
        initTopData()
    }

    private fun initTopData() {
        markerPulseValue.setValue(null)
    }

    override fun updateTranslateData() {
        val newX = getScaledHighLow(chart.lowestVisibleX)
        xAxis.setBackXValue(newX[0])
        markerTime.text = getTimeDateFormat(newX[0], newX[1])
        updateYAxis()

    }

}