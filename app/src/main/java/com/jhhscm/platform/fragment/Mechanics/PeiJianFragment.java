package com.jhhscm.platform.fragment.Mechanics;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.FragmentNewMechanicsBinding;
import com.jhhscm.platform.databinding.FragmentPeiJianBinding;
import com.jhhscm.platform.fragment.Mechanics.action.FindBrandAction;
import com.jhhscm.platform.fragment.Mechanics.action.FindCategoryAction;
import com.jhhscm.platform.fragment.Mechanics.action.GetComboBoxAction;
import com.jhhscm.platform.fragment.Mechanics.action.GetGoodsPageListAction;
import com.jhhscm.platform.fragment.Mechanics.adapter.BrandAdapter;
import com.jhhscm.platform.fragment.Mechanics.adapter.JXDropAdapter;
import com.jhhscm.platform.fragment.Mechanics.adapter.SXDropAdapter;
import com.jhhscm.platform.fragment.Mechanics.adapter.SelectedAdapter;
import com.jhhscm.platform.fragment.Mechanics.bean.FindBrandBean;
import com.jhhscm.platform.fragment.Mechanics.bean.FindCategoryBean;
import com.jhhscm.platform.fragment.Mechanics.bean.GetComboBoxBean;
import com.jhhscm.platform.fragment.Mechanics.bean.GetGoodsPageListBean;
import com.jhhscm.platform.fragment.Mechanics.holder.NewMechanicsViewHolder;
import com.jhhscm.platform.fragment.Mechanics.holder.PeiJianViewHolder;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.DisplayUtils;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.recyclerview.WrappedRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;


public class PeiJianFragment extends AbsFragment<FragmentPeiJianBinding> {
    private InnerAdapter mAdapter;
    private SelectedAdapter selectedAdapter;
    private List<GetComboBoxBean.ResultBean> resultBeanList;
    private int mShowCount = 10;
    private int mCurrentPage = 1;
    private final int START_PAGE = mCurrentPage;

    private String fix_p_9 = "";//机型
    private String merchant_id = "";//产商
    private String fix_p_3 = "";//动力
    private String fix_p_2 = "";//铲斗
    private String fix_p_1 = "";//吨位

    public static PeiJianFragment instance() {
        PeiJianFragment view = new PeiJianFragment();
        return view;
    }

    @Override
    protected FragmentPeiJianBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentPeiJianBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        RelativeLayout.LayoutParams llParams = (RelativeLayout.LayoutParams) mDataBinding.rlTop.getLayoutParams();
        llParams.topMargin += DisplayUtils.getStatusBarHeight(getContext());
        mDataBinding.rlTop.setLayoutParams(llParams);

        mDataBinding.wrvRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new InnerAdapter(getContext());
        mDataBinding.wrvRecycler.setAdapter(mAdapter);
        mDataBinding.wrvRecycler.autoRefresh();
        mDataBinding.wrvRecycler.setOnPullListener(new WrappedRecyclerView.OnPullListener() {
            @Override
            public void onRefresh(RecyclerView view) {
                findCategory(true);
            }

            @Override
            public void onLoadMore(RecyclerView view) {
                findCategory(false);
            }
        });

        initDrop();
        resultBeanList = new ArrayList<>();
        mDataBinding.llOhter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataBinding.llXiala.setVisibility(View.GONE);
                closeDrap();
            }
        });
        mDataBinding.tvZonghe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDataBinding.llXiala.getVisibility() == View.GONE) {
                    mDataBinding.llXiala.setVisibility(View.VISIBLE);
                    mDataBinding.llZonghe.setVisibility(View.VISIBLE);
                } else {
                    if (mDataBinding.llZonghe.getVisibility() == View.VISIBLE) {
                        mDataBinding.llXiala.setVisibility(View.GONE);
                        closeDrap();
                    } else {
                        closeDrap();
                        mDataBinding.llZonghe.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        mDataBinding.tvQuanbu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDataBinding.llXiala.getVisibility() == View.GONE) {
                    mDataBinding.llXiala.setVisibility(View.VISIBLE);
                    mDataBinding.llQuanbu.setVisibility(View.VISIBLE);
                } else {
                    if (mDataBinding.llQuanbu.getVisibility() == View.VISIBLE) {
                        mDataBinding.llXiala.setVisibility(View.GONE);
                        closeDrap();
                    } else {
                        closeDrap();
                        mDataBinding.llQuanbu.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        mDataBinding.tvPinpai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDataBinding.llXiala.getVisibility() == View.GONE) {
                    mDataBinding.llXiala.setVisibility(View.VISIBLE);
                    mDataBinding.llPinpai.setVisibility(View.VISIBLE);
                } else {
                    if (mDataBinding.llPinpai.getVisibility() == View.VISIBLE) {
                        mDataBinding.llXiala.setVisibility(View.GONE);
                        closeDrap();
                    } else {
                        closeDrap();
                        mDataBinding.llPinpai.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        mDataBinding.tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    /**
     * 获取配件列表
     */
    private void findCategory(final boolean refresh) {
        if (getContext() != null) {
            mCurrentPage = refresh ? START_PAGE : ++mCurrentPage;
            Map<String, String> map = new TreeMap<String, String>();
            map.put("keyword", "");
            map.put("category_id", fix_p_9);
            map.put("brand_id", merchant_id);
            map.put("sort_type", fix_p_3);//1是价格降序 2是价格升序
            map.put("page", mCurrentPage + "");
            map.put("limit", mShowCount + "");
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = Sign.getSignKey(getActivity(), map,"findCategory");
            NetBean netBean = new NetBean();
            netBean.setToken("");
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(FindCategoryAction.newInstance(getContext(), netBean)
                    .request(new AHttpService.IResCallback<BaseEntity<FindCategoryBean>>() {
                        @Override
                        public void onCallback(int resultCode, Response<BaseEntity<FindCategoryBean>> response,
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

    private FindCategoryBean findCategoryBean;

    private void doSuccessResponse(boolean refresh, FindCategoryBean categoryBean) {
        this.findCategoryBean = categoryBean;
        if (refresh) {
            mAdapter.setData(categoryBean.getData());
        } else {
            mAdapter.append(categoryBean.getData());
        }
        mDataBinding.wrvRecycler.getAdapter().notifyDataSetChanged();
        mDataBinding.wrvRecycler.loadComplete(mAdapter.getItemCount() == 0, ((float) findCategoryBean.getPage().getTotal() / (float) findCategoryBean.getPage().getPageSize()) > mCurrentPage);
    }

    private class InnerAdapter extends AbsRecyclerViewAdapter<FindCategoryBean.DataBean> {
        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<FindCategoryBean.DataBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new PeiJianViewHolder(mInflater.inflate(R.layout.item_mechanics_peijian, parent, false));
        }
    }

    /**
     * 获取下拉框
     */
    private void getComboBox(final String name) {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("key_group_name", name);
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getActivity(), map, "getComboBox:" + "name");
        NetBean netBean = new NetBean();
        netBean.setToken("");
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(GetComboBoxAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<GetComboBoxBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<GetComboBoxBean>> response, BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                if (response.body().getCode().equals("200")) {
                                    if ("goods_type".equals(name)) {
                                        zonghe(response.body().getData());
                                    } else if ("goods_factory".equals(name)) {
                                        quanbu(response.body().getData());
                                    }
                                } else {
                                    ToastUtils.show(getContext(), "error " + name + ":" + response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
    }

    /**
     * 初始化下拉
     */
    private void initDrop() {
        /**
         * goods_type	    否		机型
         * goods_factory	否		商品厂商
         * goods_power  	是		动力
         * goods_shovel	    否		铲斗
         * goods_ton	    是		吨位
         * old_sort     	是		二手机排序
         */

        getComboBox("goods_type");
        getComboBox("goods_factory");
        getComboBox("goods_power");
        getComboBox("goods_shovel");
        getComboBox("goods_ton");
        findBrand();
    }

    /**
     * 获取品牌列表 findBrand
     */
    private void findBrand() {
        if (getContext() != null) {
            Map<String, String> map = new TreeMap<String, String>();
            map.put("brand_type", "2");
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = Sign.getSignKey(getActivity(), map, "findBrand");
            NetBean netBean = new NetBean();
            netBean.setToken("");
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(FindBrandAction.newInstance(getContext(), netBean)
                    .request(new AHttpService.IResCallback<BaseEntity<FindBrandBean>>() {
                        @Override
                        public void onCallback(int resultCode, Response<BaseEntity<FindBrandBean>> response,
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
     * 下拉综合
     */
    private void zonghe(final GetComboBoxBean getComboBoxBean) {
        mDataBinding.rlZonghe.setLayoutManager(new LinearLayoutManager(getContext()));
        JXDropAdapter JXAdapter = new JXDropAdapter(getComboBoxBean.getResult(), getContext());
        mDataBinding.rlZonghe.setAdapter(JXAdapter);
        JXAdapter.setMyListener(new JXDropAdapter.ItemListener() {
            @Override
            public void onItemClick(GetComboBoxBean.ResultBean item) {
                fix_p_9 = item.getId();
                mDataBinding.tvZonghe.setText(item.getKey_value());
                mDataBinding.llXiala.setVisibility(View.GONE);
                closeDrap();
                mDataBinding.wrvRecycler.autoRefresh();
            }
        });
    }

    /**
     * 下拉全部
     */
    private void quanbu(final GetComboBoxBean getComboBoxBean) {
        mDataBinding.rlQuanbu.setLayoutManager(new LinearLayoutManager(getContext()));
        JXDropAdapter JXAdapter = new JXDropAdapter(getComboBoxBean.getResult(), getContext());
        mDataBinding.rlQuanbu.setAdapter(JXAdapter);
        JXAdapter.setMyListener(new JXDropAdapter.ItemListener() {
            @Override
            public void onItemClick(GetComboBoxBean.ResultBean item) {
                merchant_id = item.getId();
                mDataBinding.tvQuanbu.setText(item.getKey_value());
                mDataBinding.llXiala.setVisibility(View.GONE);
                closeDrap();
                mDataBinding.wrvRecycler.autoRefresh();
            }
        });
    }

    /**
     * 下拉品牌
     */
    private void pinpai(FindBrandBean findBrandBean) {
        mDataBinding.rlPinpai.setLayoutManager(new GridLayoutManager(getContext(), 3));
        BrandAdapter bAdapter = new BrandAdapter(findBrandBean.getResult(), getContext());
        mDataBinding.rlPinpai.setAdapter(bAdapter);
        bAdapter.setMyListener(new BrandAdapter.ItemListener() {
            @Override
            public void onItemClick(FindBrandBean.ResultBean item) {
                merchant_id = item.getId();
                mDataBinding.tvPinpai.setText(item.getName());
                mDataBinding.llXiala.setVisibility(View.GONE);
                closeDrap();
                mDataBinding.wrvRecycler.autoRefresh();
            }
        });
    }

    private void closeDrap() {
        mDataBinding.llZonghe.setVisibility(View.GONE);
        mDataBinding.llQuanbu.setVisibility(View.GONE);
        mDataBinding.llPinpai.setVisibility(View.GONE);
    }
}
