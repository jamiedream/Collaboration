package com.viwave.collaborationproject.data.bios

import com.google.gson.annotations.SerializedName

sealed class Bio {
    data class BloodPressure(
        @SerializedName("takenAt") val takenAt: Long,
        @SerializedName("value") val sys: Int,
        @SerializedName("value") val dia: Int,
        @SerializedName("note") val note: String
    ): Bio()
    {
        override fun toString(): String {
            return "BloodPressure(takenAt=$takenAt, sys=$sys, dia=$dia, note=$note)"
        }
    }
    data class BloodGlucose(
        @SerializedName("takenAt") val takenAt: String,
        @SerializedName("value") val glucose: String,
        @SerializedName("note") val note: String
    ): Bio()
    {
        override fun toString(): String {
            return "BloodGlucose(takenAt=$takenAt, glucose=$glucose, note=$note)"
        }
    }
    data class Temperature (
        @SerializedName("takenAt") val takenAt: String,
        @SerializedName("value") val temperature: String,
        @SerializedName("note") val note: String = ""
    ): Bio(){
        override fun toString(): String {
            return "Temperature(takenAt='$takenAt', temperature='$temperature', note='$note')"
        }
    }
    data class Weight (
        @SerializedName("takenAt") val takenAt: String,
        @SerializedName("value") val weight: String,
        @SerializedName("note") val note: String = ""
    ): Bio(){
        override fun toString(): String {
            return "Weight(takenAt='$takenAt', weight='$weight', note='$note')"
        }
    }
    data class Pulse (
        @SerializedName("takenAt") val takenAt: String,
        @SerializedName("value") val pulse: String,
        @SerializedName("note") val note: String = ""
    ): Bio(){
        override fun toString(): String {
            return "Pulse(takenAt='$takenAt', pulse='$pulse', note='$note')"
        }
    }
    data class Oxygen (
        @SerializedName("takenAt") val takenAt: String,
        @SerializedName("value") val spo2: String,
        @SerializedName("note") val note: String = ""
    ): Bio(){
        override fun toString(): String {
            return "Oxygen(takenAt='$takenAt', spo2='$spo2', note='$note')"
        }
    }
    data class Respire (
        @SerializedName("takenAt") val takenAt: String,
        @SerializedName("value") val respire: String,
        @SerializedName("note") val note: String = ""
    ): Bio(){
        override fun toString(): String {
            return "Respire(takenAt='$takenAt', respire='$respire', note='$note')"
        }
    }
    data class Height(
        @SerializedName("takenAt") val takenAt: String,
        @SerializedName("value") val height: String,
        @SerializedName("note") val note: String = ""
    ): Bio(){
        override fun toString(): String {
            return "Height(takenAt='$takenAt', height='$height', note='$note')"
        }
    }

}
