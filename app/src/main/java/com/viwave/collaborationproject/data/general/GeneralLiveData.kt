package com.viwave.collaborationproject.data.general

import androidx.lifecycle.MutableLiveData
import com.viwave.collaborationproject.DB.cache.UserPreference

class GeneralLiveData {

    private val loginUserName = MutableLiveData<String?>()
    internal fun getLoginUserName(): MutableLiveData<String?> {
        loginUserName.postValue(UserPreference.instance.queryUser()?.name)
        return loginUserName
    }

    private val selectedSubSys = MutableLiveData<SubSys?>()
    internal fun getSelectedSubSys(): MutableLiveData<SubSys?> {
        selectedSubSys.postValue(UserPreference.instance.querySubSys())
        return selectedSubSys
    }
}