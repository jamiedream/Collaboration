/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 24/ 3/ 2020.
 * Last modified 3/24/20 11:22 AM
 */

package com.viwave.collaborationproject.fragments.subsys.diagram

import com.github.mikephil.charting.data.CombinedData
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.DAY
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.MONTH
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.WEEK
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.calXIndex
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.getScaledHighLow
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.switchPress
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.updateBarWidth
import com.viwave.RossmaxConnect.Measurement.yaxis.YAxisBP
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.data.bios.Bio
import com.viwave.collaborationproject.fragments.ITogglePressedListener
import com.viwave.collaborationproject.fragments.subsys.caseList.CaseListFragment.Companion.bioViewModel
import com.viwave.collaborationproject.fragments.subsys.history.HistoryChartFragment
import com.viwave.collaborationproject.fragments.widgets.MarkerInfoLayout
import com.viwave.collaborationproject.utils.DateUtil
import java.lang.ref.WeakReference

class DiagramBloodPressure(fragment: WeakReference<HistoryChartFragment>): DiagramView(fragment), ITogglePressedListener {

    private val yAxis by lazy { YAxisBP(chart) }

    private val markerBPSysValue by lazy { view.findViewById<MarkerInfoLayout>(R.id.bp_sys_marker_value) }
    private val markerBPDiaValue by lazy { view.findViewById<MarkerInfoLayout>(R.id.bp_dia_marker_value) }

    //setting safe area
    private val sysSafeLow = 90f
    private val sysSafeHigh = 139f
    private val diaSafeLow = 60f
    private val diaSafeHigh = 89f

    override fun pressedToggle(toggleName: String) {

        (toggleName != togglePeriod).let{

            togglePeriod = toggleName

            chart.dragDecelerationFrictionCoef = 0f
            xAxis.setPreLocX(chart.lowestVisibleX, chart.highestVisibleX)

            (bioViewModel.getMarkerData().value)?.let {
                it as Bio.BloodPressure
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
        combineData.setData(JCandleData.getCandleData(updateBarWidth(), bioViewModel.getBPListData().value))
        chart.data = combineData
        chart.invalidate()

    }

    override fun setMarkerData(data: Bio?) {
        data as Bio.BloodPressure?
        when (data == null) {
            false -> {
                //date
                markerTime.text = DateUtil.getMeasurementTime(data.takenAt * 1000L)
                //marker data
                markerBPSysValue.setValue(data.sys.toString())
                markerBPDiaValue.setValue(data.dia.toString())
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
            true,
            if(diaSafeLow < yAxis.minY) yAxis.minY else diaSafeLow,
            if(diaSafeHigh > yAxis.maxY) yAxis.maxY else diaSafeHigh,
            yAxis.minY,
            yAxis.minY,
            yAxis.maxY,
            R.color.cornflower_blue
        )
        setSecondSafeArea(
            if(sysSafeLow < yAxis.minY) yAxis.minY else sysSafeLow,
            if(sysSafeHigh > yAxis.maxY) yAxis.maxY else sysSafeHigh)
    }

    private fun getYMinMax(start: Float, end: Float): MutableList<Float>{

        val inRangeList = mutableListOf<Bio.BloodPressure>()
        bioViewModel.getBPListData().value?.forEach {
            val index = calXIndex(it.takenAt)
            if(index in start..end){
                inRangeList.add(it)
            }

        }

        var bpHigh = yAxis.defaultMax
        var bpLow = yAxis.defaultMin
        if(inRangeList.isEmpty()) return mutableListOf(bpHigh, bpLow)
        for(num in 0 until inRangeList.size){
            val diaData = inRangeList[num].dia.toFloat()
            val sysData = inRangeList[num].sys.toFloat()
            bpHigh = Math.max(bpHigh, sysData)
            bpLow = Math.min(bpLow, diaData)
            inRangeList[num].pulse?.let {
                bpHigh = Math.max(bpHigh, it.toFloat())
                bpLow = Math.min(bpLow, it.toFloat())
            }
        }

        return mutableListOf(bpHigh, bpLow)
    }

    override fun emptyMarker() {
        bioViewModel.getMarkerData().value = null
        chart.highlightValue(null)
        initTopData()
    }

    private fun initTopData() {
        markerTime.text = "--"
        markerBPSysValue.setValue(null)
        markerBPDiaValue.setValue(null)
    }

    override fun updateTranslateData() {
        val newX = getScaledHighLow(chart.lowestVisibleX)
        xAxis.setBackXValue(newX[0])
        updateYAxis()

    }


}