package com.jhhscm.platform.fragment.my.store;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.SelectDeviceActivity;
import com.jhhscm.platform.activity.SelectMemberActivity;
import com.jhhscm.platform.activity.StoreOrderSubmit2Activity;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.FragmentStoreOrderSubmit1Binding;
import com.jhhscm.platform.event.FinishEvent;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.lessee.LesseeBean;
import com.jhhscm.platform.fragment.my.store.viewholder.ItemFaultDeviceViewHolder;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.StringUtils;
import com.jhhscm.platform.tool.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class StoreOrderSubmit1Fragment extends AbsFragment<FragmentStoreOrderSubmit1Binding> {
    private InnerAdapter mAdapter;
    private LesseeBean lesseeBean;
    private List<LesseeBean.WBankLeaseItemsBean> itemsBeans;


    public static StoreOrderSubmit1Fragment instance() {
        StoreOrderSubmit1Fragment view = new StoreOrderSubmit1Fragment();
        return view;
    }

    @Override
    protected FragmentStoreOrderSubmit1Binding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentStoreOrderSubmit1Binding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        EventBusUtil.registerEvent(this);
        lesseeBean = (LesseeBean) getArguments().getSerializable("lesseeBean");

        mDataBinding.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LesseeBean.WBankLeaseItemsBean dataBean = new LesseeBean.WBankLeaseItemsBean();
                itemsBeans.add(dataBean);
                mAdapter.add(dataBean);
                mDataBinding.tvDel.setVisibility(View.VISIBLE);
            }
        });

        mDataBinding.tvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAdapter.getItemCount() > 1) {
                    mAdapter.remove(mAdapter.getItemCount() - 1);
                    itemsBeans.remove(mAdapter.getItemCount());
                }
                if (mAdapter.getItemCount() == 1) {
                    mDataBinding.tvDel.setVisibility(View.GONE);
                }
            }
        });

        mDataBinding.sMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectMemberActivity.start(getContext());
            }
        });

        mDataBinding.sDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectDeviceActivity.start(getContext());
            }
        });

        mDataBinding.tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                lesseeBean.setWBankLeaseItems(itemsBeans);
//                if (judge()) {
                StoreOrderSubmit2Activity.start(getContext());
//        }
            }
        });

        initList();
    }


    private void initList() {
        mDataBinding.rv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new InnerAdapter(getContext());
        mDataBinding.rv.setAdapter(mAdapter);

        itemsBeans = new ArrayList<>();
        LesseeBean.WBankLeaseItemsBean dataBean = new LesseeBean.WBankLeaseItemsBean();
        itemsBeans.add(dataBean);
        mAdapter.setData(itemsBeans);
    }


    private boolean judge() {
        for (LesseeBean.WBankLeaseItemsBean wBankLeaseItemsBean : itemsBeans) {
            if (StringUtils.isNullEmpty(wBankLeaseItemsBean.getBrandId())) {
                ToastUtil.show(getContext(), "设备品牌不能为空");
                return false;
            }
            if (StringUtils.isNullEmpty(wBankLeaseItemsBean.getFactoryTime())) {
                ToastUtil.show(getContext(), "出厂日期不能为空");
                return false;
            }
            if (StringUtils.isNullEmpty(wBankLeaseItemsBean.getMachinePrice())) {
                ToastUtil.show(getContext(), "单价不能为空");
                return false;
            }
            if (StringUtils.isNullEmpty(wBankLeaseItemsBean.getMachineNum())) {
                ToastUtil.show(getContext(), "设备序列号不能为空");
                return false;
            }
            if (StringUtils.isNullEmpty(wBankLeaseItemsBean.getName())) {
                ToastUtil.show(getContext(), "设备名称不能为空");
                return false;
            }
            if (StringUtils.isNullEmpty(wBankLeaseItemsBean.getFixP17())) {
                ToastUtil.show(getContext(), "设备型号不能为空");
                return false;
            }
        }
        return true;
    }

    public void onEvent(FinishEvent event) {
        if (event.getType() == 0) {
            getActivity().finish();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }

    private class InnerAdapter extends AbsRecyclerViewAdapter<LesseeBean.WBankLeaseItemsBean> {

        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<LesseeBean.WBankLeaseItemsBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ItemFaultDeviceViewHolder(mInflater.inflate(R.layout.item_fault_devices, parent, false));
        }
    }
}
