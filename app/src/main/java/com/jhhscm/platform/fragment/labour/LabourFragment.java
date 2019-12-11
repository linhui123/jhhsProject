package com.jhhscm.platform.fragment.labour;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.jhhscm.platform.R;
import com.jhhscm.platform.databinding.FragmentLabourBinding;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.tool.DisplayUtils;

public class LabourFragment extends AbsFragment<FragmentLabourBinding> {
    private FragmentManager fm;
    private FragmentTransaction transaction;

    private LabourListFragment zhaopinFragment;
    private LabourListFragment qiuzhiFragment;
    private LabourCertificateFragment labourCertificateFragment;
    private LabourCertificateFragment elseFragment;

    public static LabourFragment instance() {
        LabourFragment view = new LabourFragment();
        return view;
    }

    @Override
    protected FragmentLabourBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentLabourBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        RelativeLayout.LayoutParams llParams = (RelativeLayout.LayoutParams) mDataBinding.rlTop.getLayoutParams();
        llParams.topMargin += DisplayUtils.getStatusBarHeight(getContext());
        mDataBinding.rlTop.setLayoutParams(llParams);

        fm = getChildFragmentManager();
        transaction = fm.beginTransaction();
        zhaopinFragment = (LabourListFragment) fm.findFragmentByTag("zhaopinFragment");
        qiuzhiFragment = (LabourListFragment) fm.findFragmentByTag("qiuzhiFragment");
        labourCertificateFragment = (LabourCertificateFragment) fm.findFragmentByTag("labourCertificateFragment");
        elseFragment = (LabourCertificateFragment) fm.findFragmentByTag("elseFragment");

        zhaopinFragment = new LabourListFragment().instance(0);
        fm.beginTransaction().add(R.id.fl, zhaopinFragment, "zhaopinFragment").commit();

        initClick();
    }

    private void initClick() {
        mDataBinding.tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        mDataBinding.tvZhaopin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initTab();
                onTabChanged(mDataBinding.tvZhaopin.getId());
                mDataBinding.tvZhaopin.setBackgroundResource(R.color.white);
                mDataBinding.tvZhaopin.setTextColor(getResources().getColor(R.color.a397));
            }
        });

        mDataBinding.tvQiuzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initTab();
                onTabChanged(mDataBinding.tvQiuzhi.getId());
                mDataBinding.tvQiuzhi.setBackgroundResource(R.color.white);
                mDataBinding.tvQiuzhi.setTextColor(getResources().getColor(R.color.a397));
            }
        });

        mDataBinding.tvZhengjian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initTab();
                onTabChanged(mDataBinding.tvZhengjian.getId());
                mDataBinding.tvZhengjian.setBackgroundResource(R.color.white);
                mDataBinding.tvZhengjian.setTextColor(getResources().getColor(R.color.a397));
            }
        });

        mDataBinding.tvQita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initTab();
                onTabChanged(mDataBinding.tvQita.getId());
                mDataBinding.tvQita.setBackgroundResource(R.color.white);
                mDataBinding.tvQita.setTextColor(getResources().getColor(R.color.a397));
            }
        });
    }

    private void initTab() {
        mDataBinding.tvZhaopin.setBackgroundResource(R.drawable.bg_397_left_ovel);
        mDataBinding.tvZhaopin.setTextColor(getResources().getColor(R.color.white));
        mDataBinding.tvQiuzhi.setBackgroundResource(R.color.a397);
        mDataBinding.tvQiuzhi.setTextColor(getResources().getColor(R.color.white));
        mDataBinding.tvZhengjian.setBackgroundResource(R.color.a397);
        mDataBinding.tvZhengjian.setTextColor(getResources().getColor(R.color.white));
        mDataBinding.tvQita.setBackgroundResource(R.drawable.bg_397_right_ovel);
        mDataBinding.tvQita.setTextColor(getResources().getColor(R.color.white));
    }

    public void onTabChanged(int checkedId) {
        transaction = fm.beginTransaction();
        if (zhaopinFragment != null) {
            transaction.hide(zhaopinFragment);
        }
        if (qiuzhiFragment != null) {
            transaction.hide(qiuzhiFragment);
        }
        if (labourCertificateFragment != null) {
            transaction.hide(labourCertificateFragment);
        }
        if (elseFragment != null) {
            transaction.hide(elseFragment);
        }
        if (checkedId == R.id.tv_zhaopin) {
            if (zhaopinFragment == null) {
                zhaopinFragment = new LabourListFragment().instance(0);
                transaction.add(R.id.fl, zhaopinFragment, "zhaopinFragment");
            } else {
                transaction.show(zhaopinFragment);
            }
        } else if (checkedId == R.id.tv_qiuzhi) {
            if (qiuzhiFragment == null) {
                qiuzhiFragment = new LabourListFragment().instance(1);
                transaction.add(R.id.fl, qiuzhiFragment, "qiuzhiFragment");
            } else {
                transaction.show(qiuzhiFragment);
            }
        } else if (checkedId == R.id.tv_zhengjian) {
            if (labourCertificateFragment == null) {
                labourCertificateFragment = new LabourCertificateFragment().instance(0);
                transaction.add(R.id.fl, labourCertificateFragment, "labourCertificateFragment");
            } else {
                transaction.show(labourCertificateFragment);
            }
        } else if (checkedId == R.id.tv_qita) {
            if (elseFragment == null) {
                elseFragment = new LabourCertificateFragment().instance(1);
                transaction.add(R.id.fl, elseFragment, "elseFragment");
            } else {
                transaction.show(elseFragment);
            }
        }
        transaction.commitAllowingStateLoss();
    }
}
