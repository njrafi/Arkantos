package com.arkantos.arkantos

import android.app.Application
import com.facebook.stetho.Stetho
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class GameApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }
}