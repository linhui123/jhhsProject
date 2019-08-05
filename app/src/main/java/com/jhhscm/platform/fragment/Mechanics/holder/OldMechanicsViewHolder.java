package com.jhhscm.platform.fragment.Mechanics.holder;

import android.view.View;

import com.jhhscm.platform.activity.H5Activity;
import com.jhhscm.platform.activity.MechanicsH5Activity;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemMechanicsOldBinding;
import com.jhhscm.platform.fragment.Mechanics.MechanicsItem;
import com.jhhscm.platform.fragment.Mechanics.bean.GetOldPageListBean;
import com.jhhscm.platform.fragment.home.AdBean;
import com.jhhscm.platform.fragment.home.HomePageItem;
import com.jhhscm.platform.tool.UrlUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class OldMechanicsViewHolder extends AbsRecyclerViewHolder<GetOldPageListBean.DataBean> {

    private ItemMechanicsOldBinding mBinding;

    public OldMechanicsViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemMechanicsOldBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final GetOldPageListBean.DataBean item) {
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
        mBinding.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getGood_code() != null) {
                    //                String url = UrlUtils.XJXQ + "&good_code=" + item.getGood_code();
                    //                H5Activity.start(itemView.getContext(), url, "新机详情");
                }
                String url = UrlUtils.ESJXQ + "&good_code=8";
                MechanicsH5Activity.start(itemView.getContext(), url, "二手机详情", "8", 2);
            }
        });

        mBinding.tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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

