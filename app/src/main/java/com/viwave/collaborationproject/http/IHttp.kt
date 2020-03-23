package com.viwave.collaborationproject.http

import com.google.gson.JsonObject
import com.viwave.collaborationproject.data.general.Login
import com.viwave.collaborationproject.data.general.Logout
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface IHttp {

    @POST(HttpManager.API_LOGIN)
    fun login(@Body uploadLoginInfo: JsonObject): Call<Login>

    @GET(HttpManager.API_LOGOUT)
    fun logout(@Header("AUTHORIZATION") token: String): Call<Logout>

}