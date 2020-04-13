/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 6/ 4/ 2020.
 * Last modified 4/6/20 2:53 PM
 */

package com.viwave.collaborationproject.fragments.device.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.fragments.device.IDeviceClicked
import com.viwave.collaborationproject.fragments.device.data.DeviceDetailData
import com.viwave.collaborationproject.fragments.device.data.MeasurementDevice

class DeviceDetailAdapter(private val device: MeasurementDevice, private val infoList: MutableList<DeviceDetailData>, private val listener: IDeviceClicked) : RecyclerView.Adapter<DeviceDetailViewHolder>() {

    private val TAG = this::class.java

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceDetailViewHolder {
        return DeviceDetailViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_device_detail, parent, false))
    }

    override fun getItemCount(): Int {
        return infoList.size
    }

    override fun onBindViewHolder(holder: DeviceDetailViewHolder, position: Int) {
        holder.detailTitle.text = infoList[position].title
        holder.detailContent.text =
            when(infoList[position].title == holder.itemView.context.getString(R.string.device_rename_title)){
                true -> {
                    holder.detailContent.setCompoundDrawablesWithIntrinsicBounds(
                        null,
                        null,
                        holder.itemView.context.getDrawable(R.drawable.ic_arrow_next),
                        null
                    )
                    holder.detailContent.setOnClickListener{ listener.onClicked(device.deviceSku, device) }
                    infoList[position].content
                }
                false -> infoList[position].content
            }

    }
}