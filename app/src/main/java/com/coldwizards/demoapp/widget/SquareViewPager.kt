package com.coldwizards.demoapp.widget

import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager

/**
 * Created by jess on 19-6-25.
 */
class SquareViewPager : ViewPager {
    constructor(context: Context): this(context, null)

    constructor(context: Context, attr: AttributeSet?): super(context, attr)
//    constructor(context: Context, attr: AttributeSet?, defStyleAttr: Int): super(context, attr, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
//        val width = measuredWidth
//        setMeasuredDimension(width, width)
    }

}