/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 9/ 4/ 2020.
 * Last modified 4/9/20 3:24 PM
 */

package com.viwave.collaborationproject.fragments.subsys.history.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.viwave.collaborationproject.R

class HistoryListTypeOneViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    val measureTime = itemView.findViewById<TextView>(R.id.content_measure_time)
    val value = itemView.findViewById<TextView>(R.id.content_value)

}