/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 1/ 4/ 2020.
 * Last modified 4/1/20 12:19 PM
 */

package com.viwave.collaborationproject.fragments.device.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.fragments.device.data.MeasurementDevice
import com.viwave.collaborationproject.fragments.device.IDeviceClicked

class BindingListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    val txtAddDevice by lazy { itemView.findViewById<TextView>(R.id.txt_add_device) }
    val viewAddDevice by lazy { itemView.findViewById<View>(R.id.view_add_device) }
    val imgAddDevice by lazy { itemView.findViewById<ImageView>(R.id.img_add_device) }

    fun bindDevice(deviceList: MutableList<MeasurementDevice>, listener: IDeviceClicked){
        viewAddDevice.setOnClickListener{
            val txtEnable = txtAddDevice.isEnabled
            txtAddDevice.isEnabled = !txtEnable

            val bindingDeviceListAdapter = BindingSubListAdapter(deviceList, listener)
            val recyclerView = itemView.findViewById<RecyclerView>(R.id.list_device)
            recyclerView?.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = bindingDeviceListAdapter
                val decorator =
                    DividerItemDecoration(
                        context,
                        LinearLayoutManager(context).orientation
                    )
                decorator.setDrawable(resources.getDrawable(R.drawable.line_case_list, null))
                addItemDecoration(decorator)
            }

            when(!txtEnable){
                true -> recyclerView.visibility = View.VISIBLE
                false -> recyclerView.visibility = View.GONE
            }
        }

    }

}