package com.jhhscm.platform.views.dialog;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.databinding.DialogAddressBinding;
import com.jhhscm.platform.event.GetRegionEvent;
import com.jhhscm.platform.fragment.Mechanics.action.GetRegionAction;
import com.jhhscm.platform.fragment.Mechanics.bean.GetRegionBean;
import com.jhhscm.platform.fragment.address.LocationAdapter;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.DisplayUtils;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.recyclerview.DividerItemDecoration;

import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

public class AddressDialog extends BaseDialog {
    private DialogAddressBinding mDataBinding;
    private String mContent;
    private CallbackListener mListener;
    private boolean mCancelable = true;
    private GetRegionBean getRegionBean;

    private String pID;
    private String cityId;
    private String countryId;
    private String pName;
    private String cityName;
    private String countryName;

    public interface CallbackListener {
        void clickResult(String pid, String pNmae, String cityId,
                         String cName, String countryID, String countryName);
    }

    public AddressDialog(Context context) {
        this(context, null, null);
    }

    public AddressDialog(Context context, CallbackListener listener) {
        this(context, null, listener);
    }

    public AddressDialog(Context context, String content, CallbackListener listener) {
        super(context);
        setCanceledOnTouchOutside(true);
        this.mContent = content;
        this.mListener = listener;
    }

    @Override
    protected View onInflateView(LayoutInflater inflater) {
        mDataBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_address, null, false);
        return mDataBinding.getRoot();
    }

    @Override
    protected void onInitView(View view) {
        EventBusUtil.registerEvent(this);
        initAddress();
        getRegion("1", "");
        mDataBinding.tv1.setText("请选择");
        if (mContent != null) {
            mDataBinding.tvTitle.setText(mContent);
        }

        mDataBinding.tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataBinding.rv1.setVisibility(View.VISIBLE);
                mDataBinding.rv2.setVisibility(View.GONE);
                mDataBinding.rv3.setVisibility(View.GONE);
            }
        });

        mDataBinding.tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataBinding.rv1.setVisibility(View.GONE);
                mDataBinding.rv2.setVisibility(View.VISIBLE);
                mDataBinding.rv3.setVisibility(View.GONE);
            }
        });

        mDataBinding.tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataBinding.rv1.setVisibility(View.GONE);
                mDataBinding.rv2.setVisibility(View.GONE);
                mDataBinding.rv3.setVisibility(View.VISIBLE);
            }
        });

        mDataBinding.imClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private LocationAdapter pAdapter;
    private LocationAdapter cityAdapter;
    private LocationAdapter countryAdapter;

    private void initAddress() {
        mDataBinding.rv1.addItemDecoration(new DividerItemDecoration(getContext()));
        mDataBinding.rv1.setLayoutManager(new LinearLayoutManager(getContext()));
        pAdapter = new LocationAdapter(getContext());
        mDataBinding.rv1.setAdapter(pAdapter);

        mDataBinding.rv2.addItemDecoration(new DividerItemDecoration(getContext()));
        mDataBinding.rv2.setLayoutManager(new LinearLayoutManager(getContext()));
        cityAdapter = new LocationAdapter(getContext());
        mDataBinding.rv2.setAdapter(cityAdapter);

        mDataBinding.rv3.addItemDecoration(new DividerItemDecoration(getContext()));
        mDataBinding.rv3.setLayoutManager(new LinearLayoutManager(getContext()));
        countryAdapter = new LocationAdapter(getContext());
        mDataBinding.rv3.setAdapter(countryAdapter);
    }

    public void setCanDissmiss(boolean cancelable) {
        this.mCancelable = cancelable;
    }

    @Override
    public void show() {
        super.show();
        Display d = this.getWindow().getWindowManager().getDefaultDisplay();
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        int h = (int) (DisplayUtils.getDeviceHeight(getContext()) * 0.7);
        lp.height = h;
        this.getWindow().setAttributes(lp);
        Window window = getWindow();
        window.setWindowAnimations(R.style.botton_animation);
        window.setGravity(Gravity.BOTTOM);
    }

    @Override
    public void onBackPressed() {
        if (mCancelable) {
            super.onBackPressed();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBusUtil.unregisterEvent(this);
    }

    /**
     * 更新地区选择
     */
    public void onEvent(GetRegionEvent event) {
        if (event.pid != null && event.type != null && event.activity == 4) {
            if (event.type.equals("1")) {//省点击，获取市
                pID = event.pid;
                pName = event.name;
                mDataBinding.tv1.setText(pName);
                mDataBinding.tv2.setText("请选择");
                mDataBinding.tv3.setText("");
                getRegion("2", event.pid);
            } else if (event.type.equals("2")) {//市点击
                cityId = event.pid;
                cityName = event.name;
                mDataBinding.tv2.setText(cityName);
                mDataBinding.tv3.setText("请选择");
                getRegion("3", event.pid);
            } else if (event.type.equals("3")) {//市点击
                countryId = event.pid;
                countryName = event.name;
                mDataBinding.tv3.setText(countryName);
                if (mListener != null) {
                    mListener.clickResult(pID, pName, cityId, cityName, countryId, countryName);
                }
                dismiss();
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
            String sign = Sign.getSignKey(getContext(), map, "getRegion");
            NetBean netBean = new NetBean();
            netBean.setToken("");
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(GetRegionAction.newInstance(getContext(), netBean)
                    .request(new AHttpService.IResCallback<BaseEntity<GetRegionBean>>() {
                        @Override
                        public void onCallback(int resultCode, Response<BaseEntity<GetRegionBean>> response,
                                               BaseErrorInfo baseErrorInfo) {

                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getContext().getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                if (response.body().getCode().equals("200")) {
                                    GetRegionBean getRegionBean = response.body().getData();
                                    if (getRegionBean.getResult() != null) {
                                        if (type.equals("1")) {//省
                                            mDataBinding.rv1.setVisibility(View.VISIBLE);
                                            mDataBinding.rv2.setVisibility(View.GONE);
                                            mDataBinding.rv3.setVisibility(View.GONE);
                                            pAdapter.setData(getRegionBean.getResult());
                                        } else if (type.equals("2")) {//市
                                            mDataBinding.rv1.setVisibility(View.GONE);
                                            mDataBinding.rv2.setVisibility(View.VISIBLE);
                                            mDataBinding.rv3.setVisibility(View.GONE);
                                            cityAdapter.setData(getRegionBean.getResult());
                                        } else if (type.equals("3")) {//区
                                            mDataBinding.rv1.setVisibility(View.GONE);
                                            mDataBinding.rv2.setVisibility(View.GONE);
                                            mDataBinding.rv3.setVisibility(View.VISIBLE);
                                            countryAdapter.setData(getRegionBean.getResult());
                                        }
                                    }
                                } else {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                }
                            }
                        }
                    }));
        }
    }
}


