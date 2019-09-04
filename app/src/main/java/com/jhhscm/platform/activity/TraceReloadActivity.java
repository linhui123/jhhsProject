package com.jhhscm.platform.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;


import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapsInitializer;
import com.amap.api.maps2d.SupportMapFragment;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.Polyline;
import com.amap.api.maps2d.model.PolylineOptions;
import com.jhhscm.platform.R;
import com.jhhscm.platform.tool.AMapUtil;

import java.util.ArrayList;
import java.util.Random;

/**
 * AMap 轨迹回放demo
 */

public class TraceReloadActivity extends FragmentActivity {
    private AMap mAMap;
    private Button mButton;
    private SeekBar mSeekBar;
    private Marker mCarMarker;
    private Polyline mPolyline;
    // 存放所有坐标的数组
    private final ArrayList<LatLng> mLatLngs = new ArrayList<>();
    private final ArrayList<LatLng> mTraceLatLngs = new ArrayList<>();

    public static void start(Context context) {
        Intent intent = new Intent(context, TraceReloadActivity.class);
        context.startActivity(intent);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                int pro = mSeekBar.getProgress();
                if (pro != mSeekBar.getMax()) {
                    mSeekBar.setProgress(pro + 1);
                    mHandler.sendEmptyMessageDelayed(1, 100);
                } else {
                    Button button = (Button) findViewById(R.id.btn_replay);
                    button.setText(" 回放 ");// 已执行到最后一个坐标 停止任务
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trace_reload);

        try {
            MapsInitializer.initialize(this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        //获取基站信息
        AMapUtil.getTowerInfo(this);

        initAmap();

        float f = 0;
        for (int i = 0; i < mLatLngs.size() - 1; i++) {
            f += AMapUtils.calculateLineDistance(mLatLngs.get(i), mLatLngs.get(i + 1));
        }
    }

    /**
     * 初始化AMap对象
     */
    private void initAmap() {
        mButton = findViewById(R.id.btn_replay);
        mSeekBar = findViewById(R.id.process_bar);
        mSeekBar.setSelected(false);

        mSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                if (mPolyline == null) {
                    final PolylineOptions polylineOptions = new PolylineOptions(); //绘制轨迹线
                    polylineOptions.color(Color.GREEN).width(8.0f);
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
                                getResources(), R.drawable.car)))
                .anchor(0.5f, 0.5f);
        mCarMarker = mAMap.addMarker(markerOptions);
        //地图缩放度
        mAMap.animateCamera(CameraUpdateFactory.newLatLngZoom(replayGeoPoint, 9));

        if (latLngs.size() > 1) {
            mPolyline.setPoints(latLngs);
        }

        if (latLngs.size() == mLatLngs.size()) {
            mAMap.addMarker(new MarkerOptions()
                    .position(latLngs.get(latLngs.size() - 1))
                    .icon(BitmapDescriptorFactory
                            .fromBitmap(BitmapFactory
                                    .decodeResource(getResources(), R.drawable.nav_route_result_end_point))));
        } else {
            mAMap.addMarker(new MarkerOptions()
                    .position(latLngs.get(latLngs.size() - 1))
                    .icon(BitmapDescriptorFactory
                            .fromBitmap(BitmapFactory
                                    .decodeResource(getResources(), R.drawable.car))));
        }
    }

    private void setUpMap() {
        double lat = 36.6666;
        double lng = 110.8888;
        mLatLngs.clear();
        for (int i = 0; i < 20; i++) {
            mLatLngs.add(new LatLng(lat + (float) i / 10, lng + (float) i / 15));
        }

        // 设置进度条最大长度为数组长度
        mSeekBar.setMax(mLatLngs.size());
        mAMap.clear();
        mAMap.setMapType(AMap.MAP_TYPE_NORMAL);
        //地图缩放度
        mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLngs.get(0), 9));

        // 增加起点位置
        mAMap.addMarker(new MarkerOptions()
                .position(mLatLngs.get(0))
                .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(
                                getResources(),
                                R.drawable.nav_route_result_start_point))));
    }

    public void btn_replay_click(View v) {
        // 根据按钮上的字判断当前是否在回放
        if (mButton.getText().toString().contains("回放")) {
            if (mLatLngs.size() > 0) {
                mPolyline = null;
                setUpMap();
                // 假如当前已经回放到最后一点 置0
                if (mSeekBar.getProgress() == mSeekBar.getMax()) {
                    mSeekBar.setProgress(0);
                }
                // 将按钮上的字设为"停止" 开始调用定时器回放
                mButton.setText(" 停止 ");
                mHandler.sendEmptyMessage(1);
            }
        } else {
            // 移除定时器的任务
            mHandler.removeMessages(1);
            mButton.setText(" 回放 ");
        }
    }

}