package com.viwave.collaborationproject.data.general

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.viwave.collaborationproject.DB.cache.UserPreference

class GeneralViewModel: ViewModel() {

    var loginUser = MutableLiveData<User?>().apply {
        postValue(
            UserPreference.instance.queryUser()
        )
    }

    private var loginUserName: MutableLiveData<String?>? = null
    fun getLoginUserName(): MutableLiveData<String?>{
        if(loginUserName == null){
            loginUserName = GeneralLiveData().getLoginUserName()
        }
        return loginUserName!!
    }

    private var selectedSubSys: MutableLiveData<SubSys?>? = null
    fun getSelectedSubSys(): MutableLiveData<SubSys?>{
        if(selectedSubSys == null){
            selectedSubSys = GeneralLiveData().getSelectedSubSys()
        }
        return selectedSubSys!!
    }

}