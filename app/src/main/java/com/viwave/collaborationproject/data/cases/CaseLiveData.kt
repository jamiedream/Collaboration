package com.viwave.collaborationproject.data.cases

import androidx.lifecycle.MutableLiveData
import com.viwave.collaborationproject.DB.remote.entity.CaseEntity

class CaseLiveData {

    private val selectedCase = MutableLiveData<CaseEntity>()
    internal fun getSelectedCase(): MutableLiveData<CaseEntity> {
        return selectedCase
    }

}