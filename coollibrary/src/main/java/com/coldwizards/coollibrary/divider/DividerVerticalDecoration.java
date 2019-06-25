package com.coldwizards.coollibrary.divider;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by JessYuan on 4/8/16.
 */
public class DividerVerticalDecoration extends RecyclerView.ItemDecoration {

    private int mVerticalSpaceHeight = 0;

    /**
     * 垂直方向间隔大小
     *
     * @param mVerticalSpaceHeight
     */
    public DividerVerticalDecoration(int mVerticalSpaceHeight) {
        this.mVerticalSpaceHeight = mVerticalSpaceHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        // 最后一个item下面不用间隔
        if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
            outRect.bottom = mVerticalSpaceHeight;
        }
    }
}