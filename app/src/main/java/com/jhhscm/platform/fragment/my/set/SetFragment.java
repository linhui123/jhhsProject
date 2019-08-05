package com.jhhscm.platform.fragment.my.set;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.AboutActivity;
import com.jhhscm.platform.activity.FeedbackActivity;
import com.jhhscm.platform.databinding.FragmentMyBinding;
import com.jhhscm.platform.databinding.FragmentSetBinding;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.my.MyFragment;
import com.jhhscm.platform.tool.DataCleanManager;
import com.jhhscm.platform.tool.DisplayUtils;

public class SetFragment extends AbsFragment<FragmentSetBinding> {

    public static SetFragment instance() {
        SetFragment view = new SetFragment();
        return view;
    }

    @Override
    protected FragmentSetBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentSetBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        try {
            DataCleanManager.getTotalCacheSize(getContext());
            mDataBinding.tvCache.setText(DataCleanManager.getTotalCacheSize(getContext()));
        } catch (Exception e) {
            e.printStackTrace();
            mDataBinding.tvCache.setText("0.0M");
        }


        mDataBinding.rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutActivity.start(getActivity());
            }
        });

        mDataBinding.rl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    DataCleanManager.clearAllCache(getContext());
                    mDataBinding.tvCache.setText("0.0M");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        mDataBinding.rl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeedbackActivity.start(getContext());
            }
        });
    }
}
