/*
 * Copyright (c) viWave 2019.
 * Create by J.Y Yen 20/ 8/ 2019.
 * Last modified 8/1/19 6:09 PM
 */

package com.viwave.chart.UI.health.Chart

import com.github.mikephil.charting.formatter.ValueFormatter
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.DAY
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.HOUR
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.MONTH
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.SEC_PER_DAY
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.SEC_PER_HOUR
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.SEC_PER_MIN
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.WEEK
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.isHourlyOxygenDetail
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.startTime
import com.viwave.RossmaxConnect.Measurement.chart.JTimeSwitcher.timeFormat
import java.text.SimpleDateFormat
import java.util.*

class JCustomTimeAxisValueFormatter(private val day: Int = 0): ValueFormatter(){

    private val TAG = this::class.java.simpleName

    private val hourlyTime = SimpleDateFormat("H:mm", Locale.getDefault())
    private val startHourlyTime = SimpleDateFormat("H:mm\nM/dd", Locale.getDefault())

    private val weeklyTime = SimpleDateFormat("EEE\nM/dd", Locale.getDefault())

    private val monthlyTime = SimpleDateFormat("M/dd", Locale.getDefault()) //sunday
    private val firstDayOfMonthlyTime = SimpleDateFormat("\nMMM", Locale.getDefault()) //first day of month

    override fun getFormattedValue(value: Float): String {


        val day: Int = when(timeFormat){
            HOUR -> startTime + Math.round((value) * SEC_PER_DAY)
            DAY -> {
                when(isHourlyOxygenDetail){
                    true -> day + Math.round((value) * SEC_PER_MIN)
                    false -> startTime + Math.round((value) * 24) * SEC_PER_HOUR
                }
            }
            WEEK, MONTH -> (startTime + Math.round(value) * SEC_PER_DAY)
            else -> 0
        }

        return getDateFormat(Date(day * 1000L))
    }

    private fun getDateFormat(date: Date): String{


        return when(timeFormat){
            HOUR -> {
                val calendar = Calendar.getInstance()
                calendar.time = date
                val dateHour = calendar.get(Calendar.HOUR_OF_DAY)
                val minInHour = calendar.get(Calendar.MINUTE)
//                Log.d(TAG, "dateHour: $dateHour")
                when(dateHour == 0 && minInHour == 0){
                    true -> startHourlyTime.format(date)
                    false -> {
                        when(minInHour % 10 == 0){
                            true -> {
//                                LogUtil.logD(TAG, dateHour)
                                hourlyTime.format(date)
                            }
                            false -> ""
                        }
                    }
                }
            }
            DAY -> {
                when(isHourlyOxygenDetail){
                    true -> hourlyTime.format(date)
                    false -> {
                        val calendar = Calendar.getInstance()
                        calendar.time = date
                        val dateHour = calendar.get(Calendar.HOUR_OF_DAY)
//                Log.d(TAG, "dateHour: $dateHour")
                        when(dateHour == 0){
                            true -> startHourlyTime.format(date)
                            false -> {
                                when(dateHour % 3 == 0){
                                    true -> {
//                                LogUtil.logD(TAG, dateHour)
                                        hourlyTime.format(date)
                                    }
                                    false -> ""
                                }
                            }
                        }
                    }
                }
            }
            WEEK -> weeklyTime.format(date) //index: 1, 2, 3, 4, 5, 6, 7
            MONTH -> {
                val calendar = Calendar.getInstance()
                calendar.time = date
                val day = calendar.get(Calendar.DAY_OF_MONTH)
                val dateOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
                when(day){
                    1 -> firstDayOfMonthlyTime.format(date)
                    else -> {
                        when(dateOfWeek){
                            1 -> monthlyTime.format(date)
                            else -> ""
                        }
                    }
                }
            }
            else -> monthlyTime.format(date)
        }
    }

}