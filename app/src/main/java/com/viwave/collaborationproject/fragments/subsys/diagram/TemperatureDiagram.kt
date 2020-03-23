package com.viwave.collaborationproject.fragments.subsys.diagram

import com.github.mikephil.charting.data.CombinedData
import com.viwave.RossmaxConnect.Measurement.chart.JLineData
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.DAY
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.MONTH
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.WEEK
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.calXIndex
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.getScaledHighLow
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.switchPress
import com.viwave.RossmaxConnect.Measurement.yaxis.YAxisTemperature
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.data.bios.Bio
import com.viwave.collaborationproject.fragments.ITogglePressedListener
import com.viwave.collaborationproject.fragments.subsys.caseList.CaseListFragment.Companion.bioViewModel
import com.viwave.collaborationproject.fragments.subsys.history.HistoryChartFragment
import com.viwave.collaborationproject.fragments.widgets.MarkerInfoLayout
import com.viwave.collaborationproject.utils.DataFormatUtil
import com.viwave.collaborationproject.utils.DateUtil
import java.lang.ref.WeakReference

class TemperatureDiagram(fragment: WeakReference<HistoryChartFragment>): DiagramView(fragment), ITogglePressedListener {

    private val yAxis by lazy { YAxisTemperature(chart) }

    private val markerTempValue by lazy { view.findViewById<MarkerInfoLayout>(R.id.temp_marker_value) }

    //setting safe area
    private val tempSafeLow = 36f
    private val tempSafeHigh = 37f

    override fun pressedToggle(toggleName: String) {

        (toggleName != togglePeriod).let{

            togglePeriod = toggleName

            chart.dragDecelerationFrictionCoef = 0f
            xAxis.setPreLocX(chart.lowestVisibleX, chart.highestVisibleX)

            (bioViewModel.getMarkerData().value)?.let {
                it as Bio.Temperature
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
        combineData.setData(JLineData.getTempLineData(bioViewModel.getTempListData().value))
        chart.data = combineData
        chart.invalidate()

    }

    override fun setMarkerData(data: Bio?) {
        data as Bio.Temperature?
        when (data == null) {
            false -> {
                //date
                markerTime.text = DateUtil.getMeasurementTime(data.takenAt * 1000L)
                //marker data
                markerTempValue.setValue(DataFormatUtil.formatString(data.temperature))
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
            !(tempSafeHigh < yAxis.minY || tempSafeLow > yAxis.maxY),
            if(tempSafeLow < yAxis.minY) yAxis.minY else tempSafeLow,
            if(tempSafeHigh > yAxis.maxY) yAxis.maxY else tempSafeHigh,
            yAxis.minY,
            yAxis.minY,
            yAxis.maxY,
            R.color.cornflower_blue
        )
    }

    private fun getYMinMax(start: Float, end: Float): MutableList<Float>{

        val inRangeList = mutableListOf<Bio.Temperature>()
        bioViewModel.getTempListData().value?.forEach {
            val index = calXIndex(it.takenAt)
            if(index in start..end){
                inRangeList.add(it)
            }

        }

        var tempHigh = yAxis.defaultMax
        var tempLow = yAxis.defaultMin
        if(inRangeList.isEmpty()) return mutableListOf(tempHigh, tempLow)
        for(num in 0 until inRangeList.size){
            val dateTemp = inRangeList[num].temperature
            tempHigh = Math.max(tempHigh, dateTemp)
            tempLow = Math.min(tempLow, dateTemp)
        }

        return mutableListOf(tempHigh, tempLow)
    }

    override fun emptyMarker() {
        bioViewModel.getMarkerData().value = null
        chart.highlightValue(null)
        initTopData()
    }

    private fun initTopData() {
        markerTime.text = "--"
        markerTempValue.setValue(null)
    }

    override fun updateTranslateData() {
        val newX = getScaledHighLow(chart.lowestVisibleX)
        xAxis.setBackXValue(newX[0])
        updateYAxis()

    }


}