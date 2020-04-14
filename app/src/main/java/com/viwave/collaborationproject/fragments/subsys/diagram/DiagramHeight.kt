/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 8/ 4/ 2020.
 * Last modified 4/8/20 4:53 PM
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
import com.viwave.collaborationproject.fragments.subsys.caseList.CaseListFragment.Companion.bioViewModel
import com.viwave.collaborationproject.fragments.subsys.diagram.yaxis.YAxisHeight
import com.viwave.collaborationproject.fragments.subsys.history.HistoryChartFragment
import com.viwave.collaborationproject.utils.DataFormatUtil
import com.viwave.collaborationproject.utils.DateUtil
import java.lang.ref.WeakReference

class DiagramHeight(fragment: WeakReference<HistoryChartFragment>): DiagramView(fragment),
    ITogglePressedListener {

    private val yAxis by lazy { YAxisHeight(chart) }

    private val markerHeightValue by lazy { view.findViewById<ChartValueComponent2>(R.id.height_marker_value) }

    override fun pressedToggle(toggleName: String) {

        (toggleName != togglePeriod).let {

            togglePeriod = toggleName

            chart.dragDecelerationFrictionCoef = 0f
            xAxis.setPreLocX(chart.lowestVisibleX, chart.highestVisibleX)

            (bioViewModel.getMarkerData().value)?.let {
                it as Bio.Height
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
        combineData.setData(JLineData.getHeightLineData(bioViewModel.getHeightListData().value))
        chart.data = combineData
        chart.invalidate()

    }

    override fun setMarkerData(data: Bio?) {
        data as Bio.Height?
        when (data == null) {
            false -> {
                //date
                markerTime.text = DateUtil.getMeasurementTime(data.takenAt * 1000L)
                //marker data
                markerHeightValue.setValue(DataFormatUtil.formatString(data.height))
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
    }

    private fun getYMinMax(start: Float, end: Float): MutableList<Float> {

        val inRangeList = mutableListOf<Bio.Height>()
        bioViewModel.getHeightListData().value?.forEach {
            val index = calXIndex(it.takenAt)
            if (index in start..end) {
                inRangeList.add(it)
            }

        }

        var heightHigh = yAxis.defaultMax
        var heightLow = yAxis.defaultMin
        if (inRangeList.isEmpty()) return mutableListOf(heightHigh, heightLow)
        for (num in 0 until inRangeList.size) {
            val dateTemp = inRangeList[num].height
            heightHigh = Math.max(heightHigh, dateTemp)
            heightLow = Math.min(heightLow, dateTemp)
        }

        return mutableListOf(heightHigh, heightLow)
    }

    override fun emptyMarker() {
        bioViewModel.getMarkerData().value = null
        chart.highlightValue(null)
        initTopData()
    }

    private fun initTopData() {
        markerHeightValue.setValue(null)
    }

    override fun updateTranslateData() {
        val newX = getScaledHighLow(chart.lowestVisibleX)
        xAxis.setBackXValue(newX[0])
        markerTime.text = JTimeSwitcher.getTimeDateFormat(newX[0], newX[1])
        updateYAxis()

    }

}