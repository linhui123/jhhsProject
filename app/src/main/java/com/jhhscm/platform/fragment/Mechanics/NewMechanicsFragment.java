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

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.FragmentMechanicsBinding;
import com.jhhscm.platform.databinding.FragmentNewMechanicsBinding;
import com.jhhscm.platform.fragment.Mechanics.action.FindBrandAction;
import com.jhhscm.platform.fragment.Mechanics.action.GetComboBoxAction;
import com.jhhscm.platform.fragment.Mechanics.action.GetGoodsPageListAction;
import com.jhhscm.platform.fragment.Mechanics.adapter.BrandAdapter;
import com.jhhscm.platform.fragment.Mechanics.adapter.JXDropAdapter;
import com.jhhscm.platform.fragment.Mechanics.adapter.SXDropAdapter;
import com.jhhscm.platform.fragment.Mechanics.adapter.SelectedAdapter;
import com.jhhscm.platform.fragment.Mechanics.bean.FindBrandBean;
import com.jhhscm.platform.fragment.Mechanics.bean.GetComboBoxBean;
import com.jhhscm.platform.fragment.Mechanics.bean.GetGoodsPageListBean;
import com.jhhscm.platform.fragment.Mechanics.holder.NewMechanicsViewHolder;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.home.HomePageAdapter;
import com.jhhscm.platform.fragment.home.HomePageItem;
import com.jhhscm.platform.fragment.home.action.FindBrandHomePageAction;
import com.jhhscm.platform.fragment.home.bean.FindBrandHomePageBean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.recyclerview.WrappedRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

/**
 * 新机列表
 */
public class NewMechanicsFragment extends AbsFragment<FragmentNewMechanicsBinding> {
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
    private String brand_id = "";

    public static NewMechanicsFragment instance() {
        NewMechanicsFragment view = new NewMechanicsFragment();
        return view;
    }

    @Override
    protected FragmentNewMechanicsBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentNewMechanicsBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        mDataBinding.wrvRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new InnerAdapter(getContext());
        mDataBinding.wrvRecycler.setAdapter(mAdapter);
        mDataBinding.wrvRecycler.autoRefresh();
        mDataBinding.wrvRecycler.setOnPullListener(new WrappedRecyclerView.OnPullListener() {
            @Override
            public void onRefresh(RecyclerView view) {
                getGoodsPageList(true);
            }

            @Override
            public void onLoadMore(RecyclerView view) {
                getGoodsPageList(false);
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
        mDataBinding.tvJixing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDataBinding.llXiala.getVisibility() == View.GONE) {
                    mDataBinding.llXiala.setVisibility(View.VISIBLE);
                    mDataBinding.llJixing.setVisibility(View.VISIBLE);
                } else {
                    if (mDataBinding.llJixing.getVisibility() == View.VISIBLE) {
                        mDataBinding.llXiala.setVisibility(View.GONE);
                        closeDrap();
                    } else {
                        closeDrap();
                        mDataBinding.llJixing.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        mDataBinding.tvChanshang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDataBinding.llXiala.getVisibility() == View.GONE) {
                    mDataBinding.llXiala.setVisibility(View.VISIBLE);
                    mDataBinding.llChanshang.setVisibility(View.VISIBLE);
                } else {
                    if (mDataBinding.llChanshang.getVisibility() == View.VISIBLE) {
                        mDataBinding.llXiala.setVisibility(View.GONE);
                        closeDrap();
                    } else {
                        closeDrap();
                        mDataBinding.llChanshang.setVisibility(View.VISIBLE);
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

        mDataBinding.tvShaixuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDataBinding.llXiala.getVisibility() == View.GONE) {
                    mDataBinding.llXiala.setVisibility(View.VISIBLE);
                    mDataBinding.llShuaixuan.setVisibility(View.VISIBLE);
                } else {
                    if (mDataBinding.llShuaixuan.getVisibility() == View.VISIBLE) {
                        mDataBinding.llXiala.setVisibility(View.GONE);
                        closeDrap();
                    } else {
                        closeDrap();
                        mDataBinding.llShuaixuan.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    /**
     * 获取新机列表
     */
    private void getGoodsPageList(final boolean refresh) {
        if (getContext() != null) {
            mCurrentPage = refresh ? START_PAGE : ++mCurrentPage;
            Map<String, String> map = new TreeMap<String, String>();
            map.put("keyword", "");
            map.put("fix_p_9", fix_p_9);
            map.put("merchant_id", merchant_id);
            map.put("merchant_id", merchant_id);
            map.put("fix_p_3", fix_p_3);
            map.put("fix_p_2", fix_p_2);
            map.put("fix_p_1", fix_p_1);
            map.put("brand_id", brand_id);
            map.put("page", mCurrentPage + "");
            map.put("limit", mShowCount + "");
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = Sign.getSignKey(getActivity(), map, "getGoodsPageList");
            NetBean netBean = new NetBean();
            netBean.setToken("");
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(GetGoodsPageListAction.newInstance(getContext(), netBean)
                    .request(new AHttpService.IResCallback<BaseEntity<GetGoodsPageListBean>>() {
                        @Override
                        public void onCallback(int resultCode, Response<BaseEntity<GetGoodsPageListBean>> response,
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

    private GetGoodsPageListBean getGoodsPageListBean;

    private void doSuccessResponse(boolean refresh, GetGoodsPageListBean getGoodsPageList) {
        this.getGoodsPageListBean = getGoodsPageList;
        if (refresh) {
            mAdapter.setData(getGoodsPageList.getData());
        } else {
            mAdapter.append(getGoodsPageList.getData());
        }
        mDataBinding.wrvRecycler.getAdapter().notifyDataSetChanged();
        mDataBinding.wrvRecycler.loadComplete(mAdapter.getItemCount() == 0, ((float) getGoodsPageListBean.getPage().getTotal() / (float) getGoodsPageListBean.getPage().getPageSize()) > mCurrentPage);
    }

    private class InnerAdapter extends AbsRecyclerViewAdapter<GetGoodsPageListBean.DataBean> {
        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<GetGoodsPageListBean.DataBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new NewMechanicsViewHolder(mInflater.inflate(R.layout.item_mechanics_new, parent, false));
        }
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
        mDataBinding.tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initSelected();
            }
        });
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
                                        jixing(response.body().getData());
                                    } else if ("goods_factory".equals(name)) {
                                        chanshang(response.body().getData());
                                    } else if ("goods_power".equals(name)) {
                                        dongli(response.body().getData());
                                    } else if ("goods_shovel".equals(name)) {
                                        chandou(response.body().getData());
                                    } else if ("goods_ton".equals(name)) {
                                        dunwei(response.body().getData());
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
     * 获取品牌列表 findBrand
     */
    private void findBrand() {
        if (getContext() != null) {
            Map<String, String> map = new TreeMap<String, String>();
            map.put("brand_type", "1");
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

    private void initSelected() {
        //调整RecyclerView的排列方向
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mDataBinding.rlSelected.setLayoutManager(layoutManager);
        selectedAdapter = new SelectedAdapter(resultBeanList, getContext());
        mDataBinding.rlSelected.setAdapter(selectedAdapter);
        mDataBinding.llXiala.setVisibility(View.GONE);
        closeDrap();
        mDataBinding.wrvRecycler.autoRefresh();
    }

    /**
     * 下拉机型
     */
    private void jixing(final GetComboBoxBean getComboBoxBean) {
        mDataBinding.rlJixing.setLayoutManager(new LinearLayoutManager(getContext()));
        JXDropAdapter JXAdapter = new JXDropAdapter(getComboBoxBean.getResult(), getContext());
        mDataBinding.rlJixing.setAdapter(JXAdapter);
        JXAdapter.setMyListener(new JXDropAdapter.ItemListener() {
            @Override
            public void onItemClick(GetComboBoxBean.ResultBean item) {
                fix_p_9 = item.getId();
                mDataBinding.tvJixing.setText(item.getKey_value());
                mDataBinding.llXiala.setVisibility(View.GONE);
                closeDrap();
                mDataBinding.wrvRecycler.autoRefresh();
            }
        });
    }

    /**
     * 下拉产商
     */
    private void chanshang(final GetComboBoxBean getComboBoxBean) {
        mDataBinding.rlChanshang.setLayoutManager(new LinearLayoutManager(getContext()));
        JXDropAdapter JXAdapter = new JXDropAdapter(getComboBoxBean.getResult(), getContext());
        mDataBinding.rlChanshang.setAdapter(JXAdapter);
        JXAdapter.setMyListener(new JXDropAdapter.ItemListener() {
            @Override
            public void onItemClick(GetComboBoxBean.ResultBean item) {
                merchant_id = item.getId();
                mDataBinding.tvChanshang.setText(item.getKey_value());
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
                brand_id = item.getId();
                mDataBinding.tvPinpai.setText(item.getName());
                mDataBinding.llXiala.setVisibility(View.GONE);
                closeDrap();
                mDataBinding.wrvRecycler.autoRefresh();
            }
        });
    }

    /**
     * 下拉筛选
     */
    private void dongli(final GetComboBoxBean getComboBoxBean) {
        mDataBinding.rlDongli.setLayoutManager(new GridLayoutManager(getContext(), 3));
        SXDropAdapter sxDropAdapter = new SXDropAdapter(getComboBoxBean.getResult(), getContext());
        mDataBinding.rlDongli.setAdapter(sxDropAdapter);
        sxDropAdapter.setMyListener(new SXDropAdapter.ItemListener() {
            @Override
            public void onItemClick(GetComboBoxBean.ResultBean item) {
                if (resultBeanList.size() > 0) {
                    for (GetComboBoxBean.ResultBean r : getComboBoxBean.getResult()) {
                        if (resultBeanList.contains(r)) {
                            resultBeanList.remove(r);
                        }
                    }
                }
                fix_p_3 = item.getId();
                resultBeanList.add(item);
//                initSelected();
            }
        });
    }

    private void chandou(final GetComboBoxBean getComboBoxBean) {
        mDataBinding.rlChandou.setLayoutManager(new GridLayoutManager(getContext(), 3));
        SXDropAdapter sxDropAdapter = new SXDropAdapter(getComboBoxBean.getResult(), getContext());
        mDataBinding.rlChandou.setAdapter(sxDropAdapter);
        sxDropAdapter.setMyListener(new SXDropAdapter.ItemListener() {
            @Override
            public void onItemClick(GetComboBoxBean.ResultBean item) {
                if (resultBeanList.size() > 0) {
                    for (GetComboBoxBean.ResultBean r : getComboBoxBean.getResult()) {
                        if (resultBeanList.contains(r)) {
                            resultBeanList.remove(r);
                        }
                    }
                }
                fix_p_2 = item.getId();
                resultBeanList.add(item);
//                initSelected();
            }
        });
    }

    private void dunwei(final GetComboBoxBean getComboBoxBean) {
        mDataBinding.rlDunwei.setLayoutManager(new GridLayoutManager(getContext(), 3));
        SXDropAdapter sxDropAdapter = new SXDropAdapter(getComboBoxBean.getResult(), getContext());
        mDataBinding.rlDunwei.setAdapter(sxDropAdapter);
        sxDropAdapter.setMyListener(new SXDropAdapter.ItemListener() {
            @Override
            public void onItemClick(GetComboBoxBean.ResultBean item) {
                if (resultBeanList.size() > 0) {
                    for (GetComboBoxBean.ResultBean r : getComboBoxBean.getResult()) {
                        if (resultBeanList.contains(r)) {
                            resultBeanList.remove(r);
                        }
                    }
                }
                fix_p_1 = item.getId();
                resultBeanList.add(item);
//                initSelected();
            }
        });
    }

    private void closeDrap() {
        mDataBinding.llJixing.setVisibility(View.GONE);
        mDataBinding.llChanshang.setVisibility(View.GONE);
        mDataBinding.llPinpai.setVisibility(View.GONE);
        mDataBinding.llShuaixuan.setVisibility(View.GONE);
    }
}
