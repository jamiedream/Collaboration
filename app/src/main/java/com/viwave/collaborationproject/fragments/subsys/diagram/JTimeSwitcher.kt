/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/25/20 1:49 PM
 */

package com.viwave.RossmaxConnect.Measurement.chart

import com.viwave.collaborationproject.utils.DateUtil
import java.util.*

object JTimeSwitcher {

    const val HOUR = "Hour"
    const val DAY = "Day"
    const val WEEK = "Week"
    const val MONTH = "Month"
    var timeFormat = MONTH
    var preTimeFormat = ""
    var isHourlyOxygenDetail = false

    private const val hourCount = 12
    private const val dayCount = 24
    private const val weekCount = 7
    private const val monthCount = 31
    var labelCount = monthCount

    private const val hourBackCount = 1f / 24f
    private const val dayBackCount = 1f
    private const val weekBackCount = 7f
    private const val monthBackCount = 31f
    var backXCount = monthBackCount

    const val SEC_PER_DAY = 24 * 60 * 60
    const val SEC_PER_HOUR = 60 * 60
    const val SEC_PER_MIN = 60

    //time start from 93 days
    val startTime = Calendar.getInstance().timeInMillis.minus(3.times(31).times(24).times(60).times(60).times(1000L)).div(1000L).toInt()

    var differentTime = renewNowtime() - startTime
    var daysOfYear = differentTime.toFloat() / SEC_PER_DAY + 1
    var preDaysOfYear = daysOfYear

    fun calXIndex(time: Long): Float{
        return (time.toFloat() - startTime) / SEC_PER_DAY
    }

    fun switchPress(newTimeformat: String){

        preTimeFormat = timeFormat
        preDaysOfYear = daysOfYear

        timeFormat = newTimeformat
        when(newTimeformat){
            HOUR -> {
                labelCount = hourCount
                backXCount = hourBackCount
                differentTime = renewNowtime() - startTime
                daysOfYear = differentTime.toFloat() / SEC_PER_DAY
            }
            DAY -> {
                labelCount = dayCount
                backXCount = dayBackCount
                differentTime = renewNowtime() - startTime
                daysOfYear = (differentTime / SEC_PER_DAY + 1).toFloat()
            }
            WEEK -> {
                labelCount = weekCount
                backXCount = weekBackCount
                differentTime = renewNowtime() - startTime
                daysOfYear = (differentTime / SEC_PER_DAY + 1).toFloat()
            }
            MONTH -> {
                labelCount = monthCount
                backXCount = monthBackCount
                differentTime = renewNowtime() - startTime
                daysOfYear = (differentTime / SEC_PER_DAY + 1).toFloat()
            }
        }

//        LogUtil.logD("daysOfYear", daysOfYear)

    }

    private fun renewNowtime(): Int {
        val calendar = Calendar.getInstance()
        calendar.time = Date(System.currentTimeMillis())
        when (timeFormat) {
            HOUR -> {
                val newMin = Math.ceil(calendar.get(Calendar.MINUTE).toDouble() / 10) * 10
                calendar.set(Calendar.MINUTE, newMin.toInt())
            }
            WEEK -> calendar.set(Calendar.DAY_OF_WEEK, labelCount)
            MONTH -> calendar.set(Calendar.DAY_OF_MONTH, labelCount)
        }

        return (calendar.timeInMillis / 1000).toInt()

    }

    //time judgement
//    fun getWeekday(day: Int): Int{
//        val dayTime = startTime + day * SEC_PER_DAY
//        val c = Calendar.getInstance()
//        c.time = Date(dayTime * 1000L)
//        return c.get(Calendar.DAY_OF_WEEK)
//    }

    fun getFirstDayOfMonth(day: Int): Boolean{
        val dayTime = startTime + day * SEC_PER_DAY
        val c = Calendar.getInstance()
        c.time = Date(dayTime * 1000L)
        return c.get(Calendar.DAY_OF_MONTH) == 1
    }

    //bar width, default 0.1f (10%), max 0.45f, min 0f
    private const val monthlyBarWidthPercentage = .45f
    fun updateBarWidth(): Float{

        val percentageOfMonthly =  when(timeFormat){
            HOUR -> dayBackCount / (1 / 24f / 30f)
            DAY -> monthBackCount / dayBackCount
            WEEK -> 1.div(weekBackCount).times(monthBackCount)
            else -> 1f
        }

        return monthlyBarWidthPercentage / percentageOfMonthly
    }

    fun getCalendar(midOfView: Float): Calendar{

        val dayTime =
            when (timeFormat) {
                HOUR -> startTime + Math.round(midOfView * SEC_PER_DAY)
                DAY -> startTime + Math.round((midOfView) * 24) * SEC_PER_HOUR
                else -> startTime + Math.round(midOfView) * SEC_PER_DAY
            }

        val calendar = Calendar.getInstance()
        calendar.time = Date(dayTime * 1000L)
        return calendar
    }

    fun getTimeDateFormat(low: Float, high: Float): String{

        return DateUtil.getChartDateRange(
            if (low < (daysOfYear - backXCount))
                getCalendar(low)
            else
                getCalendar(daysOfYear - backXCount)
            ,
            if (high >= daysOfYear)
                getCalendar(daysOfYear - 1f)
            else if (high < backXCount)
                getCalendar(backXCount)
            else
                if(timeFormat == DAY) getCalendar(high) else getCalendar(high - 1)
            ,timeFormat)

    }

    fun getScaledHighLow(low: Float): MutableList<Float>{

        var adjustLowestPos = 0f
        var adjustHighestPos = 0f
        when(timeFormat){
            HOUR -> {
                val eachScale = 1 / 24f / 12f
                adjustLowestPos = Math.round(low / eachScale) * eachScale
                adjustHighestPos = adjustLowestPos + 1 / 24f
            }
            DAY -> {
                val eachScale = 1 / 24f
                adjustLowestPos = Math.round(low / eachScale) * eachScale
                adjustHighestPos = adjustLowestPos + 1f
            }
            WEEK -> {
                val eachScale = 1f
                adjustLowestPos = Math.round(low / eachScale) * eachScale
                adjustHighestPos = adjustLowestPos + 7f
            }
            MONTH -> {
                val eachScale = 1f
                adjustLowestPos = Math.round(low / eachScale) * eachScale
                adjustHighestPos = adjustLowestPos + 31f
            }
        }

        return mutableListOf(adjustLowestPos, adjustHighestPos)
    }


}