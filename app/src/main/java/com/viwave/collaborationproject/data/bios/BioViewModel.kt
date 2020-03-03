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

    private var demoTempData: MutableLiveData<Float>? = null
    fun getDemoTempData(): MutableLiveData<Float> {
        if(demoTempData == null){
            demoTempData = BioLiveData().demoTempData()
        }
        return demoTempData!!
    }

    private var demoGlucoseData: MutableLiveData<Int>? = null
    fun getDemoGlucoseData(): MutableLiveData<Int> {
        if(demoGlucoseData == null){
            demoGlucoseData = BioLiveData().demoGlucoseData()
        }
        return demoGlucoseData!!
    }

    private var demoGlucoseNoteData: MutableLiveData<String>? = null
    fun getDemoGlucoseNoteData(): MutableLiveData<String> {
        if(demoGlucoseNoteData == null){
            demoGlucoseNoteData = BioLiveData().demoGlucoseNoteData()
        }
        return demoGlucoseNoteData!!
    }

    private var demoWeightData: MutableLiveData<Float>? = null
    fun getDemoWeightData(): MutableLiveData<Float> {
        if(demoWeightData == null){
            demoWeightData = BioLiveData().demoWeightData()
        }
        return demoWeightData!!
    }

    private var demoRespireData: MutableLiveData<Int>? = null
    fun getDemoRespireData(): MutableLiveData<Int> {
        if(demoRespireData == null){
            demoRespireData = BioLiveData().demoRespireData()
        }
        return demoRespireData!!
    }

    private var demoHeightData: MutableLiveData<Float>? = null
    fun getDemoHeightData(): MutableLiveData<Float> {
        if(demoHeightData == null){
            demoHeightData = BioLiveData().demoHeightData()
        }
        return demoHeightData!!
    }

    private var demoPulseData: MutableLiveData<Int>? = null
    fun getDemoPulseData(): MutableLiveData<Int> {
        if(demoPulseData == null){
            demoPulseData = BioLiveData().demoPulseData()
        }
        return demoPulseData!!
    }

}