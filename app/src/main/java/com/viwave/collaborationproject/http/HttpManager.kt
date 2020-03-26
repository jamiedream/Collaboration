package com.viwave.collaborationproject.http

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HttpManager {

    private val TAG = this::class.java.simpleName
    private val API_URL = "https://pm.st-mary.org.tw:8443"

    companion object {
        private val manager: HttpManager = HttpManager()
        val client: Retrofit
            get() = manager.retrofit
        const val API_LOGIN = "/api/Auth"
        const val API_LOGOUT = "/api/Auth/LogOut"
        const val API_CASE = "/api/GetList"
    }

    private val retrofit: Retrofit
    private val okHttp = OkHttpClient()

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttp)
            .build()
    }


}