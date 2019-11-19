package com.jhhscm.platform.fragment.my.store;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.FragmentMyProductBinding;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.home.action.GetArticleListAction;
import com.jhhscm.platform.fragment.home.bean.GetPageArticleListBean;
import com.jhhscm.platform.fragment.my.store.action.BusinessFindcategorybyBuscodeAction;
import com.jhhscm.platform.fragment.my.store.action.BusinessFindcategorybyBuscodeBean;
import com.jhhscm.platform.fragment.my.store.viewholder.ProductItemViewHolder;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.sign.SignObject;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.recyclerview.DividerItemDecoration;
import com.jhhscm.platform.views.recyclerview.WrappedRecyclerView;

import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

public class MyProductFragment extends AbsFragment<FragmentMyProductBinding> {

    private InnerAdapter mAdapter;
    private int mShowCount = 10;
    private int mCurrentPage = 1;
    private final int START_PAGE = mCurrentPage;

    public static MyProductFragment instance() {
        MyProductFragment view = new MyProductFragment();
        return view;
    }

    @Override
    protected FragmentMyProductBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentMyProductBinding.inflate(inflater, container, attachToRoot);
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
                businessFindcategorybyBuscode(true);
            }

            @Override
            public void onLoadMore(RecyclerView view) {
                businessFindcategorybyBuscode(false);
            }
        });

        mDataBinding.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDataBinding.searchContent.getText().toString().length() > 0) {
                    mDataBinding.recyclerview.autoRefresh();
                } else {
                    ToastUtil.show(getContext(), "输入内容不能为空");
                }
            }
        });
    }

    private void businessFindcategorybyBuscode(final boolean refresh) {
        if (getContext() != null) {
            mCurrentPage = refresh ? START_PAGE : ++mCurrentPage;
            Map<String, Object> map = new TreeMap<String, Object>();
            map.put("page", mCurrentPage);
            map.put("limit", mShowCount);
            map.put("keyword", mDataBinding.searchContent.getText().toString().trim());
            map.put("user_code", ConfigUtils.getCurrentUser(getContext()).getUserCode());
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = SignObject.getSignKey(getActivity(), map, "businessFindcategorybyBuscode");
            NetBean netBean = new NetBean();
            netBean.setToken(ConfigUtils.getCurrentUser(getContext()).getToken());
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(BusinessFindcategorybyBuscodeAction.newInstance(getContext(), netBean)
                    .request(new AHttpService.IResCallback<BaseEntity<BusinessFindcategorybyBuscodeBean>>() {
                        @Override
                        public void onCallback(int resultCode, Response<BaseEntity<BusinessFindcategorybyBuscodeBean>> response,
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

    BusinessFindcategorybyBuscodeBean getPushListBean;

    private void initView(boolean refresh, BusinessFindcategorybyBuscodeBean pushListBean) {

        this.getPushListBean = pushListBean;
        if (refresh) {
            mAdapter.setData(pushListBean.getData());
        } else {
            mAdapter.append(pushListBean.getData());
        }
        mDataBinding.recyclerview.loadComplete(mAdapter.getItemCount() == 0,
                ((float) getPushListBean.getPage().getTotal() / (float) getPushListBean.getPage().getPageSize()) > mCurrentPage);
    }


    private class InnerAdapter extends AbsRecyclerViewAdapter<BusinessFindcategorybyBuscodeBean.DataBean> {
        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<BusinessFindcategorybyBuscodeBean.DataBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ProductItemViewHolder(mInflater.inflate(R.layout.item_store_product, parent, false));
        }
    }
}
