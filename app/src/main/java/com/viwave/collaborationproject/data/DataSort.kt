package com.viwave.collaborationproject.data

import com.google.gson.*
import com.viwave.collaborationproject.DB.cache.SysKey
import com.viwave.collaborationproject.DB.remote.DataCount
import com.viwave.collaborationproject.data.bios.Bio
import com.viwave.collaborationproject.data.general.Login
import com.viwave.collaborationproject.data.general.SubSys
import com.viwave.collaborationproject.data.general.User
import com.viwave.collaborationproject.utils.ConvertUtil
import org.json.JSONObject
import java.lang.reflect.Type

object DataSort{

    //login
    fun staff(login: Login): User{
        val token = login.token
        val tokenArray = token.split(".")
        val payload = JSONObject(ConvertUtil.fromBase64(tokenArray[1]))
        val id = payload["id"].toString()
        val name = payload["name"].toString()
        val loginTime = payload["LoginTime"].toString()
        val sysArray = login.system
        val sysList = mutableListOf<SubSys>()
        sysArray.forEach {subSys ->
            subSys.sysCode.apply {
                sysList.add(
                    when(this){
                        SysKey.DAILY_CARE_CODE -> SysKey.DailyCare
                        SysKey.DAILY_NURSING_CODE -> SysKey.DailyNursing
                        SysKey.DAILY_STATION_CODE -> SysKey.Station
                        SysKey.DAILY_HOME_CARE_CODE -> SysKey.HomeCare
                        else -> SysKey.DailyCare
                    }
                )
            }
        }
        return User(id, name, loginTime, token, sysList)
    }

    val staffInfo =
        object: JsonDeserializer<User>{
            override fun deserialize(
                json: JsonElement?,
                typeOfT: Type?,
                context: JsonDeserializationContext?
            ): User? {
                json?.let {
                    val jsonObject = json.asJsonObject
                    val token = jsonObject.get("token").asString
                    val tokenArray = token.split(".")
                    val payload = JSONObject(ConvertUtil.fromBase64(tokenArray[1]))
                    val id = payload["id"].toString()
                    val name = payload["name"].toString()
                    val loginTime = payload["LoginTime"].toString()
                    val sysArray = jsonObject.get("system").asJsonArray
                    val sysList = mutableListOf<SubSys>()
                    sysArray.forEach {subSys ->
                        subSys.asJsonObject.get("sysCode").asString.apply {
                            sysList.add(
                                when(this){
                                    SysKey.DAILY_CARE_CODE -> SysKey.DailyCare
                                    SysKey.DAILY_NURSING_CODE -> SysKey.DailyNursing
                                    SysKey.DAILY_STATION_CODE -> SysKey.Station
                                    SysKey.DAILY_HOME_CARE_CODE -> SysKey.HomeCare
                                    else -> SysKey.DailyCare
                                }
                            )
                        }
                    }
                    return User(id, name, loginTime, token, sysList)
                }
                return null
            }
        }


    //upload
    val numberToString =
        JsonSerializer<Number> { src, _, _ ->
            JsonPrimitive(
                src.toString()
            )
        }

    //count
    const val Temperature = "Temperature"
    const val Pulse = "Pulse"
    const val Respire = "Respire"
    const val BloodPressure = "BloodPressure"
    const val BloodGlucose = "BloodGlucose"
    const val Oxygen = "Oxygen"
    const val Height = "Height"
    const val Weight = "Weight"
    val dataCountList =
        JsonDeserializer<MutableList<DataCount>> { json, _, _ ->
            val list = mutableListOf<DataCount>()
            json.asJsonArray.forEach {data ->
                list.add(
                    DataCount(
                        data.asJsonObject.get("type").asString,
                        data.asJsonObject.get("count").asInt
                        )
                )
            }
            list
        }


    //query
    val bloodPressureList =
        JsonDeserializer<MutableList<Bio.BloodPressure>> { json, _, _ ->
            val list = mutableListOf<Bio.BloodPressure>()
            json?.let {
                when{
                    json.isJsonArray -> {
                        json.asJsonArray.forEach {data ->
                            list.add(
                                Bio.BloodPressure(
                                    data.asJsonObject.get("takenAt").asLong,
                                    data.asJsonObject.get("systolic").asInt,
                                    data.asJsonObject.get("diastolic").asInt,
                                    data.asJsonObject.get("pulse")?.asInt,
                                    data.asJsonObject.get("scene")?.asString,
                                    data.asJsonObject.get("ARR")?.asBoolean,
                                    data.asJsonObject.get("Afib")?.asBoolean,
                                    data.asJsonObject.get("PC")?.asBoolean,
                                    data.asJsonObject.get("IHB")?.asBoolean
                                )
                            )

                        }
                    }
                    json.isJsonObject -> {
                        list.add(
                            Bio.BloodPressure(
                                json.asJsonObject.get("takenAt").asLong,
                                json.asJsonObject.get("systolic").asInt,
                                json.asJsonObject.get("diastolic").asInt,
                                json.asJsonObject.get("pulse")?.asInt,
                                json.asJsonObject.get("scene")?.asString,
                                json.asJsonObject.get("ARR")?.asBoolean,
                                json.asJsonObject.get("Afib")?.asBoolean,
                                json.asJsonObject.get("PC")?.asBoolean,
                                json.asJsonObject.get("IHB")?.asBoolean
                            )
                        )
                    }
                    else -> {}
                }
            }
            list
        }

    val temperatureList =
        JsonDeserializer<MutableList<Bio.Temperature>> { json, _, _ ->
            val list = mutableListOf<Bio.Temperature>()
            json?.let {
                when{
                    json.isJsonArray -> {
                        json.asJsonArray.forEach {data ->
                            list.add(
                                Bio.Temperature(
                                    data.asJsonObject.get("takenAt").asLong,
                                    data.asJsonObject.get("temperature").asFloat
                                )
                            )

                        }
                    }
                    json.isJsonObject -> {
                        list.add(
                            Bio.Temperature(
                                json.asJsonObject.get("takenAt").asLong,
                                json.asJsonObject.get("temperature").asFloat
                            )
                        )
                    }
                    else -> {}
                }
            }
            list
        }

    val glucoseList =
        JsonDeserializer<MutableList<Bio.BloodGlucose>> { json, _, _ ->
            val list = mutableListOf<Bio.BloodGlucose>()
            json?.let {
                when{
                    json.isJsonArray -> {
                        json.asJsonArray.forEach {data ->
                            list.add(
                                Bio.BloodGlucose(
                                    data.asJsonObject.get("takenAt").asLong,
                                    data.asJsonObject.get("bloodGlucose").asInt,
                                    data.asJsonObject.get("meal").asString
                                )
                            )

                        }
                    }
                    json.isJsonObject -> {
                        list.add(
                            Bio.BloodGlucose(
                                json.asJsonObject.get("takenAt").asLong,
                                json.asJsonObject.get("bloodGlucose").asInt,
                                json.asJsonObject.get("meal").asString

                            )
                        )
                    }
                    else -> {}
                }
            }
            list
        }

    val weightList =
        JsonDeserializer<MutableList<Bio.Weight>> { json, _, _ ->
            val list = mutableListOf<Bio.Weight>()
            json?.let {
                when{
                    json.isJsonArray -> {
                        json.asJsonArray.forEach {data ->
                            list.add(
                                Bio.Weight(
                                    data.asJsonObject.get("takenAt").asLong,
                                    data.asJsonObject.get("weight").asFloat,
                                    data.asJsonObject.get("bmi")?.asFloat,
                                    data.asJsonObject.get("bmr")?.asInt,
                                    data.asJsonObject.get("bodyFat")?.asFloat,
                                    data.asJsonObject.get("bodyWater")?.asInt,
                                    data.asJsonObject.get("muscleMass")?.asFloat,
                                    data.asJsonObject.get("visceralFat")?.asInt
                                )
                            )

                        }
                    }
                    json.isJsonObject -> {
                        list.add(
                            Bio.Weight(
                                json.asJsonObject.get("takenAt").asLong,
                                json.asJsonObject.get("weight").asFloat,
                                json.asJsonObject.get("bmi")?.asFloat,
                                json.asJsonObject.get("bmr")?.asInt,
                                json.asJsonObject.get("bodyFat")?.asFloat,
                                json.asJsonObject.get("bodyWater")?.asInt,
                                json.asJsonObject.get("muscleMass")?.asFloat,
                                json.asJsonObject.get("visceralFat")?.asInt
                            )
                        )
                    }
                    else -> {}
                }
            }
            list
        }

    val pulseList =
        JsonDeserializer<MutableList<Bio.Pulse>> { json, _, _ ->
            val list = mutableListOf<Bio.Pulse>()
            json?.let {
                when{
                    json.isJsonArray -> {
                        json.asJsonArray.forEach {data ->
                            list.add(
                                Bio.Pulse(
                                    data.asJsonObject.get("takenAt").asLong,
                                    data.asJsonObject.get("pulse").asInt
                                )
                            )

                        }
                    }
                    json.isJsonObject -> {
                        list.add(
                            Bio.Pulse(
                                json.asJsonObject.get("takenAt").asLong,
                                json.asJsonObject.get("pulse").asInt
                            )
                        )
                    }
                    else -> {}
                }
            }
            list
        }

    val oxygenList =
        JsonDeserializer<MutableList<Bio.Oxygen>> { json, _, _ ->
            val list = mutableListOf<Bio.Oxygen>()
            json?.let {
                when{
                    json.isJsonArray -> {
                        json.asJsonArray.forEach {data ->
                            list.add(
                                Bio.Oxygen(
                                    data.asJsonObject.get("takenAt").asLong,
                                    data.asJsonObject.get("spo2Highest").asInt,
                                    data.asJsonObject.get("spo2Lowest").asInt,
                                    data.asJsonObject.get("pulseHighest")?.asInt,
                                    data.asJsonObject.get("pulseLowest")?.asInt,
                                    data.asJsonObject.get("actHighest")?.asInt,
                                    data.asJsonObject.get("duration")?.asInt
                                )
                            )

                        }
                    }
                    json.isJsonObject -> {
                        list.add(
                            Bio.Oxygen(
                                json.asJsonObject.get("takenAt").asLong,
                                json.asJsonObject.get("spo2Highest").asInt,
                                json.asJsonObject.get("spo2Lowest").asInt,
                                json.asJsonObject.get("pulseHighest")?.asInt,
                                json.asJsonObject.get("pulseLowest")?.asInt,
                                json.asJsonObject.get("actHighest")?.asInt,
                                json.asJsonObject.get("duration")?.asInt
                            )
                        )
                    }
                    else -> {}
                }
            }
            list
        }

    val respireList =
        JsonDeserializer<MutableList<Bio.Respire>> { json, _, _ ->
            val list = mutableListOf<Bio.Respire>()
            json?.let {
                when{
                    json.isJsonArray -> {
                        json.asJsonArray.forEach {data ->
                            list.add(
                                Bio.Respire(
                                    data.asJsonObject.get("takenAt").asLong,
                                    data.asJsonObject.get("respire").asInt
                                )
                            )

                        }
                    }
                    json.isJsonObject -> {
                        list.add(
                            Bio.Respire(
                                json.asJsonObject.get("takenAt").asLong,
                                json.asJsonObject.get("respire").asInt
                            )
                        )
                    }
                    else -> {}
                }
            }
            list
        }

    val heightList =
        JsonDeserializer<MutableList<Bio.Height>> { json, _, _ ->
            val list = mutableListOf<Bio.Height>()
            json?.let {
                when{
                    json.isJsonArray -> {
                        json.asJsonArray.forEach {data ->
                            list.add(
                                Bio.Height(
                                    data.asJsonObject.get("takenAt").asLong,
                                    data.asJsonObject.get("height").asFloat
                                )
                            )

                        }
                    }
                    json.isJsonObject -> {
                        list.add(
                            Bio.Height(
                                json.asJsonObject.get("takenAt").asLong,
                                json.asJsonObject.get("height").asFloat
                            )
                        )
                    }
                    else -> {}
                }
            }
            list
        }
}