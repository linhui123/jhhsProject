package com.jhhscm.platform.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 解决与ScrollView共用的冲突问题
 */
public class ExtendGridView extends GridView {
    public ExtendGridView(Context context) {
        super(context);
    }

    public ExtendGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExtendGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO 自动生成的构造函数存根
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO 自动生成的方法存根
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
