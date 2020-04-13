/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/25/20 1:49 PM
 */

package com.viwave.collaborationproject.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.viwave.collaborationproject.DB.cache.SysKey
import com.viwave.collaborationproject.DB.remote.BioAction
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.data.bios.Bio
import com.viwave.collaborationproject.data.cases.Case
import com.viwave.collaborationproject.data.pending_data.PendingDataViewModel
import com.viwave.collaborationproject.fragments.subsys.caseList.CaseListFragment.Companion.FEMALE
import com.viwave.collaborationproject.fragments.widgets.MeasurementItemLayout
import com.viwave.collaborationproject.utils.DateUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class PendingDataFragment: BaseFragment() {

    companion object {
        //體溫 脈搏 呼吸 血壓 血糖 身高 體重 血氧
        const val TYPE_TEMPERATURE:Int = 0
        const val TYPE_PULSE:Int = 1
        const val TYPE_RESPIRE:Int = 2
        const val TYPE_BLOOD_PRESSURE:Int = 3
        const val TYPE_BLOOD_GLUCOSE:Int = 4
        const val TYPE_HEIGHT:Int = 5
        const val TYPE_WEIGHT:Int = 6
        const val TYPE_OXYGEN:Int = 7
    }

    private lateinit var pendingDataViewModel:PendingDataViewModel

    private lateinit var inflater: LayoutInflater

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
        this.inflater = inflater
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
                val adapter:CaseBioAdapter = CaseBioAdapter(v)
                when(k){
                    //日照中心
                    SysKey.DailyCare.sysCode -> {
                        if(v.size == 0) {
                            group1.visibility = View.GONE
                        } else {
                            group1.visibility = View.VISIBLE
                            title1.text = SysKey.DailyCare.sysName
                            listView1.setAdapter(adapter)
                        }
                    }

                    //居護
                    SysKey.DailyNursing.sysCode -> {
                        if(v.size == 0) {
                            group2.visibility = View.GONE
                        } else {
                            group2.visibility = View.VISIBLE
                            title2.text = SysKey.DailyCare.sysName
                            listView2.setAdapter(adapter)
                        }
                    }

                    //活力站
                    SysKey.Station.sysCode -> {
                        if(v.size == 0) {
                            group3.visibility = View.GONE
                        } else {
                            group3.visibility = View.VISIBLE
                            title3.text = SysKey.DailyCare.sysName
                            listView3.setAdapter(adapter)
                        }
                    }
                    //居護
                    SysKey.HomeCare.sysCode -> {
                        if(v.size == 0) {
                            group4.visibility = View.GONE
                        } else {
                            group4.visibility = View.VISIBLE
                            title4.text = SysKey.DailyCare.sysName
                            listView4.setAdapter(adapter)
                        }
                    }
                }
            }
        }

    private inner class CaseBioAdapter(val dataMap:TreeMap<Case, ArrayList<Bio>>): BaseExpandableListAdapter() {

        override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
            return false
        }

        override fun hasStableIds(): Boolean {
            return true
        }

        override fun getGroupId(groupPosition: Int): Long {
            return groupPosition.toLong()
        }

        override fun getChildId(groupPosition: Int, childPosition: Int): Long {
            return childPosition.toLong()
        }

        override fun getGroupCount(): Int {
            return dataMap.size
        }

        override fun getChildrenCount(groupPosition: Int): Int {
            val key:Case = ArrayList(dataMap.keys)[groupPosition]
            return dataMap[key]!!.size
        }

        override fun getGroup(groupPosition: Int): Any {
            return ArrayList(dataMap.keys)[groupPosition]
        }

        override fun getChild(groupPosition: Int, childPosition: Int): Any {
            val key:Case = ArrayList(dataMap.keys)[groupPosition]
            return dataMap[key]!![childPosition]
        }

        override fun getGroupView(groupPosition: Int, b: Boolean, view: View?, viewGroup: ViewGroup?): View {
            var holder:GroupViewHolder? = null
            var currentView = view
            if(currentView == null) {
                currentView = inflater.inflate(R.layout.layout_pending_data_case, viewGroup, false)
                holder = GroupViewHolder();
                holder.igGender = currentView!!.findViewById(R.id.imgGender)
                holder.tvName = currentView.findViewById(R.id.name)
                holder.tvCaseNo = currentView.findViewById(R.id.caseNo)

                currentView.tag = holder
            } else {
                holder = currentView.tag as GroupViewHolder?
            }
            val case:Case = getGroup(groupPosition) as Case
            holder!!.igGender.setImageResource(
                when(case.caseGender == FEMALE){
                    true -> R.drawable.ic_gender_female
                    else -> R.drawable.ic_gender_male
                }
            )

            holder.tvName.text = case.caseName
            holder.tvCaseNo.text = case.caseNumber

            return currentView
        }

        override fun getChildView(groupPosition: Int, childPosition: Int, b: Boolean, view: View?, viewGroup: ViewGroup?): View {
            var currentView:View? = view
            var holder:ChildViewHolder? = null
            val childType:Int = getChildType(groupPosition, childPosition)
            if(currentView == null) {
                when(childType) {
                    TYPE_BLOOD_GLUCOSE ->
                        currentView = inflater.inflate(R.layout.layout_pending_blood_glucose, viewGroup, false)
                    TYPE_BLOOD_PRESSURE ->
                            currentView = inflater.inflate(R.layout.layout_pending_blood_pressure, viewGroup, false)
                    TYPE_HEIGHT ->
                        currentView = inflater.inflate(R.layout.layout_pending_height, viewGroup, false)
                    TYPE_OXYGEN ->
                        currentView = inflater.inflate(R.layout.layout_pending_oxygen, viewGroup, false)
                    TYPE_PULSE ->
                        currentView = inflater.inflate(R.layout.layout_pending_pulse, viewGroup, false)
                    TYPE_RESPIRE ->
                        currentView = inflater.inflate(R.layout.layout_pending_respire, viewGroup, false)
                    TYPE_TEMPERATURE ->
                        currentView = inflater.inflate(R.layout.layout_pending_blood_pressure, viewGroup, false)
                    TYPE_WEIGHT ->
                        currentView = inflater.inflate(R.layout.layout_pending_weight, viewGroup, false)
                }

                holder = ChildViewHolder()
                holder.measurementData = currentView!!.findViewById(R.id.measureData)
                holder.time = currentView.findViewById(R.id.time)
                currentView.tag = holder
            } else {
                holder = currentView.tag as ChildViewHolder
            }

            val bio:Bio = getChild(groupPosition, childPosition) as Bio
            when(childType) {
                TYPE_BLOOD_GLUCOSE -> {
                    val data = bio as Bio.BloodGlucose
                    holder.measurementData.setValue(data.glucose)
                    holder.time.text = DateUtil.getPendingDataTime(data.takenAt)
                }
                TYPE_BLOOD_PRESSURE -> {
                    val data = bio as Bio.BloodPressure
                    holder.measurementData.setValues(data.sys, data.dia)
                    holder.time.text = DateUtil.getPendingDataTime(data.takenAt)
                }
                TYPE_HEIGHT -> {
                    val data = bio as Bio.Height
                    holder.measurementData.setValue(data.height)
                    holder.time.text = DateUtil.getPendingDataTime(data.takenAt)
                }
                TYPE_OXYGEN -> {
                    val data = bio as Bio.Oxygen
                    //TODO 如何顯示
                    holder.measurementData.setValues(data.spo2Highest, data.spo2Lowest)
                    holder.time.text = DateUtil.getPendingDataTime(data.takenAt)
                }
                TYPE_PULSE -> {
                    val data = bio as Bio.Pulse
                    holder.measurementData.setValue(data.pulse)
                    holder.time.text = DateUtil.getPendingDataTime(data.takenAt)
                }
                TYPE_RESPIRE -> {
                    val data = bio as Bio.Respire
                    holder.measurementData.setValue(data.respire)
                    holder.time.text = DateUtil.getPendingDataTime(data.takenAt)
                }
                TYPE_TEMPERATURE -> {
                    val data = bio as Bio.Temperature
                    holder.measurementData.setValue(data.temperature)
                    holder.time.text = DateUtil.getPendingDataTime(data.takenAt)
                }
                TYPE_WEIGHT -> {
                    val data = bio as Bio.Weight
                    holder.measurementData.setValue(data.weight)
                    holder.time.text = DateUtil.getPendingDataTime(data.takenAt)
                }
            }

            return currentView
        }

        override fun getChildType(groupPosition: Int, childPosition: Int): Int {
            //體溫 脈搏 呼吸 血壓 血糖 身高 體重 血氧
            return when(getChild(groupPosition, childPosition) as Bio) {
                is Bio.Temperature -> TYPE_TEMPERATURE
                is Bio.Pulse -> TYPE_PULSE
                is Bio.Respire -> TYPE_RESPIRE
                is Bio.BloodPressure -> TYPE_BLOOD_PRESSURE
                is Bio.BloodGlucose -> TYPE_BLOOD_GLUCOSE
                is Bio.Height -> TYPE_HEIGHT
                is Bio.Weight -> TYPE_WEIGHT
                is Bio.Oxygen -> TYPE_OXYGEN
            }
        }

        override fun getChildTypeCount(): Int {
            //體溫 脈搏 呼吸 血壓 血糖 身高 體重 血氧
            return 8
        }
    }

    private class GroupViewHolder {
        lateinit var igGender: ImageView
        lateinit var tvName: TextView
        lateinit var tvCaseNo: TextView
    }

    private class ChildViewHolder {
        lateinit var measurementData: MeasurementItemLayout
        lateinit var time:TextView
    }
}