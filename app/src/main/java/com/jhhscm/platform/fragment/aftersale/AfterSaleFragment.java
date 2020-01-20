package com.jhhscm.platform.fragment.aftersale;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.MyApplication;
import com.jhhscm.platform.R;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.FragmentAfterSaleBinding;
import com.jhhscm.platform.event.GetRegionEvent;
import com.jhhscm.platform.fragment.Mechanics.action.GetRegionAction;
import com.jhhscm.platform.fragment.Mechanics.bean.GetRegionBean;
import com.jhhscm.platform.fragment.Mechanics.holder.GetRegionViewHolder;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.home.AdBean;
import com.jhhscm.platform.fragment.home.action.GetAdAction;
import com.jhhscm.platform.fragment.home.action.SaveMsgAction;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.http.sign.SignObject;
import com.jhhscm.platform.jpush.ExampleUtil;
import com.jhhscm.platform.permission.YXPermission;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.DisplayUtils;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.StringUtils;
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.dialog.SimpleDialog;
import com.jhhscm.platform.views.dialog.TelPhoneDialog;
import com.jhhscm.platform.views.recyclerview.DividerItemDecoration;
import com.jhhscm.platform.views.recyclerview.DividerItemStrokeDecoration;
import com.jhhscm.platform.views.recyclerview.WrappedRecyclerView;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;
import com.tencent.mm.opensdk.utils.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

/**
 * 售后
 */
public class AfterSaleFragment extends AbsFragment<FragmentAfterSaleBinding> {
    private InnerAdapter mAdapter;
    private int mShowCount = 10;
    private int mCurrentPage = 1;
    private final int START_PAGE = mCurrentPage;

    private String pID = "";
    private String cID = "";

    private String pName;
    private String cName;

    private PrinviceAdapter pAdapter;
    private CityAdapter cAdapter;

    private GetRegionBean pRegionBean;
    private GetRegionBean cRegionBean;

    private double latitude = 0.0;
    private double longitude = 0.0;
    private List<FindBusListBean.DataBean> beanList;

    public static AfterSaleFragment instance() {
        AfterSaleFragment view = new AfterSaleFragment();
        return view;
    }

    @Override
    protected FragmentAfterSaleBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentAfterSaleBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        EventBusUtil.registerEvent(this);

        RelativeLayout.LayoutParams llParams = (RelativeLayout.LayoutParams) mDataBinding.rlTop.getLayoutParams();
        llParams.topMargin += DisplayUtils.getStatusBarHeight(getContext());
        mDataBinding.rlTop.setLayoutParams(llParams);

        mDataBinding.recyclerview.addItemDecoration(new DividerItemStrokeDecoration(getContext()));
        mDataBinding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new InnerAdapter(getContext());
        mDataBinding.recyclerview.setAdapter(mAdapter);
        mDataBinding.recyclerview.autoRefresh();
        mDataBinding.recyclerview.setOnPullListener(new WrappedRecyclerView.OnPullListener() {
            @Override
            public void onRefresh(RecyclerView view) {
                findBusList(true);
            }

            @Override
            public void onLoadMore(RecyclerView view) {
                findBusList(false);
            }
        });

        initPrivince();
        getAD(7);
        mDataBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        getRegion("1", "");
        mDataBinding.location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDataBinding.llArea.getVisibility() == View.GONE) {
                    mDataBinding.llArea.setVisibility(View.VISIBLE);
                } else if (mDataBinding.llArea.getVisibility() == View.VISIBLE) {
                    mDataBinding.llArea.setVisibility(View.GONE);
                }
            }
        });

        mDataBinding.tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TelPhoneDialog(getContext(), "免费咨询", new TelPhoneDialog.CallbackListener() {

                    @Override
                    public void clickYes(String phone) {
                        saveMsg(phone, "10");
                    }
                }).show();
            }
        });
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
                    longitude = ((MyApplication) getActivity().getApplicationContext()).getGaodeLon();
                    latitude = ((MyApplication) getActivity().getApplicationContext()).getGaodeLat();
                } else {
                    getLocation();
                }
            }
        });
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
            if (longitude == 0.0) {
                map.put("v1", ((MyApplication) getActivity().getApplicationContext()).getGaodeLon() + "");
                if (((MyApplication) getActivity().getApplicationContext()).getGaodeLon() == 0.0) {
                    ToastUtil.show(getContext(), "定位失败");
                }
            } else {
                map.put("v1", longitude + "");
            }
            if (latitude == 0.0) {
                map.put("v2", ((MyApplication) getActivity().getApplicationContext()).getGaodeLat() + "");
            } else {
                map.put("v2", latitude + "");
            }
            map.put("province_id", pID);
            map.put("city_id", cID);
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

    FindBusListBean getPushListBean;

    private void initView(boolean refresh, FindBusListBean pushListBean) {
        this.getPushListBean = pushListBean;
        if (refresh) {
            mAdapter.setData(pushListBean.getData());
        } else {
            mAdapter.append(pushListBean.getData());
        }
        mDataBinding.recyclerview.loadComplete(mAdapter.getItemCount() == 0,
                ((float) getPushListBean.getPage().getTotal() / (float) getPushListBean.getPage().getPageSize()) > mCurrentPage);

        if (((float) getPushListBean.getPage().getTotal() / (float) getPushListBean.getPage().getPageSize()) > mCurrentPage) {

        } else {
            if (beanList != null && beanList.size() > 0) {
                mAdapter.append(beanList);
            }
        }
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
     * 获取广告
     */
    private void getAD(final int position) {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("position", position + "");
        map.put("app_version", ExampleUtil.GetVersion(MyApplication.getInstance()));
        map.put("app_platform", StringUtils.getChannel(MyApplication.getInstance()));
        map.put("appid", "336abf9e97cd4276bf8aecde9d32ed99");
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getActivity(), map, "getAD type:" + position);
        NetBean netBean = new NetBean();
        netBean.setToken("");
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(GetAdAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<AdBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<AdBean>> response, BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                if (response.body().getCode().equals("200")) {
                                    if (position == 7) {
                                        AdBean adBean = response.body().getData();
                                        beanList = new ArrayList<>();
                                        for (AdBean.ResultBean resultBean : adBean.getResult()) {
                                            FindBusListBean.DataBean dataBean = new FindBusListBean.DataBean(resultBean.getUrl(), 1);
                                            beanList.add(dataBean);
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
            if (event.type.equals("1")) {//省点击，获取市
                pID = event.pid;
                pName = event.name;
                getRegion("2", event.pid);
            } else if (event.type.equals("2")) {//市点击
                cID = event.pid;
                cName = event.name;
                mDataBinding.location.setText(pName + " " + cName);
                mDataBinding.llArea.setVisibility(View.GONE);
                mDataBinding.recyclerview.autoRefresh();
            } else if (event.type.equals("0")) {//全部点击
                cID = "";
                pID = "";
                mDataBinding.location.setText("选择地区");
                mDataBinding.llArea.setVisibility(View.GONE);
                mDataBinding.recyclerview.autoRefresh();
            }
        }
    }

    /**
     * 获取行政区域列表
     */
    private void getRegion(final String type, final String pid) {
        if (getContext() != null) {
            Map<String, String> map = new TreeMap<String, String>();
            map.put("type", type);
            map.put("pid", pid);
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = Sign.getSignKey(getActivity(), map, "getRegion");
            NetBean netBean = new NetBean();
            netBean.setToken("");
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(GetRegionAction.newInstance(getContext(), netBean)
                    .request(new AHttpService.IResCallback<BaseEntity<GetRegionBean>>() {
                        @Override
                        public void onCallback(int resultCode, Response<BaseEntity<GetRegionBean>> response,
                                               BaseErrorInfo baseErrorInfo) {
                            if (getView() != null) {
                                closeDialog();
                                if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                    return;
                                }
                                if (response != null) {
                                    new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                    if (response.body().getCode().equals("200")) {
                                        GetRegionBean getRegionBean = response.body().getData();
                                        if (getRegionBean.getResult() != null) {
                                            if (type.equals("1")) {//初次加载
                                                pRegionBean = getRegionBean;
                                                GetRegionBean.ResultBean resultBean = new GetRegionBean.ResultBean();
                                                resultBean.setName("全部");
                                                resultBean.setId(0);
                                                resultBean.setType(0);
                                                pRegionBean.getResult().add(0, resultBean);
                                                pID = "";
                                                pAdapter.setData(pRegionBean.getResult());
                                                getRegion("2", getRegionBean.getResult().get(0).getId() + "");
                                            } else {
                                                if (getRegionBean.getResult().size() > 0) {
                                                    cRegionBean = getRegionBean;
                                                    cID = getRegionBean.getResult().get(0).getId() + "";
                                                    cAdapter.setData(cRegionBean.getResult());
                                                } else {
//                                                    ToastUtils.show(getContext(), "无数据");
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
    }

    private void initPrivince() {
        mDataBinding.rlPrivince.setLayoutManager(new LinearLayoutManager(getContext()));
        pAdapter = new PrinviceAdapter(getContext());
        mDataBinding.rlPrivince.setAdapter(pAdapter);

        mDataBinding.rlCity.addItemDecoration(new DividerItemDecoration(getContext()));
        mDataBinding.rlCity.setLayoutManager(new LinearLayoutManager(getContext()));
        cAdapter = new CityAdapter(getContext());
        mDataBinding.rlCity.setAdapter(cAdapter);
    }

    private class PrinviceAdapter extends AbsRecyclerViewAdapter<GetRegionBean.ResultBean> {
        public PrinviceAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<GetRegionBean.ResultBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new GetRegionViewHolder(mInflater.inflate(R.layout.item_mechanics_privince, parent, false), 3);
        }
    }

    private class CityAdapter extends AbsRecyclerViewAdapter<GetRegionBean.ResultBean> {
        public CityAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<GetRegionBean.ResultBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new GetRegionViewHolder(mInflater.inflate(R.layout.item_mechanics_privince, parent, false), 3);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }
}
