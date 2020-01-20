package com.jhhscm.platform.fragment.my.store;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.StoreOrderSubmit3Activity;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.FragmentStoreOrderSubmit2Binding;
import com.jhhscm.platform.event.FinishEvent;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.my.store.action.BusinessFindcategorybyBuscodeAction;
import com.jhhscm.platform.fragment.my.store.action.BusinessFindcategorybyBuscodeBean;
import com.jhhscm.platform.fragment.my.store.action.FindUserGoodsOwnerBean;
import com.jhhscm.platform.fragment.my.store.viewholder.StoreOrderProductItemViewHolder;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.sign.SignObject;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.recyclerview.DividerItemDecoration;
import com.jhhscm.platform.views.recyclerview.WrappedRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

public class StoreOrderSubmit2Fragment extends AbsFragment<FragmentStoreOrderSubmit2Binding> {
    private InnerAdapter mAdapter;

    private int mShowCount = 10;
    private int mCurrentPage = 1;
    private final int START_PAGE = mCurrentPage;

    private String goodsOwnerV1s = "";//机器编号
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


        mDataBinding.tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getPushListBean != null && getPushListBean.getData() != null
                        && getPushListBean.getData().size() > 0) {
                    BusinessFindcategorybyBuscodeBean businessFindcategorybyBuscodeBean = new BusinessFindcategorybyBuscodeBean();
                    List<BusinessFindcategorybyBuscodeBean.DataBean> list = new ArrayList<>();
                    for (BusinessFindcategorybyBuscodeBean.DataBean dataBean : getPushListBean.getData()) {
                        if (dataBean.isSelect()) {
                            list.add(dataBean);
                        }
                    }
                    if (list.size() > 0) {
                        businessFindcategorybyBuscodeBean.setData(list);
                        StoreOrderSubmit3Activity.start(getContext(), dataBean, businessFindcategorybyBuscodeBean, name, phone);

                    } else {
                        ToastUtil.show(getContext(), "请选择配件");
                    }
                } else {
                    ToastUtil.show(getContext(), "请选择配件");
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
}
