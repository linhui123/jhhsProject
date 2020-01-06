package com.jhhscm.platform.fragment.my;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.BuildConfig;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.AuthenticationActivity;
import com.jhhscm.platform.activity.BookingActivity;
import com.jhhscm.platform.activity.IntegralActivity;
import com.jhhscm.platform.activity.LoginActivity;
import com.jhhscm.platform.activity.MsgActivity;
import com.jhhscm.platform.activity.MyCollectionActivity;
import com.jhhscm.platform.activity.MyCouponActivity;
import com.jhhscm.platform.activity.MyInviteActivity;
import com.jhhscm.platform.activity.MyLabourActivity;
import com.jhhscm.platform.activity.MyMechanicsActivity;
import com.jhhscm.platform.activity.MyPeiJianListActivity;
import com.jhhscm.platform.activity.MyStoreActivity;
import com.jhhscm.platform.activity.RepaymentActivity;
import com.jhhscm.platform.activity.SettingActivity;
import com.jhhscm.platform.databinding.FragmentMyBinding;
import com.jhhscm.platform.event.LoginOutEvent;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.action.GetUserAction;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.UserBean;
import com.jhhscm.platform.http.bean.UserSession;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.DisplayUtils;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

import static com.jhhscm.platform.tool.UdaUtils.hiddenPhoneNumber;


public class MyFragment extends AbsFragment<FragmentMyBinding> {
    private UserCenterBean userCenterBean;

    public static MyFragment instance() {
        MyFragment view = new MyFragment();
        return view;
    }

    @Override
    protected FragmentMyBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentMyBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        EventBusUtil.registerEvent(this);
        LinearLayout.LayoutParams llParams = (LinearLayout.LayoutParams) mDataBinding.rlTop.getLayoutParams();
        llParams.topMargin += DisplayUtils.getStatusBarHeight(getContext());
        mDataBinding.rlTop.setLayoutParams(llParams);

        initOnClick();
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
                                    UserBean userBean = response.body().getData();
                                    UserSession userSession = new UserSession();
                                    userSession.setToken(ConfigUtils.getCurrentUser(getContext()).getToken());
                                    userSession.setExpire(ConfigUtils.getCurrentUser(getContext()).getExpire());
                                    userSession.setAvatar(userBean.getData().getAvatar());
                                    userSession.setMobile(userBean.getData().getMobile());
                                    userSession.setTimestamp(userBean.getTimestamp());
                                    userSession.setUserCode(userBean.getData().getUserCode());
                                    userSession.setStatus(userBean.getData().getStatus() + "");
                                    userSession.setNickname(userBean.getData().getNickname());
                                    userSession.setIs_check(userBean.getData().getIs_check());
                                    ConfigUtils.setCurrentUser(getContext(), userSession);
                                } else if (response.body().getCode().equals("1003")) {
//                                    ToastUtils.show(getContext(), "登录信息过期，请重新登录");
//                                    startNewActivity(LoginActivity.class);
                                    ConfigUtils.removeCurrentUser(getContext());
                                } else if (!BuildConfig.DEBUG && response.body().getCode().equals("1006")) {
                                    ToastUtils.show(getContext(), "网络错误");
                                } else {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
    }

    private void getUserConter() {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("user_code", ConfigUtils.getCurrentUser(getContext()).getUserCode());
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getContext(), map, "getUserConter");
        NetBean netBean = new NetBean();
        netBean.setToken(ConfigUtils.getCurrentUser(getContext()).getToken());
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(UserCenterAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<UserCenterBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<UserCenterBean>> response, BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                if (response.body().getCode().equals("200")) {
                                    userCenterBean = response.body().getData();
                                    if (userCenterBean != null) {
                                        UserSession userSession = ConfigUtils.getCurrentUser(getContext());
                                        userSession.setIs_bus(response.body().getData().getResult().getIs_bus());
                                        ConfigUtils.setCurrentUser(getContext(), userSession);

                                        if (userCenterBean.getResult().getIs_bus() == 0) {
                                            mDataBinding.llStore.setVisibility(View.GONE);
                                            mDataBinding.tvStoreNum.setVisibility(View.GONE);
                                        } else {
                                            mDataBinding.llStore.setVisibility(View.VISIBLE);
                                            mDataBinding.tvStoreNum.setVisibility(View.VISIBLE);
                                            mDataBinding.tvStoreNum.setText("店铺积分：" + userCenterBean.getResult().getBus_points());
                                        }
                                        mDataBinding.tvPersonNum.setText(userCenterBean.getResult().getUser_points() + "");
                                        mDataBinding.tvCouponNum.setText(userCenterBean.getResult().getCoupons_count() + "");
                                        mDataBinding.tvShoucangNum.setText(userCenterBean.getResult().getCollect_count() + "");
                                        mDataBinding.tvInviteNum.setText(userCenterBean.getResult().getBususer_count() + "");
                                        if (userCenterBean.getResult().getBus_pointdesc().length() > 0) {
                                            Drawable drawable = getContext().getResources().getDrawable(R.mipmap.ic_ques);
                                            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                            mDataBinding.tvStoreNum.setCompoundDrawables(null, null, drawable, null);
                                        } else {
                                            mDataBinding.tvStoreNum.setCompoundDrawables(null, null, null, null);
                                        }
                                        if (userCenterBean.getResult().getUser_pointdesc().length() > 0) {
                                            Drawable drawable = getContext().getResources().getDrawable(R.mipmap.ic_ques);
                                            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                            mDataBinding.personNum.setCompoundDrawables(null, null, drawable, null);
                                        } else {
                                            mDataBinding.personNum.setCompoundDrawables(null, null, null, null);
                                        }
                                    }
                                } else if (response.body().getCode().equals("1003")) {
//                                    ToastUtils.show(getContext(), "登录信息过期，请重新登录");
//                                    startNewActivity(LoginActivity.class);
                                    ConfigUtils.removeCurrentUser(getContext());
                                } else if (!BuildConfig.DEBUG && response.body().getCode().equals("1006")) {
                                    ToastUtils.show(getContext(), "网络错误");
                                } else {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
    }

    private void getBusCount() {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("user_code", ConfigUtils.getCurrentUser(getContext()).getUserCode());
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getContext(), map, "getBusCount");
        NetBean netBean = new NetBean();
        netBean.setToken(ConfigUtils.getCurrentUser(getContext()).getToken());
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(BusCountAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<BusCountBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<BusCountBean>> response, BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                if (response.body().getCode().equals("200")) {
                                    if (response.body().getData().getResult() != null &&
                                            response.body().getData().getResult().getData().size() > 0 &&
                                            response.body().getData().getResult().getData().get(0) != null) {
                                        mDataBinding.tvMemberNum.setText(response.body().getData().getResult().getData().get(0).getUsers_num() + "");
                                        mDataBinding.tvOrderNum.setText(response.body().getData().getResult().getData().get(0).getOrder_num() + "");
                                        mDataBinding.tvProjectNum.setText(response.body().getData().getResult().getData().get(0).getGoods_num() + "");
                                    }
                                } else if (response.body().getCode().equals("1003")) {
//                                    ToastUtils.show(getContext(), "登录信息过期，请重新登录");
//                                    startNewActivity(LoginActivity.class);
                                    ConfigUtils.removeCurrentUser(getContext());
                                } else if (!BuildConfig.DEBUG && response.body().getCode().equals("1006")) {
                                    ToastUtils.show(getContext(), "网络错误");
                                } else {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
    }

    private void initOnClick() {
        mDataBinding.tvCerGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("未认证".equals(mDataBinding.tvCerGo.getText().toString())) {
                    AuthenticationActivity.start(getContext());
                }
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

        mDataBinding.setImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConfigUtils.getCurrentUser(getContext()) != null
                        && ConfigUtils.getCurrentUser(getContext()).getUserCode() != null) {
                    SettingActivity.start(getActivity());
                } else {
                    startNewActivity(LoginActivity.class);
                }
            }
        });

        mDataBinding.llCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConfigUtils.getCurrentUser(getContext()) != null
                        && ConfigUtils.getCurrentUser(getContext()).getUserCode() != null) {
                    MyCouponActivity.start(getActivity());
                } else {
                    startNewActivity(LoginActivity.class);
                }
            }
        });
        mDataBinding.llShoucang.setOnClickListener(new View.OnClickListener() {
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

        mDataBinding.llInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConfigUtils.getCurrentUser(getContext()) != null
                        && ConfigUtils.getCurrentUser(getContext()).getUserCode() != null) {
                    MyInviteActivity.start(getContext(), Integer.parseInt(mDataBinding.tvInviteNum.getText().toString()));
                } else {
                    startNewActivity(LoginActivity.class);
                }
            }
        });

        mDataBinding.llDevice.setOnClickListener(new View.OnClickListener() {
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

        mDataBinding.rlStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConfigUtils.getCurrentUser(getContext()) != null
                        && ConfigUtils.getCurrentUser(getContext()).getUserCode() != null) {
                    MyStoreActivity.start(getContext(), 0);
                } else {
                    startNewActivity(LoginActivity.class);
                }
            }
        });

        mDataBinding.llProjectNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConfigUtils.getCurrentUser(getContext()) != null
                        && ConfigUtils.getCurrentUser(getContext()).getUserCode() != null) {
                    MyStoreActivity.start(getContext(), 0);
                } else {
                    startNewActivity(LoginActivity.class);
                }
            }
        });

        mDataBinding.llMemberNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConfigUtils.getCurrentUser(getContext()) != null
                        && ConfigUtils.getCurrentUser(getContext()).getUserCode() != null) {
                    MyStoreActivity.start(getContext(), 1);
                } else {
                    startNewActivity(LoginActivity.class);
                }
            }
        });

        mDataBinding.llOrderNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConfigUtils.getCurrentUser(getContext()) != null
                        && ConfigUtils.getCurrentUser(getContext()).getUserCode() != null) {
                    MyStoreActivity.start(getContext(), 2);
                } else {
                    startNewActivity(LoginActivity.class);
                }
            }
        });

        mDataBinding.llBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConfigUtils.getCurrentUser(getContext()) != null
                        && ConfigUtils.getCurrentUser(getContext()).getUserCode() != null) {
                    BookingActivity.start(getContext());
                } else {
                    startNewActivity(LoginActivity.class);
                }
            }
        });

        mDataBinding.llLabour.setOnClickListener(new View.OnClickListener() {
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
        mDataBinding.rlOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ConfigUtils.getCurrentUser(getContext()) != null
                        && ConfigUtils.getCurrentUser(getContext()).getUserCode() != null) {
                    MyPeiJianListActivity.start(getContext(), 0);
                } else {
                    startNewActivity(LoginActivity.class);
                }
            }
        });

        mDataBinding.llOrder1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConfigUtils.getCurrentUser(getContext()) != null
                        && ConfigUtils.getCurrentUser(getContext()).getUserCode() != null) {
                    MyPeiJianListActivity.start(getContext(), 1);
                } else {
                    startNewActivity(LoginActivity.class);
                }
            }
        });
        mDataBinding.llOrder2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConfigUtils.getCurrentUser(getContext()) != null
                        && ConfigUtils.getCurrentUser(getContext()).getUserCode() != null) {
                    MyPeiJianListActivity.start(getContext(), 2);
                } else {
                    startNewActivity(LoginActivity.class);
                }
            }
        });

        mDataBinding.llOrder3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConfigUtils.getCurrentUser(getContext()) != null
                        && ConfigUtils.getCurrentUser(getContext()).getUserCode() != null) {
                    MyPeiJianListActivity.start(getContext(), 3);
                } else {
                    startNewActivity(LoginActivity.class);
                }
            }
        });
        mDataBinding.llOrder4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConfigUtils.getCurrentUser(getContext()) != null
                        && ConfigUtils.getCurrentUser(getContext()).getUserCode() != null) {
                    MyPeiJianListActivity.start(getContext(), 4);
                } else {
                    startNewActivity(LoginActivity.class);
                }
            }
        });


        mDataBinding.llRepay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConfigUtils.getCurrentUser(getContext()) != null
                        && ConfigUtils.getCurrentUser(getContext()).getUserCode() != null) {
                    RepaymentActivity.start(getContext());
                } else {
                    startNewActivity(LoginActivity.class);
                }
            }
        });

        mDataBinding.tvStoreNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userCenterBean != null && userCenterBean.getResult() != null
                        && userCenterBean.getResult().getBus_pointdesc() != null
                        && userCenterBean.getResult().getBus_pointdesc().length() > 0) {
                    IntegralActivity.start(getContext(), 1, userCenterBean.getResult().getBus_pointdesc());
                }
            }
        });

        mDataBinding.llPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userCenterBean != null && userCenterBean.getResult() != null
                        && userCenterBean.getResult().getUser_pointdesc() != null
                        && userCenterBean.getResult().getUser_pointdesc().length() > 0) {
                    IntegralActivity.start(getContext(), 0, userCenterBean.getResult().getUser_pointdesc());
                }
            }
        });

//        mDataBinding.rlTel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //6.0权限处理
//                YXPermission.getInstance(getContext()).request(new AcpOptions.Builder()
//                        .setDeniedCloseBtn(getContext().getString(R.string.permission_dlg_close_txt))
//                        .setDeniedSettingBtn(getContext().getString(R.string.permission_dlg_settings_txt))
//                        .setDeniedMessage(getContext().getString(R.string.permission_denied_txt, "拨打电话"))
//                        .setPermissions(Manifest.permission.CALL_PHONE).build(), new AcpListener() {
//                    @Override
//                    public void onGranted() {
//                        Uri uriScheme = Uri.parse("tel:" + "0591-83590001");
//                        Intent it = new Intent(Intent.ACTION_CALL, uriScheme);
//                        getContext().startActivity(it);
//                    }
//
//
//                    @Override
//                    public void onDenied(List<String> permissions) {
//
//                    }
//                });
//
//            }
//        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {//可见
            initUser();
        }
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
                mDataBinding.tvCer.setText("已认证");
                mDataBinding.tvCerGo.setText("已认证");
            } else {
                mDataBinding.tvCer.setText("未认证");
                mDataBinding.tvCerGo.setText("未认证");
            }
            getUser();
            getUserConter();
            getBusCount();
            if (ConfigUtils.getCurrentUser(getContext()).getIs_bus() == 0) {
                mDataBinding.llStore.setVisibility(View.GONE);
                mDataBinding.tvStoreNum.setVisibility(View.GONE);
            } else {
                mDataBinding.llStore.setVisibility(View.VISIBLE);
                mDataBinding.tvStoreNum.setVisibility(View.VISIBLE);
            }
            mDataBinding.tvName.setVisibility(View.GONE);
            mDataBinding.rlCer.setVisibility(View.VISIBLE);
            mDataBinding.username.setText(hiddenPhoneNumber(ConfigUtils.getCurrentUser(getContext()).getMobile()));
            if (ConfigUtils.getCurrentUser(getContext()).getAvatar() != null
                    && ConfigUtils.getCurrentUser(getContext()).getAvatar().length() > 0) {
                ImageLoader.getInstance().displayImage(ConfigUtils.getCurrentUser(getContext()).getAvatar(), mDataBinding.imUser);
            } else {
                mDataBinding.imUser.setImageResource(R.mipmap.ic_heard);
            }
        } else {
            mDataBinding.imUser.setImageResource(R.mipmap.ic_heard);
            mDataBinding.tvName.setVisibility(View.VISIBLE);
            mDataBinding.rlCer.setVisibility(View.GONE);
            mDataBinding.llStore.setVisibility(View.GONE);
            mDataBinding.tvStoreNum.setVisibility(View.GONE);
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

}
