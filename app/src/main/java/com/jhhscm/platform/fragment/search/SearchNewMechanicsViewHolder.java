package com.jhhscm.platform.fragment.search;

import android.view.View;

import com.jhhscm.platform.activity.h5.MechanicsH5Activity;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemMechanicsNewBinding;
import com.jhhscm.platform.event.ConsultationEvent;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.UrlUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

public class SearchNewMechanicsViewHolder extends AbsRecyclerViewHolder<SearchBean.DataBean> {

    private ItemMechanicsNewBinding mBinding;

    public SearchNewMechanicsViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemMechanicsNewBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final SearchBean.DataBean item) {
        if (item.getPic_url() != null) {
            ImageLoader.getInstance().displayImage(item.getPic_url(), mBinding.im);
        }

        mBinding.tv1.setText(item.getName());
        mBinding.tv2.setText("铲斗容量：" + item.getFix_p_2() + " m³");
        mBinding.tv3.setText("额定功率：" + item.getFix_p_5() + " km/rpm");
        if (item.getSale_num() != null) {
            mBinding.tv5.setText("已售出：" + item.getSale_num() + "台");
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
