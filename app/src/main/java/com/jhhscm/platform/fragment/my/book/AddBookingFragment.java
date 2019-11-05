package com.jhhscm.platform.fragment.my.book;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jhhscm.platform.R;
import com.jhhscm.platform.databinding.FragmentAddBookingBinding;
import com.jhhscm.platform.databinding.FragmentBookingBinding;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.tool.DataUtil;
import com.jhhscm.platform.views.timePickets.TimePickerShow;

public class AddBookingFragment extends AbsFragment<FragmentAddBookingBinding> {

    private int type;

    public static AddBookingFragment instance() {
        AddBookingFragment view = new AddBookingFragment();
        return view;
    }

    @Override
    protected FragmentAddBookingBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentAddBookingBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        type = getArguments().getInt("type");
        if (type == 0) {
            mDataBinding.rlPay.setVisibility(View.GONE);
            mDataBinding.rlTypePay.setVisibility(View.GONE);
        } else {
            mDataBinding.rlIncome.setVisibility(View.GONE);
            mDataBinding.rlUnIncome.setVisibility(View.GONE);
            mDataBinding.rlTypeIncome.setVisibility(View.GONE);
        }

        mDataBinding.rlDataIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerShow timePickerShow = new TimePickerShow(getContext());
                timePickerShow.timePickerAlertDialog("");
                timePickerShow.setOnTimePickerListener(new TimePickerShow.OnTimePickerListener() {
                    @Override
                    public void onClicklistener(String dataTime) {
                        mDataBinding.dataIncome.setText(dataTime.trim());
                    }
                });
            }
        });

        mDataBinding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }
}
