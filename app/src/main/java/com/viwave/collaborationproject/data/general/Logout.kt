package com.viwave.collaborationproject.data.general

import com.google.gson.annotations.SerializedName

data class Logout (
    @SerializedName("res") val res: String,
    @SerializedName("msg") val msg: String
)