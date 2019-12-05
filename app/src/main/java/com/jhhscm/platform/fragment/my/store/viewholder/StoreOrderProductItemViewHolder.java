package com.jhhscm.platform.fragment.my.store.viewholder;

import android.view.View;

import com.jhhscm.platform.R;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemStoreOrderProductBinding;
import com.jhhscm.platform.event.RefreshEvent;
import com.jhhscm.platform.fragment.my.store.action.BusinessFindcategorybyBuscodeBean;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtil;

public class StoreOrderProductItemViewHolder extends AbsRecyclerViewHolder<BusinessFindcategorybyBuscodeBean.DataBean> {

    private ItemStoreOrderProductBinding mBinding;

    public StoreOrderProductItemViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemStoreOrderProductBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final BusinessFindcategorybyBuscodeBean.DataBean item) {

        if (item.isSelect()) {
            mBinding.tvSelect.setImageResource(R.mipmap.ic_shoping_s1);
        } else {
            mBinding.tvSelect.setImageResource(R.mipmap.ic_shoping_s);
        }

        mBinding.name.setText(item.getName());
        mBinding.brand.setText(item.getBrandName());
        mBinding.tvNum.setText(item.getNum() + "");
        if (item.getCategoryName() != null) {
            mBinding.type.setText("类型：" + item.getCategoryName());
        }
        mBinding.price.setText("品牌：" + item.getCounter_price() + "");


        mBinding.tvSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.isSelect()) {
                    item.setSelect(false);
                    mBinding.tvSelect.setImageResource(R.mipmap.ic_shoping_s);
                } else {
                    item.setSelect(true);
                    mBinding.tvSelect.setImageResource(R.mipmap.ic_shoping_s1);
                    item.setNum(Integer.parseInt(mBinding.tvNum.getText().toString()));
                }
                EventBusUtil.post(new RefreshEvent());
            }
        });

        mBinding.imJia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(mBinding.tvNum.getText().toString()) + 1;
                mBinding.tvNum.setText(num + "");
                item.setNum(num);
                EventBusUtil.post(new RefreshEvent());
            }
        });

        mBinding.imJian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(mBinding.tvNum.getText().toString()) > 1) {
                    int num = Integer.parseInt(mBinding.tvNum.getText().toString()) - 1;
                    mBinding.tvNum.setText(num + "");
                    item.setNum(num);
                    EventBusUtil.post(new RefreshEvent());
                } else {
                    ToastUtil.show(itemView.getContext(), "商品数量不能为空！");
                }
            }
        });

    }
}