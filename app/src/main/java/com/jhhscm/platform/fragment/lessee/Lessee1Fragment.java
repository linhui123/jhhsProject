package com.jhhscm.platform.fragment.lessee;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jhhscm.platform.activity.Lessee2Activity;
import com.jhhscm.platform.databinding.FragmentLessee1Binding;
import com.jhhscm.platform.event.LesseeFinishEvent;
import com.jhhscm.platform.fragment.Mechanics.bean.GetComboBoxBean;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.my.mechanics.FindGoodsOwnerBean;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.tool.UdaUtils;
import com.jhhscm.platform.views.dialog.DropTDialog;

import java.util.ArrayList;
import java.util.List;

public class Lessee1Fragment extends AbsFragment<FragmentLessee1Binding> {
    private LesseeBean lesseeBean;
    private LesseeBean.WBankLeasePersonBean personBean;
    private FindGoodsOwnerBean.DataBean dataBean;

    public static Lessee1Fragment instance() {
        Lessee1Fragment view = new Lessee1Fragment();
        return view;
    }

    @Override
    protected FragmentLessee1Binding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentLessee1Binding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        EventBusUtil.registerEvent(this);
        dataBean = (FindGoodsOwnerBean.DataBean) getArguments().getSerializable("data");
        lesseeBean = new LesseeBean();
        personBean = new LesseeBean.WBankLeasePersonBean();
        mDataBinding.tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDataBinding.etName.getText().toString().trim().length() > 0) {
                    personBean.setName(mDataBinding.etName.getText().toString().trim());
                    if (mDataBinding.tvSex.getText().toString().trim().length() > 0) {
                        if (mDataBinding.etId.getText().toString().trim().length() > 0) {
                            personBean.setIdCard(mDataBinding.etId.getText().toString().trim());
                            if (mDataBinding.tvMarray.getText().toString().trim().length() > 0) {
                                if (mDataBinding.etPhone.getText().toString().trim().length() > 0) {
                                    if (UdaUtils.isMobile(mDataBinding.etPhone.getText().toString().trim())) {
                                        personBean.setPhone(mDataBinding.etPhone.getText().toString().trim());
                                        if (mDataBinding.etAddress.getText().toString().trim().length() > 0) {
                                            personBean.setIdCardAddress(mDataBinding.etAddress.getText().toString().trim());
                                            lesseeBean.setWBankLeasePerson(personBean);
                                            if (dataBean != null) {
                                                Lessee2Activity.start(getContext(), lesseeBean, dataBean);
                                            } else {
                                                Lessee2Activity.start(getContext(), lesseeBean);
                                            }
                                        } else {
                                            ToastUtil.show(getActivity(), "通讯地址不能为空");
                                        }
                                    } else {
                                        ToastUtil.show(getActivity(), "手机号格式错误");
                                    }
                                } else {
                                    ToastUtil.show(getActivity(), "手机号不能为空");
                                }
                            } else {
                                ToastUtil.show(getActivity(), "婚姻情况不能为空");
                            }
                        } else {
                            ToastUtil.show(getActivity(), "身份证号不能为空");
                        }
                    } else {
                        ToastUtil.show(getActivity(), "性别不能为空");
                    }
                } else {
                    ToastUtil.show(getActivity(), "姓名不能为空");
                }
            }
        });

        mDataBinding.tvSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<GetComboBoxBean.ResultBean> list = new ArrayList<>();
                list.add(new GetComboBoxBean.ResultBean("0", "男"));
                list.add(new GetComboBoxBean.ResultBean("1", "女"));
                new DropTDialog(getActivity(), "性别", list, new DropTDialog.CallbackListener() {
                    @Override
                    public void clickResult(String id, String Nmae) {
                        mDataBinding.tvSex.setText(Nmae);
                        personBean.setSex(Integer.parseInt(id));
                    }
                }).show();
            }
        });

        mDataBinding.tvMarray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<GetComboBoxBean.ResultBean> list = new ArrayList<>();
                list.add(new GetComboBoxBean.ResultBean("1", "已婚"));
                list.add(new GetComboBoxBean.ResultBean("0", "未婚"));
                list.add(new GetComboBoxBean.ResultBean("2", "离异"));
                new DropTDialog(getActivity(), "婚姻情况", list, new DropTDialog.CallbackListener() {
                    @Override
                    public void clickResult(String id, String Nmae) {
                        mDataBinding.tvMarray.setText(Nmae);
                        personBean.setMarryType(Integer.parseInt(id));
                    }
                }).show();
            }
        });
    }

    public void onEvent(LesseeFinishEvent event) {
        getActivity().finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }
}
