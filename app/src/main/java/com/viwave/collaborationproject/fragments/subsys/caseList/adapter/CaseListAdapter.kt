package com.viwave.collaborationproject.fragments.subsys.caseList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.data.cases.Case
import com.viwave.collaborationproject.fragments.subsys.caseList.ICaseClicked
import com.viwave.collaborationproject.utils.LogUtil

class CaseListAdapter(private val caseList: MutableList<Case>, private val whichCaseClicked: ICaseClicked): RecyclerView.Adapter<CaseListViewHolder>() {

    private val TAG = this::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CaseListViewHolder {
        return CaseListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_case_list, parent, false))
    }

    override fun getItemCount(): Int {
        return caseList.size
    }

    override fun onBindViewHolder(holder: CaseListViewHolder, position: Int) {

        LogUtil.logD(TAG, caseList[position].caseName)
        holder.caseNumber.text = caseList[position].caseNumber
        holder.caseName.text = caseList[position].caseName
        holder.itemView.setOnClickListener { whichCaseClicked.whichCase(caseList[position]) }

    }
}