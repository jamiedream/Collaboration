/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/25/20 1:49 PM
 */

package com.viwave.collaborationproject.data.cases

import androidx.lifecycle.MutableLiveData
import com.viwave.collaborationproject.DB.remote.entity.CaseEntity

class CaseLiveData {

    private val selectedCase = MutableLiveData<CaseEntity>()
    internal fun getSelectedCase(): MutableLiveData<CaseEntity> {
        return selectedCase
    }

}