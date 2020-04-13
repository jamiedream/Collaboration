/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 1/ 4/ 2020.
 * Last modified 4/1/20 12:20 PM
 */

package com.viwave.collaborationproject.fragments.device.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.fragments.device.data.DeviceData
import com.viwave.collaborationproject.fragments.device.IDeviceClicked
import com.viwave.collaborationproject.utils.LogUtil

class BindingListAdapter(private val bindingTypeList: MutableList<DeviceData>, private val listener: IDeviceClicked): RecyclerView.Adapter<BindingListViewHolder>() {

    private val TAG = this::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingListViewHolder {
        return BindingListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_device_binding_type, parent, false))
    }

    override fun getItemCount(): Int {
        return bindingTypeList.size
    }

    override fun getItemViewType(position: Int): Int {
        LogUtil.logD(TAG, position)
        return super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: BindingListViewHolder, position: Int) {

        holder.txtAddDevice.text = bindingTypeList[position].type
        holder.bindDevice(bindingTypeList[position].deviceList, listener)
        holder.imgAddDevice.setOnClickListener { listener.onClicked(bindingTypeList[position].type) }

    }


}