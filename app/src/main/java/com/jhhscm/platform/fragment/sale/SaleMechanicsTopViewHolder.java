package com.jhhscm.platform.fragment.sale;

import android.util.Log;
import android.view.View;

import com.jhhscm.platform.activity.AssessActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemSaleMechanicsTopBinding;
import com.jhhscm.platform.event.ConsultationEvent;
import com.jhhscm.platform.event.OrderSussessEvent;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtil;

public class SaleMechanicsTopViewHolder extends AbsRecyclerViewHolder<SaleItem> {

    private ItemSaleMechanicsTopBinding mBinding;

    public SaleMechanicsTopViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemSaleMechanicsTopBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final SaleItem item) {
        mBinding.tvPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBinding.etPhone.getText().toString().length()>8){
                    EventBusUtil.post(new OrderSussessEvent(mBinding.etPhone.getText().toString()));
                }else {
                    ToastUtil.show(itemView.getContext(),"请输入正确手机号");
                }
            }
        });

        mBinding.tvGujia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AssessActivity.start(itemView.getContext());
            }
        });
    }
}
