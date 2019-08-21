package com.jhhscm.platform.fragment.my;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.AuthenticationActivity;
import com.jhhscm.platform.activity.LoginActivity;
import com.jhhscm.platform.activity.MsgActivity;
import com.jhhscm.platform.activity.MyCollectionActivity;
import com.jhhscm.platform.activity.MyLabourActivity;
import com.jhhscm.platform.activity.MyMechanicsActivity;
import com.jhhscm.platform.activity.MyPeiJianListActivity;
import com.jhhscm.platform.activity.ReceiveAddressActivity;
import com.jhhscm.platform.activity.SettingActivity;
import com.jhhscm.platform.bean.LogingResultBean;
import com.jhhscm.platform.databinding.FragmentHomePageBinding;
import com.jhhscm.platform.databinding.FragmentMyBinding;
import com.jhhscm.platform.event.ConsultationEvent;
import com.jhhscm.platform.event.LoginOutEvent;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.home.HomePageFragment;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.action.GetUserAction;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.UserBean;
import com.jhhscm.platform.http.bean.UserSession;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.permission.YXPermission;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.DisplayUtils;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;


public class MyFragment extends AbsFragment<FragmentMyBinding> {

    public static MyFragment instance() {
        MyFragment view = new MyFragment();
        return view;
    }

    @Override
    protected FragmentMyBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentMyBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e("MyFragment", "setUserVisibleHint");
        if (isVisibleToUser) {
            if (ConfigUtils.getCurrentUser(getContext()) != null
                    && ConfigUtils.getCurrentUser(getContext()).getMobile() != null) {
                mDataBinding.tvName.setVisibility(View.GONE);
                mDataBinding.username.setText(ConfigUtils.getCurrentUser(getContext()).getMobile());
                if ("1".equals(ConfigUtils.getCurrentUser(getContext()).getIs_check())) {
                    mDataBinding.tvCerGo.setVisibility(View.GONE);
                    mDataBinding.tvCer.setText("已认证");
                } else {
                    mDataBinding.tvCer.setText("未认证");
                    mDataBinding.tvCerGo.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    protected void setupViews() {
        EventBusUtil.registerEvent(this);
        LinearLayout.LayoutParams llParams = (LinearLayout.LayoutParams) mDataBinding.rlTop.getLayoutParams();
        llParams.topMargin += DisplayUtils.getStatusBarHeight(getContext());
        mDataBinding.rlTop.setLayoutParams(llParams);
        if (ConfigUtils.getCurrentUser(getContext()) != null
                && ConfigUtils.getCurrentUser(getContext()).getMobile() != null) {
            getUser();
        }

        mDataBinding.tvCerGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthenticationActivity.start(getContext());
            }
        });
        mDataBinding.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity(LoginActivity.class);
            }
        });

        mDataBinding.msgImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MsgActivity.start(getActivity());
            }
        });

        mDataBinding.rlShoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConfigUtils.getCurrentUser(getContext()) != null
                        && ConfigUtils.getCurrentUser(getContext()).getUserCode() != null) {
                    MyCollectionActivity.start(getContext());
                } else {
                    startNewActivity(LoginActivity.class);
                }
            }
        });

        mDataBinding.rlJizi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConfigUtils.getCurrentUser(getContext()) != null
                        && ConfigUtils.getCurrentUser(getContext()).getUserCode() != null) {
                    MyMechanicsActivity.start(getContext());
                } else {
                    startNewActivity(LoginActivity.class);
                }
            }
        });
        mDataBinding.rlLaowu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConfigUtils.getCurrentUser(getContext()) != null
                        && ConfigUtils.getCurrentUser(getContext()).getUserCode() != null) {
                    MyLabourActivity.start(getContext());
                } else {
                    startNewActivity(LoginActivity.class);
                }
            }
        });
        mDataBinding.rlPeijian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ConfigUtils.getCurrentUser(getContext()) != null
                        && ConfigUtils.getCurrentUser(getContext()).getUserCode() != null) {
                    MyPeiJianListActivity.start(getContext());
                } else {
                    startNewActivity(LoginActivity.class);
                }
            }
        });

        mDataBinding.rlSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingActivity.start(getActivity());
            }
        });

        mDataBinding.rlTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //6.0权限处理
                YXPermission.getInstance(getContext()).request(new AcpOptions.Builder()
                        .setDeniedCloseBtn(getContext().getString(R.string.permission_dlg_close_txt))
                        .setDeniedSettingBtn(getContext().getString(R.string.permission_dlg_settings_txt))
                        .setDeniedMessage(getContext().getString(R.string.permission_denied_txt, "拨打电话"))
                        .setPermissions(Manifest.permission.CALL_PHONE).build(), new AcpListener() {
                    @Override
                    public void onGranted() {
                        Uri uriScheme = Uri.parse("tel:" + "0591-88390068");
                        Intent it = new Intent(Intent.ACTION_CALL, uriScheme);
                        getContext().startActivity(it);
                    }


                    @Override
                    public void onDenied(List<String> permissions) {

                    }
                });

            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        initUser();
    }

    private void initUser() {
        if (ConfigUtils.getCurrentUser(getContext()) != null
                && ConfigUtils.getCurrentUser(getContext()).getMobile() != null) {
            if ("1".equals(ConfigUtils.getCurrentUser(getContext()).getIs_check())) {
                mDataBinding.tvCerGo.setVisibility(View.GONE);
                mDataBinding.tvCer.setText("已认证");
            } else {
                mDataBinding.tvCer.setText("未认证");
                mDataBinding.tvCerGo.setVisibility(View.VISIBLE);
            }
            mDataBinding.tvName.setVisibility(View.GONE);
            mDataBinding.rlCer.setVisibility(View.VISIBLE);
            mDataBinding.username.setText(ConfigUtils.getCurrentUser(getContext()).getMobile());
            ImageLoader.getInstance().displayImage(ConfigUtils.getCurrentUser(getContext()).getAvatar(), mDataBinding.imUser);
        } else {
            mDataBinding.imUser.setImageResource(R.mipmap.ic_heard);
            mDataBinding.tvName.setVisibility(View.VISIBLE);
            mDataBinding.rlCer.setVisibility(View.GONE);
        }
    }

    public void onEvent(LoginOutEvent event) {
        initUser();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }

    private void getUser() {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("mobile", ConfigUtils.getCurrentUser(getContext()).getMobile());
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getContext(), map, "getUser");
        NetBean netBean = new NetBean();
        netBean.setToken(ConfigUtils.getCurrentUser(getContext()).getToken());
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(GetUserAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<UserBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<UserBean>> response, BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                if (response.body().getCode().equals("200")) {

                                } else if (response.body().getCode().equals("1003")) {
                                    ToastUtils.show(getContext(), "登录信息过期，请重新登录");
                                    startNewActivity(LoginActivity.class);
                                } else {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
    }

}
