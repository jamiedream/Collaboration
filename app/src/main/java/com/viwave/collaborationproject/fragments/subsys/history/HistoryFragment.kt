/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/27/20 4:09 PM
 */

package com.viwave.collaborationproject.fragments.subsys.history

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.viwave.collaborationproject.DB.remote.entity.CaseEntity
import com.viwave.collaborationproject.FakeData.QueryData
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.data.DataSort
import com.viwave.collaborationproject.data.bios.BioLiveData
import com.viwave.collaborationproject.fragments.BaseFragment
import com.viwave.collaborationproject.fragments.subsys.caseList.CaseListFragment
import com.viwave.collaborationproject.fragments.subsys.caseList.CaseListFragment.Companion.bioViewModel
import com.viwave.collaborationproject.fragments.subsys.caseList.CaseListFragment.Companion.caseViewModel
import com.viwave.collaborationproject.utils.ConvertUtil

class HistoryFragment: BaseFragment(){

    private val TAG = this::class.java.simpleName

    private val caseGender by lazy { view!!.findViewById<ImageView>(R.id.case_gender) }
    private val caseName by lazy { view!!.findViewById<TextView>(R.id.case_name) }
    private val caseNumber by lazy { view!!.findViewById<TextView>(R.id.case_number) }

    private val CHART = "CHART"
    private val LIST = "LIST"
    private var PAGE = MutableLiveData<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        setHasOptionsMenu(true)
        return view
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        when(PAGE.value){
            LIST -> inflater.inflate(R.menu.menu_history_list, menu)
            CHART -> inflater.inflate(R.menu.menu_history_chart, menu)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId){
                    R.id.menu_history_chart -> {
                        PAGE.value = LIST
                        true
                    }
                    R.id.menu_history_list -> {
                        PAGE.value = CHART
                        true
                    }
                    else -> super.onOptionsItemSelected(item)
                }
    }

    private val pageObserver =
        Observer<String>{
            activity?.invalidateOptionsMenu()
            when(it){
                LIST -> replacePartialFragment(this, HistoryListFragment(), R.id.content_history, getString(R.string.tag_list))
                CHART -> replacePartialFragment(this, HistoryChartFragment(), R.id.content_history, getString(R.string.tag_chart))
            }
        }

    private val selectedCaseObserver =
        Observer<CaseEntity>{
            caseName.text = it.getCaseName
            caseNumber.text = it.getCaseNumber
            caseGender.setImageResource(
                when(it.getCaseGender){
                    CaseListFragment.FEMALE -> R.drawable.ic_gender_female
                    else -> R.drawable.ic_gender_male
                }
            )
        }

    private val selectedTypeObserver =
        Observer<BioLiveData.Companion.BioType>{
            when(it){
                BioLiveData.Companion.BioType.Temperature -> {
                    setToolbarTitle(getString(R.string.temperature))
                    bioViewModel.getTempListData().value =
                        ConvertUtil.sortListData(DataSort.temperatureList, QueryData().tempData)
                    bioViewModel.getTempListData().value?.sortBy { it.takenAt }
                }

                BioLiveData.Companion.BioType.Pulse -> {
                    setToolbarTitle(getString(R.string.pulse))
                    bioViewModel.getPulseListData().value =
                        ConvertUtil.sortListData(DataSort.pulseList, QueryData().pulseData)
                    bioViewModel.getPulseListData().value?.sortBy { it.takenAt }
                }

                BioLiveData.Companion.BioType.Weight -> {
                    setToolbarTitle(getString(R.string.weight))
                    bioViewModel.getWeightListData().value =
                        ConvertUtil.sortListData(DataSort.weightList, QueryData().weightData)
                    bioViewModel.getWeightListData().value?.sortBy { it.takenAt }
                }

                BioLiveData.Companion.BioType.BloodGlucose -> {
                    setToolbarTitle(getString(R.string.blood_glucose))
                    bioViewModel.getGlucoseListData().value =
                        ConvertUtil.sortListData(DataSort.glucoseList, QueryData().glucoseData)
                    bioViewModel.getGlucoseListData().value?.sortBy { it.takenAt }
                }

                BioLiveData.Companion.BioType.BloodPressure -> {
                    setToolbarTitle(getString(R.string.blood_pressure))
                    bioViewModel.getBPListData().value =
                        ConvertUtil.sortListData(DataSort.bloodPressureList, QueryData().bpData)
                    bioViewModel.getBPListData().value?.sortBy { it.takenAt }
                }

                BioLiveData.Companion.BioType.Oxygen -> {
                    setToolbarTitle(getString(R.string.oxygen))
                    bioViewModel.getOxygenListData().value =
                        ConvertUtil.sortListData(DataSort.oxygenList, QueryData().oxygenData)
                    bioViewModel.getOxygenListData().value?.sortBy { it.takenAt }
                }

                BioLiveData.Companion.BioType.Respire -> {
                    setToolbarTitle(getString(R.string.respire))
                    bioViewModel.getRespireListData().value =
                        ConvertUtil.sortListData(DataSort.respireList, QueryData().respireData)
                    bioViewModel.getRespireListData().value?.sortBy { it.takenAt }
                }

                BioLiveData.Companion.BioType.Height -> {
                    setToolbarTitle(getString(R.string.height))
                    bioViewModel.getHeightListData().value =
                        ConvertUtil.sortListData(DataSort.heightList, QueryData().heightData)
                    bioViewModel.getHeightListData().value?.sortBy { it.takenAt }
                }
            }

            when(PAGE.value){
                LIST -> replacePartialFragment(this, HistoryListFragment(), R.id.content_history, getString(R.string.tag_list))
                CHART -> replacePartialFragment(this, HistoryChartFragment(), R.id.content_history, getString(R.string.tag_chart))
            }

        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //init
        PAGE.value = LIST

        caseViewModel.getSelectedCase().observe(this, selectedCaseObserver)

        bioViewModel.getSelectedType().observe(this, selectedTypeObserver)

        PAGE.observe(this, pageObserver)

    }

    override fun onResume() {
        super.onResume()
        setToolbarLeftIcon(false)

    }

    override fun onStop() {
        PAGE.removeObserver(pageObserver)
        bioViewModel.getSelectedType().removeObserver(selectedTypeObserver)
        caseViewModel.getSelectedCase().removeObserver(selectedCaseObserver)
        super.onStop()
    }


}