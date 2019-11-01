package com.jhhscm.platform.fragment.my.mechanics;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.TraceReloadActivity;
import com.jhhscm.platform.databinding.FragmentAddDeviceBinding;
import com.jhhscm.platform.databinding.FragmentMyMechanicsBinding;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.http.bean.UserSession;
import com.jhhscm.platform.views.timePickets.TimePickerShow;

public class AddDeviceFragment extends AbsFragment<FragmentAddDeviceBinding> {

    public static AddDeviceFragment instance() {
        AddDeviceFragment view = new AddDeviceFragment();
        return view;
    }

    @Override
    protected FragmentAddDeviceBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentAddDeviceBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        mDataBinding.data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerShow timePickerShow = new TimePickerShow(getContext());
                timePickerShow.timePickerAlertDialog("");
                timePickerShow.setOnTimePickerListener(new TimePickerShow.OnTimePickerListener() {
                    @Override
                    public void onClicklistener(String dataTime) {
                        mDataBinding.data.setText(dataTime.trim());
                    }
                });
            }
        });
    }


}
