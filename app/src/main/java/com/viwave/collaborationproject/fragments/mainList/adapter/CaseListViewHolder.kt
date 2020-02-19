package com.viwave.collaborationproject.fragments.mainList.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.viwave.collaborationproject.R


class CaseListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    val imgGender by lazy { itemView.findViewById<ImageView>(R.id.img_gender) }
    val caseNumber by lazy { itemView.findViewById<TextView>(R.id.txt_case_id) }
    val caseName by lazy { itemView.findViewById<TextView>(R.id.case_name) }

    val bpCount by lazy { itemView.findViewById<TextView>(R.id.bp_count) }
    val tempCount by lazy { itemView.findViewById<TextView>(R.id.temp_count) }
    val bgCount by lazy { itemView.findViewById<TextView>(R.id.bg_count) }

}