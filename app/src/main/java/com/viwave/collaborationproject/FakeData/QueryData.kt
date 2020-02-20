package com.viwave.collaborationproject.FakeData

import com.viwave.collaborationproject.data.cases.Case

class QueryData {

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