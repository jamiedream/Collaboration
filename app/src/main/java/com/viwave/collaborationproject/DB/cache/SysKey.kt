/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/25/20 5:01 PM
 */

package com.viwave.collaborationproject.DB.cache

import com.viwave.collaborationproject.data.general.SubSys

object SysKey {
    const val DAILY_CARE_CODE = "S03"
    const val DAILY_CARE_NAME = "日照中心"
    const val DAILY_NURSING_CODE = "S05"
    const val DAILY_NURSING_NAME = "居護"
    const val DAILY_STATION_CODE = "S26"
    const val DAILY_STATION_NAME = "活力站"
    const val DAILY_HOME_CARE_CODE = "S01"
    const val DAILY_HOME_CARE_NAME = "居服"

    val DailyCare = SubSys(DAILY_CARE_CODE, DAILY_CARE_NAME)
    val DailyNursing = SubSys(DAILY_NURSING_CODE, DAILY_NURSING_NAME)
    val Station = SubSys(DAILY_STATION_CODE, DAILY_STATION_NAME)
    val HomeCare = SubSys(DAILY_HOME_CARE_CODE, DAILY_HOME_CARE_NAME)
}