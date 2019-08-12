package com.jhhscm.platform.views.recyclerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jhhscm.platform.R;

/**
 * 分割线（虚线）
 */
public class DividerItemStrokeDecoration extends RecyclerView.ItemDecoration {

    private Context mContext;   //上下文
    private int dividerHeight;  //分割线的高度
    private Paint mPaint;   //画笔

    //自定义构造方法，在构造方法中初始化一些变量
    public DividerItemStrokeDecoration(Context context) {
        mContext = context;
        dividerHeight = 2;
        mPaint = new Paint();
        mPaint.setColor(context.getResources().getColor(R.color.de));  //设置颜色
    }

    public void onDrawOver(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - 1; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            //以下计算主要用来确定绘制的位置
            final int top = child.getBottom() + params.bottomMargin;

            //绘制虚线
            mPaint = new Paint();
            mPaint.setStyle(Paint.Style.STROKE);
            Path path = new Path();
            path.moveTo(left, top);
            path.lineTo(right, top);
            PathEffect effects = new DashPathEffect(new float[]{15, 15, 15, 15}, 5);//此处单位是像素不是dp  注意 请自行转化为dp
            mPaint.setPathEffect(effects);
            c.drawPath(path, mPaint);
        }
    }
}
