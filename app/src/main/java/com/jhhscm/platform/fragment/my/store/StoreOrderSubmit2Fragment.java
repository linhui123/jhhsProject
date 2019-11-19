package com.jhhscm.platform.fragment.my.store;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.FragmentStoreOrderSubmit2Binding;
import com.jhhscm.platform.event.FinishEvent;
import com.jhhscm.platform.event.RefreshEvent;
import com.jhhscm.platform.fragment.Mechanics.push.UpdateImageBean;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.home.action.GetArticleListAction;
import com.jhhscm.platform.fragment.home.bean.GetPageArticleListBean;
import com.jhhscm.platform.fragment.my.mechanics.AddGoodsOwnerAction;
import com.jhhscm.platform.fragment.my.store.action.BusinessFindcategorybyBuscodeAction;
import com.jhhscm.platform.fragment.my.store.action.BusinessFindcategorybyBuscodeBean;
import com.jhhscm.platform.fragment.my.store.action.BusorderCreateOrderAction;
import com.jhhscm.platform.fragment.my.store.action.FindUserGoodsOwnerBean;
import com.jhhscm.platform.fragment.my.store.viewholder.StoreOrderProductItemViewHolder;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.ResultBean;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.http.sign.SignObject;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.recyclerview.DividerItemDecoration;
import com.jhhscm.platform.views.recyclerview.WrappedRecyclerView;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

public class StoreOrderSubmit2Fragment extends AbsFragment<FragmentStoreOrderSubmit2Binding> {
    private InnerAdapter mAdapter;

    private int mShowCount = 10;
    private int mCurrentPage = 1;
    private final int START_PAGE = mCurrentPage;

    private String goodsOwnerV1s = "";//设备序列号
    private String goodsOwnerV2s = "";//gps序列号
    private String goodsOwnerV3s = "";//故障类型
    private String brandIds = "";
    private String fixs = "";

    private String goodsCodes = "";//商品编号
    private String goodsList = "";//商品编号对应数量
    private FindUserGoodsOwnerBean dataBean;
    private String name = "";
    private String phone = "";

    public static StoreOrderSubmit2Fragment instance() {
        StoreOrderSubmit2Fragment view = new StoreOrderSubmit2Fragment();
        return view;
    }

    @Override
    protected FragmentStoreOrderSubmit2Binding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentStoreOrderSubmit2Binding.inflate(inflater, container, attachToRoot);
    }


    @Override
    protected void setupViews() {
        EventBusUtil.registerEvent(this);
        name = getArguments().getString("name");
        phone = getArguments().getString("phone");
        dataBean = (FindUserGoodsOwnerBean) getArguments().getSerializable("databean");

        mDataBinding.recyclerview.addItemDecoration(new DividerItemDecoration(getContext()));
        mDataBinding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new InnerAdapter(getContext());
        mDataBinding.recyclerview.setAdapter(mAdapter);
        mDataBinding.recyclerview.autoRefresh();
//        mDataBinding.recyclerview.hideLoad();
        mDataBinding.recyclerview.setOnPullListener(new WrappedRecyclerView.OnPullListener() {
            @Override
            public void onRefresh(RecyclerView view) {
                businessFindcategorybyBuscode(true);
            }

            @Override
            public void onLoadMore(RecyclerView view) {
                businessFindcategorybyBuscode(false);
            }
        });

        mDataBinding.workFee.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                double total = 0.0;
                if (s.toString().length() > 0) {
                    if (mDataBinding.peijianFee.getText().toString().length() > 0) {
                        total = Double.parseDouble(mDataBinding.peijianFee.getText().toString()) + Double.parseDouble(mDataBinding.workFee.getText().toString());
                    } else {
                        total = Double.parseDouble(mDataBinding.workFee.getText().toString());
                    }
                } else {
                    if (mDataBinding.peijianFee.getText().toString().length() > 0) {
                        total = Double.parseDouble(mDataBinding.peijianFee.getText().toString());
                    }
                }
                mDataBinding.total.setText(total + "");
            }
        });


        mDataBinding.tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name != null && phone != null && dataBean != null) {
                    busorder_createOrder();
                }
            }
        });
    }

    private void businessFindcategorybyBuscode(final boolean refresh) {
        if (getContext() != null) {
            mCurrentPage = refresh ? START_PAGE : ++mCurrentPage;
            Map<String, Object> map = new TreeMap<String, Object>();
            map.put("page", mCurrentPage);
            map.put("limit", mShowCount);
            map.put("user_code", ConfigUtils.getCurrentUser(getContext()).getUserCode());
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = SignObject.getSignKey(getActivity(), map, "businessFindcategorybyBuscode");
            NetBean netBean = new NetBean();
            netBean.setToken(ConfigUtils.getCurrentUser(getContext()).getToken());
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(BusinessFindcategorybyBuscodeAction.newInstance(getContext(), netBean)
                    .request(new AHttpService.IResCallback<BaseEntity<BusinessFindcategorybyBuscodeBean>>() {
                        @Override
                        public void onCallback(int resultCode, Response<BaseEntity<BusinessFindcategorybyBuscodeBean>> response,
                                               BaseErrorInfo baseErrorInfo) {
                            if (getView() != null) {
                                closeDialog();
                                if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                    return;
                                }
                                if (response != null) {
                                    new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                    if (response.body().getCode().equals("200")) {

                                        initView(refresh, response.body().getData());
                                    } else {
                                        ToastUtils.show(getContext(), response.body().getMessage());
                                    }
                                }
                            }
                        }
                    }));
        }
    }

    BusinessFindcategorybyBuscodeBean getPushListBean;

    private void initView(boolean refresh, BusinessFindcategorybyBuscodeBean pushListBean) {

        this.getPushListBean = pushListBean;
        if (refresh) {
            mAdapter.setData(pushListBean.getData());
        } else {
            mAdapter.append(pushListBean.getData());
        }
        mDataBinding.recyclerview.loadComplete(mAdapter.getItemCount() == 0,
                ((float) getPushListBean.getPage().getTotal() / (float) getPushListBean.getPage().getPageSize()) > mCurrentPage);
    }

    private class InnerAdapter extends AbsRecyclerViewAdapter<BusinessFindcategorybyBuscodeBean.DataBean> {
        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<BusinessFindcategorybyBuscodeBean.DataBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new StoreOrderProductItemViewHolder(mInflater.inflate(R.layout.item_store_order_product, parent, false));
        }
    }

    public void onEvent(RefreshEvent event) {
        //计算金额
//        BusinessFindcategorybyBuscodeBean
        double peijian = 0.0;
        for (int i = 0; i < mAdapter.getItemCount(); i++) {
            if (mAdapter.get(i).isSelect()) {
                peijian = peijian + mAdapter.get(i).getCounter_price() * mAdapter.get(i).getNum();
            }
        }
        mDataBinding.peijianFee.setText(peijian + "");
        double total = 0.0;
        if (mDataBinding.workFee.getText().toString().length() > 0) {
            total = peijian + Double.parseDouble(mDataBinding.workFee.getText().toString());
        } else {
            total = peijian;
        }
        mDataBinding.total.setText(total + "");
        ToastUtil.show(getContext(), "计算金额");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }

    /**
     * 我的店铺商户提交订单
     */
    private void busorder_createOrder() {
        if (getContext() != null) {
            showDialog();
            if (dataBean.getData().size() > 0) {
                for (FindUserGoodsOwnerBean.DataBean updateImageBean : dataBean.getData()) {
                    if (updateImageBean.getBrand_id() != null) {
                        if (brandIds.length() > 0) {
                            brandIds = brandIds + "," + "" + updateImageBean.getBrand_id() + "";
                        } else {
                            brandIds = "" + updateImageBean.getBrand_id() + "";
                        }
                    }
                    if (updateImageBean.getFixp17() != null) {
                        if (fixs.length() > 0) {
                            fixs = fixs + "," + "" + updateImageBean.getFixp17() + "";
                        } else {
                            fixs = "" + updateImageBean.getFixp17() + "";
                        }
                    }
                    if (updateImageBean.getNo() != null) {
                        if (goodsOwnerV1s.length() > 0) {
                            goodsOwnerV1s = goodsOwnerV1s + "," + "" + updateImageBean.getNo() + "";
                        } else {
                            goodsOwnerV1s = "" + updateImageBean.getNo() + "";
                        }
                    }

                    if (updateImageBean.getGps_no() != null) {
                        if (goodsOwnerV2s.length() > 0) {
                            goodsOwnerV2s = goodsOwnerV2s + "," + "" + updateImageBean.getGps_no() + "";
                        } else {
                            goodsOwnerV2s = "" + updateImageBean.getGps_no() + "";
                        }
                    }
                    if (updateImageBean.getError_no() != null) {
                        if (goodsOwnerV3s.length() > 0) {
                            goodsOwnerV3s = goodsOwnerV3s + "," + "" + updateImageBean.getError_no() + "";
                        } else {
                            goodsOwnerV3s = "" + updateImageBean.getError_no() + "";
                        }
                    }
                }
            }

            if (getPushListBean.getData().size() > 0) {
                for (BusinessFindcategorybyBuscodeBean.DataBean dataBean : getPushListBean.getData()) {
                    if (dataBean.isSelect() && dataBean.getNum() > 0) {
                        if (goodsCodes.length() > 0) {
                            goodsCodes = goodsCodes + "," + "" + dataBean.getGood_code() + "";
                        } else {
                            goodsCodes = "" + dataBean.getGood_code() + "";
                        }

                        if (goodsList.length() > 0) {
                            goodsList = goodsList + "," + "\"" + dataBean.getGood_code() + "\":" + dataBean.getNum();
                        } else {
                            goodsList = "\"" + dataBean.getGood_code() + "\":" + dataBean.getNum();
                        }
                    }
                }
            }

            Map<String, String> map = new TreeMap<String, String>();
            map.put("userName", name);
            map.put("userMobile", phone);
            map.put("brandIds", brandIds);
            map.put("fixs", fixs);
            map.put("goodsOwnerV1s", goodsOwnerV1s);
            map.put("goodsOwnerV2s", goodsOwnerV2s);
            map.put("goodsOwnerV3s", goodsOwnerV3s);

            map.put("goodsCodes", goodsCodes);
            if (goodsList.length() > 0) {
                map.put("goodsList", "{" + goodsList + "}");
            }

            if (mDataBinding.workFee.getText().toString().length() > 0) {
                map.put("otherPrice", mDataBinding.workFee.getText().toString());
            }
            map.put("busMobile", ConfigUtils.getCurrentUser(getContext()).getMobile());
            map.put("busCode", ConfigUtils.getCurrentUser(getContext()).getUserCode());
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = Sign.getSignKey(getActivity(), map, "addGoodsOwner");
            NetBean netBean = new NetBean();
            netBean.setToken(ConfigUtils.getCurrentUser(getContext()).getToken());
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(BusorderCreateOrderAction.newInstance(getContext(), netBean)
                    .request(new AHttpService.IResCallback<BaseEntity<ResultBean>>() {
                        @Override
                        public void onCallback(int resultCode, Response<BaseEntity<ResultBean>> response,
                                               BaseErrorInfo baseErrorInfo) {
                            if (getView() != null) {
                                closeDialog();
                                if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                    return;
                                }
                                if (response != null) {
                                    new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                    if (response.body().getCode().equals("200")) {
                                        EventBusUtil.post(new RefreshEvent());
                                        getActivity().finish();
                                        EventBusUtil.post(new FinishEvent());
                                        ToastUtil.show(getContext(), "提交成功");
                                    } else {
                                        ToastUtils.show(getContext(), response.body().getMessage());
                                    }
                                }
                            }
                        }
                    }));
        }
    }
}
