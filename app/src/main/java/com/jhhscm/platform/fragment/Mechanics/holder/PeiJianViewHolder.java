package com.jhhscm.platform.fragment.Mechanics.holder;

import android.view.View;

import com.jhhscm.platform.activity.StoreDetailActivity;
import com.jhhscm.platform.activity.h5.H5PeiJianActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemMechanicsPeijianBinding;
import com.jhhscm.platform.fragment.Mechanics.bean.FindCategoryBean;
import com.jhhscm.platform.tool.UrlUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

public class PeiJianViewHolder extends AbsRecyclerViewHolder<FindCategoryBean.DataBean> {

    private ItemMechanicsPeijianBinding mBinding;
    private boolean type = true;

    public PeiJianViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemMechanicsPeijianBinding.bind(itemView);
    }

    public PeiJianViewHolder(View itemView, boolean type) {
        super(itemView);
        this.type = type;
        mBinding = ItemMechanicsPeijianBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final FindCategoryBean.DataBean item) {
        if (item.getPic_url() != null) {
            ImageLoader.getInstance().displayImage(item.getPic_url(), mBinding.im);
        }
        if (item.getBus_name() != null && item.getBus_name().length() > 0) {
            mBinding.tvStore.setText(item.getBus_name() + ">");
            mBinding.tvStore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (item.getBus_code() != null && item.getBus_code().length() > 0 && type) {
                        StoreDetailActivity.start(itemView.getContext(), item.getBus_code(), "", "");
                    }
                }
            });
        } else {
            mBinding.tvStore.setText("自营>");
        }

        mBinding.tv1.setText(item.getName());
        mBinding.tv2.setText(item.getCounter_price() != null ? "￥" + item.getCounter_price() : "￥ --");
        mBinding.tv3.setText(item.getSale_num() != null ? "已售出 " + item.getSale_num() + " 件" : "已售出 --件");
        mBinding.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = UrlUtils.PJXQ + "&good_code=" + item.getGood_code();
                H5PeiJianActivity.start(itemView.getContext(), url, "配件详情", item.getBus_code(), item.getBus_name(),
                        item.getName(), item.getGood_code(), item.getPic_url(), item.getCounter_price(), 3);
            }
        });
    }
}

