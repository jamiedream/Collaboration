package com.viwave.collaborationproject.DB.remote.Dao

import androidx.room.Dao
import androidx.room.Query
import com.viwave.collaborationproject.DB.remote.BasicDao
import com.viwave.collaborationproject.DB.remote.CaseDatabase
import com.viwave.collaborationproject.DB.remote.entity.CaseEntity

@Dao
interface CaseStationDao: BasicDao<CaseEntity.CaseStationEntity> {

    @Query("SELECT * FROM ${CaseDatabase.tableStationCase}")
    override fun getAll(): MutableList<CaseEntity.CaseStationEntity>

    @Query("DELETE FROM ${CaseDatabase.tableStationCase}")
    override fun deleteAll()

}