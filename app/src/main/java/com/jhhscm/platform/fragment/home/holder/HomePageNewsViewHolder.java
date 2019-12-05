package com.jhhscm.platform.fragment.home.holder;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.ArticleDetailActivity;
import com.jhhscm.platform.activity.ArticleListActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemHomeNewsListBinding;
import com.jhhscm.platform.databinding.ItemHomePageNewsBinding;
import com.jhhscm.platform.fragment.home.HomePageItem;
import com.jhhscm.platform.fragment.home.bean.GetPageArticleListBean;
import com.nostra13.universalimageloader.core.ImageLoader;

public class HomePageNewsViewHolder extends AbsRecyclerViewHolder<HomePageItem> {

    private ItemHomePageNewsBinding mBinding;

    public HomePageNewsViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemHomePageNewsBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final HomePageItem item) {
        if (item.getPageArticleListBean != null) {
            mBinding.recyclerview.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            InnerAdapter mAdapter = new InnerAdapter(itemView.getContext());
            mBinding.recyclerview.setAdapter(mAdapter);
            mAdapter.setData(HomePageItem.getPageArticleListBean.getData());

            mBinding.rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArticleListActivity.start(itemView.getContext());
                }
            });
        }
    }


    private class InnerAdapter extends AbsRecyclerViewAdapter<GetPageArticleListBean.DataBean> {
        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<GetPageArticleListBean.DataBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new HomePageNewViewHolder(mInflater.inflate(R.layout.item_home_news_list, parent, false));
        }
    }

    public class HomePageNewViewHolder extends AbsRecyclerViewHolder<GetPageArticleListBean.DataBean> {

        private ItemHomeNewsListBinding mBinding;

        public HomePageNewViewHolder(View itemView) {
            super(itemView);
            mBinding = ItemHomeNewsListBinding.bind(itemView);
        }

        @Override
        protected void onBindView(final GetPageArticleListBean.DataBean item) {
            mBinding.tvTitle.setText(item.getTitle());
            mBinding.tvContent.setText(item.getContent_text());
            mBinding.tv3.setText(item.getRelease_time());
            ImageLoader.getInstance().displayImage(item.getContent_url(), mBinding.im);

            mBinding.rlNews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArticleDetailActivity.start(itemView.getContext(),item.getId());
                }
            });
        }
    }
}
