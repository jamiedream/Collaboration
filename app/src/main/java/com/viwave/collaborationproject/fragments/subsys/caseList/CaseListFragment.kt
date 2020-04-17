/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/30/20 11:49 AM
 */

package com.viwave.collaborationproject.fragments.subsys.caseList

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.viwave.collaborationproject.BackPressedDelegate
import com.viwave.collaborationproject.CollaborationApplication
import com.viwave.collaborationproject.DB.cache.SysKey
import com.viwave.collaborationproject.DB.remote.CaseDatabase
import com.viwave.collaborationproject.DB.remote.entity.CaseEntity
import com.viwave.collaborationproject.MainActivity.Companion.generalViewModel
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.data.bios.BioViewModel
import com.viwave.collaborationproject.data.cases.CaseViewModel
import com.viwave.collaborationproject.data.general.SubSys
import com.viwave.collaborationproject.data.http.GetListRtnDto
import com.viwave.collaborationproject.data.http.HttpErrorData
import com.viwave.collaborationproject.fragments.BaseFragment
import com.viwave.collaborationproject.fragments.subsys.MeasurementDashboardFragment
import com.viwave.collaborationproject.fragments.subsys.SupportCaseFragment
import com.viwave.collaborationproject.fragments.subsys.caseList.adapter.CaseListAdapter
import com.viwave.collaborationproject.http.HttpClientService
import kotlinx.coroutines.*

class CaseListFragment: BaseFragment(), ICaseClicked, BackPressedDelegate, SwipeRefreshLayout.OnRefreshListener{

    override fun onBackPressed(): Boolean {
        return true
    }

    companion object{
        lateinit var caseViewModel: CaseViewModel
        lateinit var bioViewModel: BioViewModel
        const val FEMALE = "女"
        const val MALE = "男"
    }

    private val TAG = this::class.java.simpleName
    private val layoutRefresh by lazy { view!!.findViewById<SwipeRefreshLayout>(R.id.layout_refresh) }

    private var fragmentView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (fragmentView != null) {
            return fragmentView
        }
        val view = inflater.inflate(R.layout.fragment_case_list, container, false)
        fragmentView = view
        setHasOptionsMenu(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        generalViewModel.getSelectedSubSys().observe(this, subSysObserver)
        layoutRefresh.setOnRefreshListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_case, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.support_case -> {
                replaceFragment(this, SupportCaseFragment(), getString(R.string.tag_case_support))
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
                            val list = CaseDatabase(CollaborationApplication.context).getCaseCareDao().getAll()
                            withContext(Dispatchers.Main){
                                loadListWithAnim(list)
                            }
                        }
//                        caseList = mutableListOf<CaseEntity.CaseCareEntity>()
//                        caseListView()
//                        GlobalScope.launch(Dispatchers.IO) {
//                            val list = CaseDatabase(context!!).getCaseCareDao().getAll()
//                            withContext(Dispatchers.Main){
//                                for(num in 0 until list.size){
//                                    (caseList as MutableList<CaseEntity.CaseCareEntity>).add(list[num])
//                                    caseListAdapter.notifyItemInserted(num)
//                                    delay(30)
//                                }
//                            }
//                        }
                        this.setMenuVisibility(false)
                        getString(R.string.sys_daily_care)
                    }
                    SysKey.DAILY_NURSING_NAME -> {
                        GlobalScope.launch(Dispatchers.IO) {
                            val list = CaseDatabase(CollaborationApplication.context).getCaseNursingDao().getAll()
                            withContext(Dispatchers.Main){
                                loadListWithAnim(list)
                            }
                        }
                        this.setMenuVisibility(true)
                        getString(R.string.sys_daily_nursing)
                    }
                    SysKey.DAILY_STATION_NAME -> {
                        GlobalScope.launch(Dispatchers.IO) {
                            val list = CaseDatabase(CollaborationApplication.context).getCaseStationDao().getAll()
                            withContext(Dispatchers.Main){
                                loadListWithAnim(list)
                            }
                        }
                        this.setMenuVisibility(false)
                        getString(R.string.sys_station)
                    }
                    SysKey.DAILY_HOME_CARE_NAME -> {
                        GlobalScope.launch(Dispatchers.IO) {
                            val list = CaseDatabase(CollaborationApplication.context).getCaseHomeCareDao().getAll()
                            withContext(Dispatchers.Main){
                                loadListWithAnim(list)
                            }
                        }
                        this.setMenuVisibility(true)
                        getString(R.string.sys_home_service)
                    }
                    else -> getString(R.string.sys_daily_care)
                }
            )
            setToolbarLeftIcon(true)
        }

    private val recyclerView by lazy { view?.findViewById<RecyclerView>(R.id.cmn_recycler) }
    private lateinit var caseList: MutableList<out CaseEntity>
    private lateinit var caseListAdapter: CaseListAdapter<out CaseEntity>
    private fun caseListView(){
        caseListAdapter = CaseListAdapter(caseList, this@CaseListFragment)
        recyclerView?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = caseListAdapter
            val decorator =
                DividerItemDecoration(
                    context,
                    LinearLayoutManager(context).orientation
                )
            decorator.setDrawable(resources.getDrawable(R.drawable.line_case_list, null))
            addItemDecoration(decorator)
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

    override fun onRefresh() {
        generalViewModel.getSelectedSubSys().value?.apply {
            val sysCode = this.sysCode
            HttpClientService.getList(sysCode,
                object: HttpClientService.HttpCallback<GetListRtnDto>{
                    override fun onSuccess(data: GetListRtnDto) {
                        GlobalScope.launch(Dispatchers.IO) {
                            when (sysCode) {
                                SysKey.DAILY_CARE_CODE -> {
                                    GlobalScope.launch(Dispatchers.IO) {
                                        val list = CaseDatabase(CollaborationApplication.context).getCaseCareDao().getAll()
                                        withContext(Dispatchers.Main){
                                            loadListWithAnim(list)
                                        }
                                    }
                                }
                                SysKey.DAILY_NURSING_CODE -> {
                                    GlobalScope.launch(Dispatchers.IO) {
                                        val list = CaseDatabase(CollaborationApplication.context).getCaseNursingDao().getAll()
                                        withContext(Dispatchers.Main){
                                            loadListWithAnim(list)
                                        }
                                    }
                                }
                                SysKey.DAILY_STATION_CODE -> {
                                    GlobalScope.launch(Dispatchers.IO) {
                                        val list = CaseDatabase(CollaborationApplication.context).getCaseStationDao().getAll()
                                        withContext(Dispatchers.Main){
                                            loadListWithAnim(list)
                                        }
                                    }
                                }
                                else -> {
                                    GlobalScope.launch(Dispatchers.IO) {
                                        val list = CaseDatabase(CollaborationApplication.context).getCaseHomeCareDao().getAll()
                                        withContext(Dispatchers.Main){
                                            loadListWithAnim(list)
                                        }
                                    }
                                }
                            }
                        }
                    }

                    override fun onFailure(errData: HttpErrorData) {
                        layoutRefresh.isRefreshing = false
                        Toast.makeText(context, "Reload list failed.", Toast.LENGTH_LONG).show()
                    }
                }, true)
        }
    }

    private fun <Entity> loadListWithAnim(list: MutableList<Entity>){

        GlobalScope.launch(Dispatchers.Main){
            caseList = mutableListOf<Entity>() as MutableList<out CaseEntity>
            caseListView()
            for(num in 0 until list.size){
                (caseList as MutableList<Entity>).add(list[num])
                caseListAdapter.notifyItemInserted(num)
                delay(30)
            }
            layoutRefresh.isRefreshing = false
        }
    }

}