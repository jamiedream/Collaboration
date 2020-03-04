package com.viwave.collaborationproject.DB.cache

import com.viwave.collaborationproject.data.general.SubSys

object SysKey {
    const val DAILY_CARE_CODE = "A"
    const val DAILY_CARE_NAME = "居服"
    const val DAILY_NURSING_CODE = "B"
    const val DAILY_NURSING_NAME = "居護"
    const val DAILY_STATION_CODE = "C"
    const val DAILY_STATION_NAME = "活力站"
    const val DAILY_HOME_CARE_CODE = "D"
    const val DAILY_HOME_CARE_NAME = "日照中心"

    val DailyCare = SubSys(DAILY_CARE_CODE, DAILY_CARE_NAME)
    val DailyNursing = SubSys(DAILY_NURSING_CODE, DAILY_NURSING_NAME)
    val Station = SubSys(DAILY_STATION_CODE, DAILY_STATION_NAME)
    val HomeCare = SubSys(DAILY_HOME_CARE_CODE, DAILY_HOME_CARE_NAME)
}