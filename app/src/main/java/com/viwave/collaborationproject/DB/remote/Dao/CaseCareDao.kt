/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/30/20 10:14 AM
 */

package com.viwave.collaborationproject.DB.remote.Dao

import androidx.room.Dao
import androidx.room.Query
import com.viwave.collaborationproject.DB.remote.BasicDao
import com.viwave.collaborationproject.DB.remote.CaseDatabase.Companion.tableCareCase
import com.viwave.collaborationproject.DB.remote.entity.CaseEntity

@Dao
interface CaseCareDao: BasicDao<CaseEntity.CaseCareEntity> {

    @Query("SELECT dataCount FROM $tableCareCase WHERE caseNumber = :caseNumber")
    fun getDataCountStr(caseNumber: String): String

    @Query("UPDATE $tableCareCase SET dataCount = :new WHERE caseNumber = :caseNumber")
    fun updateDataCount(caseNumber: String, new: String)

    @Query("SELECT * FROM $tableCareCase")
    override fun getAll(): MutableList<CaseEntity.CaseCareEntity>

    @Query("DELETE FROM $tableCareCase")
    override fun deleteAll()

    @Query("SELECT * FROM $tableCareCase WHERE caseNumber = :caseNumber")
    override fun search(caseNumber: String): CaseEntity.CaseCareEntity
}