package com.coldwizards.demoapp.ndk

/**
 * Created by jess on 19-6-11.
 */
class NdkJniUtils {

    val string: String
        external get

    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }
}
