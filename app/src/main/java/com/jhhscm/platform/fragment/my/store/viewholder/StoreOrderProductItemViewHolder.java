package com.jhhscm.platform.fragment.my.store.viewholder;

import android.view.View;

import com.jhhscm.platform.R;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemStoreOrderProductBinding;
import com.jhhscm.platform.databinding.ItemStoreProductBinding;
import com.jhhscm.platform.fragment.home.bean.GetPageArticleListBean;
import com.jhhscm.platform.tool.ToastUtil;

public class StoreOrderProductItemViewHolder extends AbsRecyclerViewHolder<GetPageArticleListBean.DataBean> {

    private ItemStoreOrderProductBinding mBinding;
    private boolean isSelect;

    public StoreOrderProductItemViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemStoreOrderProductBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final GetPageArticleListBean.DataBean item) {

        if (isSelect) {
            mBinding.tvSelect.setImageResource(R.mipmap.ic_shoping_s1);
        } else {
            mBinding.tvSelect.setImageResource(R.mipmap.ic_shoping_s);
        }

        mBinding.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSelect) {
                    isSelect = false;
                    mBinding.tvSelect.setImageResource(R.mipmap.ic_shoping_s);
                } else {
                    isSelect = true;
                    mBinding.tvSelect.setImageResource(R.mipmap.ic_shoping_s1);
                }

            }
        });

        mBinding.imJia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(mBinding.tvNum.getText().toString()) + 1;
                mBinding.tvNum.setText(num + "");
            }
        });

        mBinding.imJian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(mBinding.tvNum.getText().toString()) > 1) {
                    int num = Integer.parseInt(mBinding.tvNum.getText().toString()) - 1;
                    mBinding.tvNum.setText(num + "");

                } else {
                    ToastUtil.show(itemView.getContext(), "商品数量不能为空！");
                }
            }
        });

    }
}