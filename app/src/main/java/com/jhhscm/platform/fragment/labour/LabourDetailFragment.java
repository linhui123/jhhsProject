package com.jhhscm.platform.fragment.labour;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.FragmentLabourBinding;
import com.jhhscm.platform.databinding.FragmentLabourDetailBinding;
import com.jhhscm.platform.event.GetRegionEvent;
import com.jhhscm.platform.fragment.Mechanics.adapter.SelectedAdapter;
import com.jhhscm.platform.fragment.Mechanics.bean.GetComboBoxBean;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.home.action.SaveMsgAction;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.DisplayUtils;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.dialog.SimpleDialog;
import com.jhhscm.platform.views.dialog.TelPhoneDialog;
import com.jhhscm.platform.views.recyclerview.WrappedRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;


public class LabourDetailFragment extends AbsFragment<FragmentLabourDetailBinding> {
    private FindLabourReleaseListBean.DataBean dataBean;

    public static LabourDetailFragment instance() {
        LabourDetailFragment view = new LabourDetailFragment();
        return view;
    }

    @Override
    protected FragmentLabourDetailBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentLabourDetailBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        EventBusUtil.registerEvent(this);

//        RelativeLayout.LayoutParams llParams = (RelativeLayout.LayoutParams) mDataBinding.rlTop.getLayoutParams();
//        llParams.topMargin += DisplayUtils.getStatusBarHeight(getContext());
//        mDataBinding.rlTop.setLayoutParams(llParams);
        dataBean = (FindLabourReleaseListBean.DataBean) getArguments().getSerializable("dataBean");
        if (dataBean != null) {
            if (dataBean.getType().equals("1")) {
                findLabourReleaseDetail(dataBean.getLabour_code());
            } else {
                findLabourWorkDetail(dataBean.getLabour_code());
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }

    /**
     * 更新地区选择
     */
    public void onEvent(GetRegionEvent event) {
        if (event.pid != null && event.type != null) {
        }
    }


    /**
     * 查询劳务招聘
     */
    private void findLabourReleaseDetail(final String labour_code) {
        if (getContext() != null) {
            Map<String, String> map = new TreeMap<String, String>();
            map.put("labour_code", labour_code);
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = Sign.getSignKey(getActivity(), map, "findLabourReleaseDetail");
            NetBean netBean = new NetBean();
            netBean.setToken("");
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(FindLabourReleaseDetailAction.newInstance(getContext(), netBean)
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
//                                        FindLabourReleaseListBean findLabourReleaseListBean = response.body().getData();
//                                        for (FindLabourReleaseListBean.DataBean dataBean : findLabourReleaseListBean.getData()) {
//                                            dataBean.setType("1");
//                                        }
//                                        doSuccessResponse(refresh,findLabourReleaseListBean);
                                        initViewRelease(response.body().getData().getData());
                                    } else {
                                        ToastUtils.show(getContext(), response.body().getMessage());
                                    }
                                }
                            }
                        }
                    }));
        }
    }

    private void initViewRelease(final FindLabourReleaseDetailBean.DataBean dataBean) {
        mDataBinding.llBase.setVisibility(View.GONE);
        mDataBinding.llJinyan.setVisibility(View.GONE);
        mDataBinding.name.setText(dataBean.getName());
        mDataBinding.data.setText("更新时间：" + dataBean.getUpdate_time());
        mDataBinding.times.setText("关注次数：" + dataBean.getNum() + "");

        mDataBinding.tvZhiwei1.setText(dataBean.getName());
        mDataBinding.tvZhiwei2.setText(dataBean.getM_type_text());
        mDataBinding.tvZhiwei3.setText(dataBean.getWork_pre_text());
        mDataBinding.tvZhiwei4.setText(dataBean.getWork_num());
        mDataBinding.tvZhiwei5.setText(dataBean.getWork_time_text());

        mDataBinding.tvFuli1.setText(dataBean.getSalay_money_text());
        mDataBinding.tvFuli2.setText(dataBean.getSettl_time_text());
        mDataBinding.tvFuli3.setText(dataBean.getOther_req());

        mDataBinding.tvProject1.setText(dataBean.getWork_type_text());
        mDataBinding.tvProject2.setText(dataBean.getPosition());
        mDataBinding.tvProject3.setText(dataBean.getEnd_time());
        mDataBinding.tvProject4.setText(dataBean.getOther_desc());


        mDataBinding.userName.setText(dataBean.getContact());
        mDataBinding.userTel.setText(dataBean.getContact_msg());
        mDataBinding.phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMsg(dataBean.getContact_msg());
            }
        });
    }

    /**
     * 查询劳务求职
     */
    private void findLabourWorkDetail(final String labour_code) {
        if (getContext() != null) {
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
//                                        FindLabourReleaseListBean findLabourReleaseListBean = response.body().getData();
//                                        for (FindLabourReleaseListBean.DataBean dataBean : findLabourReleaseListBean.getData()) {
//                                            dataBean.setType("2");
//                                        }
//                                        doSuccessResponse(refresh,findLabourReleaseListBean);
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
        mDataBinding.llBase.setVisibility(View.VISIBLE);
        mDataBinding.llJinyan.setVisibility(View.VISIBLE);
        mDataBinding.llZhiwei.setVisibility(View.GONE);
        mDataBinding.llProject.setVisibility(View.GONE);
        mDataBinding.name.setText(dataBean.getName());
        mDataBinding.data.setText("更新时间：" + dataBean.getUpdate_time());
        mDataBinding.times.setText("关注次数：" + dataBean.getNum() + "");

        mDataBinding.tvBase1.setText(dataBean.getName());
        mDataBinding.tvBase1.setText(dataBean.getM_type_text());
        mDataBinding.tvBase1.setText(dataBean.getWork_pre_text());
        mDataBinding.tvBase1.setText(dataBean.getPosition());

        mDataBinding.tvFuli1.setText(dataBean.getSalay_money_text());
        mDataBinding.tvFuli2.setText(dataBean.getSettl_time_text());
        mDataBinding.tvFuli3.setText(dataBean.getOther_req());

        mDataBinding.tvJinyan1.setText(dataBean.getWork_time_text());
        mDataBinding.tvJinyan2.setText(dataBean.getGood_work_text());
        mDataBinding.tvJinyan3.setText(dataBean.getEnd_time());
        mDataBinding.tvJinyan4.setText(dataBean.getOther_desc());

        mDataBinding.userName.setText(dataBean.getContact());
        mDataBinding.userTel.setText(dataBean.getContact_msg());
        mDataBinding.phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        saveMsg(dataBean.getContact_msg());
            }
        });
    }

    /**
     * 信息咨询
     */
    private void saveMsg(final String phone) {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("mobile", phone);
        map.put("type", "1");
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getActivity(), map, "saveMsg");
        NetBean netBean = new NetBean();
        netBean.setToken("");
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(SaveMsgAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity> response, BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                if (response.body().getCode().equals("200")) {
                                    new SimpleDialog(getContext(), phone, new SimpleDialog.CallbackListener() {
                                        @Override
                                        public void clickYes() {

                                        }
                                    }).show();
                                } else {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
    }
}

