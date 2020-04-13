/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 1/ 4/ 2020.
 * Last modified 4/1/20 4:09 PM
 */

package com.viwave.collaborationproject.fragments.device.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.fragments.device.IDeviceClicked
import com.viwave.collaborationproject.fragments.device.data.MeasurementDevice

class BindingSubListAdapter(private val deviceList: MutableList<MeasurementDevice>, private val listener: IDeviceClicked) : RecyclerView.Adapter<BindingSubListViewHolder>() {

    private val TAG = this::class.java

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingSubListViewHolder {
        return BindingSubListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_device_binding, parent, false))
    }

    override fun getItemCount(): Int {
        return deviceList.size
    }

    override fun onBindViewHolder(holder: BindingSubListViewHolder, position: Int) {
        holder.txtDeviceType.text = deviceList[position].deviceName
        holder.txtDeviceMac.text = deviceList[position].macAddress
        holder.txtDeviceNickName.text = deviceList[position].deviceNickname
        holder.imgNext.setOnClickListener { listener.onClicked(deviceList[position].deviceSku, deviceList[position]) }
    }
}