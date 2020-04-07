package com.viwave.collaborationproject.DB.remote.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.viwave.collaborationproject.DB.remote.CaseDatabase
import com.viwave.collaborationproject.data.bios.Bio

sealed class BioDataEntity {
    abstract val caseNumber: String
    abstract val sysCode: String
    abstract var data:Bio

    @Entity(tableName = CaseDatabase.tableBioDataBloodPressure)
    data class BloodPressureEntity(
        @PrimaryKey(autoGenerate = true) val id : Int = 0,
        override val caseNumber: String,
        override val sysCode: String,
        var isPendingData: Boolean,
        @Embedded val bloodPressure: Bio.BloodPressure
    )  : BioDataEntity(){
        override var data: Bio
            get() = bloodPressure
            set(value) {}
    }

    @Entity(tableName = CaseDatabase.tableBioDataBloodGlucose)
    data class BloodGlucoseEntity(
        @PrimaryKey(autoGenerate = true) val id : Int = 0,
        override val caseNumber: String,
        override val sysCode: String,
        var isPendingData: Boolean,
        @Embedded val bloodGlucose: Bio.BloodGlucose
    ) : BioDataEntity(){
        override var data: Bio
            get() = bloodGlucose
            set(value) {}
    }

    @Entity(tableName = CaseDatabase.tableBioDataHeight)
    data class HeightEntity(
        @PrimaryKey(autoGenerate = true) val id : Int = 0,
        override val caseNumber: String,
        override val sysCode: String,
        var isPendingData: Boolean,
        @Embedded val height: Bio.Height
    ) : BioDataEntity(){
        override var data: Bio
            get() = height
            set(value) {}
    }

    @Entity(tableName = CaseDatabase.tableBioDataOxygen)
    data class OxygenEntity(
        @PrimaryKey(autoGenerate = true) val id : Int = 0,
        override val caseNumber: String,
        override val sysCode: String,
        var isPendingData: Boolean,
        @Embedded val oxygen: Bio.Oxygen
    ) : BioDataEntity(){
        override var data: Bio
            get() = oxygen
            set(value) {}
    }

    @Entity(tableName = CaseDatabase.tableBioDataPulse)
    data class PulseEntity(
        @PrimaryKey(autoGenerate = true) val id : Int = 0,
        override val caseNumber: String,
        override val sysCode: String,
        var isPendingData: Boolean,
        @Embedded val pulse: Bio.Pulse
    ) : BioDataEntity(){
        override var data: Bio
            get() = pulse
            set(value) {}
    }

    @Entity(tableName = CaseDatabase.tableBioDataRespire)
    data class RespireEntity(
        @PrimaryKey(autoGenerate = true) val id : Int = 0,
        override val caseNumber: String,
        override val sysCode: String,
        var isPendingData: Boolean,
        @Embedded val respire: Bio.Respire
    ) : BioDataEntity(){
        override var data: Bio
            get() = respire
            set(value) {}
    }

    @Entity(tableName = CaseDatabase.tableBioDataTemperature)
    data class TemperatureEntity(
        @PrimaryKey(autoGenerate = true) val id : Int = 0,
        override val caseNumber: String,
        override val sysCode: String,
        var isPendingData: Boolean,
        @Embedded val temperature: Bio.Temperature
    ) : BioDataEntity(){
        override var data: Bio
            get() = temperature
            set(value) {}
    }

    @Entity(tableName = CaseDatabase.tableBioDataWeight)
    data class WeightEntity(
        @PrimaryKey(autoGenerate = true) val id : Int = 0,
        override val caseNumber: String,
        override val sysCode: String,
        var isPendingData: Boolean,
        @Embedded val weight: Bio.Weight
    ) : BioDataEntity(){
        override var data: Bio
            get() = weight
            set(value) {}
    }
}