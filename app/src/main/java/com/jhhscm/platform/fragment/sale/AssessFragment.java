package com.jhhscm.platform.fragment.sale;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.AssessResultActivity;
import com.jhhscm.platform.databinding.FragmentAssessBinding;
import com.jhhscm.platform.databinding.FragmentSaleMachineBinding;
import com.jhhscm.platform.event.ConsultationEvent;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.home.action.SaveMsgAction;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.dialog.DropDialog;
import com.jhhscm.platform.views.dialog.SimpleDialog;
import com.jhhscm.platform.views.recyclerview.WrappedRecyclerView;

import retrofit2.Response;

import java.util.Map;
import java.util.TreeMap;

public class AssessFragment extends AbsFragment<FragmentAssessBinding> implements View.OnClickListener {

    private SaleMachineAdapter mAdapter;
    private OldGoodOrderHistoryBean oldGoodOrderHistoryBean;
    private String tv1String, tv2String, tv3String, tv4String, tv5String, tv6String, tv7String, tv8String, tv9String = "";

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
        findGoodsAssess();
        initView();

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

            }
        });
    }


    /**
     * 咨询
     */
    public void onEvent(ConsultationEvent event) {
        if (event.phone != null) {
        }
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
        Map<String, String> map = new TreeMap<String, String>();
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getActivity(), map, "findGoodsAssess");
        NetBean netBean = new NetBean();
        netBean.setToken("");
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(FindGoodsAssessAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<OldGoodOrderHistoryBean>>() {

                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<OldGoodOrderHistoryBean>> response, BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                if (response.body().getCode().equals("200")) {

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
            case R.id.tv_1:
                new DropDialog(getContext(), "品牌", new DropDialog.CallbackListener() {
                    @Override
                    public void clickResult(String pid, String pNmae, String cityId, String cName, String countryID, String countryName) {

                    }
                });
                break;
            case R.id.tv_2:
                break;
            case R.id.tv_3:
                break;
            case R.id.tv_4:
                break;
            case R.id.tv_6:
                break;
            case R.id.tv_7:
                break;
            case R.id.tv_9:
                break;
            case R.id.tv_reset:
                initReset();
                break;
            case R.id.tv_assess:
                AssessResultActivity.start(getContext());
                break;
        }
    }

    public void initReset() {
        tv1String = "";
        tv2String = "";
        tv3String = "";
        tv4String = "";
        tv5String = "";
        tv6String = "";
        tv7String = "";
        tv8String = "";
        tv9String = "";
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
}
