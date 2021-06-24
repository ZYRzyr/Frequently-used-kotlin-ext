package com.zyr.frequently_used_kotlin_ext

import android.app.Application

lateinit var appContext: Application

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }
}
