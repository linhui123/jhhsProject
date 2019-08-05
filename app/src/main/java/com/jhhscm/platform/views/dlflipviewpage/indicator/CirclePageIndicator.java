/*
 * Copyright (C) 2011 Patrik Akerfeldt
 * Copyright (C) 2011 Jake Wharton
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jhhscm.platform.views.dlflipviewpage.indicator;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.jhhscm.platform.R;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

/**
 * Draws circles (one for each view). The current view position is filled and
 * others are only stroked.
 * 页面指示器
 */
public class CirclePageIndicator extends View implements PageIndicator {
    /** 无效手指指示 */
    private static final int INVALID_POINTER = -1;

    /** 上下文 */
    private Context mContext;

    /** 圆形指示器半径 */
    private float mRadius;
    /** 所有页面圆形指示器实心圆画笔 */
    private final Paint mPaintPageFill = new Paint(ANTI_ALIAS_FLAG);
    /** 所有页面圆形指示器边框空心圆画笔 */
    private final Paint mPaintStroke = new Paint(ANTI_ALIAS_FLAG);
    /** 当前页面圆形指示器实心圆画笔 */
    private final Paint mPaintSelect = new Paint(ANTI_ALIAS_FLAG);
    /** 页面管理控件 */
    private ViewPager mViewPager;
    /** 页面改变监听器 */
    private ViewPager.OnPageChangeListener mListener;
    /** 当前页面号码 */
    private int mCurrentPage;
    /** 保存页面号码 */
    private int mSnapPage;
    /** 页面划动时的偏移距离 */
    private float mPageOffset;
    /** 页面划动条状态 */
    private int mScrollState;
    /** 指示器是否居中显示 */
    private boolean mCentered;
    /** 指示器是否锁定在位置上，设置为false的话，指示器就会跟着手指划动而移动 */
    private boolean mSnap;
    /** 控件最小可触发响应的划动距离 */
    private int mTouchSlop;
    /** 手指按下位置 */
    private float mLastMotionX = -1;
    /** 响应的手指ID */
    private int mActivePointerId = INVALID_POINTER;
    /** 手指是否在拖动中 */
    private boolean mIsDragging;


    public CirclePageIndicator(Context context) {
        super(context);
        mContext = context;
        init(null);
    }

    public CirclePageIndicator(Context context, AttributeSet attrs){
        super(context, attrs);
        mContext = context;
        init(attrs);
    }

    /**
     * 初始化
     * @param attrs
     */
    private void init(AttributeSet attrs) {
        // Load defaults from resources
        // 加载默认资源
        final Resources res = getResources();
        // 默认当前页面指示器实心圆颜色
        final int defaultPageColor = res.getColor(R.color.default_circle_indicator_page_color);
        // 默认全部页面指示器实心圆颜色
        final int defaultFillColor = res.getColor(R.color.default_circle_indicator_select_color);
        // 默认全部页面指示器空心圆边框颜色
        final int defaultStrokeColor = res.getColor(R.color.default_circle_indicator_stroke_color);
        // 默认全部页面指示器空心圆边框宽度
        final float defaultStrokeWidth = res.getDimension(R.dimen.default_circle_indicator_stroke_width);
        // 默认指示器圆形半径
        final float defaultRadius = res.getDimension(R.dimen.default_circle_indicator_radius);
        // 默认指示器是否居中
        final boolean defaultCentered = res.getBoolean(R.bool.default_circle_indicator_centered);
        // 默认指示器是否锁定在位置上
        final boolean defaultSnap = res.getBoolean(R.bool.default_circle_indicator_snap);
        // Retrieve styles attributes
        // 读取配置信息
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.CirclePageIndicator);
        mCentered = a.getBoolean(R.styleable.CirclePageIndicator_centered, defaultCentered);
        mPaintPageFill.setStyle(Style.FILL);
        mPaintPageFill.setColor(a.getColor(R.styleable.CirclePageIndicator_pageColor, defaultPageColor));
        mPaintStroke.setStyle(Style.STROKE);
        mPaintStroke.setColor(a.getColor(R.styleable.CirclePageIndicator_strokeColor, defaultStrokeColor));
        mPaintStroke.setStrokeWidth(a.getDimension(R.styleable.CirclePageIndicator_strokeWidth, defaultStrokeWidth));
        mPaintSelect.setStyle(Style.FILL);
        mPaintSelect.setColor(a.getColor(R.styleable.CirclePageIndicator_selectColor, defaultFillColor));
        mRadius = a.getDimension(R.styleable.CirclePageIndicator_radius, defaultRadius);
        mSnap = a.getBoolean(R.styleable.CirclePageIndicator_snap, defaultSnap);
        Drawable background = a.getDrawable(R.styleable.CirclePageIndicator_android_background);
        if (null != background) {
            setBackgroundDrawable(background);
        }
        // 释放内存，回收资源
        a.recycle();
        // 拿到控件的最小划动距离
        final ViewConfiguration configuration = ViewConfiguration.get(mContext);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
    }

    /**
     * 测量
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 保存测量数据
        setMeasuredDimension(measureLong(widthMeasureSpec), measureShort(heightMeasureSpec));
    }

    /**
     * Determines the width of this view
     *
     * @param measureSpec A measureSpec packed into an int
     * @return The width of the view, honoring constraints from measureSpec
     */
    private int measureLong(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if ((specMode == MeasureSpec.EXACTLY) || (mViewPager == null)) {
            // We were told how big to be
            // 给定数据
            result = specSize;
        } else {
            // Calculate the width according the views count
            // 根据页面数量来计算宽度，最后加1为了保证后面绘制时数据计算成float也能有数据冗余
            final int count = mViewPager.getAdapter().getCount();
            result = (int) (getPaddingLeft() + getPaddingRight()
                    + (count * 2 * mRadius) + (count - 1) * mRadius + 1);
            //Respect AT_MOST value if that was what is called for by measureSpec
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    /**
     * Determines the height of this view
     *
     * @param measureSpec A measureSpec packed into an int
     * @return The height of the view, honoring constraints from measureSpec
     */
    private int measureShort(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            // 给定数据
            result = specSize;
        } else {
            // Measure the height
            result = (int) (2 * mRadius + getPaddingTop() + getPaddingBottom() + 1);
            //Respect AT_MOST value if that was what is called for by measureSpec
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    /**
     * 绘制
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绑定的页面管理控件不能为空
        if (mViewPager == null) {
            return;
        }
        // 绑定的页面数量也不能为0
        final int count = mViewPager.getAdapter().getCount();
        if (count == 0) {
            return;
        }
        // 如果当前页的页码大于全部页面数量，就要修改当前页为最后一页
        if (mCurrentPage >= count) {
            setCurrentItem(count - 1);
            return;
        }
        // 声明变量
        int longSize = getWidth();  // 整体长度
        int longPaddingBefore = getPaddingLeft();  // 距离前面的长度
        int longPaddingAfter = getPaddingRight();  // 距离后面的长度
        int shortPaddingBefore = getPaddingTop(); // 距离上面的长度
        // 一个指示器占据的位置，一个圆形再加上左边边界半个圆和右边边界半个圆
        final float fourRadius = mRadius * 4;
        // 短边偏移量，举例：横着排列，那就是顶部距离到指示器圆中心点
        final float shortOffset = shortPaddingBefore + mRadius;
        // 长边偏移量，举例：横着排列，那就是左边距离到指示器圆中心点
        float longOffset = longPaddingBefore + fourRadius / 2.0f;
        if (mCentered) {
            // 如果指示器居中显示，计算中间开始位置
            longOffset += ((longSize - longPaddingBefore - longPaddingAfter) / 2.0f) - ((count * fourRadius) / 2.0f);
        }
        // 圆心X轴
        float dX;
        // 圆心Y轴
        float dY;
        // 全部页面指示器实心圆半径
        float pageFillRadius = mRadius;
        if (mPaintStroke.getStrokeWidth() > 0) {
            // 全部页面指示器实心圆半径 为 设定圆半径减去空心圆宽度的一半
            pageFillRadius -= mPaintStroke.getStrokeWidth() / 2.0f;
        }
        // 循环绘制全部页面指示器
        for (int iLoop = 0; iLoop < count; iLoop++) {
            // 记录XY轴位置
            dX = longOffset + (iLoop * fourRadius);
            dY = shortOffset;
            // Only paint fill if not completely transparent
            if (mPaintPageFill.getAlpha() > 0) {
                // 绘制全部页面指示器实心圆
                canvas.drawCircle(dX, dY, pageFillRadius, mPaintPageFill);
            }
            // Only paint stroke if a stroke width was non-zero
            if (pageFillRadius != mRadius) {
                // 绘制全部页面指示器空心圆外框
                canvas.drawCircle(dX, dY, mRadius, mPaintStroke);
            }
        }
        // Draw the filled circle according to the current scroll
        // 绘制当前页面页面指示器
        float cx = (mSnap ? mSnapPage : mCurrentPage) * fourRadius;
        if (!mSnap) {
            // 如果不锁定位置，那么就要在划动时记录页面间偏移量，然后在这里加上
            cx += mPageOffset * fourRadius;
        }
        // 记录XY轴位置
        dX = longOffset + cx;
        dY = shortOffset;
        // 绘制当前页面实心圆
        canvas.drawCircle(dX, dY, mRadius, mPaintSelect);
    }

    /**
     * 拦截触控事件
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (super.onTouchEvent(ev)) {
            return true;
        }
        // 绑定的页面管理控件不能为空并且页面数量不能为0
        if ((mViewPager == null) || (mViewPager.getAdapter().getCount() == 0)) {
            return false;
        }
        // 拿到当前动作
        final int action = ev.getAction() & MotionEventCompat.ACTION_MASK;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // 手指按下时
                // 拿到手指ID
                mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                // 将放下手指的位置记录下来
                mLastMotionX = ev.getX();
                break;
            case MotionEvent.ACTION_MOVE: {
                // 手指在屏幕上移动时
                // 根据手指按下时记录的手指ID去获得活动手指的Index
                final int activePointerIndex = MotionEventCompat.findPointerIndex(ev, mActivePointerId);
                // 拿到这个手指当前所在的X轴位置
                final float x = MotionEventCompat.getX(ev, activePointerIndex);
                // 计算移动的距离
                final float deltaX = x - mLastMotionX;
                // 如果上一次记录的没在移动中
                if (!mIsDragging) {
                    // 那么这一次就要看移动距离的绝对值（Math.abs）有没有大过最小识别的移动距离
                    if (Math.abs(deltaX) > mTouchSlop) {
                        // 有的话，就记录为移动中
                        mIsDragging = true;
                    }
                }
                // 如果在移动中
                if (mIsDragging) {
                    // 记录手指当前位置
                    mLastMotionX = x;
                    // 如果是虚假的移动
                    if (mViewPager.isFakeDragging() || mViewPager.beginFakeDrag()) {
                        // 使用fakeDragBy将页面偏移手指移动的距离
                        mViewPager.fakeDragBy(deltaX);
                    }
                }
                break;
            }
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                // 手指离开屏幕时
                // 上一个状态不是移动中
                if (!mIsDragging) {
                    final int count = mViewPager.getAdapter().getCount();
                    final int width = getWidth();
                    final float halfWidth = width / 2f;
                    final float sixthWidth = width / 6f;

                    if ((mCurrentPage > 0) && (ev.getX() < halfWidth - sixthWidth)) {
                        // 当前页面不是第一页、上一个状态是划动中、最后手指的位置在屏幕的左边1/3的位置内，就当作要往后翻一页
                        if (action != MotionEvent.ACTION_CANCEL) {
                            mViewPager.setCurrentItem(mCurrentPage - 1);
                        }
                        return true;
                        // 当前页面不是最后一页、上一个状态是划动中、最后手指的位置在屏幕的右边1/3的位置内，就当作要往前翻一页
                    } else if ((mCurrentPage < count - 1) && (ev.getX() > halfWidth + sixthWidth)) {
                        if (action != MotionEvent.ACTION_CANCEL) {
                            mViewPager.setCurrentItem(mCurrentPage + 1);
                        }
                        return true;
                    }
                }
                // 移动状态归否
                mIsDragging = false;
                // 手指头ID清空
                mActivePointerId = INVALID_POINTER;
                // 结束页面管理器的假动作
                if (mViewPager.isFakeDragging()) mViewPager.endFakeDrag();
                break;
            case MotionEventCompat.ACTION_POINTER_DOWN: {
                final int index = MotionEventCompat.getActionIndex(ev);
                mLastMotionX = MotionEventCompat.getX(ev, index);
                mActivePointerId = MotionEventCompat.getPointerId(ev, index);
                break;
            }
            case MotionEventCompat.ACTION_POINTER_UP:
                final int pointerIndex = MotionEventCompat.getActionIndex(ev);
                final int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);
                if (pointerId == mActivePointerId) {
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
                }
                mLastMotionX = MotionEventCompat.getX(ev, MotionEventCompat.findPointerIndex(ev, mActivePointerId));
                break;
        }

        return true;
    }

    /**
     * 复现
     * @param state
     */
    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        mCurrentPage = savedState.currentPage;
        mSnapPage = savedState.currentPage;
        requestLayout();
    }

    /**
     * 保存
     * @return
     */
    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.currentPage = mCurrentPage;
        return savedState;
    }

    /**
     * 保存状态类
     */
    static class SavedState extends BaseSavedState {
        int currentPage;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            currentPage = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(currentPage);
        }

        @SuppressWarnings("UnusedDeclaration")
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

    /**
     * 设置页面管理器
     * @param view
     */
    @Override
    public void setViewPager(ViewPager view) {
        if (mViewPager == view) {
            return;
        }
        if (mViewPager != null) {
            mViewPager.setOnPageChangeListener(null);
        }
        if (view.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }
        mViewPager = view;
        mViewPager.setOnPageChangeListener(this);
        invalidate();
    }

    /**
     * 设置页面管理器，同时定义了当前页面
     * @param view
     * @param initialPosition
     */
    @Override
    public void setViewPager(ViewPager view, int initialPosition) {
        setViewPager(view);
        setCurrentItem(initialPosition);
    }

    /**
     * 设置当前页面
     * @param item
     */
    @Override
    public void setCurrentItem(int item) {
        if (mViewPager == null) {
            throw new IllegalStateException("ViewPager has not been bound.");
        }
        mViewPager.setCurrentItem(item);
        mCurrentPage = item;
        invalidate();
    }

    /**
     * 设置页面改变监听器
     * @param listener
     */
    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mListener = listener;
    }

    /**
     * 请求重新加载
     */
    @Override
    public void notifyDataSetChanged() {
        invalidate();
    }

    /**
     * 页面滚动时
     * @param i  位置
     * @param v  偏移距离
     * @param i1 偏移像素距离
     */
    @Override
    public void onPageScrolled(int i, float v, int i1) {
        mCurrentPage = i;
        mPageOffset = v;
        invalidate();

        if (mListener != null) {
            mListener.onPageScrolled(i, v, i1);
        }
    }

    /**
     * 页面选择
     * @param i  位置
     */
    @Override
    public void onPageSelected(int i) {
        if (mSnap || mScrollState == ViewPager.SCROLL_STATE_IDLE) {
            mCurrentPage = i;
            mSnapPage = i;
            invalidate();
        }

        if (mListener != null) {
            mListener.onPageSelected(i);
        }
    }

    /**
     * 页面划动状态改变
     * @param i  状态
     */
    @Override
    public void onPageScrollStateChanged(int i) {
        mScrollState = i;

        if (mListener != null) {
            mListener.onPageScrollStateChanged(i);
        }
    }
}
