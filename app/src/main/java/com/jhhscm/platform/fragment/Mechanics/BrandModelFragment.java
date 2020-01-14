package com.jhhscm.platform.fragment.Mechanics;


import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.BuildConfig;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.BrandModelActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.FragmentBrandModelBinding;
import com.jhhscm.platform.databinding.ItemLocationBinding;
import com.jhhscm.platform.databinding.ItemTvBinding;
import com.jhhscm.platform.event.BrandResultEvent;
import com.jhhscm.platform.event.SerachEvent;
import com.jhhscm.platform.fragment.Mechanics.action.BrandModelList1Action;
import com.jhhscm.platform.fragment.Mechanics.action.BrandModelList2Action;
import com.jhhscm.platform.fragment.Mechanics.adapter.BrandModelAdapter;
import com.jhhscm.platform.fragment.Mechanics.bean.BrandModel1Bean;
import com.jhhscm.platform.fragment.Mechanics.bean.BrandModel2Bean;
import com.jhhscm.platform.fragment.Mechanics.bean.BrandModelBean;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.recyclerview.DividerItemDecoration;
import com.jhhscm.platform.views.recyclerview.DividerItemStrokeDecoration;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

import static com.jhhscm.platform.fragment.search.SearchFragment.hideSoftInputFromWindow;

/**
 * 品牌机型列表
 */
public class BrandModelFragment extends AbsFragment<FragmentBrandModelBinding> {
    private BrandModelBean.DataBean item;
    private InnerAdapter mAdapter;
    private String id;
    private String name;

    public static BrandModelFragment instance() {
        BrandModelFragment view = new BrandModelFragment();
        return view;
    }

    @Override
    protected FragmentBrandModelBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentBrandModelBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        item = (BrandModelBean.DataBean) getArguments().getSerializable("dataBean");
        id = getArguments().getString("id");
        name = getArguments().getString("name");
        if (id != null && name != null) {
            mDataBinding.tvName.setText(name);
            brandModel();
            mDataBinding.homeEidt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        if (mDataBinding.homeEidt.getText().toString().trim().length() > 0) {
                            brandModel();
                        }
                        hideSoftInputFromWindow(getActivity());
                        return true;
                    }
                    return false;
                }
            });
        } else {
            getActivity().finish();
        }
//        if (item != null) {
//            mDataBinding.tvName.setText(item.getBrand_name());
//            mDataBinding.recyclerview.addItemDecoration(new DividerItemDecoration(getContext()));
//            mDataBinding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
//            mAdapter = new InnerAdapter(getContext());
//            mDataBinding.recyclerview.setAdapter(mAdapter);
//            mAdapter.setData(item.getBrand_model_list());
//        } else {
//            getActivity().finish();
//        }
    }

    /**
     * 获取+ 机型列表
     */
    private void brandModel() {
        if (getContext() != null) {
            showDialog();
            Map<String, String> map = new TreeMap<String, String>();
            map.put("keyword", mDataBinding.homeEidt.getText().toString().trim());
            map.put("id", id);
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = Sign.getSignKey(getActivity(), map, "brandModel");
            NetBean netBean = new NetBean();
            netBean.setToken("");
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(BrandModelList2Action.newInstance(getContext(), netBean)
                    .request(new AHttpService.IResCallback<BaseEntity<BrandModel2Bean>>() {
                        @Override
                        public void onCallback(int resultCode, Response<BaseEntity<BrandModel2Bean>> response,
                                               BaseErrorInfo baseErrorInfo) {
                            if (getView() != null) {
                                closeDialog();
                                if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                    return;
                                }
                                if (response != null) {
                                    new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                    if (response.body().getCode().equals("200")) {
                                        jixing(response.body().getData());
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
     * 下拉机型
     */
    private void jixing(BrandModel2Bean brandModelBean) {
        mDataBinding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        InnerAdapter bAdapter = new InnerAdapter(getContext());
        mDataBinding.recyclerview.setAdapter(bAdapter);
        if (brandModelBean != null && brandModelBean.getData() != null && brandModelBean.getData().size() > 0) {
            bAdapter.setData(brandModelBean.getData().get(0).getType());
        }
    }

    private class InnerAdapter extends AbsRecyclerViewAdapter<BrandModel2Bean.DataBean.TypeBean> {
        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<BrandModel2Bean.DataBean.TypeBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BrandModelItemViewHolder(mInflater.inflate(R.layout.item_tv, parent, false));
        }
    }

    private class BrandModelItemViewHolder extends AbsRecyclerViewHolder<BrandModel2Bean.DataBean.TypeBean> {

        private ItemTvBinding mBinding;

        public BrandModelItemViewHolder(View itemView) {
            super(itemView);
            mBinding = ItemTvBinding.bind(itemView);
        }

        @Override
        protected void onBindView(final BrandModel2Bean.DataBean.TypeBean item) {
            if (item != null) {
                mBinding.tvName.setText(item.getType_name());
                mBinding.tvName.setGravity(Gravity.LEFT);
                mBinding.tvName.setTypeface(Typeface.DEFAULT_BOLD);
                mBinding.rl.setBackgroundResource(R.color.cf5);
                mBinding.rv.setVisibility(View.VISIBLE);
                mBinding.line.setVisibility(View.GONE);
                mBinding.rv.setLayoutManager(new LinearLayoutManager(getContext()));
                Inner2Adapter bAdapter = new Inner2Adapter(getContext());
                bAdapter.setData(item.getBrand_model_list());
                mBinding.rv.setAdapter(bAdapter);
                mBinding.rv.setNestedScrollingEnabled(false);
            }
        }
    }

    private class Inner2Adapter extends AbsRecyclerViewAdapter<BrandModel2Bean.DataBean.TypeBean.BrandModelListBean> {
        public Inner2Adapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<BrandModel2Bean.DataBean.TypeBean.BrandModelListBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BrandModel2ItemViewHolder(mInflater.inflate(R.layout.item_tv, parent, false));
        }
    }

    private class BrandModel2ItemViewHolder extends AbsRecyclerViewHolder<BrandModel2Bean.DataBean.TypeBean.BrandModelListBean> {

        private ItemTvBinding mBinding;

        public BrandModel2ItemViewHolder(View itemView) {
            super(itemView);
            mBinding = ItemTvBinding.bind(itemView);
        }

        @Override
        protected void onBindView(final BrandModel2Bean.DataBean.TypeBean.BrandModelListBean item) {
            if (item != null) {
                mBinding.tvName.setText(item.getModel_name());
                mBinding.tvName.setGravity(Gravity.LEFT);
                mBinding.rl.setBackgroundResource(R.color.white);
                mBinding.line.setVisibility(View.VISIBLE);
                mBinding.rl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventBusUtil.post(new BrandResultEvent(item.getModel_id() + "", item.getModel_name(), 1));
                        getActivity().finish();
                    }
                });
            }
        }
    }
}

