package com.jhhscm.platform.fragment.lessee;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.Lessee3Activity;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.FragmentLessee1Binding;
import com.jhhscm.platform.databinding.FragmentLessee2Binding;
import com.jhhscm.platform.event.LesseeFinishEvent;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.labour.FindLabourReleaseListBean;
import com.jhhscm.platform.fragment.labour.LabourFragment;
import com.jhhscm.platform.fragment.labour.LabourViewHolder;
import com.jhhscm.platform.http.bean.UserSession;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.views.recyclerview.DividerItemStrokeDecoration;

import java.util.ArrayList;
import java.util.List;


public class Lessee2Fragment extends AbsFragment<FragmentLessee2Binding> {
    private FindLabourReleaseListBean.DataBean dataBean;
    private int type;
    private UserSession userSession;
    private String id;
    private InnerAdapter mAdapter;
    private List<FindLabourReleaseListBean.DataBean> dataBeans;

    public static Lessee2Fragment instance() {
        Lessee2Fragment view = new Lessee2Fragment();
        return view;
    }

    @Override
    protected FragmentLessee2Binding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentLessee2Binding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        EventBusUtil.registerEvent(this);
        initList();

        mDataBinding.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FindLabourReleaseListBean.DataBean dataBean = new FindLabourReleaseListBean.DataBean();
                dataBeans.add(dataBean);
                mAdapter.add(dataBean);
                mDataBinding.tvDel.setVisibility(View.VISIBLE);
            }
        });

        mDataBinding.tvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAdapter.getItemCount() > 1) {
                    mAdapter.remove(mAdapter.getItemCount() - 1);
                }
                if (mAdapter.getItemCount() == 1) {
                    mDataBinding.tvDel.setVisibility(View.GONE);
                }
            }
        });

        mDataBinding.tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Lessee3Activity.start(getContext());
            }
        });
    }

    public void onEvent(LesseeFinishEvent event) {
        getActivity().finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }

    private void initList() {
        mDataBinding.rv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new InnerAdapter(getContext());
        mDataBinding.rv.setAdapter(mAdapter);

        dataBeans = new ArrayList<>();
        FindLabourReleaseListBean.DataBean dataBean = new FindLabourReleaseListBean.DataBean();
        dataBeans.add(dataBean);
        mAdapter.setData(dataBeans);
    }

    private class InnerAdapter extends AbsRecyclerViewAdapter<FindLabourReleaseListBean.DataBean> {
        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<FindLabourReleaseListBean.DataBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new LesseeViewHolder(mInflater.inflate(R.layout.item_lessee_mechanics, parent, false));
        }
    }


}
