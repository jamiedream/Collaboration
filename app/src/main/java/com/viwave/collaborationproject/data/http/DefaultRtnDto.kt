package com.viwave.collaborationproject.data.http

import com.google.gson.annotations.SerializedName

open class DefaultRtnDto {
    @SerializedName("res")
    var res:String = ""
        private set
    @SerializedName("msg")
    var msg:String = ""
        private set
}