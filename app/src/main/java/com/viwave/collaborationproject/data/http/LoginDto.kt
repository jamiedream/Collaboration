package com.viwave.collaborationproject.data.http

import com.google.gson.annotations.SerializedName

class LoginDto {
    @SerializedName("staffid")
    var staffId:String = ""
    @SerializedName("staffPwd")
    var staffPwd:String = ""
}