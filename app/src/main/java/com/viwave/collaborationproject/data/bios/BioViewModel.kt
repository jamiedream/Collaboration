package com.viwave.collaborationproject.data.bios

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.viwave.collaborationproject.R

open class BioViewModel: ViewModel() {

    private var selectedType: MutableLiveData<BioLiveData.Companion.BioType>? = null
    fun getSelectedType(): MutableLiveData<BioLiveData.Companion.BioType> {
        if(selectedType == null){
            selectedType = BioLiveData().getSelectedType()
        }
        return selectedType!!
    }

    private var selectedTypeManualLayout = MutableLiveData<Int>()
    fun getSelectedTypeManualLayout(): MutableLiveData<Int> {
        selectedTypeManualLayout.value =
            when(selectedType?.value){
                BioLiveData.Companion.BioType.BloodGlucose -> R.layout.layout_manual_blood_glucose
                BioLiveData.Companion.BioType.Temperature -> R.layout.layout_manual_temp
                else -> 0
            }
        return selectedTypeManualLayout
    }

    private var demoTempData: MutableLiveData<Float>? = null
    fun getDemoTempData(): MutableLiveData<Float> {
        if(demoTempData == null){
            demoTempData = BioLiveData().demoTempData()
        }
        return demoTempData!!
    }

    private var demoGlucoseData: MutableLiveData<Int>? = null
    fun getDemoGlucoseData(): MutableLiveData<Int> {
        if(demoGlucoseData == null){
            demoGlucoseData = BioLiveData().demoGlucoseData()
        }
        return demoGlucoseData!!
    }
}