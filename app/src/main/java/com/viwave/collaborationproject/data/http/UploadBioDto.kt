package com.viwave.collaborationproject.data.http

import com.google.gson.annotations.SerializedName

class UploadBioDto(
    @SerializedName("caseNo")
    val caseNo:String,
    @SerializedName("staffId")
    val staffId:String,
    @SerializedName("SCDTID")
    val scdtid:String,
    @SerializedName("sysCode")
    val sysCode:String,
    @SerializedName("type")
    val type:String,
    @SerializedName("takenAt")
    val takenAt:String,
    @SerializedName("value")
    val value:String,
    @SerializedName("note")
    val note:String,
    @SerializedName("rowid")
    val rowId:String
)