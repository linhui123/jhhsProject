package com.jhhscm.platform.fragment.repayment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jhhscm.platform.R;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.FragmentRepaymentBinding;
import com.jhhscm.platform.databinding.FragmentRepaymentDetailBinding;
import com.jhhscm.platform.event.FinishEvent;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.lessee.LesseeBean;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.views.dialog.PayDialog;
import com.jhhscm.platform.views.recyclerview.WrappedRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RepaymentDetailFragment extends AbsFragment<FragmentRepaymentDetailBinding> {
    private InnerAdapter mAdapter;

    public static RepaymentDetailFragment instance() {
        RepaymentDetailFragment view = new RepaymentDetailFragment();
        return view;
    }

    @Override
    protected FragmentRepaymentDetailBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentRepaymentDetailBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        EventBusUtil.registerEvent(this);
        mDataBinding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mAdapter = new InnerAdapter(getContext());
        mDataBinding.recyclerview.setAdapter(mAdapter);
        initView(true);

        mDataBinding.tvPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PayDialog(getContext()).show();
            }
        });
    }

    public void onEvent(FinishEvent finishEvent) {
    }

    private void initView(boolean refresh) {
        List<LesseeBean.WBankLeaseItemsBean> wBankLeaseItemsBeans = new ArrayList<>();
        wBankLeaseItemsBeans.add(new LesseeBean.WBankLeaseItemsBean());
        wBankLeaseItemsBeans.add(new LesseeBean.WBankLeaseItemsBean());
        wBankLeaseItemsBeans.add(new LesseeBean.WBankLeaseItemsBean());
        wBankLeaseItemsBeans.add(new LesseeBean.WBankLeaseItemsBean());
        wBankLeaseItemsBeans.add(new LesseeBean.WBankLeaseItemsBean());
        wBankLeaseItemsBeans.add(new LesseeBean.WBankLeaseItemsBean());
        if (refresh) {
            mAdapter.setData(wBankLeaseItemsBeans);
        } else {
            mAdapter.append(wBankLeaseItemsBeans);
        }
    }

    @Override
    public void onDestroy() {
        EventBusUtil.unregisterEvent(this);
        super.onDestroy();
    }

    private class InnerAdapter extends AbsRecyclerViewAdapter<LesseeBean.WBankLeaseItemsBean> {

        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<LesseeBean.WBankLeaseItemsBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new RepaymentStagesViewHolder(mInflater.inflate(R.layout.item_repay_stages, parent, false));
        }
    }

}
