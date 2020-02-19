package com.viwave.collaborationproject.utils

import android.util.Base64


object ConvertUtil {

    fun fromBase64(encodeStr: String): String{
        val decoded = Base64.decode(encodeStr, Base64.URL_SAFE)
        return String(decoded, charset("UTF-8"))
    }

}