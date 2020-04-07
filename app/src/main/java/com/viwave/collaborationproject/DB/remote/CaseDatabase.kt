/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/27/20 1:40 PM
 */

package com.viwave.collaborationproject.DB.remote

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.viwave.collaborationproject.DB.remote.CaseDatabase.Companion.tableVersion
import com.viwave.collaborationproject.DB.remote.Dao.*
import com.viwave.collaborationproject.DB.remote.entity.BioDataEntity
import com.viwave.collaborationproject.DB.remote.entity.CaseEntity

@Database(entities =
    [CaseEntity.CaseCareEntity::class, CaseEntity.CaseNursingEntity::class,
     CaseEntity.CaseStationEntity::class, CaseEntity.CaseHomeCareEntity::class,
     BioDataEntity.BloodPressureEntity::class, BioDataEntity.BloodGlucoseEntity::class,
     BioDataEntity.HeightEntity::class, BioDataEntity.OxygenEntity::class,
     BioDataEntity.PulseEntity::class, BioDataEntity.RespireEntity::class,
     BioDataEntity.TemperatureEntity::class, BioDataEntity.WeightEntity::class
    ], version = tableVersion)

abstract class CaseDatabase: RoomDatabase() {

    companion object{
        const val tableVersion = 1
        const val tableCareCase = "tableCareCase"
        const val tableNursingCase = "tableNursingCase"
        const val tableStationCase = "tableStationCase"
        const val tableHomeCareCase = "tableHomeCareCase"
        const val tableBioDataBloodPressure = "tableBloodPressure"
        const val tableBioDataBloodGlucose = "tableBloodGlucose"
        const val tableBioDataTemperature = "tableTemperature"
        const val tableBioDataWeight = "tableWeight"
        const val tableBioDataPulse = "tablePulse"
        const val tableBioDataOxygen = "tableOxygen"
        const val tableBioDataRespire = "tableRespire"
        const val tableBioDataHeight = "tableHeight"

        @Volatile private var instance: CaseDatabase? = null
        private val LOCK = Any()
        const val tableCase = "tableCase"
        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context, CaseDatabase::class.java, tableCase).build()
    }

    abstract fun getCaseCareDao(): CaseCareDao
    abstract fun getCaseNursingDao(): CaseNursingDao
    abstract fun getCaseStationDao(): CaseStationDao
    abstract fun getCaseHomeCareDao(): CaseHomeCareDao
    abstract fun getBioDao(): BioDao
}
