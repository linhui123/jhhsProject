package com.jhhscm.platform.fragment.my.set;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jhhscm.platform.R;
import com.jhhscm.platform.databinding.FragmentAboutBinding;
import com.jhhscm.platform.databinding.FragmentSetBinding;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.jpush.ExampleUtil;

public class AboutFragment extends AbsFragment<FragmentAboutBinding> {

    public static AboutFragment instance() {
        AboutFragment view = new AboutFragment();
        return view;
    }

    @Override
    protected FragmentAboutBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentAboutBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {

        mDataBinding.version.setText("当前版本号：" + ExampleUtil.GetVersion(getContext()));
    }
}
