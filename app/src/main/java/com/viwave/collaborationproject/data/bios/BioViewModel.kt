package com.viwave.collaborationproject.data.bios

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BioViewModel: ViewModel() {

    private var selectedType: MutableLiveData<BioLiveData.Companion.BioType>? = null
    fun getSelectedType(): MutableLiveData<BioLiveData.Companion.BioType> {
        if(selectedType == null){
            selectedType = BioLiveData().getSelectedType()
        }
        return selectedType!!
    }

    private var demoTempData: MutableLiveData<Float>? = null
    fun getDemoTempData(): MutableLiveData<Float> {
        if(demoTempData == null){
            demoTempData = BioLiveData().demoTempData()
        }
        return demoTempData!!
    }
}