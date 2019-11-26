package com.jhhscm.platform.fragment.repayment;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.FragmentRepaymentDetailBinding;
import com.jhhscm.platform.event.RefreshEvent;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.sign.SignObject;
import com.jhhscm.platform.tool.DataUtil;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.dialog.ContractPayDialog;

import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

public class RepaymentDetailFragment extends AbsFragment<FragmentRepaymentDetailBinding> {
    private InnerAdapter mAdapter;
    private String contractCode;

    private int mShowCount = 10;
    private int mCurrentPage = 1;
    private final int START_PAGE = mCurrentPage;

    public static RepaymentDetailFragment instance() {
        RepaymentDetailFragment view = new RepaymentDetailFragment();
        return view;
    }

    @Override
    protected FragmentRepaymentDetailBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentRepaymentDetailBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        EventBusUtil.registerEvent(this);
        contractCode = getArguments().getString("contractCode");
        Log.e("setupViews", "contractCode = " + contractCode);
        if (contractCode != null && contractCode.length() > 0) {
            contractDetail(contractCode);
            mDataBinding.tvPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new ContractPayDialog(getContext()).show();
                }
            });
        } else {
            ToastUtil.show(getContext(), "找不到该合同！");
            getActivity().finish();
        }
    }

    public void onEvent(RefreshEvent event) {
        if (contractCode != null && contractCode.length() > 0) {
            contractDetail(contractCode);
            mDataBinding.tvPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new ContractPayDialog(getContext()).show();
                }
            });
        } else {
            ToastUtil.show(getContext(), "找不到该合同！");
            getActivity().finish();
        }
    }

    /**
     * 合同列表 15927112992
     */
    private void contractDetail(final String contractCode) {
        Map<String, Object> map = new TreeMap<String, Object>();
        map.put("contractCode", contractCode);
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = SignObject.getSignKey(getActivity(), map, "ContractDetail");
        NetBean netBean = new NetBean();
        netBean.setToken("");
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(ContractDetailAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<ContractDetailBean>>() {

                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<ContractDetailBean>> response, BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                if (response.body().getCode().equals("200")) {
                                    initView(response.body().getData());
                                } else if (response.body().getCode().equals("1001")) {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                } else {
                                    ToastUtils.show(getContext(), "网络异常");
                                }
                            }
                        }
                    }
                }));
    }

    private void initView(ContractDetailBean detailBean) {
        mDataBinding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mAdapter = new InnerAdapter(getContext(), detailBean.getCount());
        mDataBinding.recyclerview.setAdapter(mAdapter);

        int count = 0;//待还期数
        if (detailBean.getMoneyPlan() != null && detailBean.getMoneyPlan().size() > 0) {
            for (ContractDetailBean.MoneyPlanBean moneyPlanBean : detailBean.getMoneyPlan()) {
                if (DataUtil.TimeCompare(DataUtil.getCurDate("yyyy-MM"),
                        moneyPlanBean.getRepayTime(), "yyyy-MM")) {//还款日期大于当前日期
                    count++;
                }
            }

            mDataBinding.tv1.setText("全部待还" + count + "期(共" + detailBean.getCount() + "期)");
            mAdapter.setData(detailBean.getMoneyPlan());
        }
    }

    @Override
    public void onDestroy() {
        EventBusUtil.unregisterEvent(this);
        super.onDestroy();
    }

    private class InnerAdapter extends AbsRecyclerViewAdapter<ContractDetailBean.MoneyPlanBean> {
        private int count = 0;

        public InnerAdapter(Context context, int count) {
            super(context);
            this.count = count;
        }

        @Override
        public AbsRecyclerViewHolder<ContractDetailBean.MoneyPlanBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new RepaymentStagesViewHolder(mInflater.inflate(R.layout.item_repay_stages, parent, false), getActivity(), count);
        }
    }

}
