/*
 * Copyright (c) viWave 2019.
 * Create by J.Y Yen 20/ 8/ 2019.
 * Last modified 3/21/20 1:30 PM
 */

package com.viwave.RossmaxConnect.Measurement.chart

import android.content.Context
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.calXIndex
import com.viwave.collaborationproject.data.bios.BioLiveData
import com.viwave.collaborationproject.fragments.subsys.caseList.CaseListFragment.Companion.bioViewModel

class JMarkdown(context: Context?, layoutResource: Int) : MarkerView(context, layoutResource) {

    override fun getOffsetForDrawingAtPoint(posX: Float, posY: Float): MPPointF {
        val offset = offset
        val width = width.toFloat()
        val height = height.toFloat()

        offset.y = -height * 1.5f
        offset.x = 0f
        if (posX > width / 2) {
            offset.x = -(width / 2)
        }

        return offset
    }

    override fun refreshContent(e: Entry?, highlight: Highlight?) {

        e?.let {
            getBioData(e.x, e.y)
        }
//        LogUtil.logD("refreshContent", "${e?.x} ${e?.y}")
        super.refreshContent(e, highlight)

    }

    private fun getBioData(x: Float, y: Float) {

        bioViewModel.getSelectedType().value?.let {
            when(it){
                BioLiveData.Companion.BioType.Temperature -> {
                    bioViewModel.getTempListData().value?.forEach {
                        if(calXIndex(it.takenAt) == x && it.temperature == y){
                            bioViewModel.getMarkerData().value = it
                            return
                        }
                    }
                }
                BioLiveData.Companion.BioType.Pulse -> {
                    bioViewModel.getPulseListData().value?.forEach {
                        if(calXIndex(it.takenAt) == x && it.pulse.toFloat() == y){
                            bioViewModel.getMarkerData().value = it
                            return
                        }
                    }
                }
                BioLiveData.Companion.BioType.Weight -> {
                    bioViewModel.getWeightListData().value?.forEach {
                        if(calXIndex(it.takenAt) == x && it.weight == y){
                            bioViewModel.getMarkerData().value = it
                            return
                        }
                    }
                }
                BioLiveData.Companion.BioType.BloodGlucose -> {
                    bioViewModel.getGlucoseListData().value?.forEach {
                        if(calXIndex(it.takenAt) == x && it.glucose.toFloat() == y){
                            bioViewModel.getMarkerData().value = it
                            return
                        }
                    }
                }
                BioLiveData.Companion.BioType.BloodPressure -> {
                    bioViewModel.getBPListData().value?.forEach {
                        if(calXIndex(it.takenAt) == x && it.sys.toFloat() == y){
                            bioViewModel.getMarkerData().value = it
                            return
                        }
                    }
                }
                BioLiveData.Companion.BioType.Oxygen -> {
                    bioViewModel.getOxygenListData().value?.forEach {
                        val xIndex =
                                when(JTimeSwitcher.timeFormat) {
                                    JTimeSwitcher.DAY -> (.5f / 24)
                                    JTimeSwitcher.HOUR -> (.5f / 24 / 12)
                                    else -> .5f
                                }
                        if(calXIndex(it.takenAt).plus(xIndex) == x && it.spo2Highest.toFloat().plus(.3f) == y){
                            bioViewModel.getMarkerData().value = it
                            return
                        }
                    }
                }
                else -> {}
            }
        }
    }

}