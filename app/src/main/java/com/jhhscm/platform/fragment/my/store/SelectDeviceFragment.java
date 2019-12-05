package com.jhhscm.platform.fragment.my.store;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.FragmentSelectDeviceBinding;
import com.jhhscm.platform.event.FinishEvent;
import com.jhhscm.platform.event.StoreDeviceEvent;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.my.store.action.FindUserGoodsOwnerAction;
import com.jhhscm.platform.fragment.my.store.action.FindUserGoodsOwnerBean;
import com.jhhscm.platform.fragment.my.store.viewholder.MyDeviceSelectItemViewHolder;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.recyclerview.DividerItemDecoration;
import com.jhhscm.platform.views.recyclerview.WrappedRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

public class SelectDeviceFragment extends AbsFragment<FragmentSelectDeviceBinding> {

    private InnerAdapter mAdapter;
    private int mShowCount = 10;
    private int mCurrentPage = 1;
    private final int START_PAGE = mCurrentPage;
    private String phone;

    public static SelectDeviceFragment instance() {
        SelectDeviceFragment view = new SelectDeviceFragment();
        return view;
    }

    @Override
    protected FragmentSelectDeviceBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentSelectDeviceBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        EventBusUtil.registerEvent(this);
        phone = getArguments().getString("phone");
        if (phone != null && phone.length() > 0) {
            mDataBinding.recyclerview.addItemDecoration(new DividerItemDecoration(getContext()));
            mDataBinding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
            mAdapter = new InnerAdapter(getContext());
            mDataBinding.recyclerview.setAdapter(mAdapter);
            mDataBinding.recyclerview.autoRefresh();
            mDataBinding.recyclerview.setOnPullListener(new WrappedRecyclerView.OnPullListener() {
                @Override
                public void onRefresh(RecyclerView view) {
                    findUserGoodsOwner(true);
                }

                @Override
                public void onLoadMore(RecyclerView view) {
                    findUserGoodsOwner(false);
                }
            });

            mDataBinding.tvNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<FindUserGoodsOwnerBean.DataBean> dataBeans = new ArrayList<>();
                    for (int i = 0; i < mAdapter.getItemCount(); i++) {
                        if (mAdapter.get(i).isSelect()) {
                            dataBeans.add(mAdapter.get(i));
                        }
                    }
                    EventBusUtil.post(new StoreDeviceEvent(dataBeans));
                    getActivity().finish();
                }
            });
        } else {
            ToastUtil.show(getContext(), "用户手机号不能为空");
            getActivity().finish();
        }


    }

    /**
     * v1-3/busorder/findUserGoodsOwner
     * 商户创建订单选择用户列表
     */
    private void findUserGoodsOwner(final boolean refresh) {
        if (getContext() != null) {
            mCurrentPage = refresh ? START_PAGE : ++mCurrentPage;
            Map<String, String> map = new TreeMap<String, String>();
            map.put("mobile", phone);
            map.put("page", mCurrentPage + "");
            map.put("limit", mShowCount + "");
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = Sign.getSignKey(getActivity(), map, "findUserGoodsOwner");
            NetBean netBean = new NetBean();
            netBean.setToken(ConfigUtils.getCurrentUser(getContext()).getToken());
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(FindUserGoodsOwnerAction.newInstance(getContext(), netBean)
                    .request(new AHttpService.IResCallback<BaseEntity<FindUserGoodsOwnerBean>>() {
                        @Override
                        public void onCallback(int resultCode, Response<BaseEntity<FindUserGoodsOwnerBean>> response,
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
                                        ToastUtils.show(getContext(), response.body().getMessage());
                                    }
                                }
                            }
                        }
                    }));
        }
    }

    FindUserGoodsOwnerBean findOldGoodByUserCodeBean;

    private void doSuccessResponse(boolean refresh, FindUserGoodsOwnerBean getGoodsPageList) {
        this.findOldGoodByUserCodeBean = getGoodsPageList;
        if (refresh) {
            mAdapter.setData(getGoodsPageList.getData());
        } else {
            mAdapter.append(getGoodsPageList.getData());
        }
        mDataBinding.recyclerview.loadComplete(true,false);
//        mDataBinding.recyclerview.loadComplete(mAdapter.getItemCount() == 0, ((float) findOldGoodByUserCodeBean.getPage().getTotal() / (float) findOldGoodByUserCodeBean.getPage().getPageSize()) > mCurrentPage);
    }

    public void onEvent(FinishEvent event) {
        if (event.getType() == 1) {
            getActivity().finish();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }

    private class InnerAdapter extends AbsRecyclerViewAdapter<FindUserGoodsOwnerBean.DataBean> {
        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<FindUserGoodsOwnerBean.DataBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyDeviceSelectItemViewHolder(mInflater.inflate(R.layout.item_store_select_device, parent, false));
        }
    }
}