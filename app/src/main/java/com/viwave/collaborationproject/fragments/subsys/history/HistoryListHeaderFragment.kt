/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 9/ 4/ 2020.
 * Last modified 4/9/20 1:03 PM
 */

package com.viwave.collaborationproject.fragments.subsys.history

import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.data.bios.BioLiveData
import com.viwave.collaborationproject.fragments.BaseFragment
import com.viwave.collaborationproject.fragments.subsys.caseList.CaseListFragment.Companion.bioViewModel

class HistoryListHeaderFragment: BaseFragment(){

    private val TAG = this::class.java.simpleName

    private val unit by lazy { view!!.findViewById<TextView>(R.id.type_unit) }
    private val unitOne by lazy { view!!.findViewById<TextView>(R.id.type_unit1) }
    private val unitTwo by lazy { view!!.findViewById<TextView>(R.id.type_unit2) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(bioViewModel.getSelectedTypeHistoryHeaderLayout().value?: R.layout.layout_history_list_header, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bioViewModel.getSelectedType().value?.let {
            when(it){
                BioLiveData.Companion.BioType.Temperature -> unit.text = getString(R.string.unit_celsius)
                BioLiveData.Companion.BioType.Respire -> unit.text = getString(R.string.unit_respire)
                BioLiveData.Companion.BioType.Pulse -> unit.text = getString(R.string.unit_pulse)
                BioLiveData.Companion.BioType.BloodPressure -> {
                    strWithSub(unit, getString(R.string.systolic_diastolic), getString(R.string.unit_blood_pressure))
                }
                BioLiveData.Companion.BioType.Weight -> unit.text = getString(R.string.unit_blood_pressure)
                BioLiveData.Companion.BioType.Height -> unit.text = getString(R.string.unit_height)
                BioLiveData.Companion.BioType.Oxygen -> {
                    strWithSub(unitOne, getString(R.string.oxygen_saturation), getString(R.string.percentage))
                    strWithSub(unitTwo, getString(R.string.pulse), getString(R.string.unit_pulse))
                }
                BioLiveData.Companion.BioType.BloodGlucose -> {
                    unitOne.text = getString(R.string.unit_blood_glucose)
                    unitTwo.text = getString(R.string.meal)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

    }

    private fun strWithSub(textView: TextView, str: String, subStr: String){
        textView.text =
            SpannableString("$str\n$subStr").let {
                it.setSpan(AbsoluteSizeSpan(16, true), 0, str.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                it.setSpan(StyleSpan(Typeface.BOLD), 0, str.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                it.setSpan(AbsoluteSizeSpan(12, true), str.length, str.length.plus(subStr.length).plus(1), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                it.setSpan(StyleSpan(Typeface.NORMAL), str.length, str.length.plus(subStr.length).plus(1), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                it
            }
    }
}