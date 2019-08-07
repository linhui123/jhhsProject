package com.jhhscm.platform.fragment.casebase;


import android.view.View;

import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemCaseBaseBinding;
import com.jhhscm.platform.tool.BlsAppUtils;
import com.jhhscm.platform.tool.DateUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Administrator on 2017/11/29.
 */

public class CaseBaseViewHolder extends AbsRecyclerViewHolder<FindCostomerByNameMhItem> {

    private ItemCaseBaseBinding mBinding;

    public CaseBaseViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemCaseBaseBinding.bind(itemView);
    }


    @Override
    protected void onBindView(final FindCostomerByNameMhItem item) {
        final FindCostomerByNameMhEntity.CUSTOMERDETAILSBean customerdetailsBean = item.customerdetailsBean;
        if (customerdetailsBean != null) {
            mBinding.tvName.setText(customerdetailsBean.getNAME());
            if (DateUtils.daysBetween(customerdetailsBean.getTIME(), DateUtils.getCurDate("yyyy-MM-dd"),"yyyy-MM-dd") <= 3) {
                mBinding.imNew.setVisibility(View.VISIBLE);
            } else {
                mBinding.imNew.setVisibility(View.GONE);
            }
            mBinding.tvTime.setText(customerdetailsBean.getTIME());
            ImageLoader.getInstance().displayImage(customerdetailsBean.getIMG_URL(), mBinding.ivCaseBase, BlsAppUtils.getCaseBaseDisplayImageOptions());
            mBinding.tvNumber.setText(customerdetailsBean.getP_SUM() + "张");
            mBinding.tvNumber.setVisibility(customerdetailsBean.getP_SUM() > 0 ? View.VISIBLE : View.GONE);
        }
        mBinding.llCaseBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                CaseBasePhotoActivity.start(itemView.getContext(), item.customerdetailsBean.getCASE_ID() + "", item.mType);
            }
        });

    }

}
