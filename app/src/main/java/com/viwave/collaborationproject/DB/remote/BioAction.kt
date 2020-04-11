package com.viwave.collaborationproject.DB.remote

import com.viwave.collaborationproject.CollaborationApplication
import com.viwave.collaborationproject.CollaborationApplication.Companion.context
import com.viwave.collaborationproject.DB.cache.SysKey
import com.viwave.collaborationproject.DB.remote.entity.BioDataEntity
import com.viwave.collaborationproject.DB.remote.entity.CaseEntity
import com.viwave.collaborationproject.data.bios.Bio
import com.viwave.collaborationproject.data.cases.Case
import com.viwave.collaborationproject.utils.LogUtil
import java.util.*

object BioAction {

    private val TAG = this::class.java.simpleName

    fun saveBio(caseNumber:String, sysCode:String, bio:Bio, isPending:Boolean) {
        when(bio) {
            is Bio.BloodGlucose -> {
                val entity = BioDataEntity.BloodGlucoseEntity(
                    0,
                    caseNumber,
                    sysCode,
                    isPending,
                    bio
                )
                CaseDatabase(context).getBioDao().insertBG(entity)
            }
            is Bio.BloodPressure -> {
                val entity = BioDataEntity.BloodPressureEntity(
                    0,
                    caseNumber,
                    sysCode,
                    isPending,
                    bio
                )
                CaseDatabase(context).getBioDao().insertBP(entity)
            }
            is Bio.Height -> {
                val entity = BioDataEntity.HeightEntity(
                    0,
                    caseNumber,
                    sysCode,
                    isPending,
                    bio
                )
                CaseDatabase(context).getBioDao().insertHeight(entity)
            }

            is Bio.Oxygen -> {
                val entity = BioDataEntity.OxygenEntity(
                    0,
                    caseNumber,
                    sysCode,
                    isPending,
                    bio
                )
                CaseDatabase(context).getBioDao().insertOxygen(entity)
            }
            is Bio.Pulse -> {
                val entity = BioDataEntity.PulseEntity(
                    0,
                    caseNumber,
                    sysCode,
                    isPending,
                    bio
                )
                CaseDatabase(context).getBioDao().insertPulse(entity)
            }
            is Bio.Respire -> {
                val entity = BioDataEntity.RespireEntity(
                    0,
                    caseNumber,
                    sysCode,
                    isPending,
                    bio
                )
                CaseDatabase(context).getBioDao().insertRespire(entity)
            }
            is Bio.Temperature -> {
                val entity = BioDataEntity.TemperatureEntity(
                    0,
                    caseNumber,
                    sysCode,
                    isPending,
                    bio
                )
                CaseDatabase(context).getBioDao().insertTemperature(entity)
            }
            is Bio.Weight -> {
                val entity = BioDataEntity.WeightEntity(
                    0,
                    caseNumber,
                    sysCode,
                    isPending,
                    bio
                )
                CaseDatabase(context).getBioDao().insertWeight(entity)
            }
        }
    }

    //<sysCode, <case, List<Bio>>
    fun getAllPendingData() : TreeMap<String, TreeMap<Case, ArrayList<Bio>>> {
        val dataMap: TreeMap<String, TreeMap<Case, ArrayList<Bio>>> = TreeMap()

        //1. 先取得所有的 pending data
        val bpList: List<BioDataEntity.BloodPressureEntity> =
            CaseDatabase(context).getBioDao().getPendingBP()
        val bgList: List<BioDataEntity.BloodGlucoseEntity> =
            CaseDatabase(context).getBioDao().getPendingBG()
        val heightList: List<BioDataEntity.HeightEntity> =
            CaseDatabase(context).getBioDao().getPendingHeight()
        val oxygenList: List<BioDataEntity.OxygenEntity> =
            CaseDatabase(context).getBioDao().getPendingOxygen()
        val pulseList: List<BioDataEntity.PulseEntity> =
            CaseDatabase(context).getBioDao().getPendingPulse()
        val respireList: List<BioDataEntity.RespireEntity> =
            CaseDatabase(context).getBioDao().getPendingRespire()
        val temperatureList: List<BioDataEntity.TemperatureEntity> =
            CaseDatabase(context).getBioDao().getPendingTemperature()
        val weightList: List<BioDataEntity.WeightEntity> =
            CaseDatabase(context).getBioDao().getPendingWeight()


        val allList: ArrayList<BioDataEntity> = ArrayList()
        allList.addAll(bpList)
        allList.addAll(bgList)
        allList.addAll(heightList)
        allList.addAll(oxygenList)
        allList.addAll(pulseList)
        allList.addAll(respireList)
        allList.addAll(temperatureList)
        allList.addAll(weightList)

        //2 依照不同的 sysCode 取得所有的 pending data
        //四個 sysCode
        //"S03" "日照中心"
        //"S05" "居護"
        //"S26" "活力站"
        //"S01" "居服"

        val careMap:TreeMap<Case, ArrayList<Bio>> = TreeMap()
        getPendingData(SysKey.DAILY_CARE_CODE, allList, careMap)
        dataMap[SysKey.DAILY_CARE_CODE] = careMap

        val nursingMap:TreeMap<Case, ArrayList<Bio>> = TreeMap()
        getPendingData(SysKey.DAILY_NURSING_CODE, allList, nursingMap)
        dataMap[SysKey.DAILY_NURSING_CODE] = nursingMap

        val stationMap:TreeMap<Case, ArrayList<Bio>> = TreeMap()
        getPendingData(SysKey.DAILY_STATION_CODE, allList, stationMap)
        dataMap[SysKey.DAILY_STATION_CODE] = stationMap

        val homeMap:TreeMap<Case, ArrayList<Bio>> = TreeMap()
        getPendingData(SysKey.DAILY_HOME_CARE_CODE, allList, homeMap)
        dataMap[SysKey.DAILY_HOME_CARE_CODE] = homeMap

        return dataMap
    }

    private fun getPendingData(sysCode:String, bioDataList:List<BioDataEntity>, caseMap:TreeMap<Case, ArrayList<Bio>> ) {

        bioDataList.forEach {
            val caseNumber = it.caseNumber
            var isFound = false;
            for ((k, v) in caseMap) {
                if(k.caseNumber == it.caseNumber) {
                    isFound = true
                    v.add(it.data)
                }
            }

            if(!isFound) {
                val case:Case? = getCase(sysCode, caseNumber)
                if(case == null) {
                    LogUtil.logE(TAG, "can' get case, sysCode:$sysCode, caseNumber:$caseNumber")
                    return@forEach
                }
                val bioList:ArrayList<Bio> = ArrayList()
                bioList.add(it.data)
                caseMap[case] = bioList
            }
        }
    }

    private fun getCase(sysCode:String, caseNumber:String):Case? {
        var caseEntity: CaseEntity? = null
        when(sysCode) {
            SysKey.DAILY_CARE_CODE -> {
                //日照中心
                caseEntity = CaseDatabase(CollaborationApplication.context).
                    getCaseCareDao().search(caseNumber)
            }
            SysKey.DAILY_NURSING_CODE -> {
                //居護
                caseEntity = CaseDatabase(CollaborationApplication.context).
                    getCaseNursingDao().search(caseNumber)
            }
            SysKey.DAILY_STATION_CODE -> {
                //活力站
                caseEntity = CaseDatabase(CollaborationApplication.context).
                    getCaseStationDao().search(caseNumber)
            }
            SysKey.DAILY_HOME_CARE_CODE -> {
                //居服
                caseEntity = CaseDatabase(CollaborationApplication.context).
                    getCaseHomeCareDao().search(caseNumber)
            }
        }

        if (caseEntity == null) {
            LogUtil.logE("BioDAO", "can't find Case $caseNumber")
            return null
        }

        return Case(
            caseEntity.getCaseNumber,
            caseEntity.getCaseName,
            caseEntity.getCaseGender,
            caseEntity.getStartTime,
            caseEntity.getSCDID
        )
    }
}
