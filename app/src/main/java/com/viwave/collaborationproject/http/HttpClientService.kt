package com.viwave.collaborationproject.http

import com.viwave.collaborationproject.DB.cache.UserPreference
import com.viwave.collaborationproject.data.http.*
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

    fun login(account:String, pass:String, callback:HttpCallback<LoginRtnDto>) {
        //TODO hardcode id & pwd
        val loginDto = LoginDto()
        loginDto.staffId = "p00012"
        loginDto.staffPwd = "91b6a5c610ebbaf0d71921d9ee391e28b1e68bb28cf896fa8616903842bcbdbe"
        val call:Call<LoginRtnDto> = service.login(loginDto)
        call.enqueue(object:Callback<LoginRtnDto> {
            override fun onFailure(call: Call<LoginRtnDto>, t: Throwable) {
                callback.onFailure(getErrorData(t))
            }

            override fun onResponse(call: Call<LoginRtnDto>, response: Response<LoginRtnDto>) {
                if (response.isSuccessful
                    && response.body() != null) {
                    callback.onSuccess(response.body()!!)
                } else {
                    callback.onFailure(getErrorData(response))
                }
            }
        })
    }

    fun logout(account:String, pass:String, callback:HttpCallback<String>) {
        //TODO hardcode id & pwd
        val loginDto = LoginDto()
        loginDto.staffId = "p00012"
        loginDto.staffPwd = "91b6a5c610ebbaf0d71921d9ee391e28b1e68bb28cf896fa8616903842bcbdbe"
        val call:Call<String> = service.logout(loginDto)
        call.enqueue(object:Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                callback.onFailure(getErrorData(t))
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful
                    && response.body() != null) {
                    callback.onSuccess(response.body()!!)
                } else {
                    callback.onFailure(getErrorData(response))
                }
            }
        })
    }

    fun getList(sysCode:String, callback:HttpCallback<GetListRtnDto>) {
        val token = UserPreference.instance.queryUser()?.token
        val call:Call<GetListRtnDto> = service.getList("Bearer $token", sysCode)
        call.enqueue(object:Callback<GetListRtnDto> {
            override fun onFailure(call: Call<GetListRtnDto>, t: Throwable) {
                callback.onFailure(getErrorData(t))
            }

            override fun onResponse(call: Call<GetListRtnDto>, response: Response<GetListRtnDto>) {
                if (response.isSuccessful
                    && response.body() != null) {
                    callback.onSuccess(response.body()!!)
                } else {
                    callback.onFailure(getErrorData(response))
                }
            }
        })
    }

    fun uploadBio(bioList:ArrayList<UploadBioDto>, callback:HttpCallback<String>) {
        val token = UserPreference.instance.queryUser()?.token
        val call:Call<String> = service.uploadBio("Bearer $token", bioList)
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