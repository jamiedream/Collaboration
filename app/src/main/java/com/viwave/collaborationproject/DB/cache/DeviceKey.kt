/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/25/20 1:49 PM
 */

package com.viwave.collaborationproject.DB.cache

object DeviceKey {

    const val DEVICE_KEY_BP = "Blood Pressure Monitor"
    const val DEVICE_KEY_BG = "Blood Glucose Meters"
    const val DEVICE_KEY_TEMP = "Thermometers"
    const val DEVICE_KEY_OXYGEN = "Pulse Oximeters"
    const val DEVICE_KEY_WEIGHT = "Body Fat Monitors"

    const val DEVICE_X3 = "X3"
    const val DEVICE_X5 = "X5"
    const val DEVICE_HT100 = "HT100"
    const val DEVICE_HS200 = "HS200"
    const val DEVICE_HC700 = "HC700"
    const val DEVICE_SB210 = "SB210"
    const val DEVICE_LS212B = "LS212B"

    fun getSupportDevice(): HashMap<String, MutableList<String>>{
        return hashMapOf<String, MutableList<String>>().apply {
            this[DEVICE_KEY_BP] =
                mutableListOf<String>().apply {
                    this.add(DEVICE_X3)
                }
            this[DEVICE_KEY_BG] =
                mutableListOf<String>().apply {
                    this.add(DEVICE_HT100)
                }
            this[DEVICE_KEY_TEMP] =
                mutableListOf<String>().apply {
                    this.add(DEVICE_HC700)
                }
//            this[DEVICE_KEY_OXYGEN] =
//                mutableListOf<String>().apply {
//                    this.add(DEVICE_SB210)
//                }
//            this[DEVICE_KEY_WEIGHT] =
//                mutableListOf<String>().apply {
//                    this.add(DEVICE_LS212B)
//                }
        }
    }
}