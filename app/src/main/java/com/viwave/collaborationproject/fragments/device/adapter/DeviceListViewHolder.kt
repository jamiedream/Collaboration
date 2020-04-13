/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 1/ 4/ 2020.
 * Last modified 4/1/20 4:53 PM
 */

package com.viwave.collaborationproject.fragments.device.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.viwave.collaborationproject.R

class DeviceListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    val deviceType by lazy { itemView.findViewById<TextView>(R.id.device_type) }
    val deviceImage by lazy { itemView.findViewById<ImageView>(R.id.device_img) }
    val deviceView by lazy { itemView.findViewById<View>(R.id.device_list_view) }
}