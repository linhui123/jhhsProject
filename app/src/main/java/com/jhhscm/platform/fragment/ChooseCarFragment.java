package com.jhhscm.platform.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jhhscm.platform.R;

import butterknife.ButterKnife;

public class ChooseCarFragment extends Fragment{
private View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.choose_car_fragment, container, false);
        initView();
        return rootView;
    }

    private void initView() {

    }
}
