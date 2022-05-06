package com.base.ui

import android.app.Application

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        application = this
    }
    companion object {
        private var application: Application? = null
        fun getApplicationContext() = application!!
        private const val LOG_TAG = "BaseApp"
    }
}