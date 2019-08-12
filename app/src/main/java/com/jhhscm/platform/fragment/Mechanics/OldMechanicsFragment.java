package com.jhhscm.platform.fragment.Mechanics;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.FragmentNewMechanicsBinding;
import com.jhhscm.platform.databinding.FragmentOldMechanicsBinding;
import com.jhhscm.platform.event.GetRegionEvent;
import com.jhhscm.platform.fragment.Mechanics.action.FindBrandAction;
import com.jhhscm.platform.fragment.Mechanics.action.GetComboBoxAction;
import com.jhhscm.platform.fragment.Mechanics.action.GetGoodsPageListAction;
import com.jhhscm.platform.fragment.Mechanics.action.GetOldPageListAction;
import com.jhhscm.platform.fragment.Mechanics.adapter.BrandAdapter;
import com.jhhscm.platform.fragment.Mechanics.adapter.JXDropAdapter;
import com.jhhscm.platform.fragment.Mechanics.adapter.SXDropAdapter;
import com.jhhscm.platform.fragment.Mechanics.adapter.SelectedAdapter;
import com.jhhscm.platform.fragment.Mechanics.bean.FindBrandBean;
import com.jhhscm.platform.fragment.Mechanics.bean.GetComboBoxBean;
import com.jhhscm.platform.fragment.Mechanics.bean.GetGoodsPageListBean;
import com.jhhscm.platform.fragment.Mechanics.bean.GetOldPageListBean;
import com.jhhscm.platform.fragment.Mechanics.bean.GetRegionBean;
import com.jhhscm.platform.fragment.Mechanics.holder.NewMechanicsViewHolder;
import com.jhhscm.platform.fragment.Mechanics.holder.OldMechanicsViewHolder;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.home.HomePageAdapter;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.bottommenu.BottomMenuShow;
import com.jhhscm.platform.views.bottommenu.bean.MenuData;
import com.jhhscm.platform.views.recyclerview.DividerItemStrokeDecoration;
import com.jhhscm.platform.views.recyclerview.WrappedRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

/**
 * 二手机列表
 */
public class OldMechanicsFragment extends AbsFragment<FragmentOldMechanicsBinding> {
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
    private String old_sort = "";
    String pID = "";
    String cID = "";

    public static OldMechanicsFragment instance() {
        OldMechanicsFragment view = new OldMechanicsFragment();
        return view;
    }

    @Override
    protected FragmentOldMechanicsBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentOldMechanicsBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        EventBusUtil.registerEvent(this);
        mDataBinding.wrvRecycler.addItemDecoration(new DividerItemStrokeDecoration(getContext()));
        mDataBinding.wrvRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new InnerAdapter(getContext());
        mDataBinding.wrvRecycler.setAdapter(mAdapter);
        mDataBinding.wrvRecycler.autoRefresh();
        mDataBinding.wrvRecycler.setOnPullListener(new WrappedRecyclerView.OnPullListener() {
            @Override
            public void onRefresh(RecyclerView view) {
                getOldPageList(true);
            }

            @Override
            public void onLoadMore(RecyclerView view) {
                getOldPageList(false);
            }
        });

        resultBeanList = new ArrayList<>();
        initDrop();

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

        mDataBinding.tvPaixu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDataBinding.llXiala.getVisibility() == View.GONE) {
                    mDataBinding.llXiala.setVisibility(View.VISIBLE);
                    mDataBinding.llPaixu.setVisibility(View.VISIBLE);
                } else {
                    if (mDataBinding.llPaixu.getVisibility() == View.VISIBLE) {
                        mDataBinding.llXiala.setVisibility(View.GONE);
                        closeDrap();
                    } else {
                        closeDrap();
                        mDataBinding.llPaixu.setVisibility(View.VISIBLE);
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
     * 更新地区选择
     */
    public void onEvent(GetRegionEvent event) {
        if (event.pid != null && event.type != null) {
            if (event.type.equals("1")) {//省点击，获取市
                pID = event.pid;
            } else if (event.type.equals("2")) {//市点击
                cID = event.pid;
            }
            mDataBinding.wrvRecycler.autoRefresh();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }

    /**
     * 获取新机列表
     */
    private void getOldPageList(final boolean refresh) {
        if (getContext() != null) {
            mCurrentPage = refresh ? START_PAGE : ++mCurrentPage;
            Map<String, String> map = new TreeMap<String, String>();
            map.put("keyword", "");
            map.put("old_sort", old_sort);//小时数
            map.put("fix_p_3", fix_p_3);
            map.put("fix_p_2", fix_p_2);
            map.put("merchant_id", merchant_id);
            map.put("fix_p_9", fix_p_9);
            map.put("brand_id", brand_id);
//            ArrayList<Integer> list = new ArrayList<>();
//            if (merchant_id != null && merchant_id.length() > 0) {
//                list.add(Integer.parseInt(merchant_id));
//                map.put("merchant_id", JSON.toJSONString(list));
//            }
//            list.clear();
//            if (fix_p_9 != null && fix_p_9.length() > 0) {
//                list.add(Integer.parseInt(fix_p_9));
//                map.put("fix_p_9", JSON.toJSONString(list));
//            }
//            list.clear();
//            if (brand_id != null && brand_id.length() > 0) {
//                list.add(Integer.parseInt(brand_id));
//                map.put("brand_id", JSON.toJSONString(list));
//            }
            map.put("fix_p_1", fix_p_1);
            map.put("page", mCurrentPage + "");
            map.put("limit", mShowCount + "");
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = Sign.getSignKey(getActivity(), map, "getGoodsPageList");
            NetBean netBean = new NetBean();
            netBean.setToken("");
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(GetOldPageListAction.newInstance(getContext(), netBean)
                    .request(new AHttpService.IResCallback<BaseEntity<GetOldPageListBean>>() {
                        @Override
                        public void onCallback(int resultCode, Response<BaseEntity<GetOldPageListBean>> response,
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
                                        mDataBinding.wrvRecycler.loadComplete(true, false);
                                        ToastUtils.show(getContext(), response.body().getMessage());
                                    }
                                }
                            }
                        }
                    }));
        }
    }

    private GetOldPageListBean getOldPageListBean;

    private void doSuccessResponse(boolean refresh, GetOldPageListBean getOldPageList) {
        this.getOldPageListBean = getOldPageList;
        if (refresh) {
            mAdapter.setData(getOldPageList.getData());
        } else {
            mAdapter.append(getOldPageList.getData());
        }
        mDataBinding.wrvRecycler.getAdapter().notifyDataSetChanged();
        mDataBinding.wrvRecycler.loadComplete(mAdapter.getItemCount() == 0, ((float) getOldPageListBean.getPage().getTotal() / (float) getOldPageListBean.getPage().getPageSize()) > mCurrentPage);
    }

    private class InnerAdapter extends AbsRecyclerViewAdapter<GetOldPageListBean.DataBean> {
        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<GetOldPageListBean.DataBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new OldMechanicsViewHolder(mInflater.inflate(R.layout.item_mechanics_old, parent, false));
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
        getComboBox("old_sort");
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
                                    } else if ("old_sort".equals(name)) {
                                        paixu(response.body().getData());
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
        selectedAdapter.setMyListener(new SelectedAdapter.ItemListener() {
            @Override
            public void onItemClick(GetComboBoxBean.ResultBean item) {
                resultBeanList.remove(item);
                selectedAdapter.notifyDataSetChanged();
                mDataBinding.wrvRecycler.autoRefresh();
            }
        });
        mDataBinding.llXiala.setVisibility(View.GONE);
        closeDrap();
        mDataBinding.wrvRecycler.autoRefresh();
    }


    /**
     * 下拉机型
     */
    private void jixing(GetComboBoxBean getComboBoxBean) {
        mDataBinding.rlJixing.setLayoutManager(new LinearLayoutManager(getContext()));
        JXDropAdapter JXAdapter = new JXDropAdapter(getComboBoxBean.getResult(), getContext());
        mDataBinding.rlJixing.setAdapter(JXAdapter);
        JXAdapter.setMyListener(new JXDropAdapter.ItemListener() {
            @Override
            public void onItemClick(GetComboBoxBean.ResultBean item) {
                fix_p_9 = item.getKey_name();
                mDataBinding.tvJixing.setText(item.getKey_value());
                mDataBinding.llXiala.setVisibility(View.GONE);
                closeDrap();
                mDataBinding.wrvRecycler.autoRefresh();
            }
        });
    }

    /**
     * 下拉排序
     */
    private void paixu(GetComboBoxBean getComboBoxBean) {
        mDataBinding.rlPaixu.setLayoutManager(new LinearLayoutManager(getContext()));
        JXDropAdapter JXAdapter = new JXDropAdapter(getComboBoxBean.getResult(), getContext());
        mDataBinding.rlPaixu.setAdapter(JXAdapter);
        JXAdapter.setMyListener(new JXDropAdapter.ItemListener() {
            @Override
            public void onItemClick(GetComboBoxBean.ResultBean item) {
                old_sort = item.getKey_name();
                mDataBinding.tvPaixu.setText(item.getKey_value());
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
                fix_p_3 = item.getKey_name();
                resultBeanList.add(item);
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
                fix_p_2 = item.getKey_name();
                resultBeanList.add(item);
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
                fix_p_1 = item.getKey_name();
                resultBeanList.add(item);
            }
        });
    }

    private void closeDrap() {
        mDataBinding.llJixing.setVisibility(View.GONE);
        mDataBinding.llPaixu.setVisibility(View.GONE);
        mDataBinding.llPinpai.setVisibility(View.GONE);
        mDataBinding.llShuaixuan.setVisibility(View.GONE);
    }

    GetRegionBean getRegionBean;

    private void showArea() {
        if (getRegionBean != null) {
            BottomMenuShow bottomMenuShow = new BottomMenuShow(getContext());
            final ArrayList<MenuData> menuDatas = new ArrayList<>();
//            for (int i = 0; i < mGetSourceEntity.getPROVINCE().size(); i++) {
//                MenuData menuData = new MenuData();
//                menuData.setmSecondary(true);
//                menuData.setmText(mGetSourceEntity.getPROVINCE().get(i).getNAME());
//                ArrayList<String> arrayList = new ArrayList<>();
//                for (int j = 0; j < mGetSourceEntity.getPROVINCE().get(i).getCITY().size(); j++) {
//                    arrayList.add(mGetSourceEntity.getPROVINCE().get(i).getCITY().get(j).getNAME());
//                }
//                menuData.setmData(arrayList);
//                menuDatas.add(menuData);
//            }

            bottomMenuShow.bottomMenuAlertDialog("", menuDatas);
            bottomMenuShow.setOnBottomMenuListener(new BottomMenuShow.OnBottomMenuListener() {
                @Override
                public void onClicklistener(MenuData data) {
//                    mArge = menuDatas.get(data.getId()).getmText() + "  " + menuDatas.get(data.getId()).getmData().get(data.getIds());
//                    mProvinceId = mGetSourceEntity.getPROVINCE().get(data.getId()).getAREA_ID();
//                    mCityId = mGetSourceEntity.getPROVINCE().get(data.getId()).getCITY().get(data.getIds()).getAREA_ID();
//                    mDataBinding.tvsArea.setText(mArge);
//                    judgeButton();
                }
            });
        } else {
//            getSourece();
        }
    }
}
