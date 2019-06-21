package com.coldwizards.demoapp.adapter

import androidx.room.TypeConverter
import com.coldwizards.demoapp.model.Comment
import com.coldwizards.demoapp.model.User
import com.google.gson.Gson
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet


/**
 * Created by jess on 19-6-13.
 */
class Converters {

    companion object {

        @TypeConverter
        @JvmStatic
        fun stringListToString(strings: List<String>?): String{
            if (strings == null) {
                return ""
            }
            return Gson().toJson(strings)
        }

        @TypeConverter
        @JvmStatic
        fun stringToStringList(string: String): List<String>{
            if (string.isEmpty()) {
                return arrayListOf()
            }
            return Gson().fromJson(string, Array<String>::class.java).toList()
        }

        @TypeConverter
        @JvmStatic
        fun userToString(user: User): String{
            return Gson().toJson(user)
        }

        @TypeConverter
        @JvmStatic
        fun StringToUser(string: String): User{
            return Gson().fromJson(string, User::class.java)
        }

        @TypeConverter
        @JvmStatic
        fun commentsToString(comments: ArrayList<Comment>): String{
            return Gson().toJson(comments)
        }

        @TypeConverter
        @JvmStatic
        fun stringTOComments(string: String): ArrayList<Comment>{
            val list = ArrayList(Gson().fromJson(string, Array<Comment>::class.java).toList())
            if (list.isEmpty()) {
                return arrayListOf()
            }
            return  list
        }

        @TypeConverter
        @JvmStatic
        fun usersToString(users: ArrayList<User>): String{
            return Gson().toJson(users)
        }

        @TypeConverter
        @JvmStatic
        fun stringToUsers(string: String): ArrayList<User>{
            val list = ArrayList(Gson().fromJson(string, Array<User>::class.java).toList())
            if (list.isEmpty()) {
                return arrayListOf()
            }
            return  list
        }

        @TypeConverter
        @JvmStatic
        fun fromTimestamp(value: Long?): Date? {
            return if (value == null) null else Date(value)
        }

        @TypeConverter
        @JvmStatic
        fun dateToTimestamp(date: Date?): Long? {
            return (if (date == null) 0 else date!!.getTime()).toLong()
        }

        @TypeConverter
        @JvmStatic
        fun fromSetString(sets: HashSet<String>): String {
            return Gson().toJson(sets)
        }

        @TypeConverter
        @JvmStatic
        fun stringToSet(string: String): HashSet<String> {
            var sets = hashSetOf<String>()
            return Gson().fromJson(string, sets.javaClass)
        }

    }

}