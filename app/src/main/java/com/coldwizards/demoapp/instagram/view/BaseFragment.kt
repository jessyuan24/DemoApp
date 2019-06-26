package com.coldwizards.demoapp.instagram.view

import android.app.Activity
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import com.coldwizards.coollibrary.ui.CenterToast
import com.coldwizards.coollibrary.LoadingDialog


/**
 * Created by jess on 19-6-14.
 */
open class BaseFragment : Fragment() {


    private var showKeyboardListener: (keyboardHeight: Int) -> Unit = {}
    private var hiddenKeyboardListener: () -> Unit = {}
    private var mLoadingDialog: LoadingDialog? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initKeyBoardListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        showKeyboardListener = {}
        hiddenKeyboardListener = {}
    }

    fun getToolbar(): ActionBar {
        return (activity as InsActivity).supportActionBar!!
    }

    fun seToolbarTitle(title: String) {
        getToolbar().title = title
    }

    fun showToast(text: String) {
        Toast.makeText(context!!, text, Toast.LENGTH_SHORT).show()
    }

    fun showCenterToast(text: String) {
        CenterToast(context!!).show(text)
    }

    fun showLoading(text: String) {
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingDialog(context!!)
        }

        mLoadingDialog?.show()
    }

    fun dismissLoading() {
        mLoadingDialog?.dismiss()
    }

    /**
     * 隐藏键盘
     */
    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view!!.windowToken, 0)
    }

    /**
     * 显示键盘
     */
    fun showKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    /**
     * 设置键盘的出现和隐藏事件
     */
    private fun initKeyBoardListener() {
        val MIN_KEYBOARD_HEIGHT_PX = 150
        val decorView = activity?.getWindow()?.getDecorView()
        decorView?.getViewTreeObserver()?.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            private val windowVisibleDisplayFrame = Rect()
            private var lastVisibleDecorViewHeight: Int = 0

            override fun onGlobalLayout() {
                decorView.getWindowVisibleDisplayFrame(windowVisibleDisplayFrame)
                val visibleDecorViewHeight = windowVisibleDisplayFrame.height()
                Log.d("键盘高度",  "${visibleDecorViewHeight - lastVisibleDecorViewHeight}")

                if (lastVisibleDecorViewHeight != 0) {
                    if (lastVisibleDecorViewHeight > visibleDecorViewHeight + MIN_KEYBOARD_HEIGHT_PX) {

                        showKeyboardListener(Math.abs(visibleDecorViewHeight - lastVisibleDecorViewHeight))
                    } else if (lastVisibleDecorViewHeight + MIN_KEYBOARD_HEIGHT_PX < visibleDecorViewHeight) {
                        hiddenKeyboardListener()
                    }
                }
                lastVisibleDecorViewHeight = visibleDecorViewHeight
            }
        })
    }

    /**
     * 设置显示键盘时的监听事件
     */
    fun setShowKeyboardListener(listener : (height: Int) -> Unit) {
        showKeyboardListener = listener
    }

    /**
     * 设置隐藏键盘时的监听事件
     */
    fun setHiddenKeyboardListener(listener : () -> Unit) {
        hiddenKeyboardListener = listener
    }

}
