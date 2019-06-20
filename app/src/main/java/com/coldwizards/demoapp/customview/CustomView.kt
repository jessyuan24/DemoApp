package com.coldwizards.demoapp.customview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

/**
 * Created by jess on 19-5-27.
 */
class CustomView: View {
    constructor(context: Context):this(context, null)
    constructor(context: Context, attrs: AttributeSet?):this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int):super(context, attrs, defStyleAttr) {
    }

    val TAG = CustomView::class.java.simpleName

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        var result = super.dispatchTouchEvent(ev)
        Log.d(TAG, "View dispatch ${result} ${getAction(ev)}")
        return result
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var result = super.onTouchEvent(event)
        Log.d(TAG, "View touch event ${result} ${getAction(event)}")
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