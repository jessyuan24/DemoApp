package com.coldwizards.demoapp.instagram.viewmodel

import android.app.Activity
import android.app.Application
import android.app.SharedElementCallback
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.coldwizards.demoapp.db.InsDB
import com.coldwizards.demoapp.instagram.view.BaseActivity
import com.coldwizards.demoapp.model.User
import com.coldwizards.demoapp.utils.ioThread
import io.reactivex.Maybe
import java.util.*

/**
 * Created by jess on 19-6-20.
 */
class LoginViewModel(app: Application) : AndroidViewModel(app) {

    private val mUserDao = InsDB.getInstance(app).userDao()

    fun fillLastUser(): LiveData<Array<User>> {
        return mUserDao.findLoginedUser()
    }

    fun login(
        lifecycle: LifecycleOwner, username: String, password: String,
        callback: (Int, User?) -> Unit
    ) {
        val liveData = mUserDao.findUserByUsername(username)
        liveData.observe(lifecycle, Observer {
            if (it.isNotEmpty()) {
                val user = it[0]
                if (user.password != password) {
                    callback(1, null)
                } else {
                    // 修改用户登录状态和时间
                    ioThread {
                        val allUser = mUserDao.queryAllUser()
                        allUser.forEach {
                            if (it.username == user.username) {
                                it.logined = true
                                it.loginDate = Date()
                            } else {
                                it.logined = false
                            }
                        }
                        mUserDao.updateUser(allUser)
                    }
                    callback(0, user)
                }
            } else {
                callback(2, null)
            }

            liveData.removeObservers(lifecycle)
        })
    }

}