/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 1/ 4/ 2020.
 * Last modified 4/1/20 4:52 PM
 */

package com.viwave.collaborationproject.fragments.device.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.fragments.device.IDeviceClicked
import com.viwave.collaborationproject.fragments.device.data.DeviceTypeData

class DeviceListAdapter(private val deviceTypeList: MutableList<DeviceTypeData>, private val listener: IDeviceClicked): RecyclerView.Adapter<DeviceListViewHolder>() {

    private val TAG = this::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceListViewHolder {
        return DeviceListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_device_list, parent, false))
    }

    override fun getItemCount(): Int {
        return deviceTypeList.size
    }

    override fun onBindViewHolder(holder: DeviceListViewHolder, position: Int) {
        holder.deviceType.text = deviceTypeList[position].type
        holder.deviceImage.setImageResource(deviceTypeList[position].img)
        holder.deviceView.setOnClickListener { listener.onClicked(deviceTypeList[position]) }
    }
}