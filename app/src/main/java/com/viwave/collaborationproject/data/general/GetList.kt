/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 26/ 3/ 2020.
 * Last modified 3/26/20 4:05 PM
 */

package com.viwave.collaborationproject.data.general

import com.google.gson.annotations.SerializedName
import com.viwave.collaborationproject.data.cases.Case

data class GetList (
    @SerializedName("res") val res: String,
    @SerializedName("msg") val msg: String,
    @SerializedName("caseList") val caseList: MutableList<Case>
)