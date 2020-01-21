package com.jhhscm.platform.fragment.GoodsToCarts;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.CashierActivity;
import com.jhhscm.platform.activity.LoginActivity;
import com.jhhscm.platform.activity.ReceiveAddressActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.FragmentCreateOrderBinding;
import com.jhhscm.platform.event.AddressResultEvent;
import com.jhhscm.platform.fragment.GoodsToCarts.action.CalculateOrderAction;
import com.jhhscm.platform.fragment.GoodsToCarts.action.CreateOrderAction;
import com.jhhscm.platform.fragment.GoodsToCarts.action.FindAddressListAction;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.UserSession;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

/**
 * 确认订单
 */
public class CreateOrderFragment extends AbsFragment<FragmentCreateOrderBinding> {
    private InnerAdapter mAdapter;
    private List<GetCartGoodsByUserCodeBean.ResultBean> resultBeans;
    private GetCartGoodsByUserCodeBean getCartGoodsByUserCodeBean;
    private UserSession userSession;
    private String selectAddressID;
    private FindAddressListBean findAddressListBean;

    public static CreateOrderFragment instance() {
        CreateOrderFragment view = new CreateOrderFragment();
        return view;
    }

    @Override
    protected FragmentCreateOrderBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentCreateOrderBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        EventBusUtil.registerEvent(this);
        if (ConfigUtils.getCurrentUser(getContext()) != null
                && ConfigUtils.getCurrentUser(getContext()).getMobile() != null) {
            userSession = ConfigUtils.getCurrentUser(getContext());
        } else {
            startNewActivity(LoginActivity.class);
        }
        findAddressListBean = new FindAddressListBean();
        if (getArguments() != null) {
            getCartGoodsByUserCodeBean = (GetCartGoodsByUserCodeBean) getArguments().getSerializable("getCartGoodsByUserCodeBean");
        }
        if (getCartGoodsByUserCodeBean != null) {
            findAddressList();

            mDataBinding.rv.setLayoutManager(new LinearLayoutManager(getContext()));
            mAdapter = new InnerAdapter(getContext());
            mDataBinding.rv.setAdapter(mAdapter);
        }

        mDataBinding.tvTijiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (findAddressListBean != null) {
                    if (selectAddressID != null) {
                        createOrder(ConfigUtils.getCurrentUser(getContext()).getMobile(), getCartGoodsByUserCodeBean, ConfigUtils.getCurrentUser(getContext()).getToken());
                    }
                } else {
                    ToastUtils.show(getContext(), "地址不能为空");
                }
            }
        });

        mDataBinding.rlLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReceiveAddressActivity.start(getContext(), true);
            }
        });
    }

    public void onEvent(AddressResultEvent event) {
        if (event.getResultBean() != null) {
            mDataBinding.tvEmpty.setVisibility(View.GONE);
            mDataBinding.tvName.setText(event.getResultBean().getName());
            mDataBinding.tvTel.setText(event.getResultBean().getTel());
            selectAddressID = event.getResultBean().getId();
            if (event.getResultBean().getIs_default().equals("1")) {
                mDataBinding.tvDefault.setText("默认");
                mDataBinding.tvDefault.setVisibility(View.VISIBLE);
                mDataBinding.tvAddress.setText("           " + event.getResultBean().getAddress_detail());
            } else {
                mDataBinding.tvDefault.setVisibility(View.GONE);
                mDataBinding.tvAddress.setText(event.getResultBean().getAddress_detail());
            }
            for (GetCartGoodsByUserCodeBean.ResultBean r : getCartGoodsByUserCodeBean.getResult()) {
                calculateOrder(r);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }

    /**
     * 获取地址列表
     */
    private void findAddressList() {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("user_code", ConfigUtils.getCurrentUser(getContext()).getUserCode());
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getActivity(), map, "findAddressList");
        NetBean netBean = new NetBean();
        netBean.setToken(ConfigUtils.getCurrentUser(getContext()).getToken());
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(FindAddressListAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<FindAddressListBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<FindAddressListBean>> response,
                                           BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                if (response.body().getCode().equals("200")) {
                                    findAddressListBean = response.body().getData();
                                    initAddress();
                                    Log.e("findAddressList", "获取地址列表成功");
                                } else {
                                    ToastUtils.show(getContext(), "请求错误，请联系管理员");
                                }
                            }
                        }
                    }
                }));
    }

    private void initAddress() {
        if (findAddressListBean.getResult().getData() != null
                && findAddressListBean.getResult().getData().size() > 0) {
            mDataBinding.tvEmpty.setVisibility(View.GONE);
            FindAddressListBean.ResultBean.DataBean showAddress = null;
            for (FindAddressListBean.ResultBean.DataBean dataBean : findAddressListBean.getResult().getData()) {
                if (dataBean.getIs_default().equals("1")) {//默认地址湱
                    showAddress = dataBean;
                }
            }
            if (showAddress == null) {
                showAddress = findAddressListBean.getResult().getData().get(0);
            }
            mDataBinding.tvName.setText(showAddress.getName());
            mDataBinding.tvTel.setText(showAddress.getTel());
            selectAddressID = showAddress.getId();
            if (showAddress.getIs_default().equals("1")) {
                mDataBinding.tvDefault.setText("默认");
                mDataBinding.tvDefault.setVisibility(View.VISIBLE);
                mDataBinding.tvAddress.setText("           " + showAddress.getAddress_detail());
            } else {
                mDataBinding.tvDefault.setVisibility(View.GONE);
                mDataBinding.tvAddress.setText(showAddress.getAddress_detail());
            }

            for (GetCartGoodsByUserCodeBean.ResultBean r : getCartGoodsByUserCodeBean.getResult()) {
                calculateOrder(r);
            }
        } else {
            mDataBinding.tvEmpty.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 计算订单和运费
     */
    private void calculateOrder(final GetCartGoodsByUserCodeBean.ResultBean resultBean) {
        String goodsCode = "";
        String goodsList = "";
        Map<String, Integer> gMap = new TreeMap<String, Integer>();
        for (GetCartGoodsByUserCodeBean.ResultBean.GoodsListBean goodsListBean : resultBean.getGoodsList()) {
            if (goodsListBean.isIscheck()) {
                if (goodsCode.length() > 0) {
                    goodsCode = goodsCode + "," + goodsListBean.getGoodsCode();
                } else {
                    goodsCode = goodsListBean.getGoodsCode();
                }
                gMap.put(goodsListBean.getGoodsCode(), goodsListBean.getNumber());
            }
        }

        goodsList = JSON.toJSONString(gMap);

        showDialog();
        Map<String, String> map = new TreeMap<String, String>();
        map.put("goodsCode", goodsCode);
        map.put("goodsList", goodsList);
        map.put("mobile", ConfigUtils.getCurrentUser(getContext()).getMobile());
        map.put("addressId", selectAddressID);
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getActivity(), map, "s");
        NetBean netBean = new NetBean();
        netBean.setToken(ConfigUtils.getCurrentUser(getContext()).getToken());
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(CalculateOrderAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<CalculateOrderBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<CalculateOrderBean>> response,
                                           BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                if (response.body().getCode().equals("200")) {
                                    if (response.body().getData().getData() != null) {
                                        resultBean.setFreight_price(response.body().getData().getData().getFreight_price());
                                    }
                                    initView();
                                } else {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
    }

    private void initView() {
        double count = 0;//金额
        int num = 0;//数量
        double yufei = 0.0;//运费
        double youhui = 0.0;//优惠
        for (GetCartGoodsByUserCodeBean.ResultBean bean : getCartGoodsByUserCodeBean.getResult()) {
            double itemTotal = 0;//小计

            for (GetCartGoodsByUserCodeBean.ResultBean.GoodsListBean goodsListBean : bean.getGoodsList()) {
                if (goodsListBean.getNumber() > 0) {
                    count = count + Double.parseDouble(goodsListBean.getPrice()) * Double.parseDouble(goodsListBean.getNumber() + "");
                    num = num + goodsListBean.getNumber();
                    itemTotal = itemTotal + Double.parseDouble(goodsListBean.getPrice()) * Double.parseDouble(goodsListBean.getNumber() + "");
                }
            }
            if (bean.getFreight_price() != null) {
                yufei = yufei + Double.parseDouble(bean.getFreight_price());
                itemTotal = itemTotal + Double.parseDouble(bean.getFreight_price());
            }
            bean.setSum(itemTotal + "");
        }

        double total = count + yufei - youhui;//合计
        mDataBinding.tvPrice.setText("￥" + count);
        mDataBinding.tvYouhui.setText("-￥" + youhui);
        mDataBinding.rlCoupon.setVisibility(View.GONE);
        mDataBinding.tvQuanxuan.setText("共计" + num + "件商品");
        BigDecimal b = new BigDecimal(total);
        mDataBinding.tvSum.setText("￥" + b.setScale(2, BigDecimal.ROUND_HALF_UP).toString());

        mAdapter.setData(getCartGoodsByUserCodeBean.getResult());
        mAdapter.notifyDataSetChanged();
    }

    private class InnerAdapter extends AbsRecyclerViewAdapter<GetCartGoodsByUserCodeBean.ResultBean> {
        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<GetCartGoodsByUserCodeBean.ResultBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CreateOrderItemViewHolder(mInflater.inflate(R.layout.item_create_order_list, parent, false));
        }
    }

    /**
     * 创建订单
     */
    private void createOrder(final String mobile, GetCartGoodsByUserCodeBean getCartGoodsByUserCodeBean, final String token) {
        String goodsCode = "";
        String goodsList = "";
        Map<String, Integer> gMap = new TreeMap<String, Integer>();
        for (GetCartGoodsByUserCodeBean.ResultBean r : getCartGoodsByUserCodeBean.getResult()) {
            for (GetCartGoodsByUserCodeBean.ResultBean.GoodsListBean goodsListBean : r.getGoodsList()) {
                if (goodsCode.length() > 0) {
                    goodsCode = goodsCode + "," + goodsListBean.getGoodsCode();
                } else {
                    goodsCode = goodsListBean.getGoodsCode();
                }
                gMap.put(goodsListBean.getGoodsCode(), goodsListBean.getNumber());

            }
        }
        goodsList = JSON.toJSONString(gMap);

        Map<String, String> map = new TreeMap<String, String>();
        map.put("goodsCode", goodsCode);
        map.put("goodsList", goodsList);
        map.put("mobile", mobile);
        map.put("addressId", selectAddressID);
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getActivity(), map, "createOrder");
        NetBean netBean = new NetBean();
        netBean.setToken(token);
        netBean.setSign(sign);
        netBean.setContent(content);

        onNewRequestCall(CreateOrderAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<CreateOrderResultBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<CreateOrderResultBean>>
                            response,
                                           BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                if (response.body().getCode().equals("200")) {
                                    CashierActivity.start(getContext(), response.body().getData());
                                    ToastUtils.show(getContext(), "创建订单成功");
                                    getActivity().finish();
                                } else {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
    }


}
