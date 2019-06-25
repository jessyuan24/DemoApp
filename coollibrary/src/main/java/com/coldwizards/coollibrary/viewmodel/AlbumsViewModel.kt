package com.coldwizards.coollibrary.viewmodel

import android.app.Activity
import android.app.Application
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.coldwizards.coollibrary.Albums
import java.io.File

/**
 * Created by jess on 19-6-25.
 */
class AlbumsViewModel(app: Application) : AndroidViewModel(app) {

    private val TAG = AlbumsViewModel::class.java.simpleName

    private var albums = ArrayList<Albums>()

    fun getAllAlbums(activity: Activity): LiveData<ArrayList<Albums>> {

        val uri: Uri
        val cursor: Cursor
        var cursorBucket: Cursor
        val column_index_data: Int
        val column_index_folder_name: Int
        val listOfAllImages = ArrayList<String>()
        var coverPath: String? = null
        var albumsList = ArrayList<Albums>()

        val BUCKET_GROUP_BY = "1) GROUP BY 1,(2"
        val BUCKET_ORDER_BY = "MAX(datetaken) DESC"

        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val projection = arrayOf(
            MediaStore.Images.ImageColumns.BUCKET_ID,
            MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
            MediaStore.Images.ImageColumns.DATE_TAKEN,
            MediaStore.Images.ImageColumns.DATA
        )

        cursor = activity.contentResolver.query(uri, projection, BUCKET_GROUP_BY, null, BUCKET_ORDER_BY)

        if (cursor != null) {
            column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
            column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
            while (cursor.moveToNext()) {
                coverPath = cursor.getString(column_index_data)
                Log.d(TAG, "bucket name:" + cursor.getString(column_index_data))

                val selectionArgs = arrayOf("%" + cursor.getString(column_index_folder_name) + "%")
                val selection = MediaStore.Images.Media.DATA + " like ? "
                val projectionOnlyBucket =
                    arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME)

                cursorBucket = activity.contentResolver.query(uri, projectionOnlyBucket, selection, selectionArgs, null)
                Log.d(TAG, "bucket size:" + cursorBucket.count)

                val albumName = cursor.getString(column_index_folder_name)

                if (coverPath != "" && coverPath != null) {
                    listOfAllImages.add(coverPath)
                    albumsList.add(
                        Albums(
                            albumName,
                            coverPath,
                            coverPath.substring(IntRange(0, coverPath.lastIndexOf("/"))),
                            cursorBucket.count,
                            false
                        )
                    )
                }
            }
        }

        val livedata = MutableLiveData<ArrayList<Albums>>()
        livedata.value = albumsList
        albums = albumsList

        return livedata
    }

    fun getAllVideos(activity: Activity, albumsList: ArrayList<Albums>): ArrayList<Albums> {

        var cursor: Cursor
        var cursorBucket: Cursor
        var uri: Uri
        val BUCKET_GROUP_BY = "1) GROUP BY 1,(2"
        val BUCKET_ORDER_BY = "MAX(datetaken) DESC"
        val column_index_album_name: Int
        val column_index_album_video: Int

        uri = android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI

        val projection1 = arrayOf(
            MediaStore.Video.VideoColumns.BUCKET_ID,
            MediaStore.Video.VideoColumns.BUCKET_DISPLAY_NAME,
            MediaStore.Video.VideoColumns.DATE_TAKEN,
            MediaStore.Video.VideoColumns.DATA
        )

        cursor = activity.contentResolver.query(uri, projection1, BUCKET_GROUP_BY, null, BUCKET_ORDER_BY)

        if (cursor != null) {
            column_index_album_name = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME)
            column_index_album_video = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            while (cursor.moveToNext()) {
                Log.d(TAG, "bucket video:" + cursor.getString(column_index_album_name))
                Log.d(TAG, "bucket video:" + cursor.getString(column_index_album_video))
                val selectionArgs = arrayOf("%" + cursor.getString(column_index_album_name) + "%")

                val selection = MediaStore.Video.Media.DATA + " like ? "
                val projectionOnlyBucket =
                    arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Video.Media.BUCKET_DISPLAY_NAME)

                cursorBucket = activity.contentResolver.query(uri, projectionOnlyBucket, selection, selectionArgs, null)
                Log.d("title_apps", "bucket size:" + cursorBucket.count)

                val albumName = cursor.getString(column_index_album_name)
                val coverPath = cursor.getString(column_index_album_video)

                albumsList.add(
                    Albums(
                        albumName,
                        coverPath,
                        coverPath.substring(IntRange(0, coverPath.lastIndexOf("/"))),
                        cursorBucket.count,
                        true
                    )
                )
            }
        }
        return albumsList
    }

    fun getAllImageFromAlbums(albums: ArrayList<Albums>): ArrayList<String> {
        val image = ArrayList<String>()
        val regex = """.*(\.jpg|\.png|\.jpeg)""".toRegex()
        albums.forEach { album ->
            val dir = File(album.folderPath)
            dir.listFiles().forEach {
                if (it.isFile && regex.containsMatchIn(it.absolutePath)) {
                    Log.d(TAG, it.absolutePath)
                    image.add(it.absolutePath)
                }
            }
        }

        return image
    }

    fun getAllImageFromAlbum(album: Albums): ArrayList<String> {
        val image = ArrayList<String>()
        val regex = """.*(\.jpg|\.png|\.jpeg)""".toRegex()

        val dir = File(album.folderPath)
        dir.listFiles().forEach {
            if (it.isFile && regex.containsMatchIn(it.absolutePath)) {
                Log.d(TAG, it.absolutePath)
                image.add(it.absolutePath)
            }
        }
        

        return image
    }

}