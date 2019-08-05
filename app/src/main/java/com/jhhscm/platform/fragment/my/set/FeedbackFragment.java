package com.jhhscm.platform.fragment.my.set;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jhhscm.platform.R;
import com.jhhscm.platform.databinding.FragmentFeedbackBinding;
import com.jhhscm.platform.fragment.base.AbsFragment;


public class FeedbackFragment extends AbsFragment<FragmentFeedbackBinding> {

    public static FeedbackFragment instance() {
        FeedbackFragment view = new FeedbackFragment();
        return view;
    }

    @Override
    protected FragmentFeedbackBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentFeedbackBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {

    }
}
