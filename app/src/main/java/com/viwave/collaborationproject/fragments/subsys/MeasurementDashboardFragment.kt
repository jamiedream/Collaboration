package com.viwave.collaborationproject.fragments.subsys

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import com.viwave.collaborationproject.BackPressedDelegate
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.data.bios.BioLiveData
import com.viwave.collaborationproject.fragments.BaseFragment
import com.viwave.collaborationproject.fragments.subsys.caseList.CaseListFragment
import com.viwave.collaborationproject.fragments.subsys.caseList.CaseListFragment.Companion.bioViewModel
import com.viwave.collaborationproject.fragments.subsys.caseList.CaseListFragment.Companion.caseViewModel
import com.viwave.collaborationproject.fragments.widgets.MeasurementItemLayout
import com.viwave.collaborationproject.utils.DataFormatUtil

class MeasurementDashboardFragment(): BaseFragment(), BackPressedDelegate {

    override fun onBackPressed(): Boolean {
        replaceFragment(this@MeasurementDashboardFragment, CaseListFragment(), getString(R.string.tag_case_list))
        return true
    }

    private val TAG = this::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_case_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTempView(view)

    }

    override fun onResume() {
        super.onResume()
        caseViewModel.getSelectedCase().value?.let {
            setToolbarTitle(it.caseName)
        }
        setToolbarLeftIcon(false)
    }

    private fun initTempView(view: View){

        val lastTempDataObserver = Observer<Float> {
            view.findViewById<MeasurementItemLayout>(R.id.value_temp).setValue(DataFormatUtil.formatString(it))
        }
        bioViewModel.getDemoTempData().observe(this, lastTempDataObserver)

        view.findViewById<ImageView>(R.id.add_temp).setOnClickListener {
            replaceFragment(this@MeasurementDashboardFragment,
                ManualInputFragment(
                    BioLiveData.Companion.BioType.Temperature
                ), getString(R.string.tag_case_manual))
        }

        view.findViewById<CardView>(R.id.block_temp).setOnClickListener {
            //todo, to data(diagram or list)
        }

    }

}