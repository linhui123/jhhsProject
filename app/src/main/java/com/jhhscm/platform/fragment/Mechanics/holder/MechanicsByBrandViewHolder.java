package com.jhhscm.platform.fragment.Mechanics.holder;

import android.view.View;

import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.MechanicsH5Activity;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemCompairsonSelectBinding;
import com.jhhscm.platform.databinding.ItemMechanicsBrandBinding;
import com.jhhscm.platform.event.BrandResultEvent;
import com.jhhscm.platform.event.CompMechanicsEvent;
import com.jhhscm.platform.event.FinishEvent;
import com.jhhscm.platform.fragment.Mechanics.bean.FindBrandBean;
import com.jhhscm.platform.fragment.Mechanics.bean.GetGoodsByBrandBean;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.UrlUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class MechanicsByBrandViewHolder extends AbsRecyclerViewHolder<GetGoodsByBrandBean.ResultBean.DataBean> {

    private ItemCompairsonSelectBinding mBinding;
    private int type = 0;//0 返回；1进入详情；

    public MechanicsByBrandViewHolder(View itemView, int t) {
        super(itemView);
        this.type = t;
        mBinding = ItemCompairsonSelectBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final GetGoodsByBrandBean.ResultBean.DataBean item) {
        if (type == 1) {
            mBinding.tvSelect.setVisibility(View.INVISIBLE);
        }
        mBinding.tvTitle.setText(item.getName());
        mBinding.tvPrice.setText(wan(item.getCounterPrice()));
        mBinding.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 0) {
                    mBinding.tvSelect.setBackgroundResource(R.mipmap.ic_shoping_s1);
                    EventBusUtil.post(new CompMechanicsEvent(item));
                    EventBusUtil.post(new BrandResultEvent(item.getBrandId() + "", item.getId() + "", item.getName()));
                    EventBusUtil.post(new FinishEvent());
                } else {
                    String url = UrlUtils.XJXQ + "&good_code=" + item.getCode();
                    MechanicsH5Activity.start(itemView.getContext(), url, "新机详情", item.getCode(), item.getName(), item.getPicUrl(),1);
                }
            }
        });
    }

    private String wan(String toal) {
        DecimalFormat df = new DecimalFormat("#.0000");
        toal = df.format(Double.parseDouble(toal) / 10000);
        //保留2位小数
        BigDecimal b = new BigDecimal(Double.parseDouble(toal));
        if (b.compareTo(new BigDecimal(Double.parseDouble("0"))) == 0) {
            toal = "暂无报价";
        } else {
            toal = b.setScale(2, BigDecimal.ROUND_DOWN).toString() + "万";
        }
        return toal;
    }
}
