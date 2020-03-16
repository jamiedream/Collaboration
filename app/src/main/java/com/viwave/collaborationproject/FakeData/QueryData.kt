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
            this.addProperty("token", "MDAwMDAxLkphbWllLjE1ODMzMDQyMDQ=")
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
            this.addProperty("token", "MDAwMDAyLkx1Y3kuMjU4MzMwNDIwNA==")
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