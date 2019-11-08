package com.jhhscm.platform.fragment.my.store.viewholder;

import android.view.View;

import com.jhhscm.platform.activity.ServiceRecordActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemInviteBinding;
import com.jhhscm.platform.databinding.ItemStoreMemberBinding;
import com.jhhscm.platform.fragment.home.bean.GetPageArticleListBean;
import com.jhhscm.platform.fragment.invitation.ReqListBean;
import com.jhhscm.platform.tool.ToastUtil;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;

public class MyMemberItemViewHolder extends AbsRecyclerViewHolder<ReqListBean.ResultBean.DataBean> {

    private ItemStoreMemberBinding mBinding;

    public MyMemberItemViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemStoreMemberBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final ReqListBean.ResultBean.DataBean item) {
        mBinding.llMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show(itemView.getContext(), "查看");
                ServiceRecordActivity.start(itemView.getContext(),item.getBus_code());
            }
        });
    }
}
