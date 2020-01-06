package com.jhhscm.platform.fragment.Mechanics.holder;

import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.view.View;
import android.view.ViewGroup;

import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.StoreDetailActivity;
import com.jhhscm.platform.activity.h5.H5PeiJianActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemMechanicsPeijian2Binding;
import com.jhhscm.platform.fragment.Mechanics.bean.FindCategoryBean;
import com.jhhscm.platform.tool.DisplayUtils;
import com.jhhscm.platform.tool.UrlUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

public class PeiJian2ViewHolder extends AbsRecyclerViewHolder<FindCategoryBean.DataBean> {

    private ItemMechanicsPeijian2Binding mBinding;
    private boolean type = true;

    public PeiJian2ViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemMechanicsPeijian2Binding.bind(itemView);
    }

    @Override
    protected void onBindView(final FindCategoryBean.DataBean item) {

        ViewGroup.LayoutParams para = mBinding.im.getLayoutParams();
        para.height = (int) Math.ceil(DisplayUtils.getDeviceWidth(itemView.getContext()) * 0.35);
        mBinding.im.setLayoutParams(para);

        if (item.getPic_url() != null) {
            ImageLoader.getInstance().displayImage(item.getPic_url(), mBinding.im);
        } else {
            mBinding.im.setImageResource(R.mipmap.ic_site);
        }
        if (item.getBus_name() != null && item.getBus_name().length() > 0) {
            mBinding.tvStore.setText(item.getBus_name() + ">");
            mBinding.tvStore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (item.getBus_code() != null && item.getBus_code().length() > 0 && type) {
                        StoreDetailActivity.start(itemView.getContext(), item.getBus_code(), "", "");
                    }
                }
            });
        } else {
            mBinding.tvStore.setText("自营");
        }
        if (item.getNum() != null && item.getNum().length() < 0 && Integer.parseInt(item.getNum()) == 0) {
            mBinding.im2.setVisibility(View.VISIBLE);
        } else {
            mBinding.im2.setVisibility(View.GONE);
        }
        mBinding.tv1.setText(item.getName());
        mBinding.tv2.setText(item.getCounter_price() != null ? "￥" + item.getCounter_price() : "￥ --");
        mBinding.tv3.setText(item.getSale_num() != null ? "已售 " + item.getSale_num() + " 件" : "已售 --件");
        mBinding.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = UrlUtils.PJXQ + "&good_code=" + item.getGood_code();
                H5PeiJianActivity.start(itemView.getContext(), url, "配件详情", item.getBus_code(), item.getBus_name(),
                        item.getName(), item.getGood_code(), item.getPic_url(), item.getCounter_price(), item.getNum(), 3);
            }
        });

        if (item.getOriginal_price() != null) {
            SpannableString content = new SpannableString("原价：" + item.getOriginal_price());
            content.setSpan(new StrikethroughSpan(), 3, content.length(), 0);
            mBinding.tv4.setText(content);
        }else {
            mBinding.tv4.setText("");
        }
    }
}