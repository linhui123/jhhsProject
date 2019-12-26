package com.jhhscm.platform.fragment.Mechanics.holder;

import android.view.View;

import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.h5.MechanicsH5Activity;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemMechanicsOldBinding;
import com.jhhscm.platform.event.ConsultationEvent;
import com.jhhscm.platform.fragment.Mechanics.bean.GetOldPageListBean;
import com.jhhscm.platform.tool.CalculationUtils;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.UrlUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

public class OldMechanicsViewHolder extends AbsRecyclerViewHolder<GetOldPageListBean.DataBean> {

    private ItemMechanicsOldBinding mBinding;

    public OldMechanicsViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemMechanicsOldBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final GetOldPageListBean.DataBean item) {
        if (item != null) {
            if (item.getIs_sell().equals("1")) {//已成交
                mBinding.im2.setVisibility(View.VISIBLE);
            } else {
                mBinding.im2.setVisibility(View.GONE);
            }

            if (item.getPic_url() != null) {
                ImageLoader.getInstance().displayImage(item.getPic_url(), mBinding.im);
            } else {
                mBinding.im.setImageResource(R.mipmap.ic_site);
            }
            mBinding.tv1.setText(item.getName());
            String data = item.getFactory_time() == null ? "" : item.getFactory_time() + "年 | ";
            String Old_time = item.getOld_time() == null ? "" : item.getOld_time() + "小时 | ";
            String Province = item.getProvince() == null ? "" : item.getProvince() + "-";
            String City = item.getCity() == null ? "" : item.getCity();
            mBinding.tv2.setText(data + Old_time + Province + City);

            String Counter_price = item.getCounter_price() == null ? "" : CalculationUtils.wan(item.getCounter_price()) + "  ";
            String Retail_price = item.getRetail_price() == null ? "" : "首付" + CalculationUtils.wan(item.getRetail_price());
            mBinding.tv3.setText(Counter_price + Retail_price);
            mBinding.rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = UrlUtils.ESJXQ + "&good_code=" + item.getGood_code();
                    MechanicsH5Activity.start(itemView.getContext(), url, "二手机详情", item.getGood_code(), item.getName(), item.getPic_url(), 2);
                }
            });

            mBinding.tv4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBusUtil.post(new ConsultationEvent(3));
                }
            });
        }

    }

}

