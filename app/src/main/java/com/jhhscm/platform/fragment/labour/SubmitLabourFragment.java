package com.jhhscm.platform.fragment.labour;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.BuildConfig;
import com.jhhscm.platform.R;
import com.jhhscm.platform.databinding.FragmentSubmitLabourBinding;
import com.jhhscm.platform.event.FinishEvent;
import com.jhhscm.platform.fragment.Mechanics.action.GetComboBoxAction;
import com.jhhscm.platform.fragment.Mechanics.bean.GetComboBoxBean;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.dialog.AddressDialog;
import com.jhhscm.platform.views.dialog.DropTDialog;
import com.jhhscm.platform.views.dialog.LabourSubmitDialog;

import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

/**
 * 劳务信息提交
 */
public class SubmitLabourFragment extends AbsFragment<FragmentSubmitLabourBinding> {
    private int type = 0;
    private String pId = "";
    private String cId = "";

    public static SubmitLabourFragment instance() {
        SubmitLabourFragment view = new SubmitLabourFragment();
        return view;
    }

    @Override
    protected FragmentSubmitLabourBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentSubmitLabourBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        EventBusUtil.registerEvent(this);
        type = getArguments().getInt("type");
        initView();
        initClick();
    }

    private void initView() {
        if (type == 0) {//证书代办
            mDataBinding.rlTv1.setVisibility(View.VISIBLE);
            mDataBinding.tv11.setText("工种");
            mDataBinding.rlEt1.setVisibility(View.VISIBLE);
            mDataBinding.et11.setText("联系人");
            mDataBinding.rlEt2.setVisibility(View.VISIBLE);
            mDataBinding.et21.setText("联系方式");
            mDataBinding.rlEt3.setVisibility(View.VISIBLE);
            mDataBinding.et31.setText("年龄");
            mDataBinding.rlTv2.setVisibility(View.VISIBLE);
            mDataBinding.tv21.setText("所在地");
            mDataBinding.rlTv1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getComboBox("work_arg_1");//工种
                }
            });
            mDataBinding.rlTv2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //地址
                    new AddressDialog(getActivity(), "所在地", new AddressDialog.CallbackListener() {
                        @Override
                        public void clickResult(String pid, String pNmae, String cityId, String cName, String countryID, String countryName) {
                            mDataBinding.tv22.setText(pNmae + " " + cName + " " + countryName);
                            pId = pid;
                            cId = cityId;
                        }
                    }).show();
                }
            });
        } else if (type == 1) {//职称代评
            mDataBinding.rlTv1.setVisibility(View.VISIBLE);
            mDataBinding.tv11.setText("职业等级");
            mDataBinding.rlEt1.setVisibility(View.VISIBLE);
            mDataBinding.et11.setText("联系人");
            mDataBinding.rlEt2.setVisibility(View.VISIBLE);
            mDataBinding.et21.setText("联系方式");
            mDataBinding.rlEt3.setVisibility(View.VISIBLE);
            mDataBinding.et31.setText("年龄");
            mDataBinding.rlTv2.setVisibility(View.VISIBLE);
            mDataBinding.tv21.setText("现在最高学历");
            mDataBinding.rlTv3.setVisibility(View.VISIBLE);
            mDataBinding.tv31.setText("所在地");
            mDataBinding.rlTv1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getComboBox("work_arg_2");       //职业等级
                }
            });
            mDataBinding.rlTv2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getComboBox("work_arg_5");//最高学历
                }
            });
            mDataBinding.rlTv3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //地址
                    new AddressDialog(getActivity(), "所在地", new AddressDialog.CallbackListener() {
                        @Override
                        public void clickResult(String pid, String pNmae, String cityId, String cName, String countryID, String countryName) {
                            mDataBinding.tv32.setText(pNmae + " " + cName + " " + countryName);
                            pId = pid;
                            cId = cityId;
                        }
                    }).show();
                }
            });
        } else if (type == 2) {//学历提升
            mDataBinding.rlTv1.setVisibility(View.VISIBLE);
            mDataBinding.tv11.setText("提升学历");
            mDataBinding.rlEt1.setVisibility(View.VISIBLE);
            mDataBinding.et11.setText("联系人");
            mDataBinding.rlEt2.setVisibility(View.VISIBLE);
            mDataBinding.et21.setText("联系方式");
            mDataBinding.rlEt3.setVisibility(View.VISIBLE);
            mDataBinding.et31.setText("年龄");
            mDataBinding.rlTv2.setVisibility(View.VISIBLE);
            mDataBinding.tv21.setText("现在最高学历");
            mDataBinding.rlTv3.setVisibility(View.VISIBLE);
            mDataBinding.tv31.setText("所在地");
            mDataBinding.rlTv1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getComboBox("work_arg_3");//提升学历
                }
            });
            mDataBinding.rlTv2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getComboBox("work_arg_5");//最高学历
                }
            });
            mDataBinding.rlTv3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //地址
                    new AddressDialog(getActivity(), "所在地", new AddressDialog.CallbackListener() {
                        @Override
                        public void clickResult(String pid, String pNmae, String cityId, String cName, String countryID, String countryName) {
                            mDataBinding.tv32.setText(pNmae + " " + cName + " " + countryName);
                            pId = pid;
                            cId = cityId;
                        }
                    }).show();
                }
            });
        } else if (type == 3) {//职业培训
            mDataBinding.rlTv1.setVisibility(View.VISIBLE);
            mDataBinding.tv11.setText("职业");
            mDataBinding.rlEt1.setVisibility(View.VISIBLE);
            mDataBinding.et11.setText("姓名");
            mDataBinding.rlEt2.setVisibility(View.VISIBLE);
            mDataBinding.et21.setText("手机号码");
            mDataBinding.rlEt3.setVisibility(View.VISIBLE);
            mDataBinding.et31.setText("年龄");
            mDataBinding.rlEt4.setVisibility(View.VISIBLE);
            mDataBinding.et41.setText("身高 (cm)");
            mDataBinding.rlEt5.setVisibility(View.VISIBLE);
            mDataBinding.et51.setText("体重 (kg)");
            mDataBinding.rlTv2.setVisibility(View.VISIBLE);
            mDataBinding.tv21.setText("户籍所在地");
            mDataBinding.rlTv1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getComboBox("work_arg_4");//职业
                }
            });
            mDataBinding.rlTv2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //地址
                    new AddressDialog(getActivity(), "户籍所在地", new AddressDialog.CallbackListener() {
                        @Override
                        public void clickResult(String pid, String pNmae, String cityId, String cName, String countryID, String countryName) {
                            mDataBinding.tv22.setText(pNmae + " " + cName + " " + countryName);
                            pId = pid;
                            cId = cityId;
                        }
                    }).show();
                }
            });
        } else if (type == 4) {//委托招聘
            mDataBinding.rlEt1.setVisibility(View.VISIBLE);
            mDataBinding.et11.setText("联系人");
            mDataBinding.rlEt2.setVisibility(View.VISIBLE);
            mDataBinding.et21.setText("联系方式");
            mDataBinding.rlEt6.setVisibility(View.VISIBLE);
            mDataBinding.et61.setText("企业名称");
        } else if (type == 5) {//保险代理
            mDataBinding.rlEt1.setVisibility(View.VISIBLE);
            mDataBinding.et11.setText("联系人");
            mDataBinding.rlEt2.setVisibility(View.VISIBLE);
            mDataBinding.et21.setText("联系方式");
            mDataBinding.rlEt6.setVisibility(View.VISIBLE);
            mDataBinding.et61.setText("企业名称");
        }
    }

    private void initClick() {
        mDataBinding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (judge()) {
                    submit();
                } else {
                    ToastUtil.show(getContext(), "请填写完整");
                }
            }
        });
    }

    private boolean judge() {
        if (type == 0) {//证书代办
            if (mDataBinding.tv12.getText().toString().length() > 0
                    && mDataBinding.et12.getText().toString().length() > 0
                    && mDataBinding.et22.getText().toString().length() > 0
                    && mDataBinding.et32.getText().toString().length() > 0
                    && mDataBinding.tv22.getText().toString().length() > 0) {
                return true;
            }
        } else if (type == 1) {//职称代评
            if (mDataBinding.tv12.getText().toString().length() > 0
                    && mDataBinding.et12.getText().toString().length() > 0
                    && mDataBinding.et22.getText().toString().length() > 0
                    && mDataBinding.et32.getText().toString().length() > 0
                    && mDataBinding.tv22.getText().toString().length() > 0
                    && mDataBinding.tv32.getText().toString().length() > 0) {
                return true;
            }
        } else if (type == 2) {//学历提升
            if (mDataBinding.tv12.getText().toString().length() > 0
                    && mDataBinding.et12.getText().toString().length() > 0
                    && mDataBinding.et22.getText().toString().length() > 0
                    && mDataBinding.et32.getText().toString().length() > 0
                    && mDataBinding.tv22.getText().toString().length() > 0
                    && mDataBinding.tv32.getText().toString().length() > 0) {
                return true;
            }
        } else if (type == 3) {//职业培训
            if (mDataBinding.tv12.getText().toString().length() > 0
                    && mDataBinding.et12.getText().toString().length() > 0
                    && mDataBinding.et22.getText().toString().length() > 0
                    && mDataBinding.et32.getText().toString().length() > 0
                    && mDataBinding.et42.getText().toString().length() > 0
                    && mDataBinding.et52.getText().toString().length() > 0
                    && mDataBinding.tv22.getText().toString().length() > 0) {
                return true;
            }
        } else if (type == 4) {//委托招聘
            if (mDataBinding.et12.getText().toString().length() > 0
                    && mDataBinding.et22.getText().toString().length() > 0) {
                return true;
            }
        } else if (type == 5) {//保险代理
            if (mDataBinding.et12.getText().toString().length() > 0
                    && mDataBinding.et22.getText().toString().length() > 0) {
                return true;
            }
        }
        return false;
    }

    private void submit() {
        if (type == 0) {//证书代办
            saveMsg("11");
        } else if (type == 1) {//职称代评
            saveMsg("12");
        } else if (type == 2) {//学历提升
            saveMsg("13");
        } else if (type == 3) {//职业培训
            saveMsg("14");
        } else if (type == 4) {//委托招聘
            saveMsg("15");
        } else if (type == 5) {//保险代理
            saveMsg("16");
        }
    }

    /**
     * 获取下拉框 工种 职业等级 最高学历 提升学历 职业
     * 证书代办（工种）： work_arg_1 职称代评（职业等级） ： work_arg_2 学历提升 ：work_arg_3 职业培训（职业）： work_arg_4
     * 最高学历： work_arg_5
     */
    private void getComboBox(final String name) {
        showDialog();
        Map<String, String> map = new TreeMap<String, String>();
        map.put("key_group_name", name);
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getActivity(), map, "getComboBox:" + name);
        NetBean netBean = new NetBean();
        netBean.setToken("");
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(GetComboBoxAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<GetComboBoxBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<GetComboBoxBean>> response, BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                if (response.body().getCode().equals("200")) {
                                    GetComboBoxBean getComboBoxBean = response.body().getData();
                                    if ("work_arg_1".equals(name)) {
                                        new DropTDialog(getActivity(), "工种", getComboBoxBean.getResult(), new DropTDialog.CallbackListener() {
                                            @Override
                                            public void clickResult(String id, String Nmae) {
                                                mDataBinding.tv12.setText(Nmae);
                                                mDataBinding.tv12.setTag(id);
                                            }
                                        }).show();
                                    } else if ("work_arg_2".equals(name)) {
                                        new DropTDialog(getActivity(), "职业等级", getComboBoxBean.getResult(), new DropTDialog.CallbackListener() {
                                            @Override
                                            public void clickResult(String id, String Nmae) {
                                                mDataBinding.tv12.setText(Nmae);
                                                mDataBinding.tv12.setTag(id);
                                            }
                                        }).show();
                                    } else if ("work_arg_5".equals(name)) {
                                        new DropTDialog(getActivity(), "最高学历", getComboBoxBean.getResult(), new DropTDialog.CallbackListener() {
                                            @Override
                                            public void clickResult(String id, String Nmae) {
                                                mDataBinding.tv22.setText(Nmae);
                                                mDataBinding.tv22.setTag(id);
                                            }
                                        }).show();
                                    } else if ("work_arg_3".equals(name)) {
                                        new DropTDialog(getActivity(), "提升学历", getComboBoxBean.getResult(), new DropTDialog.CallbackListener() {
                                            @Override
                                            public void clickResult(String id, String Nmae) {
                                                mDataBinding.tv12.setText(Nmae);
                                                mDataBinding.tv12.setTag(id);
                                            }
                                        }).show();
                                    } else if ("work_arg_4".equals(name)) {
                                        new DropTDialog(getActivity(), "职业", getComboBoxBean.getResult(), new DropTDialog.CallbackListener() {
                                            @Override
                                            public void clickResult(String id, String Nmae) {
                                                mDataBinding.tv12.setText(Nmae);
                                                mDataBinding.tv12.setTag(id);
                                            }
                                        }).show();
                                    }
                                } else {
                                    ToastUtils.show(getContext(), "error " + name + ":" + response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
    }


    /**
     * 信息咨询-- 劳务 11:证书代办 12:职称代评 13:学历提升 14:职业培训 15: 委托招聘 16:保险代理
     */
    private void saveMsg(String fix) {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("type", fix);
        map.put("name",  mDataBinding.et12.getText().toString().trim());
        map.put("content", "");
        map.put("contactName", mDataBinding.et12.getText().toString().trim());
        map.put("mobile", mDataBinding.et22.getText().toString().trim());
        if (type == 0) {//证书代办
            map.put("typeClass", mDataBinding.tv12.getTag().toString().trim());
            map.put("contactCity", pId);
            map.put("contactProvince", cId);
            map.put("contactAge", mDataBinding.et32.getText().toString().trim());
        } else if (type == 1) {//职称代评
            map.put("typeClass", mDataBinding.tv12.getTag().toString().trim());
            map.put("contactEd", mDataBinding.tv22.getTag().toString().trim());//最高学历
            map.put("contactCity", pId);
            map.put("contactProvince", cId);
            map.put("contactAge", mDataBinding.et32.getText().toString().trim());
        } else if (type == 2) {//学历提升
            map.put("typeClass", mDataBinding.tv12.getTag().toString().trim());
            map.put("contactCity", pId);
            map.put("contactProvince", cId);
            map.put("contactAge", mDataBinding.et32.getText().toString().trim());
        } else if (type == 3) {//职业培训
            map.put("typeClass", mDataBinding.tv12.getTag().toString().trim());
            map.put("contactCity", pId);
            map.put("contactProvince", cId);
            map.put("contactAge", mDataBinding.et32.getText().toString().trim());
            map.put("contactHeight", mDataBinding.et42.getText().toString().trim());
            map.put("contactWeight", mDataBinding.et52.getText().toString().trim());
        } else if (type == 4) {//委托招聘
            map.put("contactCompany", mDataBinding.et62.getText().toString().trim());
        } else if (type == 5) {//保险代理
            map.put("contactCompany", mDataBinding.et62.getText().toString().trim());
        }
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getContext(), map, "saveMsg3 type = " + fix);
        NetBean netBean = new NetBean();
        netBean.setToken("");
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(SaveMsg3Action.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity> response, BaseErrorInfo baseErrorInfo) {
                        closeDialog();
                        if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                            return;
                        }
                        if (response != null) {
                            if (response.body().getCode().equals("200")) {
                                new LabourSubmitDialog(getContext()).show();
                            } else if (!BuildConfig.DEBUG && response.body().getCode().equals("1006")) {
                                ToastUtils.show(getContext(), "网络错误");
                            } else {
                                ToastUtils.show(getContext(), response.body().getMessage());
                            }
                        }
                    }
                }));
    }

    public void onEvent(FinishEvent event) {
        getActivity().finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }
}