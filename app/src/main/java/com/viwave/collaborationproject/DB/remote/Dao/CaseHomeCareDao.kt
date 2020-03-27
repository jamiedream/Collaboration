package com.viwave.collaborationproject.DB.remote.Dao

import androidx.room.Dao
import androidx.room.Query
import com.viwave.collaborationproject.DB.remote.BasicDao
import com.viwave.collaborationproject.DB.remote.CaseDatabase
import com.viwave.collaborationproject.DB.remote.entity.CaseEntity

@Dao
interface CaseHomeCareDao: BasicDao<CaseEntity.CaseHomeCareEntity> {

    @Query("SELECT dataCount FROM ${CaseDatabase.tableHomeCareCase} WHERE caseNumber = :caseNumber")
    fun getDataCountStr(caseNumber: String): String

    @Query("UPDATE ${CaseDatabase.tableHomeCareCase} SET dataCount = :new WHERE caseNumber = :caseNumber")
    fun updateDataCount(caseNumber: String, new: String)

    @Query("SELECT * FROM ${CaseDatabase.tableHomeCareCase}")
    override fun getAll(): MutableList<CaseEntity.CaseHomeCareEntity>

    @Query("DELETE FROM ${CaseDatabase.tableHomeCareCase}")
    override fun deleteAll()

}