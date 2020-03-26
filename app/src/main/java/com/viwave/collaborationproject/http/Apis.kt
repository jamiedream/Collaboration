/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 26/ 3/ 2020.
 * Last modified 3/26/20 3:41 PM
 */

package com.viwave.collaborationproject.http

import com.google.gson.JsonObject
import com.viwave.collaborationproject.CollaborationApplication.Companion.context
import com.viwave.collaborationproject.DB.cache.SysKey
import com.viwave.collaborationproject.DB.cache.UserPreference
import com.viwave.collaborationproject.DB.remote.CaseDatabase
import com.viwave.collaborationproject.DB.remote.DataCountAction.initDataCount
import com.viwave.collaborationproject.DB.remote.entity.CaseEntity
import com.viwave.collaborationproject.data.DataSort
import com.viwave.collaborationproject.data.general.GetList
import com.viwave.collaborationproject.data.general.Login
import com.viwave.collaborationproject.utils.LogUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Apis {

    private val TAG = this::class.java.simpleName
    private val httpManager = HttpManager.client.create(IHttp::class.java)

    fun login(loginData: JsonObject, listener: IAPIResult){
        httpManager.
            login(loginData).
            enqueue(object : Callback<Login> {
                override fun onFailure(call: Call<Login>, t: Throwable) {
                    listener.onFailed(t.message)
                }

                override fun onResponse(call: Call<Login>, response: Response<Login>) {
                    val res = response.body()
                    val resCode = res?.res?.toInt()
                    val msg = res?.msg
                    LogUtil.logD(TAG, "$resCode $msg")
                    if(response.isSuccessful){
                        when(resCode){
                            1 -> {
                                //login success
                                UserPreference.instance.editUser(DataSort.staff(res))
//                                LogUtil.logD(TAG, "$resCode $msg")
                                //sys list
                                res.system.forEach {
                                    getCaseList(it.sysCode,
                                        object : IAPIResult{
                                            override fun onSuccess() {
                                                listener.onSuccess()
                                            }

                                            override fun onFailed(msg: String?) {
                                                listener.onFailed(msg)
                                            }

                                        })
                                }

                            }
                            else -> listener.onFailed(msg)
                        }
                    }else listener.onFailed(msg)
                }
            })
    }


    private fun getCaseList(sysCode: String, listener: IAPIResult){
        httpManager.
            getList(UserPreference.instance.queryUser().token, sysCode).
            enqueue(object : Callback<GetList> {
                override fun onFailure(call: Call<GetList>, t: Throwable) {
                    listener.onFailed(t.message)
                }
                override fun onResponse(
                    call: Call<GetList>,
                    response: Response<GetList>
                ) {
                    val res = response.body()
                    val resCode = res?.res?.toInt()
                    val msg = res?.msg
                    LogUtil.logD(TAG, "$resCode $msg")
                    if(response.isSuccessful) {
                        when (resCode) {
                            1 -> {
                                val caseList = res.caseList
                                GlobalScope.launch(Dispatchers.IO) {
                                    when(sysCode){
                                        SysKey.DAILY_CARE_CODE -> {
                                            caseList.forEach {
                                                CaseDatabase(context).getCaseCareDao().insert(
                                                    CaseEntity.CaseCareEntity(
                                                        it.caseNumber,
                                                        it.caseName,
                                                        it.caseGender,
                                                        it.SCDTID,
                                                        it.startTime,
                                                        false,
                                                        initDataCount
                                                    )
                                                )
                                            }
                                        }
                                        SysKey.DAILY_NURSING_CODE -> {
                                            caseList.forEach {
                                                CaseDatabase(context).getCaseNursingDao().insert(
                                                    CaseEntity.CaseNursingEntity(
                                                        it.caseNumber,
                                                        it.caseName,
                                                        it.caseGender,
                                                        it.SCDTID,
                                                        it.startTime,
                                                        false,
                                                        initDataCount
                                                    )
                                                )
                                            }
                                        }
                                        SysKey.DAILY_STATION_CODE -> {
                                            caseList.forEach {
                                                CaseDatabase(context).getCaseStationDao().insert(
                                                    CaseEntity.CaseStationEntity(
                                                        it.caseNumber,
                                                        it.caseName,
                                                        it.caseGender,
                                                        it.SCDTID,
                                                        it.startTime,
                                                        false,
                                                        initDataCount
                                                    )
                                                )
                                            }
                                        }
                                        SysKey.DAILY_HOME_CARE_CODE -> {
                                            caseList.forEach {
                                                CaseDatabase(context).getCaseHomeCareDao().insert(
                                                    CaseEntity.CaseHomeCareEntity(
                                                        it.caseNumber,
                                                        it.caseName,
                                                        it.caseGender,
                                                        it.SCDTID,
                                                        it.startTime,
                                                        false,
                                                        initDataCount
                                                    )
                                                )
                                            }
                                        }
                                    }
                                }
                                listener.onSuccess()
                            }
                            else -> listener.onFailed(msg)
                        }
                    }else listener.onFailed(msg)

                }
            })

    }
    
}