package com.jhhscm.platform.fragment.Mechanics;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.BrandActivity;
import com.jhhscm.platform.activity.CreateOrderActivity;
import com.jhhscm.platform.activity.LoginActivity;
import com.jhhscm.platform.activity.MechanicsByBrandActivity;
import com.jhhscm.platform.activity.MyPeiJianListActivity;
import com.jhhscm.platform.activity.ReceiveAddressActivity;
import com.jhhscm.platform.activity.SettingActivity;
import com.jhhscm.platform.databinding.FragmentComparisonBinding;
import com.jhhscm.platform.databinding.FragmentMyBinding;
import com.jhhscm.platform.event.CompMechanicsEvent;
import com.jhhscm.platform.event.GetRegionEvent;
import com.jhhscm.platform.fragment.GoodsToCarts.GetCartGoodsByUserCodeBean;
import com.jhhscm.platform.fragment.GoodsToCarts.adapter.RecOtherTypeAdapter;
import com.jhhscm.platform.fragment.Mechanics.adapter.CompairsonAdapter;
import com.jhhscm.platform.fragment.Mechanics.bean.GetGoodsByBrandBean;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.my.MyFragment;
import com.jhhscm.platform.http.bean.UserSession;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.DisplayUtils;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.views.slideswaphelper.PlusItemSlideCallback;
import com.jhhscm.platform.views.slideswaphelper.WItemTouchHelperPlus;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class ComparisonFragment extends AbsFragment<FragmentComparisonBinding> implements CompairsonAdapter.DeletedItemListener, CompairsonAdapter.SelectedListener {
    private CompairsonAdapter compairsonAdapter;
    private CompairsonAdapter wAdapter;
    List<GetCartGoodsByUserCodeBean.ResultBean> list;
    private boolean total;
    private UserSession userSession;

    public static ComparisonFragment instance() {
        ComparisonFragment view = new ComparisonFragment();
        return view;
    }

    @Override
    protected FragmentComparisonBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentComparisonBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        EventBusUtil.registerEvent(this);

        mDataBinding.refreshlayout.setEnableRefresh(true);
        mDataBinding.refreshlayout.setEnableLoadMore(false);
        mDataBinding.refreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                getCartGoodsByUserCode(userSession.getUserCode(), userSession.getToken(), true);
            }
        });

        mDataBinding.refreshlayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//                getCartGoodsByUserCode(userSession.getUserCode(), userSession.getToken(), false);

            }
        });
        initView();
        initBottom();
    }

    private void initBottom() {
        mDataBinding.tvCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (total) {

                }
            }
        });

        mDataBinding.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrandActivity.start(getContext());
            }
        });
    }

    List<GetGoodsByBrandBean.ResultBean> getCartGoodsByUserCodeBean;

    private void initData(List<GetGoodsByBrandBean.ResultBean> resultBeans, boolean refresh) {
        compairsonAdapter.setList(resultBeans, refresh);
        if (refresh) {
            getCartGoodsByUserCodeBean = resultBeans;
            mDataBinding.refreshlayout.finishRefresh(1000);
        } else {
            getCartGoodsByUserCodeBean.addAll(resultBeans);
            mDataBinding.refreshlayout.finishLoadMore(1000);
        }
    }

    private void initView() {
        mDataBinding.rvSelect.setLayoutManager(new LinearLayoutManager(getActivity()));
        compairsonAdapter = new CompairsonAdapter(getContext());
        compairsonAdapter.setDeletedItemListener(this);
        compairsonAdapter.setSelectedListener(this);
        mDataBinding.rvSelect.setAdapter(compairsonAdapter);

        PlusItemSlideCallback callback = new PlusItemSlideCallback();
        WItemTouchHelperPlus extension = new WItemTouchHelperPlus(callback);
        extension.attachToRecyclerView(mDataBinding.rvSelect);

        mDataBinding.rvWatch.setLayoutManager(new LinearLayoutManager(getActivity()));
        wAdapter = new CompairsonAdapter(getContext());
        wAdapter.setDeletedItemListener(new CompairsonAdapter.DeletedItemListener() {
            @Override
            public void deleted(GetGoodsByBrandBean.ResultBean resultBean) {

            }
        });
        wAdapter.setSelectedListener(new CompairsonAdapter.SelectedListener() {
            @Override
            public void select(GetGoodsByBrandBean.ResultBean resultBean) {

            }
        });
        mDataBinding.rvWatch.setAdapter(wAdapter);
    }


    @Override
    public void deleted(GetGoodsByBrandBean.ResultBean resultBean) {

    }

    public void onEvent(CompMechanicsEvent event) {
        if (event.resultBean != null) {
            compairsonAdapter.setData(event.resultBean);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }

    @Override
    public void select(GetGoodsByBrandBean.ResultBean resultBean) {
//        MechanicsByBrandActivity.start(getContext(), resultBean.getId());
    }
}

