package com.jhhscm.platform.fragment.my.mechanics;

import android.view.View;

import com.jhhscm.platform.activity.h5.MechanicsH5Activity;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemMechanicsOldBinding;
import com.jhhscm.platform.tool.CalculationUtils;
import com.jhhscm.platform.tool.UrlUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class MyMechanicsViewHolder extends AbsRecyclerViewHolder<FindOldGoodByUserCodeBean.DataBean> {

    private ItemMechanicsOldBinding mBinding;

    public MyMechanicsViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemMechanicsOldBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final FindOldGoodByUserCodeBean.DataBean item) {
        ImageLoader.getInstance().displayImage(item.getPic_url(), mBinding.im);
        mBinding.tv1.setText(item.getName());
        String data = item.getFactory_time() == null ? "" : item.getFactory_time() + "年 | ";
        String Old_time = item.getOld_time() == null ? "" : item.getOld_time() + "小时 | ";
        String Province = item.getProvince() == null ? "" : item.getProvince() + "-";
        String City = item.getCity() == null ? "" : item.getCity();
        mBinding.tv2.setText(data + Old_time + Province + City);

        String Counter_price = item.getCounter_price() == null ? "" : CalculationUtils.wan(item.getCounter_price()) + "  ";
        String Retail_price = "";
        if (item.getRetail_price() != null && Double.parseDouble(item.getRetail_price()) != 0.0) {
            Retail_price = "首付" + CalculationUtils.wan(item.getRetail_price());
        } else {
            Retail_price = "";
        }
//        String Retail_price = item.getRetail_price() == null ? "" : "首付" + CalculationUtils.wan(item.getRetail_price());
        mBinding.tv3.setText(Counter_price + Retail_price);
        mBinding.tv4.setText("");
        if (item.getIs_sell() == 0) {
            mBinding.tv4.setText("审核");
        } else if (item.getIs_sell() == 1) {
            mBinding.tv4.setText("已售出");
        } else if (item.getIs_sell() == 2) {
            mBinding.tv4.setText("正在销售");
        }

        mBinding.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = UrlUtils.ESJXQ + "&good_code=" + item.getGood_code();
                MechanicsH5Activity.start(itemView.getContext(), url, "二手机详情", item.getGood_code(), item.getName(), item.getPic_url(), 2);
            }
        });
    }
}
