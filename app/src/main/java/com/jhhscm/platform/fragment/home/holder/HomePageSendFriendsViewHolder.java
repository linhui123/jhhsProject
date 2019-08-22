package com.jhhscm.platform.fragment.home.holder;

import android.view.View;
import android.widget.Toast;

import com.jhhscm.platform.activity.MainActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemHomePageAdBinding;
import com.jhhscm.platform.event.ConsultationEvent;
import com.jhhscm.platform.fragment.home.HomePageItem;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.dialog.HomeAlterDialog;
import com.jhhscm.platform.views.dialog.SimpleDialog;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import com.jhhscm.platform.databinding.ItemHomePagePhoneBinding;

public class HomePageSendFriendsViewHolder extends AbsRecyclerViewHolder<HomePageItem> {

    private ItemHomePagePhoneBinding mBinding;

    public HomePageSendFriendsViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemHomePagePhoneBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final HomePageItem item) {
        mBinding.tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBinding.etPhone.getText().toString().length() > 8) {
                    MobclickAgent.onEvent(itemView.getContext(), "consult_home");
                    EventBusUtil.post(new ConsultationEvent(mBinding.etPhone.getText().toString()));
                }else {
                    ToastUtils.show(itemView.getContext(),"请输入正确的手机号");
                }
            }
        });
    }
}

