package com.viwave.collaborationproject.DB.remote.Dao

import androidx.room.*
import com.viwave.collaborationproject.CollaborationApplication
import com.viwave.collaborationproject.DB.cache.SysKey
import com.viwave.collaborationproject.DB.remote.CaseDatabase
import com.viwave.collaborationproject.DB.remote.entity.BioDataEntity
import com.viwave.collaborationproject.DB.remote.entity.CaseEntity
import com.viwave.collaborationproject.data.bios.Bio
import com.viwave.collaborationproject.data.cases.Case
import com.viwave.collaborationproject.utils.LogUtil
import java.util.*
import kotlin.collections.ArrayList

@Dao
interface BioDao {

    //BP
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBP(entity: BioDataEntity.BloodPressureEntity)

    @Query("DELETE FROM ${CaseDatabase.tableBioDataBloodPressure}")
    fun deleteAllBP()

    @Query("SELECT * FROM ${CaseDatabase.tableBioDataBloodPressure} WHERE caseNumber = :caseNumber")
    fun getAllBP(caseNumber: String): List<BioDataEntity.BloodPressureEntity>

    @Query("SELECT * FROM ${CaseDatabase.tableBioDataBloodPressure} isPendingData" )
    fun getPendingBP(): List<BioDataEntity.BloodPressureEntity>

    @Query("UPDATE ${CaseDatabase.tableBioDataBloodPressure} SET isPendingData = 'FALSE' WHERE id = :id" )
    fun updateBPUploadSuccess(id: Long)

    //Blood Glucose
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBG(entity: BioDataEntity.BloodGlucoseEntity)

    @Query("DELETE FROM ${CaseDatabase.tableBioDataBloodGlucose}")
    fun deleteAllBG()

    @Query("SELECT * FROM ${CaseDatabase.tableBioDataBloodGlucose} WHERE caseNumber = :caseNumber")
    fun getAllBG(caseNumber: String): List<BioDataEntity.BloodGlucoseEntity>

    @Query("SELECT * FROM ${CaseDatabase.tableBioDataBloodGlucose} WHERE caseNumber = :caseNumber AND isPendingData" )
    fun getPendingBG(): List<BioDataEntity.BloodGlucoseEntity>

    @Query("UPDATE ${CaseDatabase.tableBioDataBloodGlucose} SET isPendingData = 'FALSE' WHERE id = :id" )
    fun updateBGUploadSuccess(id: Long)


    //Temperature
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTemperature(entity: BioDataEntity.TemperatureEntity)

    @Query("DELETE FROM ${CaseDatabase.tableBioDataTemperature}")
    fun deleteAllTemperature()

    @Query("SELECT * FROM ${CaseDatabase.tableBioDataTemperature} WHERE caseNumber = :caseNumber")
    fun getAllTemperature(caseNumber: String): List<BioDataEntity.TemperatureEntity>

    @Query("SELECT * FROM ${CaseDatabase.tableBioDataTemperature} WHERE isPendingData" )
    fun getPendingTemperature(): List<BioDataEntity.TemperatureEntity>

    @Query("UPDATE ${CaseDatabase.tableBioDataTemperature} SET isPendingData = 'FALSE' WHERE id = :id" )
    fun updateTemperatureUploadSuccess(id: Long)


    //Weight
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeight(entity: BioDataEntity.WeightEntity)

    @Query("DELETE FROM ${CaseDatabase.tableBioDataWeight}")
    fun deleteAllWeight()

    @Query("SELECT * FROM ${CaseDatabase.tableBioDataWeight} WHERE caseNumber = :caseNumber")
    fun getAllWeight(caseNumber: String): List<BioDataEntity.WeightEntity>

    @Query("SELECT * FROM ${CaseDatabase.tableBioDataWeight} WHERE isPendingData" )
    fun getPendingWeight(): List<BioDataEntity.WeightEntity>

    @Query("UPDATE ${CaseDatabase.tableBioDataWeight} SET isPendingData = 'FALSE' WHERE id = :id" )
    fun updateWeightUploadSuccess(id: Long)

    //Pulse
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPulse(entity: BioDataEntity.PulseEntity)

    @Query("DELETE FROM ${CaseDatabase.tableBioDataPulse}")
    fun deleteAllPulse()

    @Query("SELECT * FROM ${CaseDatabase.tableBioDataPulse} WHERE caseNumber = :caseNumber")
    fun getAllPulse(caseNumber: String): List<BioDataEntity.PulseEntity>

    @Query("SELECT * FROM ${CaseDatabase.tableBioDataPulse} WHERE isPendingData" )
    fun getPendingPulse(): List<BioDataEntity.PulseEntity>

    @Query("UPDATE ${CaseDatabase.tableBioDataPulse} SET isPendingData = 'FALSE' WHERE id = :id" )
    fun updatePulseUploadSuccess(id: Long)

    //Oxygen
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOxygen(entity: BioDataEntity.OxygenEntity)

    @Query("DELETE FROM ${CaseDatabase.tableBioDataOxygen}")
    fun deleteAllOxygen()

    @Query("SELECT * FROM ${CaseDatabase.tableBioDataOxygen} WHERE caseNumber = :caseNumber")
    fun getAllOxygen(caseNumber: String): List<BioDataEntity.OxygenEntity>

    @Query("SELECT * FROM ${CaseDatabase.tableBioDataOxygen} WHERE isPendingData" )
    fun getPendingOxygen(): List<BioDataEntity.OxygenEntity>

    @Query("UPDATE ${CaseDatabase.tableBioDataOxygen} SET isPendingData = 'FALSE' WHERE id = :id" )
    fun updateOxygenUploadSuccess(id: Long)

    //Respire
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRespire(entity: BioDataEntity.RespireEntity)

    @Query("DELETE FROM ${CaseDatabase.tableBioDataRespire}")
    fun deleteAllRespire()

    @Query("SELECT * FROM ${CaseDatabase.tableBioDataRespire} WHERE caseNumber = :caseNumber")
    fun getAllRespire(caseNumber: String): List<BioDataEntity.RespireEntity>

    @Query("SELECT * FROM ${CaseDatabase.tableBioDataRespire} WHERE isPendingData" )
    fun getPendingRespire(): List<BioDataEntity.RespireEntity>

    @Query("UPDATE ${CaseDatabase.tableBioDataRespire} SET isPendingData = 'FALSE' WHERE id = :id" )
    fun updateRespireUploadSuccess(id: Long)

    //Height
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHeight(entity: BioDataEntity.HeightEntity)

    @Query("DELETE FROM ${CaseDatabase.tableBioDataHeight}")
    fun deleteAllHeight()

    @Query("SELECT * FROM ${CaseDatabase.tableBioDataHeight} WHERE caseNumber = :caseNumber")
    fun getAllHeight(caseNumber: String): List<BioDataEntity.HeightEntity>

    @Query("SELECT * FROM ${CaseDatabase.tableBioDataHeight} WHERE isPendingData" )
    fun getPendingHeight(): List<BioDataEntity.HeightEntity>

    @Query("UPDATE ${CaseDatabase.tableBioDataHeight} SET isPendingData = 'FALSE' WHERE id = :id" )
    fun updateHeightUploadSuccess(id: Long)
}