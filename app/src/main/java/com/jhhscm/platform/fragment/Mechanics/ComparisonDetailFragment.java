package com.jhhscm.platform.fragment.Mechanics;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.mbms.MbmsErrors;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.databinding.FragmentComparisonBinding;
import com.jhhscm.platform.databinding.FragmentComparisonDetailBinding;
import com.jhhscm.platform.fragment.Mechanics.action.GetGoodsDetailsAction;
import com.jhhscm.platform.fragment.Mechanics.bean.GetGoodsDetailsBean;
import com.jhhscm.platform.fragment.Mechanics.bean.GetGoodsPageListBean;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.StringUtils;
import com.jhhscm.platform.tool.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

public class ComparisonDetailFragment extends AbsFragment<FragmentComparisonDetailBinding> {

    private String good_code1;
    private String good_code2;
    private GetGoodsDetailsBean getGoodsDetailsBean;

    public static ComparisonDetailFragment instance() {
        ComparisonDetailFragment view = new ComparisonDetailFragment();
        return view;
    }

    @Override
    protected FragmentComparisonDetailBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentComparisonDetailBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        good_code1 = getArguments().getString("good_code1");
        good_code2 = getArguments().getString("good_code2");
        if (good_code1 != null) {
            getGoodsDetails(good_code1, true);
        }
        if (good_code2 != null) {
            getGoodsDetails(good_code2, false);
        }
    }

    /**
     * 获取新机详情  getGoodsDetails
     */
    private void getGoodsDetails(String goodsCode, final boolean isLeft) {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("good_code", goodsCode);
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getContext(), map, "getGoodsDetails");
        NetBean netBean = new NetBean();
        netBean.setToken("");
        netBean.setSign(sign);
        netBean.setContent(content);
        showDialog();
        onNewRequestCall(GetGoodsDetailsAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<GetGoodsDetailsBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<GetGoodsDetailsBean>> response,
                                           BaseErrorInfo baseErrorInfo) {
                        closeDialog();
                        if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                            return;
                        }
                        if (response != null) {
                            new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                            if (response.body().getCode().equals("200")) {
                                getGoodsDetailsBean = response.body().getData();
                                if (isLeft) {
                                    initLeft(getGoodsDetailsBean);
                                } else {
                                    initRight(getGoodsDetailsBean);
                                }
                            } else {
                                ToastUtils.show(getContext(), response.body().getMessage());
                            }
                        }
                    }
                }));
    }

    private void initRight(GetGoodsDetailsBean getGoodsDetailsBean) {
        mDataBinding.tvNameR.setText(getGoodsDetailsBean.getResult().getGoodsDetails().getName());
        mDataBinding.tvPriceR.setText("" + getGoodsDetailsBean.getResult().getGoodsDetails().getCounter_price());
        mDataBinding.tvDongliR.setText("--");
        mDataBinding.tvChanshangR.setText("--");
        mDataBinding.tvChandouR.setText(getGoodsDetailsBean.getResult().getGoodsDetails().getFix_p3());
        mDataBinding.tvDunweiR.setText("--");
        mDataBinding.tvZhongliangR.setText(getGoodsDetailsBean.getResult().getGoodsDetails().getFix_p1());
        mDataBinding.tvCdrongliangR.setText(getGoodsDetailsBean.getResult().getGoodsDetails().getFix_p2());

        mDataBinding.tvGonglvR.setText(getGoodsDetailsBean.getResult().getGoodsDetails().getFix_p5());
        mDataBinding.tvFadongjiR.setText(getGoodsDetailsBean.getResult().getGoodsDetails().getFix_p4());
        mDataBinding.tvRangyouxiangR.setText(getGoodsDetailsBean.getResult().getGoodsDetails().getFix_p6());
        mDataBinding.tvHeightR.setText(getGoodsDetailsBean.getResult().getGoodsDetails().getFix_p7());
    }

    private void initLeft(GetGoodsDetailsBean getGoodsDetailsBean) {
        mDataBinding.tvName.setText(getGoodsDetailsBean.getResult().getGoodsDetails().getName());
        mDataBinding.tvPrice.setText("" + getGoodsDetailsBean.getResult().getGoodsDetails().getCounter_price());
        mDataBinding.tvDongli.setText("--");
        mDataBinding.tvChanshang.setText("--");
        mDataBinding.tvChandou.setText(getGoodsDetailsBean.getResult().getGoodsDetails().getFix_p3());
        mDataBinding.tvDunwei.setText("--");
        mDataBinding.tvZhongliang.setText(getGoodsDetailsBean.getResult().getGoodsDetails().getFix_p1());
        mDataBinding.tvCdrongliang.setText(getGoodsDetailsBean.getResult().getGoodsDetails().getFix_p2());

        mDataBinding.tvGonglv.setText(getGoodsDetailsBean.getResult().getGoodsDetails().getFix_p5());
        mDataBinding.tvFadongji.setText(getGoodsDetailsBean.getResult().getGoodsDetails().getFix_p4());
        mDataBinding.tvRangyouxiang.setText(getGoodsDetailsBean.getResult().getGoodsDetails().getFix_p6());
        mDataBinding.tvHeight.setText(getGoodsDetailsBean.getResult().getGoodsDetails().getFix_p7());
    }
}
