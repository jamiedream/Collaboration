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
            holder.caseState.text = this.getStartTime
            if(this.getIsSupport == true)
                holder.caseState.
                    setCompoundDrawables(
                        null,
                        null,
                        ContextCompat.getDrawable(holder.itemView.context,R.drawable.ic_support),
                        null)
            holder.caseNumber.text = this.getCaseNumber
            holder.caseName.text = this.getCaseName
            holder.itemView.setOnClickListener { whichCaseClicked.whichCase(this) }
            val datacountList = getListFromStr(this.getDataCount)
            holder.tempCount.text = datacountList.find { it.type == DataSort.Temperature }?.count?.toString()
            holder.pulseCount.text = datacountList.find { it.type == DataSort.Pulse }?.count?.toString()
            holder.respireCount.text = datacountList.find { it.type == DataSort.Respire }?.count?.toString()
            holder.bgCount.text = datacountList.find { it.type == DataSort.BloodGlucose }?.count?.toString()
            holder.bpCount.text = datacountList.find { it.type == DataSort.BloodPressure }?.count?.toString()
            holder.oxygenCount.text = datacountList.find { it.type == DataSort.Oxygen }?.count?.toString()
            holder.heightCount.text = datacountList.find { it.type == DataSort.Height }?.count?.toString()
            holder.weightCount.text = datacountList.find { it.type == DataSort.Weight }?.count?.toString()
        }



    }
}