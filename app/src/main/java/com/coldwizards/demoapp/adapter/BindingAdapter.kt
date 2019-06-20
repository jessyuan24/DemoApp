package com.coldwizards.demoapp.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.util.*

/**
 * Created by jess on 19-6-18.
 */
object BindingAdapter {


        @BindingAdapter("post_time")
        @JvmStatic
        fun postTime(textView: TextView, date: Date) {
            val delta = Date().time - date.time

            if (delta < 1000) {
                textView.text = "1秒前"
            } else if (delta < 60000) {
                textView.text = "${delta / 1000}秒前"
            } else if (delta < 60000 * 60) {
                textView.text = "${delta / 60000}分钟前"
            } else if (delta < 60000 * 60 * 24) {
                textView.text = "${delta / (60000*60)}小时前"
            } else {
                textView.text = "${delta / (60000*60*24)}天前"
            }
        }

}