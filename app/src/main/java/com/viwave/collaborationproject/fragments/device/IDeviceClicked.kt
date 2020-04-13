/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 1/ 4/ 2020.
 * Last modified 4/1/20 1:00 PM
 */

package com.viwave.collaborationproject.fragments.device

import com.viwave.collaborationproject.fragments.device.data.DeviceTypeData
import com.viwave.collaborationproject.fragments.device.data.MeasurementDevice
import com.viwaveulife.vuioht.VUBleDevice

interface IDeviceClicked {

    fun onClicked(type: String, device: VUBleDevice? = null)
    //device detail
    fun onClicked(type: String, device: MeasurementDevice)
    //device type
    fun onClicked(typeInfo: DeviceTypeData)
}