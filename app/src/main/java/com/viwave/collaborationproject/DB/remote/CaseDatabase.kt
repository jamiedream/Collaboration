package com.viwave.collaborationproject.DB.remote

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.viwave.collaborationproject.DB.remote.CaseDatabase.Companion.tableVersion
import com.viwave.collaborationproject.DB.remote.Dao.CaseCareDao
import com.viwave.collaborationproject.DB.remote.Dao.CaseHomeCareDao
import com.viwave.collaborationproject.DB.remote.Dao.CaseNursingDao
import com.viwave.collaborationproject.DB.remote.Dao.CaseStationDao
import com.viwave.collaborationproject.DB.remote.entity.CaseEntity

@Database(entities = [CaseEntity.CaseCareEntity::class, CaseEntity.CaseNursingEntity::class, CaseEntity.CaseStationEntity::class, CaseEntity.CaseHomeCareEntity::class], version = tableVersion)
abstract class CaseDatabase: RoomDatabase() {

    companion object{
        const val tableVersion = 1
        const val tableCareCase = "tableCareCase"
        const val tableNursingCase = "tableNursingCase"
        const val tableStationCase = "tableStationCase"
        const val tableHomeCareCase = "tableHomeCareCase"

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
}
