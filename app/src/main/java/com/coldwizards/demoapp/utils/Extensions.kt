package com.coldwizards.demoapp.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.AdaptiveIconDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.coldwizards.coollibrary.ui.CenterToast
import com.coldwizards.demoapp.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by jess on 19-6-14.
 */
inline fun <reified T : Any> Activity.launchActivity (
    requestCode: Int = -1,
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {})
{
    val intent = newIntent<T>(this)
    intent.init()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
    {
        startActivityForResult(intent, requestCode, options)
    } else {
        startActivityForResult(intent, requestCode)
    }
}

inline fun <reified T : Any> Context.launchActivity (
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {})
{
    val intent = newIntent<T>(this)
    intent.init()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
    {
        startActivity(intent, options)
    } else {
        startActivity(intent)
    }
}

inline fun <reified T : Any> newIntent(context: Context): Intent =
    Intent(context, T::class.java)

fun ImageView.loadImage(context: Context, url: String)
        = Glide.with(context).load(url).placeholder(R.drawable.image_holder).fitCenter().into(this)

fun ImageView.loadImage(context: Context, uri: Uri)
        = Glide.with(context).load(uri).placeholder(R.drawable.image_holder).fitCenter().into(this)


fun ImageView.loadImage(context: Context, drawable: Drawable) = Glide.with(context).load(drawable).into(this)

fun Fragment.showToast(text: String) {
    this.activity?.runOnUiThread {
        Toast.makeText(this.context!!, text, Toast.LENGTH_SHORT).show()
    }
}

fun Fragment.showCenterToast(text: String) {
    this.activity?.runOnUiThread {
        CenterToast(this.context!!).show(text)

    }
}

fun Activity.showToast(text: String) {
    runOnUiThread {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}

fun File.createImageFile(context: Context): File {
    val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val imageFileName = "IMG_$timestamp"

    val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
    if (!dir.exists()) {
        dir.mkdirs()
    }

    return File.createTempFile(imageFileName, ".jpg", dir)
}
