package com.jhhscm.platform.fragment.my.set;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.databinding.FragmentFeedbackBinding;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.msg.GetPushListAction;
import com.jhhscm.platform.fragment.msg.GetPushListBean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.ResultBean;
import com.jhhscm.platform.http.sign.SignObject;
import com.jhhscm.platform.jpush.ExampleUtil;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.bottommenu.bean.MenuData;

import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;


public class FeedbackFragment extends AbsFragment<FragmentFeedbackBinding> {

    public static FeedbackFragment instance() {
        FeedbackFragment view = new FeedbackFragment();
        return view;
    }

    @Override
    protected FragmentFeedbackBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentFeedbackBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        mDataBinding.tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDataBinding.tvContent.getText().toString().length() > 0) {
                    if (mDataBinding.tvTel.getText().toString().length() > 7) {
                        save(mDataBinding.tvContent.getText().toString(), mDataBinding.tvTel.getText().toString());
                    } else {
                        ToastUtil.show(getContext(), "请输入正确的手机号");
                    }
                } else {
                    ToastUtil.show(getContext(), "请输入您的宝贵意见");
                }
            }
        });
    }

    /**
     * 意见反馈
     */
    private void save(String mobile, String con) {
        if (getContext() != null) {
            Map<String, Object> map = new TreeMap<String, Object>();
            map.put("mobile", mobile);
            map.put("content", con);
            map.put("from_type", "1");
            map.put("app_version", ExampleUtil.GetVersion(getContext()));
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = SignObject.getSignKey(getActivity(), map, "save");
            NetBean netBean = new NetBean();
            netBean.setToken("");
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(SavefeedBackAction.newInstance(getContext(), netBean)
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
                                        ToastUtil.show(getContext(), "反馈成功");
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
