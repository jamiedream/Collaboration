/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/25/20 5:01 PM
 */

package com.viwave.collaborationproject.fragments.subsys.diagram

import com.github.mikephil.charting.data.CombinedData
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.DAY
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.MONTH
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.WEEK
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.calXIndex
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.getScaledHighLow
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.switchPress
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.timeFormat
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.updateBarWidth
import com.viwave.RossmaxConnect.Measurement.yaxis.YAxisOxygen
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.data.bios.Bio
import com.viwave.collaborationproject.fragments.ITogglePressedListener
import com.viwave.collaborationproject.fragments.subsys.caseList.CaseListFragment.Companion.bioViewModel
import com.viwave.collaborationproject.fragments.subsys.history.HistoryChartFragment
import com.viwave.collaborationproject.fragments.widgets.MarkerInfoLayout
import com.viwave.collaborationproject.utils.DateUtil
import java.lang.ref.WeakReference

class DiagramOxygen(fragment: WeakReference<HistoryChartFragment>): DiagramView(fragment),
    ITogglePressedListener {

    private val yAxis by lazy { YAxisOxygen(chart) }

    private val markerOxygenValue by lazy { view.findViewById<MarkerInfoLayout>(R.id.oxygen_marker_value) }


    override fun pressedToggle(toggleName: String) {

        (toggleName != togglePeriod).let{

            togglePeriod = toggleName

            chart.dragDecelerationFrictionCoef = 0f
            xAxis.setPreLocX(chart.lowestVisibleX, chart.highestVisibleX)

            (bioViewModel.getMarkerData().value)?.let {
                it as Bio.Oxygen
                xAxis.setMarkerDataX(calXIndex(it.takenAt))
            }

            when(toggleName){
                view.context.getString(R.string.month) -> switchPress(MONTH)
                view.context.getString(R.string.week) -> switchPress(WEEK)
                view.context.getString(R.string.day) -> switchPress(DAY)
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
        combineData.setData(JCandleData.getSpO2CandleData(updateBarWidth(), bioViewModel.getOxygenListData().value, timeFormat))
        chart.data = combineData
        chart.invalidate()

    }

    override fun setMarkerData(data: Bio?) {
        data as Bio.Oxygen?
        when (data == null) {
            false -> {
                //date
                markerTime.text = DateUtil.getMeasurementTime(data.takenAt * 1000L)
                //marker data
                markerOxygenValue.setValue(data.spo2Highest.toString())
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

    private fun getYMinMax(start: Float, end: Float): MutableList<Int>{

        val inRangeList = mutableListOf<Bio.Oxygen>()
        bioViewModel.getOxygenListData().value?.forEach {
            val index = calXIndex(it.takenAt)
            if(index in start..end){
                inRangeList.add(it)
            }

        }

        var spo2High = 0
        var spo2Low = 100
        if(inRangeList.isEmpty()) return mutableListOf(spo2High, spo2Low)
        for(num in 0 until inRangeList.size){
            spo2High = Math.max(spo2High, inRangeList[num].spo2Highest)
            spo2Low = Math.min(spo2Low, inRangeList[num].spo2Lowest)
        }

        return mutableListOf(spo2High, spo2Low)
    }

    override fun emptyMarker() {
        bioViewModel.getMarkerData().value = null
        chart.highlightValue(null)
        initTopData()
    }

    private fun initTopData() {
        markerTime.text = "--"
        markerOxygenValue.setValue(null)
    }

    override fun updateTranslateData() {
        val newX = getScaledHighLow(chart.lowestVisibleX)
        xAxis.setBackXValue(newX[0])
        updateYAxis()

    }


}