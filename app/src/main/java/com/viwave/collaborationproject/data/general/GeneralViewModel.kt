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

}