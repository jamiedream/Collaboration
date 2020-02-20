package com.viwave.collaborationproject.data.bios

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BioViewModel: ViewModel() {

    private var selectedType: MutableLiveData<BioLiveData.Companion.BioType>? = null
    fun getSelectedCase(): MutableLiveData<BioLiveData.Companion.BioType> {
        if(selectedType == null){
            selectedType = BioLiveData().getSelectedType()
        }
        return selectedType!!
    }
}