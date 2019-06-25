package com.coldwizards.coollibrary.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

/**
 * Created by jess on 19-6-25.
 */
class SquareFrameLayout : FrameLayout {
    constructor(context: Context): this(context, null)

    constructor(context: Context, attr: AttributeSet?): this(context, attr, 0)
    constructor(context: Context, attr: AttributeSet?, defStyleAttr: Int): super(context, attr, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }

}