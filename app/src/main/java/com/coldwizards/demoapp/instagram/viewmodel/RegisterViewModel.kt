package com.coldwizards.demoapp.instagram.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.coldwizards.demoapp.App
import com.coldwizards.demoapp.db.InsDB
import com.coldwizards.demoapp.model.User
import com.coldwizards.demoapp.utils.ioThread
import io.reactivex.Maybe

/**
 * Created by jess on 19-6-20.
 */
class RegisterViewModel( app: Application): AndroidViewModel(app) {

    private var mUser: User? = null
    private val mUserDao = InsDB.getInstance(app).userDao()

    fun check(username: String, password: String, confirm: String):Int {
        if (username.isEmpty()) {
            return 1
        }

        if (password.isEmpty()) {
            return 2
        }

        if (confirm.isEmpty()) {
            return 3
        }

        if (password != confirm) {
            return 4
        }

        return 0
    }

    fun exist(username: String): LiveData<Array<User>> = mUserDao.findUserByUsername(username)

    fun register(user: User) = ioThread {
        mUserDao.insertUser(user)
    }
}