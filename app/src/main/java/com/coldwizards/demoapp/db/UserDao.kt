package com.coldwizards.demoapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.coldwizards.demoapp.model.User
import io.reactivex.Maybe

/**
 * Created by jess on 19-6-18.
 */
@Dao
interface UserDao {

    @Insert
    fun insertUser(user: User)

    @Query("SELECT * FROM user WHERE id=:id")
    fun queryUser(id: Int): LiveData<User>

    @Query("SELECT * FROM user")
    fun queryAllUser(): Array<User>

    @Update
    fun updateUser(user: User)

    @Update
    fun updateUser(users: Array<User>)


    @Query("SELECT * FROM user WHERE username=:username")
    fun findUserByUsername(username: String): LiveData<Array<User>>

    @Query("SELECT * FROM user WHERE logined = 1")
    fun findLoginedUser(): LiveData<Array<User>>
}