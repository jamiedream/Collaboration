/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/25/20 1:49 PM
 */

package com.viwave.collaborationproject.fragments.subsys.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.data.bios.BioLiveData
import com.viwave.collaborationproject.fragments.BaseFragment
import com.viwave.collaborationproject.fragments.subsys.caseList.CaseListFragment.Companion.bioViewModel
import com.viwave.collaborationproject.fragments.subsys.history.adapter.HistoryListTypeAdapter

class HistoryListFragment: BaseFragment() {

    private val TAG = this::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        replacePartialFragment(this, HistoryListHeaderFragment(), R.id.list_header, getString(R.string.tag_list_header))
        val historyListAdapter =
            HistoryListTypeAdapter(
                bioViewModel.getSelectedType().value?.let {
                    when(it){
                        BioLiveData.Companion.BioType.Temperature -> bioViewModel.getTempListData().value
                        BioLiveData.Companion.BioType.Respire -> bioViewModel.getRespireListData().value
                        BioLiveData.Companion.BioType.Pulse -> bioViewModel.getPulseListData().value
                        BioLiveData.Companion.BioType.BloodPressure -> bioViewModel.getBPListData().value
                        BioLiveData.Companion.BioType.Weight -> bioViewModel.getWeightListData().value
                        BioLiveData.Companion.BioType.Height -> bioViewModel.getHeightListData().value
                        BioLiveData.Companion.BioType.Oxygen -> bioViewModel.getOxygenListData().value
                        BioLiveData.Companion.BioType.BloodGlucose -> bioViewModel.getGlucoseListData().value
                    }
                }
            )

        view.findViewById<RecyclerView>(R.id.cmn_recycler)?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = historyListAdapter
            val decorator =
                DividerItemDecoration(
                    context,
                    LinearLayoutManager(context).orientation
                )
            decorator.setDrawable(resources.getDrawable(R.drawable.line_case_list, null))
            addItemDecoration(decorator)
        }
    }

    override fun onResume() {
        super.onResume()
    }
}