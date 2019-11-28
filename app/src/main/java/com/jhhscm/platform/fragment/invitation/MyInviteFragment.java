package com.jhhscm.platform.fragment.invitation;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.InvitationRegisterActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.FragmentMyBinding;
import com.jhhscm.platform.databinding.FragmentMyInviteBinding;
import com.jhhscm.platform.fragment.GoodsToCarts.GetCartGoodsByUserCodeBean;
import com.jhhscm.platform.fragment.aftersale.AfterSaleFragment;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.coupon.CouponCenterViewHolder;
import com.jhhscm.platform.fragment.coupon.MyCouponListAdapter;
import com.jhhscm.platform.fragment.home.action.GetArticleListAction;
import com.jhhscm.platform.fragment.home.bean.GetPageArticleListBean;
import com.jhhscm.platform.fragment.my.MyFragment;
import com.jhhscm.platform.fragment.my.order.FindOrderListBean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.UserSession;
import com.jhhscm.platform.http.sign.SignObject;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.recyclerview.DividerItemDecoration;
import com.jhhscm.platform.views.recyclerview.WrappedRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;


public class MyInviteFragment extends AbsFragment<FragmentMyInviteBinding> {
    private UserSession userSession;
    private InnerAdapter mAdapter;
    private int mShowCount = 10;
    private int mCurrentPage = 1;
    private final int START_PAGE = mCurrentPage;

    public static MyInviteFragment instance() {
        MyInviteFragment view = new MyInviteFragment();
        return view;
    }

    @Override
    protected FragmentMyInviteBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentMyInviteBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        mDataBinding.recyclerview.addItemDecoration(new DividerItemDecoration(getContext()));
        mDataBinding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new InnerAdapter(getContext());
        mDataBinding.recyclerview.setAdapter(mAdapter);
        mDataBinding.recyclerview.autoRefresh();
        mDataBinding.recyclerview.setOnPullListener(new WrappedRecyclerView.OnPullListener() {
            @Override
            public void onRefresh(RecyclerView view) {
                getReqList(true);
            }

            @Override
            public void onLoadMore(RecyclerView view) {
                getReqList(false);
            }
        });

        mDataBinding.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (mDataBinding.searchContent.getText().toString().length() > 0) {
                    mDataBinding.recyclerview.autoRefresh();
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
//                } else {
//                    ToastUtil.show(getContext(), "输入内容不能为空");
//                }
            }
        });

        mDataBinding.invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //邀请注册
                InvitationRegisterActivity.start(getContext());
            }
        });
    }

    private void getReqList(final boolean refresh) {
        if (getContext() != null) {
            mCurrentPage = refresh ? START_PAGE : ++mCurrentPage;
            Map<String, Object> map = new TreeMap<String, Object>();
            map.put("page", mCurrentPage);
            map.put("limit", mShowCount);
            map.put("user_code", ConfigUtils.getCurrentUser(getContext()).getUserCode());
            map.put("keyword", mDataBinding.searchContent.getText().toString().trim());
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = SignObject.getSignKey(getActivity(), map, "getReqList");
            NetBean netBean = new NetBean();
            netBean.setToken("");
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(ReqListAction.newInstance(getContext(), netBean)
                    .request(new AHttpService.IResCallback<BaseEntity<ReqListBean>>() {
                        @Override
                        public void onCallback(int resultCode, Response<BaseEntity<ReqListBean>> response,
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

    ReqListBean getPushListBean;

    private void initView(boolean refresh, ReqListBean pushListBean) {

        this.getPushListBean = pushListBean;
        if (refresh) {
            mAdapter.setData(pushListBean.getResult().getData());
        } else {
            mAdapter.append(pushListBean.getResult().getData());
        }
        mDataBinding.recyclerview.loadComplete(mAdapter.getItemCount() == 0,
                ((float) getPushListBean.getResult().getPage().getTotal() / (float) getPushListBean.getResult().getPage().getPageSize()) > mCurrentPage);
    }


    private class InnerAdapter extends AbsRecyclerViewAdapter<ReqListBean.ResultBean.DataBean> {
        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<ReqListBean.ResultBean.DataBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new InviteItemViewHolder(mInflater.inflate(R.layout.item_invite, parent, false));
        }
    }
}
