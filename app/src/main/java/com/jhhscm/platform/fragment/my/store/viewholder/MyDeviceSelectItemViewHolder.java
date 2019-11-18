package com.jhhscm.platform.fragment.my.store.viewholder;

import android.view.View;

import com.jhhscm.platform.R;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemStoreSelectDeviceBinding;
import com.jhhscm.platform.databinding.ItemStoreSelectMemberBinding;
import com.jhhscm.platform.event.FinishEvent;
import com.jhhscm.platform.fragment.home.bean.GetPageArticleListBean;
import com.jhhscm.platform.fragment.my.mechanics.FindGoodsOwnerBean;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.views.selector.ImageSelectorItem;
import com.jhhscm.platform.views.selector.ImageSelectorPreviewActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class MyDeviceSelectItemViewHolder extends AbsRecyclerViewHolder<FindGoodsOwnerBean.DataBean> {

    private ItemStoreSelectDeviceBinding mBinding;

    public MyDeviceSelectItemViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemStoreSelectDeviceBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final FindGoodsOwnerBean.DataBean item) {
        mBinding.tvName.setText(item.getName());
        mBinding.tv1.setText(item.getBrand_name());
        mBinding.tv2.setText(item.getFixp17());
        mBinding.tv3.setText(item.getFcatory_time());
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