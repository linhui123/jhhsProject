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
//        mDataBinding.recyclerview.addItemDecoration(new DividerItemDecoration(getContext()));
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
    }

    private void getCouponList(final boolean refresh) {
        if (getContext() != null) {
            mCurrentPage = refresh ? START_PAGE : ++mCurrentPage;
            Map<String, Object> map = new TreeMap<String, Object>();
            map.put("page", mCurrentPage);
            map.put("limit", mShowCount);
            map.put("article_type_list", 1);
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = SignObject.getSignKey(getActivity(), map, "getArticleList");
            NetBean netBean = new NetBean();
            netBean.setToken("");
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(GetArticleListAction.newInstance(getContext(), netBean)
                    .request(new AHttpService.IResCallback<BaseEntity<GetPageArticleListBean>>() {
                        @Override
                        public void onCallback(int resultCode, Response<BaseEntity<GetPageArticleListBean>> response,
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

    GetPageArticleListBean getPushListBean;

    private void initView(boolean refresh, GetPageArticleListBean pushListBean) {

        this.getPushListBean = pushListBean;
        if (refresh) {
            mAdapter.setData(pushListBean.getData());
        } else {
            mAdapter.append(pushListBean.getData());
        }
        mDataBinding.recyclerview.loadComplete(mAdapter.getItemCount() == 0,
                ((float) getPushListBean.getPage().getTotal() / (float) getPushListBean.getPage().getPageSize()) > mCurrentPage);
    }

    private class InnerAdapter extends AbsRecyclerViewAdapter<GetPageArticleListBean.DataBean> {
        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<GetPageArticleListBean.DataBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CouponCenterViewHolder(mInflater.inflate(R.layout.item_coupon_center, parent, false));
        }
    }
}
