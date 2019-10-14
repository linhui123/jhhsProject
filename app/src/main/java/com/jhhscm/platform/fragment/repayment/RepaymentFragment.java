package com.jhhscm.platform.fragment.repayment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
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
import com.jhhscm.platform.fragment.vehicle.GpsDetailAction;
import com.jhhscm.platform.fragment.vehicle.GpsDetailBean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.sign.SignObject;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.recyclerview.DividerItemStrokeDecoration;
import com.jhhscm.platform.views.recyclerview.WrappedRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

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
                contractList(true);
            }

            @Override
            public void onLoadMore(RecyclerView view) {
                contractList(false);
            }
        });
    }

    /**
     * 合同列表 15927112992
     */
    private void contractList(final boolean refresh) {
        Map<String, Object> map = new TreeMap<String, Object>();
        map.put("phone", "15927112992");
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = SignObject.getSignKey(getActivity(), map, "contractList");
        NetBean netBean = new NetBean();
        netBean.setToken("");
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(ContractListAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<ContractListBean>>() {

                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<ContractListBean>> response, BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                if (response.body().getCode().equals("200")) {
                                    initView(response.body().getData(), refresh);
                                } else if (response.body().getCode().equals("1001")) {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                } else {
                                    ToastUtils.show(getContext(), "网络异常");
                                }
                            }
                        }
                    }
                }));
    }

    private void initView(ContractListBean beanList, boolean refresh) {
        if (refresh) {
            mAdapter.setData(beanList.getData());
            mDataBinding.recyclerview.loadComplete(true, true);
        } else {
            mAdapter.append(beanList.getData());
            mDataBinding.recyclerview.loadComplete(true, false);
        }

    }

    private class InnerAdapter extends AbsRecyclerViewAdapter<ContractListBean.DataBean> {

        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<ContractListBean.DataBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new RepaymentViewHolder(mInflater.inflate(R.layout.item_repayment, parent, false));
        }
    }
}
