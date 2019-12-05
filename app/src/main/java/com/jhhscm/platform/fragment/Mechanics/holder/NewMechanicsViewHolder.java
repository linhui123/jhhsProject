package com.jhhscm.platform.fragment.Mechanics.holder;

import android.view.View;

import com.jhhscm.platform.activity.h5.MechanicsH5Activity;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemMechanicsNewBinding;
import com.jhhscm.platform.event.ConsultationEvent;
import com.jhhscm.platform.fragment.Mechanics.bean.GetGoodsPageListBean;
import com.jhhscm.platform.tool.EventBusUtil;
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
        if (item.getPic_url() != null) {
            ImageLoader.getInstance().displayImage(item.getPic_url(), mBinding.im);
        }

        mBinding.tv1.setText(item.getName());
        mBinding.tv2.setText("铲斗容量：" + item.getFixP2() + " m³");
        mBinding.tv3.setText("额定功率：" + item.getFixP5() + " km/rpm");
        if (item.getNum() != null) {
            mBinding.tv5.setText("已售出：" + item.getNum() + "台");
        } else {
            mBinding.tv5.setText("已售出：0台");
        }

        mBinding.tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBusUtil.post(new ConsultationEvent(2));
            }
        });

        mBinding.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = UrlUtils.XJXQ + "&good_code=" + item.getGood_code();
                MechanicsH5Activity.start(itemView.getContext(), url, "新机详情", item.getGood_code(), item.getName(), item.getPic_url(), 1);
            }
        });
    }
}
