package com.viwave.collaborationproject.fragments

data class MeasurementDevice(
    var macAddress:String,
    var deviceName: String,
    var deviceSku:String,
    var deviceCategory:String,
    var deviceCategoryDisplay:String? = null
)