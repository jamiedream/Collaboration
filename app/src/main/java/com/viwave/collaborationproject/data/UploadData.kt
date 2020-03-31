/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/25/20 1:49 PM
 */

package com.viwave.collaborationproject.data

import com.google.gson.JsonObject
import java.security.MessageDigest

object UploadData {

    fun uploadLoginInfo(account: String, password: String): JsonObject {
        return JsonObject().apply {
            this.addProperty("staffId", account)
            this.addProperty("staffPwd", getSha256String(password))
        }
    }

    @Throws(Exception::class)
    private fun getSha256String(pwd: String): String{
        val digest = MessageDigest.getInstance("SHA-256")
        digest.reset()
        return binToHex(digest.digest(pwd.toByteArray()))

    }

    private fun binToHex(data: ByteArray): String{
        val hexString = StringBuilder(data.size.times(2))
        data.forEach {
            hexString.append(String.format("%02x", it.toInt() and 0xFF))
        }
        return hexString.toString()
    }

}