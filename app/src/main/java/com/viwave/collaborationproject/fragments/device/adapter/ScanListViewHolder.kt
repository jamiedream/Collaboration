/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 6/ 4/ 2020.
 * Last modified 4/6/20 11:26 AM
 */

package com.viwave.collaborationproject.fragments.device.adapter

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.viwave.collaborationproject.R

class ScanListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val txtScan by lazy { itemView.findViewById<TextView>(R.id.scan_device) }
    val btnScanBind by lazy { itemView.findViewById<Button>(R.id.scan_btn_bind) }
}