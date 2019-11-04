package com.jhhscm.platform.fragment.my.store.viewholder;

import android.view.View;

import com.jhhscm.platform.R;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemStoreSelectDeviceBinding;
import com.jhhscm.platform.databinding.ItemStoreSelectMemberBinding;
import com.jhhscm.platform.event.FinishEvent;
import com.jhhscm.platform.fragment.home.bean.GetPageArticleListBean;
import com.jhhscm.platform.tool.EventBusUtil;

public class MyDeviceSelectItemViewHolder extends AbsRecyclerViewHolder<GetPageArticleListBean.DataBean> {

    private ItemStoreSelectDeviceBinding mBinding;
    private boolean isSelect;

    public MyDeviceSelectItemViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemStoreSelectDeviceBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final GetPageArticleListBean.DataBean item) {
        if (isSelect) {
            mBinding.tvSelect.setImageResource(R.mipmap.ic_shoping_s1);
        } else {
            mBinding.tvSelect.setImageResource(R.mipmap.ic_shoping_s);
        }

        mBinding.llDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //遍历单选
                if (isSelect) {
                    isSelect = false;
                    mBinding.tvSelect.setImageResource(R.mipmap.ic_shoping_s);
                } else {
                    isSelect = true;
                    mBinding.tvSelect.setImageResource(R.mipmap.ic_shoping_s1);
                }
            }
        });
    }
}