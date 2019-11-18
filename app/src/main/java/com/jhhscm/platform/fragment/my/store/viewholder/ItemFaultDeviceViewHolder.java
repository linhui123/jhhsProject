package com.jhhscm.platform.fragment.my.store.viewholder;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemFaultDevicesBinding;
import com.jhhscm.platform.databinding.ItemLesseeMechanicsBinding;
import com.jhhscm.platform.fragment.Mechanics.bean.GetComboBoxBean;
import com.jhhscm.platform.fragment.lessee.LesseeBean;
import com.jhhscm.platform.fragment.my.mechanics.FindGoodsOwnerBean;
import com.jhhscm.platform.views.dialog.DropTDialog;
import com.jhhscm.platform.views.timePickets.TimePickerShow;

import java.util.List;

public class ItemFaultDeviceViewHolder extends AbsRecyclerViewHolder<FindGoodsOwnerBean.DataBean> {

    private ItemFaultDevicesBinding mBinding;

    public ItemFaultDeviceViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemFaultDevicesBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final FindGoodsOwnerBean.DataBean item) {
        if (this.getAdapterPosition() == 0) {
            mBinding.tvShadow.setVisibility(View.GONE);
        }

        if (item != null) {
            mBinding.brand.setText(item.getBrand_name());
            mBinding.model.setText(item.getFixp17());
        }
    }
}

