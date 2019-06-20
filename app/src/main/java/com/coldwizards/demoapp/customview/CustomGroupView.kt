package com.coldwizards.demoapp.customview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.LinearLayout

/**
 * Created by jess on 19-5-27.
 */
class CustomGroupView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
): LinearLayout(context, attrs, defStyleAttr) {

    val TAG = CustomGroupView::class.java.simpleName

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        var result = super.dispatchTouchEvent(ev)
        Log.d(TAG, "ViewGroup dispatch ${result} ${getAction(ev)}")
        return result
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        var result = super.onInterceptTouchEvent(ev)
        Log.d(TAG, "ViewGroup intercept $result ${getAction(ev)}")
        return result
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var result =  super.onTouchEvent(event)
        Log.d(TAG, "ViewGroup touch event ${result} ${getAction(event)}")
        return result
    }

    private fun getAction(ev: MotionEvent?): String {
        when(ev?.actionMasked) {
            MotionEvent.ACTION_DOWN -> return "ACTION_DOWN"
            MotionEvent.ACTION_UP -> return "ACTION_UP"
            MotionEvent.ACTION_MOVE -> return "ACTION_MOVE"
            else -> return "UNKNOWN"
        }
    }
}