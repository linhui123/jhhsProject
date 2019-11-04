package com.jhhscm.platform.fragment.my.store;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.FragmentMyStoreOrderBinding;
import com.jhhscm.platform.databinding.FragmentStoreOrderBinding;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.home.action.GetArticleListAction;
import com.jhhscm.platform.fragment.home.bean.GetPageArticleListBean;
import com.jhhscm.platform.fragment.my.store.viewholder.StoreOrderItemViewHolder;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.sign.SignObject;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.recyclerview.WrappedRecyclerView;

import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class StoreOrderFragment extends AbsFragment<FragmentStoreOrderBinding> {

    private InnerAdapter mAdapter;
    private int mShowCount = 10;
    private int mCurrentPage = 1;
    private final int START_PAGE = mCurrentPage;
    private String type = "";

    public static StoreOrderFragment instance() {
        StoreOrderFragment view = new StoreOrderFragment();
        return view;
    }

    @Override
    protected FragmentStoreOrderBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentStoreOrderBinding.inflate(inflater, container, attachToRoot);
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

        mDataBinding.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "";
                mDataBinding.button1.setTextColor(getResources().getColor(R.color.white));
                mDataBinding.button1.setBackgroundResource(R.drawable.button_c397);
                mDataBinding.button2.setTextColor(getResources().getColor(R.color.acc9));
                mDataBinding.button2.setBackgroundResource(R.drawable.bg_line_de);
                mDataBinding.button3.setTextColor(getResources().getColor(R.color.acc9));
                mDataBinding.button3.setBackgroundResource(R.drawable.bg_line_de);
                mDataBinding.recyclerview.autoRefresh();
            }
        });

        mDataBinding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "";
                mDataBinding.button1.setTextColor(getResources().getColor(R.color.acc9));
                mDataBinding.button1.setBackgroundResource(R.drawable.bg_line_de);
                mDataBinding.button2.setTextColor(getResources().getColor(R.color.white));
                mDataBinding.button2.setBackgroundResource(R.drawable.button_c397);
                mDataBinding.button3.setTextColor(getResources().getColor(R.color.acc9));
                mDataBinding.button3.setBackgroundResource(R.drawable.bg_line_de);
                mDataBinding.recyclerview.autoRefresh();
            }
        });

        mDataBinding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "";
                mDataBinding.button1.setTextColor(getResources().getColor(R.color.acc9));
                mDataBinding.button1.setBackgroundResource(R.drawable.bg_line_de);
                mDataBinding.button2.setTextColor(getResources().getColor(R.color.acc9));
                mDataBinding.button2.setBackgroundResource(R.drawable.bg_line_de);
                mDataBinding.button3.setTextColor(getResources().getColor(R.color.white));
                mDataBinding.button3.setBackgroundResource(R.drawable.button_c397);
                mDataBinding.recyclerview.autoRefresh();
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
            return new StoreOrderItemViewHolder(mInflater.inflate(R.layout.item_store_order, parent, false));
        }
    }

}
