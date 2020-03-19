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
                BioLiveData.Companion.BioType.BloodGlucose -> R.layout.layout_history_glucose_chart
                BioLiveData.Companion.BioType.Temperature -> R.layout.layout_history_temperature_chart
                BioLiveData.Companion.BioType.Weight -> R.layout.layout_history_weight_chart
                BioLiveData.Companion.BioType.Respire -> R.layout.layout_history_respire_chart
                BioLiveData.Companion.BioType.Height -> R.layout.layout_history_height_chart
                BioLiveData.Companion.BioType.Pulse -> R.layout.layout_history_pulse_chart
                BioLiveData.Companion.BioType.BloodPressure -> R.layout.layout_history_blood_pressure_chart
                BioLiveData.Companion.BioType.Oxygen -> R.layout.layout_history_oxygen_chart
                else -> 0
            }
        return selectedTypeHistoryLayout
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

//    private var demoTempData: MutableLiveData<Float>? = null
//    fun getDemoTempData(): MutableLiveData<Float> {
//        if(demoTempData == null){
//            demoTempData = BioLiveData().demoTempData()
//        }
//        return demoTempData!!
//    }
//
//    private var demoGlucoseData: MutableLiveData<Int>? = null
//    fun getDemoGlucoseData(): MutableLiveData<Int> {
//        if(demoGlucoseData == null){
//            demoGlucoseData = BioLiveData().demoGlucoseData()
//        }
//        return demoGlucoseData!!
//    }
//
//    private var demoGlucoseNoteData: MutableLiveData<String>? = null
//    fun getDemoGlucoseNoteData(): MutableLiveData<String> {
//        if(demoGlucoseNoteData == null){
//            demoGlucoseNoteData = BioLiveData().demoGlucoseNoteData()
//        }
//        return demoGlucoseNoteData!!
//    }
//
//    private var demoWeightData: MutableLiveData<Float>? = null
//    fun getDemoWeightData(): MutableLiveData<Float> {
//        if(demoWeightData == null){
//            demoWeightData = BioLiveData().demoWeightData()
//        }
//        return demoWeightData!!
//    }
//
//    private var demoRespireData: MutableLiveData<Int>? = null
//    fun getDemoRespireData(): MutableLiveData<Int> {
//        if(demoRespireData == null){
//            demoRespireData = BioLiveData().demoRespireData()
//        }
//        return demoRespireData!!
//    }
//
//    private var demoHeightData: MutableLiveData<Float>? = null
//    fun getDemoHeightData(): MutableLiveData<Float> {
//        if(demoHeightData == null){
//            demoHeightData = BioLiveData().demoHeightData()
//        }
//        return demoHeightData!!
//    }
//
//    private var demoPulseData: MutableLiveData<Int>? = null
//    fun getDemoPulseData(): MutableLiveData<Int> {
//        if(demoPulseData == null){
//            demoPulseData = BioLiveData().demoPulseData()
//        }
//        return demoPulseData!!
//    }

}