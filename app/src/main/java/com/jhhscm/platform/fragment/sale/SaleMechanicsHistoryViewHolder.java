package com.jhhscm.platform.fragment.sale;

import android.view.View;

import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemSaleMechanicsHistoryBinding;
import com.jhhscm.platform.tool.CalculationUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class SaleMechanicsHistoryViewHolder extends AbsRecyclerViewHolder<SaleItem> {

    private ItemSaleMechanicsHistoryBinding mBinding;

    public SaleMechanicsHistoryViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemSaleMechanicsHistoryBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final SaleItem item) {
        if (item.dataBean != null) {
            final OldGoodOrderHistoryBean.DataBean dataBean = item.dataBean;
            ImageLoader.getInstance().displayImage(dataBean.getPic_url(), mBinding.im);
            mBinding.tv1.setText(dataBean.getName());
            String data = dataBean.getFactory_time() == null ? "" : dataBean.getFactory_time() + "年 | ";
            String Old_time = dataBean.getOld_time() == null ? "" : dataBean.getOld_time() + "小时 | ";
            String Province = dataBean.getProvince() == null ? "" : dataBean.getProvince() + "-";
            String City = dataBean.getCity() == null ? "" : dataBean.getCity();
            mBinding.tv2.setText(data + Old_time + Province + City);

            String Counter_price = dataBean.getCounter_price() == null ? "" : CalculationUtils.wan(dataBean.getCounter_price()) + "  ";
            String Retail_price = dataBean.getRetail_price() == null ? "" : "首付" + CalculationUtils.wan(dataBean.getRetail_price());
            mBinding.tv3.setText(Counter_price + Retail_price);
        }
    }

}
