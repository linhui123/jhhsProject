package com.jhhscm.platform.fragment.my.mechanics;

import android.view.View;

import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.AddDeviceActivity;
import com.jhhscm.platform.activity.Lessee1Activity;
import com.jhhscm.platform.activity.PushOldMechanicsActivity;
import com.jhhscm.platform.activity.h5.MechanicsH5Activity;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.bean.PbImage;
import com.jhhscm.platform.databinding.ItemDeviceBinding;
import com.jhhscm.platform.databinding.ItemMechanicsOldBinding;
import com.jhhscm.platform.event.DelDeviceEvent;
import com.jhhscm.platform.tool.CalculationUtils;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.tool.UrlUtils;
import com.jhhscm.platform.views.selector.ImageSelector;
import com.jhhscm.platform.views.selector.ImageSelectorItem;
import com.jhhscm.platform.views.selector.ImageSelectorPreviewActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MyMechanicsViewHolder extends AbsRecyclerViewHolder<FindGoodsOwnerBean.DataBean> {

    private ItemDeviceBinding mBinding;

    public MyMechanicsViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemDeviceBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final FindGoodsOwnerBean.DataBean item) {
        if (item != null) {
            mBinding.tvName.setText(item.getName());
            mBinding.tv1.setText("品牌：" + item.getBrand_name());
            mBinding.tv2.setText("型号：" + item.getFixp17());
            if (item.getFcatory_time() != null && item.getFcatory_time().length() >= 10) {
                mBinding.tv3.setText("出厂时间：" + item.getFcatory_time().substring(0, 10));
            } else {
                mBinding.tv3.setText("出厂时间：" + item.getFcatory_time());
            }

            mBinding.tvType.setText(item.getStatus() + "");
            if (item.getPic_gallery_url_list() != null && item.getPic_gallery_url_list().length() > 10) {
                String listString = item.getPic_gallery_url_list().replace("[\"", "").replace("\"]", "");
                final String[] strs = listString.split("\",\"");
                if (strs.length > 0) {
                    ImageLoader.getInstance().displayImage(strs[0].trim(), mBinding.im);
                    mBinding.num.setText(strs.length + "张");
                    mBinding.num.setVisibility(View.VISIBLE);
                    mBinding.im.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ArrayList<ImageSelectorItem> items = new ArrayList<>();
                            for (int i = 0; i < strs.length; i++) {
                                ImageSelectorItem imageSelectorItem = new ImageSelectorItem();
                                imageSelectorItem.imageToken = strs[i].trim();
                                imageSelectorItem.imageUrl = strs[i].trim();
                                items.add(imageSelectorItem);
                            }
                            ImageSelectorPreviewActivity.startActivity(itemView.getContext(), 0, items, 0);

                        }
                    });
                }else {
                    mBinding.num.setText("0张");
                    mBinding.num.setVisibility(View.INVISIBLE);
                    mBinding.im.setImageResource(R.mipmap.ic_site);
                }
            }else {
                mBinding.num.setText("0张");
                mBinding.num.setVisibility(View.INVISIBLE);
                mBinding.im.setImageResource(R.mipmap.ic_site);
            }

            mBinding.tvFunc1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.show(itemView.getContext(), "租赁");
                    Lessee1Activity.start(itemView.getContext());
                }

            });

            mBinding.tvFunc2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.show(itemView.getContext(), "卖车");
                    PushOldMechanicsActivity.start(itemView.getContext());
                }
            });
            mBinding.tvFunc3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddDeviceActivity.start(itemView.getContext(), 1, item);
                }
            });

            mBinding.tvFunc4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBusUtil.post(new DelDeviceEvent(item.getCode()));
                    ToastUtil.show(itemView.getContext(), "删除");
                }
            });
        }

        //        ImageLoader.getInstance().displayImage(item.getPic_url(), mBinding.im);
//        mBinding.tv1.setText("品牌："+item.getName());
//        String data = item.getFactory_time() == null ? "" : item.getFactory_time() + "年 | ";
//        String Old_time = item.getOld_time() == null ? "" : item.getOld_time() + "小时 | ";
//        String Province = item.getProvince() == null ? "" : item.getProvince() + "-";
//        String City = item.getCity() == null ? "" : item.getCity();
//        mBinding.tv2.setText("型号："+data + Old_time + Province + City);
//
//        String Counter_price = item.getCounter_price() == null ? "" : CalculationUtils.wan(item.getCounter_price()) + "  ";
//        String Retail_price = "";
//        if (item.getRetail_price() != null && Double.parseDouble(item.getRetail_price()) != 0.0) {
//            Retail_price = "首付" + CalculationUtils.wan(item.getRetail_price());
//        } else {
//            Retail_price = "";
//        }
////        String Retail_price = item.getRetail_price() == null ? "" : "首付" + CalculationUtils.wan(item.getRetail_price());
//        mBinding.tv3.setText("出厂时间："+Counter_price + Retail_price);
//        mBinding.tvType.setText("");
//        if (item.getIs_sell() == 0) {
//            mBinding.tvType.setText("审核");
//        } else if (item.getIs_sell() == 1) {
//            mBinding.tvType.setText("已售出");
//        } else if (item.getIs_sell() == 2) {
//            mBinding.tvType.setText("正在销售");
//        }
//
//        mBinding.rl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String url = UrlUtils.ESJXQ + "&good_code=" + item.getGood_code();
//                MechanicsH5Activity.start(itemView.getContext(), url, "二手机详情", item.getGood_code(), item.getName(), item.getPic_url(), 2);
//            }
//        });
    }
}
