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

public class ItemBookingListViewHolder extends AbsRecyclerViewHolder<AllSumByDataTimeBean.DataBean.DetailBean> {

    private ItemBookingListBinding mBinding;
    private boolean isShow;

    public ItemBookingListViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemBookingListBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final AllSumByDataTimeBean.DataBean.DetailBean item) {
        if (item != null) {
            if (item.getData_type() == 0) {//收入
                mBinding.tv1.setText(item.getIn_type_name());
                mBinding.tv2.setText(item.getData_content());
                mBinding.tv3.setText(item.getPrice_1() + "");
                mBinding.tv4.setText(item.getPrice_2() + "");
                mBinding.pay.setText("");
                mBinding.pay.setVisibility(View.GONE);
                mBinding.llIncome.setVisibility(View.VISIBLE);
            } else {//支出
                mBinding.tv1.setText(item.getOut_type_name());
                mBinding.tv2.setText(item.getData_content());
                mBinding.tv3.setText("");
                mBinding.tv4.setText("");
                mBinding.pay.setText("-" + item.getPrice_3());
                mBinding.pay.setVisibility(View.VISIBLE);
                mBinding.llIncome.setVisibility(View.GONE);
            }

            mBinding.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (item.getData_type() == 0) {//收入
                        BookingDetailActivity.start(itemView.getContext(), 0, item.getData_code());
                    } else {//支出
                        BookingDetailActivity.start(itemView.getContext(), 1, item.getData_code());
                    }
                }
            });
        }
    }
}