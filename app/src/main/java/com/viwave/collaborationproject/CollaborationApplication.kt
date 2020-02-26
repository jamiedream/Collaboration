package com.viwave.collaborationproject

import android.app.Application

class CollaborationApplication: Application() {

    companion object{
        lateinit var context: CollaborationApplication
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }

}