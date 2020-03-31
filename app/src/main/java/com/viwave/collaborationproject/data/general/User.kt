/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/25/20 1:49 PM
 */

package com.viwave.collaborationproject.data.general

data class User(
    val id: String,
    val name: String,
    val loginTime: String,
    val token: String,
    val sysList: MutableList<SubSys>
)