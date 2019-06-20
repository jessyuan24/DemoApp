package com.coldwizards.demoapp

import android.app.Application
import com.facebook.stetho.Stetho

/**
 * Created by jess on 19-6-13.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)
    }

}