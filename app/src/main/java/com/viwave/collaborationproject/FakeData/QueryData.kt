package com.viwave.collaborationproject.FakeData

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.viwave.collaborationproject.DB.cache.SysKey.DAILY_CARE_CODE
import com.viwave.collaborationproject.DB.cache.SysKey.DAILY_CARE_NAME
import com.viwave.collaborationproject.DB.cache.SysKey.DAILY_HOME_CARE_CODE
import com.viwave.collaborationproject.DB.cache.SysKey.DAILY_HOME_CARE_NAME
import com.viwave.collaborationproject.DB.cache.SysKey.DAILY_NURSING_CODE
import com.viwave.collaborationproject.DB.cache.SysKey.DAILY_NURSING_NAME
import com.viwave.collaborationproject.DB.cache.SysKey.DAILY_STATION_CODE
import com.viwave.collaborationproject.DB.cache.SysKey.DAILY_STATION_NAME
import com.viwave.collaborationproject.data.cases.Case

class QueryData {

    val aObject =
        JsonObject().apply {
            this.addProperty("sysCode", DAILY_CARE_CODE)
            this.addProperty("sysName", DAILY_CARE_NAME)
        }
    val bObject =
        JsonObject().apply {
            this.addProperty("sysCode", DAILY_NURSING_CODE)
            this.addProperty("sysName", DAILY_NURSING_NAME)
        }

    val cObject =
        JsonObject().apply {
            this.addProperty("sysCode", DAILY_STATION_CODE)
            this.addProperty("sysName", DAILY_STATION_NAME)
        }

    val dObject =
        JsonObject().apply {
            this.addProperty("sysCode", DAILY_HOME_CARE_CODE)
            this.addProperty("sysName", DAILY_HOME_CARE_NAME)
        }

    val sysArray =
        JsonArray().apply {
            this.add(aObject)
        }

    val loginReturn =
        JsonObject().apply {
            this.addProperty("res", "1")
            this.addProperty("msg", "1")
            this.addProperty("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6InAwMDAxMiIsIm5hbWUiOiLnsKFPTyIsIkxvZ2luVGltZSI6IjIwMjAtMDMtMTggMTA6NDYifQ.l7UH2QuunhY-CsOhrlPJBwTqw8sU5cyw0Jw1qQVociQ=")
            this.add("system", sysArray as JsonElement)
        }

    val sysArray2 =
        JsonArray().apply {
            this.add(aObject)
            this.add(bObject)
            this.add(cObject)
            this.add(dObject)
        }

    val loginReturn2 =
        JsonObject().apply {
            this.addProperty("res", "1")
            this.addProperty("msg", "1")
            this.addProperty("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6InAwMDAxMiIsIm5hbWUiOiLnsKFPTyIsIkxvZ2luVGltZSI6IjIwMjAtMDMtMTggMTA6NDYifQ.l7UH2QuunhY-CsOhrlPJBwTqw8sU5cyw0Jw1qQVociQ=")
            this.add("system", sysArray2 as JsonElement)
        }

    val caseList =
        mutableListOf(
            Case("3333333", "賈個案", "M", null, null),
            Case("4444444", "甄個案", "M", null, null)
        )

    val caseList2 =
        mutableListOf(
            Case("3333333", "臼個案", "M", null, null),
            Case("4444444", "辛個案", "M", null, null)
        )

    val tempData =
        JsonArray().apply {
            this.add(
                JsonObject().apply {
                    this.addProperty("takenAt", "1584343726")
                    this.addProperty("temperature", "36.8")
                }
            )

            this.add(
                JsonObject().apply {
                    this.addProperty("takenAt", "1583998126")
                    this.addProperty("temperature", "37.1")
                }
            )

            this.add(
                JsonObject().apply {
                    this.addProperty("takenAt", "1583911726")
                    this.addProperty("temperature", "36.6")
                }
            )
        }

    val pulseData =
        JsonArray().apply {
            this.add(
                JsonObject().apply {
                    this.addProperty("takenAt", "1584343726")
                    this.addProperty("pulse", "82")
                }
            )

            this.add(
                JsonObject().apply {
                    this.addProperty("takenAt", "1583998126")
                    this.addProperty("pulse", "103")
                }
            )

            this.add(
                JsonObject().apply {
                    this.addProperty("takenAt", "1583911726")
                    this.addProperty("pulse", "91")
                }
            )
        }

    val weightData =
        JsonArray().apply {
            this.add(
                JsonObject().apply {
                    this.addProperty("takenAt", "1584343726")
                    this.addProperty("weight", "66.8")
                }
            )

            this.add(
                JsonObject().apply {
                    this.addProperty("takenAt", "1583998126")
                    this.addProperty("weight", "166.8")
                }
            )

            this.add(
                JsonObject().apply {
                    this.addProperty("takenAt", "1583911726")
                    this.addProperty("weight", "76.8")
                }
            )
        }

    val glucoseData =
        JsonArray().apply {
            this.add(
                JsonObject().apply {
                    this.addProperty("takenAt", "1584343726")
                    this.addProperty("bloodGlucose", "90")
                    this.addProperty("meal", "空腹")
                }
            )

            this.add(
                JsonObject().apply {
                    this.addProperty("takenAt", "1583998126")
                    this.addProperty("bloodGlucose", "118")
                    this.addProperty("meal", "餐後")
                }
            )

            this.add(
                JsonObject().apply {
                    this.addProperty("takenAt", "1583911726")
                    this.addProperty("bloodGlucose", "103")
                    this.addProperty("meal", "空腹")
                }
            )
        }

    val bpData =
        JsonArray().apply {
            this.add(
                JsonObject().apply {
                    this.addProperty("takenAt", "1584343726")
                    this.addProperty("systolic", "218")
                    this.addProperty("diastolic", "133")
                }
            )

            this.add(
                JsonObject().apply {
                    this.addProperty("takenAt", "1583998126")
                    this.addProperty("systolic", "121")
                    this.addProperty("diastolic", "80")
                }
            )

            this.add(
                JsonObject().apply {
                    this.addProperty("takenAt", "1583911726")
                    this.addProperty("systolic", "132")
                    this.addProperty("diastolic", "91")
                }
            )
        }


    val data =
        "{\n" +
                "    {\n" +
                "        type: \"血糖\",\n" +
                "       \"dataSet\": [{\n" +
                "            \"takenAt\": \"1581650233\",\n" +
                "            \"value\": \"105\",\n" +
                "            \"note\": null？\n" +
                "        }, \n" +
                "        {\n" +
                "            \"takenAt\": \"1581650233\",\n" +
                "            \"value\": \"93\",\n" +
                "            \"note\": null\n" +
                "        }] \n" +
                "    },\n" +
                "    {\n" +
                "        type: \"血壓\",\n" +
                "       \"dataSet\": [{\n" +
                "            \"takenAt\": \"1581650233\",\n" +
                "            \"value\": \"114/76\",\n" +
                "            \"note\": null\n" +
                "        }, {\n" +
                "            \"takenAt\": \"1581650233\",\n" +
                "            \"value\": \"113/70\",\n" +
                "            \"note\": null\n" +
                "        }]\n" +
                "    },\n" +
                "    {\n" +
                "        type: \"體溫\",\n" +
                "       \"dataSet\": [{\n" +
                "            \"takenAt\": \"1581650233\",\n" +
                "            \"value\": \"36.9\",\n" +
                "            \"note\": null\n" +
                "        }, {\n" +
                "            \"takenAt\": \"1581650233\",\n" +
                "            \"value\": \"36.1\",\n" +
                "            \"note\": null\n" +
                "        }]\n" +
                "    },\n" +
                "    {\n" +
                "        type: \"脈搏\",\n" +
                "       \"dataSet\": [{\n" +
                "            \"takenAt\": \"1581650233\",\n" +
                "            \"value\": \"90\",\n" +
                "            \"note\": null\n" +
                "        }, {\n" +
                "            \"takenAt\": \"1581650233\",\n" +
                "            \"value\": \"92\",\n" +
                "            \"note\": null\n" +
                "        }]\n" +
                "    }\n" +
                "}"
}