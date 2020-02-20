package com.viwave.collaborationproject.data.cases

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CaseViewModel: ViewModel() {

    private var selectedCase: MutableLiveData<Case>? = null
    fun getSelectedCase(): MutableLiveData<Case>{
        if(selectedCase == null){
            selectedCase = CaseLiveData().getSelectedCase()
        }
        return selectedCase!!
    }


}