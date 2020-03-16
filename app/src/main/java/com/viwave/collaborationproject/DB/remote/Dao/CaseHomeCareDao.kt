package com.viwave.collaborationproject.DB.remote.Dao

import androidx.room.Dao
import androidx.room.Query
import com.viwave.collaborationproject.DB.remote.BasicDao
import com.viwave.collaborationproject.DB.remote.CaseDatabase
import com.viwave.collaborationproject.DB.remote.entity.CaseEntity

@Dao
interface CaseHomeCareDao: BasicDao<CaseEntity.CaseHomeCareEntity> {

    @Query("SELECT * FROM ${CaseDatabase.tableHomeCareCase}")
    override fun getAll(): MutableList<CaseEntity.CaseHomeCareEntity>

    @Query("DELETE FROM ${CaseDatabase.tableHomeCareCase}")
    override fun deleteAll()

}