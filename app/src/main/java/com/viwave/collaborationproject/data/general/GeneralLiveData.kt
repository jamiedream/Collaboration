/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/25/20 1:49 PM
 */

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