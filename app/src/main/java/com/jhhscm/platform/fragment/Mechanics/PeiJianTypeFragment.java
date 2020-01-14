package com.jhhscm.platform.fragment.Mechanics;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.BuildConfig;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.SearchActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.FragmentPeiJianBinding;
import com.jhhscm.platform.databinding.FragmentPeiJianTypeBinding;
import com.jhhscm.platform.fragment.Mechanics.action.FindBrandAction;
import com.jhhscm.platform.fragment.Mechanics.action.GoodsCatatoryListAction;
import com.jhhscm.platform.fragment.Mechanics.adapter.BrandAdapter;
import com.jhhscm.platform.fragment.Mechanics.adapter.JXDropAdapter;
import com.jhhscm.platform.fragment.Mechanics.adapter.PeiJianModelAdapter;
import com.jhhscm.platform.fragment.Mechanics.adapter.PeiJianTypeAdapter;
import com.jhhscm.platform.fragment.Mechanics.bean.FindBrandBean;
import com.jhhscm.platform.fragment.Mechanics.bean.GetComboBoxBean;
import com.jhhscm.platform.fragment.Mechanics.bean.GetGoodsPageListBean;
import com.jhhscm.platform.fragment.Mechanics.bean.GoodsCatatoryListBean;
import com.jhhscm.platform.fragment.Mechanics.holder.NewMechanicsViewHolder;
import com.jhhscm.platform.fragment.Mechanics.holder.PeiJianTypeViewHolder;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.search.SearchNewFragment;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.DisplayUtils;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.recyclerview.DividerItemStrokeDecoration;

import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

public class PeiJianTypeFragment extends AbsFragment<FragmentPeiJianTypeBinding> {
    private GridLayoutManager gridLayoutManager;
    private InnerAdapter adapter;
    /**
     * 是否来自点击
     */
    private boolean mIsFromClick = false;


    public static PeiJianTypeFragment instance() {
        PeiJianTypeFragment view = new PeiJianTypeFragment();
        return view;
    }

    @Override
    protected FragmentPeiJianTypeBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentPeiJianTypeBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        LinearLayout.LayoutParams llParams = (LinearLayout.LayoutParams) mDataBinding.top.getLayoutParams();
        llParams.topMargin += DisplayUtils.getStatusBarHeight(getContext());
        mDataBinding.top.setLayoutParams(llParams);
        mDataBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        mDataBinding.homeEidt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.start(getContext());
            }
        });
        findBrand();
    }

    /**
     * 获取配件品类列表 v1-3/goodscatatory/list
     */
    private void findBrand() {
        if (getContext() != null) {
            showDialog();
            Map<String, String> map = new TreeMap<String, String>();
            map.put("pid", "7");
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = Sign.getSignKey(getActivity(), map, "GoodsCatatoryList");
            NetBean netBean = new NetBean();
            netBean.setToken("");
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(GoodsCatatoryListAction.newInstance(getContext(), netBean)
                    .request(new AHttpService.IResCallback<BaseEntity<GoodsCatatoryListBean>>() {
                        @Override
                        public void onCallback(int resultCode, Response<BaseEntity<GoodsCatatoryListBean>> response,
                                               BaseErrorInfo baseErrorInfo) {
                            if (getView() != null) {
                                closeDialog();
                                if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                    return;
                                }
                                if (response != null) {
                                    new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                    if (response.body().getCode().equals("200")) {
                                        pinpai(response.body().getData());
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

    /**
     * 配件品类
     */
    private void pinpai(GoodsCatatoryListBean findBrandBean) {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        final PeiJianModelAdapter mAdapterLeft = new PeiJianModelAdapter(findBrandBean.getData(), getContext());
        mAdapterLeft.append(findBrandBean.getData());
        //左边
        mDataBinding.type1.setLayoutManager(new LinearLayoutManager(getContext()));
        mDataBinding.type1.setAdapter(mAdapterLeft);
        mAdapterLeft.setMyListener(new PeiJianModelAdapter.ItemListener() {
            @Override
            public void onItemClick(GoodsCatatoryListBean.DataBean item, int position) {
                mAdapterLeft.mCheckedPosition = position;
                mAdapterLeft.notifyDataSetChanged();
                mIsFromClick = true;//不走onScrolled，防止来回调
                LinearSmoothScroller topScroller = new LinearSmoothScroller(getActivity()) {

                    @Override
                    protected int getHorizontalSnapPreference() {
                        return SNAP_TO_START;//具体见源码注释
                    }

                    @Override
                    protected int getVerticalSnapPreference() {
                        return SNAP_TO_START;//具体见源码注释
                    }

                    @Override
                    protected void onStop() {
                        super.onStop();
                        mIsFromClick = false;
                    }
                };
                topScroller.setTargetPosition(position);
                linearLayoutManager.startSmoothScroll(topScroller);
            }
        });
        //右边
        mDataBinding.type2.setLayoutManager(linearLayoutManager);
        adapter = new InnerAdapter(getContext());
        mDataBinding.type2.setAdapter(adapter);
        adapter.setData(findBrandBean.getData());
        mDataBinding.type2.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //和下面的handler配合，这样才能无bug
                    if (mIsFromClick) {
                        changePosition();//防止突然停了，定错位置
                    }
                    mIsFromClick = false;//滑动结束放开
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mIsFromClick) {//防止来回调
                    return;
                }
                changePosition();
            }

            private void changePosition() {
                int firstPosition = linearLayoutManager.findFirstVisibleItemPosition();
                if (mAdapterLeft.mCheckedPosition != firstPosition) {
                    mAdapterLeft.mCheckedPosition = firstPosition;
                    mAdapterLeft.notifyDataSetChanged();

                    //此方法无置顶效果
                    mDataBinding.type1.scrollToPosition(mAdapterLeft.mCheckedPosition);
                }
            }
        });
    }

    private class InnerAdapter extends AbsRecyclerViewAdapter<GoodsCatatoryListBean.DataBean> {
        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<GoodsCatatoryListBean.DataBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new PeiJianTypeViewHolder(mInflater.inflate(R.layout.item_peijian_right, parent, false));
        }
    }
}
