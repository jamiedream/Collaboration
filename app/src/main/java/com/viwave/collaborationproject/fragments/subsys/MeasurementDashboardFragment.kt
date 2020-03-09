package com.viwave.collaborationproject.fragments.subsys

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import com.viwave.collaborationproject.BackPressedDelegate
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.data.bios.BioLiveData
import com.viwave.collaborationproject.data.cases.Case
import com.viwave.collaborationproject.fragments.BaseFragment
import com.viwave.collaborationproject.fragments.subsys.caseList.CaseListFragment.Companion.bioViewModel
import com.viwave.collaborationproject.fragments.subsys.caseList.CaseListFragment.Companion.caseViewModel

class MeasurementDashboardFragment(): BaseFragment(), BackPressedDelegate {

    override fun onBackPressed(): Boolean {
        fragmentManager?.popBackStack()
        return true
    }

    private val TAG = this::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_case_dashboard, container, false)
        setHasOptionsMenu(true)
        return view
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val item = menu.findItem(R.id.dashboard_no)
        caseViewModel.getSelectedCase().value?.let {
            item.setTitle(it.caseNumber)
        }
        super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_dashboard_no, menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTempView(view)
        initBloodGlucose(view)
        initWeight(view)
        initRespire(view)
        initHeight(view)
        initPulse(view)
        initBloodPressure(view)
        initOxygen(view)

    }

    private val selectedCaseObserver =
        Observer<Case>{
            setToolbarTitle(it.caseName)
            setToolbarLeftIcon(false)
        }

    override fun onResume() {
        super.onResume()
        caseViewModel.getSelectedCase().observe(this, selectedCaseObserver)
    }

    override fun onStop() {
        caseViewModel.getSelectedCase().removeObserver(selectedCaseObserver)
        super.onStop()
    }

    private fun initTempView(view: View){

        view.findViewById<ImageView>(R.id.add_temp).setOnClickListener {
            bioViewModel.getSelectedType().value = BioLiveData.Companion.BioType.Temperature
            intentToManualInput()
        }

        view.findViewById<CardView>(R.id.block_temp).setOnClickListener {
            //todo, to data(diagram or list)
        }

    }

    private fun initBloodGlucose(view: View){

        view.findViewById<ImageView>(R.id.add_blood_glucose).setOnClickListener {
            bioViewModel.getSelectedType().value = BioLiveData.Companion.BioType.BloodGlucose
            intentToManualInput()
        }

    }

    private fun initWeight(view: View){

        view.findViewById<ImageView>(R.id.add_weight).setOnClickListener {
            bioViewModel.getSelectedType().value = BioLiveData.Companion.BioType.Weight
            intentToManualInput()
        }
    }

    private fun initRespire(view: View){

        view.findViewById<ImageView>(R.id.add_respire).setOnClickListener {
            bioViewModel.getSelectedType().value = BioLiveData.Companion.BioType.Respire
            intentToManualInput()
        }

    }

    private fun initHeight(view: View){

        view.findViewById<ImageView>(R.id.add_height).setOnClickListener {
            bioViewModel.getSelectedType().value = BioLiveData.Companion.BioType.Height
            intentToManualInput()
        }
    }

    private fun initPulse(view: View){

        view.findViewById<ImageView>(R.id.add_pulse).setOnClickListener {
            bioViewModel.getSelectedType().value = BioLiveData.Companion.BioType.Pulse
            intentToManualInput()
        }

    }

    private fun initBloodPressure(view: View){

        view.findViewById<ImageView>(R.id.add_blood_pressure).setOnClickListener {
            bioViewModel.getSelectedType().value = BioLiveData.Companion.BioType.BloodPressure
            intentToManualInput()
        }

    }

    private fun initOxygen(view: View){

        view.findViewById<ImageView>(R.id.add_oxygen).setOnClickListener {
            bioViewModel.getSelectedType().value = BioLiveData.Companion.BioType.Oxygen
            intentToManualInput()
        }

    }

    private fun intentToManualInput(){
        replaceFragment(this@MeasurementDashboardFragment, ManualInputFragment(), getString(R.string.tag_case_manual))
    }
}