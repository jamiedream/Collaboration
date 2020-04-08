package com.viwave.collaborationproject.data.http

import com.google.gson.annotations.SerializedName
import com.viwave.collaborationproject.data.cases.Case

class GetListRtnDto {
    @SerializedName("res")
    var res:String = ""
        private set
    @SerializedName("msg")
    var msg:String = ""
        private set
    @SerializedName("caseList")
    var sysList:ArrayList<Case>? = null
        private set
}