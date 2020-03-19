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

    /**
     * Temperature
     * */
    private val tempListData = MutableLiveData<MutableList<Bio.Temperature>>()
    internal fun getTempListData(): MutableLiveData<MutableList<Bio.Temperature>>? {
        return tempListData
    }

    private val tempLastData = MutableLiveData<Bio.Temperature>()
    internal fun getTempLastData(): MutableLiveData<Bio.Temperature>? {
        return tempLastData
    }

//    private val tempData = MutableLiveData<Float>()
//    internal fun demoTempData(): MutableLiveData<Float> {
//        return tempData
//    }
//
//    private val glucoseData = MutableLiveData<Int>()
//    internal fun demoGlucoseData(): MutableLiveData<Int> {
//        return glucoseData
//    }
//
//    private val glucoseNoteData = MutableLiveData<String>()
//    internal fun demoGlucoseNoteData(): MutableLiveData<String> {
//        return glucoseNoteData
//    }
//
//    private val weightData = MutableLiveData<Float>()
//    internal fun demoWeightData(): MutableLiveData<Float> {
//        return weightData
//    }
//
//    private val respireData = MutableLiveData<Int>()
//    internal fun demoRespireData(): MutableLiveData<Int> {
//        return respireData
//    }
//
//    private val heightData = MutableLiveData<Float>()
//    internal fun demoHeightData(): MutableLiveData<Float> {
//        return heightData
//    }
//
//    private val pulseData = MutableLiveData<Int>()
//    internal fun demoPulseData(): MutableLiveData<Int> {
//        return pulseData
//    }
}