package com.viwave.collaborationproject.data.bios

import androidx.lifecycle.MutableLiveData

class BioLiveData {

    companion object{
        enum class BioType{BloodPressure, BloodGlucose, Temperature, Weight, Pulse, Oxygen, Respire, Height}
        enum class BloodPressure{Sys, Dia, Pulse}
        enum class Oxygen{OxygenHigh, OxygenLow, PulseHigh, PulseLow}
    }

    private val selectedType = MutableLiveData<BioType>()
    internal fun getSelectedType(): MutableLiveData<BioType> {
        return selectedType
    }

    private val tempData = MutableLiveData<Float>()
    internal fun demoTempData(): MutableLiveData<Float> {
        return tempData
    }

    private val glucoseData = MutableLiveData<Int>()
    internal fun demoGlucoseData(): MutableLiveData<Int> {
        return glucoseData
    }
}