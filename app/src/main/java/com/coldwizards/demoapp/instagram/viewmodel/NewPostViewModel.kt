package com.coldwizards.demoapp.instagram.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.paging.Config
import androidx.paging.toLiveData
import com.coldwizards.demoapp.db.InsDB
import com.coldwizards.demoapp.model.Post
import com.coldwizards.demoapp.model.User
import com.coldwizards.demoapp.utils.ioThread

/**
 * Created by jess on 19-6-13.
 */
class NewPostViewModel(app: Application) : AndroidViewModel(app) {

    val mpostDao = InsDB.getInstance(app).postDao()
    var mUser: User? = null
        get() = field
        set(value) {
            field = value
        }

    fun insert(post: Post) = ioThread {
        mpostDao.insert(post)
    }

    fun update(post: Post) = ioThread {
        mpostDao.update(post)
    }

    fun newPost(content: String, pictures: ArrayList<String>): Int {
        if (mUser == null) {
            return 1
        } else {
            val post = Post(0, content, pictures, mUser!!)
            insert(post)

            return 0
        }
    }

}