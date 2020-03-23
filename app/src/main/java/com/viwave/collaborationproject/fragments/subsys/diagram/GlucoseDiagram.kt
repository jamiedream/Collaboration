/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 23/ 3/ 2020.
 * Last modified 3/23/20 4:39 PM
 */

package com.viwave.collaborationproject.fragments.subsys.diagram

import android.widget.TextView
import com.github.mikephil.charting.data.CombinedData
import com.viwave.RossmaxConnect.Measurement.chart.JLineData
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.DAY
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.MONTH
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.WEEK
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.calXIndex
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.getScaledHighLow
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.switchPress
import com.viwave.RossmaxConnect.Measurement.yaxis.YAxisGlucose
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.data.bios.Bio
import com.viwave.collaborationproject.fragments.ITogglePressedListener
import com.viwave.collaborationproject.fragments.subsys.caseList.CaseListFragment
import com.viwave.collaborationproject.fragments.subsys.caseList.CaseListFragment.Companion.bioViewModel
import com.viwave.collaborationproject.fragments.subsys.history.HistoryChartFragment
import com.viwave.collaborationproject.fragments.widgets.MarkerInfoLayout
import com.viwave.collaborationproject.utils.DateUtil
import java.lang.ref.WeakReference

class GlucoseDiagram(fragment: WeakReference<HistoryChartFragment>): DiagramView(fragment), ITogglePressedListener {

    private val yAxis by lazy { YAxisGlucose(chart) }

    private val markerGlucoseValue by lazy { view.findViewById<MarkerInfoLayout>(R.id.glucose_marker_value) }
    private val markerGlucoseMealValue by lazy { view.findViewById<TextView>(R.id.glucose_meal) }

    override fun pressedToggle(toggleName: String) {

        (toggleName != togglePeriod).let{

            togglePeriod = toggleName

            chart.dragDecelerationFrictionCoef = 0f
            xAxis.setPreLocX(chart.lowestVisibleX, chart.highestVisibleX)

            (bioViewModel.getMarkerData().value)?.let {
                it as Bio.BloodGlucose
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
        combineData.setData(JLineData.getGlucoseLineData(bioViewModel.getGlucoseListData().value))
        chart.data = combineData
        chart.invalidate()

    }

    override fun setMarkerData(data: Bio?) {
        data as Bio.BloodGlucose?
        when (data == null) {
            false -> {
                //date
                markerTime.text = DateUtil.getMeasurementTime(data.takenAt * 1000L)
                //marker data
                markerGlucoseValue.setValue(data.glucose.toString())
                markerGlucoseMealValue.text = data.meal
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
        yAxis.updateYAxis()
    }

    private fun getYMinMax(start: Float, end: Float): MutableList<Float>{

        val inRangeList = mutableListOf<Bio.BloodGlucose>()
        bioViewModel.getGlucoseListData().value?.forEach {
            val index = calXIndex(it.takenAt)
            if(index in start..end){
                inRangeList.add(it)
            }

        }

        var glucoseHigh = 0f
        val glucoseLow = 0f
        if(inRangeList.isEmpty()) return mutableListOf(glucoseHigh, glucoseLow)
        for(num in 0 until inRangeList.size){
            glucoseHigh = Math.max(glucoseHigh, inRangeList[num].glucose.toFloat())
        }

        return mutableListOf(glucoseHigh, glucoseLow)
    }

    override fun emptyMarker() {
        CaseListFragment.bioViewModel.getMarkerData().value = null
        chart.highlightValue(null)
        initTopData()
    }

    private fun initTopData() {
        markerTime.text = "--"
        markerGlucoseValue.setValue(null)
        markerGlucoseMealValue.text = "--"
    }

    override fun updateTranslateData() {
        val newX = getScaledHighLow(chart.lowestVisibleX)
        xAxis.setBackXValue(newX[0])
        updateYAxis()

    }


}