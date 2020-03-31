/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/25/20 1:49 PM
 */

package com.viwave.collaborationproject.fragments

data class MeasurementDevice(
    var macAddress:String,
    var deviceName: String,
    var deviceSku:String,
    var deviceCategory:String,
    var deviceCategoryDisplay:String? = null
)