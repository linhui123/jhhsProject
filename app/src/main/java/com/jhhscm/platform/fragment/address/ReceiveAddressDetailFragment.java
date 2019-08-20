package com.jhhscm.platform.fragment.address;


import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.LoginActivity;
import com.jhhscm.platform.databinding.FragmentReceiveAddressDetailBinding;
import com.jhhscm.platform.event.AddressRefreshEvent;
import com.jhhscm.platform.fragment.GoodsToCarts.FindAddressListBean;
import com.jhhscm.platform.fragment.GoodsToCarts.action.AddAddressAction;
import com.jhhscm.platform.fragment.GoodsToCarts.action.DelAddressAction;
import com.jhhscm.platform.fragment.GoodsToCarts.action.UpdateAddressAction;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.ResultBean;
import com.jhhscm.platform.http.bean.UserSession;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.dialog.AddressDialog;

import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

public class ReceiveAddressDetailFragment extends AbsFragment<FragmentReceiveAddressDetailBinding> {

    private FindAddressListBean.ResultBean.DataBean dataBean;
    private UserSession userSession;
    private int type;  //1 新增；2 修改

    private String isDefault = "0";//未设定默认
    private String addressId;
    private String mArge;
    private String mProvinceId;
    private String mProvinceNmae;
    private String mCityId;
    private String mCityName;
    private String countyId;
    private String countyName;
    private String addressDetail;
    private String name;
    private String tel;

    public static ReceiveAddressDetailFragment instance() {
        ReceiveAddressDetailFragment view = new ReceiveAddressDetailFragment();
        return view;
    }

    @Override
    protected FragmentReceiveAddressDetailBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentReceiveAddressDetailBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        EventBusUtil.registerEvent(getContext());
        if (ConfigUtils.getCurrentUser(getContext()) != null
                && ConfigUtils.getCurrentUser(getContext()).getMobile() != null) {
            userSession = ConfigUtils.getCurrentUser(getContext());
        } else {
            startNewActivity(LoginActivity.class);
        }

        type = getArguments().getInt("type", 0);
        dataBean = (FindAddressListBean.ResultBean.DataBean) getArguments().getSerializable("dataBean");

        initView();

        mDataBinding.etName.addTextChangedListener(createTextWatcher());
        mDataBinding.etLocation.addTextChangedListener(createTextWatcher());
        mDataBinding.etPhone.addTextChangedListener(createTextWatcher());

        mDataBinding.rlArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddressDialog(getActivity(), new AddressDialog.CallbackListener() {
                    @Override
                    public void clickResult(String pid, String pNmae, String cityId, String cName, String countryID, String countryName) {
                        mProvinceId = pid;
                        mCityId = cityId;
                        countyId = countryID;
                        mProvinceNmae = pNmae;
                        mCityName = cName;
                        countyName = countryName;
                        mArge = mProvinceNmae + " " + mCityName + " " + countyName;
                        mDataBinding.tvsArea.setText(mArge);
                    }
                }).show();
            }
        });

        mDataBinding.imDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDefault.equals("0")) {
                    isDefault = "1";
                    mDataBinding.imDefault.setImageResource(R.mipmap.ic_address_s1);
                } else {
                    isDefault = "0";
                    mDataBinding.imDefault.setImageResource(R.mipmap.ic_address_s);
                }
            }
        });

        mDataBinding.tvTijiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 1) {
                    addAddress(userSession.getUserCode(), userSession.getToken());
                } else {
                    updateAddress(userSession.getUserCode(), userSession.getToken());
                }
            }
        });

        judgeButton();

    }

    /**
     * 收货地址-删除
     */
    public void delAddress() {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("id", addressId);
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getActivity(), map, "delAddress");
        NetBean netBean = new NetBean();
        netBean.setToken(userSession.getToken());
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(DelAddressAction.newInstance(getContext(), netBean)
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
                                    Log.e("delAddress", "删除地址列表成功");
                                    EventBusUtil.post(new AddressRefreshEvent(1));
                                    getActivity().finish();
                                } else {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
    }

    /**
     * 收货地址-修改
     */
    private void updateAddress(String userCode, String token) {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("user_code", userCode);
        map.put("name", name);
        map.put("province", mProvinceId);
        map.put("city", mCityId);
        map.put("county", countyId);
        map.put("address_detail", addressDetail);
        map.put("tel", tel);
        map.put("id", addressId);
        map.put("is_default", isDefault);
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getActivity(), map, "updateAddress");
        NetBean netBean = new NetBean();
        netBean.setToken(token);
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(UpdateAddressAction.newInstance(getContext(), netBean)
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
                                    Log.e("updateAddress", "修改地址列表成功");
                                    EventBusUtil.post(new AddressRefreshEvent(1));
                                    getActivity().finish();
                                } else {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
    }

    /**
     * 新增地址列表
     */
    private void addAddress(String userCode, String token) {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("user_code", userCode);
        map.put("name", name);
        map.put("province", mProvinceId);
        map.put("city", mCityId);
        map.put("county", countyId);
        map.put("address_detail", addressDetail);
        map.put("tel", tel);
        map.put("is_default", isDefault);
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getActivity(), map, "addAddress");
        NetBean netBean = new NetBean();
        netBean.setToken(token);
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(AddAddressAction.newInstance(getContext(), netBean)
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
                                    Log.e("addAddress", "新增地址列表成功");
                                    EventBusUtil.post(new AddressRefreshEvent(1));
                                    getActivity().finish();
                                } else {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
    }

    private void initView() {
        if (dataBean != null) {
            name = dataBean.getName();
            tel = dataBean.getTel();
            mProvinceId = dataBean.getProvince_id();
            mCityId = dataBean.getCity_id();
            countyId = dataBean.getCounty_id();
            mProvinceNmae = dataBean.getProvince() != null ? dataBean.getProvince() : "";
            mCityName = dataBean.getCity() != null ? dataBean.getCity() : "";
            countyName = dataBean.getCounty() != null ? dataBean.getCounty() : "";
            mArge = mProvinceNmae + " " + mCityName + " " + countyName;
            addressId = dataBean.getId();
            isDefault = dataBean.getIs_default();
            addressDetail = dataBean.getAddress_detail();

            mDataBinding.etName.setText(name);
            mDataBinding.etPhone.setText(tel);
            mDataBinding.tvsArea.setText(mArge);
            mDataBinding.etLocation.setText(addressDetail);
            if (isDefault.equals("1")) {
                mDataBinding.imDefault.setImageResource(R.mipmap.ic_address_s1);
            } else {
                mDataBinding.imDefault.setImageResource(R.mipmap.ic_address_s);
            }
        }
    }

    private TextWatcher createTextWatcher() {
        return new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                judgeButton();
            }
        };
    }

    private void judgeButton() {
        name = mDataBinding.etName.getText().toString().trim();
        tel = mDataBinding.etPhone.getText().toString().trim();
        addressDetail = mDataBinding.etLocation.getText().toString().trim();
        if (mDataBinding.etName.getText().toString().length() > 0
                && mDataBinding.etLocation.getText().toString().length() > 0
                && mDataBinding.etPhone.getText().toString().length() > 0
                && mDataBinding.tvsArea.getText().toString().length() > 0) {
            mDataBinding.tvTijiao.setEnabled(true);
        } else {
            mDataBinding.tvTijiao.setEnabled(false);
        }
    }

    private void showArea() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }
}
