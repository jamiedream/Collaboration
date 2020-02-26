package com.viwave.collaborationproject.data.general

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.viwave.collaborationproject.DB.cache.UserKey
import com.viwave.collaborationproject.DB.cache.UserPreference

class GeneralViewModel: ViewModel() {

    var isLogin = MutableLiveData<Boolean>().apply {
        postValue(
            UserPreference.instance.query(UserKey.IS_LOGIN, false)
        )
    }

    var loginUser = MutableLiveData<User?>().apply {
        postValue(
            UserPreference.instance.queryUser(UserKey.USER)
        )
    }

}