package com.viwave.collaborationproject.data.general

import androidx.lifecycle.MutableLiveData
import com.viwave.collaborationproject.DB.cache.UserPreference

class GeneralLiveData {

    private val loginUser = MutableLiveData<User?>()
    internal fun getLoginUser(): MutableLiveData<User?> {
        loginUser.postValue(UserPreference.instance.queryUser())
        return loginUser
    }

    private val selectedSubSys = MutableLiveData<SubSys?>()
    internal fun getSelectedSubSys(): MutableLiveData<SubSys?> {
        selectedSubSys.postValue(UserPreference.instance.querySubSys())
        return selectedSubSys
    }
}