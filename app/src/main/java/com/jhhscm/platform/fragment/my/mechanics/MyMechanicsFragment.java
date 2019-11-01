package com.jhhscm.platform.fragment.my.mechanics;


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
import com.jhhscm.platform.activity.AddDeviceActivity;
import com.jhhscm.platform.activity.LoginActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.FragmentMechanicsBinding;
import com.jhhscm.platform.databinding.FragmentMyMechanicsBinding;
import com.jhhscm.platform.event.GetRegionEvent;
import com.jhhscm.platform.fragment.Mechanics.MechanicsFragment;
import com.jhhscm.platform.fragment.Mechanics.NewMechanicsFragment;
import com.jhhscm.platform.fragment.Mechanics.OldMechanicsFragment;
import com.jhhscm.platform.fragment.Mechanics.action.GetGoodsPageListAction;
import com.jhhscm.platform.fragment.Mechanics.action.GetRegionAction;
import com.jhhscm.platform.fragment.Mechanics.bean.GetGoodsPageListBean;
import com.jhhscm.platform.fragment.Mechanics.bean.GetRegionBean;
import com.jhhscm.platform.fragment.Mechanics.holder.GetRegionViewHolder;
import com.jhhscm.platform.fragment.Mechanics.holder.NewMechanicsViewHolder;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.UserSession;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.DisplayUtils;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.recyclerview.DividerItemStrokeDecoration;
import com.jhhscm.platform.views.recyclerview.WrappedRecyclerView;

import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;


public class MyMechanicsFragment extends AbsFragment<FragmentMyMechanicsBinding> {
    private InnerAdapter mAdapter;
    private int mShowCount = 10;
    private int mCurrentPage = 1;
    private final int START_PAGE = mCurrentPage;
    private UserSession userSession;


    public static MyMechanicsFragment instance() {
        MyMechanicsFragment view = new MyMechanicsFragment();
        return view;
    }

    @Override
    protected FragmentMyMechanicsBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentMyMechanicsBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        if (ConfigUtils.getCurrentUser(getContext()) != null
                && ConfigUtils.getCurrentUser(getContext()).getMobile() != null) {
            userSession = ConfigUtils.getCurrentUser(getContext());
        } else {
            startNewActivity(LoginActivity.class);
        }
//        mDataBinding.wrvRecycler.addItemDecoration(new DividerItemStrokeDecoration(getContext()));
        mDataBinding.wrvRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new InnerAdapter(getContext());
        mDataBinding.wrvRecycler.setAdapter(mAdapter);
        mDataBinding.wrvRecycler.autoRefresh();
        mDataBinding.wrvRecycler.setOnPullListener(new WrappedRecyclerView.OnPullListener() {
            @Override
            public void onRefresh(RecyclerView view) {
                findOldGoodByUserCode(true);
            }

            @Override
            public void onLoadMore(RecyclerView view) {
                findOldGoodByUserCode(false);
            }
        });

        mDataBinding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDeviceActivity.start(getContext());
            }
        });
    }


    /**
     * 获取新机列表
     */
    private void findOldGoodByUserCode(final boolean refresh) {
        if (getContext() != null) {
            mCurrentPage = refresh ? START_PAGE : ++mCurrentPage;
            Map<String, String> map = new TreeMap<String, String>();
            map.put("user_code", userSession.getUserCode());
            map.put("page", mCurrentPage + "");
            map.put("limit", mShowCount + "");
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = Sign.getSignKey(getActivity(), map, "findOldGoodByUserCode");
            NetBean netBean = new NetBean();
            netBean.setToken(userSession.getToken());
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(FindOldGoodByUserCodeAction.newInstance(getContext(), netBean)
                    .request(new AHttpService.IResCallback<BaseEntity<FindOldGoodByUserCodeBean>>() {
                        @Override
                        public void onCallback(int resultCode, Response<BaseEntity<FindOldGoodByUserCodeBean>> response,
                                               BaseErrorInfo baseErrorInfo) {
                            if (getView() != null) {
                                closeDialog();
                                if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                    return;
                                }
                                if (response != null) {
                                    new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                    if (response.body().getCode().equals("200")) {
                                        doSuccessResponse(refresh, response.body().getData());
                                    } else {
                                        ToastUtils.show(getContext(), response.body().getMessage());
                                    }
                                }
                            }
                        }
                    }));
        }
    }

    FindOldGoodByUserCodeBean findOldGoodByUserCodeBean;

    private void doSuccessResponse(boolean refresh, FindOldGoodByUserCodeBean getGoodsPageList) {
        this.findOldGoodByUserCodeBean = getGoodsPageList;
        if (refresh) {
            mAdapter.setData(getGoodsPageList.getData());
        } else {
            mAdapter.append(getGoodsPageList.getData());
        }
        mDataBinding.wrvRecycler.getAdapter().notifyDataSetChanged();
//        if (mAdapter.getItemCount()>0) {
        mDataBinding.rlCaseBaseNull.setVisibility(View.GONE);
        mDataBinding.wrvRecycler.loadComplete(mAdapter.getItemCount() == 0, ((float) findOldGoodByUserCodeBean.getPage().getTotal() / (float) findOldGoodByUserCodeBean.getPage().getPageSize()) > mCurrentPage);
//        } else {
//            mDataBinding.wrvRecycler.loadComplete(true, false);
//            mDataBinding.rlCaseBaseNull.setVisibility(View.VISIBLE);
//        }
    }

    private class InnerAdapter extends AbsRecyclerViewAdapter<FindOldGoodByUserCodeBean.DataBean> {
        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<FindOldGoodByUserCodeBean.DataBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyMechanicsViewHolder(mInflater.inflate(R.layout.item_device, parent, false));
        }
    }

}
