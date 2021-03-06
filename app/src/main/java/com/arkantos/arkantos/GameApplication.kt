package com.arkantos.arkantos

import android.app.Application
import com.arkantos.arkantos.helpers.SharedPreferenceHelper
import com.facebook.stetho.Stetho

class GameApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        SharedPreferenceHelper.init(this)
    }
}