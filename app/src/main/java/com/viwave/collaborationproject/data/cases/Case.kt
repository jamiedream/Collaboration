/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/31/20 2:26 PM
 */

package com.viwave.collaborationproject.data.cases

import com.google.gson.annotations.SerializedName

data class Case(
    @SerializedName("caseNO") val caseNumber: String,
    @SerializedName("caseName") val caseName: String,
    @SerializedName("caseGender") val caseGender: String,
    @SerializedName("startTime") val startTime: String?,
    @SerializedName("SCDTID") val SCDTID: String?
)