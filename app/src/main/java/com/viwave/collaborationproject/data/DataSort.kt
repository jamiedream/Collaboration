package com.viwave.collaborationproject.data

import com.google.gson.*
import com.viwave.collaborationproject.DB.cache.SysKey
import com.viwave.collaborationproject.data.bios.Bio
import com.viwave.collaborationproject.data.general.SubSys
import com.viwave.collaborationproject.data.general.User
import com.viwave.collaborationproject.utils.ConvertUtil
import java.lang.reflect.Type

object DataSort{

    //todo, check token split symbol
    //login
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
                    val tokenArray = ConvertUtil.fromBase64(token).split(".")
                    val id = tokenArray[0]
                    val name = tokenArray[1]
                    val loginTime = tokenArray[2]
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

    //query
    val bloodPressureList =
        JsonDeserializer<MutableList<Bio.BloodPressure>> { json, _, _ ->
            val list = mutableListOf<Bio.BloodPressure>()
            json?.let {
                when{
                    json.isJsonArray -> {
                        json.asJsonArray.forEach {data ->
                            val sysDia = data.asJsonObject.get("value").asString.split("/")
                            list.add(
                                Bio.BloodPressure(
                                    data.asJsonObject.get("takenAt").asLong,
                                    sysDia[0].toInt(),
                                    sysDia[1].toInt(),
                                    data.asJsonObject.get("note").asString
                                )
                            )

                        }
                    }
                    json.isJsonObject -> {
                        val data = json.asJsonObject
                        val sysDia = data.asJsonObject.get("value").asString.split("/")
                        list.add(
                            Bio.BloodPressure(
                                data.asJsonObject.get("takenAt").asLong,
                                sysDia[0].toInt(),
                                sysDia[1].toInt(),
                                data.asJsonObject.get("note").asString
                            )
                        )
                    }
                    else -> {}
                }
            }
            list
        }

}