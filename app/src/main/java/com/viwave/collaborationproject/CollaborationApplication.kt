package com.viwave.collaborationproject

import com.viwaveulife.vuioht.VUMeasurementApplication

class CollaborationApplication: VUMeasurementApplication() {

    companion object{
        lateinit var context: CollaborationApplication
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }

}