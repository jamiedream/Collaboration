package com.viwave.collaborationproject.data.cases

import com.google.gson.annotations.SerializedName

data class Case(
    @SerializedName("caseNO") val caseNumber: String,
    @SerializedName("caseName") val caseName: String,
    @SerializedName("caseGender") val caseGender: String,
    @SerializedName("startTime") val startTime: String?,
    @SerializedName("SCDTID") val SCDTID: String?
)