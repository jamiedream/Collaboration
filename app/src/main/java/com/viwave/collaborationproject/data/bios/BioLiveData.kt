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

    /**
     * Glucose
     * */
    private val glucoseListData = MutableLiveData<MutableList<Bio.BloodGlucose>>()
    internal fun getGlucoseListData(): MutableLiveData<MutableList<Bio.BloodGlucose>>? {
        return glucoseListData
    }

    private val glucoseLastData = MutableLiveData<Bio.BloodGlucose>()
    internal fun getGlucoseLastData(): MutableLiveData<Bio.BloodGlucose>? {
        return glucoseLastData
    }

    /**
     * BloodPressure
     * */
    private val bpListData = MutableLiveData<MutableList<Bio.BloodPressure>>()
    internal fun getBPListData(): MutableLiveData<MutableList<Bio.BloodPressure>>? {
        return bpListData
    }

    private val bpLastData = MutableLiveData<Bio.BloodPressure>()
    internal fun getBPLastData(): MutableLiveData<Bio.BloodPressure>? {
        return bpLastData
    }

    /**
     * Oxygen
     * */
    private val oxygenListData = MutableLiveData<MutableList<Bio.Oxygen>>()
    internal fun getOxygenListData(): MutableLiveData<MutableList<Bio.Oxygen>>? {
        return oxygenListData
    }

    private val oxygenLastData = MutableLiveData<Bio.Oxygen>()
    internal fun getOxygenLastData(): MutableLiveData<Bio.Oxygen>? {
        return oxygenLastData
    }
}