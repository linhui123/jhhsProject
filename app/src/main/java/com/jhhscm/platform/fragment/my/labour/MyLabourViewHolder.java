package com.jhhscm.platform.fragment.my.labour;

import android.view.View;

import com.jhhscm.platform.activity.LabourDetailActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemLabourBinding;
import com.jhhscm.platform.databinding.ItemMyLabourBinding;
import com.jhhscm.platform.fragment.labour.FindLabourReleaseListBean;

public class MyLabourViewHolder extends AbsRecyclerViewHolder<FindLabourListBean.DataBean> {

    private ItemMyLabourBinding mBinding;

    public MyLabourViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemMyLabourBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final FindLabourListBean.DataBean item) {
        if (item.getType().equals("0")) {//0招聘；1求职
            mBinding.tvType.setText("招聘");
        } else {
            mBinding.tvType.setText("求职");
        }
        mBinding.tvName.setText(item.getName());
        if (item.getUpdate_time() != null && item.getUpdate_time().length() > 10) {
            mBinding.tvData.setText("更新时间：" + item.getUpdate_time().substring(0, 10));
        }
        mBinding.tvNum.setText("关注次数：" + item.getNum());
        mBinding.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FindLabourReleaseListBean.DataBean dataBean = new FindLabourReleaseListBean.DataBean(item.getId(), item.getType(), item.getLabour_code());
                LabourDetailActivity.start(itemView.getContext(), 1, dataBean);
            }
        });
    }
}