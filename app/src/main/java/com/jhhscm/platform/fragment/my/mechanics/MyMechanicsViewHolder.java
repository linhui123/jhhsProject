package com.jhhscm.platform.fragment.my.mechanics;

import android.view.View;

import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.AddDeviceActivity;
import com.jhhscm.platform.activity.Lessee1Activity;
import com.jhhscm.platform.activity.PushOldMechanicsActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemDeviceBinding;
import com.jhhscm.platform.event.DelDeviceEvent;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.views.selector.ImageSelectorItem;
import com.jhhscm.platform.views.selector.ImageSelectorPreviewActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

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
            if (item.getFcatory_time() != null) {
                if (item.getFcatory_time().length() >= 10) {
                    mBinding.tv3.setText("出厂时间：" + item.getFcatory_time().substring(0, 10));
                } else {
                    mBinding.tv3.setText("出厂时间：" + item.getFcatory_time());
                }
            } else {
                mBinding.tv3.setText("出厂时间：--");
            }

            if (item.getStatus() == 0) {
                mBinding.tvType.setText("状态：未使用");
            } else {
                mBinding.tvType.setText("状态：使用中");
            }

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
                } else {
                    mBinding.num.setText("0张");
                    mBinding.num.setVisibility(View.INVISIBLE);
                    mBinding.im.setImageResource(R.mipmap.ic_site);
                }
            } else {
                mBinding.num.setText("0张");
                mBinding.num.setVisibility(View.INVISIBLE);
                mBinding.im.setImageResource(R.mipmap.ic_site);
            }

            mBinding.tvFunc1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Lessee1Activity.start(itemView.getContext(), item);
                }

            });

            mBinding.tvFunc2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PushOldMechanicsActivity.start(itemView.getContext(), item);
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
    }
}
