package com.viwave.collaborationproject.data.bios

import androidx.lifecycle.MutableLiveData

class BioLiveData {

    companion object{
        enum class BioType{BloodPressure, BloodGlucose, Temperature, Weight, Pulse, Oxygen, Respire, Height}
        enum class Oxygen{OxygenHigh, OxygenLow, PulseHigh, PulseLow}
    }

    private val selectedType = MutableLiveData<BioType>()
    internal fun getSelectedType(): MutableLiveData<BioType> {
        return selectedType
    }

    /**
     * Marker
     * */
    private val markerData = MutableLiveData<Bio>()
    internal fun getMarkerData(): MutableLiveData<Bio>?{
        return markerData
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

    /**
     * Pulse
     * */
    private val pulseListData = MutableLiveData<MutableList<Bio.Pulse>>()
    internal fun getPulseListData(): MutableLiveData<MutableList<Bio.Pulse>>? {
        return pulseListData
    }

    private val pulseLastData = MutableLiveData<Bio.Pulse>()
    internal fun getPulseLastData(): MutableLiveData<Bio.Pulse>? {
        return pulseLastData
    }

    /**
     * Weight
     * */
    private val weightListData = MutableLiveData<MutableList<Bio.Weight>>()
    internal fun getWeightListData(): MutableLiveData<MutableList<Bio.Weight>>? {
        return weightListData
    }

    private val weightLastData = MutableLiveData<Bio.Weight>()
    internal fun getWeightLastData(): MutableLiveData<Bio.Weight>? {
        return weightLastData
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