package com.jhhscm.platform.fragment.financing;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.BuildConfig;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.LoginActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.FragmentFinancingProgressBinding;
import com.jhhscm.platform.databinding.FragmentRepaymentBinding;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.my.mechanics.FindGoodsOwnerAction;
import com.jhhscm.platform.fragment.my.mechanics.FindGoodsOwnerBean;
import com.jhhscm.platform.fragment.repayment.ContractListAction;
import com.jhhscm.platform.fragment.repayment.ContractListBean;
import com.jhhscm.platform.fragment.repayment.RepaymentFragment;
import com.jhhscm.platform.fragment.repayment.RepaymentViewHolder;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.http.sign.SignObject;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.recyclerview.WrappedRecyclerView;

import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

/**
 * 融资申请进度查询
 */
public class FinancingProgressFragment extends AbsFragment<FragmentFinancingProgressBinding> {
    private InnerAdapter mAdapter;
    private int mShowCount = 10;
    private int mCurrentPage = 1;
    private final int START_PAGE = mCurrentPage;

    public static FinancingProgressFragment instance() {
        FinancingProgressFragment view = new FinancingProgressFragment();
        return view;
    }

    @Override
    protected FragmentFinancingProgressBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentFinancingProgressBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        if (ConfigUtils.getCurrentUser(getContext()) != null
                && ConfigUtils.getCurrentUser(getContext()).getMobile() != null
                && ConfigUtils.getCurrentUser(getContext()).getToken() != null) {
            mDataBinding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
            mAdapter = new InnerAdapter(getContext());
            mDataBinding.recyclerview.setAdapter(mAdapter);
            mDataBinding.recyclerview.autoRefresh();
            mDataBinding.recyclerview.setOnPullListener(new WrappedRecyclerView.OnPullListener() {
                @Override
                public void onRefresh(RecyclerView view) {
                    findGoodsOwner(true);
                }

                @Override
                public void onLoadMore(RecyclerView view) {
                    findGoodsOwner(false);
                }
            });
        } else {
            getActivity().finish();
            LoginActivity.start(getActivity());
        }
    }

    /**
     * 个人中心我的设备列表
     */
    private void findGoodsOwner(final boolean refresh) {
        if (getContext() != null) {
            mCurrentPage = refresh ? START_PAGE : ++mCurrentPage;
            Map<String, String> map = new TreeMap<String, String>();
            map.put("user_code", ConfigUtils.getCurrentUser(getContext()).getUserCode());
            map.put("page", mCurrentPage + "");
            map.put("limit", mShowCount + "");
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = Sign.getSignKey(getActivity(), map, "findOldGoodByUserCode");
            NetBean netBean = new NetBean();
            netBean.setToken(ConfigUtils.getCurrentUser(getContext()).getToken());
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(LeaseSelAction.newInstance(getContext(), netBean)
                    .request(new AHttpService.IResCallback<BaseEntity<LeaseSelBean>>() {
                        @Override
                        public void onCallback(int resultCode, Response<BaseEntity<LeaseSelBean>> response,
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
                                    } else if (!BuildConfig.DEBUG && response.body().getCode().equals("1006")) {
                                        ToastUtils.show(getContext(), "网络错误");
                                    } else {
                                        ToastUtils.show(getContext(), response.body().getMessage());
                                    }
                                }
                            }
                        }
                    }));
        }
    }

    LeaseSelBean findOldGoodByUserCodeBean;

    private void doSuccessResponse(boolean refresh, LeaseSelBean getGoodsPageList) {
        this.findOldGoodByUserCodeBean = getGoodsPageList;
        if (refresh) {
            mAdapter.setData(getGoodsPageList.getResult());
        } else {
            mAdapter.append(getGoodsPageList.getResult());
        }
        mDataBinding.recyclerview.getAdapter().notifyDataSetChanged();
        mDataBinding.recyclerview.loadComplete(true,false);
//        mDataBinding.recyclerview.loadComplete(mAdapter.getItemCount() == 0, ((float) findOldGoodByUserCodeBean.getPage().getTotal() / (float) findOldGoodByUserCodeBean.getPage().getPageSize()) > mCurrentPage);
    }

    private class InnerAdapter extends AbsRecyclerViewAdapter<LeaseSelBean.ResultBean> {

        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<LeaseSelBean.ResultBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new FinancingProgressViewHolder(mInflater.inflate(R.layout.item_financ_progress, parent, false));
        }
    }
}
