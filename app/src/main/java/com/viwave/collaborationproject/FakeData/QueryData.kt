package com.viwave.collaborationproject.FakeData

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.viwave.collaborationproject.data.cases.Case

class QueryData {

    val aObject =
        JsonObject().apply {
            this.addProperty("sysCode", "A")
            this.addProperty("sysName", "居服")
        }
    val bObject =
        JsonObject().apply {
            this.addProperty("sysCode", "B")
            this.addProperty("sysName", "居護")
        }

    val cObject =
        JsonObject().apply {
            this.addProperty("sysCode", "C")
            this.addProperty("sysName", "活力站")
        }

    val dObject =
        JsonObject().apply {
            this.addProperty("sysCode", "D")
            this.addProperty("sysName", "日照中心")
        }


//    val aObject = JSONObject().put("sysCode", "A").put("sysName", "居服")
//    val bObject = JSONObject().put("sysCode", "B").put("sysName", "居護")
//    val cObject = JSONObject().put("sysCode", "C").put("sysName", "活力站")
//    val dObject = JSONObject().put("sysCode", "D").put("sysName", "日照中心")

//    val sysArray =
//        JSONArray().put(aObject)
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


//    val sysArray2 =
//        JSONArray().put(aObject).put(bObject).put(cObject).put(dObject)
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
            this.addProperty("token", "MDAwMDAxLkphbWllLjE1ODMzMDQyMDQ=")
            this.add("system", sysArray2 as JsonElement)
        }

    val caseList =
        mutableListOf(
            Case("3333333", "賈個案", "M", null, null),
            Case("4444444", "甄個案", "M", null, null)
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