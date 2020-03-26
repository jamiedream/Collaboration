/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 26/ 3/ 2020.
 * Last modified 3/26/20 3:42 PM
 */

package com.viwave.collaborationproject.http

interface IAPIResult {

    fun onSuccess()
    fun onFailed(msg: String?)
}