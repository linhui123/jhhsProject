package com.jhhscm.platform.fragment.GoodsToCarts;


import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.CreateOrderActivity;
import com.jhhscm.platform.activity.LoginActivity;
import com.jhhscm.platform.databinding.FragmentGoodsToCartsBinding;

import com.jhhscm.platform.event.GetRegionEvent;
import com.jhhscm.platform.fragment.GoodsToCarts.action.CreateOrderAction;
import com.jhhscm.platform.fragment.GoodsToCarts.action.DelGoodsToCartsAction;
import com.jhhscm.platform.fragment.GoodsToCarts.action.GetCartGoodsByUserCodeAction;
import com.jhhscm.platform.fragment.GoodsToCarts.action.UpdateGoodsToCartsAction;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.ResultBean;
import com.jhhscm.platform.http.bean.UserSession;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.http.sign.SignObject;
import com.jhhscm.platform.shoppingcast.adapter.CartExpandAdapter;
import com.jhhscm.platform.shoppingcast.callback.OnClickAddCloseListenter;
import com.jhhscm.platform.shoppingcast.callback.OnClickDeleteListenter;
import com.jhhscm.platform.shoppingcast.callback.OnClickListenterModel;
import com.jhhscm.platform.shoppingcast.callback.OnViewItemClickListener;
import com.jhhscm.platform.shoppingcast.entity.CartInfo;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.DisplayUtils;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.recyclerview.DividerItemStrokeDecoration;
import com.jhhscm.platform.views.slideswaphelper.PlusItemSlideCallback;
import com.jhhscm.platform.views.slideswaphelper.WItemTouchHelperPlus;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;


/**
 * 购物车
 */
public class GoodsToCartsFragment extends AbsFragment<FragmentGoodsToCartsBinding> {
    private List<GetCartGoodsByUserCodeBean.ResultBean> list;
    private boolean total = false;//全选
    private GetCartGoodsByUserCodeBean getCartGoodsByUserCodeBean;
    private CartExpandAdapter cartExpandAdapter;
    private double price;
    private int num;

    public static GoodsToCartsFragment instance() {
        GoodsToCartsFragment view = new GoodsToCartsFragment();
        return view;
    }

    @Override
    protected FragmentGoodsToCartsBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentGoodsToCartsBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        EventBusUtil.registerEvent(this);

        if (ConfigUtils.getCurrentUser(getContext()) != null
                && ConfigUtils.getCurrentUser(getContext()).getUserCode() != null
                && ConfigUtils.getCurrentUser(getContext()).getMobile() != null) {
        } else {
            startNewActivity(LoginActivity.class);
        }

        getCartGoodsByUserCode();
        initBottom();
    }

    private void initBottom() {
        mDataBinding.imQuanxuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (total) {
                    if (getCartGoodsByUserCodeBean != null) {
                        for (GetCartGoodsByUserCodeBean.ResultBean r : getCartGoodsByUserCodeBean.getResult()) {
                            r.setIscheck(false);
                            for (GetCartGoodsByUserCodeBean.ResultBean.GoodsListBean goodsListBean : r.getGoodsList()) {
                                goodsListBean.setIscheck(false);
                            }

                        }
                        initData(getCartGoodsByUserCodeBean);
                    }
                    total = false;
                    showCommodityCalculation();
                } else {
                    if (getCartGoodsByUserCodeBean != null) {
                        for (GetCartGoodsByUserCodeBean.ResultBean r : getCartGoodsByUserCodeBean.getResult()) {
                            r.setIscheck(true);
                            for (GetCartGoodsByUserCodeBean.ResultBean.GoodsListBean goodsListBean : r.getGoodsList()) {
                                goodsListBean.setIscheck(true);
                            }

                        }
                        initData(getCartGoodsByUserCodeBean);
                    }
                    total = true;
                    showCommodityCalculation();
                }
            }
        });

        mDataBinding.tvJiesuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<GetCartGoodsByUserCodeBean.ResultBean> resultBeans = new ArrayList<>();
                if (getCartGoodsByUserCodeBean != null) {
                    for (GetCartGoodsByUserCodeBean.ResultBean r : getCartGoodsByUserCodeBean.getResult()) {
                        List<GetCartGoodsByUserCodeBean.ResultBean.GoodsListBean> list = new ArrayList<>();
                        GetCartGoodsByUserCodeBean.ResultBean resultBean = new GetCartGoodsByUserCodeBean.ResultBean();
                        for (GetCartGoodsByUserCodeBean.ResultBean.GoodsListBean goodsListBean : r.getGoodsList()) {
                            if (goodsListBean.isIscheck()) {
                                list.add(goodsListBean);
                            }
                        }
                        if (list.size() > 0) {
                            resultBean.setGoodsList(list);
                            resultBeans.add(resultBean);
                        }
                    }
                    initData(getCartGoodsByUserCodeBean);
                }

                if (resultBeans != null && resultBeans.size() > 0) {
                    GetCartGoodsByUserCodeBean g = new GetCartGoodsByUserCodeBean();
                    g.setResult(resultBeans);
                    CreateOrderActivity.start(getContext(), g);
                    getActivity().finish();
                } else {
                    ToastUtil.show(getContext(), "请先选择商品");
                }
            }
        });
    }

    /**
     * 更新地区选择
     */
    public void onEvent(GetRegionEvent event) {
    }

    /**
     * 获取用户购物车列表
     */
    private void getCartGoodsByUserCode() {
        showDialog();
        Map<String, String> map = new TreeMap<String, String>();
        map.put("userCode", ConfigUtils.getCurrentUser(getContext()).getUserCode());
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getActivity(), map, "getCartGoodsByUserCode");
        NetBean netBean = new NetBean();
        netBean.setToken(ConfigUtils.getCurrentUser(getContext()).getToken());
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(GetCartGoodsByUserCodeAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<GetCartGoodsByUserCodeBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<GetCartGoodsByUserCodeBean>> response,
                                           BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                if (response.body().getCode().equals("200")) {
                                    getCartGoodsByUserCodeBean = response.body().getData();
                                    initData(response.body().getData());
                                } else {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
    }

    private void initData(GetCartGoodsByUserCodeBean resultBeans) {
        this.getCartGoodsByUserCodeBean = resultBeans;
        mDataBinding.cartExpandablelistview.setGroupIndicator(null);
        if (getCartGoodsByUserCodeBean != null && getCartGoodsByUserCodeBean.getResult().size() > 0) {
            cartExpandAdapter = null;
            showExpandData();
        } else {
            try {
                cartExpandAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                return;
            }
        }
    }

    private void showExpandData() {
        cartExpandAdapter = new CartExpandAdapter(getContext(), mDataBinding.cartExpandablelistview,
                getCartGoodsByUserCodeBean.getResult());
        mDataBinding.cartExpandablelistview.setAdapter(cartExpandAdapter);
        int intgroupCount = mDataBinding.cartExpandablelistview.getCount();
        for (int i = 0; i < intgroupCount; i++) {
            mDataBinding.cartExpandablelistview.expandGroup(i);
        }
        /**
         * 全选
         */
        cartExpandAdapter.setOnItemClickListener(new OnViewItemClickListener() {
            @Override
            public void onItemClick(boolean isFlang, View view, int position) {
                getCartGoodsByUserCodeBean.getResult().get(position).setIscheck(isFlang);
                int length = getCartGoodsByUserCodeBean.getResult().get(position).getGoodsList().size();
                for (int i = 0; i < length; i++) {
                    getCartGoodsByUserCodeBean.getResult().get(position).getGoodsList().get(i).setIscheck(isFlang);
                }
                cartExpandAdapter.notifyDataSetChanged();
                showCommodityCalculation();
            }
        });

        /**
         * 单选
         */
        cartExpandAdapter.setOnClickListenterModel(new OnClickListenterModel() {
            @Override
            public void onItemClick(boolean isFlang, View view, int onePosition, int position) {
                Log.e("onItemClick", "isFlang " + isFlang);
                getCartGoodsByUserCodeBean.getResult().get(onePosition).getGoodsList().get(position).setIscheck(isFlang);

                int length = getCartGoodsByUserCodeBean.getResult().get(onePosition).getGoodsList().size();
                for (int i = 0; i < length; i++) {
                    if (!getCartGoodsByUserCodeBean.getResult().get(onePosition).getGoodsList().get(i).isIscheck()) {//false
                        if (!isFlang) {
                            getCartGoodsByUserCodeBean.getResult().get(onePosition).setIscheck(isFlang);
                        }
                        cartExpandAdapter.notifyDataSetChanged();
                        showCommodityCalculation();
                        return;
                    } else {
                        if (i == (length - 1)) {
                            getCartGoodsByUserCodeBean.getResult().get(onePosition).setIscheck(isFlang);
                            cartExpandAdapter.notifyDataSetChanged();
                        }
                    }
                }
                showCommodityCalculation();
            }
        });
        cartExpandAdapter.setOnClickDeleteListenter(new OnClickDeleteListenter() {
            @Override
            public void onItemClick(View view, int onePosition, int position) {
                delGoodsToCarts(onePosition, position,
                        getCartGoodsByUserCodeBean.getResult().get(onePosition).getGoodsList().get(position).getGoodsCode());
            }
        });

        /***
         * 数量增加和减少
         */
        cartExpandAdapter.setOnClickAddCloseListenter(new OnClickAddCloseListenter() {
            @Override
            public void onItemClick(View view, int index, int onePosition, int position, int num) {
                if (index == 1) {
                    if (num > 1) {
                        getCartGoodsByUserCodeBean.getResult().get(onePosition).getGoodsList().get(position).setNumber((num - 1));
                        cartExpandAdapter.notifyDataSetChanged();
                    }
                } else {
                    getCartGoodsByUserCodeBean.getResult().get(onePosition).getGoodsList().get(position).setNumber((num + 1));
                    cartExpandAdapter.notifyDataSetChanged();
                }
                showCommodityCalculation();
                updateGoodsToCarts(getCartGoodsByUserCodeBean.getResult().get(onePosition).getGoodsList().get(position));
            }
        });
        showCommodityCalculation();
    }

    private void showCommodityCalculation() {
        //判断是否全选
        boolean all = true;
        for (GetCartGoodsByUserCodeBean.ResultBean resultBean : getCartGoodsByUserCodeBean.getResult()) {
            boolean allGroup = true;
            for (GetCartGoodsByUserCodeBean.ResultBean.GoodsListBean goodsListBean : resultBean.getGoodsList()) {
                if (!goodsListBean.isIscheck()) {
                    allGroup = false;
                }
            }
            resultBean.setIscheck(allGroup);
            if (!resultBean.isIscheck()) {
                all = false;
            }
        }
        if (all) {
            total = true;
            mDataBinding.imQuanxuan.setImageResource(R.mipmap.ic_shoping_s1);
        } else {
            total = false;
            mDataBinding.imQuanxuan.setImageResource(R.mipmap.ic_shoping_s);
        }
        cartExpandAdapter.notifyDataSetChanged();

        price = 0;
        num = 0;
        for (int i = 0; i < getCartGoodsByUserCodeBean.getResult().size(); i++) {
            for (int j = 0; j < getCartGoodsByUserCodeBean.getResult().get(i).getGoodsList().size(); j++) {
                if (getCartGoodsByUserCodeBean.getResult().get(i).getGoodsList().get(j).isIscheck()) {
                    price += Double.valueOf((getCartGoodsByUserCodeBean.getResult().get(i).getGoodsList().get(j).getNumber() *
                            Double.valueOf(getCartGoodsByUserCodeBean.getResult().get(i).getGoodsList().get(j).getPrice())));
                    num++;
                }
            }
        }
        if (price == 0.0) {
//            mDataBinding.cartNum.setText("共0件商品");
            mDataBinding.tvSum.setText("¥ 0.0");
            return;
        }
        try {
            String money = String.valueOf(price);
//            cartNum.setText("共" + num + "件商品");
            if (money.substring(money.indexOf("."), money.length()).length() > 2) {
                mDataBinding.tvSum.setText("¥ " + money.substring(0, (money.indexOf(".") + 3)));
                return;
            }
            mDataBinding.tvSum.setText("¥ " + money.substring(0, (money.indexOf(".") + 2)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除用户购物车
     */
    private void delGoodsToCarts(final int onePosition, final int position, String goodsCode) {
        showDialog();
        Map<String, String> map = new TreeMap<String, String>();
        map.put("userCode", ConfigUtils.getCurrentUser(getContext()).getUserCode());
        map.put("goodsCode", goodsCode);
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getActivity(), map, "delGoodsToCarts");
        NetBean netBean = new NetBean();
        netBean.setToken(ConfigUtils.getCurrentUser(getContext()).getToken());
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(DelGoodsToCartsAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<ResultBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<ResultBean>> response,
                                           BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                if (response.body().getCode().equals("200")) {
                                    if (getCartGoodsByUserCodeBean.getResult().get(onePosition).getGoodsList().size() == 1) {
                                        getCartGoodsByUserCodeBean.getResult().get(onePosition).getGoodsList().remove(position);
                                        getCartGoodsByUserCodeBean.getResult().remove(onePosition);
                                    } else if (getCartGoodsByUserCodeBean.getResult().get(onePosition).getGoodsList().size() > 1) {
                                        getCartGoodsByUserCodeBean.getResult().get(onePosition).getGoodsList().remove(position);
                                    }

                                    showCommodityCalculation();
                                } else {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
    }

    /**
     * 修改用户购物车
     */
    private void updateGoodsToCarts(GetCartGoodsByUserCodeBean.ResultBean.GoodsListBean resultBean) {
        showDialog();
        Map<String, Object> map = new TreeMap<String, Object>();
        map.put("userCode", ConfigUtils.getCurrentUser(getContext()).getUserCode());
        map.put("goodsCode", resultBean.getGoodsCode());
        map.put("number", resultBean.getNumber());
        map.put("id", resultBean.getId());
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = SignObject.getSignKey(getActivity(), map, "updateGoodsToCarts");
        NetBean netBean = new NetBean();
        netBean.setToken(ConfigUtils.getCurrentUser(getContext()).getToken());
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(UpdateGoodsToCartsAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<ResultBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<ResultBean>> response,
                                           BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                if (response.body().getCode().equals("200")) {
                                    Log.e("updateGoodsToCarts", "修改信息成功");
                                } else {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }
}
