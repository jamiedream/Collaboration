/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/30/20 10:14 AM
 */

package com.viwave.collaborationproject.DB.remote

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.viwave.collaborationproject.CollaborationApplication.Companion.context
import com.viwave.collaborationproject.DB.cache.SysKey
import com.viwave.collaborationproject.data.DataSort
import com.viwave.collaborationproject.utils.ConvertUtil.sortListData
import com.viwave.collaborationproject.utils.LogUtil
import org.json.JSONArray

object DataCountAction {

    fun updateDataCount(sysCode: String, caseNumber: String, type: String){
        val rawData =
            when(sysCode){
                SysKey.DAILY_CARE_CODE -> CaseDatabase(context).getCaseCareDao().getDataCountStr(caseNumber)
                SysKey.DAILY_NURSING_CODE -> CaseDatabase(context).getCaseNursingDao().getDataCountStr(caseNumber)
                SysKey.DAILY_STATION_CODE -> CaseDatabase(context).getCaseStationDao().getDataCountStr(caseNumber)
                else -> CaseDatabase(context).getCaseHomeCareDao().getDataCountStr(caseNumber)
            }
        LogUtil.logD("updateDataCount", CaseDatabase(context).getCaseNursingDao().getDataCountStr(caseNumber))

        val dataList = getListFromStr(rawData)
        dataList.find { it.type == type }?.apply { this.count += 1 }
        when(sysCode){
            SysKey.DAILY_CARE_CODE -> CaseDatabase(context).getCaseCareDao().getDataCountStr(caseNumber)
            SysKey.DAILY_NURSING_CODE -> CaseDatabase(context).getCaseNursingDao().updateDataCount(caseNumber, newJsonArrayStr(dataList))
            SysKey.DAILY_STATION_CODE -> CaseDatabase(context).getCaseStationDao().getDataCountStr(caseNumber)
            else -> CaseDatabase(context).getCaseHomeCareDao().getDataCountStr(caseNumber)
        }

        LogUtil.logD("updateDataCount", CaseDatabase(context).getCaseNursingDao().getDataCountStr(caseNumber))
    }

    fun getListFromStr(rawData: String): MutableList<DataCount>{
        val dataArray = JsonArray()
        val array = JSONArray(rawData)
        for(num in 0 until array.length()){
            val type = array.getJSONObject(num).get("type").toString()
            val count = array.getJSONObject(num).get("count").toString().toInt()
            dataArray.add(
                JsonObject().apply {
                    this.addProperty("type", type)
                    this.addProperty("count", count)
                }
            )
        }
        return  sortListData(DataSort.dataCountList, dataArray)
    }

    private fun newJsonArrayStr(list: MutableList<DataCount>): String{
        val array = JsonArray()
        list.forEach {
            array.add(
                JsonObject().apply {
                    this.addProperty("type", it.type)
                    this.addProperty("count", it.count)
                }
            )
        }
        return Gson().toJson(array)
    }

    private val initDataCountList =
        mutableListOf<DataCount>().apply {
            this.add(DataCount(DataSort.Temperature, 0))
            this.add(DataCount(DataSort.Pulse, 0))
            this.add(DataCount(DataSort.Respire, 0))
            this.add(DataCount(DataSort.BloodGlucose, 0))
            this.add(DataCount(DataSort.BloodPressure, 0))
            this.add(DataCount(DataSort.Oxygen, 0))
            this.add(DataCount(DataSort.Height, 0))
            this.add(DataCount(DataSort.Weight, 0))
        }

    val initDataCount = newJsonArrayStr(initDataCountList)

}