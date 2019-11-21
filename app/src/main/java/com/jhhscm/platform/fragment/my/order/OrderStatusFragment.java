package com.jhhscm.platform.fragment.my.order;

import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.jhhscm.platform.BuildConfig;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.GoodsToCartsActivity;
import com.jhhscm.platform.activity.LoginActivity;
import com.jhhscm.platform.databinding.FragmentOrderStatusBinding;
import com.jhhscm.platform.event.AddressResultEvent;
import com.jhhscm.platform.event.OrderCancleEvent;
import com.jhhscm.platform.event.OrderConfirmEvent;
import com.jhhscm.platform.event.PayEvent;
import com.jhhscm.platform.fragment.Mechanics.bean.GetComboBoxBean;
import com.jhhscm.platform.fragment.Mechanics.bean.GetGoodsPageListBean;
import com.jhhscm.platform.fragment.aftersale.FindBusListBean;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.coupon.CouponListAction;
import com.jhhscm.platform.fragment.coupon.CouponListBean;
import com.jhhscm.platform.fragment.sale.FindOrderAction;
import com.jhhscm.platform.fragment.sale.FindOrderBean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.ResultBean;
import com.jhhscm.platform.http.bean.UserSession;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.http.sign.SignObject;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.dialog.ConfirmOrderDialog;
import com.jhhscm.platform.views.dialog.ConfirmOrderDialog.CallbackListener;
import com.jhhscm.platform.views.dialog.NewCouponListDialog;
import com.jhhscm.platform.views.dialog.PayWithCouponDialog;
import com.jhhscm.platform.views.recyclerview.WrappedRecyclerView;

import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

public class OrderStatusFragment extends AbsFragment<FragmentOrderStatusBinding> {
    private UserSession userSession;
    private MyPeiJianListAdapter mAdapter;
    private int mShowCount = 10;
    private int mCurrentPage = 1;
    private final int START_PAGE = mCurrentPage;

    private String type = "";

    private List<GetComboBoxBean.ResultBean> list;
    private FindOrderListBean findOrderListBean;

    public static OrderStatusFragment instance(String type) {
        OrderStatusFragment view = new OrderStatusFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        view.setArguments(bundle);
        return view;
    }

    @Override
    protected FragmentOrderStatusBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentOrderStatusBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        EventBusUtil.registerEvent(this);
        type = getArguments().getString("type");
        initView();
    }

    private void initView() {
        if (ConfigUtils.getCurrentUser(getContext()) != null
                && ConfigUtils.getCurrentUser(getContext()).getMobile() != null) {
            userSession = ConfigUtils.getCurrentUser(getContext());
        } else {
            startNewActivity(LoginActivity.class);
        }
        getCouponList();
        mDataBinding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new MyPeiJianListAdapter(getContext());
        mDataBinding.recyclerview.setAdapter(mAdapter);
        mDataBinding.recyclerview.autoRefresh();
        mDataBinding.recyclerview.setOnPullListener(new WrappedRecyclerView.OnPullListener() {
            @Override
            public void onRefresh(RecyclerView view) {
//                if (type.equals("")) {
                findOrderList3(true, type);
//                }
            }

            @Override
            public void onLoadMore(RecyclerView view) {
                findOrderList3(false, type);
            }
        });

        mDataBinding.tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsToCartsActivity.start(getContext());
            }
        });
//        String jsonString1 = "{\"data\":[{\"goods_price\":1637.00,\"is_payframe\":\"0\",\"order_price\":2237.00,\"bus_pic_url\":\"[{\\\"name\\\":\\\"http://wajueji.oss-cn-shenzhen.aliyuncs.com/business/78ff88cf31fa418296137eab90b46f1b.jpg?Expires=1889408613&OSSAccessKeyId=LTAI4F3Gt8M6rbEl&Signature=fNtWiExSqb%2FdFdD0CMk3xQ%2BLQFI%3D\\\",\\\"url\\\":\\\"http://wajueji.oss-cn-shenzhen.aliyuncs.com/business/78ff88cf31fa418296137eab90b46f1b.jpg?Expires=1889408613&OSSAccessKeyId=LTAI4F3Gt8M6rbEl&Signature=fNtWiExSqb%2FdFdD0CMk3xQ%2BLQFI%3D\\\",\\\"uid\\\":1574048527532,\\\"status\\\":\\\"success\\\"}]\",\"order_text\":\"未付款\",\"message\":\"bus\",\"other_price\":600.00,\"order_name\":\"700柴滤\",\"order_code\":\"2019111960002087639117\",\"order_status\":101,\"user_code\":\"2019111860000706486737\",\"goodsOwnerList\":[{\"fixs\":\"002\",\"brands\":\"日立\",\"v3s\":\"123456\"},{\"fixs\":\"002\",\"brands\":\"日立\",\"v3s\":\"123456\"}],\"order_num\":3,\"id\":481,\"pic_url\":\"http://wajueji.oss-cn-shenzhen.aliyuncs.com/category_water/4557fac8c5b7439d86ca1fe6c99ed453.png?Expires=1886145360&OSSAccessKeyId=LTAI4F3Gt8M6rbEl&Signature=svc9Bv5UHJ%2FKQpQSI0VW29A6XtI%3D\",\"order_type\":1,\"add_time\":\"2019-11-19 18:52:11\",\"bus_name\":\"18030129694\",\"bus_code\":\"2019111860000706486737\"},{\"goods_price\":71.00,\"is_payframe\":\"0\",\"order_price\":171.00,\"bus_pic_url\":\"[{\\\"name\\\":\\\"http://wajueji.oss-cn-shenzhen.aliyuncs.com/business/78ff88cf31fa418296137eab90b46f1b.jpg?Expires=1889408613&OSSAccessKeyId=LTAI4F3Gt8M6rbEl&Signature=fNtWiExSqb%2FdFdD0CMk3xQ%2BLQFI%3D\\\",\\\"url\\\":\\\"http://wajueji.oss-cn-shenzhen.aliyuncs.com/business/78ff88cf31fa418296137eab90b46f1b.jpg?Expires=1889408613&OSSAccessKeyId=LTAI4F3Gt8M6rbEl&Signature=fNtWiExSqb%2FdFdD0CMk3xQ%2BLQFI%3D\\\",\\\"uid\\\":1574048527532,\\\"status\\\":\\\"success\\\"}]\",\"order_text\":\"未付款\",\"message\":\"bus\",\"other_price\":100.00,\"order_name\":\"700泄油滤\",\"order_code\":\"2019111920002002788501\",\"order_status\":101,\"user_code\":\"2019111860000706486737\",\"goodsOwnerList\":[{\"fixs\":\"001\",\"brands\":\"小松\",\"v3s\":\"001\"}],\"order_num\":1,\"id\":479,\"pic_url\":\"http://wajueji.oss-cn-shenzhen.aliyuncs.com/category_water/b7404498892c49c9984d09befd864d7f.png?Expires=1886145608&OSSAccessKeyId=LTAI4F3Gt8M6rbEl&Signature=qFF1NjfcSDzI5KRKqVChHLK%2Fqd8%3D\",\"order_type\":1,\"add_time\":\"2019-11-19 14:33:52\",\"bus_name\":\"18030129694\",\"bus_code\":\"2019111860000706486737\"}],\"page\":{\"total\":4,\"startRow\":1,\"size\":4,\"navigateFirst\":1,\"prePage\":0,\"endRow\":4,\"pageSize\":10,\"pageNum\":1,\"navigateLastPageNums\":1,\"navigatePageNums\":[1]}}";
//        String jsonString = "{\"data\":[{\"goods_price\":1637.00,\"is_payframe\":\"0\",\"order_price\":2237.00,\"order_text\":\"未付款\",\"message\":\"bus\",\"other_price\":600.00,\"order_name\":\"700柴滤\",\"order_code\":\"2019111960002087639117\",\"order_status\":101,\"user_code\":\"2019111860000706486737\",\"goodsOwnerList\":[{\"fixs\":\"002\",\"brands\":\"日立\",\"v3s\":\"123456\"},{\"fixs\":\"002\",\"brands\":\"日立\",\"v3s\":\"123456\"}],\"order_num\":3,\"id\":481,\"order_type\":1,\"add_time\":\"2019-11-19 18:52:11\",\"bus_name\":\"18030129694\",\"bus_code\":\"2019111860000706486737\"},{\"goods_price\":71.00,\"is_payframe\":\"0\",\"order_price\":171.00,\"order_text\":\"未付款\",\"message\":\"bus\",\"other_price\":100.00,\"order_name\":\"700泄油滤\",\"order_code\":\"2019111920002002788501\",\"order_status\":101,\"user_code\":\"2019111860000706486737\",\"goodsOwnerList\":[{\"fixs\":\"001\",\"brands\":\"小松\",\"v3s\":\"001\"}],\"order_num\":1,\"id\":479,\"order_type\":1,\"add_time\":\"2019-11-19 14:33:52\",\"bus_name\":\"18030129694\",\"bus_code\":\"2019111860000706486737\"}],\"page\":{\"total\":4,\"startRow\":1,\"size\":4,\"navigateFirst\":1,\"prePage\":0,\"endRow\":4,\"pageSize\":10,\"pageNum\":1,\"navigateLastPageNums\":1,\"navigatePageNums\":[1]}}";
//
//        Gson gson = new Gson();
//        FindOrderListBean dataBean1 = gson.fromJson(jsonString1, FindOrderListBean.class);
//        FindOrderListBean dataBean = gson.fromJson(jsonString, FindOrderListBean.class);
    }

    public void onEvent(AddressResultEvent messageEvent) {
        mDataBinding.recyclerview.autoRefresh();
    }

    public void onEvent(PayEvent event) {
        if (event.order_code != null && type.equals(event.type) && list != null) {
            new PayWithCouponDialog(getContext(), getActivity(), event.order_code, list).show();
        }
    }

    public void onEvent(final OrderCancleEvent event) {
        if (event.order_code != null && type.equals(event.type)) {
            new ConfirmOrderDialog(getContext(), "请确认是否取消订单", new CallbackListener() {
                @Override
                public void clickY() {
                    delOrder(event.order_code);
                }
            }).show();
        }
    }

    public void onEvent(final OrderConfirmEvent event) {
        if (event.order_code != null && type.equals(event.type)) {
            new ConfirmOrderDialog(getContext(), new CallbackListener() {
                @Override
                public void clickY() {
                    updateOrderStatus(event.order_code);
                }
            }).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }

    /**
     * 获取订单列表
     * 获取列表后，遍历商品信息
     */
    private void findOrderList3(final boolean refresh, final String type) {
        mCurrentPage = refresh ? START_PAGE : ++mCurrentPage;
        Map<String, Object> map = new TreeMap<String, Object>();
        map.put("user_code", ConfigUtils.getCurrentUser(getContext()).getUserCode());
        map.put("order_type", type);
        map.put("page", mCurrentPage + "");
        map.put("limit", mShowCount + "");
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = SignObject.getSignKey(getActivity(), map, "findOrderList3: " + type);
        NetBean netBean = new NetBean();
        netBean.setToken(ConfigUtils.getCurrentUser(getContext()).getToken());
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(FindOrderList3Action.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<FindOrderListBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<FindOrderListBean>> response,
                                           BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                if (response.body().getCode().equals("200")) {
                                    doSuccessResponse(refresh, response.body().getData());
                                } else if (response.body().getCode().equals("1003")) {
                                    ToastUtils.show(getContext(), "登录信息过期，请重新登录");
                                    startNewActivity(LoginActivity.class);
                                } else {
                                    if (!BuildConfig.DEBUG && response.body().getCode().equals("1006")) {
                                        ToastUtils.show(getContext(), "网络错误");
                                        mDataBinding.recyclerview.loadComplete(true, false);
                                    } else {
                                        ToastUtils.show(getContext(), "error " + type + ":" + response.body().getMessage());
                                    }
                                }
                            } else {
                                ToastUtils.show(getContext(), "网络错误");
                                mDataBinding.recyclerview.loadComplete(true, false);
                            }
                        }
                    }
                }));
    }

    private void findOrderList(final boolean refresh, final String type) {
        mCurrentPage = refresh ? START_PAGE : ++mCurrentPage;
        Map<String, String> map = new TreeMap<String, String>();
        map.put("user_code", ConfigUtils.getCurrentUser(getContext()).getUserCode());
        map.put("order_type", type);
        map.put("page", mCurrentPage + "");
        map.put("limit", mShowCount + "");
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getActivity(), map, "findOrderList: " + type);
        NetBean netBean = new NetBean();
        netBean.setToken(ConfigUtils.getCurrentUser(getContext()).getToken());
        netBean.setSign(sign);
        netBean.setContent(content);

        onNewRequestCall(FindOrderListAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<FindOrderListBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<FindOrderListBean>> response,
                                           BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                if (response.body().getCode().equals("200")) {
                                    doSuccessResponse(refresh, response.body().getData());
                                } else if (response.body().getCode().equals("1003")) {
                                    ToastUtils.show(getContext(), "登录信息过期，请重新登录");
                                    startNewActivity(LoginActivity.class);
                                } else {
                                    ToastUtils.show(getContext(), "error " + type + ":" + response.body().getMessage());
                                }
                            } else {
                                ToastUtils.show(getContext(), "网络错误");
                                mDataBinding.recyclerview.loadComplete(true, false);
                            }
                        }
                    }
                }));
    }

    private void doSuccessResponse(final boolean refresh, final FindOrderListBean categoryBean) {
        this.findOrderListBean = categoryBean;
        if (categoryBean != null && categoryBean.getData() != null
                && categoryBean.getData().size() > 0) {
            for (int i = 0; i < categoryBean.getData().size(); i++) {
                if (categoryBean.getData().get(i).getIs_payframe() != null
                        && "1".equals(categoryBean.getData().get(i).getIs_payframe())) {
                    findOrder(categoryBean.getData().get(i).getOrder_code());
                }

                if (i == categoryBean.getData().size() - 1) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (refresh) {
                                mAdapter.setDetail(categoryBean);
                            } else {
                                mAdapter.setExpend(categoryBean);
                            }
                        }
                    }, 500);
                }
            }
            mDataBinding.recyclerview.loadComplete(mAdapter.getItemCount() == 0, ((float) findOrderListBean.getPage().getTotal() / (float) findOrderListBean.getPage().getPageSize()) > mCurrentPage);
        } else {
            if (refresh) {
                mAdapter.setDetail(categoryBean);
            } else {
                mAdapter.setExpend(categoryBean);
            }
            mDataBinding.recyclerview.loadComplete(true, false);
        }
    }

    /**
     * 订单查询
     */
    private void findOrder(final String orderGood) {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("orderGood", orderGood);
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getContext(), map, "findOrder");
        NetBean netBean = new NetBean();
        netBean.setToken(ConfigUtils.getCurrentUser(getContext()).getToken());
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(FindOrderAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<FindOrderBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<FindOrderBean>> response,
                                           BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                if (response.body().getCode().equals("200")) {
                                    FindOrderBean findOrderBean = response.body().getData();
                                    if (findOrderBean != null && findOrderBean.getGoodsList() != null) {
                                        for (FindOrderListBean.DataBean dataBean : findOrderListBean.getData()) {
                                            if (dataBean.getOrder_code().equals(orderGood)) {
                                                for (FindOrderBean.GoodsListBean goodsListBean : findOrderBean.getGoodsList()) {
                                                    goodsListBean.setOrder_text(dataBean.getOrder_text());
                                                    goodsListBean.setOrder_status(dataBean.getOrder_status() + "");
                                                }
                                                dataBean.setGoodsListBeans(findOrderBean.getGoodsList());
                                            }
                                        }
                                    }
                                } else {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
    }

    /**
     * 取消订单
     */
    private void delOrder(final String orderGood) {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("order_code", orderGood);
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getContext(), map, "delOrder");
        NetBean netBean = new NetBean();
        netBean.setToken(ConfigUtils.getCurrentUser(getContext()).getToken());
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(DelOrderAction.newInstance(getContext(), netBean)
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
                                    if (response.body().getData().getData().equals("0")) {
                                        ToastUtil.show(getContext(), "取消成功");
                                    }
                                    mDataBinding.recyclerview.autoRefresh();
                                } else {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
    }

    /**
     * 确认订单
     */
    private void updateOrderStatus(final String orderGood) {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("order_code", orderGood);
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getContext(), map, "delOrder");
        NetBean netBean = new NetBean();
        netBean.setToken(ConfigUtils.getCurrentUser(getContext()).getToken());
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(UpdateOrderStatusAction.newInstance(getContext(), netBean)
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
                                    if (response.body().getData().getData().equals("0")) {
                                        ToastUtil.show(getContext(), "操作成功");
                                    }
                                    mDataBinding.recyclerview.autoRefresh();
                                } else {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
    }

    /**
     * 获取优惠券列表
     */
    private void getCouponList() {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("user_code", ConfigUtils.getCurrentUser(getContext()).getUserCode());
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getActivity(), map, "getCouponList");
        NetBean netBean = new NetBean();
        netBean.setToken(ConfigUtils.getCurrentUser(getContext()).getToken());
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(CouponListAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<CouponListBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<CouponListBean>> response, BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                if (response.body().getCode().equals("200")) {
                                    doSuccessResponse(response.body().getData());
                                } else if (response.body().getCode().equals("1003")) {
                                    ToastUtils.show(getContext(), "登录信息过期，请重新登录");
                                    startNewActivity(LoginActivity.class);
                                } else {
                                    ToastUtils.show(getContext(), "error " + type + ":" + response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
    }

    private void doSuccessResponse(final CouponListBean couponListBean) {
        list = new ArrayList<>();
        if (couponListBean != null && couponListBean.getResult() != null && couponListBean.getResult().size() > 0) {
            for (CouponListBean.ResultBean resultBean : couponListBean.getResult()) {
                if (resultBean.getStatus() == 0) {
                    GetComboBoxBean.ResultBean resultBean1 = new GetComboBoxBean.ResultBean(resultBean.getOrder_code(), resultBean.getName());
                    list.add(resultBean1);
                }
            }
        }
        list.add(new GetComboBoxBean.ResultBean("", "不使用优惠券"));
    }
}

