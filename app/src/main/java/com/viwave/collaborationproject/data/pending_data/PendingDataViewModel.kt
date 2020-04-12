package com.viwave.collaborationproject.data.pending_data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.viwave.collaborationproject.DB.remote.BioAction
import com.viwave.collaborationproject.data.bios.Bio
import com.viwave.collaborationproject.data.cases.Case
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class PendingDataViewModel:ViewModel() {
    private val data: MutableLiveData<TreeMap<String, TreeMap<Case, ArrayList<Bio>>>> by lazy {
        MutableLiveData<TreeMap<String, TreeMap<Case, ArrayList<Bio>>>>().also {
            GlobalScope.launch(Dispatchers.IO) {
                it.postValue(BioAction.getAllPendingData())
            }
        }
    }

    fun getPendingData(): MutableLiveData<TreeMap<String, TreeMap<Case, ArrayList<Bio>>>>{
        return data
    }
}