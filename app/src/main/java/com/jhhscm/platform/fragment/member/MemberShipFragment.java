package com.jhhscm.platform.fragment.member;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.AddDeviceActivity;
import com.jhhscm.platform.activity.AuthenticationActivity;
import com.jhhscm.platform.activity.LoginActivity;
import com.jhhscm.platform.databinding.FragmentMemberShipBinding;
import com.jhhscm.platform.databinding.FragmentReceiveAddressDetailBinding;
import com.jhhscm.platform.fragment.GoodsToCarts.FindAddressListBean;
import com.jhhscm.platform.fragment.address.ReceiveAddressDetailFragment;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.http.bean.UserSession;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.ToastUtil;

/**
 * 会员权益
 */
public class MemberShipFragment extends AbsFragment<FragmentMemberShipBinding> {

    public static MemberShipFragment instance() {
        MemberShipFragment view = new MemberShipFragment();
        return view;
    }

    @Override
    protected FragmentMemberShipBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentMemberShipBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        mDataBinding.llMember1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConfigUtils.getCurrentUser(getContext()) != null &&
                        ConfigUtils.getCurrentUser(getContext()).getMobile() != null) {
                    ToastUtil.show(getContext(), "您已登录，请前往认证");
                } else {
                    LoginActivity.start(getContext());
                }
            }
        });

        mDataBinding.llMember2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConfigUtils.getCurrentUser(getContext()) != null &&
                        ConfigUtils.getCurrentUser(getContext()).getMobile() != null) {
                    if (ConfigUtils.getCurrentUser(getContext()).getIs_check() != null &&
                            "1".equals(ConfigUtils.getCurrentUser(getContext()).getIs_check())) {
                        ToastUtil.show(getContext(), "您已认证，请前往设备登记");
                    } else {
                        AuthenticationActivity.start(getContext());
                    }
                } else {
                    LoginActivity.start(getContext());
                }
            }
        });

        mDataBinding.llMember3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConfigUtils.getCurrentUser(getContext()) != null &&
                        ConfigUtils.getCurrentUser(getContext()).getMobile() != null) {
                    if (ConfigUtils.getCurrentUser(getContext()).getIs_check() != null &&
                            "1".equals(ConfigUtils.getCurrentUser(getContext()).getIs_check())) {
                        AddDeviceActivity.start(getContext(), 0);
                    } else {
                        AuthenticationActivity.start(getContext());
                    }
                } else {
                    LoginActivity.start(getContext());
                }
            }
        });
    }

}
