package com.jhhscm.platform.fragment.home.holder;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.BrandActivity;
import com.jhhscm.platform.activity.MechanicsByBrandActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.bean.UserData;
import com.jhhscm.platform.databinding.ItemHomePagePhoneBinding;
import com.jhhscm.platform.fragment.home.HomePageItem;
import com.jhhscm.platform.databinding.ItemHomePageBrandBinding;
import com.jhhscm.platform.databinding.ItemHomeProjectListBinding;
import com.jhhscm.platform.fragment.home.bean.FindBrandHomePageBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class HomePageBrandViewHolder extends AbsRecyclerViewHolder<HomePageItem> {

    private ItemHomePageBrandBinding mBinding;

    public HomePageBrandViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemHomePageBrandBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final HomePageItem item) {
        if (HomePageItem.findBrandHomePageBean != null) {
            mBinding.layoutProject.setLayoutManager(new GridLayoutManager(itemView.getContext(), 3));
            InnerAdapter mAdapter = new InnerAdapter(itemView.getContext());
            mBinding.layoutProject.setAdapter(mAdapter);
            mAdapter.setData(HomePageItem.findBrandHomePageBean.getResult());

            mBinding.rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BrandActivity.start(itemView.getContext(),2);
                }
            });
        }
    }


    private class InnerAdapter extends AbsRecyclerViewAdapter<FindBrandHomePageBean.ResultBean> {
        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<FindBrandHomePageBean.ResultBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new HomePageProjectViewHolder(mInflater.inflate(R.layout.item_home_project_list, parent, false));
        }
    }

    public class HomePageProjectViewHolder extends AbsRecyclerViewHolder<FindBrandHomePageBean.ResultBean> {

        private ItemHomeProjectListBinding mBinding;

        public HomePageProjectViewHolder(View itemView) {
            super(itemView);
            mBinding = ItemHomeProjectListBinding.bind(itemView);
        }

        @Override
        protected void onBindView(final FindBrandHomePageBean.ResultBean item) {
            mBinding.tvBrand.setText(item.getName());
            ImageLoader.getInstance().displayImage(item.getPic_url(), mBinding.imBrand);

            mBinding.rlName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MechanicsByBrandActivity.start(itemView.getContext(), item.getId());
                }
            });
        }
    }
}
