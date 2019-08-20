package com.jhhscm.platform.fragment.sale;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.AssessResultActivity;
import com.jhhscm.platform.activity.BrandActivity;
import com.jhhscm.platform.activity.LoginActivity;
import com.jhhscm.platform.activity.MechanicsByBrandActivity;
import com.jhhscm.platform.databinding.FragmentAssessBinding;
import com.jhhscm.platform.databinding.FragmentSaleMachineBinding;
import com.jhhscm.platform.event.BrandResultEvent;
import com.jhhscm.platform.event.ConsultationEvent;
import com.jhhscm.platform.fragment.Mechanics.action.GetComboBoxAction;
import com.jhhscm.platform.fragment.Mechanics.bean.GetComboBoxBean;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.home.action.SaveMsgAction;
import com.jhhscm.platform.fragment.my.mechanics.FindOldGoodByUserCodeBean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.UserSession;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.http.sign.SignObject;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.DataUtil;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.dialog.AddressDialog;
import com.jhhscm.platform.views.dialog.DropDialog;
import com.jhhscm.platform.views.dialog.DropTDialog;
import com.jhhscm.platform.views.dialog.SimpleDialog;
import com.jhhscm.platform.views.recyclerview.WrappedRecyclerView;
import com.jhhscm.platform.views.timePickets.TimePickerShow;

import retrofit2.Response;

import java.util.Map;
import java.util.TreeMap;

public class AssessFragment extends AbsFragment<FragmentAssessBinding> implements View.OnClickListener {

    private SaleMachineAdapter mAdapter;
    private OldGoodOrderHistoryBean oldGoodOrderHistoryBean;
    private String brand_id, fix_p_9, factory_time, old_time, fix_p_13, fix_p_14, province, city;
    private String tel;
    private String valuation_intention;
    private UserSession userSession;

    public static AssessFragment instance() {
        AssessFragment view = new AssessFragment();
        return view;
    }

    @Override
    protected FragmentAssessBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentAssessBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        EventBusUtil.registerEvent(this);
//        if (ConfigUtils.getCurrentUser(getContext()) != null
//                && ConfigUtils.getCurrentUser(getContext()).getMobile() != null
//                && ConfigUtils.getCurrentUser(getContext()).getUserCode() != null) {
//            userSession = ConfigUtils.getCurrentUser(getContext());
//        } else {
//            startNewActivity(LoginActivity.class);
//        }

        initView();
        judgeButton();
    }

    private void initView() {
        mDataBinding.tv1.setOnClickListener(this);
        mDataBinding.tv2.setOnClickListener(this);
        mDataBinding.tv3.setOnClickListener(this);
        mDataBinding.tv4.setOnClickListener(this);
        mDataBinding.tv6.setOnClickListener(this);
        mDataBinding.tv7.setOnClickListener(this);
        mDataBinding.tv9.setOnClickListener(this);

        mDataBinding.tvAssess.setOnClickListener(this);
        mDataBinding.tvReset.setOnClickListener(this);

        mDataBinding.tv5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                old_time = editable.toString().trim();
                judgeButton();
            }
        });

        mDataBinding.tv8.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                tel = editable.toString().trim();
                judgeButton();
            }
        });
    }


    /**
     * 咨询
     */
    public void onEvent(BrandResultEvent event) {
        if (event.getBrand_id() != null) {
            brand_id = event.getBrand_id();
        }
        if (event.getBrand_name() != null && event.getBrand_name().length() > 0) {
            mDataBinding.tv1.setText(event.getBrand_name());
        }
        if (event.getFix_p_9() != null) {
            fix_p_9 = event.getFix_p_9();
            mDataBinding.tv2.setText(event.getFix_p_9_name());
        }
        judgeButton();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }

    /**
     * 二手机估价
     */
    private void findGoodsAssess() {
        Map<String, Object> map = new TreeMap<String, Object>();
        if (brand_id!=null){
            map.put("brand_id", Integer.parseInt(brand_id));
        }
        map.put("fix_p_9", fix_p_9);
        map.put("province", province);
        map.put("city", city);
        map.put("factory_time", factory_time);
        if (old_time!=null){
            map.put("old_time", Integer.parseInt(old_time));
        }
        map.put("fix_p_13", fix_p_13);
        map.put("fix_p_13", fix_p_13);
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = SignObject.getSignKey(getActivity(), map, "findGoodsAssess");
        NetBean netBean = new NetBean();
        netBean.setToken("");
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(FindGoodsAssessAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<FindGoodsAssessBean>>() {

                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<FindGoodsAssessBean>> response, BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                if (response.body().getCode().equals("200")) {
                                    if (response.body().getData().getData().getMessage() != null) {
                                        ToastUtils.show(getContext(), response.body().getData().getData().getMessage());
                                    } else {
                                        AssessResultActivity.start(getContext(), response.body().getData());
                                    }
                                } else {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_1://品牌 brand_id
                BrandActivity.start(getContext(), 1);
                break;
            case R.id.tv_2://型号 fix_p_9
                if (brand_id != null && brand_id.length() > 0) {
                    MechanicsByBrandActivity.start(getContext(), brand_id,0);
                } else {
                    ToastUtil.show(getContext(), "请先选择品牌");
                }
                break;
            case R.id.tv_3://施工地区 province city
                new AddressDialog(getActivity(), "施工地区", new AddressDialog.CallbackListener() {
                    @Override
                    public void clickResult(String pid, String pNmae, String cityId, String cName, String countryID, String countryName) {
                        mDataBinding.tv3.setText(pNmae + " " + cName + " " + countryName);
                        province = pid;
                        city = cityId;
                        judgeButton();
                    }
                }).show();
                break;
            case R.id.tv_4://年限   factory_time
                TimePickerShow timePickerShow = new TimePickerShow(getContext());
                timePickerShow.timePickerAlertDialogs("", 2);
                timePickerShow.setOnTimePickerListener(new TimePickerShow.OnTimePickerListener() {
                    @Override
                    public void onClicklistener(String dataTime) {
                        if (dataTime.length() > 4) {
                            factory_time = dataTime.substring(0, 4);
                            mDataBinding.tv4.setText(dataTime.trim());
                        }
                        judgeButton();
                    }
                });
                break;
            case R.id.tv_6://是否大锤  fix_p_13    is_hammer
                getComboBox("is_hammer");
                break;
            case R.id.tv_7://作业方式  fix_p_14    work_type
                getComboBox("work_type");
                break;
            case R.id.tv_9://估价意向   valuation_intention
                getComboBox("valuation_intention");
                break;
            case R.id.tv_reset:
                initReset();
                break;
            case R.id.tv_assess:

                findGoodsAssess();
                break;
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
                                    if ("is_hammer".equals(name)) {
                                        new DropTDialog(getActivity(), "是否打锤", getComboBoxBean.getResult(), new DropTDialog.CallbackListener() {
                                            @Override
                                            public void clickResult(String id, String Nmae) {
                                                fix_p_13 = id;
                                                mDataBinding.tv6.setText(Nmae);
                                                mDataBinding.tv6.setTag(id);
                                            }
                                        }).show();
                                    } else if ("work_type".equals(name)) {
                                        new DropTDialog(getContext(), "作业方式", getComboBoxBean.getResult(), new DropTDialog.CallbackListener() {
                                            @Override
                                            public void clickResult(String id, String Nmae) {
                                                fix_p_14 = id;
                                                mDataBinding.tv7.setText(Nmae);
                                                mDataBinding.tv7.setTag(id);
                                            }
                                        }).show();
                                    } else if ("valuation_intention".equals(name)) {
                                        new DropTDialog(getActivity(), "估价意向", getComboBoxBean.getResult(), new DropTDialog.CallbackListener() {
                                            @Override
                                            public void clickResult(String id, String Nmae) {
                                                valuation_intention = id;
                                                mDataBinding.tv9.setText(Nmae);
                                                mDataBinding.tv9.setTag(id);
                                            }
                                        }).show();
                                    }
                                    judgeButton();
                                } else {
                                    ToastUtils.show(getContext(), "error " + name + ":" + response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
//        return comboBoxBean[0];
    }

    public void initReset() {
        brand_id = "";
        fix_p_9 = "";
        factory_time = "";
        fix_p_13 = "";
        fix_p_14 = "";
        province = "";
        city = "";

        mDataBinding.tv1.setText("");
        mDataBinding.tv2.setText("");
        mDataBinding.tv3.setText("");
        mDataBinding.tv4.setText("");
        mDataBinding.tv5.setText("");
        mDataBinding.tv1.setText("");
        mDataBinding.tv7.setText("");
        mDataBinding.tv8.setText("");
        mDataBinding.tv9.setText("");
    }

    private void judgeButton() {
//        if (brand_id != null && brand_id.length() > 0
//                && fix_p_9 != null && fix_p_9.length() > 0
//                && factory_time != null && factory_time.length() > 0
//                && old_time != null && old_time.length() > 0
//                && fix_p_13 != null && fix_p_13.length() > 0
//                && fix_p_14 != null && fix_p_14.length() > 0
//                && province != null && province.length() > 0
//                && city != null && city.length() > 0) {
//            mDataBinding.tvAssess.setEnabled(true);
//            mDataBinding.tvAssess.setBackgroundResource(R.drawable.button_c397);
//        } else {
//            mDataBinding.tvAssess.setEnabled(false);
//            mDataBinding.tvAssess.setBackgroundResource(R.drawable.button_b0c);
//        }
    }
}
