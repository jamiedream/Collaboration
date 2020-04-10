/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/25/20 5:01 PM
 */

package com.viwave.collaborationproject.data.bios

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.viwave.collaborationproject.R

open class BioViewModel: ViewModel() {

    private var selectedType: MutableLiveData<BioLiveData.Companion.BioType>? = null
    fun getSelectedType(): MutableLiveData<BioLiveData.Companion.BioType> {
        if(selectedType == null){
            selectedType = BioLiveData().getSelectedType()
        }
        return selectedType!!
    }

    private var selectedTypeManualLayout = MutableLiveData<Int>()
    fun getSelectedTypeManualLayout(): MutableLiveData<Int> {
        selectedTypeManualLayout.value =
            when(selectedType?.value){
                BioLiveData.Companion.BioType.BloodGlucose -> R.layout.layout_manual_blood_glucose
                BioLiveData.Companion.BioType.Temperature -> R.layout.layout_manual_temp
                BioLiveData.Companion.BioType.Weight -> R.layout.layout_manual_weight
                BioLiveData.Companion.BioType.Respire -> R.layout.layout_manual_respire
                BioLiveData.Companion.BioType.Height -> R.layout.layout_manual_height
                BioLiveData.Companion.BioType.Pulse -> R.layout.layout_manual_pulse
                BioLiveData.Companion.BioType.BloodPressure -> R.layout.layout_manual_blood_pressure
                BioLiveData.Companion.BioType.Oxygen -> R.layout.layout_manual_oxygen
                else -> 0
            }
        return selectedTypeManualLayout
    }

    /**Chart*/
    private var selectedTypeHistoryTitle = MutableLiveData<Int>()
    fun getSelectedTypeHistoryTitle(): MutableLiveData<Int> {
        selectedTypeHistoryTitle.value =
            when(selectedType?.value){
                BioLiveData.Companion.BioType.BloodGlucose -> R.string.blood_glucose
                BioLiveData.Companion.BioType.Temperature -> R.string.temperature
                BioLiveData.Companion.BioType.Weight -> R.string.weight
                BioLiveData.Companion.BioType.Respire -> R.string.respire
                BioLiveData.Companion.BioType.Height -> R.string.height
                BioLiveData.Companion.BioType.Pulse -> R.string.pulse
                BioLiveData.Companion.BioType.BloodPressure -> R.string.blood_pressure
                BioLiveData.Companion.BioType.Oxygen -> R.string.oxygen
                else -> 0
            }
        return selectedTypeHistoryTitle
    }

    private var selectedTypeHistoryLayout = MutableLiveData<Int>()
    fun getSelectedTypeHistoryLayout(): MutableLiveData<Int> {
        selectedTypeHistoryLayout.value =
            when(selectedType?.value){
                BioLiveData.Companion.BioType.BloodGlucose -> R.layout.layout_history_chart_glucose
                BioLiveData.Companion.BioType.Temperature -> R.layout.layout_history_chart_temperature
                BioLiveData.Companion.BioType.Weight -> R.layout.layout_history_chart_weight
                BioLiveData.Companion.BioType.Respire -> R.layout.layout_history_chart_respire
                BioLiveData.Companion.BioType.Height -> R.layout.layout_history_chart_height
                BioLiveData.Companion.BioType.Pulse -> R.layout.layout_history_chart_pulse
                BioLiveData.Companion.BioType.BloodPressure -> R.layout.layout_history_chart_blood_pressure
                BioLiveData.Companion.BioType.Oxygen -> R.layout.layout_history_chart_oxygen
                else -> 0
            }
        return selectedTypeHistoryLayout
    }

    private var selectedTypeHistoryHeaderLayout = MutableLiveData<Int>()
    fun getSelectedTypeHistoryHeaderLayout(): MutableLiveData<Int> {
        selectedTypeHistoryHeaderLayout.value =
            when(selectedType?.value){
                BioLiveData.Companion.BioType.Temperature,
                BioLiveData.Companion.BioType.Weight,
                BioLiveData.Companion.BioType.Respire,
                BioLiveData.Companion.BioType.Height,
                BioLiveData.Companion.BioType.Pulse,
                BioLiveData.Companion.BioType.BloodPressure -> R.layout.layout_history_list_header
                BioLiveData.Companion.BioType.BloodGlucose,
                BioLiveData.Companion.BioType.Oxygen -> R.layout.layout_history_list_header1
                else -> 0
            }
        return selectedTypeHistoryHeaderLayout
    }

    private var markerData: MutableLiveData<Bio>? = null
    fun getMarkerData(): MutableLiveData<Bio>{
        if(markerData == null){
            markerData = BioLiveData().getMarkerData()
        }
        return markerData!!
    }

    /**
     * Temperature
     * */
    private var tempListData: MutableLiveData<MutableList<Bio.Temperature>>? = null
    fun getTempListData(): MutableLiveData<MutableList<Bio.Temperature>>{
        if(tempListData == null){
            tempListData = BioLiveData().getTempListData()
        }
        return tempListData!!
    }

    private var tempLastData: MutableLiveData<Bio.Temperature>? = null
    fun getTempLastData(): MutableLiveData<Bio.Temperature>{
        if(tempLastData == null){
            tempLastData = BioLiveData().getTempLastData()
        }
        return tempLastData!!
    }

    /**
     * Pulse
     * */
    private var pulseListData: MutableLiveData<MutableList<Bio.Pulse>>? = null
    fun getPulseListData(): MutableLiveData<MutableList<Bio.Pulse>>{
        if(pulseListData == null){
            pulseListData = BioLiveData().getPulseListData()
        }
        return pulseListData!!
    }

    private var pulseLastData: MutableLiveData<Bio.Pulse>? = null
    fun getPulseLastData(): MutableLiveData<Bio.Pulse>{
        if(pulseLastData == null){
            pulseLastData = BioLiveData().getPulseLastData()
        }
        return pulseLastData!!
    }

    /**
     * Weight
     * */
    private var weightListData: MutableLiveData<MutableList<Bio.Weight>>? = null
    fun getWeightListData(): MutableLiveData<MutableList<Bio.Weight>>{
        if(weightListData == null){
            weightListData = BioLiveData().getWeightListData()
        }
        return weightListData!!
    }

    private var weightLastData: MutableLiveData<Bio.Weight>? = null
    fun getWeightLastData(): MutableLiveData<Bio.Weight>{
        if(weightLastData == null){
            weightLastData = BioLiveData().getWeightLastData()
        }
        return weightLastData!!
    }

    /**
     * Glucose
     * */
    private var glucoseListData: MutableLiveData<MutableList<Bio.BloodGlucose>>? = null
    fun getGlucoseListData(): MutableLiveData<MutableList<Bio.BloodGlucose>>{
        if(glucoseListData == null){
            glucoseListData = BioLiveData().getGlucoseListData()
        }
        return glucoseListData!!
    }

    private var glucoseLastData: MutableLiveData<Bio.BloodGlucose>? = null
    fun getGlucoseLastData(): MutableLiveData<Bio.BloodGlucose>{
        if(glucoseLastData == null){
            glucoseLastData = BioLiveData().getGlucoseLastData()
        }
        return glucoseLastData!!
    }

    /**
     * BloodPressure
     * */
    private var bpListData: MutableLiveData<MutableList<Bio.BloodPressure>>? = null
    fun getBPListData(): MutableLiveData<MutableList<Bio.BloodPressure>>{
        if(bpListData == null){
            bpListData = BioLiveData().getBPListData()
        }
        return bpListData!!
    }

    private var bpLastData: MutableLiveData<Bio.BloodPressure>? = null
    fun getBPLastData(): MutableLiveData<Bio.BloodPressure>{
        if(bpLastData == null){
            bpLastData = BioLiveData().getBPLastData()
        }
        return bpLastData!!
    }

    /**
     * Oxygen
     * */
    private var oxygenListData: MutableLiveData<MutableList<Bio.Oxygen>>? = null
    fun getOxygenListData(): MutableLiveData<MutableList<Bio.Oxygen>>{
        if(oxygenListData == null){
            oxygenListData = BioLiveData().getOxygenListData()
        }
        return oxygenListData!!
    }

    private var oxygenLastData: MutableLiveData<Bio.Oxygen>? = null
    fun getOxygenLastData(): MutableLiveData<Bio.Oxygen>{
        if(oxygenLastData == null){
            oxygenLastData = BioLiveData().getOxygenLastData()
        }
        return oxygenLastData!!
    }

    /**
     * Respire
     * */
    private var respireListData: MutableLiveData<MutableList<Bio.Respire>>? = null
    fun getRespireListData(): MutableLiveData<MutableList<Bio.Respire>>{
        if(respireListData == null){
            respireListData = BioLiveData().getRespireListData()
        }
        return respireListData!!
    }

    private var respireLastData: MutableLiveData<Bio.Respire>? = null
    fun getRespireLastData(): MutableLiveData<Bio.Respire> {
        if (respireLastData == null) {
            respireLastData = BioLiveData().getRespireLastData()
        }
        return respireLastData!!
    }

    /**
     * Height
     * */
    private var heightListData: MutableLiveData<MutableList<Bio.Height>>? = null
    fun getHeightListData(): MutableLiveData<MutableList<Bio.Height>>{
        if(heightListData == null){
            heightListData = BioLiveData().getHeightListData()
        }
        return heightListData!!
    }

    private var heightLastData: MutableLiveData<Bio.Height>? = null
    fun getHeightLastData(): MutableLiveData<Bio.Height> {
        if (heightLastData == null) {
            heightLastData = BioLiveData().getHeightLastData()
        }
        return heightLastData!!
    }

}