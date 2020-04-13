/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 6/ 4/ 2020.
 * Last modified 4/6/20 2:36 PM
 */

package com.viwave.collaborationproject.fragments.device.data

data class DeviceData (
    val type: String,
    val deviceList: MutableList<MeasurementDevice>
)