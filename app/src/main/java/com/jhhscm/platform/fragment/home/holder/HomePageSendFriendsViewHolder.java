package com.jhhscm.platform.fragment.home.holder;

import android.view.View;

import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemHomePagePhoneBinding;
import com.jhhscm.platform.event.ConsultationEvent;
import com.jhhscm.platform.fragment.home.HomePageItem;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.umeng.analytics.MobclickAgent;

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

