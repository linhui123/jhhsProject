package com.jhhscm.platform.fragment.my.book;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.ServiceRecordActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemBookingBinding;
import com.jhhscm.platform.databinding.ItemStoreMemberBinding;
import com.jhhscm.platform.fragment.home.bean.GetPageArticleListBean;
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.views.recyclerview.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class ItemBookingViewHolder extends AbsRecyclerViewHolder<GetPageArticleListBean.DataBean> {

    private ItemBookingBinding mBinding;
    private boolean isShow;

    public ItemBookingViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemBookingBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final GetPageArticleListBean.DataBean item) {
        mBinding.num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShow) {
                    isShow = false;
                    mBinding.detail.setVisibility(View.GONE);
                    Drawable drawable = itemView.getContext().getResources().getDrawable(R.mipmap.ic_down_397);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    mBinding.num.setCompoundDrawables(null, null, drawable, null);
                } else {
                    isShow = true;
                    mBinding.detail.setVisibility(View.VISIBLE);
                    Drawable drawable = itemView.getContext().getResources().getDrawable(R.mipmap.ic_up_397);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    mBinding.num.setCompoundDrawables(null, null, drawable, null);
                }
            }
        });
        mBinding.recyclerview.addItemDecoration(new DividerItemDecoration(itemView.getContext()));
        mBinding.recyclerview.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        InnerAdapter mAdapter = new InnerAdapter(itemView.getContext());
        mBinding.recyclerview.setAdapter(mAdapter);

        List<GetPageArticleListBean.DataBean> list = new ArrayList<>();
        list.add(item);
        list.add(item);
        mAdapter.setData(list);

    }

    private class InnerAdapter extends AbsRecyclerViewAdapter<GetPageArticleListBean.DataBean> {
        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<GetPageArticleListBean.DataBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ItemBookingListViewHolder(mInflater.inflate(R.layout.item_booking_list, parent, false));
        }
    }
}
