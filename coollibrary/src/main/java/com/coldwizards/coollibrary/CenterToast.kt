package com.coldwizards.coollibrary

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast

class CenterToast(context: Context) {

    private var toast: Toast
    private var layout: View?= null

    init {
        toast = Toast(context)
        layout = LayoutInflater.from(context).inflate(
            R.layout.center_toast_layout,
            null
        )
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout
    }

    fun show(text: String) {
        val textView = layout?.findViewById<TextView>(R.id.toast_message)
        textView?.text = text

        toast.show()
    }

}