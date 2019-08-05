package com.jhhscm.platform.views.dialog;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;

import com.jhhscm.platform.R;
import com.jhhscm.platform.databinding.DialogSimpleBinding;
import com.jhhscm.platform.tool.StringUtils;

/**
 * 文字提示
 */
public class SimpleDialog extends BaseDialog {
    private DialogSimpleBinding mDataBinding;
    private String mContent;
    private CallbackListener mListener;
    private boolean mCancelable = true;
    private String sure;


    public interface CallbackListener {
        void clickYes();
    }

    public SimpleDialog(Context context, String content, CallbackListener listener) {
        this(context, content, listener, null);
    }

    public SimpleDialog(Context context, String content, CallbackListener listener, String sure) {
        super(context);
//        setCancelable(false);
        setCanceledOnTouchOutside(true);
        this.mContent = content;
        this.mListener = listener;
        this.sure = sure;
    }

    @Override
    protected View onInflateView(LayoutInflater inflater) {
        mDataBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_simple, null, false);
        return mDataBinding.getRoot();
    }

    @Override
    protected void onInitView(View view) {
        initText(mContent);
//        mDataBinding.content.setText(StringUtils.filterNullAndTrim(mContent));
    }

    public void setCanDissmiss(boolean cancelable) {
        this.mCancelable = cancelable;
    }


    @Override
    public void onBackPressed() {
        if (mCancelable) {
            super.onBackPressed();
        }
    }

    private void initText(String phone) {
        String content = "您的手机" + phone + "将很快接到挖矿来的来电请注意接听";
        SpannableString spannableString = new SpannableString(content);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#999999")), 0, 4 + phone.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#3977FE")), 4, 4 + phone.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#999999")), 4 + phone.length(), content.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//        spannableString.setSpan(new AbsoluteSizeSpan(60), 2, 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#00ff00")), 5, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//        spannableString.setSpan(new BackgroundColorSpan(Color.parseColor("#999999")), 0, spannableString.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        mDataBinding.content.setText(spannableString);
    }
}
