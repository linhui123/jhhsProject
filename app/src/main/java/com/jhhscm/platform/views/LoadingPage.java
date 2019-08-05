package com.jhhscm.platform.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jhhscm.platform.R;


public class LoadingPage extends FrameLayout {
    private ViewGroup mLoadingLayout;
    private ViewGroup mFailedLayout;
    private TextView mFailedText;

    public LoadingPage(Context context) {
        super(context);
        initViews(context);
    }

    public LoadingPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    private void initViews(Context context) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.view_loading, null);
        this.addView(contentView);
        mLoadingLayout = (ViewGroup) contentView.findViewById(R.id.loading_layout);
        mFailedLayout = (ViewGroup) contentView.findViewById(R.id.failed_layout);
        mFailedText = (TextView) contentView.findViewById(R.id.failed_message_text);
        mFailedLayout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                loading();
                reload();
            }
        });
    }

    public void loading() {
        mFailedLayout.setVisibility(View.GONE);
        mLoadingLayout.setVisibility(View.VISIBLE);
        mFailedText.setText("");
    }

    public void failed(String failedMessage) {
        mLoadingLayout.setVisibility(View.GONE);
        mFailedLayout.setVisibility(View.VISIBLE);
        if (failedMessage != null) {
            mFailedText.setText(failedMessage);
        }
    }

    public void failed(int failedStringId) {
        mLoadingLayout.setVisibility(View.GONE);
        mFailedLayout.setVisibility(View.VISIBLE);
        mFailedText.setText(getContext().getString(failedStringId));
    }

    public void close(){
        this.setVisibility(View.GONE);
        mLoadingLayout.setVisibility(View.GONE);
        mFailedLayout.setVisibility(View.GONE);
    }
    
    protected void reload() {}
}
