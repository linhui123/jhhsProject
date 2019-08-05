package com.jhhscm.platform.fragment.Mechanics.holder;

import android.view.View;

import com.jhhscm.platform.activity.H5Activity;
import com.jhhscm.platform.activity.MechanicsH5Activity;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemMechanicsNewBinding;
import com.jhhscm.platform.fragment.Mechanics.bean.GetGoodsPageListBean;
import com.jhhscm.platform.tool.UrlUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

public class NewMechanicsViewHolder extends AbsRecyclerViewHolder<GetGoodsPageListBean.DataBean> {

    private ItemMechanicsNewBinding mBinding;

    public NewMechanicsViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemMechanicsNewBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final GetGoodsPageListBean.DataBean item) {
        ImageLoader.getInstance().displayImage(item.getPic_url(), mBinding.im);
        mBinding.tv1.setText(item.getName());
        mBinding.tv2.setText("铲斗容量：" + item.getFixP2() + " m");
        mBinding.tv3.setText("额定功率：" + item.getFixP5() + " km/rpm");
        mBinding.tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mBinding.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getGood_code() != null) {
                }
                String url = UrlUtils.ESJXQ + "&good_code=8";
                MechanicsH5Activity.start(itemView.getContext(), url, "新机详情", "8", 1);
            }
        });
    }
}
