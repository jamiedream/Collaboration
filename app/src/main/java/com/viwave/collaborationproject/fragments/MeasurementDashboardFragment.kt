package com.viwave.collaborationproject.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.viwave.collaborationproject.BackPressedDelegate
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.data.bios.BioType
import com.viwave.collaborationproject.data.cases.Case
import com.viwave.collaborationproject.utils.LogUtil

class MeasurementDashboardFragment(private val case: Case): BaseFragment(), BackPressedDelegate {

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
        return inflater.inflate(R.layout.fragment_case_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTempView(view)

    }

    override fun onResume() {
        super.onResume()
        setToolbarTitle(case.caseName)
        setToolbarLeftIcon(false)
    }

    private fun initTempView(view: View){
        val tempValue = view.findViewById<TextView>(R.id.value_temp)
        view.findViewById<CardView>(R.id.block_temp).setOnClickListener(
            object : View.OnClickListener{
                override fun onClick(v: View?) {
                    addFragment(this@MeasurementDashboardFragment, ManualInputFragment(BioType.Temperature), getString(R.string.tag_case_dashboard))
                }

            }
        )

    }

}