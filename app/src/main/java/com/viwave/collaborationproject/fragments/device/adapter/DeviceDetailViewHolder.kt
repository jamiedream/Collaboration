/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 6/ 4/ 2020.
 * Last modified 4/6/20 2:53 PM
 */

package com.viwave.collaborationproject.fragments.device.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.viwave.collaborationproject.R

class DeviceDetailViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val detailTitle by lazy { itemView.findViewById<TextView>(R.id.detail_title) }
    val detailContent by lazy { itemView.findViewById<TextView>(R.id.detail_content) }
}