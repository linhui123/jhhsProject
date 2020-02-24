package com.jhhscm.platform.fragment.coupon;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.LoginActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.FragmentSelectCouponBinding;
import com.jhhscm.platform.event.SelectCouponEvent;
import com.jhhscm.platform.fragment.Mechanics.bean.GetComboBoxBean;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.my.store.action.PayUseListAction;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.recyclerview.WrappedRecyclerView;

import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectCouponFragment extends AbsFragment<FragmentSelectCouponBinding> {
    private InnerAdapter mAdapter;
    private int mShowCount = 10;
    private int mCurrentPage = 1;
    private final int START_PAGE = mCurrentPage;
    private String coupon_code;
    private String order_code;
    private CouponListBean couponListBean;
    public static SelectCouponFragment instance() {
        SelectCouponFragment view = new SelectCouponFragment();
        return view;
    }

    @Override
    protected FragmentSelectCouponBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentSelectCouponBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        EventBusUtil.registerEvent(this);
        order_code = getArguments().getString("order_code");
        coupon_code = getArguments().getString("coupon_code");
        couponListBean = (CouponListBean) getArguments().getSerializable("list");
        if (order_code != null) {
            mDataBinding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
            mAdapter = new InnerAdapter(getContext());
            mDataBinding.recyclerview.setAdapter(mAdapter);
            mDataBinding.recyclerview.autoRefresh();
            mDataBinding.recyclerview.setOnPullListener(new WrappedRecyclerView.OnPullListener() {
                @Override
                public void onRefresh(RecyclerView view) {
                    getCouponList(true);
                }

                @Override
                public void onLoadMore(RecyclerView view) {
                    getCouponList(false);
                }
            });
        } else if (couponListBean != null && couponListBean.getResult() != null && couponListBean.getResult().size() > 0) {
            mDataBinding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
            mAdapter = new InnerAdapter(getContext());
            mDataBinding.recyclerview.setAdapter(mAdapter);
            if (coupon_code != null && coupon_code.length() > 0) {
                for (CouponListBean.ResultBean resultBean : couponListBean.getResult()) {
                    if (resultBean.getCoupon_code().equals(coupon_code)) {
                        resultBean.setSelect(true);
                    }
                }
            }
            mAdapter.setData(couponListBean.getResult());
            mDataBinding.recyclerview.loadComplete(true,false);
        } else {
            ToastUtil.show(getContext(), "数据错误");
            getActivity().finish();
        }

        mDataBinding.tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (CouponListBean.ResultBean resultBean : couponListBean.getResult()) {
                    if (resultBean.isSelect()) {
                        EventBusUtil.post(new SelectCouponEvent(resultBean, 1));
                        getActivity().finish();
                        return;
                    }
                }
                EventBusUtil.post(new SelectCouponEvent(new CouponListBean.ResultBean(), 1));
                getActivity().finish();
            }
        });
    }


    /**
     * 获取可用优惠券列表
     */
    private void getCouponList(final boolean isFinsh) {
        showDialog();
        Map<String, String> map = new TreeMap<String, String>();
        map.put("user_code", ConfigUtils.getCurrentUser(getContext()).getUserCode());
        map.put("order_code", order_code);
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getActivity(), map, "payUseList");
        NetBean netBean = new NetBean();
        netBean.setToken(ConfigUtils.getCurrentUser(getContext()).getToken());
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(PayUseListAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<CouponListBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<CouponListBean>> response, BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                if (response.body().getCode().equals("200")) {
                                    doSuccessResponse(response.body().getData(), isFinsh);
                                } else if (response.body().getCode().equals("1003")) {
                                    ToastUtils.show(getContext(), "登录信息过期，请重新登录");
                                    startNewActivity(LoginActivity.class);
                                } else {
                                    ToastUtils.show(getContext(), "error :" + response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
    }

    private void doSuccessResponse(final CouponListBean couponListBean, boolean refresh) {
        if (coupon_code != null && coupon_code.length() > 0) {
            for (CouponListBean.ResultBean resultBean : couponListBean.getResult()) {
                if (resultBean.getCoupon_code().equals(coupon_code)) {
                    resultBean.setSelect(true);
                }
            }
        }
        this.couponListBean = couponListBean;
        if (refresh) {
            mAdapter.setData(couponListBean.getResult());
        } else {
            mAdapter.append(couponListBean.getResult());
        }
        mDataBinding.recyclerview.loadComplete(true, false);
        mDataBinding.recyclerview.hideLoad();
    }

    public void onEvent(SelectCouponEvent event) {
        if (event.getResultBean() != null && event.getType() == 0) {
            coupon_code = event.getResultBean().getCoupon_code();
            for (CouponListBean.ResultBean resultBean : couponListBean.getResult()) {
                if (resultBean.getCoupon_code().equals(coupon_code)) {
                    resultBean.setSelect(true);
                } else {
                    resultBean.setSelect(false);
                }
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }

    private class InnerAdapter extends AbsRecyclerViewAdapter<CouponListBean.ResultBean> {
        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<CouponListBean.ResultBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new SelectCouponViewHolder(mInflater.inflate(R.layout.item_select_coupon, parent, false));
        }
    }
}
