package com.jhhscm.platform.fragment.my.mechanics;

import android.view.View;

import com.jhhscm.platform.activity.MechanicsH5Activity;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemMechanicsNewBinding;
import com.jhhscm.platform.databinding.ItemMechanicsOldBinding;
import com.jhhscm.platform.fragment.Mechanics.bean.GetGoodsPageListBean;
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

        String Counter_price = item.getCounter_price() == null ? "" : wan(item.getCounter_price()) + "  ";
        String Retail_price = item.getRetail_price() == null ? "" : "首付" + wan(item.getRetail_price());
        mBinding.tv3.setText(Counter_price + Retail_price);
        mBinding.tv4.setText("");
        if (item.getIs_sell() == 0) {
            mBinding.tv4.setText("审核");
        }else   if (item.getIs_sell() == 1) {
            mBinding.tv4.setText("已售出");
        }else   if (item.getIs_sell() ==2) {
            mBinding.tv4.setText("正在销售");
        }


//        mBinding.rl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String url = UrlUtils.ESJXQ + "&good_code=" + item.getGood_code();
//                MechanicsH5Activity.start(itemView.getContext(), url, "二手机详情", item.getGood_code(), 2);
//            }
//        });

//        mBinding.tv4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    private String wan(String toal) {
        DecimalFormat df = new DecimalFormat("#.0000");
        toal = df.format(Double.parseDouble(toal) / 10000);
        //保留2位小数
        BigDecimal b = new BigDecimal(Double.parseDouble(toal));
        toal = b.setScale(2, BigDecimal.ROUND_DOWN).toString() + "万";
        return toal;
    }
}
