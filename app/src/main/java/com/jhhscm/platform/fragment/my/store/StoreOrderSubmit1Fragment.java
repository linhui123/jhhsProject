package com.jhhscm.platform.fragment.my.store;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.github.mikephil.charting.formatter.IFillFormatter;
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
import com.jhhscm.platform.fragment.Mechanics.action.FindBrandAction;
import com.jhhscm.platform.fragment.Mechanics.action.GetComboBoxAction;
import com.jhhscm.platform.fragment.Mechanics.bean.FindBrandBean;
import com.jhhscm.platform.fragment.Mechanics.bean.GetComboBoxBean;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.lessee.LesseeBean;
import com.jhhscm.platform.fragment.my.mechanics.FindGoodsOwnerBean;
import com.jhhscm.platform.fragment.my.store.action.FindUserGoodsOwnerBean;
import com.jhhscm.platform.fragment.my.store.viewholder.ItemFaultDeviceViewHolder;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.StringUtils;
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.tool.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

public class StoreOrderSubmit1Fragment extends AbsFragment<FragmentStoreOrderSubmit1Binding> {
    private InnerAdapter mAdapter;
    private LesseeBean lesseeBean;
    private List<FindUserGoodsOwnerBean.DataBean> itemsBeans;

    private String userName;
    private String userCode;
    private String userMobile;

    private List<String> goodsOwnerV1s;//设备序列号
    private List<String> goodsOwnerV2s;//gps序列号
    private List<String> goodsOwnerV3s;//故障类型
    private List<String> brandIds;
    private List<String> fixs;

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
        getComboBox("bus_order_event");
    }

    private void initView() {
        mDataBinding.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FindUserGoodsOwnerBean.DataBean dataBean = new FindUserGoodsOwnerBean.DataBean();
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
                if (mDataBinding.phone.getText().toString().length() > 8) {
                    SelectDeviceActivity.start(getContext(), mDataBinding.phone.getText().toString());
                } else {
                    ToastUtil.show(getContext(), "请先输入用户手机号");
                }

            }
        });

        mDataBinding.tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                lesseeBean.setWBankLeaseItems(itemsBeans);
                if (judge()) {
                    FindUserGoodsOwnerBean findUserGoodsOwnerBean = new FindUserGoodsOwnerBean();
                    findUserGoodsOwnerBean.setData(itemsBeans);
                    StoreOrderSubmit2Activity.start(getContext(), findUserGoodsOwnerBean, userName, userMobile);
                }
            }
        });

        initList();
    }

    private void initList() {
        mDataBinding.rv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new InnerAdapter(getContext());
        mDataBinding.rv.setAdapter(mAdapter);

        itemsBeans = new ArrayList<>();
        FindUserGoodsOwnerBean.DataBean dataBean = new FindUserGoodsOwnerBean.DataBean();
        itemsBeans.add(dataBean);
        mAdapter.setData(itemsBeans);
    }

    private boolean judge() {
        for (FindUserGoodsOwnerBean.DataBean wBankLeaseItemsBean : itemsBeans) {
            if (StringUtils.isNullEmpty(wBankLeaseItemsBean.getBrand_name())) {
                ToastUtil.show(getContext(), "设备品牌不能为空");
                return false;
            }
            if (StringUtils.isNullEmpty(wBankLeaseItemsBean.getFixp17())) {
                ToastUtil.show(getContext(), "型号不能为空");
                return false;
            }
            if (StringUtils.isNullEmpty(wBankLeaseItemsBean.getNo())) {
                ToastUtil.show(getContext(), "序列号不能为空");
                return false;
            }
            if (StringUtils.isNullEmpty(wBankLeaseItemsBean.getGps_no())) {
                ToastUtil.show(getContext(), "GPS序列号不能为空");
                return false;
            }
            if (StringUtils.isNullEmpty(wBankLeaseItemsBean.getError_no())) {
                ToastUtil.show(getContext(), "故障类型不能为空");
                return false;
            }
        }
        userName = mDataBinding.name.getText().toString().trim();
        if (StringUtils.isNullEmpty(userName)) {
            ToastUtil.show(getContext(), "用户姓名不能为空");
            return false;
        }
        userMobile = mDataBinding.phone.getText().toString().trim();
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
            if (mAdapter.getItemCount() > 0 && mAdapter.get(0) != null
                    && mAdapter.get(0).getBrand_id() != null && mAdapter.get(0).getFixp17() != null) {
                mAdapter.append(event.dataBeans);
                itemsBeans.addAll(event.dataBeans);
                mDataBinding.tvDel.setVisibility(View.VISIBLE);
            } else {
                mAdapter.setData(event.dataBeans);
                itemsBeans.clear();
                itemsBeans.addAll(event.dataBeans);
                if (event.dataBeans.size() > 1) {
                    mDataBinding.tvDel.setVisibility(View.VISIBLE);
                } else {
                    mDataBinding.tvDel.setVisibility(View.GONE);
                }
            }
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

    private class InnerAdapter extends AbsRecyclerViewAdapter<FindUserGoodsOwnerBean.DataBean> {

        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<FindUserGoodsOwnerBean.DataBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ItemFaultDeviceViewHolder(mInflater.inflate(R.layout.item_fault_devices, parent, false), list, errorList);
        }
    }

    /**
     * 获取品牌列表
     */
    private void findBrand() {
        if (getContext() != null) {
            showDialog();
            Map<String, String> map = new TreeMap<String, String>();
            map.put("brand_type", "1");
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = Sign.getSignKey(getActivity(), map, "findBrand");
            NetBean netBean = new NetBean();
            netBean.setToken("");
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(FindBrandAction.newInstance(getContext(), netBean)
                    .request(new AHttpService.IResCallback<BaseEntity<FindBrandBean>>() {
                        @Override
                        public void onCallback(int resultCode, Response<BaseEntity<FindBrandBean>> response,
                                               BaseErrorInfo baseErrorInfo) {
                            if (getView() != null) {
                                closeDialog();
                                if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                    return;
                                }
                                if (response != null) {
                                    new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                    if (response.body().getCode().equals("200")) {
                                        doSuccessResponse(response.body().getData());
                                    } else {
                                        ToastUtils.show(getContext(), response.body().getMessage());
                                    }
                                }
                            }
                        }
                    }));
        }
    }

    private FindBrandBean findBrandBean;
    private List<GetComboBoxBean.ResultBean> list;
    private GetComboBoxBean errorList;

    private void doSuccessResponse(FindBrandBean categoryBean) {
        this.findBrandBean = categoryBean;
        list = new ArrayList<>();
        for (FindBrandBean.ResultBean resultBean : findBrandBean.getResult()) {
            list.add(new GetComboBoxBean.ResultBean(resultBean.getId(), resultBean.getName()));
        }
        initView();
    }

    /**
     * 获取故障列表 bus_order_event
     */
    private void getComboBox(final String name) {
        showDialog();
        Map<String, String> map = new TreeMap<String, String>();
        map.put("key_group_name", name);
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getActivity(), map, "getComboBox:" + name);
        NetBean netBean = new NetBean();
        netBean.setToken("");
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(GetComboBoxAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<GetComboBoxBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<GetComboBoxBean>> response, BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                if (response.body().getCode().equals("200")) {
                                    if ("bus_order_event".equals(name)) {
                                        findBrand();
                                        errorList = response.body().getData();
                                    }
                                } else {
                                    ToastUtils.show(getContext(), "error " + name + ":" + response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
    }


}