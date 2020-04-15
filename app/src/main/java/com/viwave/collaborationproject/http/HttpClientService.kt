package com.viwave.collaborationproject.http

import com.google.gson.JsonObject
import com.viwave.collaborationproject.CollaborationApplication
import com.viwave.collaborationproject.DB.cache.SysKey
import com.viwave.collaborationproject.DB.cache.UserPreference
import com.viwave.collaborationproject.DB.remote.CaseDatabase
import com.viwave.collaborationproject.DB.remote.DataCountAction
import com.viwave.collaborationproject.DB.remote.entity.CaseEntity
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.data.bios.Bio
import com.viwave.collaborationproject.data.general.Logout
import com.viwave.collaborationproject.data.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object HttpClientService {
    private var service:HttpClient
    private const val baseUrl = "https://pm.st-mary.org.tw:8443/"
    private const val timeOut:Long = 30 //seconds
    private const val failureCode:Int = 9
    init {
        val client = OkHttpClient.Builder()
            .readTimeout(timeOut, TimeUnit.SECONDS)
            .writeTimeout(timeOut, TimeUnit.SECONDS)
            .connectTimeout(timeOut, TimeUnit.SECONDS)
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .client(client)
            .build()

        service = retrofit.create(HttpClient::class.java)
    }

    fun login(loginData: JsonObject, callback:HttpCallback<LoginRtnDto>) {
        val call:Call<LoginRtnDto> = service.login(loginData)
        call.enqueue(object:Callback<LoginRtnDto> {
            override fun onFailure(call: Call<LoginRtnDto>, t: Throwable) {
                callback.onFailure(HttpErrorData(failureCode, t.message))
            }

            override fun onResponse(call: Call<LoginRtnDto>, response: Response<LoginRtnDto>) {
                if (response.isSuccessful
                    && response.body() != null) {
                    response.body()?.let {
                        val resCode = it.res.toInt()
                        if(resCode == 1) {
                            callback.onSuccess(it)
                        }else{
                            callback.onFailure(HttpErrorData(resCode, it.msg))
                        }
                    }

                } else {
                    callback.onFailure(HttpErrorData(response.body()?.res?.toInt(), response.body()?.msg))
                }
            }
        })
    }

    fun logout(callback:HttpCallback<Logout>) {
        val call:Call<Logout> = service.logout()
        call.enqueue(object:Callback<Logout> {
            override fun onFailure(call: Call<Logout>, t: Throwable) {
                callback.onFailure(HttpErrorData(failureCode, t.message))
            }

            override fun onResponse(call: Call<Logout>, response: Response<Logout>) {
                if (response.isSuccessful
                    && response.body() != null) {
                    response.body()?.let {
                        val resCode = it.res.toInt()
                        if(resCode == 1) {
                            callback.onSuccess(it)
                        }else callback.onFailure(HttpErrorData(resCode, it.msg))
                    }

                } else {
                    callback.onFailure(HttpErrorData(response.body()?.res?.toInt(), response.body()?.msg))
                }
            }
        })
    }

    fun getList(sysCode:String, callback:HttpCallback<GetListRtnDto>) {
        val token = UserPreference.instance.queryUser()?.token
        val call:Call<GetListRtnDto> = service.getList("Bearer $token", sysCode)
        call.enqueue(object:Callback<GetListRtnDto> {
            override fun onFailure(call: Call<GetListRtnDto>, t: Throwable) {
                callback.onFailure(HttpErrorData(failureCode, t.message))
            }

            override fun onResponse(call: Call<GetListRtnDto>, response: Response<GetListRtnDto>) {
                if (response.isSuccessful
                    && response.body() != null) {
                    response.body()?.let {
                        val resCode = it.res.toInt()
                        if(resCode == 1) {
                            it.caseList?.let { caseList ->
                                GlobalScope.launch(Dispatchers.IO) {
                                    when (sysCode) {
                                        SysKey.DAILY_CARE_CODE -> {
                                            caseList.forEach { case ->
                                                CaseDatabase(CollaborationApplication.context).getCaseCareDao()
                                                    .insert(
                                                        CaseEntity.CaseCareEntity(
                                                            case.caseNumber,
                                                            case.caseName,
                                                            case.caseGender,
                                                            case.SCDTID,
                                                            case.startTime,
                                                            false,
                                                            DataCountAction.initDataCount
                                                        )
                                                    )
                                            }
                                            callback.onSuccess(it)
                                        }
                                        SysKey.DAILY_NURSING_CODE -> {
                                            caseList.forEach {case ->
                                                CaseDatabase(CollaborationApplication.context).getCaseNursingDao()
                                                    .insert(
                                                        CaseEntity.CaseNursingEntity(
                                                            case.caseNumber,
                                                            case.caseName,
                                                            case.caseGender,
                                                            case.SCDTID,
                                                            case.startTime,
                                                            false,
                                                            DataCountAction.initDataCount
                                                        )
                                                    )
                                            }
                                            callback.onSuccess(it)
                                        }
                                        SysKey.DAILY_STATION_CODE -> {
                                            caseList.forEach { case ->
                                                CaseDatabase(CollaborationApplication.context).getCaseStationDao()
                                                    .insert(
                                                        CaseEntity.CaseStationEntity(
                                                            case.caseNumber,
                                                            case.caseName,
                                                            case.caseGender,
                                                            case.SCDTID,
                                                            case.startTime,
                                                            false,
                                                            DataCountAction.initDataCount
                                                        )
                                                    )
                                            }
                                            callback.onSuccess(it)
                                        }
                                        SysKey.DAILY_HOME_CARE_CODE -> {
                                            caseList.forEach { case ->
                                                CaseDatabase(CollaborationApplication.context).getCaseHomeCareDao()
                                                    .insert(
                                                        CaseEntity.CaseHomeCareEntity(
                                                            case.caseNumber,
                                                            case.caseName,
                                                            case.caseGender,
                                                            case.SCDTID,
                                                            case.startTime,
                                                            false,
                                                            DataCountAction.initDataCount
                                                        )
                                                    )
                                            }
                                            callback.onSuccess(it)
                                        }
                                    }
                                }
                            }
                        }else callback.onFailure(HttpErrorData(resCode, it.msg))
                    }
                } else {
                    callback.onFailure(HttpErrorData(response.body()?.res?.toInt(), response.body()?.msg))
                }
            }
        })
    }

    fun uploadBloodGlucose(
        caseNo:String, staffId: String, SCDID: String, sysCode: String,
        bioData: Bio.BloodGlucose?, callback: HttpCallback<DefaultRtnDto>){
        if(bioData == null) {
            return
        }
        val takenAt = bioData.takenAt.div(1000L).toString()

        val type = CollaborationApplication.context.getString(R.string.blood_glucose)
        val uploadBioDto:UploadBioDto = UploadBioDto(
            caseNo,
            staffId,
            SCDID,
            sysCode,
            type,
            takenAt,
            "${bioData.glucose}",
            bioData.meal,
            "01"
        );

        val uploadList:ArrayList<UploadBioDto> = ArrayList();
        uploadList.add(uploadBioDto)
        uploadBio(uploadList, callback)
    }

    fun uploadBloodPressure(
        caseNo:String, staffId: String, SCDID: String, sysCode: String,
        bioData: Bio.BloodPressure, callback: HttpCallback<DefaultRtnDto>) {
        val takenAt = bioData.takenAt.div(1000L).toString()

        var type = CollaborationApplication.context.getString(R.string.blood_pressure)
        val bloodPressure: UploadBioDto = UploadBioDto(
            caseNo,
            staffId,
            SCDID,
            sysCode,
            type,
            takenAt,
            "${bioData.sys}/${bioData.dia}",
            "",
            "001"
        )

        type = CollaborationApplication.context.getString(R.string.pulse)
        val pulse = UploadBioDto(
                caseNo,
                staffId,
                SCDID,
                sysCode,
                type,
                takenAt,
                "${bioData.pulse}",
                "",
                "002"
            )
        val uploadList: ArrayList<UploadBioDto> = ArrayList();
        uploadList.add(bloodPressure)
        uploadList.add(pulse)
        uploadBio(uploadList, callback)
    }

    fun uploadHeight(
        caseNo:String, staffId: String, SCDID: String, sysCode: String,
        bioData: Bio.Height, callback: HttpCallback<DefaultRtnDto>){
        val takenAt = bioData.takenAt.div(1000L).toString()

        val type = CollaborationApplication.context.getString(R.string.height)
        val uploadBioDto:UploadBioDto = UploadBioDto(
            caseNo,
            staffId,
            SCDID,
            sysCode,
            type,
            takenAt,
            "${bioData.height}",
            "",
            "01"
        );

        val uploadList:ArrayList<UploadBioDto> = ArrayList();
        uploadList.add(uploadBioDto)
        uploadBio(uploadList, callback)
    }

    fun uploadOxygen(
        caseNo:String, staffId: String, SCDID: String, sysCode: String,
        bioData: Bio.Oxygen, callback: HttpCallback<DefaultRtnDto>){
        val takenAt = bioData.takenAt.div(1000L).toString()
        //TODO 血氧上傳格式
        val uploadList:ArrayList<UploadBioDto> = ArrayList();

        var uploadBioDto:UploadBioDto = UploadBioDto(
            caseNo,
            staffId,
            SCDID,
            sysCode,
            "actHighest",
            takenAt,
            "${bioData.actHighest}",
            "",
            "01"
        )
        uploadList.add(uploadBioDto)

        uploadBioDto = UploadBioDto(
            caseNo,
            staffId,
            SCDID,
            sysCode,
            "spo2Lowest",
            takenAt,
            "${bioData.spo2Lowest}",
            "",
            "02"
        )
        uploadList.add(uploadBioDto)

        uploadBioDto = UploadBioDto(
            caseNo,
            staffId,
            SCDID,
            sysCode,
            "spo2Highest",
            takenAt,
            "${bioData.spo2Highest}",
            "",
            "03"
        )
        uploadList.add(uploadBioDto)

        uploadBioDto = UploadBioDto(
            caseNo,
            staffId,
            SCDID,
            sysCode,
            "pulseHighest",
            takenAt,
            "${bioData.pulseHighest}",
            "",
            "04"
        )
        uploadList.add(uploadBioDto)

        uploadBioDto = UploadBioDto(
            caseNo,
            staffId,
            SCDID,
            sysCode,
            "pulseLowest",
            takenAt,
            "${bioData.pulseLowest}",
            "",
            "05"
        )
        uploadList.add(uploadBioDto)

        uploadBioDto = UploadBioDto(
            caseNo,
            staffId,
            SCDID,
            sysCode,
            "duration",
            takenAt,
            "${bioData.duration}",
            "",
            "06"
        )
        uploadList.add(uploadBioDto)

        uploadBio(uploadList, callback)
    }

    fun uploadPulse(
        caseNo:String, staffId: String, SCDID: String, sysCode: String,
        bioData: Bio.Pulse, callback: HttpCallback<DefaultRtnDto>){
        val takenAt = bioData.takenAt.div(1000L).toString()

        val type = CollaborationApplication.context.getString(R.string.pulse)
        val uploadBioDto:UploadBioDto = UploadBioDto(
            caseNo,
            staffId,
            SCDID,
            sysCode,
            type,
            takenAt,
            "${bioData.pulse}",
            "",
            "01"
        );

        val uploadList:ArrayList<UploadBioDto> = ArrayList();
        uploadList.add(uploadBioDto)
        uploadBio(uploadList, callback)
    }

    fun uploadRespire(
        caseNo:String, staffId: String, SCDID: String, sysCode: String,
        bioData: Bio.Respire, callback: HttpCallback<DefaultRtnDto>){
        val takenAt = bioData.takenAt.div(1000L).toString()
        val type = CollaborationApplication.context.getString(R.string.respire)
        val uploadBioDto:UploadBioDto = UploadBioDto(
            caseNo,
            staffId,
            SCDID,
            sysCode,
            type,
            takenAt,
            "${bioData.respire}",
            "",
            "01"
        );

        val uploadList:ArrayList<UploadBioDto> = ArrayList();
        uploadList.add(uploadBioDto)
        uploadBio(uploadList, callback)
    }

    fun uploadTemperature(
        caseNo:String, staffId: String, SCDID: String, sysCode: String,
        bioData: Bio.Temperature?, callback: HttpCallback<DefaultRtnDto>){
        if(bioData == null) {
            return
        }
        val takenAt = bioData.takenAt.div(1000L).toString()

        val type = CollaborationApplication.context.getString(R.string.temperature)
        val uploadBioDto:UploadBioDto = UploadBioDto(
            caseNo,
            staffId,
            SCDID,
            sysCode,
            type,
            takenAt,
            "${bioData.temperature}",
            "",
            "01"
        );

        val uploadList:ArrayList<UploadBioDto> = ArrayList();
        uploadList.add(uploadBioDto)
        uploadBio(uploadList, callback)
    }

    fun uploadWeight(
        caseNo:String, staffId: String, SCDID: String, sysCode: String,
        bioData: Bio.Weight, callback: HttpCallback<DefaultRtnDto>){
        val takenAt = bioData.takenAt.div(1000L).toString()

        val uploadList:ArrayList<UploadBioDto> = ArrayList();
        var uploadBioDto:UploadBioDto = UploadBioDto(
            caseNo,
            staffId,
            SCDID,
            sysCode,
            "bmi",
            takenAt,
            "${bioData.bmi}",
            "",
            "01"
        )
        uploadList.add(uploadBioDto)

        uploadBioDto = UploadBioDto(
            caseNo,
            staffId,
            SCDID,
            sysCode,
            "bodyFat",
            takenAt,
            "${bioData.bodyFat}",
            "",
            "02"
        )
        uploadList.add(uploadBioDto)

        uploadBioDto = UploadBioDto(
            caseNo,
            staffId,
            SCDID,
            sysCode,
            "bodyWater",
            takenAt,
            "${bioData.bodyWater}",
            "",
            "03"
        )
        uploadList.add(uploadBioDto)

        uploadBioDto = UploadBioDto(
            caseNo,
            staffId,
            SCDID,
            sysCode,
            "muscleMass",
            takenAt,
            "${bioData.muscleMass}",
            "",
            "04"
        )
        uploadList.add(uploadBioDto)

        uploadBioDto = UploadBioDto(
            caseNo,
            staffId,
            SCDID,
            sysCode,
            "visceralFat",
            takenAt,
            "${bioData.muscleMass}",
            "",
            "05"
        )
        uploadList.add(uploadBioDto)

        val type = CollaborationApplication.context.getString(R.string.weight)
        uploadBioDto = UploadBioDto(
            caseNo,
            staffId,
            SCDID,
            sysCode,
            type,
            takenAt,
            "${bioData.weight}",
            "",
            "06"
        )
        uploadList.add(uploadBioDto)

        uploadBio(uploadList, callback)
    }


    private fun uploadBio(bioList:ArrayList<UploadBioDto>, callback:HttpCallback<DefaultRtnDto>) {
        val token = UserPreference.instance.queryUser()?.token
        val call:Call<DefaultRtnDto> = service.uploadBio("Bearer $token", bioList)
        call.enqueue(object:Callback<DefaultRtnDto> {
            override fun onFailure(call: Call<DefaultRtnDto>, t: Throwable) {
                callback.onFailure(getErrorData(t))
            }

            override fun onResponse(call: Call<DefaultRtnDto>, response: Response<DefaultRtnDto>) {
                if (response.isSuccessful
                    && response.body() != null) {
                        callback.onSuccess(response.body()!!)
                } else {
                    callback.onFailure(getErrorData(response))
                }
            }
        })
    }

    fun getHistoryData(caseNumber:String, callback:HttpCallback<String>) {
        val token = UserPreference.instance.queryUser()?.token
        val call:Call<String> = service.getHistory("Bearer $token", caseNumber)
        call.enqueue(object:Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                callback.onFailure(getErrorData(t))
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful
                    && response.body() != null) {
                    callback.onSuccess(response.body().toString())
                } else {
                    callback.onFailure(getErrorData(response))
                }
            }
        })
    }

    private fun getErrorData(t:Throwable):HttpErrorData {
        val errData:HttpErrorData = HttpErrorData()
        errData.code = 0
        errData.message = t.toString()
        return errData
    }

    private fun <T> getErrorData(response:Response<T>) : HttpErrorData {
        val errData:HttpErrorData = HttpErrorData()
        val str:String = response.errorBody()!!.bytes().toString(Charsets.UTF_8)

        errData.code = response.code()
        errData.message = str

        return errData
    }

    interface HttpCallback<T> {
        fun onSuccess(data:T)
        fun onFailure(errData: HttpErrorData)
    }
}