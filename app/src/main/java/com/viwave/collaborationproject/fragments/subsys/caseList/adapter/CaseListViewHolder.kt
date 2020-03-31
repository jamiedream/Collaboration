/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/30/20 12:04 PM
 */

package com.viwave.collaborationproject.fragments.subsys.caseList.adapter

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.viwave.collaborationproject.R


class CaseListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    val imgGender by lazy { itemView.findViewById<ImageView>(R.id.img_gender) }
    val caseNumber by lazy { itemView.findViewById<TextView>(R.id.txt_case_id) }
    val caseName by lazy { itemView.findViewById<TextView>(R.id.case_name) }

    //vis-> value
    val caseSchedule by lazy { itemView.findViewById<TextView>(R.id.txt_schedule) }

    val tempCountLayout by lazy { itemView.findViewById<LinearLayout>(R.id.temp_measurement_count) }
    val pulseCountLayout by lazy { itemView.findViewById<LinearLayout>(R.id.pulse_measurement_count) }
    val respireCountLayout by lazy { itemView.findViewById<LinearLayout>(R.id.respire_measurement_count) }
    val bpCountLayout by lazy { itemView.findViewById<LinearLayout>(R.id.bp_measurement_count) }
    val bgCountLayout by lazy { itemView.findViewById<LinearLayout>(R.id.bg_measurement_count) }
    val heightCountLayout by lazy { itemView.findViewById<LinearLayout>(R.id.height_measurement_count) }
    val weightCountLayout by lazy { itemView.findViewById<LinearLayout>(R.id.weight_measurement_count) }
    val oxygenCountLayout by lazy { itemView.findViewById<LinearLayout>(R.id.oxygen_measurement_count) }

    val tempCount by lazy { itemView.findViewById<TextView>(R.id.temp_count) }
    val pulseCount by lazy { itemView.findViewById<TextView>(R.id.pulse_count) }
    val respireCount by lazy { itemView.findViewById<TextView>(R.id.respire_count) }
    val bpCount by lazy { itemView.findViewById<TextView>(R.id.bp_count) }
    val bgCount by lazy { itemView.findViewById<TextView>(R.id.bg_count) }
    val heightCount by lazy { itemView.findViewById<TextView>(R.id.height_count) }
    val weightCount by lazy { itemView.findViewById<TextView>(R.id.weight_count) }
    val oxygenCount by lazy { itemView.findViewById<TextView>(R.id.oxygen_count) }



}