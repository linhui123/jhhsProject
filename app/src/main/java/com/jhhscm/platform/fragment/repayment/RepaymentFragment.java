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
import com.jhhscm.platform.databinding.FragmentLessee1Binding;
import com.jhhscm.platform.databinding.FragmentRepaymentBinding;
import com.jhhscm.platform.fragment.Mechanics.NewMechanicsFragment;
import com.jhhscm.platform.fragment.Mechanics.bean.GetComboBoxBean;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.lessee.Lessee1Fragment;
import com.jhhscm.platform.fragment.lessee.Lessee2Fragment;
import com.jhhscm.platform.fragment.lessee.LesseeBean;
import com.jhhscm.platform.fragment.lessee.LesseeViewHolder;
import com.jhhscm.platform.views.recyclerview.DividerItemStrokeDecoration;
import com.jhhscm.platform.views.recyclerview.WrappedRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RepaymentFragment extends AbsFragment<FragmentRepaymentBinding> {
    private InnerAdapter mAdapter;

    public static RepaymentFragment instance() {
        RepaymentFragment view = new RepaymentFragment();
        return view;
    }

    @Override
    protected FragmentRepaymentBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentRepaymentBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        mDataBinding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new InnerAdapter(getContext());
        mDataBinding.recyclerview.setAdapter(mAdapter);
        mDataBinding.recyclerview.autoRefresh();
        mDataBinding.recyclerview.setOnPullListener(new WrappedRecyclerView.OnPullListener() {
            @Override
            public void onRefresh(RecyclerView view) {
                initView(true);
            }

            @Override
            public void onLoadMore(RecyclerView view) {
                initView(false);
            }
        });
    }


    private void initView(boolean refresh) {
        List<LesseeBean.WBankLeaseItemsBean> wBankLeaseItemsBeans = new ArrayList<>();
        wBankLeaseItemsBeans.add(new LesseeBean.WBankLeaseItemsBean());
        wBankLeaseItemsBeans.add(new LesseeBean.WBankLeaseItemsBean());
        wBankLeaseItemsBeans.add(new LesseeBean.WBankLeaseItemsBean());
        if (refresh) {
            mAdapter.setData(wBankLeaseItemsBeans);
            mDataBinding.recyclerview.loadComplete(true,true);
        } else {
            mAdapter.append(wBankLeaseItemsBeans);
            mDataBinding.recyclerview.loadComplete(true,false);
        }

    }

    private class InnerAdapter extends AbsRecyclerViewAdapter<LesseeBean.WBankLeaseItemsBean> {

        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<LesseeBean.WBankLeaseItemsBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new RepaymentViewHolder(mInflater.inflate(R.layout.item_repayment, parent, false));
        }
    }
}
