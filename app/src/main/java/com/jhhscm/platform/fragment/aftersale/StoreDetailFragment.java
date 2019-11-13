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
import com.jhhscm.platform.tool.MapUtil;
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.views.dialog.MapSelectDialog;

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
        mDataBinding.storeLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MapSelectDialog(getContext(), "福建省福州市鼓楼区南街街道三坊七巷社区", new MapSelectDialog.CallbackListener() {
                    @Override
                    public void clickGaode() {
                        if (MapUtil.checkMapAppsIsExist(getActivity(), MapUtil.GAODE_PKG)) {
                            MapUtil.openGaoDe(getActivity(), 26.0816086349, 119.2976188660);
                        } else {
                            ToastUtil.show(getActivity(), "检测到您没有安装高德地图");
                        }

                    }

                    @Override
                    public void clickBaidu() {
                        if (MapUtil.checkMapAppsIsExist(getActivity(), MapUtil.BAIDU_PKG)) {
                            MapUtil.openBaidu(getActivity(), 26.0816086349, 119.2976188660);
                        } else {
                            ToastUtil.show(getActivity(), "检测到您没有安装百度地图");
                        }
                    }

                    @Override
                    public void clickTenxun() {
                        if (MapUtil.checkMapAppsIsExist(getActivity(), MapUtil.PN_TENCENT_MAP)) {
                            MapUtil.openTenxun(getActivity(), 26.0816086349, 119.2976188660);
                        } else {
                            ToastUtil.show(getActivity(), "检测到您没有安装腾讯地图");
                        }
                    }
                }).show();
            }
        });
    }
}
