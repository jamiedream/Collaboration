package com.viwave.collaborationproject.data.general

data class User(
    val id: String,
    val name: String,
    val loginTime: String,
    val token: String,
    val sysList: MutableList<SubSys>
)