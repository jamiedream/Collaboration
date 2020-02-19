package com.viwave.collaborationproject.fragments.mainList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.viwave.collaborationproject.FakeData.QueryData
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.data.cases.Case
import com.viwave.collaborationproject.fragments.BaseFragment
import com.viwave.collaborationproject.fragments.MeasurementDashboardFragment
import com.viwave.collaborationproject.fragments.mainList.adapter.CaseListAdapter

class MainListFragment: BaseFragment(), ICaseClicked {

    private val TAG = this::class.java

    private val recyclerView by lazy { view?.findViewById<RecyclerView>(R.id.cmn_recycler) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.view_cmn_recycler, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()

        val caseListAdapter = CaseListAdapter(QueryData().caseList, this)
        recyclerView?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = caseListAdapter
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    LinearLayoutManager(context).orientation
                )
            )
        }

    }

    override fun whichCase(case: Case) {
        addFragment(this, MeasurementDashboardFragment(case), getString(R.string.tag_case_list))
//        replaceFragment(this, MeasurementDashboardFragment(case), getString(R.string.tag_case_list))
    }

}