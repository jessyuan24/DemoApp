package com.coldwizards.demoapp.instagram.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.coldwizards.demoapp.R
import com.coldwizards.demoapp.instagram.viewmodel.InsViewModel


/**
 * Created by jess on 19-6-13.
 */
class InsActivity : AppCompatActivity() {

    val userViewModel by lazy {
        ViewModelProviders.of(this).get(InsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)

        setToolbar()
    }

    private fun setToolbar() {
        supportActionBar?.title = "Instagram"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

}