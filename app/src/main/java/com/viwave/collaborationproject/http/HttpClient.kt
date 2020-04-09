package com.viwave.collaborationproject.http

import com.google.gson.JsonObject
import com.viwave.collaborationproject.data.general.Logout
import com.viwave.collaborationproject.data.http.GetListRtnDto
import com.viwave.collaborationproject.data.http.LoginRtnDto
import com.viwave.collaborationproject.data.http.UploadBioDto
import retrofit2.Call
import retrofit2.http.*

interface HttpClient {
    @Headers("Content-Type: application/json")
    @POST("/api/Auth")
    fun login(
        @Body loginDto: JsonObject
    ): Call<LoginRtnDto>

    @Headers("Content-Type: application/json")
    @GET("/api/Auth/LogOut")
    fun logout(
    ): Call<Logout>

    @Headers("Content-Type: application/json")
    @GET("/api/GetList/{sysCode}")
    fun getList(
        @Header("Authorization") token:String,
        @Path("sysCode") sysCode:String
    ): Call<GetListRtnDto>

    @Headers("Content-Type: application/json")
    @POST("/api/PM")
    fun uploadBio(
        @Header("Authorization") token:String,
        @Body bioList:ArrayList<UploadBioDto>
    ): Call<String>

    @Headers("Content-Type: application/json")
    @GET("/api/PM/{caseNumber}")
    fun getHistory(
        @Header("Authorization") token:String,
        @Path("caseNumber") caseNumber:String
    ): Call<String>
}