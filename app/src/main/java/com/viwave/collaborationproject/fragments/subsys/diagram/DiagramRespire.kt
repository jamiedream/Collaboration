/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 8/ 4/ 2020.
 * Last modified 4/8/20 4:07 PM
 */

package com.viwave.collaborationproject.fragments.subsys.diagram

import com.github.mikephil.charting.data.CombinedData
import com.viwave.RossmaxConnect.Measurement.chart.JLineData
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.backXCount
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.calXIndex
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.getScaledHighLow
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.switchPress
import com.viwave.RossmaxConnect.Measurement.widgets.ChartValueComponent2
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.data.bios.Bio
import com.viwave.collaborationproject.fragments.ITogglePressedListener
import com.viwave.collaborationproject.fragments.subsys.caseList.CaseListFragment
import com.viwave.collaborationproject.fragments.subsys.caseList.CaseListFragment.Companion.bioViewModel
import com.viwave.collaborationproject.fragments.subsys.diagram.yaxis.YAxisRespire
import com.viwave.collaborationproject.fragments.subsys.history.HistoryChartFragment
import com.viwave.collaborationproject.utils.DateUtil
import java.lang.ref.WeakReference

class DiagramRespire(fragment: WeakReference<HistoryChartFragment>): DiagramView(fragment),
    ITogglePressedListener {

    private val yAxis by lazy { YAxisRespire(chart) }

    private val markerRespireValue by lazy { view.findViewById<ChartValueComponent2>(R.id.respire_marker_value) }

    //setting safe area
    private val respireSafeLow = 20f
    private val respireSafeHigh = 30f

    override fun pressedToggle(toggleName: String) {

        (toggleName != togglePeriod).let {

            togglePeriod = toggleName

            chart.dragDecelerationFrictionCoef = 0f
            xAxis.setPreLocX(chart.lowestVisibleX, chart.highestVisibleX)

            (bioViewModel.getMarkerData().value)?.let {
                it as Bio.Respire
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
            markerTime.text =
                JTimeSwitcher.getTimeDateFormat(
                    xAxis.getBackXValue(),
                    xAxis.getBackXValue() + backXCount
                )
        }

    }

    override fun initPeriod() {
        tabPeriod.setToggleListener(this)
    }

    override fun setData() {

        val combineData = CombinedData()
        combineData.setData(JLineData.getRespireLineData(CaseListFragment.bioViewModel.getRespireListData().value))
        chart.data = combineData
        chart.invalidate()

    }

    override fun setMarkerData(data: Bio?) {
        data as Bio.Respire?
        when (data == null) {
            false -> {
                //date
                markerTime.text = DateUtil.getMeasurementTime(data.takenAt * 1000L)
                //marker data
                markerRespireValue.setValue(data.respire.toString())
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
            !(respireSafeHigh < yAxis.minY || respireSafeLow > yAxis.maxY),
            if (respireSafeLow < yAxis.minY) yAxis.minY else respireSafeLow,
            if (respireSafeHigh > yAxis.maxY) yAxis.maxY else respireSafeHigh,
            yAxis.minY,
            yAxis.minY,
            yAxis.maxY,
            R.color.aqua_blue
        )
    }

    private fun getYMinMax(start: Float, end: Float): MutableList<Float> {

        val inRangeList = mutableListOf<Bio.Respire>()
        bioViewModel.getRespireListData().value?.forEach {
            val index = calXIndex(it.takenAt)
            if (index in start..end) {
                inRangeList.add(it)
            }

        }

        var respireHigh = yAxis.defaultMax
        var respireLow = yAxis.defaultMin
        if (inRangeList.isEmpty()) return mutableListOf(respireHigh, respireLow)
        for (num in 0 until inRangeList.size) {
            val dateTemp = inRangeList[num].respire.toFloat()
            respireHigh = Math.max(respireHigh, dateTemp)
            respireLow = Math.min(respireLow, dateTemp)
        }

        return mutableListOf(respireHigh, respireLow)
    }

    override fun emptyMarker() {
        bioViewModel.getMarkerData().value = null
        chart.highlightValue(null)
        initTopData()
    }

    private fun initTopData() {
        markerRespireValue.setValue(null)
    }

    override fun updateTranslateData() {
        val newX = getScaledHighLow(chart.lowestVisibleX)
        xAxis.setBackXValue(newX[0])
        markerTime.text = JTimeSwitcher.getTimeDateFormat(newX[0], newX[1])
        updateYAxis()

    }

}