package com.coldwizards.demoapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.coldwizards.demoapp.adapter.Converters
import com.coldwizards.demoapp.model.Comment
import com.coldwizards.demoapp.model.Post
import com.coldwizards.demoapp.model.User
import com.coldwizards.demoapp.utils.ioThread

/**
 * Created by jess on 19-6-13.
 */
@Database(entities = arrayOf(Post::class, User::class, Comment::class), version = 1, exportSchema = true)
@TypeConverters(Converters::class)
abstract class InsDB : RoomDatabase() {

    abstract fun postDao(): PostDao
    abstract fun userDao(): UserDao

    companion object {
        private var INSTANCE: InsDB? = null

        @Synchronized
        fun getInstance(context: Context): InsDB {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                    InsDB::class.java, "InsDatabase")
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
//                            fillData(context)
                        }
                    })
                    .build()
            }

            return INSTANCE!!
        }

        private fun fillData(context: Context) {
            ioThread {
                getInstance(context).postDao().insert(
                    POST_DATA.map {
                        Post(0, it.key, arrayListOf("123"), User(it.value, ""))
                    }
                )
            }
        }
    }
}

private val POST_DATA = hashMapOf(
    "asdf" to "asdf", "11111" to "111111"
    , "1111133" to "111111"
    , "2222" to "222"
    , "333" to "333"
    , "444" to "4444"
    , "55555" to "55555"
    , "6666" to "6666"
    , "77777" to "7777"
)