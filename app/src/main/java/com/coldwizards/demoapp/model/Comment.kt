package com.coldwizards.demoapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by jess on 19-6-18.
 */
@Entity
data class Comment(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var user: User,
    var content: String
) {
    constructor(user: User, content: String)
            : this(0,user, content)

}