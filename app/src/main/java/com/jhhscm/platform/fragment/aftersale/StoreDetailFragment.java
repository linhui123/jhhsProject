package com.jhhscm.platform.fragment.aftersale;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jhhscm.platform.R;
import com.jhhscm.platform.databinding.FragmentAfterSaleBinding;
import com.jhhscm.platform.databinding.FragmentStoreDetailBinding;
import com.jhhscm.platform.databinding.FragmentStoreOrderBinding;
import com.jhhscm.platform.fragment.base.AbsFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class StoreDetailFragment extends AbsFragment<FragmentStoreDetailBinding> {


    public static StoreDetailFragment instance() {
        StoreDetailFragment view = new StoreDetailFragment();
        return view;
    }

    @Override
    protected FragmentStoreDetailBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentStoreDetailBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {

    }
}
