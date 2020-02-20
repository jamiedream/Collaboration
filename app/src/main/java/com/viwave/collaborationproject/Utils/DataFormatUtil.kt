package com.viwave.collaborationproject.utils

import java.util.*

object DataFormatUtil {

    fun formatString(value: Any?): String{
        return if(value == null) ""
        else if(value.toString() == "--" || value.toString() == "0" || value.toString() == "0.0") "--"
        else String.format(Locale.ROOT, "%.1f", value)
    }
}