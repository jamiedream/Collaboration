package com.viwave.collaborationproject.data.bios

data class BioUpload(
    val caseNo: String,
    val staffId: String,
    val SCDTID: String,
    val sysCode: String,
    val type: String,
    val takenAt: String,
    val value: String,
    val note: String
)