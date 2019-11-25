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
import com.jhhscm.platform.fragment.my.store.action.FindUserGoodsOwnerBean;
import com.jhhscm.platform.views.dialog.DropTDialog;
import com.jhhscm.platform.views.timePickets.TimePickerShow;

import java.util.List;
import java.util.jar.Attributes;

public class ItemFaultDeviceViewHolder extends AbsRecyclerViewHolder<FindUserGoodsOwnerBean.DataBean> {

    private ItemFaultDevicesBinding mBinding;
    private List<GetComboBoxBean.ResultBean> brandList;
    private GetComboBoxBean errorList;

    public ItemFaultDeviceViewHolder(View itemView, List<GetComboBoxBean.ResultBean> brandList, GetComboBoxBean errorList) {
        super(itemView);
        this.brandList = brandList;
        this.errorList = errorList;
        mBinding = ItemFaultDevicesBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final FindUserGoodsOwnerBean.DataBean item) {
        if (this.getAdapterPosition() == 0) {
            mBinding.tvShadow.setVisibility(View.GONE);
        }
        if (item != null) {
            mBinding.brand.setText(item.getBrand_name());
            mBinding.model.setText(item.getFixp17());
        }

        if (brandList != null && brandList.size() > 0) {
            mBinding.brand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DropTDialog(itemView.getContext(), "品牌", brandList, new DropTDialog.CallbackListener() {
                        @Override
                        public void clickResult(String id, String Nmae) {
                            mBinding.brand.setText(Nmae);
                            mBinding.brand.setTag(id);
                            item.setBrand_id(id);
                            item.setBrand_name(Nmae);
                        }
                    }).show();
                }
            });
        }
        if (errorList != null && errorList.getResult().size() > 0) {
            mBinding.fault.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DropTDialog(itemView.getContext(), "故障类型", errorList.getResult(), new DropTDialog.CallbackListener() {
                        @Override
                        public void clickResult(String id, String Nmae) {
                            mBinding.fault.setText(Nmae);
                            mBinding.fault.setTag(id);
                            item.setError_no(id);
                        }
                    }).show();
                }
            });
        }

        mBinding.model.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.setFixp17(s.toString().trim());
            }
        });

        mBinding.no.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.setNo(s.toString().trim());
            }
        });

        mBinding.gpsNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.setGps_no(s.toString().trim());
            }
        });
    }
}

