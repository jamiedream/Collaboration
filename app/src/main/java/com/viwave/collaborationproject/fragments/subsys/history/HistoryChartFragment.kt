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
import com.viwave.collaborationproject.fragments.subsys.diagram.*
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragment = WeakReference(this)
        diagramView =
            when( bioViewModel.getSelectedType().value){
                BioLiveData.Companion.BioType.BloodGlucose -> DiagramGlucose(fragment)
                BioLiveData.Companion.BioType.Temperature -> DiagramTemperature(fragment)
                BioLiveData.Companion.BioType.Weight -> DiagramWeight(fragment)
//            BioLiveData.Companion.BioType.Respire -> R.layout.layout_history_respire_chart
//            BioLiveData.Companion.BioType.Height -> R.layout.layout_history_height_chart
                BioLiveData.Companion.BioType.Pulse -> DiagramPulse(fragment)
                BioLiveData.Companion.BioType.BloodPressure -> DiagramBloodPressure(fragment)
                BioLiveData.Companion.BioType.Oxygen -> DiagramOxygen(fragment)
                else -> DiagramPulse(fragment)
            }

        val markerDataObserver =
            Observer<Bio>{
                diagramView.setMarkerData(it)
            }
        bioViewModel.getMarkerData().observe(this, markerDataObserver)

    }

    override fun onResume() {
        super.onResume()
        diagramView.initView()
    }

    override fun onStop() {
        bioViewModel.getMarkerData().value = null
        super.onStop()
    }

}