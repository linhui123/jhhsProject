package com.jhhscm.platform.fragment.aftersale;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.FragmentAfterSaleBinding;
import com.jhhscm.platform.databinding.FragmentCouponCenterBinding;
import com.jhhscm.platform.event.GetRegionEvent;
import com.jhhscm.platform.fragment.GoodsToCarts.GetCartGoodsByUserCodeBean;
import com.jhhscm.platform.fragment.Mechanics.MechanicsFragment;
import com.jhhscm.platform.fragment.Mechanics.action.GetRegionAction;
import com.jhhscm.platform.fragment.Mechanics.bean.GetRegionBean;
import com.jhhscm.platform.fragment.Mechanics.holder.GetRegionViewHolder;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.coupon.CouponCenterFragment;
import com.jhhscm.platform.fragment.coupon.CouponCenterViewHolder;
import com.jhhscm.platform.fragment.home.action.GetArticleListAction;
import com.jhhscm.platform.fragment.home.bean.GetPageArticleListBean;
import com.jhhscm.platform.fragment.my.store.viewholder.MyMemberItemViewHolder;
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
import com.jhhscm.platform.views.recyclerview.DividerItemDecoration;
import com.jhhscm.platform.views.recyclerview.DividerItemStrokeDecoration;
import com.jhhscm.platform.views.recyclerview.WrappedRecyclerView;

import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

public class AfterSaleFragment extends AbsFragment<FragmentAfterSaleBinding> {
    private InnerAdapter mAdapter;
    private int mShowCount = 10;
    private int mCurrentPage = 1;
    private final int START_PAGE = mCurrentPage;

    String pID = "";
    String cID = "";

    private String pName;
    private String cName;

    PrinviceAdapter pAdapter;
    CityAdapter cAdapter;

    GetRegionBean pRegionBean;
    GetRegionBean cRegionBean;

    public static AfterSaleFragment instance() {
        AfterSaleFragment view = new AfterSaleFragment();
        return view;
    }

    @Override
    protected FragmentAfterSaleBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentAfterSaleBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        EventBusUtil.registerEvent(this);

        RelativeLayout.LayoutParams llParams = (RelativeLayout.LayoutParams) mDataBinding.rlTop.getLayoutParams();
        llParams.topMargin += DisplayUtils.getStatusBarHeight(getContext());
        mDataBinding.rlTop.setLayoutParams(llParams);

        mDataBinding.recyclerview.addItemDecoration(new DividerItemStrokeDecoration(getContext()));
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

        initPrivince();
        getRegion("1", "");

        mDataBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        mDataBinding.location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDataBinding.llArea.getVisibility() == View.GONE) {
                    mDataBinding.llArea.setVisibility(View.VISIBLE);
                } else if (mDataBinding.llArea.getVisibility() == View.VISIBLE) {
                    mDataBinding.llArea.setVisibility(View.GONE);
                }
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
            return new AfterSaleViewHolder(mInflater.inflate(R.layout.item_aftersale_store, parent, false));
        }
    }

    /**
     * 更新地区选择
     */
    public void onEvent(GetRegionEvent event) {
        if (event.pid != null && event.type != null) {
            if (event.type.equals("1")) {//省点击，获取市
                pID = event.pid;
                pName = event.name;
                getRegion("2", event.pid);
            } else if (event.type.equals("2")) {//市点击
                cID = event.pid;
                cName = event.name;
                mDataBinding.location.setText(pName + " " + cName);
                mDataBinding.llArea.setVisibility(View.GONE);
                mDataBinding.recyclerview.autoRefresh();
            } else if (event.type.equals("0")) {//全部点击
                cID = event.pid;
                cName = event.name;
                mDataBinding.location.setText("选择地区");
                mDataBinding.llArea.setVisibility(View.GONE);
                mDataBinding.recyclerview.autoRefresh();
            }
        }
    }

    /**
     * 获取行政区域列表
     */
    private void getRegion(final String type, final String pid) {
        if (getContext() != null) {
            Map<String, String> map = new TreeMap<String, String>();
            map.put("type", type);
            map.put("pid", pid);
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = Sign.getSignKey(getActivity(), map, "getRegion");
            NetBean netBean = new NetBean();
            netBean.setToken("");
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(GetRegionAction.newInstance(getContext(), netBean)
                    .request(new AHttpService.IResCallback<BaseEntity<GetRegionBean>>() {
                        @Override
                        public void onCallback(int resultCode, Response<BaseEntity<GetRegionBean>> response,
                                               BaseErrorInfo baseErrorInfo) {
                            if (getView() != null) {
                                closeDialog();
                                if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                    return;
                                }
                                if (response != null) {
                                    new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                    if (response.body().getCode().equals("200")) {
                                        GetRegionBean getRegionBean = response.body().getData();
                                        if (getRegionBean.getResult() != null) {
                                            if (type.equals("1")) {//初次加载
                                                pRegionBean = getRegionBean;
                                                GetRegionBean.ResultBean resultBean = new GetRegionBean.ResultBean();
                                                resultBean.setName("全部");
                                                resultBean.setId(0);
                                                resultBean.setType(0);
                                                pRegionBean.getResult().add(0, resultBean);
                                                pID = getRegionBean.getResult().get(0).getId() + "";
                                                pAdapter.setData(pRegionBean.getResult());
                                                getRegion("2", getRegionBean.getResult().get(0).getId() + "");
                                            } else {
                                                if (getRegionBean.getResult().size() > 0) {
                                                    cRegionBean = getRegionBean;
                                                    cID = getRegionBean.getResult().get(0).getId() + "";
                                                    cAdapter.setData(cRegionBean.getResult());
                                                } else {
//                                                    ToastUtils.show(getContext(), "无数据");
                                                }
                                            }
                                        }
                                    } else {
                                        ToastUtils.show(getContext(), response.body().getMessage());
                                    }
                                }
                            }
                        }
                    }));
        }
    }

    private void initPrivince() {
        mDataBinding.rlPrivince.setLayoutManager(new LinearLayoutManager(getContext()));
        pAdapter = new PrinviceAdapter(getContext());
        mDataBinding.rlPrivince.setAdapter(pAdapter);

        mDataBinding.rlCity.addItemDecoration(new DividerItemDecoration(getContext()));
        mDataBinding.rlCity.setLayoutManager(new LinearLayoutManager(getContext()));
        cAdapter = new CityAdapter(getContext());
        mDataBinding.rlCity.setAdapter(cAdapter);
    }

    private class PrinviceAdapter extends AbsRecyclerViewAdapter<GetRegionBean.ResultBean> {
        public PrinviceAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<GetRegionBean.ResultBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new GetRegionViewHolder(mInflater.inflate(R.layout.item_mechanics_privince, parent, false));
        }
    }

    private class CityAdapter extends AbsRecyclerViewAdapter<GetRegionBean.ResultBean> {
        public CityAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<GetRegionBean.ResultBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new GetRegionViewHolder(mInflater.inflate(R.layout.item_mechanics_privince, parent, false));
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }
}
