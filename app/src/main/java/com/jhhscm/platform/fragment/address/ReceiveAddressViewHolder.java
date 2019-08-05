package com.jhhscm.platform.fragment.address;

import android.view.View;

import com.jhhscm.platform.activity.ReceiveAddressDetailActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemReceiveAddressBinding;
import com.jhhscm.platform.event.AddressRefreshEvent;
import com.jhhscm.platform.fragment.GoodsToCarts.FindAddressListBean;
import com.jhhscm.platform.tool.EventBusUtil;


public class ReceiveAddressViewHolder extends AbsRecyclerViewHolder<FindAddressListBean.ResultBean.DataBean> {

    private ItemReceiveAddressBinding mBinding;
    private boolean isResult;

    public ReceiveAddressViewHolder(View itemView, boolean isResult) {
        super(itemView);
        this.isResult = isResult;
        mBinding = ItemReceiveAddressBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final FindAddressListBean.ResultBean.DataBean item) {
        if (item != null) {
            mBinding.tvName.setText(item.getName());
            mBinding.tvTel.setText(item.getTel());
            if (item.getIs_default().equals("1")) {
                mBinding.tvDefault.setVisibility(View.VISIBLE);
                mBinding.tvAddress.setText("           " + item.getAddress_detail());
            } else {
                mBinding.tvDefault.setVisibility(View.GONE);
                mBinding.tvAddress.setText(item.getAddress_detail());
            }

            mBinding.tvEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ReceiveAddressDetailActivity.start(itemView.getContext(), item, 2);
                }
            });

            mBinding.rlLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isResult) {
                        EventBusUtil.post(new AddressRefreshEvent(2, item));
                    } else {
                        ReceiveAddressDetailActivity.start(itemView.getContext(), item, 2);
                    }
                }
            });
        }
    }
}
