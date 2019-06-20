package com.coldwizards.demoapp

import android.os.Bundle
import android.util.Log
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_drag_drop_demo.*

/**
 * Created by jess on 19-5-29.
 */
class DragDropDemoActivity : AppCompatActivity(), View.OnTouchListener, View.OnDragListener {

    private val TAG = DragDropDemoActivity::class.java.simpleName

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        Log.d(TAG, "onTouch: view->view$v\n MotionEvent$event")
        return if (event?.action === MotionEvent.ACTION_DOWN) {
            val dragShadowBuilder = View.DragShadowBuilder(v)
            v?.startDrag(null, dragShadowBuilder, v, 0)
            true
        } else {
            false
        }
    }

    override fun onDrag(v: View?, event: DragEvent?): Boolean {
        Log.d(TAG, "onDrag: view->$v\n DragEvent$event")

        when(event?.action) {
            DragEvent.ACTION_DRAG_ENDED -> {
                Log.d(TAG, "onDrag: ACTION_DRAG_ENDED")
                return true
            }
            DragEvent.ACTION_DRAG_EXITED -> {
                Log.d(TAG, "onDrag: ACTION_DRAG_EXITED")
                return true
            }
            DragEvent.ACTION_DRAG_ENTERED -> {
                Log.d(TAG, "onDrag: ACTION_DRAG_ENTERED")
                return true
            }
            DragEvent.ACTION_DRAG_STARTED -> {
                Log.d(TAG, "onDrag: ACTION_DRAG_STARTED")
                val tvState = event.localState as View
//                tvState.visibility = View.GONE
                return true
            }
            DragEvent.ACTION_DROP -> {
                Log.d(TAG, "onDrag: ACTION_DROP")
                val tvState = event.localState as View
                Log.d(TAG, "onDrag:viewX" + event.x + "viewY" + event.y)
                Log.d(TAG, "onDrag: Owner->" + tvState.parent)
                val tvParent = tvState.parent as ViewGroup
                tvParent.removeView(tvState)
                val container = v as LinearLayout
                container.addView(tvState)
                tvParent.removeView(tvState)
                tvState.x = event.x
                tvState.y = event.y
//                tvState.visibility = View.VISIBLE
                v.addView(tvState)
                v.setVisibility(View.VISIBLE)
                return true
            }
            DragEvent.ACTION_DRAG_LOCATION -> {
                Log.d(TAG, "onDrag: ACTION_DRAG_LOCATION")
                return true
            }
            else -> return false
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drag_drop_demo)

        tv_dropdrop.setOnTouchListener(this)
        ll_pinklayout.setOnDragListener(this)
    }


}