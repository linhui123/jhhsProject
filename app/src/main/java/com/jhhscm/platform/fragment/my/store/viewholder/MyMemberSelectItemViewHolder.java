package com.jhhscm.platform.fragment.my.store.viewholder;

import android.view.View;

import com.jhhscm.platform.R;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemStoreMemberBinding;
import com.jhhscm.platform.databinding.ItemStoreSelectMemberBinding;
import com.jhhscm.platform.event.FinishEvent;
import com.jhhscm.platform.fragment.home.bean.GetPageArticleListBean;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtil;

public class MyMemberSelectItemViewHolder extends AbsRecyclerViewHolder<GetPageArticleListBean.DataBean> {

    private ItemStoreSelectMemberBinding mBinding;
    private boolean isSelect;

    public MyMemberSelectItemViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemStoreSelectMemberBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final GetPageArticleListBean.DataBean item) {


        if (isSelect) {
            mBinding.tvSelect.setImageResource(R.mipmap.ic_shoping_s1);
        } else {
            mBinding.tvSelect.setImageResource(R.mipmap.ic_shoping_s);
        }

        mBinding.llMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //遍历单选
                if (isSelect) {
                    isSelect = false;
                    mBinding.tvSelect.setImageResource(R.mipmap.ic_shoping_s);
                } else {
                    isSelect = true;
                    mBinding.tvSelect.setImageResource(R.mipmap.ic_shoping_s1);
                }
                EventBusUtil.post(new FinishEvent(1));
            }
        });
    }
}