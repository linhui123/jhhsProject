package com.jhhscm.platform.views.dialog;

import android.content.ClipboardManager;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.TraceReloadActivity;
import com.jhhscm.platform.activity.VehicleDetailsActivity;
import com.jhhscm.platform.databinding.DialogLogisticsBinding;
import com.jhhscm.platform.databinding.DialogVehicleMessageBinding;
import com.jhhscm.platform.fragment.vehicle.GpsDetailBean;
import com.jhhscm.platform.tool.DataUtil;
import com.jhhscm.platform.tool.DisplayUtils;
import com.jhhscm.platform.tool.ToastUtil;

import java.io.IOException;
import java.util.List;

public class VehicleMessageDialog extends BaseDialog {
    private DialogVehicleMessageBinding mDataBinding;
    private GpsDetailBean.GpsListBean gpsListBean;
    private String no;
    private boolean mCancelable = true;
    private LogisticsDialog.CallbackListener mListener;

    public interface CallbackListener {
        void clickY();
    }

    public VehicleMessageDialog(Context context) {
        super(context);
    }

    public VehicleMessageDialog(Context context, GpsDetailBean.GpsListBean gpsListBean) {
        super(context);
        this.gpsListBean = gpsListBean;
        setCanceledOnTouchOutside(true);
    }

    public VehicleMessageDialog(Context context, LogisticsDialog.CallbackListener listener) {
        this(context);
        setCanceledOnTouchOutside(true);
        this.mListener = listener;
    }


    public void setCallbackListener(LogisticsDialog.CallbackListener listener) {
        this.mListener = listener;
    }

    public void setCanBackDismiss(boolean canBackDismiss) {
        this.mCancelable = canBackDismiss;
    }

    @Override
    public void show() {
        super.show();
        Display d = this.getWindow().getWindowManager().getDefaultDisplay();
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.width = (int) (DisplayUtils.getDeviceWidth(getContext()) - getContext().getResources().getDimension(R.dimen.head_title_height));
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        this.getWindow().setAttributes(lp);
        Window window = getWindow();
        window.setWindowAnimations(R.style.botton_animation);
        window.setGravity(Gravity.BOTTOM);
    }

    @Override
    protected View onInflateView(LayoutInflater inflater) {
        mDataBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_vehicle_message

                , null, false);
        return mDataBinding.getRoot();
    }

    @Override
    protected void onInitView(View view) {
        mDataBinding.chepai.setText("车牌：" + gpsListBean.getVid());
        if (gpsListBean.getGt().length() > 20) {
            mDataBinding.data.setText("时间：" + gpsListBean.getGt().substring(0, 19));
        } else {
            mDataBinding.data.setText("时间：" + gpsListBean.getGt());
        }
        //数据提供，速度要除以10
        mDataBinding.sudu.setText("速度：" + Double.parseDouble(gpsListBean.getSp()) / 10 + " Km/h");
        if (gpsListBean.getOl() == 1) {
            mDataBinding.status.setText("状态：在线");
        } else {
            mDataBinding.status.setText("状态：离线");
        }

        mDataBinding.longitude.setText("经度：" + gpsListBean.getMlng());
        mDataBinding.latitude.setText("纬度：" + gpsListBean.getMlat());
        getAddress(Double.parseDouble(gpsListBean.getMlng()), Double.parseDouble(gpsListBean.getMlat()));

        mDataBinding.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VehicleDetailsActivity.start(getContext(), gpsListBean);
                dismiss();
            }
        });

        mDataBinding.reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TraceReloadActivity.start(getContext());
                dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mCancelable) {
            super.onBackPressed();
        }
    }

    public String getAddress(double lnt, double lat) {
        Geocoder geocoder = new Geocoder(getContext());
        boolean falg = geocoder.isPresent();
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //根据经纬度获取地理位置信息---这里会获取最近的几组地址信息，具体几组由最后一个参数决定
            List<Address> addresses = geocoder.getFromLocation(lat, lnt, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    //每一组地址里面还会有许多地址。这里我取的前2个地址。xxx街道-xxx位置
                    if (i == 0) {
                        stringBuilder.append(address.getAddressLine(i));
                    }

                    if (i == 1) {
                        stringBuilder.append(address.getAddressLine(i));
                        break;
                    }
                }
                mDataBinding.tagPosition.setText("当前位置：" + stringBuilder);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

}


