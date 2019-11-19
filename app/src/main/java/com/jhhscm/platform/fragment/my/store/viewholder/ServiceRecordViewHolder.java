package com.jhhscm.platform.fragment.my.store.viewholder;

import android.view.View;

import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.StoreOrderActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemServiceRecordBinding;
import com.jhhscm.platform.databinding.ItemStoreSelectMemberBinding;
import com.jhhscm.platform.event.FinishEvent;
import com.jhhscm.platform.fragment.home.bean.GetPageArticleListBean;
import com.jhhscm.platform.fragment.my.store.action.FindBusGoodsOwnerListByUserCodeBean;
import com.jhhscm.platform.fragment.my.store.action.FindBusUserServerListBean;
import com.jhhscm.platform.tool.EventBusUtil;

public class ServiceRecordViewHolder extends AbsRecyclerViewHolder<FindBusGoodsOwnerListByUserCodeBean.ResultBean.DataBean> {

    private ItemServiceRecordBinding mBinding;
    private String usercode;

    public ServiceRecordViewHolder(View itemView, String usercode) {
        super(itemView);
        this.usercode = usercode;
        mBinding = ItemServiceRecordBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final FindBusGoodsOwnerListByUserCodeBean.ResultBean.DataBean item) {
        if (item != null) {
            if (item.getBrand_name() != null) {
                mBinding.brand.setText("品牌：" + item.getBrand_name());
            }
            if (item.getGoodsnum() != null) {
                mBinding.no.setText("设备序列号：" + item.getGoodsnum());
            }
            if (item.getGpsnum() != null) {
                mBinding.gpsNo.setText("GPS序列号：" + item.getGpsnum());
            }
            if (item.getFix_p_17() != null) {
                mBinding.model.setText("型号：" + item.getFix_p_17());
            }

            mBinding.order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StoreOrderActivity.start(itemView.getContext(), usercode, item);
                }
            });
        }
    }
}
