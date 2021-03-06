package com.viwave.collaborationproject.fragments.subsys.caseList.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.viwave.collaborationproject.DB.remote.entity.CaseEntity
import com.viwave.collaborationproject.R
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

        holder.caseNumber.text = caseList[position].getCaseNumber
        holder.caseName.text = caseList[position].getCaseName
        holder.itemView.setOnClickListener { whichCaseClicked.whichCase(caseList[position]) }

    }
}