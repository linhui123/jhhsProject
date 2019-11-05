package com.jhhscm.platform.fragment.my.book;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.BookingDetailActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemBookingBinding;
import com.jhhscm.platform.databinding.ItemBookingListBinding;
import com.jhhscm.platform.fragment.home.bean.GetPageArticleListBean;
import com.jhhscm.platform.views.recyclerview.DividerItemDecoration;

public class ItemBookingListViewHolder extends AbsRecyclerViewHolder<GetPageArticleListBean.DataBean> {

    private ItemBookingListBinding mBinding;
    private boolean isShow;

    public ItemBookingListViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemBookingListBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final GetPageArticleListBean.DataBean item) {
        mBinding.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getAdapterPosition() == 0) {
                    BookingDetailActivity.start(itemView.getContext(), 0);
                } else {
                    BookingDetailActivity.start(itemView.getContext(), 1);
                }
            }
        });
    }

}

