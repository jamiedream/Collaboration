/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/31/20 2:26 PM
 */

package com.viwave.collaborationproject.fragments.subsys.caseList.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.viwave.collaborationproject.DB.remote.DataCountAction.getListFromStr
import com.viwave.collaborationproject.DB.remote.entity.CaseEntity
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.data.DataSort
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

            holder.caseNumber.text = this.getCaseNumber
            holder.caseName.text = this.getCaseName
            holder.itemView.setOnClickListener { whichCaseClicked.whichCase(this) }
            val datacountList = getListFromStr(this.getDataCount)
            holder.tempCount.text =
                datacountList.find { it.type == DataSort.Temperature }?.count?.let {
                    if(it == 0) holder.itemView.context.getString(R.string.two_dash) else it.toString()
                }
            holder.pulseCount.text =
                datacountList.find { it.type == DataSort.Pulse }?.count?.let {
                    if(it == 0) holder.itemView.context.getString(R.string.two_dash) else it.toString()
                }
            holder.respireCount.text =
                datacountList.find { it.type == DataSort.Respire }?.count?.let {
                    if(it == 0) holder.itemView.context.getString(R.string.two_dash) else it.toString()
                }
            holder.bgCount.text =
                datacountList.find { it.type == DataSort.BloodGlucose }?.count?.let {
                    if(it == 0) holder.itemView.context.getString(R.string.two_dash) else it.toString()
                }
            holder.bpCount.text =
                datacountList.find { it.type == DataSort.BloodPressure }?.count?.let {
                    if(it == 0) holder.itemView.context.getString(R.string.two_dash) else it.toString()
                }
            holder.oxygenCount.text =
                datacountList.find { it.type == DataSort.Oxygen }?.count?.let {
                    if(it == 0) holder.itemView.context.getString(R.string.two_dash) else it.toString()
                }
            holder.heightCount.text =
                datacountList.find { it.type == DataSort.Height }?.count?.let {
                    if(it == 0) holder.itemView.context.getString(R.string.two_dash) else it.toString()
                }
            holder.weightCount.text =
                datacountList.find { it.type == DataSort.Weight }?.count?.let {
                    if(it == 0) holder.itemView.context.getString(R.string.two_dash) else it.toString()
                }
        }



    }
}