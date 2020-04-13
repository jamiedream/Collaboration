/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 6/ 4/ 2020.
 * Last modified 4/6/20 11:26 AM
 */

package com.viwave.collaborationproject.fragments.device.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.fragments.device.IDeviceClicked
import com.viwaveulife.vuioht.VUBleDevice

class ScanListAdapter(private val scanList: MutableList<VUBleDevice>, private val listener: IDeviceClicked): RecyclerView.Adapter<ScanListViewHolder>() {

    private val TAG = this::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScanListViewHolder {
        return ScanListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_device_scan_item, parent, false))
    }

    override fun getItemCount(): Int {
        return scanList.size
    }

    override fun onBindViewHolder(holder: ScanListViewHolder, position: Int) {
        holder.txtScan.text = scanList[position].name
        holder.btnScanBind.setOnClickListener { listener.onClicked("", scanList[position]) }
    }
}