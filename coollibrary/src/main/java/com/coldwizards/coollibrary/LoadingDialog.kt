package com.coldwizards.coollibrary

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.TextView

/**
 * Created by jess on 18-12-13.
 */
class LoadingDialog(context: Context): Dialog(context) {

    var hintTextView: TextView ?= null

    var hintText: String = "加载中..."

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCanceledOnTouchOutside(false)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.loading_dialog_layout)

        hintTextView = findViewById(R.id.tv_loading_hint)
        hintTextView?.text = hintText
    }

    fun setHint(text: String): LoadingDialog {
        if (text.isNotEmpty()) {
            hintText = text
        }

        return this
    }

}