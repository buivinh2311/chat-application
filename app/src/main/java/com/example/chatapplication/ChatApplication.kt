package com.example.chatapplication

import android.app.Application
import com.cloudinary.android.MediaManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ChatApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        MediaManager.init(
            this,
            mapOf(
                "cloud_name" to "q11fqsai",
                "secure" to true
            )
        )
    }
}