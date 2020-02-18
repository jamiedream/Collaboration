package com.viwave.collaborationproject.data

import com.google.gson.GsonBuilder
import com.viwave.collaborationproject.data.bios.Bio
import com.viwave.collaborationproject.data.cases.Case

class DataHandler{

    fun getSubSysCaseList(json: String): MutableList<Case>{
        val gson = GsonBuilder().create()
        return gson.fromJson(json, Array<Case>::class.java).toMutableList()
    }

    fun uploadData(
        type: String,
        caseNumber: String,
        staffId: String,
        SCDTID: String,
        sysCode: String,
        bioData: Bio
    ){


    }

    fun queryData(type: String, dataSet: String){
//        when(type){
//            BioType.BloodGlucose -> {
//                val dataList = gson.fromJson(dataSet, Array<Bio.BloodGlucose>::class.java).toList()
//            }
//            BioType.BloodPressure -> {
//                val dataList = gson.fromJson(dataSet, Array<Bio.BloodPressure>::class.java).toList()
//            }
//            BioType.Weight -> {
//                val dataList = gson.fromJson(dataSet, Array<Bio.Weight>::class.java).toList()
//            }
//            BioType.Temperature -> {
//                val dataList = gson.fromJson(dataSet, Array<Bio.Temperature>::class.java).toList()
//            }
//            BioType.Pulse -> {
//                val dataList = gson.fromJson(dataSet, Array<Bio.Pulse>::class.java).toList()
//            }
//            BioType.Oxygen -> {
//                val dataList = gson.fromJson(dataSet, Array<Bio.Oxygen>::class.java).toList()
//            }
//            BioType.Respire -> {
//                val dataList = gson.fromJson(dataSet, Array<Bio.Respire>::class.java).toList()
//            }
//            BioType.Height -> {
//                val dataList = gson.fromJson(dataSet, Array<Bio.Height>::class.java).toList()
//            }
//        }
    }
}