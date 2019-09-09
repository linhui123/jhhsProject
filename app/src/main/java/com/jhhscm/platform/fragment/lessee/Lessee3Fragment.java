package com.jhhscm.platform.fragment.lessee;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jhhscm.platform.R;
import com.jhhscm.platform.databinding.FragmentLessee1Binding;
import com.jhhscm.platform.databinding.FragmentLessee3Binding;
import com.jhhscm.platform.event.LesseeFinishEvent;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.labour.FindLabourReleaseListBean;
import com.jhhscm.platform.http.bean.UserSession;
import com.jhhscm.platform.tool.EventBusUtil;

public class Lessee3Fragment extends AbsFragment<FragmentLessee3Binding> {
    private FindLabourReleaseListBean.DataBean dataBean;
    private int type;
    private UserSession userSession;
    private String id;

    public static Lessee3Fragment instance() {
        Lessee3Fragment view = new Lessee3Fragment();
        return view;
    }

    @Override
    protected FragmentLessee3Binding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentLessee3Binding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        mDataBinding.tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBusUtil.post(new LesseeFinishEvent());
                getActivity().finish();
            }
        });
    }
}
