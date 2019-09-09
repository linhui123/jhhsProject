package com.jhhscm.platform.fragment.lessee;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.Lessee2Activity;
import com.jhhscm.platform.databinding.FragmentLabourDetailBinding;
import com.jhhscm.platform.databinding.FragmentLessee1Binding;
import com.jhhscm.platform.event.LesseeFinishEvent;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.labour.FindLabourReleaseListBean;
import com.jhhscm.platform.fragment.labour.LabourDetailFragment;
import com.jhhscm.platform.http.bean.UserSession;
import com.jhhscm.platform.tool.EventBusUtil;

public class Lessee1Fragment extends AbsFragment<FragmentLessee1Binding> {
    private FindLabourReleaseListBean.DataBean dataBean;
    private int type;
    private UserSession userSession;
    private String id;

    public static Lessee1Fragment instance() {
        Lessee1Fragment view = new Lessee1Fragment();
        return view;
    }

    @Override
    protected FragmentLessee1Binding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentLessee1Binding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        EventBusUtil.registerEvent(this);
        mDataBinding.tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Lessee2Activity.start(getContext(), new LesseeBean());
            }
        });
    }

    public void onEvent(LesseeFinishEvent event) {
        getActivity().finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }
}
