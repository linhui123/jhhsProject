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
import com.jhhscm.platform.databinding.FragmentServiceRecordBinding;
import com.jhhscm.platform.event.FinishEvent;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.home.action.GetArticleListAction;
import com.jhhscm.platform.fragment.home.bean.GetPageArticleListBean;
import com.jhhscm.platform.fragment.my.store.action.FindBusGoodsOwnerListByUserCodeAction;
import com.jhhscm.platform.fragment.my.store.action.FindBusGoodsOwnerListByUserCodeBean;
import com.jhhscm.platform.fragment.my.store.action.FindBusUserServerListAction;
import com.jhhscm.platform.fragment.my.store.action.FindBusUserServerListBean;
import com.jhhscm.platform.fragment.my.store.viewholder.ServiceRecordViewHolder;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.sign.SignObject;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.recyclerview.WrappedRecyclerView;

import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

public class ServiceRecordFragment extends AbsFragment<FragmentServiceRecordBinding> {

    private String busCode;

    private String user_code = "";
    private String name = "";
    private String phone = "";
    private InnerAdapter mAdapter;
    private int mShowCount = 10;
    private int mCurrentPage = 1;
    private final int START_PAGE = mCurrentPage;

    public static ServiceRecordFragment instance() {
        ServiceRecordFragment view = new ServiceRecordFragment();
        return view;
    }

    @Override
    protected FragmentServiceRecordBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentServiceRecordBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        EventBusUtil.registerEvent(this);
//        mDataBinding.recyclerview.addItemDecoration(new DividerItemDecoration(getContext()));
        busCode = getArguments().getString("code");
        name = getArguments().getString("name");
        phone = getArguments().getString("phone");
        user_code = getArguments().getString("user_code");
        if (name != null) {
            mDataBinding.username.setText(name + " ");
        }
        mDataBinding.phone.setText(phone);

        if (busCode != null && user_code != null) {
            mDataBinding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
            mAdapter = new InnerAdapter(getContext());
            mDataBinding.recyclerview.setAdapter(mAdapter);
            mDataBinding.recyclerview.autoRefresh();
            mDataBinding.recyclerview.setOnPullListener(new WrappedRecyclerView.OnPullListener() {
                @Override
                public void onRefresh(RecyclerView view) {
                    findBusGoodsOwnerListByUserCode(true);
                }

                @Override
                public void onLoadMore(RecyclerView view) {
                    findBusGoodsOwnerListByUserCode(false);
                }
            });
        }
    }

    private void findBusGoodsOwnerListByUserCode(final boolean refresh) {
        if (getContext() != null) {
            mCurrentPage = refresh ? START_PAGE : ++mCurrentPage;
            Map<String, Object> map = new TreeMap<String, Object>();
            map.put("page", mCurrentPage);
            map.put("limit", mShowCount);
            map.put("user_code", user_code);
            map.put("bus_code", ConfigUtils.getCurrentUser(getContext()).getUserCode());
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = SignObject.getSignKey(getActivity(), map, "findBusGoodsOwnerListByUserCode");
            NetBean netBean = new NetBean();
            netBean.setToken(ConfigUtils.getCurrentUser(getContext()).getToken());
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(FindBusGoodsOwnerListByUserCodeAction.newInstance(getContext(), netBean)
                    .request(new AHttpService.IResCallback<BaseEntity<FindBusGoodsOwnerListByUserCodeBean>>() {
                        @Override
                        public void onCallback(int resultCode, Response<BaseEntity<FindBusGoodsOwnerListByUserCodeBean>> response,
                                               BaseErrorInfo baseErrorInfo) {
                            if (getView() != null) {
                                closeDialog();
                                if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                    return;
                                }
                                if (response != null) {
                                    new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                    if (response.body().getCode().equals("200")) {

                                        initView(refresh, response.body().getData());
                                    } else {
                                        ToastUtils.show(getContext(), response.body().getMessage());
                                    }
                                }
                            }
                        }
                    }));
        }
    }

    FindBusGoodsOwnerListByUserCodeBean getPushListBean;

    private void initView(boolean refresh, FindBusGoodsOwnerListByUserCodeBean pushListBean) {
        mDataBinding.tvStoreNum.setText("共" + pushListBean.getSum() + "台设备记录");
        this.getPushListBean = pushListBean;
        if (refresh) {
            mAdapter.setData(pushListBean.getResult().getData());
        } else {
            mAdapter.append(pushListBean.getResult().getData());
        }
        mDataBinding.recyclerview.loadComplete(false, false);
//        mDataBinding.recyclerview.loadComplete(mAdapter.getItemCount() == 0,
//                ((float) getPushListBean.getPage().getTotal() / (float) getPushListBean.getPage().getPageSize()) > mCurrentPage);
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

    private class InnerAdapter extends AbsRecyclerViewAdapter<FindBusGoodsOwnerListByUserCodeBean.ResultBean.DataBean> {
        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<FindBusGoodsOwnerListByUserCodeBean.ResultBean.DataBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ServiceRecordViewHolder(mInflater.inflate(R.layout.item_service_record, parent, false), user_code);
        }
    }
}