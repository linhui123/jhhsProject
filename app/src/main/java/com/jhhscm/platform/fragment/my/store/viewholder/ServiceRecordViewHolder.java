package com.jhhscm.platform.fragment.my.store.viewholder;

import android.view.View;

import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.StoreOrderActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemServiceRecordBinding;
import com.jhhscm.platform.databinding.ItemStoreSelectMemberBinding;
import com.jhhscm.platform.event.FinishEvent;
import com.jhhscm.platform.fragment.home.bean.GetPageArticleListBean;
import com.jhhscm.platform.fragment.my.store.action.FindBusUserServerListBean;
import com.jhhscm.platform.tool.EventBusUtil;

public class ServiceRecordViewHolder extends AbsRecyclerViewHolder<FindBusUserServerListBean.ResultBean.DataBean> {

    private ItemServiceRecordBinding mBinding;
    private boolean isSelect;

    public ServiceRecordViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemServiceRecordBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final FindBusUserServerListBean.ResultBean.DataBean item) {
        mBinding.order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoreOrderActivity.start(itemView.getContext());
            }
        });

    }
}
