package com.jhhscm.platform.fragment.sale;


import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.LoginActivity;
import com.jhhscm.platform.activity.PushOldMechanicsActivity;
import com.jhhscm.platform.databinding.FragmentSaleMachineBinding;
import com.jhhscm.platform.event.ConsultationEvent;
import com.jhhscm.platform.event.OrderSussessEvent;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.home.action.SaveMsgAction;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.UserBean;
import com.jhhscm.platform.http.bean.UserSession;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.DisplayUtils;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.dialog.OrderSuccessDialog;
import com.jhhscm.platform.views.dialog.SimpleDialog;
import com.jhhscm.platform.views.recyclerview.WrappedRecyclerView;

import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

public class SaleMachineFragment extends AbsFragment<FragmentSaleMachineBinding> {
    private int mShowCount = 10;
    private int mCurrentPage = 1;
    private final int START_PAGE = mCurrentPage;

    private SaleMachineAdapter mAdapter;
    private OldGoodOrderHistoryBean oldGoodOrderHistoryBean;

    public static SaleMachineFragment instance() {
        SaleMachineFragment view = new SaleMachineFragment();
        return view;
    }

    @Override
    protected FragmentSaleMachineBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentSaleMachineBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        EventBusUtil.registerEvent(this);
        mDataBinding.rv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new SaleMachineAdapter(getContext());
        mDataBinding.rv.setAdapter(mAdapter);
        mDataBinding.rv.autoRefresh();
        mDataBinding.rv.setOnPullListener(new WrappedRecyclerView.OnPullListener() {
            @Override
            public void onRefresh(RecyclerView view) {
                oldGoodOrderHistory(true);
            }

            @Override
            public void onLoadMore(RecyclerView view) {
                oldGoodOrderHistory(false);
            }
        });

        mDataBinding.tvFabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConfigUtils.getCurrentUser(getContext()) != null
                        && ConfigUtils.getCurrentUser(getContext()).getMobile() != null) {
                    PushOldMechanicsActivity.start(getContext());
                } else {
                    startNewActivity(LoginActivity.class);
                }
            }
        });
    }

    /**
     * 咨询
     */
    public void onEvent(OrderSussessEvent event) {
        if (event.phone != null) {
            saveMsg(event.phone);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }

    /**
     * 二手机历史oldGoodOrderHistory
     */
    private void oldGoodOrderHistory(final boolean refresh) {
        mCurrentPage = refresh ? START_PAGE : ++mCurrentPage;
        Map<String, String> map = new TreeMap<String, String>();
        map.put("page", mCurrentPage + "");
        map.put("limit", mShowCount + "");
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getActivity(), map, "oldGoodOrderHistory");
        NetBean netBean = new NetBean();
        netBean.setToken("");
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(OldGoodOrderHistoryAction.newInstance(getContext(), netBean)
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
                                    doSuccessResponse(refresh, response.body().getData());
                                } else {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
    }

    private void doSuccessResponse(boolean refresh, OldGoodOrderHistoryBean bean) {
        this.oldGoodOrderHistoryBean = bean;
        if (refresh) {
            mAdapter.setData(bean);
        } else {
            mAdapter.setExpend(bean);
        }
        mDataBinding.rv.getAdapter().notifyDataSetChanged();
        mDataBinding.rv.loadComplete(mAdapter.getItemCount() == 0, ((float) oldGoodOrderHistoryBean.getPage().getTotal() / (float) oldGoodOrderHistoryBean.getPage().getPageSize()) > mCurrentPage);
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
                                    new OrderSuccessDialog(getContext(), new OrderSuccessDialog.CallbackListener() {

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
