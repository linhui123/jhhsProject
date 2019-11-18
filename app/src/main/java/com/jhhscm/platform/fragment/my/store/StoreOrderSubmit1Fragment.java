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
import com.jhhscm.platform.event.StoreDeviceEvent;
import com.jhhscm.platform.event.StoreUserEvent;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.lessee.LesseeBean;
import com.jhhscm.platform.fragment.my.mechanics.FindGoodsOwnerBean;
import com.jhhscm.platform.fragment.my.store.viewholder.ItemFaultDeviceViewHolder;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.StringUtils;
import com.jhhscm.platform.tool.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class StoreOrderSubmit1Fragment extends AbsFragment<FragmentStoreOrderSubmit1Binding> {
    private InnerAdapter mAdapter;
    private LesseeBean lesseeBean;
    private List<FindGoodsOwnerBean.DataBean> itemsBeans;

    private String userName;
    private String userCode;
    private String userMobile;

    private List<String> goodsOwnerV1s;//设备序列号
    private List<String> goodsOwnerV2s;//gps序列号
    private List<String> goodsOwnerV3s;//故障类型

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
                FindGoodsOwnerBean.DataBean dataBean = new FindGoodsOwnerBean.DataBean();
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
        FindGoodsOwnerBean.DataBean dataBean = new FindGoodsOwnerBean.DataBean();
        itemsBeans.add(dataBean);
        mAdapter.setData(itemsBeans);
    }


    private boolean judge() {
        for (FindGoodsOwnerBean.DataBean wBankLeaseItemsBean : itemsBeans) {
            if (StringUtils.isNullEmpty(wBankLeaseItemsBean.getBrand_name())) {
                ToastUtil.show(getContext(), "设备品牌不能为空");
                return false;
            }
            if (StringUtils.isNullEmpty(wBankLeaseItemsBean.getFixp17())) {
                ToastUtil.show(getContext(), "型号不能为空");
                return false;
            }
        }

        if (StringUtils.isNullEmpty(userName)) {
            ToastUtil.show(getContext(), "用户姓名不能为空");
            return false;
        }
        if (StringUtils.isNullEmpty(userMobile)) {
            ToastUtil.show(getContext(), "用户手机号不能为空");
            return false;
        }
        return true;
    }

    public void onEvent(StoreUserEvent event) {
        if (event.userCode != null) {
            userCode = event.userCode;
            userMobile = event.userMobile;
            userName = event.userName;
            mDataBinding.name.setText(event.userName);
            mDataBinding.phone.setText(event.userMobile);
        }
    }

    public void onEvent(StoreDeviceEvent event) {
        if (event.dataBeans != null && event.dataBeans.size() > 0) {
            mAdapter.setData(event.dataBeans);
        }
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

    private class InnerAdapter extends AbsRecyclerViewAdapter<FindGoodsOwnerBean.DataBean> {

        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<FindGoodsOwnerBean.DataBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ItemFaultDeviceViewHolder(mInflater.inflate(R.layout.item_fault_devices, parent, false));
        }
    }
}
