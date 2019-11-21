package com.jhhscm.platform.fragment.my.store.viewholder;

import android.view.View;

import com.jhhscm.platform.R;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemStoreSelectDeviceBinding;
import com.jhhscm.platform.databinding.ItemStoreSelectMemberBinding;
import com.jhhscm.platform.event.FinishEvent;
import com.jhhscm.platform.fragment.home.bean.GetPageArticleListBean;
import com.jhhscm.platform.fragment.my.mechanics.FindGoodsOwnerBean;
import com.jhhscm.platform.fragment.my.store.action.FindUserGoodsOwnerBean;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.views.selector.ImageSelectorItem;
import com.jhhscm.platform.views.selector.ImageSelectorPreviewActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class MyDeviceSelectItemViewHolder extends AbsRecyclerViewHolder<FindUserGoodsOwnerBean.DataBean> {

    private ItemStoreSelectDeviceBinding mBinding;

    public MyDeviceSelectItemViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemStoreSelectDeviceBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final FindUserGoodsOwnerBean.DataBean item) {
        mBinding.tvName.setText("设备名称：" + item.getName());
        mBinding.tv1.setText("品牌：" + item.getBrand_name());
        mBinding.tv2.setText("型号：" + item.getFixp17());
        if (item.getFcatory_time() != null) {
            if (item.getFcatory_time().length() > 10) {
                mBinding.tv3.setText("出厂时间：" + item.getFcatory_time().substring(0, 10));
            } else {
                mBinding.tv3.setText("出厂时间：" + item.getFcatory_time());
            }
        } else {
            mBinding.tv3.setText("出厂时间：");
        }

        if (item.getPic_gallery_url_list() != null && item.getPic_gallery_url_list().length() > 10) {
            String listString = item.getPic_gallery_url_list().replace("[\"", "").replace("\"]", "");
            final String[] strs = listString.split("\",\"");
            if (strs.length > 0) {
                ImageLoader.getInstance().displayImage(strs[0].trim(), mBinding.im);
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
                    }
                });
            } else {
                mBinding.im.setImageResource(R.mipmap.ic_site);
            }
        } else {
            mBinding.im.setImageResource(R.mipmap.ic_site);
        }

        if (item.isSelect()) {
            mBinding.tvSelect.setImageResource(R.mipmap.ic_shoping_s1);
        } else {
            mBinding.tvSelect.setImageResource(R.mipmap.ic_shoping_s);
        }
        mBinding.llDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //遍历单选
                if (item.isSelect()) {
                    item.setSelect(false);
                    mBinding.tvSelect.setImageResource(R.mipmap.ic_shoping_s);
                } else {
                    item.setSelect(true);
                    mBinding.tvSelect.setImageResource(R.mipmap.ic_shoping_s1);
                }
            }
        });
    }
}