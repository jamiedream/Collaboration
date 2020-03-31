/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/25/20 1:49 PM
 */

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