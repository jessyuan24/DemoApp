package com.coldwizards.coollibrary

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by jess on 19-6-25.
 */
data class Albums(var folderNames: String, var coverPath: String,
                  var folderPath: String,
                  var imgCount: Int, var isVideo: Boolean): Parcelable {
    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Albums> = object : Parcelable.Creator<Albums> {
            override fun createFromParcel(source: Parcel): Albums = Albums(source)
            override fun newArray(size: Int): Array<Albums?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel): this(
    source.readString(),
    source.readString(),
    source.readString(),
    source.readInt(),
    1 == source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(folderNames)
        writeString(coverPath)
        writeString(folderPath)
        writeInt(imgCount)
        writeInt((if (isVideo) 1 else 0))
    }
}