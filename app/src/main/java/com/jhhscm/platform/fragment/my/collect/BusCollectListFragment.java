package com.jhhscm.platform.fragment.my.collect;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.LoginActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.FragmentAfterSaleBinding;
import com.jhhscm.platform.databinding.FragmentCollectListBinding;
import com.jhhscm.platform.event.AddressResultEvent;
import com.jhhscm.platform.event.ConsultationEvent;
import com.jhhscm.platform.event.GetRegionEvent;
import com.jhhscm.platform.fragment.Mechanics.action.GetRegionAction;
import com.jhhscm.platform.fragment.Mechanics.bean.GetRegionBean;
import com.jhhscm.platform.fragment.Mechanics.holder.GetRegionViewHolder;
import com.jhhscm.platform.fragment.aftersale.AfterSaleFragment;
import com.jhhscm.platform.fragment.aftersale.AfterSaleViewHolder;
import com.jhhscm.platform.fragment.aftersale.FindBusListAction;
import com.jhhscm.platform.fragment.aftersale.FindBusListBean;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.home.action.SaveMsgAction;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.ResultBean;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.http.sign.SignObject;
import com.jhhscm.platform.permission.YXPermission;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.DisplayUtils;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.dialog.SimpleDialog;
import com.jhhscm.platform.views.dialog.TelPhoneDialog;
import com.jhhscm.platform.views.recyclerview.DividerItemDecoration;
import com.jhhscm.platform.views.recyclerview.DividerItemStrokeDecoration;
import com.jhhscm.platform.views.recyclerview.WrappedRecyclerView;
import com.jhhscm.platform.views.slideswaphelper.PlusItemSlideCallback;
import com.jhhscm.platform.views.slideswaphelper.WItemTouchHelperPlus;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.mm.opensdk.utils.Log;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

public class BusCollectListFragment extends AbsFragment<FragmentCollectListBinding> implements MyBusCollectionAdapter.DeletedItemListener {
    private MyBusCollectionAdapter recAdapter;
    private int mShowCount = 10;
    private int mCurrentPage = 1;
    private final int START_PAGE = mCurrentPage;

    private String pID = "";
    private String cID = "";

    private String pName;
    private String cName;

    private GetRegionBean pRegionBean;
    private GetRegionBean cRegionBean;

    private double latitude = 0.0;
    private double longitude = 0.0;
    private String type = "3";

    public static AfterSaleFragment instance() {
        AfterSaleFragment view = new AfterSaleFragment();
        return view;
    }

    @Override
    protected FragmentCollectListBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentCollectListBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        EventBusUtil.registerEvent(this);

        mDataBinding.refresh.setEnableLastTime(false);
        mDataBinding.load.setEnableLastTime(false);
        mDataBinding.refreshlayout.setEnableRefresh(true);
        mDataBinding.refreshlayout.setEnableLoadMore(false);
        mDataBinding.refreshlayout.autoRefresh();
        mDataBinding.refreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                findBusList(true);
            }
        });

        mDataBinding.refreshlayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                findBusList(false);

            }
        });
        initView();

        YXPermission.getInstance(getContext()).request(new AcpOptions.Builder()
                .setDeniedCloseBtn(getContext().getString(R.string.permission_dlg_close_txt))
                .setDeniedSettingBtn(getContext().getString(R.string.permission_dlg_settings_txt))
                .setDeniedMessage(getContext().getString(R.string.permission_denied_txt, "读写"))
                .setPermissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION).build(), new AcpListener() {
            @Override
            public void onGranted() {
                getLocation();
            }

            @Override
            public void onDenied(List<String> permissions) {
                if (permissions.contains(Manifest.permission.ACCESS_COARSE_LOCATION) &&
                        permissions.contains(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    longitude = 0.0;
                    latitude = 0.0;
                } else {
                    getLocation();
                }
            }
        });
    }

    private void initView() {
        mDataBinding.rvGouwuche.addItemDecoration(new DividerItemStrokeDecoration(getContext()));
        mDataBinding.rvGouwuche.setLayoutManager(new LinearLayoutManager(getActivity()));
        recAdapter = new MyBusCollectionAdapter(getContext());
        recAdapter.setDeletedItemListener(this);
        recAdapter.setHasStableIds(true);
        mDataBinding.rvGouwuche.setAdapter(recAdapter);

        PlusItemSlideCallback callback = new PlusItemSlideCallback();
        WItemTouchHelperPlus extension = new WItemTouchHelperPlus(callback);
        extension.attachToRecyclerView(mDataBinding.rvGouwuche);
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);//低精度，如果设置为高精度，依然获取不了location。
        criteria.setAltitudeRequired(false);//不要求海拔
        criteria.setBearingRequired(false);//不要求方位
        criteria.setCostAllowed(false);//允许有花费
        criteria.setPowerRequirement(Criteria.POWER_LOW);//低功耗

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            Location location = locationManager.getBestProvider(criteria, true);
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
        } else {
            LocationListener locationListener = new LocationListener() {

                // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                // Provider被enable时触发此函数，比如GPS被打开
                @Override
                public void onProviderEnabled(String provider) {

                }

                // Provider被disable时触发此函数，比如GPS被关闭
                @Override
                public void onProviderDisabled(String provider) {

                }

                //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
                @Override
                public void onLocationChanged(Location location) {
                    if (location != null) {
                        Log.e("Map", "Location changed : Lat: "
                                + location.getLatitude() + " Lng: "
                                + location.getLongitude());
                    }
                }
            };

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                latitude = location.getLatitude(); //经度
                longitude = location.getLongitude(); //纬度
            }
        }
    }


    private void findBusList(final boolean refresh) {
        if (getContext() != null) {
            mCurrentPage = refresh ? START_PAGE : ++mCurrentPage;
            Map<String, Object> map = new TreeMap<String, Object>();
            map.put("page", mCurrentPage);
            map.put("limit", mShowCount);
            map.put("v1", longitude + "");
            map.put("v2", latitude + "");
            map.put("province_id", pID);
            map.put("city_id", cID);
            map.put("user_code", ConfigUtils.getCurrentUser(getContext()).getUserCode());
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = SignObject.getSignKey(getActivity(), map, "findBusList");
            NetBean netBean = new NetBean();
            netBean.setToken("");
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(FindBusListAction.newInstance(getContext(), netBean)
                    .request(new AHttpService.IResCallback<BaseEntity<FindBusListBean>>() {
                        @Override
                        public void onCallback(int resultCode, Response<BaseEntity<FindBusListBean>> response,
                                               BaseErrorInfo baseErrorInfo) {
                            if (getView() != null) {
                                closeDialog();
                                if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                    return;
                                }
                                if (response != null) {
                                    new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                    if (response.body().getCode().equals("200")) {
                                        initData(response.body().getData().getData(), refresh);

                                    } else {
                                        ToastUtils.show(getContext(), response.body().getMessage());
                                    }
                                }
                            }
                        }
                    }));
        }
    }

    FindBusListBean getPushListBean;

    List<FindBusListBean.DataBean> getCartGoodsByUserCodeBean;

    private void initData(List<FindBusListBean.DataBean> resultBeans, boolean refresh) {
        if (resultBeans.size() == 1) {
//            resultBeans.get(0).setType(type);
            recAdapter.setList(resultBeans, refresh);
        } else if (resultBeans.size() > 1) {
            for (int i = 0; i < resultBeans.size(); i++) {
//                resultBeans.get(i).setType(type);
                if (i == resultBeans.size() - 1) {
                    recAdapter.setList(resultBeans, refresh);
                }
            }
        } else {
            recAdapter.setList(resultBeans, refresh);
        }
        if (resultBeans.size() > 0) {
            mDataBinding.rlCaseBaseNull.setVisibility(View.GONE);
        }
        if (refresh) {
            getCartGoodsByUserCodeBean = resultBeans;
            mDataBinding.refreshlayout.finishRefresh();
        } else {
            getCartGoodsByUserCodeBean.addAll(resultBeans);
            mDataBinding.refreshlayout.finishLoadMore();
        }
        if (recAdapter.getItemCount() == 0) {
            mDataBinding.rlCaseBaseNull.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void deleted(FindBusListBean.DataBean resultBean) {
        collectDelete(resultBean.getBus_code());
    }

    private class InnerAdapter extends AbsRecyclerViewAdapter<FindBusListBean.DataBean> {
        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<FindBusListBean.DataBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new AfterSaleViewHolder(mInflater.inflate(R.layout.item_aftersale_store, parent, false), latitude, longitude);
        }
    }

    /**
     * 信息咨询
     */
    private void saveMsg(final String phone, String type) {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("mobile", phone);
        map.put("type", type);
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getContext(), map, "saveMsg");
        NetBean netBean = new NetBean();
        netBean.setToken("");
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(SaveMsgAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity> response, BaseErrorInfo baseErrorInfo) {
                        closeDialog();
                        if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                            return;
                        }
                        if (response != null) {
                            if (response.body().getCode().equals("200")) {
                                new SimpleDialog(getContext(), phone, new SimpleDialog.CallbackListener() {
                                    @Override
                                    public void clickYes() {

                                    }
                                }).show();
                            } else {
                                ToastUtils.show(getContext(), response.body().getMessage());
                            }
                        }
                    }
                }));
    }

    /**
     * 更新地区选择
     */
    public void onEvent(GetRegionEvent event) {
        if (event.activity == 3 && event.pid != null && event.type != null) {

        }
    }

    public void onEvent(AddressResultEvent messageEvent) {
        mDataBinding.refreshlayout.autoRefresh();
    }

    public void onEvent(ConsultationEvent event) {
        if (event != null && event.type == 3) {
            new TelPhoneDialog(getContext(), new TelPhoneDialog.CallbackListener() {

                @Override
                public void clickYes(String phone) {
                    saveMsg(phone, "2");
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
     * 取消收藏
     */
    private void collectDelete(final String bus_code) {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("user_code", ConfigUtils.getCurrentUser(getContext()).getUserCode());
        map.put("good_code", bus_code);
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getActivity(), map, "collectDelete: ");
        NetBean netBean = new NetBean();
        netBean.setToken(ConfigUtils.getCurrentUser(getContext()).getToken());
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(CollectDeleteAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<ResultBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<ResultBean>> response, BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                if (response.body().getCode().equals("200")) {
                                    mDataBinding.refreshlayout.autoRefresh();
                                } else {
                                    ToastUtils.show(getContext(), "error " + type + ":" + response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
    }
}

