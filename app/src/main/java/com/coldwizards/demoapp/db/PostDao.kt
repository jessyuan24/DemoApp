package com.coldwizards.demoapp.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.coldwizards.demoapp.model.Post

/**
 * Created by jess on 19-6-13.
 */
@Dao
interface PostDao {

    @Insert
    fun insert(post: Post)

    @Insert
    fun insert(posts: List<Post>)

    @Update
    fun update(post: Post)

    @Query("SELECT * FROM post ORDER BY postDate DESC")
    fun allPosts(): DataSource.Factory<Int, Post>

}