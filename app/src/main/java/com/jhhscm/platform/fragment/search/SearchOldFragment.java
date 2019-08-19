package com.jhhscm.platform.fragment.search;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.FragmentSearchNewBinding;
import com.jhhscm.platform.event.ConsultationEvent;
import com.jhhscm.platform.event.SerachEvent;
import com.jhhscm.platform.fragment.Mechanics.action.GetGoodsPageListAction;
import com.jhhscm.platform.fragment.Mechanics.action.GetOldPageListAction;
import com.jhhscm.platform.fragment.Mechanics.bean.GetGoodsPageListBean;
import com.jhhscm.platform.fragment.Mechanics.bean.GetOldPageListBean;
import com.jhhscm.platform.fragment.Mechanics.holder.NewMechanicsViewHolder;
import com.jhhscm.platform.fragment.Mechanics.holder.OldMechanicsViewHolder;
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
import com.jhhscm.platform.views.dialog.SimpleDialog;
import com.jhhscm.platform.views.dialog.TelPhoneDialog;
import com.jhhscm.platform.views.recyclerview.DividerItemStrokeDecoration;
import com.jhhscm.platform.views.recyclerview.WrappedRecyclerView;

import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;


public class SearchOldFragment extends AbsFragment<FragmentSearchNewBinding> {
    private InnerAdapter mAdapter;
    private int mShowCount = 10;
    private int mCurrentPage = 1;
    private final int START_PAGE = mCurrentPage;
    private String keyword = "";

    public static SearchOldFragment instance() {
        SearchOldFragment view = new SearchOldFragment();
        return view;
    }

    @Override
    protected FragmentSearchNewBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentSearchNewBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        EventBusUtil.registerEvent(this);
        initView();
    }

    private void initView() {
        mDataBinding.rvGouwuche.addItemDecoration(new DividerItemStrokeDecoration(getContext()));
        mDataBinding.rvGouwuche.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new InnerAdapter(getContext());
        mDataBinding.rvGouwuche.setAdapter(mAdapter);
//        mDataBinding.rvGouwuche.autoRefresh();
        mDataBinding.rvGouwuche.setOnPullListener(new WrappedRecyclerView.OnPullListener() {
            @Override
            public void onRefresh(RecyclerView view) {
                getOldPageList(true);
            }

            @Override
            public void onLoadMore(RecyclerView view) {
                getOldPageList(false);
            }
        });
    }

    public void onEvent(SerachEvent event) {
        if (event.getContent() != null) {
            keyword = event.getContent();
            mDataBinding.rvGouwuche.autoRefresh();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }


    public void onEvent(ConsultationEvent event) {
        if (event != null && event.type == 3) {
            new TelPhoneDialog(getContext(), new TelPhoneDialog.CallbackListener() {

                @Override
                public void clickYes(String phone) {
                    saveMsg(phone, "3");
                }
            }).show();
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
        String sign = Sign.getSignKey(getContext(), map, "saveMsg");
        NetBean netBean = new NetBean();
        netBean.setToken("");
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(SaveMsgAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity> response, BaseErrorInfo baseErrorInfo) {
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
                }));
    }

    /**
     * 获取新机列表
     */
    private void getOldPageList(final boolean refresh) {
        if (getContext() != null) {
            mCurrentPage = refresh ? START_PAGE : ++mCurrentPage;
            Map<String, String> map = new TreeMap<String, String>();
            map.put("keyword",  keyword.trim());
            map.put("page", mCurrentPage + "");
            map.put("limit", mShowCount + "");
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = Sign.getSignKey(getActivity(), map, "getGoodsPageList");
            NetBean netBean = new NetBean();
            netBean.setToken("");
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(GetOldPageListAction.newInstance(getContext(), netBean)
                    .request(new AHttpService.IResCallback<BaseEntity<GetOldPageListBean>>() {
                        @Override
                        public void onCallback(int resultCode, Response<BaseEntity<GetOldPageListBean>> response,
                                               BaseErrorInfo baseErrorInfo) {
                            if (getView() != null) {
                                closeDialog();

                                if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                    return;
                                }
                                if (response != null) {
                                    new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                    if (response.body().getCode().equals("200")) {
                                        doSuccessResponse(refresh, response.body().getData());
                                    } else {
                                        mDataBinding.rvGouwuche.loadComplete(true, false);
                                        ToastUtils.show(getContext(), response.body().getMessage());
                                    }
                                }
                            }
                        }
                    }));
        }
    }

    private GetOldPageListBean getOldPageListBean;

    private void doSuccessResponse(boolean refresh, GetOldPageListBean getOldPageList) {
        this.getOldPageListBean = getOldPageList;
        if (refresh) {
            mAdapter.setData(getOldPageList.getData());
        } else {
            mAdapter.append(getOldPageList.getData());
        }
        mDataBinding.rvGouwuche.getAdapter().notifyDataSetChanged();
        mDataBinding.rvGouwuche.loadComplete(mAdapter.getItemCount() == 0, ((float) getOldPageListBean.getPage().getTotal() / (float) getOldPageListBean.getPage().getPageSize()) > mCurrentPage);
    }

    private class InnerAdapter extends AbsRecyclerViewAdapter<GetOldPageListBean.DataBean> {
        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<GetOldPageListBean.DataBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new OldMechanicsViewHolder(mInflater.inflate(R.layout.item_mechanics_old, parent, false));
        }
    }
}