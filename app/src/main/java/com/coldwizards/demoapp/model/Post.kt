package com.coldwizards.demoapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by jess on 19-6-13.
 */
@Entity
data class Post(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var content: String,
    var pictures: List<String>?,
    var comments: ArrayList<Comment>?,
    var author: User,
    var likeCount: Int,
    var likeUsers: ArrayList<User>,
    var postDate: Date
) {
    constructor(id:Int, content: String, pictures: List<String>?, author: User)
            :this(id, content, pictures, arrayListOf(), author, 0, arrayListOf(), Date())
}