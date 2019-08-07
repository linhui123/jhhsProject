package com.jhhscm.platform.fragment.home.holder;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.LabourActivity;
import com.jhhscm.platform.activity.LabourDetailActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.bean.UserData;
import com.jhhscm.platform.databinding.ItemHomePageMsgBinding;
import com.jhhscm.platform.databinding.ItemHomeMsgListBinding;
import com.jhhscm.platform.fragment.home.HomePageItem;
import com.jhhscm.platform.fragment.home.bean.FindLabourReleaseHomePageBean;
import com.jhhscm.platform.fragment.labour.FindLabourReleaseListBean;

import java.util.ArrayList;
import java.util.List;

public class HomePageMsgViewHolder extends AbsRecyclerViewHolder<HomePageItem> {

    private ItemHomePageMsgBinding mBinding;

    public HomePageMsgViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemHomePageMsgBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final HomePageItem item) {
        mBinding.layoutProject.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        InnerAdapter mAdapter = new InnerAdapter(itemView.getContext());
        mBinding.layoutProject.setAdapter(mAdapter);
        mAdapter.setData(HomePageItem.findLabourReleaseHomePageBean.getData());

        mBinding.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LabourActivity.start(itemView.getContext());
            }
        });
    }

    private class InnerAdapter extends AbsRecyclerViewAdapter<FindLabourReleaseHomePageBean.DataBean> {
        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<FindLabourReleaseHomePageBean.DataBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new HomeMsgViewHolder(mInflater.inflate(R.layout.item_home_msg_list, parent, false));
        }
    }

    public class HomeMsgViewHolder extends AbsRecyclerViewHolder<FindLabourReleaseHomePageBean.DataBean> {

        private ItemHomeMsgListBinding mBinding;

        public HomeMsgViewHolder(View itemView) {
            super(itemView);
            mBinding = ItemHomeMsgListBinding.bind(itemView);
        }

        @Override
        protected void onBindView(final FindLabourReleaseHomePageBean.DataBean item) {
            mBinding.tvMsg.setText(item.getName());
            mBinding.rlName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LabourDetailActivity.start(itemView.getContext(), 0,
                            new FindLabourReleaseListBean.DataBean(item.getId(), "0", item.getLabour_code()));
                }
            });
        }
    }
}

