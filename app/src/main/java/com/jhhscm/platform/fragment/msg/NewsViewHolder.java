package com.jhhscm.platform.fragment.msg;

import android.view.View;

import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.ArticleDetailActivity;
import com.jhhscm.platform.activity.MsgDetailActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemMsgBinding;
import com.jhhscm.platform.databinding.ItemNewsBinding;
import com.jhhscm.platform.fragment.home.bean.GetPageArticleListBean;
import com.nostra13.universalimageloader.core.ImageLoader;

public class NewsViewHolder extends AbsRecyclerViewHolder<GetPageArticleListBean.DataBean> {

    private ItemNewsBinding mBinding;

    public NewsViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemNewsBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final GetPageArticleListBean.DataBean item) {
        if (item != null) {
            mBinding.tvTitle.setText(item.getTitle());
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

