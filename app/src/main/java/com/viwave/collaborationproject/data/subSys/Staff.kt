package com.viwave.collaborationproject.data.subSys

data class Staff(
    val id: String,
    val name: String,
    val loginTime: String,
    val token: String,
    val sysList: MutableList<SubSys>
)