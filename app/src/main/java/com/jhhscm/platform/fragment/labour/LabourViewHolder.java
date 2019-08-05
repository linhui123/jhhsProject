package com.jhhscm.platform.fragment.labour;

import android.view.View;

import com.jhhscm.platform.activity.H5PeiJianActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemLabourBinding;
import com.jhhscm.platform.databinding.ItemMechanicsPeijianBinding;
import com.jhhscm.platform.fragment.Mechanics.bean.FindCategoryBean;
import com.jhhscm.platform.fragment.Mechanics.bean.GetOldPageListBean;
import com.jhhscm.platform.tool.UrlUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

public class LabourViewHolder extends AbsRecyclerViewHolder<GetOldPageListBean.DataBean> {

    private ItemLabourBinding mBinding;

    public LabourViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemLabourBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final GetOldPageListBean.DataBean item) {

    }
}

