package com.jhhscm.platform.fragment.Mechanics;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.BuildConfig;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.GoodsToCartsActivity;
import com.jhhscm.platform.activity.LoginActivity;
import com.jhhscm.platform.activity.SearchActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.FragmentPeiJianTypeBinding;
import com.jhhscm.platform.event.BrandResultEvent;
import com.jhhscm.platform.event.JumpEvent;
import com.jhhscm.platform.fragment.Mechanics.action.GoodsCatatoryListAction;
import com.jhhscm.platform.fragment.Mechanics.adapter.PeiJianModelAdapter;
import com.jhhscm.platform.fragment.Mechanics.bean.GoodsCatatoryListBean;
import com.jhhscm.platform.fragment.Mechanics.holder.PeiJianTypeViewHolder;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.DisplayUtils;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtils;

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
    private boolean isShowBack;
    private PeiJianModelAdapter mAdapterLeft;
    private GoodsCatatoryListBean goodsCatatoryListBean;
    private LinearLayoutManager linearLayoutManager;
    private String category_id = "";

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
        EventBusUtil.registerEvent(this);
        RelativeLayout.LayoutParams llParams = (RelativeLayout.LayoutParams) mDataBinding.top.getLayoutParams();
        llParams.topMargin += DisplayUtils.getStatusBarHeight(getContext());
        mDataBinding.top.setLayoutParams(llParams);
        mDataBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBusUtil.post(new JumpEvent("HOME_PAGE", null));
//                getActivity().finish();
            }
        });

        mDataBinding.homeEidt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.start(getContext(), 2);
            }
        });

        mDataBinding.tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConfigUtils.getCurrentUser(getContext()) != null
                        && ConfigUtils.getCurrentUser(getContext()).getUserCode() != null
                        && ConfigUtils.getCurrentUser(getContext()).getMobile() != null) {
                    GoodsToCartsActivity.start(getContext());
                } else {
                    startNewActivity(LoginActivity.class);
                }
            }
        });
        findBrand();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {//可见
            mIsFromClick = true;//防止初始化，被消耗掉
        }
    }

    /**
     * 获取配件品类列表 v1-3/goodscatatory/list
     */
    private void findBrand() {
        if (getContext() != null) {
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
        goodsCatatoryListBean = findBrandBean;
        linearLayoutManager = new LinearLayoutManager(getContext());
        mAdapterLeft = new PeiJianModelAdapter(goodsCatatoryListBean.getData(), getContext());
        //左边
        mDataBinding.type1.setLayoutManager(new LinearLayoutManager(getContext()));
        mDataBinding.type1.setAdapter(mAdapterLeft);
        mAdapterLeft.setMyListener(new PeiJianModelAdapter.ItemListener() {
            @Override
            public void onItemClick(GoodsCatatoryListBean.DataBean item, int position) {
                mAdapterLeft.mCheckedPosition = position;
                mAdapterLeft.notifyDataSetChanged();
                mIsFromClick = true;//不走onScrolled，防止来回调
                linearLayoutManager.scrollToPositionWithOffset(position, 0);
//                mIsFromClick = false;
            }
        });
        //右边
        mDataBinding.type2.setLayoutManager(linearLayoutManager);
        adapter = new InnerAdapter(getContext());
        mDataBinding.type2.setAdapter(adapter);
        adapter.setData(goodsCatatoryListBean.getData());
        mDataBinding.type2.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mIsFromClick) {//防止来回调
                    mIsFromClick = false;
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
                    mIsFromClick = false;
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

    public void onEvent(BrandResultEvent event) {
        if (event.getBrand_id() != null && event.getBrand_name() != null) {//品牌
            if (event.getType() == 2) {//品类
                category_id = event.getBrand_id();
                if (!category_id.equals("")) {
                    if (goodsCatatoryListBean != null && goodsCatatoryListBean.getData() != null && goodsCatatoryListBean.getData().size() > 0) {
                        for (int i = 0; i < goodsCatatoryListBean.getData().size(); i++) {
                            if (goodsCatatoryListBean.getData().get(i).getId().equals(category_id)) {
                                mAdapterLeft.mCheckedPosition = i;
                            }
                        }
                    }
                } else {
                    mAdapterLeft.mCheckedPosition = 0;
                }
                mAdapterLeft.notifyDataSetChanged();
                mIsFromClick = true;//不走onScrolled，防止来回调
                linearLayoutManager.scrollToPositionWithOffset(mAdapterLeft.mCheckedPosition, 0);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }
}
