/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/25/20 1:49 PM
 */

package com.viwave.RossmaxConnect.Measurement.chart

import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.CombinedChart
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.DAY
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.HOUR
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.MONTH
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.WEEK
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.backXCount
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.daysOfYear
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.getCalendar
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.getFirstDayOfMonth
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.getScaledHighLow
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.labelCount
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.preTimeFormat
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.timeFormat
import com.viwave.chart.UI.health.Chart.JCustomTimeAxisValueFormatter
import com.viwave.collaborationproject.R
import java.util.*
import kotlin.math.roundToInt

class JCustomXAxis(private val chart: CombinedChart){

    private val TAG = this::class.java.simpleName

    private val xAxis by lazy { chart.xAxis }

    private var backXValue = daysOfYear - labelCount

    private val xAxisValueFormatter = JCustomTimeAxisValueFormatter()

    private val dashLength = 8f
    private val enhanceWidth = 1f

    init {

        xAxis.textColor = ContextCompat.getColor(chart.context, R.color.nandor)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawAxisLine(false)
        xAxis.setCenterAxisLabels(true)
        xAxis.axisMinimum = 0f

        setXMoveBackward()
        setXValueFormatter()

        xAxis.setDrawLimitLinesBehindData(true)

        xAxis.gridColor = ContextCompat.getColor(chart.context, R.color.humming_bird)
        xAxis.enableGridDashedLine(dashLength, dashLength, 0f)

    }

    fun updateXAxis(){
        xAxis.removeAllLimitLines()
        setXParam()
        setXAxisLimitline()
        chart.notifyDataSetChanged()
    }

    private var preLowestVisibleX = 0f
    private var preHighestVisibleX = 0f
    fun setPreLocX(lowest: Float, highest: Float){
        preLowestVisibleX = lowest
        preHighestVisibleX = highest
    }

    private var markerX = 0f
    fun setMarkerDataX(x: Float?){
        if(x != null)
            markerX = x
    }

    fun setBackXValue(backX: Float){
//        LogUtil.logD(TAG, backX)
        val newX = getScaledHighLow(backX)
        when {
            newX[0] > (daysOfYear - backXCount) -> this.backXValue = daysOfYear - backXCount
            newX[0] < 0f -> this.backXValue = 0f
            else -> this.backXValue = newX[0]
        }

        setXMoveBackwardAnimate()

    }

    fun getBackXValue(): Float{
        return backXValue
    }

    fun arrowBackward(){

        if(preHighestVisibleX > 0f) {

            //find mid of view chart
            val midOfView = (preLowestVisibleX + preHighestVisibleX).div(2f).roundToInt()

            when(timeFormat){
                MONTH -> {
                    val c = getCalendar(midOfView.toFloat())
//                    val c = getCalendar(midOfView.toFloat())
                    val midYear = c.get(Calendar.YEAR)
                    val midMonth = c.get(Calendar.MONTH)
                    //mid of view month
                    if(midYear == 2019&& midMonth == 1){
                        c.set(midYear, midMonth, 0)
                        //2019/01/01
                        this.backXValue = 0f
                    }else if(this.backXValue == 0f){

                    }else{
                        c.set(midYear, midMonth - 1, 0)
                    }
                    updateMonthBackXValue(c)
                }
                WEEK -> {
                    var minWeekDay = midOfView - 7
                    //previous week day 1

                    if(minWeekDay <= 7) {
                        //2019/01/01
                        this.backXValue = 0f
                    }else {
                        for (num in 0 until 7) {
//                            LogUtil.logD(TAG, "month to week loop: ${getCalendar(minWeekDay.toFloat()).get(Calendar.DAY_OF_WEEK)}")
                            if (getCalendar(minWeekDay.toFloat()).get(Calendar.DAY_OF_WEEK) == 1) {
                                this.backXValue = minWeekDay.toFloat()
//                            LogUtil.logD(TAG, "new mid x $minWeekDay")
                                break
                            } else minWeekDay -= 1
                        }
                    }
                }
                DAY -> {
                    val pos = backXValue.roundToInt()
                    this.backXValue = if((pos - 1) >= 0) (pos - 1f) else 0f
                }
                HOUR -> {
                    this.backXValue = if((backXValue - 1 / 24f) >= 0f) (backXValue - 1 / 24f) else 0f
                }
            }
        }

        setXMoveBackwardAnimate()
        preHighestVisibleX = 0f
        preLowestVisibleX = 0f
    }

    fun arrowForward(){

        if(preHighestVisibleX > 0f) {

            //find mid of view chart
            val midOfView = (preLowestVisibleX + preHighestVisibleX).div(2f).roundToInt()

            when(timeFormat){
                MONTH -> {
                    val c = getCalendar(midOfView.toFloat())
                    val midYear = c.get(Calendar.YEAR)
                    val midMonth = c.get(Calendar.MONTH)
                    val now = Calendar.getInstance()
                    if(midYear == now.get(Calendar.YEAR) && midMonth == now.get(Calendar.MONTH)){
                        c.set(midYear, midMonth, 0)
                    }else{
                        c.set(midYear, midMonth + 1, 0)
                    }
                    updateMonthBackXValue(c)
                }
                WEEK -> {
                    var minWeekDay = midOfView + 7
                    //previous week day 1
                    for(num in 0 until 7){
//                        LogUtil.logD(TAG, "month to week loop: ${getCalendar(minWeekDay.toFloat()).get(Calendar.DAY_OF_WEEK)}")
                        if(getCalendar(minWeekDay.toFloat()).get(Calendar.DAY_OF_WEEK) == 1){
                            this.backXValue = minWeekDay.toFloat()
//                            LogUtil.logD(TAG, "new mid x $minWeekDay")
                            break
                        }else minWeekDay -= 1
                    }
                }
                DAY -> {
                    this.backXValue = backXValue.roundToInt() + 1f
                }
                HOUR -> {
                    this.backXValue = if((backXValue + 1 / 24f) <= daysOfYear) (backXValue + 1 / 24f) else (daysOfYear - 1 / 24f)
                }
            }
        }

        setXMoveBackwardAnimate()
        preHighestVisibleX = 0f
        preLowestVisibleX = 0f
    }

    private fun setXParam(){

        xAxis.granularity =
            when (timeFormat) {
                HOUR -> 1f.div(24f).div(12f)
                DAY -> 1f.div(24f)
                else -> 1f
            }
        xAxis.labelCount = labelCount

        xAxis.axisMaximum = daysOfYear

        chart.setVisibleXRange(backXCount, backXCount)

        switchTimeRange()
        setXMoveBackward()

    }

    private fun setXValueFormatter(){
        xAxis.valueFormatter = xAxisValueFormatter
    }

    private fun setXMoveBackward(){
        chart.moveViewToX(backXValue)
    }

    private fun setXMoveBackwardAnimate(){
        chart.moveViewToAnimated(backXValue, 0f, YAxis.AxisDependency.LEFT, 150L)
    }

    private fun setXAxisLimitline(){

        xAxis.removeAllLimitLines()

        when(timeFormat){

            HOUR -> {

                val hour = 1 / 24f
                val tenMin = hour / 6f
                val midPos =
                    if(backXValue > daysOfYear){
                        Math.round(daysOfYear - 1f)
                    }else Math.round(backXValue + backXCount / 2f)
//                LogUtil.logD(TAG, midPos)
                for(day in (midPos - 10) until (midPos + 10)){
                    for(lines in 0 until (24 * 6)){
                        when(lines % 6 == 0){
                            true -> {
                                val dayLine = generateLimitLine(day + lines * tenMin , "hour", R.color.humming_bird, enhanceWidth, null)
                                xAxis.addLimitLine(dayLine)
                            }
                            false -> {
                                val tenMinLine = generateLimitLine(day + lines * tenMin, "10 mins", R.color.humming_bird, enhanceWidth, arrayOf(dashLength, dashLength))
                                xAxis.addLimitLine(tenMinLine)
                            }
                        }
                    }
                }
            }

            else -> {
                for(day in 0..Math.ceil(daysOfYear.toDouble()).toInt()){

                    when(timeFormat){

                        DAY -> {
                            val div = 1/ 24f
                            for(hour in 0..23){
                                when{
                                    hour == 0 -> {
                                        val dayLine = generateLimitLine(day.toFloat(), "day", R.color.humming_bird, enhanceWidth, null)
                                        xAxis.addLimitLine(dayLine)
                                    }
                                    hour != 0 && hour % 3 == 0 -> {
                                        val threeHourLine = generateLimitLine(day + hour * div, "3h", R.color.humming_bird, enhanceWidth, arrayOf(dashLength, dashLength))
                                        xAxis.addLimitLine(threeHourLine)
                                    }
                                }
                            }
                        }

                        WEEK -> {
                            when{
                                getCalendar(day.toFloat()).get(Calendar.DAY_OF_WEEK) == 1 -> {
                                    val dayLine = generateLimitLine(day.toFloat(), "day", R.color.humming_bird, enhanceWidth, null)
                                    xAxis.addLimitLine(dayLine)
                                }
                            }
                        }

                        MONTH -> {
                            when{
                                getFirstDayOfMonth(day) -> {
                                    val firstDayLine = generateLimitLine(day.toFloat(), "first day of M", R.color.humming_bird, enhanceWidth, null)
                                    xAxis.addLimitLine(firstDayLine)
                                }
                                getCalendar(day.toFloat()).get(Calendar.DAY_OF_WEEK) == 1 -> {
                                    val sundayLine = generateLimitLine(day.toFloat(), "Sunday", R.color.humming_bird, enhanceWidth, arrayOf(dashLength, dashLength))
                                    xAxis.addLimitLine(sundayLine)
                                }
                            }
                        }

                    }
                }
            }
        }

    }

    private fun generateLimitLine(
        limit: Float,
        label: String,
        lineColor: Int,
        width: Float,
        dashLine: Array<Float>?): LimitLine
    {
        val limitLine = LimitLine(limit)
//        limitLine.label = label
        limitLine.lineColor = ContextCompat.getColor(chart.context, lineColor)
        limitLine.lineWidth = width
        if(dashLine != null)
            limitLine.enableDashedLine(dashLine[0], dashLine[1], 0f)
        return limitLine
    }

    private fun switchTimeRange(){
        
        //check from time format, to time format
        if(preHighestVisibleX > 0f){

            //find mid of view chart

            var midOfView = if(markerX > 0f) (markerX + .5f).roundToInt() else (preLowestVisibleX.roundToInt() + preHighestVisibleX.roundToInt()).div(2f).roundToInt()
//            LogUtil.logD(TAG, "marker x $markerX")
//            LogUtil.logD(TAG, "lowest x $preLowestVisibleX")
//            LogUtil.logD(TAG, "highest x $preHighestVisibleX")

            //place new offset
            when(preTimeFormat){
                MONTH -> {
                    when(timeFormat) {
                        WEEK -> {

                            //1. month to week
                            for(num in 0 until 7){
//                                LogUtil.logD(TAG, "month to week loop: ${getWeekday(midOfView)}")
                                if(getCalendar(midOfView.toFloat()).get(Calendar.DAY_OF_WEEK) == 1){
                                    this.backXValue = midOfView.toFloat()
//                                    LogUtil.logD(TAG, "new mid x $midOfView")
                                    break
                                }else midOfView -= 1
                            }
                        }
                        DAY -> {
                            //2. month to day
                            this.backXValue = if(markerX > 0f) (midOfView - 1f) else midOfView.toFloat()
                        }
                        HOUR -> {
                            //3. month to hour
                            this.backXValue = if(markerX > 0f)  markerX - 1 / 48f else midOfView - .5f
                        }
                    }
                }
                WEEK -> {
                    when(timeFormat){
                        HOUR -> {
                            //6. week to hour
                            this.backXValue = if(markerX > 0f)  markerX - 1 / 48f else midOfView - 1f + .5f
                        }
                        DAY -> {
                            //5. week to day
                            this.backXValue = if(markerX > 0f) (midOfView - 1f) else midOfView - 1f
                        }
                        MONTH -> {
                            //4. week to month
                            val c = getCalendar(midOfView.toFloat())
                            //mid of view month
//                            LogUtil.logD(TAG, c.get(Calendar.YEAR))
//                            LogUtil.logD(TAG, c.get(Calendar.MONTH))
//                            LogUtil.logD(TAG, c.get(Calendar.DAY_OF_YEAR))
                            if(c.get(Calendar.YEAR) == 2019 && c.get(Calendar.MONTH) == 0){
                                //2019/01/01
                                this.backXValue = 0f
                            }else {
                                c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 0)
                                updateMonthBackXValue(c)
                            }
                        }
                    }

                }
                DAY -> {
                    var nowDay = preLowestVisibleX.roundToInt() //which day shows most
//                    LogUtil.logD(TAG, nowDay)
                    when(timeFormat){
                        HOUR -> {
                            //9. day to hour
                            this.backXValue =
                                if(markerX > 0f) markerX
                                else {
                                    //2019/01/01
                                    if(nowDay > 1f){
                                        nowDay + .5f
                                    }else{
                                        .5f
                                    }
                                }
                        }
                        WEEK -> {
                            //8. day to week
                            for(num in 0 until 7){
//                                LogUtil.logD(TAG, "month to week loop: ${getWeekday(nowDay)}")
                                if(getCalendar(nowDay.toFloat()).get(Calendar.DAY_OF_WEEK) == 1){
                                    if(getCalendar(nowDay.toFloat()).get(Calendar.YEAR) == 2018){
                                        //before 2019/01/01
                                        this.backXValue = 0f
                                    }else {
                                        this.backXValue = nowDay.toFloat()
//                                    LogUtil.logD(TAG, "new mid x $nowDay")
                                    }
                                    break
                                }else nowDay -= 1
                            }
                        }
                        MONTH -> {
                            //7. day to month
                            val c = getCalendar(nowDay.toFloat())
                            if(c.get(Calendar.MONTH) == 0 && c.get(Calendar.YEAR) == 2019){
                                //before 2019/01/01
                                this.backXValue = 0f
                            }else {
                                c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 0)
                                updateMonthBackXValue(c)
                            }
//                            LogUtil.logD(TAG, backXValue)
                        }
                    }
                }
                HOUR -> {
                    var nowDay = preLowestVisibleX.roundToInt() //which day shows most
                    when(timeFormat){
                        DAY -> {
                            //12. hour to day
                            this.backXValue = if(markerX > 0f) (midOfView - 1f) else Math.floor((preHighestVisibleX - 1 / 48.0)).toFloat()
                        }
                        WEEK -> {
                            //11. hour to week
                            for(num in 0 until 7){
//                                LogUtil.logD(TAG, "month to week loop: ${getWeekday(nowDay)}")
                                if(getCalendar(nowDay.toFloat()).get(Calendar.DAY_OF_WEEK) == 1){
                                    if(getCalendar(nowDay.toFloat()).get(Calendar.YEAR) == 2018){
                                        //before 2019/01/01
                                        this.backXValue = 0f
                                    }else {
                                        this.backXValue = nowDay.toFloat()
//                                    LogUtil.logD(TAG, "new mid x $nowDay")
                                    }
                                    break
                                }else nowDay -= 1
                            }
                        }
                        MONTH -> {
                            //10. hour to month
                            val c = getCalendar(nowDay.toFloat())
                            if(c.get(Calendar.MONTH) == 0 && c.get(Calendar.YEAR) == 2019){
                                //before 2019/01/01
                                this.backXValue = 0f
                            }else {
                                c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 0)
                                updateMonthBackXValue(c)
                            }
                        }
                    }
                }

            }
        }

        markerX = 0f
        preHighestVisibleX = 0f
        preLowestVisibleX = 0f
    }

    private fun updateMonthBackXValue(midC: Calendar){
        val timeDiff = midC.timeInMillis.minus(JTimeSwitcher.startTime.times(1000L))
        val days = timeDiff.div(JTimeSwitcher.SEC_PER_DAY.times(1000L)).toFloat()
        this.backXValue = days + 1
    }

}