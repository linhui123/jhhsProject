package com.jhhscm.platform.views.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jhhscm.platform.R;


public class LoadMoreDefaultFooterView extends RelativeLayout implements LoadMoreUIHandler {

    private TextView mTextView;
    private ProgressBar mProgressBar;
    private View mDivider;
    private int mHeight;

    public LoadMoreDefaultFooterView(Context context) {
        this(context, null);
    }

    public LoadMoreDefaultFooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadMoreDefaultFooterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setupViews();
    }

    private void setupViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.views_load_more_default_footer, this);
        mTextView = (TextView) findViewById(R.id.cube_views_load_more_default_footer_text_view);
        mProgressBar = (ProgressBar) findViewById(R.id.cube_views_load_more_default_footer_progressbar);
        mDivider=findViewById(R.id.divider);
        measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        mHeight = getMeasuredHeight();
    }

    public void hideFooter(){
        mTextView.setVisibility(GONE);
        mDivider.setVisibility(VISIBLE);
    }

    public void hideLoad(){
        mTextView.setVisibility(GONE);
        mDivider.setVisibility(GONE);
    }

    @Override
    public void onLoading(LoadMoreContainer container) {
        setVisibility(VISIBLE);
        mTextView.setText(R.string.views_load_more_loading);
        mProgressBar.setVisibility(VISIBLE);
    }

    @Override
    public void onLoadFinish(LoadMoreContainer container, boolean empty, boolean hasMore) {
        mProgressBar.setVisibility(GONE);
        if (!hasMore) {
            setVisibility(VISIBLE);
            if (empty) {
                mTextView.setText(R.string.views_load_more_loaded_empty);
            } else {
                mTextView.setText(R.string.views_load_more_loaded_no_more);
//                getLayoutParams().height = 0;
            }
        } else {
            setVisibility(INVISIBLE);
            mTextView.setText(R.string.views_load_more_click_to_load_more);
//            if(getLayoutParams() != null){
//                getLayoutParams().height = mHeight;
//            }
        }
    }

    @Override
    public void onWaitToLoadMore(LoadMoreContainer container) {
        setVisibility(VISIBLE);
        mProgressBar.setVisibility(GONE);
        mTextView.setText(R.string.views_load_more_click_to_load_more);
    }

    @Override
    public void onLoadError(LoadMoreContainer container, int errorCode, String errorMessage) {
        mTextView.setText(R.string.views_load_more_error);
        mProgressBar.setVisibility(GONE);
    }

    public void setLoadMoreText(String text){
        mTextView.setText(text);
    }
}
