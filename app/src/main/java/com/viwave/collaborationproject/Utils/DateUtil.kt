/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/25/20 1:49 PM
 */

package com.viwave.collaborationproject.utils

import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher
import java.text.SimpleDateFormat
import java.util.*

object DateUtil{

    private val TAG = this::class.java.simpleName

    fun getNowTimestamp(): Long{
        return System.currentTimeMillis()
    }

    var measureDate: Date = Date()
    private val measureFormat = SimpleDateFormat("MMM dd, yyyy HH:mm ", Locale.getDefault())
    fun getMeasurementTime(time: Long): String{
        val date = Date(time) // *1000 is to convert seconds to milliseconds
        measureDate = date
        return measureFormat.format(date)
    }

    fun getChartDateRange(startCalender: Calendar, endCalendar: Calendar, format: String): String {

        val startDate = Date(startCalender.timeInMillis)
        val endDate = Date(endCalendar.timeInMillis)

        when (format) {
            JTimeSwitcher.HOUR -> {
                return if (startCalender.get(Calendar.HOUR_OF_DAY) == 0 && startCalender.get(
                        Calendar.MINUTE) == 0) {
                    val startFormat = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())
                    startFormat.format(startDate)
                } else {
                    val startDateFormat = SimpleDateFormat("MMM d HH:mm", Locale.getDefault())
                    startDateFormat.format(startDate)
                }
            }
            JTimeSwitcher.DAY -> {
                return if (startCalender.get(Calendar.HOUR_OF_DAY) == 0) {
                    val startFormat = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())
                    startFormat.format(startDate)
                } else {
                    val startDateFormat = SimpleDateFormat("MMM d HH:mm", Locale.getDefault())
                    val endDateFormat = SimpleDateFormat("MMM d HH:mm", Locale.getDefault())
                    startDateFormat.format(startDate) + " - " + endDateFormat.format(endDate)
                }
            }
            else -> {
                val startDateFormat = SimpleDateFormat("MMM d", Locale.getDefault())
                val endDateFormat = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())
//        LogUtil.logD(TAG, startDateFormat.format(startDate))
//        LogUtil.logD(TAG, endDateFormat.format(endDate))
                return startDateFormat.format(startDate) + " - " + endDateFormat.format(endDate)
            }

        }
    }

}