/*
 * Copyright (c) viWave 2019.
 * Create by J.Y Yen 20/ 8/ 2019.
 * Last modified 8/20/19 6:54 PM
 */

package com.viwave.collaborationproject.utils

object DateUtil{

    private val TAG = this::class.java.simpleName

    fun getNowTimestamp(): Long{
        return System.currentTimeMillis()
    }

}