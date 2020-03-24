package com.viwave.collaborationproject.data.bios

import com.google.gson.annotations.SerializedName

sealed class Bio {
    data class BloodPressure(
        @SerializedName("takenAt") val takenAt: Long,
        @SerializedName("systolic") val sys: Int,
        @SerializedName("diastolic") val dia: Int,
        @SerializedName("pulse") val pulse: Int?,
        @SerializedName("scene") val note: String?,
        @SerializedName("ARR") val arr: Boolean?,
        @SerializedName("Afib") val afib: Boolean?,
        @SerializedName("PC") val pc: Boolean?,
        @SerializedName("IHB") val ihb: Boolean?
    ): Bio()
    {
        override fun toString(): String {
            return "BloodPressure(takenAt=$takenAt, sys=$sys, dia=$dia, note=$note, , arr=$arr, afib=$afib, pc=$pc, ihb=$ihb)"
        }
    }
    data class BloodGlucose(
        @SerializedName("takenAt") val takenAt: Long,
        @SerializedName("bloodGlucose") val glucose: Int,
        @SerializedName("meal") val meal: String
    ): Bio()
    {
        override fun toString(): String {
            return "BloodGlucose(takenAt=$takenAt, glucose=$glucose, meal=$meal)"
        }
    }

    data class Temperature (
        @SerializedName("takenAt") val takenAt: Long,
        @SerializedName("temperature") val temperature: Float
    ): Bio(){

        companion object{}
        override fun toString(): String {
            return "Temperature(takenAt='$takenAt', temperature='$temperature')"
        }
    }

    data class Weight (
        @SerializedName("takenAt") val takenAt: Long,
        @SerializedName("weight") val weight: Float,
        @SerializedName("bmi") val bmi: Float?,
        @SerializedName("bmr") val bmr: Int?,
        @SerializedName("bodyFat") val bodyFat: Float?,
        @SerializedName("bodyWater") val bodyWater: Int?,
        @SerializedName("muscleMass") val muscleMass: Float?,
        @SerializedName("visceralFat") val visceralFat: Int?
    ): Bio(){
        override fun toString(): String {
            return "Weight(takenAt='$takenAt', weight='$weight, , bmi='$bmi, bmr='$bmr, bodyFat='$bodyFat, bodyWater='$bodyWater, muscleMass='$muscleMass, visceralFat='$visceralFat')"
        }
    }
    data class Pulse (
        @SerializedName("takenAt") val takenAt: Long,
        @SerializedName("pulse") val pulse: Int
    ): Bio(){
        companion object{}
        override fun toString(): String {
            return "Pulse(takenAt='$takenAt', pulse='$pulse')"
        }
    }
    data class Oxygen (
        @SerializedName("takenAt") val takenAt: Long,
        @SerializedName("spo2Highest") val spo2Highest: Int,
        @SerializedName("spo2Lowest") val spo2Lowest: Int,
        @SerializedName("pulseHighest") val pulseHighest: Int,
        @SerializedName("pulseLowest") val pulseLowest: Int,
        @SerializedName("actHighest") val actHighest: Int,
        @SerializedName("duration") val duration: Int
    ): Bio(){
        override fun toString(): String {
            return "Oxygen(takenAt='$takenAt', spo2Highest='$spo2Highest', spo2Lowest='$spo2Lowest', pulseHighest='$pulseHighest', pulseLowest='$pulseLowest', actHighest='$actHighest', duration='$duration')"
        }
    }
    data class Respire (
        @SerializedName("takenAt") val takenAt: Long,
        @SerializedName("respire") val respire: Int
    ): Bio(){
        override fun toString(): String {
            return "Respire(takenAt='$takenAt', respire='$respire'')"
        }
    }
    data class Height(
        @SerializedName("takenAt") val takenAt: Long,
        @SerializedName("height") val height: Float
    ): Bio(){
        override fun toString(): String {
            return "Height(takenAt='$takenAt', height='$height'')"
        }
    }

}
