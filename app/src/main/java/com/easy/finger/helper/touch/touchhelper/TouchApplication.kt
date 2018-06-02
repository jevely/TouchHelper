package com.easy.finger.helper.touch.touchhelper

import android.app.Application
import android.content.Context

/**
 * application
 */
class TouchApplication : Application() {

    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this.applicationContext
    }

}