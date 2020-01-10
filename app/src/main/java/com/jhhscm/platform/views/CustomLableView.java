package com.jhhscm.platform.views;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jhhscm.platform.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义ViewGroup实现标签流效果
 *
 * @attr customInterval  //标签之间的间隔
 * @attr customSelectMode  //标签选项模式
 * @attr customSonBackground   //标签背景
 * @attr customSonPaddingBottom   //标签底部内边距
 * @attr customSonPaddingLeft  //标签左边内边距
 * @attr customSonPaddingRight  //标签右边内边距
 * @attr customSonPaddingTop  //标签顶部内边距
 * @attr customSonTextColor  //标签文字颜色
 * @attr customSonTextSize  //标签文字尺寸
 */
public class CustomLableView extends ViewGroup {
    private static final String TAG = "CustomLableView";
    private int customInterval = 15;
    private int customSonPaddingLeft = 20;
    private int customSonPaddingRight = 20;
    private int customSonPaddingTop = 10;
    private int customSonPaddingBottom = 10;
    private Drawable customSonBackground = null;
    private float customSonTextSize = 0;
    private ColorStateList customSonTextColor = ColorStateList.valueOf(0xFF000000);
    private ArrayList<String> mSonTextContents = new ArrayList<>();
    private ArrayList<TextView> mSonTextViews = new ArrayList<>();
    private Context mContext = null;
    private OnItemClickListener mOnItemClickListener;
    private int customSelectMode;
    private View view;

    public CustomLableView(Context context) {
        this(context, null);
    }

    public CustomLableView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomLableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        //初始化自定义属性
        initAttrs(context, attrs);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.search_tv, null, false);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left = customInterval;
        int top = customInterval;
        int mMeasuredWidth = this.getMeasuredWidth();
        //防止重复添加
        CustomLableView.this.removeAllViews();
        for (int i = 0; i < mSonTextViews.size(); i++) {
            final TextView mTextView = mSonTextViews.get(i);
            if (((ViewGroup) mTextView.getParent()) != null) {
                ((ViewGroup) mTextView.getParent()).removeAllViews();
            }
            //将当前子View添加到ViewGroup中
            CustomLableView.this.addView(mTextView);
            //获取当前子View的宽高
            int measuredHeight = mTextView.getMeasuredHeight();
            int measuredWidth = mTextView.getMeasuredWidth();
            //判断一行是否可显示
            if ((mMeasuredWidth - left) >= (measuredWidth + customInterval * 2)) {//一行可显示
                /**
                 * 1、(mMeasuredWidth - left) X轴剩余空间
                 * 2、(measuredWidth + customInterval * 2) 当前子View和间隔需要的空间
                 */
                mTextView.layout(left, top, left + measuredWidth, top + measuredHeight);
                left += (measuredWidth + customInterval);
            } else {//需要换行显示
                //还原X轴的起始位置
                left = customInterval;
                //Y轴高度增加
                top += (measuredHeight + customInterval);
                mTextView.layout(left, top, left + measuredWidth, top + measuredHeight);
                left += (measuredWidth + customInterval);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //控件宽度
        int mMeasureViewWidht = MeasureSpec.getSize(widthMeasureSpec);
        //显示行数
        int line = 1;
        //每行当前宽度
        int lineWidht = customInterval;
        //每行高度(子View的高度)
        int mSonMeasuredHeight = 0;
        mSonTextViews.clear();
        for (int i = 0; i < mSonTextContents.size(); i++) {
            TextView mSonView = getSonView(i, mSonTextContents.get(i));
            //添加到数组中
            mSonTextViews.add(mSonView);
            //对子View进行测量
            mSonView.measure(0, 0);
            //获取子View的测量尺寸
            int mSonMeasuredWidth = mSonView.getMeasuredWidth() + customInterval;
            mSonMeasuredHeight = mSonView.getMeasuredHeight() + customInterval;
            if (mMeasureViewWidht >= mSonMeasuredWidth) {
                if ((mMeasureViewWidht - lineWidht) >= mSonMeasuredWidth) {
                    lineWidht += mSonMeasuredWidth;
                } else {
                    //行数自加1
                    line += 1;
                    lineWidht = customInterval + mSonMeasuredWidth;
                }
            } else {//如果子view宽度大于控件总宽度-显示内容超过一行
//                mSonTextViews.clear();
//                setMeasuredDimension(0, 0);
//                return;
                //行数自加1
                line += 1;
                lineWidht = customInterval + mSonMeasuredWidth;
            }
        }
        //设置控件尺寸
        setMeasuredDimension(mMeasureViewWidht, mSonMeasuredHeight * line + customInterval);
    }

    /**
     * 获取子View
     *
     * @return TextView
     */
    private TextView getSonView(final int i, final String content) {
        if (mContext != null && view != null) {
            TextView mTextView = (TextView) view.findViewById(R.id.tv);
            mTextView.setText(content);
            if (customSonTextColor != null) {
                mTextView.setTextColor(customSonTextColor);
            }
            mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, customSonTextSize);
            //不可以设置相同的Drawable
            if (customSonBackground != null) {
                mTextView.setBackgroundDrawable(customSonBackground.getConstantState().newDrawable());
            }
            mTextView.setPadding(customSonPaddingLeft, customSonPaddingTop, customSonPaddingRight, customSonPaddingBottom);
            //消除TextView默认的上下内边距
            mTextView.setIncludeFontPadding(false);
            mTextView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //选择模式
                    if (customSelectMode != 102) {
                        for (int j = 0; j < mSonTextViews.size(); j++) {
                            mSonTextViews.get(j).setSelected(false);
                        }
                        v.setSelected(true);
                    } else {
                        v.setSelected(true);
                    }
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, i, content);
                    }
                }
            });
            return mTextView;
        }
        return null;
    }

    /**
     * 初始化自定义属性
     *
     * @param context
     * @param attrs
     */
    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomLableView);
        customSonBackground = mTypedArray.getDrawable(R.styleable.CustomLableView_customSonBackground);
        customInterval = (int) mTypedArray.getDimension(R.styleable.CustomLableView_customInterval, customInterval);
        customSonPaddingLeft = (int) mTypedArray.getDimension(R.styleable.CustomLableView_customSonPaddingLeft, customSonPaddingLeft);
        customSonPaddingRight = (int) mTypedArray.getDimension(R.styleable.CustomLableView_customSonPaddingRight, customSonPaddingRight);
        customSonPaddingTop = (int) mTypedArray.getDimension(R.styleable.CustomLableView_customSonPaddingTop, customSonPaddingTop);
        customSonPaddingBottom = (int) mTypedArray.getDimension(R.styleable.CustomLableView_customSonPaddingBottom, customSonPaddingBottom);
        customSonTextSize = (int) mTypedArray.getDimension(R.styleable.CustomLableView_customSonTextSize, 0);
        customSonTextColor = mTypedArray.getColorStateList(R.styleable.CustomLableView_customSonTextColor);
        customSelectMode = mTypedArray.getInt(R.styleable.CustomLableView_customSelectMode, 101);
        if (customSonTextSize == 0) {
            customSonTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics());
        }
        mTypedArray.recycle();
    }

    /**
     * 设置标签内容集合
     *
     * @param sonContent 标签内容
     */
    public void setSonContent(List<String> sonContent) {
        if (sonContent != null) {
            mSonTextContents.clear();
            mSonTextContents.addAll(sonContent);
            requestLayout();
        }
    }

    /**
     * 删除标签内容集合
     */
    public void clear() {
        mSonTextContents.clear();
        requestLayout();
    }

    /**
     * 删除标签内容
     */
    public void remove(int position) {
        mSonTextContents.remove(0);
        requestLayout();
    }

    /**
     * 添加一个标签
     *
     * @param sonContent 标签内容
     */
    public void addSonContent(String sonContent) {
        if (!TextUtils.isEmpty(sonContent)) {
            mSonTextContents.add(0, sonContent);
            requestLayout();
        }
    }

    /**
     * 设置标签点击事件
     *
     * @param onItemClickListener 回调接口
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, String sonContent);
    }
}
