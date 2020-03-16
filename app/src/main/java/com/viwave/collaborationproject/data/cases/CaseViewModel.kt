package com.viwave.collaborationproject.data.cases

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.viwave.collaborationproject.DB.remote.entity.CaseEntity

class CaseViewModel: ViewModel() {

    private var selectedCase: MutableLiveData<CaseEntity>? = null
    fun getSelectedCase(): MutableLiveData<CaseEntity>{
        if(selectedCase == null){
            selectedCase = CaseLiveData().getSelectedCase()
        }
        return selectedCase!!
    }


}