package com.viwave.collaborationproject.data.cases

import androidx.lifecycle.MutableLiveData

class CaseLiveData {

    private val selectedCase = MutableLiveData<Case>()
    internal fun getSelectedCase(): MutableLiveData<Case> {
        return selectedCase
    }

}