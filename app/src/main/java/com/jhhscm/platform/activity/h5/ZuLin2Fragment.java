package com.jhhscm.platform.activity.h5;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.MyApplication;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.LabourDetailActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.FragmentZuLin2Binding;
import com.jhhscm.platform.databinding.FragmentZuLinBinding;
import com.jhhscm.platform.databinding.ItemLabourBinding;
import com.jhhscm.platform.databinding.ItemZulinBinding;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.home.action.SaveMsgAction;
import com.jhhscm.platform.fragment.labour.FindLabourReleaseListBean;
import com.jhhscm.platform.fragment.labour.LabourFragment;
import com.jhhscm.platform.fragment.labour.LabourViewHolder;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.DisplayUtils;
import com.jhhscm.platform.tool.ShareUtils;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.tool.UrlUtils;
import com.jhhscm.platform.views.YXProgressDialog;
import com.jhhscm.platform.views.dialog.ShareDialog;
import com.jhhscm.platform.views.dialog.SimpleDialog;
import com.jhhscm.platform.views.dialog.TelPhoneDialog;
import com.jhhscm.platform.views.recyclerview.DividerItemStrokeDecoration;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;


public class ZuLin2Fragment extends AbsFragment<FragmentZuLin2Binding> {
    private static final int FILE_SELECTED = 5;
    private String SHARE_URL = "";
    private String TITLE = "";
    private String CONTENT = "";
    private String IMG_URL = "";
    private String url = "";
    private InnerAdapter mAdapter;

    public static ZuLin2Fragment instance() {
        ZuLin2Fragment view = new ZuLin2Fragment();
        return view;
    }


    @Override
    protected FragmentZuLin2Binding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentZuLin2Binding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        RelativeLayout.LayoutParams llParams = (RelativeLayout.LayoutParams) mDataBinding.toolbar.getLayoutParams();
        llParams.topMargin += DisplayUtils.getStatusBarHeight(getContext());
        mDataBinding.toolbar.setLayoutParams(llParams);
        mDataBinding.toolbarTitle.setText("租赁");

        if (MyApplication.getInstance().getZulinUrl() != null) {
            Log.e("ZL", "getZulinUrl : " + MyApplication.getInstance().getZulinUrl());
            url = MyApplication.getInstance().getZulinUrl();
        } else {
            url = UrlUtils.ZL;
        }
        Log.e("ZL", "UrlUtils.ZL : " + url);
        //获取租赁图片
        initView();

        mDataBinding.ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare();
            }
        });

        mDataBinding.tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TelPhoneDialog(getContext(),
                        new TelPhoneDialog.CallbackListener() {
                            @Override
                            public void clickYes(String phone) {
                                saveMsg(phone, "9");
                            }
                        }).show();
            }
        });

        mDataBinding.tvApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initView() {
        //http://api.jhhscm.cn:9095/static/img/rent_01.b926766.png
        //http://api.jhhscm.cn:9095/static/img/rent_02.b4f23d3.png
        //http://api.jhhscm.cn:9095/static/img/rent_03.1a56701.png
        //http://api.jhhscm.cn:9095/static/img/rent_04.f31e8e0.png
        //http://api.jhhscm.cn:9095/static/img/rent_05.1f911ed.png
        List<String> list = new ArrayList<>();
        list.add("http://api.jhhscm.cn:9095/static/img/rent_01.b926766.png");
        list.add("http://api.jhhscm.cn:9095/static/img/rent_02.b4f23d3.png");
        list.add("http://api.jhhscm.cn:9095/static/img/rent_03.1a56701.png");
        list.add("http://api.jhhscm.cn:9095/static/img/rent_04.f31e8e0.png");
        list.add("http://api.jhhscm.cn:9095/static/img/rent_05.1f911ed.png");
        mDataBinding.rv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new InnerAdapter(getContext());
        mDataBinding.rv.setAdapter(mAdapter);
        mAdapter.setData(list);
    }

    /**
     * 分享
     */
    public void showShare() {
        TITLE = "租赁";
        CONTENT = "挖矿来";
        IMG_URL = "";
        Log.e("ShareDialog", "IMG_URL " + IMG_URL);
        new ShareDialog(getContext(), new ShareDialog.CallbackListener() {
            @Override
            public void wechat() {
                YXProgressDialog dialog = new YXProgressDialog(getContext(), "请稍后");
                ShareUtils.shareUrlToWx(getActivity().getApplicationContext(), url, TITLE, CONTENT, IMG_URL, 0);
            }

            @Override
            public void friends() {
                YXProgressDialog dialog = new YXProgressDialog(getContext(), "请稍后");
                ShareUtils.shareUrlToWx(getActivity().getApplicationContext(), url, TITLE, CONTENT, IMG_URL, 1);
            }
        }).show();
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

    private class InnerAdapter extends AbsRecyclerViewAdapter<String> {
        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<String> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ZuLinViewHolder(mInflater.inflate(R.layout.item_zulin, parent, false));
        }
    }

    public class ZuLinViewHolder extends AbsRecyclerViewHolder<String> {

        private ItemZulinBinding mBinding;

        public ZuLinViewHolder(View itemView) {
            super(itemView);
            mBinding = ItemZulinBinding.bind(itemView);
        }

        @Override
        protected void onBindView(final String item) {
            if (item != null) {
                ImageLoader.getInstance().displayImage(item, mBinding.im);
            } else {
                mBinding.im.setVisibility(View.GONE);
            }

        }
    }
}
