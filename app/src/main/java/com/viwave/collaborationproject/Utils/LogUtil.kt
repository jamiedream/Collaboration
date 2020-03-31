/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/25/20 1:49 PM
 */

package com.viwave.collaborationproject.utils

import android.util.Log
import com.viwave.collaborationproject.BuildConfig

object LogUtil {

    @JvmStatic
    fun logD(TAG: String, msg: Any?){
        if(BuildConfig.DEBUG)
            Log.d(TAG, "$msg")
    }

    @JvmStatic
    fun logE(TAG: String, msg: Any?){
        if(BuildConfig.DEBUG)
            Log.e(TAG, "$msg")
    }

    @JvmStatic
    fun logV(TAG: String, msg: Any?){
        if(BuildConfig.DEBUG)
            Log.v(TAG, "$msg")
    }

    @JvmStatic
    fun logI(TAG: String, msg: Any?){
        if(BuildConfig.DEBUG)
            Log.i(TAG, "$msg")
    }

    @JvmStatic
    fun logW(TAG: String, msg: Any?){
        if(BuildConfig.DEBUG)
            Log.w(TAG, "$msg")
    }

}