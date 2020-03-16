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
import com.viwave.collaborationproject.DB.remote.CaseDatabase
import com.viwave.collaborationproject.DB.remote.entity.CaseEntity
import com.viwave.collaborationproject.MainActivity.Companion.generalViewModel
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.data.bios.BioViewModel
import com.viwave.collaborationproject.data.cases.CaseViewModel
import com.viwave.collaborationproject.data.general.SubSys
import com.viwave.collaborationproject.fragments.BaseFragment
import com.viwave.collaborationproject.fragments.subsys.MeasurementDashboardFragment
import com.viwave.collaborationproject.fragments.subsys.caseList.adapter.CaseListAdapter
import com.viwave.collaborationproject.utils.LogUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CaseListFragment: BaseFragment(), ICaseClicked, BackPressedDelegate{

    override fun onBackPressed(): Boolean {
        return true
    }

    companion object{
        lateinit var caseViewModel: CaseViewModel
        lateinit var bioViewModel: BioViewModel
    }

    private val TAG = this::class.java.simpleName

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        caseViewModel = ViewModelProviders.of(this).get(CaseViewModel::class.java)
        bioViewModel = ViewModelProviders.of(this).get(BioViewModel::class.java)
    }

    private val subSysObserver =
        Observer<SubSys?>{
            setToolbarTitle(
                when(it?.sysName){
                    SysKey.DAILY_CARE_NAME -> {
                        GlobalScope.launch(Dispatchers.IO) {
                            val caseList = CaseDatabase(context!!).getCaseCareDao().getAll()
                            withContext(Dispatchers.Main){
                                caseListView(caseList)
                            }
                        }
                        getString(R.string.sys_daily_care)
                    }
                    SysKey.DAILY_NURSING_NAME -> {
                        GlobalScope.launch(Dispatchers.IO) {
                            val caseList = CaseDatabase(context!!).getCaseNursingDao().getAll()
                            withContext(Dispatchers.Main){
                                caseListView(caseList)
                            }
                        }
                        getString(R.string.sys_daily_nursing)
                    }
                    SysKey.DAILY_STATION_NAME -> {
                        GlobalScope.launch(Dispatchers.IO) {
                            val caseList = CaseDatabase(context!!).getCaseStationDao().getAll()
                            withContext(Dispatchers.Main){
                                caseListView(caseList)
                            }
                        }
                        getString(R.string.sys_station)
                    }
                    SysKey.DAILY_HOME_CARE_NAME -> {
                        GlobalScope.launch(Dispatchers.IO) {
                            val caseList = CaseDatabase(context!!).getCaseHomeCareDao().getAll()
                            withContext(Dispatchers.Main){
                                caseListView(caseList)
                            }
                        }
                        getString(R.string.sys_home_service)
                    }
                    else -> getString(R.string.sys_daily_care)
                }
            )
            setToolbarLeftIcon(true)
        }

    override fun onResume() {
        super.onResume()
        generalViewModel.getSelectedSubSys().observe(this, subSysObserver)
    }

    private fun caseListView(caseList: MutableList<out CaseEntity>){
        val caseListAdapter = CaseListAdapter(caseList, this)
        val recyclerView = view?.findViewById<RecyclerView>(R.id.cmn_recycler)
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

    override fun whichCase(case: CaseEntity) {
        caseViewModel.getSelectedCase().value = case
        replaceFragment(this,
            MeasurementDashboardFragment(), getString(R.string.tag_case_dashboard))
    }

    override fun onStop() {
        generalViewModel.getSelectedSubSys().removeObserver(subSysObserver)
        super.onStop()
    }

}