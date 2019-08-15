package com.jhhscm.platform.fragment.Mechanics.holder;

import android.view.View;

import com.jhhscm.platform.activity.H5PeiJianActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemMechanicsPeijianBinding;
import com.jhhscm.platform.fragment.Mechanics.bean.FindCategoryBean;
import com.jhhscm.platform.tool.UrlUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

public class PeiJianViewHolder extends AbsRecyclerViewHolder<FindCategoryBean.DataBean> {

    private ItemMechanicsPeijianBinding mBinding;

    public PeiJianViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemMechanicsPeijianBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final FindCategoryBean.DataBean item) {
        ImageLoader.getInstance().displayImage(item.getPic_url(), mBinding.im);
        mBinding.tv1.setText(item.getName());
        mBinding.tv2.setText(item.getCounter_price() != null ? "￥" + item.getCounter_price() : "￥ --");
        mBinding.tv3.setText(item.getSale_num() != null ? "已售出 " + item.getSale_num() + " 件" : "已售出 --件");
        mBinding.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = UrlUtils.PJXQ + "&good_code=" + item.getGood_code();
                H5PeiJianActivity.start(itemView.getContext(), url, "配件详情", item.getName(),
                        item.getGood_code(), item.getPic_url(), item.getCounter_price(), 3);
            }
        });
    }
}

