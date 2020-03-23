package com.viwave.collaborationproject.data.general

import com.google.gson.annotations.SerializedName

data class Login (
    @SerializedName("res") val res: String,
    @SerializedName("msg") val msg: String,
    @SerializedName("token") val token: String,
    @SerializedName("system") val system: List<SubSys>
)