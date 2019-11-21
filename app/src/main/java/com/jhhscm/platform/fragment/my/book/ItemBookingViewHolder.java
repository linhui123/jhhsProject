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
import com.jhhscm.platform.tool.DataUtil;
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.views.recyclerview.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class ItemBookingViewHolder extends AbsRecyclerViewHolder<AllSumByDataTimeBean.DataBean> {

    private ItemBookingBinding mBinding;
    private boolean isShow;

    public ItemBookingViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemBookingBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final AllSumByDataTimeBean.DataBean item) {
        if (item != null) {
            if (item.getData_time() != null) {
                mBinding.time.setText(DataUtil.getDateStr(item.getData_time().length() > 10 ? item.getData_time().substring(0, 10) : item.getData_time()
                        , "yyyy年MM月dd日"));
            } else {
                mBinding.time.setText("----年--月--日");
            }

            mBinding.num.setText("共" + item.getDetail().size() + "笔");
            mBinding.income.setText("已收 " + item.getPrice_1());
            mBinding.unIncome.setText("未收 " + item.getPrice_2());
            mBinding.pay.setText("支出 " + item.getPrice_3());
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
            if (getAdapterPosition() == 0) {
                isShow = true;
                mBinding.detail.setVisibility(View.VISIBLE);
                Drawable drawable = itemView.getContext().getResources().getDrawable(R.mipmap.ic_up_397);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                mBinding.num.setCompoundDrawables(null, null, drawable, null);
            }

            mBinding.recyclerview.addItemDecoration(new DividerItemDecoration(itemView.getContext()));
            mBinding.recyclerview.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            InnerAdapter mAdapter = new InnerAdapter(itemView.getContext());
            mBinding.recyclerview.setAdapter(mAdapter);


            mAdapter.setData(item.getDetail());
        }
    }

    private class InnerAdapter extends AbsRecyclerViewAdapter<AllSumByDataTimeBean.DataBean.DetailBean> {
        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<AllSumByDataTimeBean.DataBean.DetailBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ItemBookingListViewHolder(mInflater.inflate(R.layout.item_booking_list, parent, false));
        }
    }
}
