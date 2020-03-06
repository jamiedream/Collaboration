package com.viwave.collaborationproject.fragments.subsys.caseList

import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.viwave.collaborationproject.BackPressedDelegate
import com.viwave.collaborationproject.DB.cache.SysKey
import com.viwave.collaborationproject.FakeData.QueryData
import com.viwave.collaborationproject.MainActivity.Companion.generalViewModel
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.data.bios.BioViewModel
import com.viwave.collaborationproject.data.cases.Case
import com.viwave.collaborationproject.data.cases.CaseViewModel
import com.viwave.collaborationproject.data.general.SubSys
import com.viwave.collaborationproject.fragments.BaseFragment
import com.viwave.collaborationproject.fragments.subsys.MeasurementDashboardFragment
import com.viwave.collaborationproject.fragments.subsys.caseList.adapter.CaseListAdapter
import com.viwave.collaborationproject.utils.LogUtil

class CaseListFragment: BaseFragment(), ICaseClicked, BackPressedDelegate{

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
        val view = inflater.inflate(R.layout.view_cmn_recycler, container, false)
        setHasOptionsMenu(true)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_case_add, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.case_add -> {
                LogUtil.logD(TAG, "ADD CASE")
                return true
            }
        }
        return super.onOptionsItemSelected(item)
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
        generalViewModel.getSelectedSubSys().observe(this, Observer<SubSys?>{
            setToolbarTitle(
                when(it?.sysName){
                    SysKey.DAILY_CARE_NAME -> getString(R.string.sys_daily_care)
                    SysKey.DAILY_NURSING_NAME -> getString(R.string.sys_daily_nursing)
                    SysKey.DAILY_STATION_NAME -> getString(R.string.sys_station)
                    SysKey.DAILY_HOME_CARE_NAME -> getString(R.string.sys_home_service)
                    else -> getString(R.string.sys_daily_care)
                }
            )
            setToolbarLeftIcon(true)

            caseList(QueryData().caseList)
        })

    }

    private fun caseList(caseList: MutableList<Case>){
        val caseListAdapter = CaseListAdapter(caseList, this)
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
        replaceFragment(this,
            MeasurementDashboardFragment(), getString(R.string.tag_case_dashboard))
    }

}