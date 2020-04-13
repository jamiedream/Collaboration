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

    val imgGender: ImageView by lazy { itemView.findViewById<ImageView>(R.id.img_gender) }
    val caseNumber: TextView by lazy { itemView.findViewById<TextView>(R.id.txt_case_id) }
    val caseName: TextView by lazy { itemView.findViewById<TextView>(R.id.case_name) }

    //vis-> value
    val caseSchedule: TextView by lazy { itemView.findViewById<TextView>(R.id.txt_schedule) }

    val tempCountLayout: LinearLayout by lazy { itemView.findViewById<LinearLayout>(R.id.temp_measurement_count) }
    val pulseCountLayout: LinearLayout by lazy { itemView.findViewById<LinearLayout>(R.id.pulse_measurement_count) }
    val respireCountLayout: LinearLayout by lazy { itemView.findViewById<LinearLayout>(R.id.respire_measurement_count) }
    val bpCountLayout: LinearLayout by lazy { itemView.findViewById<LinearLayout>(R.id.bp_measurement_count) }
    val bgCountLayout: LinearLayout by lazy { itemView.findViewById<LinearLayout>(R.id.bg_measurement_count) }
    val heightCountLayout: LinearLayout by lazy { itemView.findViewById<LinearLayout>(R.id.height_measurement_count) }
    val weightCountLayout: LinearLayout by lazy { itemView.findViewById<LinearLayout>(R.id.weight_measurement_count) }
    val oxygenCountLayout: LinearLayout by lazy { itemView.findViewById<LinearLayout>(R.id.oxygen_measurement_count) }

    val tempCount: TextView by lazy { itemView.findViewById<TextView>(R.id.temp_count) }
    val pulseCount: TextView by lazy { itemView.findViewById<TextView>(R.id.pulse_count) }
    val respireCount: TextView by lazy { itemView.findViewById<TextView>(R.id.respire_count) }
    val bpCount: TextView by lazy { itemView.findViewById<TextView>(R.id.bp_count) }
    val bgCount: TextView by lazy { itemView.findViewById<TextView>(R.id.bg_count) }
    val heightCount: TextView by lazy { itemView.findViewById<TextView>(R.id.height_count) }
    val weightCount: TextView by lazy { itemView.findViewById<TextView>(R.id.weight_count) }
    val oxygenCount: TextView by lazy { itemView.findViewById<TextView>(R.id.oxygen_count) }

    val tempImg: ImageView by lazy { itemView.findViewById<ImageView>(R.id.temp_img) }
    val pulseImg: ImageView by lazy { itemView.findViewById<ImageView>(R.id.pulse_img) }
    val respireImg: ImageView by lazy { itemView.findViewById<ImageView>(R.id.respire_img) }
    val bpImg: ImageView by lazy { itemView.findViewById<ImageView>(R.id.bp_img) }
    val bgImg: ImageView by lazy { itemView.findViewById<ImageView>(R.id.bg_img) }
    val heightImg: ImageView by lazy { itemView.findViewById<ImageView>(R.id.height_img) }
    val weightImg: ImageView by lazy { itemView.findViewById<ImageView>(R.id.weight_img) }
    val oxygenImg: ImageView by lazy { itemView.findViewById<ImageView>(R.id.oxygen_img) }


}