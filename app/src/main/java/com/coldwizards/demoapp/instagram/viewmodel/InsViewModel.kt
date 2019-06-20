package com.coldwizards.demoapp.instagram.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.coldwizards.demoapp.db.InsDB
import com.coldwizards.demoapp.model.User
import com.coldwizards.demoapp.utils.ioThread

/**
 * Created by jess on 19-6-18.
 */
class InsViewModel(app: Application) : AndroidViewModel(app) {

    val mUserLiveData: MutableLiveData<User> = MutableLiveData()

    val userDao = InsDB.getInstance(app).userDao()

    fun setUser(user: User) {
        mUserLiveData.value = user
    }

}