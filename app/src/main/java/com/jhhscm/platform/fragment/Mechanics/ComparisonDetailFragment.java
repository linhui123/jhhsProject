package com.jhhscm.platform.fragment.Mechanics;


import android.graphics.drawable.Drawable;
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

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

public class ComparisonDetailFragment extends AbsFragment<FragmentComparisonDetailBinding> {

    private String good_code1;
    private String good_code2;
    private GetGoodsDetailsBean leftGoodsDetailsBean;
    private GetGoodsDetailsBean rightGoodsDetailsBean;

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

//        mDataBinding.tv1Same.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDataBinding.tv1Same.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(
//                        R.mipmap.ic_com_diff),
//                        null, null, null);
//                mDataBinding.tv1Diff.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(
//                        R.mipmap.ic_com_same),
//                        null, null, null);
//                if (rightGoodsDetailsBean.getResult().getGoodsDetails() != null
//                        && leftGoodsDetailsBean.getResult().getGoodsDetails() != null) {
//                    setSame1();
//                }
//
//            }
//        });
//
//        mDataBinding.tv1Diff.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDataBinding.tv1Same.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(
//                        R.mipmap.ic_com_same),
//                        null, null, null);
//                mDataBinding.tv1Diff.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(
//                        R.mipmap.ic_com_diff),
//                        null, null, null);
//                if (rightGoodsDetailsBean.getResult().getGoodsDetails() != null
//                        && leftGoodsDetailsBean.getResult().getGoodsDetails() != null) {
//                    setDiff1();
//                }
//
//            }
//        });
//
//        mDataBinding.tv2Same.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDataBinding.tv2Same.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(
//                        R.mipmap.ic_com_diff),
//                        null, null, null);
//                mDataBinding.tv2Diff.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(
//                        R.mipmap.ic_com_same),
//                        null, null, null);
//                if (rightGoodsDetailsBean.getResult().getGoodsDetails() != null
//                        && leftGoodsDetailsBean.getResult().getGoodsDetails() != null) {
//                    setSame2();
//                }
//
//            }
//        });
//
//        mDataBinding.tv2Diff.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDataBinding.tv2Same.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(
//                        R.mipmap.ic_com_same),
//                        null, null, null);
//                mDataBinding.tv2Diff.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(
//                        R.mipmap.ic_com_diff),
//                        null, null, null);
//                if (rightGoodsDetailsBean.getResult().getGoodsDetails() != null
//                        && leftGoodsDetailsBean.getResult().getGoodsDetails() != null) {
//                    setDiff2();
//                }
//
//            }
//        });

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
                                if (isLeft) {
                                    leftGoodsDetailsBean = response.body().getData();
                                    initLeft(leftGoodsDetailsBean);
                                } else {
                                    rightGoodsDetailsBean = response.body().getData();
                                    initRight(rightGoodsDetailsBean);
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
        if (getGoodsDetailsBean.getResult().getGoodsDetails().getCounter_price() != null) {
            mDataBinding.tvPriceR.setText(wan(getGoodsDetailsBean.getResult().getGoodsDetails().getCounter_price()));
        }

        mDataBinding.tvDongliR.setText(getGoodsDetailsBean.getResult().getGoodsDetails().getFix_p_10_text());
        mDataBinding.tvChanshangR.setText(getGoodsDetailsBean.getResult().getGoodsDetails().getMerchant_text());
        mDataBinding.tvChandouR.setText(getGoodsDetailsBean.getResult().getGoodsDetails().getFix_p_11_text());
        mDataBinding.tvDunweiR.setText(getGoodsDetailsBean.getResult().getGoodsDetails().getFix_p_12());
        mDataBinding.tvZhongliangR.setText(getGoodsDetailsBean.getResult().getGoodsDetails().getFix_p_1());
        mDataBinding.tvCdrongliangR.setText(getGoodsDetailsBean.getResult().getGoodsDetails().getFix_p_2());

        mDataBinding.tvGonglvR.setText(getGoodsDetailsBean.getResult().getGoodsDetails().getFix_p_5());
        if (rightGoodsDetailsBean != null
                && leftGoodsDetailsBean != null) {
            setDiff1();
            setDiff2();
        }
    }

    private void initLeft(GetGoodsDetailsBean getGoodsDetailsBean) {
        mDataBinding.tvName.setText(getGoodsDetailsBean.getResult().getGoodsDetails().getName());
        if (getGoodsDetailsBean.getResult().getGoodsDetails().getCounter_price() != null) {
            mDataBinding.tvPrice.setText(wan(getGoodsDetailsBean.getResult().getGoodsDetails().getCounter_price()));
        }
        mDataBinding.tvDongli.setText(getGoodsDetailsBean.getResult().getGoodsDetails().getFix_p_10_text());
        mDataBinding.tvChanshang.setText(getGoodsDetailsBean.getResult().getGoodsDetails().getMerchant_text());
        mDataBinding.tvChandou.setText(getGoodsDetailsBean.getResult().getGoodsDetails().getFix_p_11_text());
        mDataBinding.tvDunwei.setText(getGoodsDetailsBean.getResult().getGoodsDetails().getFix_p_12());
        mDataBinding.tvZhongliang.setText(getGoodsDetailsBean.getResult().getGoodsDetails().getFix_p_1());
        mDataBinding.tvCdrongliang.setText(getGoodsDetailsBean.getResult().getGoodsDetails().getFix_p_2());

        mDataBinding.tvGonglv.setText(getGoodsDetailsBean.getResult().getGoodsDetails().getFix_p_5());
        if (rightGoodsDetailsBean != null
                && leftGoodsDetailsBean != null) {
            setDiff1();
            setDiff2();
        }
    }

    private void setSame1() {
        if (leftGoodsDetailsBean.getResult().getGoodsDetails().getFix_p_10_text().
                equals(rightGoodsDetailsBean.getResult().getGoodsDetails().getFix_p_10_text())) {
            mDataBinding.tvDongliR.setBackgroundResource(R.color.e5);
            mDataBinding.tvDongli.setBackgroundResource(R.color.e5);
        } else {
            mDataBinding.tvDongliR.setBackgroundResource(R.color.white);
            mDataBinding.tvDongli.setBackgroundResource(R.color.white);
        }
        if (leftGoodsDetailsBean.getResult().getGoodsDetails().getMerchant_text().
                equals(rightGoodsDetailsBean.getResult().getGoodsDetails().getMerchant_text())) {
            mDataBinding.tvChanshangR.setBackgroundResource(R.color.e5);
            mDataBinding.tvChanshang.setBackgroundResource(R.color.e5);
        } else {
            mDataBinding.tvChanshangR.setBackgroundResource(R.color.white);
            mDataBinding.tvChanshang.setBackgroundResource(R.color.white);
        }
        if (leftGoodsDetailsBean.getResult().getGoodsDetails().getFix_p_11_text().
                equals(rightGoodsDetailsBean.getResult().getGoodsDetails().getFix_p_11_text())) {
            mDataBinding.tvChandouR.setBackgroundResource(R.color.e5);
            mDataBinding.tvChandou.setBackgroundResource(R.color.e5);
        } else {
            mDataBinding.tvChandouR.setBackgroundResource(R.color.white);
            mDataBinding.tvChandou.setBackgroundResource(R.color.white);
        }
        if (leftGoodsDetailsBean.getResult().getGoodsDetails().getFix_p_12().
                equals(rightGoodsDetailsBean.getResult().getGoodsDetails().getFix_p_12())) {
            mDataBinding.tvDunweiR.setBackgroundResource(R.color.e5);
            mDataBinding.tvDunwei.setBackgroundResource(R.color.e5);
        } else {
            mDataBinding.tvDunweiR.setBackgroundResource(R.color.white);
            mDataBinding.tvDunwei.setBackgroundResource(R.color.white);
        }
        if (leftGoodsDetailsBean.getResult().getGoodsDetails().getFix_p_1().
                equals(rightGoodsDetailsBean.getResult().getGoodsDetails().getFix_p_1())) {
            mDataBinding.tvZhongliangR.setBackgroundResource(R.color.e5);
            mDataBinding.tvZhongliang.setBackgroundResource(R.color.e5);
        } else {
            mDataBinding.tvZhongliangR.setBackgroundResource(R.color.white);
            mDataBinding.tvZhongliang.setBackgroundResource(R.color.white);
        }
        if (leftGoodsDetailsBean.getResult().getGoodsDetails().getFix_p_2().
                equals(rightGoodsDetailsBean.getResult().getGoodsDetails().getFix_p_2())) {
            mDataBinding.tvCdrongliangR.setBackgroundResource(R.color.e5);
            mDataBinding.tvCdrongliang.setBackgroundResource(R.color.e5);
        } else {
            mDataBinding.tvCdrongliangR.setBackgroundResource(R.color.white);
            mDataBinding.tvCdrongliang.setBackgroundResource(R.color.white);
        }
    }

    private void setDiff1() {
        if (!leftGoodsDetailsBean.getResult().getGoodsDetails().getFix_p_10_text().
                equals(rightGoodsDetailsBean.getResult().getGoodsDetails().getFix_p_10_text())) {
            mDataBinding.tvDongliR.setBackgroundResource(R.color.e5);
            mDataBinding.tvDongli.setBackgroundResource(R.color.e5);
        } else {
            mDataBinding.tvDongliR.setBackgroundResource(R.color.white);
            mDataBinding.tvDongli.setBackgroundResource(R.color.white);
        }
        if (!leftGoodsDetailsBean.getResult().getGoodsDetails().getMerchant_text().
                equals(rightGoodsDetailsBean.getResult().getGoodsDetails().getMerchant_text())) {
            mDataBinding.tvChanshangR.setBackgroundResource(R.color.e5);
            mDataBinding.tvChanshang.setBackgroundResource(R.color.e5);
        } else {
            mDataBinding.tvChanshangR.setBackgroundResource(R.color.white);
            mDataBinding.tvChanshang.setBackgroundResource(R.color.white);
        }
        if (!leftGoodsDetailsBean.getResult().getGoodsDetails().getFix_p_11_text().
                equals(rightGoodsDetailsBean.getResult().getGoodsDetails().getFix_p_11_text())) {
            mDataBinding.tvChandouR.setBackgroundResource(R.color.e5);
            mDataBinding.tvChandou.setBackgroundResource(R.color.e5);
        } else {
            mDataBinding.tvChandouR.setBackgroundResource(R.color.white);
            mDataBinding.tvChandou.setBackgroundResource(R.color.white);
        }
        if (!leftGoodsDetailsBean.getResult().getGoodsDetails().getFix_p_12().
                equals(rightGoodsDetailsBean.getResult().getGoodsDetails().getFix_p_12())) {
            mDataBinding.tvDunweiR.setBackgroundResource(R.color.e5);
            mDataBinding.tvDunwei.setBackgroundResource(R.color.e5);
        } else {
            mDataBinding.tvDunweiR.setBackgroundResource(R.color.white);
            mDataBinding.tvDunwei.setBackgroundResource(R.color.white);
        }
        if (!leftGoodsDetailsBean.getResult().getGoodsDetails().getFix_p_1().
                equals(rightGoodsDetailsBean.getResult().getGoodsDetails().getFix_p_1())) {
            mDataBinding.tvZhongliangR.setBackgroundResource(R.color.e5);
            mDataBinding.tvZhongliang.setBackgroundResource(R.color.e5);
        } else {
            mDataBinding.tvZhongliangR.setBackgroundResource(R.color.white);
            mDataBinding.tvZhongliang.setBackgroundResource(R.color.white);
        }
        if (!leftGoodsDetailsBean.getResult().getGoodsDetails().getFix_p_2().
                equals(rightGoodsDetailsBean.getResult().getGoodsDetails().getFix_p_2())) {
            mDataBinding.tvCdrongliangR.setBackgroundResource(R.color.e5);
            mDataBinding.tvCdrongliang.setBackgroundResource(R.color.e5);
        } else {
            mDataBinding.tvCdrongliangR.setBackgroundResource(R.color.white);
            mDataBinding.tvCdrongliang.setBackgroundResource(R.color.white);
        }
    }

    private void setSame2() {
        if (leftGoodsDetailsBean.getResult().getGoodsDetails().getFix_p_5().
                equals(rightGoodsDetailsBean.getResult().getGoodsDetails().getFix_p_5())) {
            mDataBinding.tvGonglvR.setBackgroundResource(R.color.e5);
            mDataBinding.tvGonglv.setBackgroundResource(R.color.e5);
        } else {
            mDataBinding.tvGonglvR.setBackgroundResource(R.color.white);
            mDataBinding.tvGonglv.setBackgroundResource(R.color.white);
        }
    }

    private void setDiff2() {
        if (!leftGoodsDetailsBean.getResult().getGoodsDetails().getFix_p_5().
                equals(rightGoodsDetailsBean.getResult().getGoodsDetails().getFix_p_5())) {
            mDataBinding.tvGonglvR.setBackgroundResource(R.color.e5);
            mDataBinding.tvGonglv.setBackgroundResource(R.color.e5);
        } else {
            mDataBinding.tvGonglvR.setBackgroundResource(R.color.white);
            mDataBinding.tvGonglv.setBackgroundResource(R.color.white);
        }
    }

    private String wan(String toal) {
        if (Double.parseDouble(toal) == 0.0) {
            return "暂无报价";
        } else {
            DecimalFormat df = new DecimalFormat("#.0000");
            toal = df.format(Double.parseDouble(toal) / 10000);
            //保留2位小数
            BigDecimal b = new BigDecimal(Double.parseDouble(toal));
            toal = b.setScale(2, BigDecimal.ROUND_DOWN).toString() + "万";
            return toal;
        }
    }
}
