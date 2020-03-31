/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/25/20 1:49 PM
 */

package com.viwave.collaborationproject.utils

import java.util.*

object DataFormatUtil {

    fun formatString(value: Any?): String{
        return if(value == null) ""
        else if(value.toString() == "--" || value.toString() == "0" || value.toString() == "0.0") "--"
        else String.format(Locale.ROOT, "%.1f", value)
    }
}