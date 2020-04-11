package com.viwave.collaborationproject.data.http

import com.google.gson.annotations.SerializedName
import com.viwave.collaborationproject.data.cases.Case

class GetListRtnDto :DefaultRtnDto() {

    @SerializedName("caseList")
    var caseList:ArrayList<Case>? = null
        private set
}