package com.viwave.collaborationproject.data.http

import com.google.gson.annotations.SerializedName

class UploadBioDto(
    @SerializedName("caseNo")
    val caseNo:String,
    @SerializedName("staffId")
    val staffId:String,
    @SerializedName("takenAt")
    val takenAt:String,
    @SerializedName("type")
    val type:String,
    @SerializedName("value")
    val value:String,
    @SerializedName("sysCode")
    val sysCode:String,
    @SerializedName("SCDTID")
    val scdtid:String,
    @SerializedName("note")
    val note:String,
    @SerializedName("rowid")
    val rowId:String
)