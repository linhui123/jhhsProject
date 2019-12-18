package com.jhhscm.platform.fragment.labour;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.LoginActivity;
import com.jhhscm.platform.activity.PushQiuZhiActivity;
import com.jhhscm.platform.activity.PushZhaoPinActivity;
import com.jhhscm.platform.databinding.FragmentLabourDetailBinding;
import com.jhhscm.platform.event.AddressRefreshEvent;
import com.jhhscm.platform.event.RefreshEvent;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.home.action.SaveMsgAction;
import com.jhhscm.platform.fragment.my.labour.action.DelLabourReleaseAction;
import com.jhhscm.platform.fragment.my.labour.action.DelLabourWorkAction;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.ResultBean;
import com.jhhscm.platform.http.bean.UserSession;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.http.sign.SignObject;
import com.jhhscm.platform.permission.YXPermission;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.tool.UdaUtils;
import com.jhhscm.platform.views.dialog.ConfirmCallPhoneDialog;
import com.jhhscm.platform.views.dialog.SimpleDialog;
import com.jhhscm.platform.views.dialog.TelPhoneDialog;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

public class LabourDetailFragment extends AbsFragment<FragmentLabourDetailBinding> {
    private FindLabourReleaseListBean.DataBean dataBean;
    private int type;
    private UserSession userSession;
    private String id;

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
        type = getArguments().getInt("type");
        if (type == 0) {//只读
            mDataBinding.rlBottom.setVisibility(View.VISIBLE);
        } else {//编辑
            mDataBinding.rlBottom1.setVisibility(View.VISIBLE);
            if (ConfigUtils.getCurrentUser(getContext()) != null
                    && ConfigUtils.getCurrentUser(getContext()).getMobile() != null
                    && ConfigUtils.getCurrentUser(getContext()).getUserCode() != null) {
                userSession = ConfigUtils.getCurrentUser(getContext());
            } else {
                startNewActivity(LoginActivity.class);
            }
        }

        dataBean = (FindLabourReleaseListBean.DataBean) getArguments().getSerializable("dataBean");
        if (dataBean != null) {
            if (dataBean.getType().equals("0")) {//招聘
                mDataBinding.type.setText("招聘");
                findLabourReleaseDetail(dataBean.getLabour_code());
            } else {//求职
                mDataBinding.type.setText("求职");
                findLabourWorkDetail(dataBean.getLabour_code());
            }
        }

        mDataBinding.tvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataBean.getType().equals("0")) {//招聘
                    if (id != null) {
                        delLabourRelease(dataBean.getId(), id);
                    }
                } else {//求职
                    if (id != null) {
                        delLabourWork(dataBean.getId(), id);
                    }
                }
            }
        });

        mDataBinding.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataBean.getType().equals("0")) {//招聘
                    PushZhaoPinActivity.start(getContext(), dataBean.getLabour_code(), dataBean.getId(), 1);
                } else {//求职
                    PushQiuZhiActivity.start(getContext(), dataBean.getLabour_code(), dataBean.getId(), 1);
                }
                getActivity().finish();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }

    /**
     * 更新地区选择
     */
    public void onEvent(RefreshEvent event) {

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
//                                            dataBean.setType("0");
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
        if (dataBean != null) {
            id = dataBean.getId();
            mDataBinding.type.setText("招聘");
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
            mDataBinding.tvProject2.setText(dataBean.getProvince_text() + " " + dataBean.getCity_text());
            if (dataBean.getEnd_time()!=null){
                mDataBinding.tvProject3.setText(dataBean.getEnd_time()+"截止");
            }
            mDataBinding.tvProject4.setText(dataBean.getOther_desc());

            mDataBinding.userName.setText(UdaUtils.hiddenNameString(dataBean.getContact()));
            mDataBinding.userTel.setText(UdaUtils.hiddenPhoneNumber(dataBean.getContact_msg()));
            mDataBinding.phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ConfigUtils.getCurrentUser(getContext()) != null
                            && ConfigUtils.getCurrentUser(getContext()).getUserCode() != null
                            && "1".equals(ConfigUtils.getCurrentUser(getContext()).getIs_check())) {
                        new ConfirmCallPhoneDialog(getContext(), dataBean.getContact_msg(), new ConfirmCallPhoneDialog.CallbackListener() {
                            @Override
                            public void clickResult() {
                                //6.0权限处理
                                YXPermission.getInstance(getContext()).request(new AcpOptions.Builder()
                                        .setDeniedCloseBtn(getContext().getString(R.string.permission_dlg_close_txt))
                                        .setDeniedSettingBtn(getContext().getString(R.string.permission_dlg_settings_txt))
                                        .setDeniedMessage(getContext().getString(R.string.permission_denied_txt, "拨打电话"))
                                        .setPermissions(Manifest.permission.CALL_PHONE).build(), new AcpListener() {
                                    @Override
                                    public void onGranted() {
                                        Uri uriScheme = Uri.parse("tel:" + dataBean.getContact_msg());
                                        Intent it = new Intent(Intent.ACTION_CALL, uriScheme);
                                        getContext().startActivity(it);
                                    }


                                    @Override
                                    public void onDenied(List<String> permissions) {

                                    }
                                });
                            }
                        }).show();
                    } else {
                        new TelPhoneDialog(getContext(), new TelPhoneDialog.CallbackListener() {

                            @Override
                            public void clickYes(String phone) {
                                if (type == 0) {
                                    saveMsg(phone, "7");
                                } else {
                                    saveMsg(phone, "8");
                                }
                            }
                        }).show();
                    }
                }
            });
        }
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
//                                            dataBean.setType("1");
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
        if (dataBean != null) {
            id = dataBean.getId();
            mDataBinding.type.setText("求职");
            mDataBinding.llBase.setVisibility(View.VISIBLE);
            mDataBinding.llJinyan.setVisibility(View.VISIBLE);
            mDataBinding.llZhiwei.setVisibility(View.GONE);
            mDataBinding.llProject.setVisibility(View.GONE);
            mDataBinding.name.setText(dataBean.getName());
            mDataBinding.data.setText("更新时间：" + dataBean.getUpdate_time());
            mDataBinding.times.setText("关注次数：" + dataBean.getNum() + "");

            mDataBinding.tvBase1.setText(dataBean.getJob_text());
            mDataBinding.tvBase2.setText(dataBean.getM_type_text());
            mDataBinding.tvBase3.setText(dataBean.getWork_pre_text());
            mDataBinding.tvBase4.setText(dataBean.getProvince_text() + " " + dataBean.getCity_text());

            mDataBinding.tvFuli1.setText(dataBean.getSalay_money_text());
            mDataBinding.tvFuli2.setText(dataBean.getSettl_time_text());
            mDataBinding.tvFuli3.setText(dataBean.getOther_req());

            mDataBinding.tvJinyan1.setText(dataBean.getWork_time_text());
            mDataBinding.tvJinyan2.setText(dataBean.getGood_work_text());
            if (dataBean.getEnd_time()!=null){
                mDataBinding.tvJinyan3.setText(dataBean.getEnd_time()+"截止");
            }
            mDataBinding.tvJinyan4.setText(dataBean.getOther_desc());

            mDataBinding.userName.setText(UdaUtils.hiddenNameString(dataBean.getContact()));
            mDataBinding.userTel.setText(UdaUtils.hiddenPhoneNumber(dataBean.getContact_msg()));
            mDataBinding.phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ConfigUtils.getCurrentUser(getContext()) != null
                            && ConfigUtils.getCurrentUser(getContext()).getUserCode() != null
                            && "1".equals(ConfigUtils.getCurrentUser(getContext()).getIs_check())) {
                        new ConfirmCallPhoneDialog(getContext(), dataBean.getContact_msg(), new ConfirmCallPhoneDialog.CallbackListener() {
                            @Override
                            public void clickResult() {
                                //6.0权限处理
                                YXPermission.getInstance(getContext()).request(new AcpOptions.Builder()
                                        .setDeniedCloseBtn(getContext().getString(R.string.permission_dlg_close_txt))
                                        .setDeniedSettingBtn(getContext().getString(R.string.permission_dlg_settings_txt))
                                        .setDeniedMessage(getContext().getString(R.string.permission_denied_txt, "拨打电话"))
                                        .setPermissions(Manifest.permission.CALL_PHONE).build(), new AcpListener() {
                                    @Override
                                    public void onGranted() {
                                        Uri uriScheme = Uri.parse("tel:" + dataBean.getContact_msg());
                                        Intent it = new Intent(Intent.ACTION_CALL, uriScheme);
                                        getContext().startActivity(it);
                                    }

                                    @Override
                                    public void onDenied(List<String> permissions) {

                                    }
                                });
                            }
                        }).show();
                    } else {
                        new TelPhoneDialog(getContext(), new TelPhoneDialog.CallbackListener() {

                            @Override
                            public void clickYes(String phone) {
                                if (type == 0) {
                                    saveMsg(phone, "7");
                                } else {
                                    saveMsg(phone, "8");
                                }
                            }
                        }).show();
                    }
                }
            });
        }
    }

    /**
     * 信息咨询
     */
    private void saveMsg(final String phone, String type) {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("mobile", phone);
        map.put("type", type);
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

    /**
     * 删除招聘信息
     */
    private void delLabourRelease(final String id, final String release_id) {
        if (getContext() != null) {
            Map<String, Object> map = new TreeMap<String, Object>();
            map.put("labour_id", Integer.parseInt(id));
            map.put("release_id", Integer.parseInt(release_id));
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = SignObject.getSignKey(getActivity(), map, "delLabourRelease");
            NetBean netBean = new NetBean();
            netBean.setToken(userSession.getToken());
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(DelLabourReleaseAction.newInstance(getContext(), netBean)
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

    /**
     * 删除求职信息
     */
    private void delLabourWork(final String labour_id, final String work_id) {
        if (getContext() != null) {
            Map<String, Object> map = new TreeMap<String, Object>();
            map.put("work_id", Integer.parseInt(work_id));
            map.put("labour_id", Integer.parseInt(labour_id));
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = SignObject.getSignKey(getActivity(), map, "delLabourWork");
            NetBean netBean = new NetBean();
            netBean.setToken(userSession.getToken());
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(DelLabourWorkAction.newInstance(getContext(), netBean)
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
}

