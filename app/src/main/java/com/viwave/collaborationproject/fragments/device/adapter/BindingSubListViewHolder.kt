/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 1/ 4/ 2020.
 * Last modified 4/1/20 3:44 PM
 */

package com.viwave.collaborationproject.fragments.device.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.viwave.collaborationproject.R

class BindingSubListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    val txtDeviceType by lazy { itemView.findViewById<TextView>(R.id.device_type) }
    val txtDeviceNickName by lazy { itemView.findViewById<TextView>(R.id.device_nick_name) }
    val txtDeviceMac by lazy { itemView.findViewById<TextView>(R.id.device_mac) }
    val imgNext by lazy { itemView.findViewById<ImageView>(R.id.device_next) }

}