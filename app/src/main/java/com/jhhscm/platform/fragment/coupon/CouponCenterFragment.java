package com.jhhscm.platform.fragment.coupon;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
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
import com.jhhscm.platform.databinding.FragmentCaseBaseBinding;
import com.jhhscm.platform.databinding.FragmentCouponCenterBinding;
import com.jhhscm.platform.databinding.FragmentLabourBinding;
import com.jhhscm.platform.databinding.FragmentMyCouponListBinding;
import com.jhhscm.platform.databinding.FragmentMyMemberBinding;
import com.jhhscm.platform.event.AddressResultEvent;
import com.jhhscm.platform.event.GetCouponEvent;
import com.jhhscm.platform.event.OrderCancleEvent;
import com.jhhscm.platform.fragment.GoodsToCarts.CreateOrderFragment;
import com.jhhscm.platform.fragment.GoodsToCarts.CreateOrderViewHolder;
import com.jhhscm.platform.fragment.GoodsToCarts.GetCartGoodsByUserCodeBean;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.home.action.GetArticleListAction;
import com.jhhscm.platform.fragment.home.bean.GetPageArticleListBean;
import com.jhhscm.platform.fragment.labour.LabourFragment;
import com.jhhscm.platform.fragment.msg.NewsListFragment;
import com.jhhscm.platform.fragment.my.order.DelOrderAction;
import com.jhhscm.platform.fragment.my.order.FindOrderListAction;
import com.jhhscm.platform.fragment.my.order.FindOrderListBean;
import com.jhhscm.platform.fragment.my.store.MyMemberFragment;
import com.jhhscm.platform.fragment.my.store.viewholder.MyMemberItemViewHolder;
import com.jhhscm.platform.fragment.sale.FindOrderAction;
import com.jhhscm.platform.fragment.sale.FindOrderBean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.ResultBean;
import com.jhhscm.platform.http.bean.UserSession;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.http.sign.SignObject;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.recyclerview.DividerItemDecoration;
import com.jhhscm.platform.views.recyclerview.DividerItemStrokeDecoration;
import com.jhhscm.platform.views.recyclerview.WrappedRecyclerView;

import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

/**
 * 领券中心
 */
public class CouponCenterFragment extends AbsFragment<FragmentCouponCenterBinding> {

    private InnerAdapter mAdapter;
    private int mShowCount = 10;
    private int mCurrentPage = 1;
    private final int START_PAGE = mCurrentPage;

    public static CouponCenterFragment instance() {
        CouponCenterFragment view = new CouponCenterFragment();
        return view;
    }

    @Override
    protected FragmentCouponCenterBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentCouponCenterBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        EventBusUtil.registerEvent(this);
//        mDataBinding.recyclerview.addItemDecoration(new DividerItemDecoration(getContext()));
        mDataBinding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new InnerAdapter(getContext());
        mDataBinding.recyclerview.setAdapter(mAdapter);
        mDataBinding.recyclerview.autoRefresh();
        mDataBinding.recyclerview.setOnPullListener(new WrappedRecyclerView.OnPullListener() {
            @Override
            public void onRefresh(RecyclerView view) {
                getCouponGetList(true);
            }

            @Override
            public void onLoadMore(RecyclerView view) {
                getCouponGetList(false);
            }
        });
    }

    public void onEvent(GetCouponEvent event) {
        if (event.coupon_code != null && event.start != null && event.end != null) {
            getCoupon(event.coupon_code, event.start, event.end);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }

    /**
     * 获取领券列表
     */
    private void getCouponGetList(final boolean refresh) {
        if (getContext() != null) {
            mCurrentPage = refresh ? START_PAGE : ++mCurrentPage;
            Map<String, Object> map = new TreeMap<String, Object>();
            map.put("page", mCurrentPage);
            map.put("limit", mShowCount);
            map.put("user_code", ConfigUtils.getCurrentUser(getContext()).getUserCode());
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = SignObject.getSignKey(getActivity(), map, "getCouponGetList");
            NetBean netBean = new NetBean();
            netBean.setToken(ConfigUtils.getCurrentUser(getContext()).getToken());
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(CouponGetListAction.newInstance(getContext(), netBean)
                    .request(new AHttpService.IResCallback<BaseEntity<CouponGetListBean>>() {
                        @Override
                        public void onCallback(int resultCode, Response<BaseEntity<CouponGetListBean>> response,
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

    CouponGetListBean getPushListBean;

    private void initView(boolean refresh, CouponGetListBean pushListBean) {

        this.getPushListBean = pushListBean;
        if (refresh) {
            mAdapter.setData(pushListBean.getData());
        } else {
            mAdapter.append(pushListBean.getData());
        }
        mDataBinding.recyclerview.loadComplete(false, false);
//        mDataBinding.recyclerview.loadComplete(mAdapter.getItemCount() == 0,
//                ((float) getPushListBean.getPage().getTotal() / (float) getPushListBean.getPage().getPageSize()) > mCurrentPage);
    }

    /**
     * 领取优惠卷
     */
    private void getCoupon(String coupon_code, String start, String end) {
        if (getContext() != null) {
            Map<String, Object> map = new TreeMap<String, Object>();
            map.put("coupon_code", coupon_code);
            map.put("user_code", ConfigUtils.getCurrentUser(getContext()).getUserCode());
            map.put("coupon_b_time", start);
            map.put("coupon_e_time", end);
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = SignObject.getSignKey(getActivity(), map, "getCoupon");
            NetBean netBean = new NetBean();
            netBean.setToken(ConfigUtils.getCurrentUser(getContext()).getToken());
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(GetCouponAction.newInstance(getContext(), netBean)
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
                                        if ("0".equals(response.body().getData().getResult())) {
                                            ToastUtil.show(getContext(), "领取成功");
                                        } else if ("2".equals(response.body().getData().getResult())) {
                                            ToastUtil.show(getContext(), "该券已领取完");
                                        } else {
                                            ToastUtil.show(getContext(), "领取失败");
                                        }
                                        mDataBinding.recyclerview.autoRefresh();
                                    } else {
                                        ToastUtils.show(getContext(), response.body().getMessage());
                                    }
                                }
                            }
                        }
                    }));
        }
    }

    private class InnerAdapter extends AbsRecyclerViewAdapter<CouponGetListBean.DataBean> {
        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<CouponGetListBean.DataBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CouponCenterViewHolder(mInflater.inflate(R.layout.item_coupon_center, parent, false));
        }
    }
}
