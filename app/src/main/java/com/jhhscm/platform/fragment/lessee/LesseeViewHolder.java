package com.jhhscm.platform.fragment.lessee;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.bean.PbImage;
import com.jhhscm.platform.databinding.ItemLesseeMechanicsBinding;
import com.jhhscm.platform.fragment.Mechanics.bean.GetComboBoxBean;
import com.jhhscm.platform.tool.StringUtils;
import com.jhhscm.platform.views.dialog.AddressDialog;
import com.jhhscm.platform.views.dialog.DropTDialog;
import com.jhhscm.platform.views.timePickets.TimePickerShow;

import java.util.ArrayList;
import java.util.List;

public class LesseeViewHolder extends AbsRecyclerViewHolder<LesseeBean.WBankLeaseItemsBean> {

    private ItemLesseeMechanicsBinding mBinding;
    private List<GetComboBoxBean.ResultBean> list;

    public LesseeViewHolder(View itemView, List<GetComboBoxBean.ResultBean> list) {
        super(itemView);
        this.list = list;
        mBinding = ItemLesseeMechanicsBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final LesseeBean.WBankLeaseItemsBean item) {
        if (item != null) {
            // * m1 购买价格   * m2 发动机号    * m3 车架号   * m4 小时数   * m5 施工区域    * m6 施工类型
            mBinding.etName.setText(item.getName());
            mBinding.etXinghao.setText(item.getFixP17());
            mBinding.tvBrand.setText(item.getBrandName());
            mBinding.etNo.setText(item.getMachineNum());
            mBinding.etPrice.setText(item.getMachinePrice());
            mBinding.tvBrand.setText(item.getBrandName());
            mBinding.etM1.setText(item.getM1());
            mBinding.etM2.setText(item.getM2());
            mBinding.etM3.setText(item.getM3());
            mBinding.etM4.setText(item.getM4());
            mBinding.tvM5.setText(item.getM5());
            mBinding.etM6.setText(item.getM6());

            if (item.getFactoryTime() != null && item.getFactoryTime().length() > 10) {
                mBinding.tvData.setText(item.getFactoryTime().substring(0, 10));
                item.setFactoryTime(item.getFactoryTime().substring(0, 10));
            } else {
                mBinding.tvData.setText(item.getFactoryTime());
                item.setFactoryTime(item.getFactoryTime());
            }
            if (item.getItemUrl() != null && item.getItemUrl().length() > 10) {
                List<PbImage> items = new ArrayList<>();
                String listString = item.getItemUrl().replace("[\"", "")
                        .replace("\"]", "")
                        .replace("\"", "");
                String[] strs = listString.split(",");
                if (strs.length > 0) {
                    for (int i = 0; i < strs.length; i++) {
                        PbImage pbImage = new PbImage();
                        pbImage.setmUrl(strs[i].trim());
                        pbImage.setmToken(strs[i].trim());
                        items.add(pbImage);
                    }
                    mBinding.isSchemeImage.setPbImageList(items);
                } else {
                    mBinding.isSchemeImage.setPbImageList(items);
                }
            }
        }
        mBinding.isSchemeImage.setPosition(getAdapterPosition());
        mBinding.tvBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DropTDialog(itemView.getContext(), "品牌", list, new DropTDialog.CallbackListener() {
                    @Override
                    public void clickResult(String id, String Nmae) {
                        mBinding.tvBrand.setText(Nmae);
                        item.setBrandId(id);
                        item.setBrandName(Nmae);
                    }
                }).show();
            }
        });

        mBinding.tvData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerShow timePickerShow = new TimePickerShow(itemView.getContext());
                timePickerShow.timePickerAlertDialogs("", 2);
                timePickerShow.setOnTimePickerListener(new TimePickerShow.OnTimePickerListener() {
                    @Override
                    public void onClicklistener(String dataTime) {
                        mBinding.tvData.setText(dataTime.trim());
                        item.setFactoryTime(dataTime.trim());
                    }
                });
            }
        });

        mBinding.etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.setName(s.toString().trim());
            }
        });

        mBinding.etNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.setMachineNum(s.toString().trim());
            }
        });

        mBinding.etPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                item.setMachinePrice(StringUtils.getValue(s.toString().trim()));
                if (!StringUtils.touzi_ed_values.equals(mBinding.etPrice.getText().toString().trim())) {
                    mBinding.etPrice.setText(StringUtils.addComma(
                            mBinding.etPrice.getText().toString().trim().replaceAll(",", ""),
                            mBinding.etPrice));
                    mBinding.etPrice.setSelection(StringUtils.addComma(
                            mBinding.etPrice.getText().toString().trim().replaceAll(",", ""),
                            mBinding.etPrice).length());
                }
            }
        });

        mBinding.etXinghao.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.setFixP17(s.toString().trim());
            }
        });
        mBinding.etM1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.setM1(s.toString().trim());
                if (!StringUtils.touzi_ed_values.equals(mBinding.etM1.getText().toString().trim())) {
                    mBinding.etM1.setText(StringUtils.addComma(
                            mBinding.etM1.getText().toString().trim().replaceAll(",", ""),
                            mBinding.etM1));
                    mBinding.etM1.setSelection(StringUtils.addComma(
                            mBinding.etM1.getText().toString().trim().replaceAll(",", ""),
                            mBinding.etM1).length());
                }
            }
        });
        mBinding.etM2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.setM2(s.toString().trim());
            }
        });
        mBinding.etM3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.setM3(s.toString().trim());
            }
        });
        mBinding.etM4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.setM4(s.toString().trim());
            }
        });
        mBinding.etM6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.setM6(s.toString().trim());
            }
        });

        mBinding.tvM5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddressDialog(itemView.getContext(), "施工区月", new AddressDialog.CallbackListener() {
                    @Override
                    public void clickResult(String pid, String pNmae, String cityId, String cName, String countryID, String countryName) {
                        mBinding.tvM5.setText(pNmae + " " + cName + " " + countryName);
                        item.setM5(pNmae + " " + cName + " " + countryName);
                    }
                }).show();
            }
        });
    }
}

