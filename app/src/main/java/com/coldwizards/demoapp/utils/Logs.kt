package com.coldwizards.demoapp.utils

import android.util.Log
import java.lang.Exception

/**
 * Created by jess on 19-6-20.
 */
object Logs {

    var On = true

    fun d(text: String) {
        if (On) {
            Log.d(Exception().stackTrace[1].className, text)
        }
    }

    fun i(text: String) {
        if (On) {
            Log.i(Exception().stackTrace[1].className, text)
        }
    }

    fun v(text: String) {
        if (On) {
            Log.v(Exception().stackTrace[1].className, text)
        }
    }

}