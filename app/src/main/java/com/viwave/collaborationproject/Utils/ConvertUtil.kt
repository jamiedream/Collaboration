/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/27/20 4:09 PM
 */

package com.viwave.collaborationproject.utils

import android.util.Base64
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonDeserializer
import com.google.gson.reflect.TypeToken


object ConvertUtil {

    fun fromBase64(encodeStr: String): String{
        val decoded = Base64.decode(encodeStr, Base64.URL_SAFE)
        return String(decoded, charset("UTF-8"))
    }

    fun <T> sortListData(deserializer: JsonDeserializer<MutableList<T>>, data: JsonArray): MutableList<T>{
        return GsonBuilder()
            .registerTypeAdapter(object: TypeToken<MutableList<T>>(){}.type, deserializer)
            .create()
            .fromJson(data, object: TypeToken<MutableList<T>>(){}.type)
    }

}