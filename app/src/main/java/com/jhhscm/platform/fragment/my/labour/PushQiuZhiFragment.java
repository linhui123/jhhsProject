package com.jhhscm.platform.fragment.my.labour;


import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.LoginActivity;
import com.jhhscm.platform.databinding.FragmentPushQiuZhiBinding;
import com.jhhscm.platform.event.AddressRefreshEvent;
import com.jhhscm.platform.event.FinishEvent;
import com.jhhscm.platform.fragment.Mechanics.action.GetComboBoxAction;
import com.jhhscm.platform.fragment.Mechanics.bean.GetComboBoxBean;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.labour.FindLabourReleaseDetailBean;
import com.jhhscm.platform.fragment.labour.FindLabourWorkDetailAction;
import com.jhhscm.platform.fragment.my.labour.action.SaveLabourWorkAction;
import com.jhhscm.platform.fragment.my.labour.action.UpdateLabourWorkAction;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.ResultBean;
import com.jhhscm.platform.http.bean.UserSession;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.http.sign.SignObject;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.dialog.AddressDialog;
import com.jhhscm.platform.views.dialog.DropTDialog;
import com.jhhscm.platform.views.timePickets.TimePickerShow;
import com.tencent.mm.opensdk.utils.Log;

import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

public class PushQiuZhiFragment extends AbsFragment<FragmentPushQiuZhiBinding> {
    private int type;//0只读；1编辑；
    private String labour_code;
    private String id;

    private int xinziType = 0;//天1；月2，面议3
    private boolean tvType1, tvType2, tvType3, tvType4, tvType5, tvType6;
    private boolean xinziType1, xinziType2, xinziType3;
    private String pId, cId;//省市
    private String endTime;//有效日期
    private String m_type;//机械类型
    private String job;//岗位职责
    private String salay_money;//薪资水平
    private String settl_time;//结款周期
    private String other_req = "";//其他待遇
    private String work_pre;//工作性质
    //    private String work_num;//招聘人数
    private String work_time;//工作经验
    private String work_type;//项目类型
    private String contact;//联系人
    private String contact_msg;//联系方式
    private String position;//项目位置
    private String other_desc;//其他说明
    private String name;//标题
    private String good_work;//擅长项目
    private UserSession userSession;

    public static PushQiuZhiFragment instance() {
        PushQiuZhiFragment view = new PushQiuZhiFragment();
        return view;
    }

    @Override
    protected FragmentPushQiuZhiBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentPushQiuZhiBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        EventBusUtil.registerEvent(this);
        if (ConfigUtils.getCurrentUser(getContext()) != null
                && ConfigUtils.getCurrentUser(getContext()).getMobile() != null
                && ConfigUtils.getCurrentUser(getContext()).getUserCode() != null) {
            userSession = ConfigUtils.getCurrentUser(getContext());
        } else {
            startNewActivity(LoginActivity.class);
        }

        type = getArguments().getInt("type");
        labour_code = getArguments().getString("labour_code");
        id = getArguments().getString("id");
        if (type == 0) {//只读
            mDataBinding.tvFabu.setText("发布信息");
        } else {//编辑
            mDataBinding.tvFabu.setText("更新信息");
            findLabourWorkDetail(labour_code);
        }

        judgeButton();
        initXinZi();
        initOtherReq();
        initText();
        initEdit();

        mDataBinding.tvFabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOtherReq();
            }
        });
    }

    private void initXinZi() {
        mDataBinding.imTian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (xinziType1) {
                    mDataBinding.imTian.setImageResource(R.mipmap.ic_shoping_s);
                } else {
                    xinziType = 1;
                    mDataBinding.imTian.setImageResource(R.mipmap.ic_shoping_s1);
                    xinziType1 = true;
                    xinziType2 = false;
                    xinziType3 = false;
                    mDataBinding.imYue.setImageResource(R.mipmap.ic_shoping_s);
                    mDataBinding.tvYue.setText("");
                    mDataBinding.imMianyi.setImageResource(R.mipmap.ic_shoping_s);
                }
            }
        });

        mDataBinding.imYue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (xinziType2) {
                    mDataBinding.imYue.setImageResource(R.mipmap.ic_shoping_s);
                } else {
                    xinziType = 2;
                    mDataBinding.imYue.setImageResource(R.mipmap.ic_shoping_s1);
                    xinziType1 = false;
                    xinziType2 = true;
                    xinziType3 = false;
                    mDataBinding.tvTian.setText("");
                    mDataBinding.imTian.setImageResource(R.mipmap.ic_shoping_s);
                    mDataBinding.imMianyi.setImageResource(R.mipmap.ic_shoping_s);
                }
            }
        });

        mDataBinding.imMianyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (xinziType3) {
                    mDataBinding.imMianyi.setImageResource(R.mipmap.ic_shoping_s);
                } else {
                    xinziType = 3;
                    mDataBinding.imMianyi.setImageResource(R.mipmap.ic_shoping_s1);
                    xinziType1 = false;
                    xinziType2 = false;
                    xinziType3 = true;
                    mDataBinding.tvYue.setText("");
                    mDataBinding.tvTian.setText("");
                    mDataBinding.imTian.setImageResource(R.mipmap.ic_shoping_s);
                    mDataBinding.imYue.setImageResource(R.mipmap.ic_shoping_s);
                }
            }
        });
    }

    private void initText() {
        /**job	是		工作岗位
         logistics	是		物流信息
         salay_money	是		薪资水平
         work_time	是		工作经验
         settl_time	是		结算周期
         work_pre	是		工作性质
         good_work	是		擅长工程
         m_type	是		机械类型
         work_type	是		机械类型
         release_work_type	是		项目类型*/
        mDataBinding.tvBaseXinzi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getComboBox("salay_money");
            }
        });
        mDataBinding.tvBaseZhize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getComboBox("job");
            }
        });
        mDataBinding.tvBaseJixie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getComboBox("m_type");
            }
        });
        mDataBinding.tvBaseWorkType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getComboBox("work_pre");
            }
        });
        mDataBinding.tvBaseJingyan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getComboBox("work_time");
            }
        });
        mDataBinding.tvPLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddressDialog(getActivity(), "项目位置", new AddressDialog.CallbackListener() {
                    @Override
                    public void clickResult(String pid, String pNmae, String cityId, String cName, String countryID, String countryName) {
                        mDataBinding.tvPLocation.setText(pNmae + " " + cName + " " + countryName);
                        pId = pid;
                        cId = cityId;
                        position = countryName;
                    }
                }).show();
            }
        });
        //擅长项目
        mDataBinding.tvPType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getComboBox("good_work");
            }
        });


        mDataBinding.tvBaseSattleTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getComboBox("settl_time");
            }
        });
        mDataBinding.tvPData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerShow timePickerShow = new TimePickerShow(getContext());
                timePickerShow.timePickerAlertDialogs("", 1);
                timePickerShow.setOnTimePickerListener(new TimePickerShow.OnTimePickerListener() {
                    @Override
                    public void onClicklistener(String dataTime) {
                        endTime = dataTime.trim();
                        mDataBinding.tvPData.setText(dataTime.trim());
                        judgeButton();
                    }
                });
            }
        });
    }

    private void initEdit() {
        mDataBinding.tvContact.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                judgeButton();
                contact = s.toString().trim();
            }
        });
        mDataBinding.tvContactMsg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                judgeButton();
                contact_msg = s.toString().trim();
            }
        });
        mDataBinding.tvTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                judgeButton();
                name = s.toString().trim();
            }
        });

        mDataBinding.tvTian.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                judgeButton();
                salay_money = s.toString().trim();
//                xinziType=1;
//                xinziType1=true;
//                xinziType2 = false;
//                xinziType3 = false;
//                mDataBinding.imTian.setImageResource(R.mipmap.ic_shoping_s1);
//                mDataBinding.imYue.setImageResource(R.mipmap.ic_shoping_s);
//                mDataBinding.imMianyi.setImageResource(R.mipmap.ic_shoping_s);
//                mDataBinding.tvYue.setText("");
            }
        });
        mDataBinding.tvYue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                judgeButton();
                salay_money = s.toString().trim();
//                xinziType=2;
//                xinziType1=false;
//                xinziType2 = true;
//                xinziType3 = false;
//                mDataBinding.imTian.setImageResource(R.mipmap.ic_shoping_s);
//                mDataBinding.imYue.setImageResource(R.mipmap.ic_shoping_s1);
//                mDataBinding.imMianyi.setImageResource(R.mipmap.ic_shoping_s);
//                mDataBinding.tvTian.setText("");
            }
        });
        mDataBinding.tvElse.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() <= 100) {
                    mDataBinding.edCount.setText(s.toString().length() + "/100");
                }
                other_desc = s.toString().trim();
                judgeButton();
            }
        });
    }

    private void initOtherReq() {
        mDataBinding.tvType1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvType1) {
                    tvType1 = false;
                    mDataBinding.tvType1.setTextColor(Color.parseColor("#333333"));
                    mDataBinding.tvType1.setBackground(getActivity().getResources().getDrawable(R.drawable.bg_eaea));
                } else {
                    tvType1 = true;
                    mDataBinding.tvType1.setTextColor(Color.parseColor("#3977FE"));
                    mDataBinding.tvType1.setBackground(getActivity().getResources().getDrawable(R.drawable.bg_d9e5));
                }
                judgeButton();
            }
        });

        mDataBinding.tvType2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvType2) {
                    tvType2 = false;
                    mDataBinding.tvType2.setTextColor(Color.parseColor("#333333"));
                    mDataBinding.tvType2.setBackground(getActivity().getResources().getDrawable(R.drawable.bg_eaea));
                } else {
                    tvType2 = true;
                    mDataBinding.tvType2.setTextColor(Color.parseColor("#3977FE"));
                    mDataBinding.tvType2.setBackground(getActivity().getResources().getDrawable(R.drawable.bg_d9e5));
                }
                judgeButton();
            }
        });

        mDataBinding.tvType3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tvType3) {
                    tvType3 = true;
                    mDataBinding.tvType3.setTextColor(Color.parseColor("#3977FE"));
                    mDataBinding.tvType3.setBackground(getActivity().getResources().getDrawable(R.drawable.bg_d9e5));
                } else {
                    tvType3 = false;
                    mDataBinding.tvType3.setTextColor(Color.parseColor("#333333"));
                    mDataBinding.tvType3.setBackground(getActivity().getResources().getDrawable(R.drawable.bg_eaea));
                }
                judgeButton();
            }
        });

        mDataBinding.tvType4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvType4) {
                    tvType4 = false;
                    mDataBinding.tvType4.setTextColor(Color.parseColor("#333333"));
                    mDataBinding.tvType4.setBackground(getActivity().getResources().getDrawable(R.drawable.bg_eaea));
                } else {
                    tvType4 = true;
                    mDataBinding.tvType4.setTextColor(Color.parseColor("#3977FE"));
                    mDataBinding.tvType4.setBackground(getActivity().getResources().getDrawable(R.drawable.bg_d9e5));

                }
                judgeButton();
            }
        });

        mDataBinding.tvType5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvType5) {
                    tvType5 = false;
                    mDataBinding.tvType5.setTextColor(Color.parseColor("#333333"));
                    mDataBinding.tvType5.setBackground(getActivity().getResources().getDrawable(R.drawable.bg_eaea));
                } else {
                    tvType5 = true;

                    mDataBinding.tvType5.setTextColor(Color.parseColor("#3977FE"));
                    mDataBinding.tvType5.setBackground(getActivity().getResources().getDrawable(R.drawable.bg_d9e5));
                }
                judgeButton();
            }
        });

        mDataBinding.tvType6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvType6) {
                    tvType6 = false;
                    mDataBinding.tvType6.setTextColor(Color.parseColor("#333333"));
                    mDataBinding.tvType6.setBackground(getActivity().getResources().getDrawable(R.drawable.bg_eaea));
                } else {
                    tvType6 = true;
                    mDataBinding.tvType6.setTextColor(Color.parseColor("#3977FE"));
                    mDataBinding.tvType6.setBackground(getActivity().getResources().getDrawable(R.drawable.bg_d9e5));
                }
                judgeButton();
            }
        });

    }

    private void getOtherReq() {
//        if (xinziType1) {
//            settl_time = "按天结";
//        }
//        if (xinziType2) {
//            settl_time = "按月结";
//        }
//        if (xinziType3) {
//            settl_time = "薪资面议";
//        }
        other_req = "";
        if (tvType1) {
            other_req = "包吃";
        }
        if (tvType2) {
            if (other_req.length() > 0) {
                other_req = other_req + ",包住";
            } else {
                other_req = "包住";
            }
        }
        if (tvType3) {
            if (other_req.length() > 0) {
                other_req = other_req + ",白班";
            } else {
                other_req = "白班";
            }
        }
        if (tvType4) {
            if (other_req.length() > 0) {
                other_req = other_req + ",有加班费";
            } else {
                other_req = "有加班费";
            }
        }
        if (tvType5) {
            if (other_req.length() > 0) {
                other_req = other_req + ",报销路费";
            } else {
                other_req = "报销路费";
            }
        }
        if (tvType6) {
            if (other_req.length() > 0) {
                other_req = other_req + ",上保险";
            } else {
                other_req = "上保险";
            }
        }

        if (type == 0) {
            saveLabourWork();
        } else {
            updateLabourWork();
        }
    }

    /**
     * 新增求职
     */
    private void saveLabourWork() {
        if (getContext() != null) {
            showDialog();
            Map<String, String> map = new TreeMap<String, String>();
            map.put("job", job);
            map.put("m_type", m_type);
            map.put("salay_money", salay_money);
            map.put("settl_time", settl_time);
            map.put("other_req", other_req);
            map.put("work_pre", work_pre);
            map.put("work_time", work_time);
            map.put("good_work", good_work);
            map.put("province", pId);
            map.put("city", cId);
            map.put("position", position);
            map.put("end_time", endTime);
            map.put("other_desc", other_desc.trim());
            map.put("name", name);
            map.put("user_code", ConfigUtils.getCurrentUser(getContext()).getUserCode());
            map.put("contact", contact.trim());
            map.put("contact_msg", contact_msg.trim());
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = Sign.getSignKey(getActivity(), map, "saveLabourWork");
            NetBean netBean = new NetBean();
            netBean.setToken(ConfigUtils.getCurrentUser(getContext()).getToken());
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(SaveLabourWorkAction.newInstance(getContext(), netBean)
                    .request(new AHttpService.IResCallback<BaseEntity<ResultBean>>() {
                        @Override
                        public void onCallback(int resultCode, Response<BaseEntity<ResultBean>> response,
                                               BaseErrorInfo baseErrorInfo) {
                            if (getView() != null) {
                                closeDialog();
                                if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                    return;
                                }
                                if (response != null) {
                                    new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                    if (response.body().getCode().equals("200")) {
                                        ToastUtils.show(getContext(), "发布成功");
                                        EventBusUtil.post(new AddressRefreshEvent(1));
                                        getActivity().finish();
                                    } else if (response.body().getCode().equals("1003")) {
                                        ToastUtils.show(getContext(), "登录信息过期，请重新登录");
                                        startNewActivity(LoginActivity.class);
                                    } else {
                                        ToastUtils.show(getContext(), response.body().getMessage());
                                    }
                                }
                            }
                        }
                    }));
        }
    }

    /**
     * 更新求职
     */
    private void updateLabourWork() {
        if (getContext() != null) {
            showDialog();
            Map<String, Object> map = new TreeMap<String, Object>();
            map.put("id", Integer.parseInt(id));
            map.put("job", job);
            map.put("m_type", m_type);
            map.put("salay_money", salay_money);
            map.put("settl_time", settl_time);
            map.put("other_req", other_req);
            map.put("work_pre", work_pre);
            map.put("work_time", work_time);
            map.put("good_work", good_work);
            map.put("province", pId);
            map.put("city", cId);
            map.put("position", position);
            map.put("end_time", endTime);
            map.put("other_desc", other_desc);
            map.put("name", name);
            map.put("user_code", ConfigUtils.getCurrentUser(getContext()).getUserCode());
            map.put("contact", contact);
            map.put("contact_msg", contact_msg);
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = SignObject.getSignKey(getActivity(), map, "saveLabourWork");
            NetBean netBean = new NetBean();
            netBean.setToken(ConfigUtils.getCurrentUser(getContext()).getToken());
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(UpdateLabourWorkAction.newInstance(getContext(), netBean)
                    .request(new AHttpService.IResCallback<BaseEntity<ResultBean>>() {
                        @Override
                        public void onCallback(int resultCode, Response<BaseEntity<ResultBean>> response,
                                               BaseErrorInfo baseErrorInfo) {
                            if (getView() != null) {
                                closeDialog();
                                if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                    return;
                                }
                                if (response != null) {
                                    new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                    if (response.body().getCode().equals("200")) {
                                        ToastUtils.show(getContext(), "发布成功");
                                        EventBusUtil.post(new AddressRefreshEvent(1));
                                        getActivity().finish();
                                    } else {
                                        ToastUtils.show(getContext(), response.body().getMessage());
                                    }
                                }
                            }
                        }
                    }));
        }
    }

    public void onEvent(FinishEvent event) {
        getActivity().finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }

    private void judgeButton() {
        if (mDataBinding.tvContact.getText().toString().length() > 0
                && mDataBinding.tvContactMsg.getText().toString().length() > 0
                && mDataBinding.tvTitle.getText().toString().length() > 0
                && mDataBinding.tvBaseZhize.getText().toString().length() > 0
                && mDataBinding.tvBaseJixie.getText().toString().length() > 0
                && mDataBinding.tvBaseWorkType.getText().toString().length() > 0
                && mDataBinding.tvBaseJingyan.getText().toString().length() > 0
                && mDataBinding.tvPType.getText().toString().length() > 0
                && mDataBinding.tvPLocation.getText().toString().length() > 0
                && mDataBinding.tvBaseXinzi.getText().toString().length() > 0
                && mDataBinding.tvBaseSattleTime.getText().toString().length() > 0
                && mDataBinding.tvPData.getText().toString().length() > 0) {
            mDataBinding.tvFabu.setEnabled(true);
            mDataBinding.tvFabu.setBackgroundResource(R.drawable.button_c397);
        } else {
            mDataBinding.tvFabu.setEnabled(false);
            mDataBinding.tvFabu.setBackgroundResource(R.drawable.button_b0c);
        }
    }

    GetComboBoxBean getComboBoxBean;

    /**
     * 获取下拉框
     */
    private void getComboBox(final String name) {
//        final GetComboBoxBean[] comboBoxBean = {new GetComboBoxBean()};
        showDialog();
        Map<String, String> map = new TreeMap<String, String>();
        map.put("key_group_name", name);
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getActivity(), map, "getComboBox:" + "name");
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
                                    getComboBoxBean = response.body().getData();
                                    if ("job".equals(name)) {
                                        new DropTDialog(getActivity(), "岗位职责", getComboBoxBean.getResult(), new DropTDialog.CallbackListener() {
                                            @Override
                                            public void clickResult(String id, String Nmae) {
                                                job = id;
                                                mDataBinding.tvBaseZhize.setText(Nmae);
                                                mDataBinding.tvBaseZhize.setTag(id);
                                                judgeButton();
                                            }
                                        }).show();
                                    } else if ("m_type".equals(name)) {
                                        new DropTDialog(getContext(), "机械类型", getComboBoxBean.getResult(), new DropTDialog.CallbackListener() {
                                            @Override
                                            public void clickResult(String id, String Nmae) {
                                                m_type = id;
                                                mDataBinding.tvBaseJixie.setText(Nmae);
                                                mDataBinding.tvBaseJixie.setTag(id);
                                                judgeButton();
                                            }
                                        }).show();
                                    } else if ("work_pre".equals(name)) {
                                        new DropTDialog(getActivity(), "工作性质", getComboBoxBean.getResult(), new DropTDialog.CallbackListener() {
                                            @Override
                                            public void clickResult(String id, String Nmae) {
                                                work_pre = id;
                                                mDataBinding.tvBaseWorkType.setText(Nmae);
                                                mDataBinding.tvBaseWorkType.setTag(id);
                                                judgeButton();
                                            }
                                        }).show();
                                    } else if ("work_time".equals(name)) {
                                        new DropTDialog(getContext(), "工作经验", getComboBoxBean.getResult(), new DropTDialog.CallbackListener() {
                                            @Override
                                            public void clickResult(String id, String Nmae) {
                                                work_time = id;
                                                mDataBinding.tvBaseJingyan.setText(Nmae);
                                                mDataBinding.tvBaseJingyan.setTag(id);
                                                judgeButton();
                                            }
                                        }).show();
                                    } else if ("good_work".equals(name)) {
                                        new DropTDialog(getContext(), "擅长工程", getComboBoxBean.getResult(), new DropTDialog.CallbackListener() {
                                            @Override
                                            public void clickResult(String id, String Nmae) {
                                                good_work = id;
                                                mDataBinding.tvPType.setTag(id);
                                                mDataBinding.tvPType.setText(Nmae);
                                                judgeButton();
                                            }
                                        }).show();
                                    } else if ("salay_money".equals(name)) {
                                        new DropTDialog(getContext(), "薪资水平", getComboBoxBean.getResult(), new DropTDialog.CallbackListener() {
                                            @Override
                                            public void clickResult(String id, String Nmae) {
                                                salay_money = id;
                                                mDataBinding.tvBaseXinzi.setTag(id);
                                                mDataBinding.tvBaseXinzi.setText(Nmae);
                                                judgeButton();
                                            }
                                        }).show();
                                    } else if ("settl_time".equals(name)) {
                                        new DropTDialog(getContext(), "结算周期", getComboBoxBean.getResult(), new DropTDialog.CallbackListener() {
                                            @Override
                                            public void clickResult(String id, String Nmae) {
                                                settl_time = id;
                                                mDataBinding.tvBaseSattleTime.setTag(id);
                                                mDataBinding.tvBaseSattleTime.setText(Nmae);
                                                judgeButton();
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
//        return comboBoxBean[0];
    }

    /**
     * 查询劳务求职
     */
    private void findLabourWorkDetail(final String labour_code) {
        if (getContext() != null) {
            showDialog();
            Map<String, String> map = new TreeMap<String, String>();
            map.put("labour_code", labour_code);
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = Sign.getSignKey(getActivity(), map, "findLabourWorkDetail");
            NetBean netBean = new NetBean();
            netBean.setToken("");
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(FindLabourWorkDetailAction.newInstance(getContext(), netBean)
                    .request(new AHttpService.IResCallback<BaseEntity<FindLabourReleaseDetailBean>>() {
                        @Override
                        public void onCallback(int resultCode, Response<BaseEntity<FindLabourReleaseDetailBean>> response,
                                               BaseErrorInfo baseErrorInfo) {
                            if (getView() != null) {
                                closeDialog();
                                if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                    return;
                                }
                                if (response != null) {
                                    new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                    if (response.body().getCode().equals("200")) {
                                        initViewWork(response.body().getData().getData());
                                    } else {
                                        ToastUtils.show(getContext(), response.body().getMessage());
                                    }
                                }
                            }
                        }
                    }));
        }
    }

    private void initViewWork(final FindLabourReleaseDetailBean.DataBean dataBean) {
        if (dataBean != null) {
            mDataBinding.tvTitle.setText(dataBean.getName());
            name = dataBean.getName();
            mDataBinding.tvBaseZhize.setText(dataBean.getJob_text());//岗位
            job = dataBean.getJob();
            mDataBinding.tvBaseJixie.setText(dataBean.getM_type_text());
            m_type = dataBean.getM_type();
            mDataBinding.tvBaseWorkType.setText(dataBean.getWork_pre_text());
            work_pre = dataBean.getWork_pre();
            mDataBinding.tvPLocation.setText(dataBean.getProvince_text() + " " + dataBean.getCity_text());
            pId = dataBean.getProvince();
            cId = dataBean.getCity();
            mDataBinding.tvBaseJingyan.setText(dataBean.getWork_time_text());
            work_time = dataBean.getWork_time();
            mDataBinding.tvPType.setText(dataBean.getGood_work_text());//擅长工程
            good_work = dataBean.getGood_work();
            mDataBinding.tvPData.setText(dataBean.getEnd_time());
            endTime = dataBean.getEnd_time();
            mDataBinding.tvElse.setText(dataBean.getOther_desc());
            other_desc = dataBean.getOther_desc();

            //福利
            settl_time = dataBean.getSettl_time_text();
            salay_money = dataBean.getSalay_money_text();
            mDataBinding.tvBaseXinzi.setText(salay_money);
            mDataBinding.tvBaseSattleTime.setText(settl_time);

            mDataBinding.tvContact.setText(dataBean.getContact());
            mDataBinding.tvContactMsg.setText(dataBean.getContact_msg());
//            if (settl_time != null) {
//                if (settl_time.contains("天")) {
//                    xinziType = 1;
//                    xinziType1 = true;
//                    mDataBinding.imTian.setImageResource(R.mipmap.ic_shoping_s1);
//                    mDataBinding.tvTian.setText(salay_money);
//                } else if (settl_time.contains("月")) {
//                    xinziType = 2;
//                    xinziType2 = true;
//                    mDataBinding.imYue.setImageResource(R.mipmap.ic_shoping_s1);
//                    mDataBinding.tvYue.setText(salay_money);
//                } else {//薪资面议
//                    xinziType = 3;
//                    xinziType3 = true;
//                    mDataBinding.imMianyi.setImageResource(R.mipmap.ic_shoping_s1);
//                }
//            }

            other_req = dataBean.getOther_req();
            if (other_req != null && other_req.length() > 0) {
                if (other_req.contains("包吃")) {
                    tvType1 = true;
                    mDataBinding.tvType1.setTextColor(Color.parseColor("#3977FE"));
                    mDataBinding.tvType1.setBackground(getActivity().getResources().getDrawable(R.drawable.bg_d9e5));
                }
                if (other_req.contains("包住")) {
                    tvType2 = true;
                    mDataBinding.tvType2.setTextColor(Color.parseColor("#3977FE"));
                    mDataBinding.tvType2.setBackground(getActivity().getResources().getDrawable(R.drawable.bg_d9e5));
                }
                if (other_req.contains("白班")) {
                    tvType3 = true;
                    mDataBinding.tvType3.setTextColor(Color.parseColor("#3977FE"));
                    mDataBinding.tvType3.setBackground(getActivity().getResources().getDrawable(R.drawable.bg_d9e5));
                }
                if (other_req.contains("有加班费")) {
                    tvType4 = true;
                    mDataBinding.tvType4.setTextColor(Color.parseColor("#3977FE"));
                    mDataBinding.tvType4.setBackground(getActivity().getResources().getDrawable(R.drawable.bg_d9e5));
                }
                if (other_req.contains("报销路费")) {
                    tvType5 = true;
                    mDataBinding.tvType5.setTextColor(Color.parseColor("#3977FE"));
                    mDataBinding.tvType5.setBackground(getActivity().getResources().getDrawable(R.drawable.bg_d9e5));
                }
                if (other_req.contains("保险")) {
                    tvType6 = true;
                    mDataBinding.tvType6.setTextColor(Color.parseColor("#3977FE"));
                    mDataBinding.tvType6.setBackground(getActivity().getResources().getDrawable(R.drawable.bg_d9e5));
                }
            }

        }
    }
}
