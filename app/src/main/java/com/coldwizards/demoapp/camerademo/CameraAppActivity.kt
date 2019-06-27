package com.coldwizards.demoapp.camerademo

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import com.coldwizards.demoapp.R
import kotlinx.android.synthetic.main.activity_camera_demo.*
import java.io.File

/**
 * Created by jess on 19-6-26.
 */
class CameraAppActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_demo)

        supportActionBar?.title = "Camera App"

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, CameraFragment.newInstance(), null).commit()
    }

}