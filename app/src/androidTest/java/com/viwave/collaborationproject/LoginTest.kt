/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 16/ 4/ 2020.
 * Last modified 4/16/20 5:40 PM
 */

package com.viwave.collaborationproject

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.gson.JsonObject
import com.viwave.collaborationproject.data.UploadData
import com.viwave.collaborationproject.data.http.HttpErrorData
import com.viwave.collaborationproject.data.http.LoginRtnDto
import com.viwave.collaborationproject.http.HttpClientService
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
open class LoginTest {

    private lateinit var loginObject: JsonObject
    @Before
    fun initAccountAndPwd(){
        loginObject =
            UploadData.uploadLoginInfo("n00198", "st13579")
    }

    @Test
    open fun testLogin(){
        HttpClientService.login(loginObject,
            object: HttpClientService.HttpCallback<LoginRtnDto>{
                override fun onSuccess(data: LoginRtnDto) {
                    assertEquals(1, data.res.toInt())
                }

                override fun onFailure(errData: HttpErrorData) {
                    assert(false)
                }

            })
    }
}