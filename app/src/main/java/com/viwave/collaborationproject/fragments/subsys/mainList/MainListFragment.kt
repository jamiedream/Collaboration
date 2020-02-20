package com.viwave.collaborationproject.fragments.subsys.mainList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.viwave.collaborationproject.BackPressedDelegate
import com.viwave.collaborationproject.FakeData.QueryData
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.data.bios.BioViewModel
import com.viwave.collaborationproject.data.cases.Case
import com.viwave.collaborationproject.data.cases.CaseViewModel
import com.viwave.collaborationproject.fragments.BaseFragment
import com.viwave.collaborationproject.fragments.subsys.MeasurementDashboardFragment
import com.viwave.collaborationproject.fragments.subsys.mainList.adapter.CaseListAdapter

class MainListFragment: BaseFragment(), ICaseClicked, BackPressedDelegate{

    override fun onBackPressed(): Boolean {
        return true
    }

    companion object{
        lateinit var caseViewModel: CaseViewModel
        lateinit var bioViewModel: BioViewModel
    }

    private val TAG = this::class.java.simpleName

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        caseViewModel = ViewModelProviders.of(this).get(CaseViewModel::class.java)
        bioViewModel = ViewModelProviders.of(this).get(BioViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        setToolbarTitle(getString(R.string.app_name))
        setToolbarLeftIcon(true)

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
        caseViewModel.getSelectedCase().value = case
//        addFragment(this, MeasurementDashboardFragment(), getString(R.string.tag_case_list))
        replaceFragment(this,
            MeasurementDashboardFragment(), getString(R.string.tag_case_dashboard))
    }

}