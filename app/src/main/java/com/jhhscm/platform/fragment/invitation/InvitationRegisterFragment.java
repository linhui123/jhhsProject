package com.jhhscm.platform.fragment.invitation;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.databinding.FragmentInvitationRegisterBinding;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.sign.SignObject;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.EncodingUtils;
import com.jhhscm.platform.tool.ShareUtils;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.YXProgressDialog;

import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

public class InvitationRegisterFragment extends AbsFragment<FragmentInvitationRegisterBinding> {
    private Bitmap bitmap;
    private String url;

    public static InvitationRegisterFragment instance() {
        InvitationRegisterFragment view = new InvitationRegisterFragment();
        return view;
    }

    @Override
    protected FragmentInvitationRegisterBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentInvitationRegisterBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        getUserShareUrl();
//         url = "http://192.168.0.235:8080/#/inviteReg?isbus=" +
//                ConfigUtils.getCurrentUser(getContext()).getIs_bus() +
//                "&user_code=" + ConfigUtils.getCurrentUser(getContext()).getUserCode();
//        Log.e("invite", "Url " + url);

        mDataBinding.wx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bitmap != null && url != null) {
                    showShare(url, "邀请好友", bitmap, "扫一扫，加入挖矿来", 1);
                } else {
                    ToastUtils.show(getContext(), "数据错误");
                }
            }
        });

        mDataBinding.pyq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bitmap != null && url != null) {
                    showShare(url, "邀请好友", bitmap, "扫一扫，加入挖矿来", 2);
                } else {
                    ToastUtils.show(getContext(), "数据错误");
                }
            }
        });
    }

    /**
     * 分享
     */
    public void showShare(final String shareUrl, final String title, final Bitmap picUrl, final String content, final int type) {
        if (type == 1) {
            YXProgressDialog dialog = new YXProgressDialog(getContext(), "请稍后");
            ShareUtils.shareUrlToWx(getActivity().getApplicationContext(), shareUrl, title, content, picUrl, 0);
        } else {
            YXProgressDialog dialog = new YXProgressDialog(getContext(), "请稍后");
            ShareUtils.shareUrlToWx(getActivity().getApplicationContext(), shareUrl, title, content, picUrl, 1);
        }
    }

    /**
     * 个人中心邀请码URL
     */
    private void getUserShareUrl() {
        if (getContext() != null) {
            showDialog();
            Map<String, Object> map = new TreeMap<String, Object>();
            map.put("user_code", ConfigUtils.getCurrentUser(getContext()).getUserCode());
            map.put("isbus", ConfigUtils.getCurrentUser(getContext()).getIs_bus());
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = SignObject.getSignKey(getActivity(), map, "getUserShareUrl");
            NetBean netBean = new NetBean();
            netBean.setToken(ConfigUtils.getCurrentUser(getContext()).getToken());
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(getUserShareUrlAction.newInstance(getContext(), netBean)
                    .request(new AHttpService.IResCallback<BaseEntity<UserShareUrlBean>>() {
                        @Override
                        public void onCallback(int resultCode, Response<BaseEntity<UserShareUrlBean>> response,
                                               BaseErrorInfo baseErrorInfo) {
                            if (getView() != null) {
                                closeDialog();
                                if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                    return;
                                }
                                if (response != null) {
                                    new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                    if (response.body().getCode().equals("200")) {
                                        Log.e("invite", "getUrl " + response.body().getData().getResult().getUrl());
                                        url = response.body().getData().getResult().getUrl();
                                        bitmap = EncodingUtils.createQRCode(url, 700, 700,
                                                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_logo_white));
                                        mDataBinding.scan.setImageBitmap(bitmap);
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
