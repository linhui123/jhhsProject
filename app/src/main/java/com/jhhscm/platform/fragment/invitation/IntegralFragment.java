package com.jhhscm.platform.fragment.invitation;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.InvitationRegisterActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.FragmentIntegralBinding;
import com.jhhscm.platform.databinding.FragmentMyInviteBinding;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.UserSession;
import com.jhhscm.platform.http.sign.SignObject;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.recyclerview.DividerItemDecoration;
import com.jhhscm.platform.views.recyclerview.WrappedRecyclerView;

import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

/**
 * 积分
 */
public class IntegralFragment extends AbsFragment<FragmentIntegralBinding> {
    private int type;
    private String des;

    public static IntegralFragment instance() {
        IntegralFragment view = new IntegralFragment();
        return view;
    }

    @Override
    protected FragmentIntegralBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentIntegralBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        type = getArguments().getInt("type", 0);
        des = getArguments().getString("des");

        if (des != null && des.length() > 0) {
            mDataBinding.content.setText(des);
        }
    }
}
