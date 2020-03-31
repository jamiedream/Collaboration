/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/25/20 1:49 PM
 */

package com.viwave.collaborationproject.fragments.subsys.diagram

import com.github.mikephil.charting.data.CombinedData
import com.viwave.RossmaxConnect.Measurement.chart.JLineData
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.DAY
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.MONTH
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.WEEK
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.calXIndex
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.getScaledHighLow
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.switchPress
import com.viwave.RossmaxConnect.Measurement.yaxis.YAxisWeight
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.data.bios.Bio
import com.viwave.collaborationproject.fragments.ITogglePressedListener
import com.viwave.collaborationproject.fragments.subsys.caseList.CaseListFragment.Companion.bioViewModel
import com.viwave.collaborationproject.fragments.subsys.history.HistoryChartFragment
import com.viwave.collaborationproject.fragments.widgets.MarkerInfoLayout
import com.viwave.collaborationproject.utils.DataFormatUtil
import com.viwave.collaborationproject.utils.DateUtil
import java.lang.ref.WeakReference

class DiagramWeight(fragment: WeakReference<HistoryChartFragment>): DiagramView(fragment), ITogglePressedListener {

    private val yAxis by lazy { YAxisWeight(chart) }

    private val markerWeightValue by lazy { view.findViewById<MarkerInfoLayout>(R.id.weight_marker_value) }

//    //setting safe area
//    private val weightSafeLow = 80f
//    private val weightSafeHigh = 100f

    override fun pressedToggle(toggleName: String) {

        (toggleName != togglePeriod).let{

            togglePeriod = toggleName

            chart.dragDecelerationFrictionCoef = 0f
            xAxis.setPreLocX(chart.lowestVisibleX, chart.highestVisibleX)

            (bioViewModel.getMarkerData().value)?.let {
                it as Bio.Weight
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
        combineData.setData(JLineData.getWeightLineData(bioViewModel.getWeightListData().value))
        chart.data = combineData
        chart.invalidate()

    }

    override fun setMarkerData(data: Bio?) {
        data as Bio.Weight?
        when (data == null) {
            false -> {
                //date
                markerTime.text = DateUtil.getMeasurementTime(data.takenAt * 1000L)
                //marker data
                markerWeightValue.setValue(DataFormatUtil.formatString(data.weight))
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

//        setSafeAreaGrid(
//            !(weightSafeHigh < yAxis.minY || weightSafeLow > yAxis.maxY),
//            if(weightSafeLow < yAxis.minY) yAxis.minY else weightSafeLow,
//            if(weightSafeHigh > yAxis.maxY) yAxis.maxY else weightSafeHigh,
//            yAxis.minY,
//            yAxis.minY,
//            yAxis.maxY,
//            R.color.cornflower_blue
//        )
    }

    private fun getYMinMax(start: Float, end: Float): MutableList<Float>{

        val inRangeList = mutableListOf<Bio.Weight>()
        bioViewModel.getWeightListData().value?.forEach {
            val index = calXIndex(it.takenAt)
            if(index in start..end){
                inRangeList.add(it)
            }

        }

        var weightHigh = yAxis.defaultMax
        var weightLow = yAxis.defaultMin
        if(inRangeList.isEmpty()) return mutableListOf(weightHigh, weightLow)
        for(num in 0 until inRangeList.size){
            val dateTemp = inRangeList[num].weight
            weightHigh = Math.max(weightHigh, dateTemp)
            weightLow = Math.min(weightLow, dateTemp)
        }

        return mutableListOf(weightHigh, weightLow)
    }

    override fun emptyMarker() {
        bioViewModel.getMarkerData().value = null
        chart.highlightValue(null)
        initTopData()
    }

    private fun initTopData() {
        markerTime.text = "--"
        markerWeightValue.setValue(null)
    }

    override fun updateTranslateData() {
        val newX = getScaledHighLow(chart.lowestVisibleX)
        xAxis.setBackXValue(newX[0])
        updateYAxis()

    }


}