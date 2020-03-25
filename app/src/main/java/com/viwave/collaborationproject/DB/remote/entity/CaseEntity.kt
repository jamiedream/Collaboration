package com.viwave.collaborationproject.DB.remote.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.viwave.collaborationproject.DB.remote.CaseDatabase

sealed class CaseEntity {

    abstract var getCaseNumber: String
    abstract var getCaseName: String
    abstract var getCaseGender: String
    abstract var getSCDID: String?
    abstract var getStartTime: String?
    abstract var getIsSupport: Boolean?

    @Entity(tableName = CaseDatabase.tableCareCase)
    data class CaseCareEntity(
        @PrimaryKey val caseNumber: String,
        @ColumnInfo val caseName: String,
        @ColumnInfo val caseGender: String,
        @ColumnInfo val SCDID: String?,
        @ColumnInfo val startTime: String?,
        @ColumnInfo var isSupport: Boolean?
    ): CaseEntity(){
        override var getCaseNumber: String
            get() = caseNumber
            set(value) {}
        override var getCaseName: String
            get() = caseName
            set(value) {}
        override var getCaseGender: String
            get() = caseGender
            set(value) {}
        override var getSCDID: String?
            get() = SCDID
            set(value) {}
        override var getStartTime: String?
            get() = startTime
            set(value) {}
        override var getIsSupport: Boolean?
            get() = isSupport
            set(value) {}

    }

    @Entity(tableName = CaseDatabase.tableNursingCase)
    data class CaseNursingEntity(
        @PrimaryKey val caseNumber: String,
        @ColumnInfo val caseName: String,
        @ColumnInfo val caseGender: String,
        @ColumnInfo val SCDID: String?,
        @ColumnInfo val startTime: String?,
        @ColumnInfo var isSupport: Boolean?
    ): CaseEntity() {
        companion object{}
        override var getCaseNumber: String
            get() = caseNumber
            set(value) {}
        override var getCaseName: String
            get() = caseName
            set(value) {}
        override var getCaseGender: String
            get() = caseGender
            set(value) {}
        override var getSCDID: String?
            get() = SCDID
            set(value) {}
        override var getStartTime: String?
            get() = startTime
            set(value) {}
        override var getIsSupport: Boolean?
            get() = isSupport
            set(value) {}
    }

    @Entity(tableName = CaseDatabase.tableStationCase)
    data class CaseStationEntity(
        @PrimaryKey val caseNumber: String,
        @ColumnInfo val caseName: String,
        @ColumnInfo val caseGender: String,
        @ColumnInfo val SCDID: String?,
        @ColumnInfo val startTime: String?,
        @ColumnInfo var isSupport: Boolean?
    ): CaseEntity(){
        override var getCaseNumber: String
            get() = caseNumber
            set(value) {}
        override var getCaseName: String
            get() = caseName
            set(value) {}
        override var getCaseGender: String
            get() = caseGender
            set(value) {}
        override var getSCDID: String?
            get() = SCDID
            set(value) {}
        override var getStartTime: String?
            get() = startTime
            set(value) {}
        override var getIsSupport: Boolean?
            get() = isSupport
            set(value) {}

    }

    @Entity(tableName = CaseDatabase.tableHomeCareCase)
    data class CaseHomeCareEntity(
        @PrimaryKey val caseNumber: String,
        @ColumnInfo val caseName: String,
        @ColumnInfo val caseGender: String,
        @ColumnInfo val SCDID: String?,
        @ColumnInfo val startTime: String?,
        @ColumnInfo var isSupport: Boolean?
    ): CaseEntity(){
        override var getCaseNumber: String
            get() = caseNumber
            set(value) {}
        override var getCaseName: String
            get() = caseName
            set(value) {}
        override var getCaseGender: String
            get() = caseGender
            set(value) {}
        override var getSCDID: String?
            get() = SCDID
            set(value) {}
        override var getStartTime: String?
            get() = startTime
            set(value) {}
        override var getIsSupport: Boolean?
            get() = isSupport
            set(value) {}

    }
}