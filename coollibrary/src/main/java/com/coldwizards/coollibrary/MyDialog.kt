package com.coldwizards.coollibrary

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

/**
 * Created by jess on 19-1-4.
 */
class MyDialog(context: Context) {

    private val DEFAULT: (View) -> Unit  = { throw UnsupportedOperationException("this shouldn't be called") }

    private var mDialog: Dialog = Dialog(context)
    private var mTitle = ""
    private var mMessage = ""
    private var mNegativeText = ""
    private var mPositiveText = ""
    private var mHint = "输入"
    private var mEditText = ""
    private var mShowEditText = false

    private var mTitleColor = R.color.black
    private var mMessageColor = R.color.black
    private var mNegativeTextColor = R.color.black
    private var mPositiveTextColor = R.color.black

    private var mNegativeListener: (View) -> Unit = DEFAULT
    private var mPositiveListener: (View) -> Unit = DEFAULT

    fun create(): Dialog {
        mDialog.setContentView(R.layout.my_dialog)
        mDialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mDialog.window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        mDialog.window.setGravity(Gravity.CENTER)
        mDialog.setCancelable(false)

        initView()

        return mDialog
    }

    fun setTitle(title: String): MyDialog {
        mTitle = title
        return this
    }

    fun setMessage(message: String): MyDialog {
        mMessage = message
        return this
    }

    fun showEditText(show: Boolean): MyDialog {
        mShowEditText = show
        return this
    }

    fun setHint(hint: String): MyDialog {
        mHint = hint
        return this
    }

    fun setNegativeButton(text: String, @ColorRes idRes: Int, listener: (View) -> Unit): MyDialog {
        mNegativeText = text
        mNegativeTextColor = idRes
        mNegativeListener = listener

        return this
    }

    fun setPositiveButton(text: String, @ColorRes idRes: Int, listener: (View) -> Unit): MyDialog {
        mPositiveText = text
        mPositiveTextColor = idRes
        mPositiveListener = listener

        return this
    }

    fun getEditText(): String {
        return mEditText
    }

    private fun initView() {
        val titleTV = mDialog.findViewById<TextView>(R.id.dialog_title_tv)
        val messageTV = mDialog.findViewById<TextView>(R.id.dialog_message_tv)
        val negativeTV = mDialog.findViewById<TextView>(R.id.dialog_negative_tv)
        val positiveTV = mDialog.findViewById<TextView>(R.id.dialog_positive_tv)
        val edittext = mDialog.findViewById<EditText>(R.id.dialog_edittext)

        edittext.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mEditText = s.toString()
            }
        })

        titleTV.text = mTitle
        messageTV.text = mMessage

        if (mShowEditText) {
            edittext.visibility = View.VISIBLE
            messageTV.visibility = View.GONE
        } else {
            edittext.visibility = View.GONE
            messageTV.visibility = View.VISIBLE
        }

        positiveTV.text = mPositiveText
        positiveTV.setTextColor(ContextCompat.getColor(mDialog.context, mPositiveTextColor))
        positiveTV.setOnClickListener{
            mDialog.dismiss()
            mPositiveListener(it)
        }

        negativeTV.text = mNegativeText
        negativeTV.setTextColor(ContextCompat.getColor(mDialog.context, mNegativeTextColor))
        negativeTV.setOnClickListener{
            mDialog.dismiss()
            mNegativeListener(it)
        }

        if (mNegativeText.isEmpty()) {
            negativeTV.visibility = View.GONE
        } else {
            negativeTV.visibility = View.VISIBLE
        }
    }

}