/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/31/20 2:26 PM
 */

package com.viwave.collaborationproject.fragments.subsys.caseList.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.viwave.collaborationproject.DB.remote.DataCountAction.getListFromStr
import com.viwave.collaborationproject.DB.remote.entity.CaseEntity
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.data.DataSort
import com.viwave.collaborationproject.fragments.subsys.caseList.CaseListFragment.Companion.FEMALE
import com.viwave.collaborationproject.fragments.subsys.caseList.ICaseClicked

class CaseListAdapter(private val caseList: MutableList<out CaseEntity>, private val whichCaseClicked: ICaseClicked): RecyclerView.Adapter<CaseListViewHolder>() {

    private val TAG = this::class.java.simpleName
    private var isNursing = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CaseListViewHolder {
        when(caseList.find { it is CaseEntity.CaseNursingEntity }?: false){
            false -> isNursing = false
        }
        return CaseListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_case_list, parent, false))
    }

    override fun getItemCount(): Int {
        return caseList.size
    }

    override fun onBindViewHolder(holder: CaseListViewHolder, position: Int) {

        if(!isNursing) {
            holder.respireCountLayout.visibility = View.GONE
            holder.heightCountLayout.visibility = View.GONE
            holder.weightCountLayout.visibility = View.GONE
            holder.oxygenCountLayout.visibility = View.GONE
        }

        caseList[position].apply {
            holder.imgGender.setImageResource(
                when(this.getCaseGender == FEMALE){
                    true -> R.drawable.ic_gender_female
                    else -> R.drawable.ic_gender_male
                }
            )
            holder.caseSchedule.text = this.getStartTime
            if(this.getIsSupport == true)
                holder.caseSchedule.
                    setCompoundDrawables(
                        null,
                        null,
                        ContextCompat.getDrawable(holder.itemView.context,R.drawable.ic_new_case),
                        null)
            holder.caseNumber.text = this.getCaseNumber
            holder.caseName.text = this.getCaseName
            holder.itemView.setOnClickListener { whichCaseClicked.whichCase(this) }
            val dataCountList = getListFromStr(this.getDataCount)
            holder.tempCount.text =
                dataCountList.find { it.type == DataSort.Temperature }?.count?.let {
                    if(it == 0) {
                        holder.tempImg.alpha = .4f
                        ""
                    } else {
                        holder.tempImg.alpha = 1f
                        it.toString()
                    }
                }
            holder.pulseCount.text =
                dataCountList.find { it.type == DataSort.Pulse }?.count?.let {
                    if(it == 0) {
                        holder.pulseImg.alpha = .4f
                        ""
                    } else {
                        holder.pulseImg.alpha = 1f
                        it.toString()
                    }
                }
            holder.respireCount.text =
                dataCountList.find { it.type == DataSort.Respire }?.count?.let {
                    if(it == 0) {
                        holder.respireImg.alpha = .4f
                        ""
                    } else {
                        holder.respireImg.alpha = 1f
                        it.toString()
                    }
                }
            holder.bgCount.text =
                dataCountList.find { it.type == DataSort.BloodGlucose }?.count?.let {
                    if(it == 0) {
                        holder.bgImg.alpha = .4f
                        ""
                    } else {
                        holder.bgImg.alpha = 1f
                        it.toString()
                    }
                }
            holder.bpCount.text =
                dataCountList.find { it.type == DataSort.BloodPressure }?.count?.let {
                    if(it == 0) {
                        holder.bpImg.alpha = .4f
                        ""
                    } else {
                        holder.bpImg.alpha = 1f
                        it.toString()
                    }
                }
            holder.oxygenCount.text =
                dataCountList.find { it.type == DataSort.Oxygen }?.count?.let {
                    if(it == 0) {
                        holder.oxygenImg.alpha = .4f
                        ""
                    } else {
                        holder.oxygenImg.alpha = 1f
                        it.toString()
                    }
                }
            holder.heightCount.text =
                dataCountList.find { it.type == DataSort.Height }?.count?.let {
                    if(it == 0) {
                        holder.heightImg.alpha = .4f
                        ""
                    } else {
                        holder.heightImg.alpha = 1f
                        it.toString()
                    }
                }
            holder.weightCount.text =
                dataCountList.find { it.type == DataSort.Weight }?.count?.let {
                    if(it == 0) {
                        holder.weightImg.alpha = .4f
                        ""
                    } else {
                        holder.weightImg.alpha = 1f
                        it.toString()
                    }
                }
        }



    }
}