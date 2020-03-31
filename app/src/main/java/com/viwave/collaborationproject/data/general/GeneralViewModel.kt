/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/25/20 1:49 PM
 */

package com.viwave.collaborationproject.data.general

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GeneralViewModel: ViewModel() {

    private var loginUser: MutableLiveData<User?>? = null
    fun getLoginUser(): MutableLiveData<User?>{
        if(loginUser == null){
            loginUser = GeneralLiveData().getLoginUser()
        }
        return loginUser!!
    }

    private var selectedSubSys: MutableLiveData<SubSys?>? = null
    fun getSelectedSubSys(): MutableLiveData<SubSys?>{
        if(selectedSubSys == null){
            selectedSubSys = GeneralLiveData().getSelectedSubSys()
        }
        return selectedSubSys!!
    }

}