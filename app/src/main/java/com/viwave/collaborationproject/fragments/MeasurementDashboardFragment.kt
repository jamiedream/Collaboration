package com.viwave.collaborationproject.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.viwave.collaborationproject.BackPressedDelegate
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.data.bios.BioLiveData
import com.viwave.collaborationproject.fragments.mainList.MainListFragment
import com.viwave.collaborationproject.fragments.mainList.MainListFragment.Companion.caseViewModel

class MeasurementDashboardFragment(): BaseFragment(), BackPressedDelegate {

    override fun onBackPressed(): Boolean {
        replaceFragment(this@MeasurementDashboardFragment, MainListFragment(), getString(R.string.tag_case_list))
//        fragmentManager?.popBackStack()
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
        val tempValue = view.findViewById<TextView>(R.id.value_temp)
        view.findViewById<CardView>(R.id.block_temp).setOnClickListener(
            object : View.OnClickListener{
                override fun onClick(v: View?) {
                    replaceFragment(this@MeasurementDashboardFragment, ManualInputFragment(
                        BioLiveData.Companion.BioType.Temperature), getString(R.string.tag_case_manual))
//                    addFragment(this@MeasurementDashboardFragment, ManualInputFragment(BioLiveData.Companion.BioType.Temperature), getString(R.string.tag_case_dashboard))
                }

            }
        )

    }

}