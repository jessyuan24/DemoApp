package com.coldwizards.demoapp.utils

import android.content.Context
import android.os.Environment
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by jess on 19-6-14.
 */
object FileUtils {

    @Throws(IOException::class)
    fun createImageFile(context: Context): File {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "IMG_$timestamp"

        val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        if (!dir.exists()) {
            dir.mkdirs()
        }

        return File.createTempFile(imageFileName, ".jpg", dir)
    }

}