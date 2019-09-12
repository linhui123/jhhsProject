package com.jhhscm.platform.fragment.lessee;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.BrandActivity;
import com.jhhscm.platform.activity.LabourDetailActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemLabourBinding;
import com.jhhscm.platform.databinding.ItemLesseeMechanicsBinding;
import com.jhhscm.platform.fragment.Mechanics.action.FindBrandAction;
import com.jhhscm.platform.fragment.Mechanics.bean.FindBrandBean;
import com.jhhscm.platform.fragment.Mechanics.bean.GetComboBoxBean;
import com.jhhscm.platform.fragment.labour.FindLabourReleaseListBean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.dialog.DropTDialog;
import com.jhhscm.platform.views.timePickets.TimePickerShow;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

import static com.umeng.socialize.utils.ContextUtil.getContext;

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
        mBinding.isSchemeImage.setPosition(getAdapterPosition());
        mBinding.tvBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DropTDialog(itemView.getContext(), "品牌", list, new DropTDialog.CallbackListener() {
                    @Override
                    public void clickResult(String id, String Nmae) {
                        mBinding.tvBrand.setText(Nmae);
                        item.setBrandId(id);
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
                item.setMachinePrice(s.toString().trim());
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
    }
}
