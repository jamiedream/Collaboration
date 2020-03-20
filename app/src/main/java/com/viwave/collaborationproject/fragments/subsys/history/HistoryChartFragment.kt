package com.viwave.collaborationproject.fragments.subsys.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.data.bios.Bio
import com.viwave.collaborationproject.data.bios.BioLiveData
import com.viwave.collaborationproject.fragments.BaseFragment
import com.viwave.collaborationproject.fragments.subsys.caseList.CaseListFragment.Companion.bioViewModel
import com.viwave.collaborationproject.fragments.subsys.diagram.DiagramView
import java.lang.ref.WeakReference

class HistoryChartFragment: BaseFragment() {

    private val TAG = this::class.java.simpleName
    private lateinit var diagramView: DiagramView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(bioViewModel.getSelectedTypeHistoryLayout().value?: R.layout.layout_history_temperature_chart, container, false)
    }

    private val selectedTypeObserver =
        Observer<BioLiveData.Companion.BioType>{
            diagramView = bioViewModel.selectedHistoryDiagram(WeakReference(this))
            diagramView.initView()
        }


    private val markerDataObserver =
        Observer<Bio>{
            diagramView.setMarkerData(it)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bioViewModel.getSelectedType().observe(this, selectedTypeObserver)
    }

    override fun onResume() {
        super.onResume()
        bioViewModel.getMarkerData().observe(this, markerDataObserver)
        diagramView.initView()
    }

    override fun onStop() {
        bioViewModel.getMarkerData().value = null
        bioViewModel.getSelectedType().removeObserver(selectedTypeObserver)
        bioViewModel.getMarkerData().removeObserver(markerDataObserver)
        super.onStop()
    }

}