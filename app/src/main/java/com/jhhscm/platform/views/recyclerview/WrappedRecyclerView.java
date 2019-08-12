package com.jhhscm.platform.views.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.jhhscm.platform.R;
import com.jhhscm.platform.views.YXProgress;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by Administrator on 2017/11/6.
 */

public class WrappedRecyclerView extends LinearLayout {
    private PtrFrameLayout mPtrFrame;
    private RecyclerView mRecyclerView;
    private LoadMoreRecyclerViewContainer mLoadMoreContainer;
    private OnPullListener mListener;

    public WrappedRecyclerView(Context context) {
        super(context);
        init();
    }

    public WrappedRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WrappedRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void setPtrFrameEnabled(boolean enabled) {
        mPtrFrame.setEnabled(enabled);
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_pull_recyclerview, this);
        mPtrFrame = (PtrFrameLayout) findViewById(R.id.ptr_frame);
        mLoadMoreContainer = (LoadMoreRecyclerViewContainer) findViewById(R.id.loadmore_container);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//        final MaterialHeader header = new MaterialHeader(getContext());
//        int[] colors = getResources().getIntArray(R.array.google_colors);
//        header.setColorSchemeColors(colors);
//        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
//        header.setPadding(0, DisplayUtils.dip2px(getContext(), 5), 0, DisplayUtils.dip2px(getContext(), 5));
//        header.setPtrFrameLayout(mPtrFrame);
        final YXProgress yxProgress = new YXProgress(getContext());

        mPtrFrame.setHeaderView(yxProgress);
        mPtrFrame.addPtrUIHandler(yxProgress);
        mLoadMoreContainer.setAutoLoadMore(true);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout ptrFrameLayout, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(ptrFrameLayout, mRecyclerView, yxProgress);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                if (mListener != null) {
                    mListener.onRefresh(mRecyclerView);
                }
            }
        });
        mLoadMoreContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                if (mListener != null) {
                    mListener.onLoadMore(mRecyclerView);
                }
            }
        });
    }

    public void addItemDecoration(RecyclerView.ItemDecoration decor) {
        mRecyclerView.addItemDecoration(decor);
    }

    public void addOnScrollListener(RecyclerView.OnScrollListener listener) {
        if (listener != null) mRecyclerView.addOnScrollListener(listener);
    }

    public void addHeaderView(View view) {
        RecyclerViewUtils.setHeaderView(mRecyclerView, view);

    }

    public void addFooterView(View view) {
        RecyclerViewUtils.setFooterView(mRecyclerView, view);
    }

    public void removeHeaderView() {
        RecyclerViewUtils.removeHeaderView(mRecyclerView);
    }

    public PtrFrameLayout getPtrFrame() {
        return mPtrFrame;
    }

    public void loadComplete(boolean emptyResult, boolean hasMore) {
        mPtrFrame.refreshComplete();
        mLoadMoreContainer.loadMoreFinish(emptyResult, hasMore);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(new HeaderAndFooterRecyclerViewAdapter(adapter));
        mLoadMoreContainer.useDefaultFooter();
    }

    public HeaderAndFooterRecyclerViewAdapter getAdapter() {
        return (HeaderAndFooterRecyclerViewAdapter) mRecyclerView.getAdapter();
    }

    public void setItemAnimator(RecyclerView.ItemAnimator animator) {
        mRecyclerView.setItemAnimator(animator);
    }

    public void setLayoutManager(RecyclerView.LayoutManager manager) {
        if (manager instanceof StaggeredGridLayoutManager) {
            mRecyclerView.setLayoutManager(new ExStaggeredGridLayoutManager(((StaggeredGridLayoutManager) manager).getSpanCount(), ((StaggeredGridLayoutManager) manager).getOrientation()));
        } else {
            mRecyclerView.setLayoutManager(manager);
        }
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return mRecyclerView.getLayoutManager();
    }

    public void setOnPullListener(OnPullListener listener) {
        this.mListener = listener;
    }

    public interface OnPullListener {
        void onRefresh(RecyclerView view);

        void onLoadMore(RecyclerView view);
    }

    public void scrollTop() {
        mRecyclerView.smoothScrollToPosition(0);
    }

    public void autoRefresh() {
        scrollTop();
        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrame.autoRefresh(false);
            }
        }, 100);
    }

    public void setLoadmoreText(String text) {
        View footer = mLoadMoreContainer.getFooterView();
        if (footer instanceof LoadMoreDefaultFooterView) {
            ((LoadMoreDefaultFooterView) footer).setLoadMoreText(text);
        }
    }

    public void hideDefaultFooter() {
        View footer = mLoadMoreContainer.getFooterView();
        if (footer instanceof LoadMoreDefaultFooterView) {
            ((LoadMoreDefaultFooterView) footer).hideFooter();
        }
    }

    public void hideLoad() {
        View footer = mLoadMoreContainer.getFooterView();
        if (footer instanceof LoadMoreDefaultFooterView) {
            ((LoadMoreDefaultFooterView) footer).hideLoad();
        }
    }
}
