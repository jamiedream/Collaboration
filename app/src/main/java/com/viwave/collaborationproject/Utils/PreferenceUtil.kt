package com.viwave.collaborationproject.utils

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.viwave.collaborationproject.CollaborationApplication
import com.viwave.collaborationproject.fragments.MeasurementDevice

class PreferenceUtil {
    companion object{
        @JvmStatic
        fun saveDevice(macAddress:String, device: MeasurementDevice? = null) {
            val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(CollaborationApplication.context)
            if(device == null) {
                preferences.edit().remove(macAddress).apply()
            } else {
                preferences.edit().putString(macAddress, Gson().toJson(device)).apply()
            }
        }

        @JvmStatic
        fun loadDevice(macAddress:String): MeasurementDevice? {
            val preferences:SharedPreferences = PreferenceManager.getDefaultSharedPreferences(CollaborationApplication.context)

            val tmpStr = preferences.getString(macAddress, null)
            if(tmpStr != null) {
                return Gson().fromJson(tmpStr, MeasurementDevice::class.java)
            }
            return null
        }
    }
}