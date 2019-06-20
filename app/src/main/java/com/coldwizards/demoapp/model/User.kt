package com.coldwizards.demoapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

/**
 * Created by jess on 19-6-13.
 */
@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val username: String,
    val password: String,
    var avator: String,
    var logined: Boolean,
    var loginDate: Date
): Serializable {
    constructor(username: String, password: String):this(0,username, password,"", false,Date())

    fun isUserNameEmpty(): Boolean {
        return username.isEmpty()
    }
}