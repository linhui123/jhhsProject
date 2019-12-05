package com.jhhscm.platform.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.alibaba.fastjson.JSON;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapsInitializer;
import com.amap.api.maps2d.SupportMapFragment;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.LatLngBounds;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.Polyline;
import com.amap.api.maps2d.model.PolylineOptions;
import com.google.gson.Gson;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.base.AbsActivity;
import com.jhhscm.platform.databinding.ActivityTraceReloadBinding;
import com.jhhscm.platform.fragment.vehicle.GpsTrackDetailAction;
import com.jhhscm.platform.fragment.vehicle.GpsTrackDetailBean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.sign.SignObject;
import com.jhhscm.platform.tool.AMapUtil;
import com.jhhscm.platform.tool.DataUtil;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.timePickets.TimePickerShow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

/**
 * AMap 轨迹回放demo
 */

public class TraceReloadActivity extends AbsActivity {
    private ActivityTraceReloadBinding mDataBinding;
    private AMap mAMap;
    private Marker mCarMarker;
    private Polyline mPolyline;

    private String id;
    private String data1 = "";
    private String data2 = "";
    private String startTime;
    private String endTime;
    //    private GpsTrackDetailBean gpsTrackDetailBean;
    private GpsTrackDetailBean.GpsgjListBean gpsTrackDetailBean;
    // 存放所有坐标的数组
    private final ArrayList<LatLng> mLatLngs = new ArrayList<>();
    private final ArrayList<LatLng> mTraceLatLngs = new ArrayList<>();

    public static void start(Context context, String id) {
        Intent intent = new Intent(context, TraceReloadActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                int pro = mDataBinding.processBar.getProgress();
                if (pro != mDataBinding.processBar.getMax()) {
                    mDataBinding.processBar.setProgress(pro + 1);
                    mHandler.sendEmptyMessageDelayed(1, 300);
                }
            }
        }
    };

    @Override
    protected boolean fullScreenMode() {
        return false;
    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getStringExtra("id");

        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_trace_reload);
        mDataBinding.toolbarTitle.setText("轨迹回放");
        mDataBinding.toolbar.setTitle("");
        setSupportActionBar(mDataBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDataBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        try {
            MapsInitializer.initialize(this);
            //获取基站信息
            AMapUtil.getTowerInfo(this);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        initAmap();

        mDataBinding.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerShow timePickerShow = new TimePickerShow(TraceReloadActivity.this);
                timePickerShow.timePickerAlertDialogs("", 1, 2);
                timePickerShow.setOnTimePickerListener(new TimePickerShow.OnTimePickerListener() {
                    @Override
                    public void onClicklistener(String dataTime) {
                        data1 = dataTime.substring(0, 10);
                        startTime = dataTime + ":00";
//                        startTime = dataTime.replace(" ", "+") + ":00";
                        mDataBinding.start.setText(dataTime.trim() + ":00");
                        mDataBinding.start.setTag(startTime.substring(11, startTime.length()));
                    }
                });
            }
        });

        mDataBinding.end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerShow timePickerShow = new TimePickerShow(TraceReloadActivity.this);
                timePickerShow.timePickerAlertDialogs("", 1, 2);
                timePickerShow.setOnTimePickerListener(new TimePickerShow.OnTimePickerListener() {
                    @Override
                    public void onClicklistener(String dataTime) {
                        data2 = dataTime.substring(0, 10);
                        endTime = dataTime + ":00";
//                        endTime = dataTime.replace(" ", "+") + ":00";
                        mDataBinding.end.setText(dataTime.trim() + ":00");
                        mDataBinding.end.setTag(endTime.substring(11, endTime.length()));
                        if (data1.length() > 0 && data2.equals(data1)) {

                        } else {
                            ToastUtil.show(getApplicationContext(), "选择时间必须是同一天");
                        }
                    }
                });
            }
        });

        mDataBinding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id != null && id.length() > 0) {
                    if (mDataBinding.start.getText().toString().length() > 0
                            && mDataBinding.end.getText().toString().length() > 0) {
                        if (DataUtil.TimeCompare(mDataBinding.start.getText().toString(),
                                mDataBinding.end.getText().toString(),
                                "yyyy-MM-dd")) {
//                            String days = DataUtil.getLongToDays(DataUtil.getLongTime(mDataBinding.start.getText().toString(),
//                                    mDataBinding.end.getText().toString(),
//                                    "yyyy-MM-dd"), "yyyy-MM-dd");
                            if (data2.equals(data1)) {
                                gpsTrackDetail();
                            } else {
                                ToastUtil.show(getApplicationContext(), "选择时间必须是同一天");
                            }
                        } else {
                            ToastUtil.show(getApplicationContext(), "结束时间不能小于开始时间");
                        }
                    } else {
                        ToastUtil.show(getApplicationContext(), "请先选择时间");
                    }
                } else {
                    ToastUtil.show(getApplicationContext(), "车辆设备号丢失");
                    finish();
                }
            }
        });
    }

    /**
     * 获取轨迹回放
     */
    private void gpsTrackDetail() {
        Map<String, Object> map = new TreeMap<String, Object>();
        map.put("systemno", id);
        map.put("startTime", mDataBinding.start.getTag().toString().trim());
        map.put("endTime", mDataBinding.end.getTag().toString().trim());
        map.put("date", data1);
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = SignObject.getSignKey(getApplicationContext(), map, "gpsTrackDetail3");
        NetBean netBean = new NetBean();
        netBean.setToken("");
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(GpsTrackDetailAction.newInstance(getApplicationContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<GpsTrackDetailBean>>() {

                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<GpsTrackDetailBean>> response, BaseErrorInfo baseErrorInfo) {
                        if (getApplicationContext() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getApplicationContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
//                                if (response.body() != null) {
//                                    if (response.body().getTracks() != null
//                                            && response.body().getTracks().size() > 0) {
//                                        gpsTrackDetailBean = response.body();
//                                        setUpMap();
//                                    } else {
//                                        ToastUtil.show(getApplicationContext(), "暂无车辆信息！");
//                                    }
//                                } else {
//                                    ToastUtils.show(getApplicationContext(), "网络异常");
//                                }
                                if (response.body().getCode().equals("200")) {
                                    GpsTrackDetailBean gpsDetailBean = response.body().getData();
                                    Gson gson = new Gson();
                                    gpsTrackDetailBean = gson.fromJson(gpsDetailBean.getGpsgjList(), GpsTrackDetailBean.GpsgjListBean.class);

                                    if ("true".equals(gpsTrackDetailBean.getRes()) && gpsTrackDetailBean != null) {
                                        setUpMap();
                                    } else {
                                        ToastUtil.show(getApplicationContext(), "暂无车辆信息！");
                                    }
                                } else if (response.body().getCode().equals("1001")) {
                                    ToastUtils.show(getApplicationContext(), response.body().getMessage());
                                } else {
                                    ToastUtils.show(getApplicationContext(), "网络异常");
                                }
                            }
                        }
                    }
                }));
    }

    private void setUpMap() {
        mLatLngs.clear();

        if (gpsTrackDetailBean != null
                && gpsTrackDetailBean.getResult() != null
                && gpsTrackDetailBean.getResult().size() > 0) {
            for (GpsTrackDetailBean.GpsgjListBean.ResultBean tracksBean : gpsTrackDetailBean.getResult()) {
                mLatLngs.add(new LatLng(Double.parseDouble(tracksBean.getLatitude()), Double.parseDouble(tracksBean.getLongitude())));
            }
//            mLatLngs.add(new LatLng(26.080648, 119.308806));
//            mLatLngs.add(new LatLng(26.084339, 119.316046));
//            mLatLngs.add(new LatLng(26.088752, 119.304117));
//            mLatLngs.add(new LatLng(26.076064, 119.32176));
//            mLatLngs.add(new LatLng(26.099719, 119.319711));
            // 设置进度条最大长度为数组长度
            mDataBinding.processBar.setMax(mLatLngs.size());
            mAMap.clear();
            mAMap.setMapType(AMap.MAP_TYPE_NORMAL);
            //地图缩放度
            LatLngBounds bounds = getLatLngBounds(mLatLngs);
            mAMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 300));
            // 增加起点位置
            mAMap.addMarker(new MarkerOptions()
                    .position(mLatLngs.get(0))
                    .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                            .decodeResource(
                                    getResources(),
                                    R.mipmap.ic_car_start)))
                    .anchor(0.5f, 0.5f));

            reload();
        }
    }

    public void reload() {
        if (mLatLngs.size() > 0) {
            mPolyline = null;
            // 假如当前已经回放到最后一点 置0
            if (mDataBinding.processBar.getProgress() == mDataBinding.processBar.getMax()) {
                mDataBinding.processBar.setProgress(0);
            }
            mHandler.sendEmptyMessage(1);
        }
    }

    /**
     * 初始化AMap对象
     */
    private void initAmap() {
        mDataBinding.processBar.setSelected(false);

        mDataBinding.processBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                if (mPolyline == null) {
                    final PolylineOptions polylineOptions = new PolylineOptions(); //绘制轨迹线
                    polylineOptions.color(getResources().getColor(R.color.color_feb)).width(8.0f);
                    mPolyline = mAMap.addPolyline(polylineOptions);
                }

                mTraceLatLngs.clear();
                if (progress != 0) {
                    for (int i = 0; i < seekBar.getProgress(); i++) {
                        mTraceLatLngs.add(mLatLngs.get(i));
                    }
                    drawLine(mTraceLatLngs, progress);
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                mTraceLatLngs.clear();
                int current = seekBar.getProgress();
                if (current != 0) {
                    for (int i = 0; i < seekBar.getProgress(); i++) {
                        mTraceLatLngs.add(mLatLngs.get(i));
                    }
                    drawLine(mTraceLatLngs, current);
                }
            }
        });

        if (mAMap == null) {
            Fragment fragment = getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mAMap = ((SupportMapFragment) fragment).getMap();

            if (mAMap != null) {
                setUpMap();
            }
        }
    }

    private void drawLine(ArrayList<LatLng> latLngs, int current) {
        LatLng replayGeoPoint = latLngs.get(current - 1);
        if (mCarMarker != null) {
            mCarMarker.destroy();
        }
        Log.e("drawLine", "latLngs " + latLngs.size() + " : " + latLngs.get(latLngs.size() - 1).latitude + " : " + latLngs.get(latLngs.size() - 1).longitude);
        // 添加车辆位置
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions
                .position(replayGeoPoint)
                .icon(BitmapDescriptorFactory
                        .fromBitmap(BitmapFactory.decodeResource(
                                getResources(), R.mipmap.ic_map_v)))
                .anchor(0.5f, 0.5f);
        mCarMarker = mAMap.addMarker(markerOptions);

        if (latLngs.size() > 1) {
            mPolyline.setPoints(latLngs);
        }

        if (latLngs.size() == mLatLngs.size()) {
            mAMap.addMarker(new MarkerOptions()
                    .position(latLngs.get(latLngs.size() - 1))
                    .icon(BitmapDescriptorFactory
                            .fromBitmap(BitmapFactory
                                    .decodeResource(getResources(), R.mipmap.ic_car_end)))
                    .anchor(0.5f, 0.5f));
        }
    }

    /**
     * 根据自定义内容获取缩放bounds
     */
    private LatLngBounds getLatLngBounds(List<LatLng> pointList) {
        LatLngBounds.Builder b = LatLngBounds.builder();
        for (int i = 0; i < pointList.size(); i++) {
            LatLng p = pointList.get(i);
            b.include(p);
        }
        return b.build();
    }
}