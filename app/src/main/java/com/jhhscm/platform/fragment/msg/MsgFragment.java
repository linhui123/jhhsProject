package com.jhhscm.platform.fragment.msg;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.FragmentMechanicsBinding;
import com.jhhscm.platform.databinding.FragmentMsgBinding;
import com.jhhscm.platform.event.GetRegionEvent;
import com.jhhscm.platform.fragment.Mechanics.MechanicsFragment;
import com.jhhscm.platform.fragment.Mechanics.NewMechanicsFragment;
import com.jhhscm.platform.fragment.Mechanics.OldMechanicsFragment;
import com.jhhscm.platform.fragment.Mechanics.action.GetRegionAction;
import com.jhhscm.platform.fragment.Mechanics.bean.GetRegionBean;
import com.jhhscm.platform.fragment.Mechanics.holder.GetRegionViewHolder;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.labour.FindLabourReleaseListAction;
import com.jhhscm.platform.fragment.labour.FindLabourReleaseListBean;
import com.jhhscm.platform.fragment.labour.LabourViewHolder;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.http.sign.SignObject;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.DisplayUtils;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.recyclerview.DividerItemStrokeDecoration;
import com.jhhscm.platform.views.recyclerview.WrappedRecyclerView;

import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

public class MsgFragment extends AbsFragment<FragmentMsgBinding> {
    private int type = 0;
    private InnerAdapter aAdapter;

    private int mShowCount = 10;
    private int mCurrentPage = 1;
    private final int START_PAGE = mCurrentPage;

    public static MsgFragment instance() {
        MsgFragment view = new MsgFragment();
        return view;
    }

    @Override
    protected FragmentMsgBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentMsgBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        RelativeLayout.LayoutParams llParams = (RelativeLayout.LayoutParams) mDataBinding.rlTop.getLayoutParams();
        llParams.topMargin += DisplayUtils.getStatusBarHeight(getContext());
        mDataBinding.rlTop.setLayoutParams(llParams);

        mDataBinding.tvNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 0;
                mDataBinding.rlActivity.autoRefresh();
                mDataBinding.tvNew.setBackgroundResource(R.color.white);
                mDataBinding.tvNew.setTextColor(getResources().getColor(R.color.a397));
                mDataBinding.tvOld.setTextColor(getResources().getColor(R.color.white));
                mDataBinding.tvOld.setBackgroundResource(R.drawable.bg_397_right_ovel);
            }
        });

        mDataBinding.tvOld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 1;
                mDataBinding.rlActivity.autoRefresh();
                mDataBinding.tvOld.setBackgroundResource(R.color.white);
                mDataBinding.tvOld.setTextColor(getResources().getColor(R.color.a397));
                mDataBinding.tvNew.setBackgroundResource(R.drawable.bg_397_left_ovel);
                mDataBinding.tvNew.setTextColor(getResources().getColor(R.color.white));
            }
        });

        mDataBinding.imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        initRv();
    }

    private void initRv() {
        mDataBinding.rlActivity.addItemDecoration(new DividerItemStrokeDecoration(getContext()));
        mDataBinding.rlActivity.setLayoutManager(new LinearLayoutManager(getContext()));
        aAdapter = new InnerAdapter(getContext());
        mDataBinding.rlActivity.setAdapter(aAdapter);
        mDataBinding.rlActivity.autoRefresh();
        mDataBinding.rlActivity.setOnPullListener(new WrappedRecyclerView.OnPullListener() {
            @Override
            public void onRefresh(RecyclerView view) {
                getPushList(true, type);
            }

            @Override
            public void onLoadMore(RecyclerView view) {
                getPushList(false, type);
            }
        });
    }

    GetPushListBean getPushListBean;

    /**
     * 消息列表
     */
    private void getPushList(final boolean refresh, final int type) {
        if (getContext() != null) {
            mCurrentPage = refresh ? START_PAGE : ++mCurrentPage;
            Map<String, Object> map = new TreeMap<String, Object>();
            map.put("msg_type", type);
            map.put("page", mCurrentPage);
            map.put("limit", mShowCount);
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = SignObject.getSignKey(getActivity(), map, "getPushList");
            NetBean netBean = new NetBean();
            netBean.setToken("");
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(GetPushListAction.newInstance(getContext(), netBean)
                    .request(new AHttpService.IResCallback<BaseEntity<GetPushListBean>>() {
                        @Override
                        public void onCallback(int resultCode, Response<BaseEntity<GetPushListBean>> response,
                                               BaseErrorInfo baseErrorInfo) {
                            if (getView() != null) {
                                closeDialog();
                                if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                    return;
                                }
                                if (response != null) {
                                    new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                    if (response.body().getCode().equals("200")) {
                                        getPushListBean = response.body().getData();
                                        if (getPushListBean != null) {
                                            for (GetPushListBean.DataBean dataBean : getPushListBean.getData()) {
                                                dataBean.setType(type);
                                            }
                                        }
                                        initView(getPushListBean, refresh);
                                    } else {
                                        ToastUtils.show(getContext(), response.body().getMessage());
                                    }
                                }
                            }
                        }
                    }));
        }
    }

    private void initView(GetPushListBean pushListBean, boolean refresh) {
        this.getPushListBean = pushListBean;
        if (refresh) {
            aAdapter.setData(pushListBean.getData());
        } else {
            aAdapter.append(pushListBean.getData());
        }
        mDataBinding.rlActivity.loadComplete(aAdapter.getItemCount() == 0, ((float) getPushListBean.getPage().getTotal() / (float) getPushListBean.getPage().getPageSize()) > mCurrentPage);
    }

    private class InnerAdapter extends AbsRecyclerViewAdapter<GetPushListBean.DataBean> {
        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<GetPushListBean.DataBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MsgViewHolder(mInflater.inflate(R.layout.item_msg, parent, false));
        }
    }
}
