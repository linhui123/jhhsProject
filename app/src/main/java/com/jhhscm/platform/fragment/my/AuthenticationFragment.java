package com.jhhscm.platform.fragment.my;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.LoginActivity;
import com.jhhscm.platform.databinding.FragmentAuthenticationBinding;
import com.jhhscm.platform.event.LoginOutEvent;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.ResultBean;
import com.jhhscm.platform.http.bean.UserSession;
import com.jhhscm.platform.http.sign.SignObject;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.IDCard;
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.tool.UdaUtils;

import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

public class AuthenticationFragment extends AbsFragment<FragmentAuthenticationBinding> {
    private boolean updateImgResult;
    private UserSession userSession;

    public static AuthenticationFragment instance() {
        AuthenticationFragment view = new AuthenticationFragment();
        return view;
    }

    @Override
    protected FragmentAuthenticationBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentAuthenticationBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        mDataBinding.tvTijiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDataBinding.etUser.getText().toString().length() > 0) {
                    if (IDCard.IDCardValidate(mDataBinding.etId.getText().toString())) {
                        checkData(mDataBinding.etUser.getText().toString(), mDataBinding.etId.getText().toString());
                    } else {
                        ToastUtil.show(getContext(), "身份证号码格式错误");
                    }
                } else {
                    ToastUtil.show(getContext(), "请输入姓名");
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ConfigUtils.getCurrentUser(getContext()) != null
                && ConfigUtils.getCurrentUser(getContext()).getMobile() != null) {
            userSession = ConfigUtils.getCurrentUser(getContext());
        } else {
            startNewActivity(LoginActivity.class);
        }

    }

    /**
     * 身份认证
     */
    private void checkData(String name, String id) {
        if (getContext() != null) {
            showDialog();
            Map<String, Object> map = new TreeMap<String, Object>();
            map.put("mobile", ConfigUtils.getCurrentUser(getContext()).getMobile());
            map.put("user_code", ConfigUtils.getCurrentUser(getContext()).getUserCode());
            map.put("user_name", name.trim());
            map.put("id_card", id.trim());
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = SignObject.getSignKey(getActivity(), map, "checkData");
            NetBean netBean = new NetBean();
            netBean.setToken(ConfigUtils.getCurrentUser(getContext()).getToken());
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(CheckDataAction.newInstance(getContext(), netBean)
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
                                        ToastUtils.show(getContext(), "身份认证成功");
                                        UserSession userSession = ConfigUtils.getCurrentUser(getContext());
                                        userSession.setIs_check("1");
                                        ConfigUtils.setCurrentUser(getContext(), userSession);
                                        EventBusUtil.post(new LoginOutEvent());
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
}