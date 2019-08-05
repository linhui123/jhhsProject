package com.jhhscm.platform.views;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.jhhscm.platform.R;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;


/**
 * Created by Administrator on 2017/6/13.
 */

public class YXProgress extends FrameLayout implements PtrUIHandler {
    private PtrFrameLayout mPtrFrameLayout;
    AnimationDrawable animationDrawable;

    public YXProgress(Context context) {
        super(context);
        initViews();
    }

    public YXProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    private void initViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_progress, this, true);
        ImageView imageView=(ImageView)findViewById(R.id.image);
        imageView.setBackgroundResource(R.drawable.animation_list);
        animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.start();
    }

    public void setPtrFrameLayout(PtrFrameLayout layout) {
    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {
        animationDrawable.stop();
        animationDrawable.selectDrawable(0);
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {

    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        animationDrawable.start();
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        animationDrawable.stop();
        animationDrawable.selectDrawable(0);
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {

    }
}
