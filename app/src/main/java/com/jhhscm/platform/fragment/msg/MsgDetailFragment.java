package com.jhhscm.platform.fragment.msg;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.FragmentMsgBinding;
import com.jhhscm.platform.databinding.FragmentMsgDetailBinding;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.sign.SignObject;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.DisplayUtils;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.recyclerview.WrappedRecyclerView;

import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

public class MsgDetailFragment extends AbsFragment<FragmentMsgDetailBinding> {
    private GetPushListBean.DataBean dataBean;

    public static MsgDetailFragment instance() {
        MsgDetailFragment view = new MsgDetailFragment();
        return view;
    }

    @Override
    protected FragmentMsgDetailBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentMsgDetailBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        dataBean = (GetPushListBean.DataBean) getArguments().getSerializable("dataBean");
        if (dataBean != null) {
            mDataBinding.title.setText(dataBean.getTitle());
            mDataBinding.data.setText(dataBean.getAdd_time());
            mDataBinding.center.setText(dataBean.getContent());
        } else {
            mDataBinding.title.setText("暂无信息");
        }
    }
}
