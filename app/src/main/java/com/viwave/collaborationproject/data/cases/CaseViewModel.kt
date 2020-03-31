/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/25/20 1:49 PM
 */

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