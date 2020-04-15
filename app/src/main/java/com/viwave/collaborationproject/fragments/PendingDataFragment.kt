package com.viwave.collaborationproject.fragments

import android.media.Image
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
        const val GROUP_TYPE_TITLE:Int = 0;
        const val GROUP_TYPE_CASE:Int = 1;

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
    private lateinit var adapter:CaseBioAdapter

    private val listView:ExpandableListView  by lazy { view!!.findViewById<ExpandableListView>(R.id.listView) }

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
            adapter = CaseBioAdapter(it)
            listView.setAdapter(adapter)
        }

    //用同一個 Expendable ListView  處理 title 和 Case
    //title 也為 Group，不可展開
    private inner class CaseBioAdapter(val dataMap:TreeMap<String, TreeMap<Case, java.util.ArrayList<Bio>>>): BaseExpandableListAdapter() {
        private var dailyCareIndex:Int = -1  //日照  S03
        private var dailyNurseIndex:Int = -1 //居護  S05
        private var dailyStationIndex:Int = -1  //活力站  S26
        private var dailyHomeCareIndex:Int = -1  //居服  S01
        init{
            var index:Int = -1
            //計算四個 title 在 group 的排序
            var caseMap = dataMap[SysKey.DAILY_CARE_CODE]

            if(caseMap != null && caseMap.size > 0) {
                index++
                dailyCareIndex = index
                index += caseMap.size
            }

            caseMap = dataMap[SysKey.DAILY_NURSING_CODE]
            if(caseMap != null && caseMap.size > 0) {
                index++
                dailyNurseIndex = index
                index += caseMap.size
            }

            caseMap = dataMap[SysKey.DAILY_STATION_CODE]
            if(caseMap != null && caseMap.size > 0) {
                index++
                dailyStationIndex = index
                index += caseMap.size
            }

            caseMap = dataMap[SysKey.DAILY_HOME_CARE_CODE]
            if(caseMap != null && caseMap.size > 0) {
                index++
                dailyHomeCareIndex = index
                index += caseMap.size
            }
        }

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
            var size:Int = 0
            for((key, value) in dataMap){

                if(value.size == 0) {
                    continue
                } else {
                    size++
                    size += value.size
                }
            }

            return size
        }

        override fun getChildrenCount(groupPosition: Int): Int {
            when(groupPosition){
                dailyCareIndex -> return 0
                dailyNurseIndex -> return 0
                dailyStationIndex -> return 0
                dailyHomeCareIndex -> return 0

                //照順序 Care, Nurse, Station, Home
                in dailyCareIndex..dailyNurseIndex -> {
                    val caseMap = dataMap[SysKey.DAILY_CARE_CODE]
                    if(caseMap != null) {
                        val case = ArrayList(caseMap.keys)[groupPosition-dailyCareIndex-1]
                        return caseMap[case]!!.size
                    }
                }
                in dailyNurseIndex..dailyStationIndex -> {
                    val caseMap = dataMap[SysKey.DAILY_NURSING_CODE]
                    if(caseMap != null) {
                        val case = ArrayList(caseMap.keys)[groupPosition-dailyNurseIndex-1]
                        return caseMap[case]!!.size
                    }
                }
                in dailyStationIndex..dailyHomeCareIndex -> {
                    val caseMap = dataMap[SysKey.DAILY_STATION_CODE]
                    if(caseMap != null) {
                        val case = ArrayList(caseMap.keys)[groupPosition-dailyStationIndex-1]
                        return caseMap[case]!!.size
                    }
                }
                else -> {
                    val caseMap = dataMap[SysKey.DAILY_HOME_CARE_CODE]
                    if(caseMap != null) {
                        val case = ArrayList(caseMap.keys)[groupPosition-dailyHomeCareIndex-1]
                        return caseMap[case]!!.size
                    }
                }
            }

            //應該不會發生
            return 0
        }

        override fun getGroup(groupPosition: Int): Any {
            when(groupPosition){
                dailyCareIndex -> return getString(R.string.sys_daily_care)
                dailyNurseIndex -> return getString(R.string.sys_daily_nursing)
                dailyStationIndex -> return getString(R.string.sys_station)
                dailyHomeCareIndex -> return getString(R.string.sys_home_service)

                //照順序 Care, Nurse, Station, Home
                in dailyCareIndex..dailyNurseIndex -> {
                    val childMap = dataMap[SysKey.DAILY_CARE_CODE]
                    if(childMap != null) {
                        return ArrayList(childMap.keys)[groupPosition-dailyCareIndex-1]
                    }
                }
                in dailyNurseIndex..dailyStationIndex -> {
                    val childMap = dataMap[SysKey.DAILY_NURSING_CODE]
                    if(childMap != null) {
                        return ArrayList(childMap.keys)[groupPosition-dailyNurseIndex-1]
                    }
                }
                in dailyStationIndex..dailyHomeCareIndex -> {
                    val childMap = dataMap[SysKey.DAILY_STATION_CODE]
                    if(childMap != null) {
                        return ArrayList(childMap.keys)[groupPosition-dailyStationIndex-1]
                    }
                }
                else -> {
                    val childMap = dataMap[SysKey.DAILY_HOME_CARE_CODE]
                    if(childMap != null) {
                        return ArrayList(childMap.keys)[groupPosition-dailyHomeCareIndex-1]
                    }
                }
            }

            //應該不會發生
            return String()
        }

        override fun getChild(groupPosition: Int, childPosition: Int): Any {
            when(groupPosition) {
                dailyCareIndex -> return 0
                dailyNurseIndex -> return 0
                dailyStationIndex -> return 0
                dailyHomeCareIndex -> return 0

                //照順序 Care, Nurse, Station, Home
                in dailyCareIndex..dailyNurseIndex -> {
                    val caseMap = dataMap[SysKey.DAILY_CARE_CODE]
                    if(caseMap != null) {
                        val case = ArrayList(caseMap.keys)[groupPosition-dailyCareIndex-1]
                        return caseMap[case]!![childPosition]
                    }
                }
                in dailyNurseIndex..dailyStationIndex -> {
                    val caseMap = dataMap[SysKey.DAILY_NURSING_CODE]
                    if(caseMap != null) {
                        val case = ArrayList(caseMap.keys)[groupPosition-dailyNurseIndex-1]
                        return caseMap[case]!![childPosition]
                    }
                }
                in dailyStationIndex..dailyHomeCareIndex -> {
                    val caseMap = dataMap[SysKey.DAILY_STATION_CODE]
                    if(caseMap != null) {
                        val case = ArrayList(caseMap.keys)[groupPosition-dailyStationIndex-1]
                        return caseMap[case]!![childPosition]
                    }
                }
                else -> {
                    val caseMap = dataMap[SysKey.DAILY_HOME_CARE_CODE]
                    if(caseMap != null) {
                        val case = ArrayList(caseMap.keys)[groupPosition-dailyHomeCareIndex-1]
                        return caseMap[case]!![childPosition]
                    }
                }
            }

            //應該不會發生
            return String()
        }

        override fun getGroupView(groupPosition: Int, isExpanded: Boolean, view: View?, viewGroup: ViewGroup?): View {
            val type = getGroupType(groupPosition)
            var holder:GroupViewHolder? = null
            var currentView = view

            if(currentView == null) {

                when(type) {
                    GROUP_TYPE_TITLE -> {
                        currentView = inflater.inflate(R.layout.layout_pending_data_title, viewGroup, false)
                        holder = GroupTitleViewHolder()
                        holder.tvTitle = currentView!!.findViewById(R.id.title)
                    }
                    else -> {
                        currentView = inflater.inflate(R.layout.layout_pending_data_case, viewGroup, false)
                        holder = GroupCaseViewHolder()
                        holder.indicator = currentView!!.findViewById(R.id.groupIndicator)
                        holder.igGender = currentView.findViewById(R.id.imgGender)
                        holder.tvName = currentView.findViewById(R.id.name)
                        holder.tvCaseNo = currentView.findViewById(R.id.caseNo)
                    }
                }
                currentView.tag = holder
            } else {
                holder = currentView.tag as GroupViewHolder?
            }
            when(type) {
                GROUP_TYPE_TITLE -> {
                    val title = getGroup(groupPosition) as String
                    holder as GroupTitleViewHolder
                    holder.tvTitle.text = title
                }
                else -> {
                    val case:Case = getGroup(groupPosition) as Case
                    holder as GroupCaseViewHolder
                    if(isExpanded) {
                        holder.indicator.setImageResource(R.drawable.ic_expand_open)
                    } else {
                        holder.indicator.setImageResource(R.drawable.ic_expand_close)
                    }
                    holder.igGender.setImageResource(
                        when(case.caseGender == FEMALE){
                            true -> R.drawable.ic_gender_female
                            else -> R.drawable.ic_gender_male
                        }
                    )

                    holder.tvName.text = case.caseName
                    holder.tvCaseNo.text = case.caseNumber

                }
            }

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

//                titleMeasure.setTextColor(ContextCompat.getColor(context, R.color.storm_dust))
//                unitMeasure.setTextColor(ContextCompat.getColor(context, R.color.storm_dust))
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

        override fun getGroupType(groupPosition: Int): Int {
            return when(getGroup(groupPosition)){
                is String -> GROUP_TYPE_TITLE
                else -> GROUP_TYPE_CASE
            }
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

        override fun getGroupTypeCount(): Int {
            //Title, Case
            return 2
        }
    }

    private open class GroupViewHolder{}

    private class GroupCaseViewHolder:GroupViewHolder() {
        lateinit var indicator: ImageView
        lateinit var igGender: ImageView
        lateinit var tvName: TextView
        lateinit var tvCaseNo: TextView
    }

    private class GroupTitleViewHolder:GroupViewHolder() {
        lateinit var tvTitle: TextView
    }

    private class ChildViewHolder {
        lateinit var measurementData: MeasurementItemLayout
        lateinit var time:TextView
    }
}