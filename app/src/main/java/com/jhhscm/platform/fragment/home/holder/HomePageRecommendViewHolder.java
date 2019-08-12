package com.jhhscm.platform.fragment.home.holder;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.LoginActivity;
import com.jhhscm.platform.activity.MechanicsH5Activity;
import com.jhhscm.platform.activity.PeiJianActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemHomePageRecommendBinding;
import com.jhhscm.platform.databinding.ItemHomeRecommendListBinding;
import com.jhhscm.platform.fragment.Mechanics.bean.FindCategoryBean;
import com.jhhscm.platform.fragment.home.HomePageItem;
import com.jhhscm.platform.fragment.home.bean.FindCategoryHomePageBean;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.UrlUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

public class HomePageRecommendViewHolder extends AbsRecyclerViewHolder<HomePageItem> {

    private ItemHomePageRecommendBinding mBinding;

    public HomePageRecommendViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemHomePageRecommendBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final HomePageItem item) {
        mBinding.layoutProject.setLayoutManager(new GridLayoutManager(itemView.getContext(), 4));
        InnerAdapter mAdapter = new InnerAdapter(itemView.getContext());
        mBinding.layoutProject.setAdapter(mAdapter);
        mAdapter.setData(HomePageItem.findCategoryHomePageBean.getResult());
        mBinding.llLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PeiJianActivity.start(itemView.getContext());
            }
        });
    }

    private class InnerAdapter extends AbsRecyclerViewAdapter<FindCategoryHomePageBean.ResultBean> {
        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<FindCategoryHomePageBean.ResultBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new HomeRecommendViewHolder(mInflater.inflate(R.layout.item_home_recommend_list, parent, false));
        }
    }

    public class HomeRecommendViewHolder extends AbsRecyclerViewHolder<FindCategoryHomePageBean.ResultBean> {

        private ItemHomeRecommendListBinding mBinding;

        public HomeRecommendViewHolder(View itemView) {
            super(itemView);
            mBinding = ItemHomeRecommendListBinding.bind(itemView);
        }

        @Override
        protected void onBindView(final FindCategoryHomePageBean.ResultBean item) {
            mBinding.tvName.setText(item.getName());
            mBinding.tvRemark.setText(item.getKeyword());
            ImageLoader.getInstance().displayImage(item.getIcon_url(), mBinding.iv);
            mBinding.rlName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PeiJianActivity.start(itemView.getContext(), item.getId(), item.getName());
                }
            });
        }
    }
}

