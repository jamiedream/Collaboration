package com.viwave.collaborationproject.http

import com.viwave.collaborationproject.data.http.GetListRtnDto
import com.viwave.collaborationproject.data.http.LoginDto
import com.viwave.collaborationproject.data.http.LoginRtnDto
import com.viwave.collaborationproject.data.http.UploadBioDto
import retrofit2.Call
import retrofit2.http.*

interface HttpClient {
    @Headers("Content-Type: application/json")
    @POST("/api/Auth")
    fun login(
        @Body loginDto: LoginDto
    ): Call<LoginRtnDto>

    @Headers("Content-Type: application/json")
    @POST("/api/Auth/LogOut")
    fun logout(
        @Body loginDto: LoginDto
    ): Call<String>

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