package com.jhhscm.platform.fragment.my;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.LoginActivity;
import com.jhhscm.platform.activity.MyPeiJianListActivity;
import com.jhhscm.platform.activity.ReceiveAddressActivity;
import com.jhhscm.platform.activity.SettingActivity;
import com.jhhscm.platform.databinding.FragmentHomePageBinding;
import com.jhhscm.platform.databinding.FragmentMyBinding;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.home.HomePageFragment;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.DisplayUtils;
import com.nostra13.universalimageloader.core.ImageLoader;


public class MyFragment extends AbsFragment<FragmentMyBinding> {

    public static MyFragment instance() {
        MyFragment view = new MyFragment();
        return view;
    }

    @Override
    protected FragmentMyBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentMyBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e("MyFragment", "setUserVisibleHint");
        if (isVisibleToUser) {
            if (ConfigUtils.getCurrentUser(getContext()) != null
                    && ConfigUtils.getCurrentUser(getContext()).getMobile() != null) {
                mDataBinding.tvName.setVisibility(View.GONE);
                mDataBinding.username.setText(ConfigUtils.getCurrentUser(getContext()).getMobile());
            }
        }
    }

    @Override
    protected void setupViews() {
        LinearLayout.LayoutParams llParams = (LinearLayout.LayoutParams) mDataBinding.title.getLayoutParams();
        llParams.topMargin += DisplayUtils.getStatusBarHeight(getContext());
        mDataBinding.title.setLayoutParams(llParams);

        mDataBinding.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity(LoginActivity.class);
            }
        });

        mDataBinding.tvJizi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfigUtils.removeCurrentUser(getContext());
                initUser();
            }
        });

        mDataBinding.rlPeijian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyPeiJianListActivity.start(getContext());
            }
        });

        mDataBinding.rlAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReceiveAddressActivity.start(getActivity(), false);
            }
        });

        mDataBinding.rlSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingActivity.start(getActivity());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        initUser();
    }

    private void initUser() {
        if (ConfigUtils.getCurrentUser(getContext()) != null
                && ConfigUtils.getCurrentUser(getContext()).getMobile() != null) {
            mDataBinding.tvName.setVisibility(View.GONE);
            mDataBinding.rlCer.setVisibility(View.VISIBLE);
            mDataBinding.username.setText(ConfigUtils.getCurrentUser(getContext()).getMobile());
            ImageLoader.getInstance().displayImage(ConfigUtils.getCurrentUser(getContext()).getAvatar(), mDataBinding.imUser);
        } else {
            mDataBinding.imUser.setImageResource(R.mipmap.ic_launcher);
            mDataBinding.tvName.setVisibility(View.VISIBLE);
            mDataBinding.rlCer.setVisibility(View.GONE);
        }
    }
}
