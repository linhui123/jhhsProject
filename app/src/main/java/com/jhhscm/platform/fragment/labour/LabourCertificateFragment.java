package com.jhhscm.platform.fragment.labour;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jhhscm.platform.activity.SubmitLabourActivity;
import com.jhhscm.platform.databinding.FragmentLabourCertificateBinding;
import com.jhhscm.platform.fragment.base.AbsFragment;

/**
 * 证件
 */
public class LabourCertificateFragment extends AbsFragment<FragmentLabourCertificateBinding> {
    private int type = 0;//0证书，1其他

    public static LabourCertificateFragment instance(int type) {
        LabourCertificateFragment view = new LabourCertificateFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        view.setArguments(bundle);
        return view;
    }

    @Override
    protected FragmentLabourCertificateBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentLabourCertificateBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        type = getArguments().getInt("type");
        if (type == 0) {
            mDataBinding.rl5.setVisibility(View.GONE);
            mDataBinding.rl6.setVisibility(View.GONE);
        } else {
            mDataBinding.rl1.setVisibility(View.GONE);
            mDataBinding.rl2.setVisibility(View.GONE);
            mDataBinding.rl3.setVisibility(View.GONE);
            mDataBinding.rl4.setVisibility(View.GONE);
        }

        initClick();
    }

    private void initClick() {
        mDataBinding.rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubmitLabourActivity.start(getContext(), 0);
            }
        });

        mDataBinding.rl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubmitLabourActivity.start(getContext(), 1);
            }
        });

        mDataBinding.rl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubmitLabourActivity.start(getContext(), 2);
            }
        });

        mDataBinding.rl4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubmitLabourActivity.start(getContext(), 3);
            }
        });

        mDataBinding.rl5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubmitLabourActivity.start(getContext(), 4);
            }
        });

        mDataBinding.rl6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubmitLabourActivity.start(getContext(), 5);
            }
        });
    }
}
