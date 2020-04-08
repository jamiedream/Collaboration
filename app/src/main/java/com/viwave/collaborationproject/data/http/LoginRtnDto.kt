package com.viwave.collaborationproject.data.http

import com.google.gson.annotations.SerializedName
import com.viwave.collaborationproject.data.general.SubSys

class LoginRtnDto {
    @SerializedName("res")
    var res:String = ""
        private set
    @SerializedName("msg")
    var msg:String = ""
        private set
    @SerializedName("token")
    var token:String = ""
        private set
    @SerializedName("system")
    var sysList:ArrayList<SubSys>? = null
        private set
}