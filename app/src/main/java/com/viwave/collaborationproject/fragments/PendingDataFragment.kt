/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/25/20 1:49 PM
 */

package com.viwave.collaborationproject.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ExpandableListView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.viwave.collaborationproject.DB.cache.SysKey
import com.viwave.collaborationproject.DB.remote.BioAction
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.data.bios.Bio
import com.viwave.collaborationproject.data.cases.Case
import com.viwave.collaborationproject.data.pending_data.PendingDataViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class PendingDataFragment: BaseFragment() {

    private lateinit var pendingDataViewModel:PendingDataViewModel

    private val rootView:View by lazy { view!!.findViewById<View>(R.id.root) }

    private val group1:View  by lazy { view!!.findViewById<View>(R.id.group_1) }
    private val title1: TextView by lazy { view!!.findViewById<TextView>(R.id.title_1) }
    private val listView1:ExpandableListView  by lazy { view!!.findViewById<ExpandableListView>(R.id.listView_1) }

    private val group2:View  by lazy { view!!.findViewById<View>(R.id.group_2) }
    private val title2: TextView by lazy { view!!.findViewById<TextView>(R.id.title_2) }
    private val listView2:ExpandableListView  by lazy { view!!.findViewById<ExpandableListView>(R.id.listView_2) }

    private val group3:View  by lazy { view!!.findViewById<View>(R.id.group_3) }
    private val title3: TextView by lazy { view!!.findViewById<TextView>(R.id.title_3) }
    private val listView3:ExpandableListView  by lazy { view!!.findViewById<ExpandableListView>(R.id.listView_3) }

    private val group4:View  by lazy { view!!.findViewById<View>(R.id.group_4) }
    private val title4: TextView by lazy { view!!.findViewById<TextView>(R.id.title_4) }
    private val listView4:ExpandableListView  by lazy { view!!.findViewById<ExpandableListView>(R.id.listView_4) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pendingDataViewModel = ViewModelProviders.of(this).get(PendingDataViewModel::class.java)
        setHasOptionsMenu(true)
    }

    override fun onResume() {
        super.onResume()
        pendingDataViewModel.getPendingData().observe(this, pendingDataObserver)
    }

    override fun onStop() {
        super.onStop()
        pendingDataViewModel.getPendingData().removeObserver(pendingDataObserver)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_pending_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbarTitle(getString(R.string.menu_unupload_data))
        setToolbarLeftIcon(true)

    }

    override fun onCreateOptionsMenu(
        menu: Menu,
        inflater: MenuInflater
    ) {
        inflater.inflate(R.menu.menu_pending_data, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.upload -> {
                testUpload()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun testUpload() {
        GlobalScope.launch(Dispatchers.IO) {
            val pendingData:TreeMap<String, TreeMap<Case, ArrayList<Bio>>> = BioAction.getAllPendingData();
            Log.v("YuYu", pendingData.firstKey())
        }

    }

    private val pendingDataObserver =
        Observer<TreeMap<String, TreeMap<Case, java.util.ArrayList<Bio>>>>{
            //TODO 依照順序
            //1 日照
            for ((k, v) in it!!) {
                when(k){
                    //日照中心
                    SysKey.DailyCare.sysCode -> {
                        if(v.size == 0) {
                            group1.visibility = View.GONE
                        } else {
                            group1.visibility = View.VISIBLE
                            title1.text = SysKey.DailyCare.sysName
                        }
                    }

                    //居護
                    SysKey.DailyNursing.sysCode -> {
                        if(v.size == 0) {
                            group2.visibility = View.GONE
                        } else {
                            group2.visibility = View.VISIBLE
                            title2.text = SysKey.DailyCare.sysName
                        }
                    }

                    //活力站
                    SysKey.Station.sysCode -> {
                        if(v.size == 0) {
                            group3.visibility = View.GONE
                        } else {
                            group3.visibility = View.VISIBLE
                            title3.text = SysKey.DailyCare.sysName
                        }
                    }
                    //居護
                    SysKey.HomeCare.sysCode -> {
                        if(v.size == 0) {
                            group4.visibility = View.GONE
                        } else {
                            group4.visibility = View.VISIBLE
                            title4.text = SysKey.DailyCare.sysName
                        }
                    }
                }
            }
        }
}