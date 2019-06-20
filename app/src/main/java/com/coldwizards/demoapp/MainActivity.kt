package com.coldwizards.demoapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.coldwizards.demoapp.instagram.view.InsActivity
import com.coldwizards.demoapp.customview.TouchDemoActivity
import com.coldwizards.demoapp.ndk.NdkJniUtils
import com.coldwizards.demoapp.utils.Logs

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Toast.makeText(this, NdkJniUtils().string, Toast.LENGTH_SHORT).show()

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun touchDemo(view: View) {
        startActivity(Intent(this, TouchDemoActivity::class.java))
    }

    fun dragDropDemo(view: View) {
        startActivity(Intent(this, DragDropDemoActivity::class.java))
    }

    fun booklibrary(view: View) {
        startActivity(Intent(this, InsActivity::class.java))
    }
}
