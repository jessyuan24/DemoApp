package com.coldwizards.demoapp.instagram.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.paging.Config
import androidx.paging.toLiveData
import com.coldwizards.demoapp.db.InsDB
import com.coldwizards.demoapp.model.Comment
import com.coldwizards.demoapp.model.Post
import com.coldwizards.demoapp.model.User
import com.coldwizards.demoapp.utils.ioThread

/**
 * Created by jess on 19-6-20.
 */
class PostListViewModel(app: Application) : AndroidViewModel(app) {

    val mpostDao = InsDB.getInstance(app).postDao()

    val mAllPosts = mpostDao.allPosts().toLiveData(Config(20, enablePlaceholders = true))

    var mUser: User? = null
        get() = field
        set(value) {
            if (value != null) {
                field = value
            }
        }

    fun isLogin(): Boolean {
        return mUser != null
    }

    fun insert(post: Post) = ioThread {
        mpostDao.insert(post)
    }

    fun update(post: Post) = ioThread {
        mpostDao.update(post)
    }

    fun newComment(post: Post, text: String) {
        post.comments?.add(
            Comment(mUser!!, text)
        )

        update(post)
    }

}