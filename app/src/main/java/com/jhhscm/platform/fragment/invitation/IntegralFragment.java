package com.jhhscm.platform.fragment.invitation;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jhhscm.platform.databinding.FragmentIntegralBinding;
import com.jhhscm.platform.fragment.base.AbsFragment;

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
