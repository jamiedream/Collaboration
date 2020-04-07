/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/30/20 10:14 AM
 */

package com.viwave.collaborationproject.DB.remote.Dao

import androidx.room.Dao
import androidx.room.Query
import com.viwave.collaborationproject.DB.remote.BasicDao
import com.viwave.collaborationproject.DB.remote.CaseDatabase
import com.viwave.collaborationproject.DB.remote.entity.CaseEntity

@Dao
interface CaseNursingDao: BasicDao<CaseEntity.CaseNursingEntity> {

    @Query("SELECT dataCount FROM ${CaseDatabase.tableNursingCase} WHERE caseNumber = :caseNumber")
    fun getDataCountStr(caseNumber: String): String

    @Query("UPDATE ${CaseDatabase.tableNursingCase} SET dataCount = :new WHERE caseNumber = :caseNumber")
    fun updateDataCount(caseNumber: String, new: String)

    @Query("SELECT * FROM ${CaseDatabase.tableNursingCase}")
    override fun getAll(): MutableList<CaseEntity.CaseNursingEntity>

    @Query("DELETE FROM ${CaseDatabase.tableNursingCase}")
    override fun deleteAll()

    @Query("SELECT * FROM ${CaseDatabase.tableNursingCase} WHERE caseNumber = :caseNumber")
    override fun search(caseNumber: String): CaseEntity.CaseNursingEntity
}