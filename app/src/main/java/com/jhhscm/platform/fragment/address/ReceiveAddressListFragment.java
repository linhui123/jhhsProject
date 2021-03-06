package com.jhhscm.platform.fragment.address;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.LoginActivity;
import com.jhhscm.platform.activity.ReceiveAddressDetailActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.FragmentReceiveAddressListBinding;
import com.jhhscm.platform.event.AddressRefreshEvent;
import com.jhhscm.platform.event.AddressResultEvent;
import com.jhhscm.platform.fragment.GoodsToCarts.FindAddressListBean;
import com.jhhscm.platform.fragment.GoodsToCarts.action.FindAddressListAction;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.UserSession;
import com.jhhscm.platform.http.sign.SignObject;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.recyclerview.DividerItemDecoration;
import com.jhhscm.platform.views.recyclerview.WrappedRecyclerView;

import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;


public class ReceiveAddressListFragment extends AbsFragment<FragmentReceiveAddressListBinding> {

    private InnerAdapter receiveAddressAdapter;
    private FindAddressListBean findAddressListBean;
    private int mShowCount = 10;
    private int mCurrentPage = 1;
    private final int START_PAGE = mCurrentPage;
    private boolean isResult;//判断是否需要回调
    private UserSession userSession;

    public static ReceiveAddressListFragment instance() {
        ReceiveAddressListFragment view = new ReceiveAddressListFragment();
        return view;
    }

    @Override
    protected FragmentReceiveAddressListBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentReceiveAddressListBinding.inflate(inflater, container, attachToRoot);
    }

    public void onEvent(AddressRefreshEvent event) {
        Log.e("AddressRefreshEvent", "event.getType() " + event.getType());
        if (event.getType() == 2) {
            EventBusUtil.post(new AddressResultEvent(event.getDataBean()));
            getActivity().finish();
        } else {
            mDataBinding.rlReceiveAddress.autoRefresh();
        }
    }


    @Override
    protected void setupViews() {
        EventBusUtil.registerEvent(this);
        if (ConfigUtils.getCurrentUser(getContext()) != null
                && ConfigUtils.getCurrentUser(getContext()).getMobile() != null) {
            userSession = ConfigUtils.getCurrentUser(getContext());
        } else {
            startNewActivity(LoginActivity.class);
        }
        isResult = getArguments().getBoolean("isResult", false);

        mDataBinding.rlReceiveAddress.addItemDecoration(new DividerItemDecoration(getContext()));
        mDataBinding.rlReceiveAddress.setLayoutManager(new LinearLayoutManager(getContext()));
        receiveAddressAdapter = new InnerAdapter(getContext());
        mDataBinding.rlReceiveAddress.setAdapter(receiveAddressAdapter);
        mDataBinding.rlReceiveAddress.autoRefresh();
        mDataBinding.rlReceiveAddress.loadComplete(true, false);
        mDataBinding.rlReceiveAddress.setOnPullListener(new WrappedRecyclerView.OnPullListener() {
            @Override
            public void onRefresh(RecyclerView view) {
                findAddressList(true, ConfigUtils.getCurrentUser(getContext()).getUserCode(), ConfigUtils.getCurrentUser(getContext()).getToken());
            }

            @Override
            public void onLoadMore(RecyclerView view) {
                findAddressList(false, ConfigUtils.getCurrentUser(getContext()).getUserCode(), ConfigUtils.getCurrentUser(getContext()).getToken());
            }
        });


        mDataBinding.rlAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReceiveAddressDetailActivity.start(getContext(), 1);
            }
        });

        mDataBinding.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReceiveAddressDetailActivity.start(getContext(), 1);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }

    /**
     * 获取地址列表
     */
    private void findAddressList(final boolean refresh, String userCode, String token) {
        mCurrentPage = refresh ? START_PAGE : ++mCurrentPage;
        showDialog();
        Map<String, Object> map = new TreeMap<String, Object>();
        map.put("user_code", userCode);
        map.put("page", mCurrentPage);
        map.put("limit", mShowCount);
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = SignObject.getSignKey(getActivity(), map, "findAddressList");
        NetBean netBean = new NetBean();
        netBean.setToken(token);
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(FindAddressListAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<FindAddressListBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<FindAddressListBean>> response,
                                           BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                if (response.body().getCode().equals("200")) {
                                    initAddress(refresh, response.body().getData());
                                    Log.e("findAddressList", "获取地址列表成功");
                                } else {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
    }

    private void initAddress(boolean refresh, FindAddressListBean categoryBean) {
        this.findAddressListBean = categoryBean;
        if (refresh) {
            receiveAddressAdapter.setData(categoryBean.getResult().getData());
        } else {
            receiveAddressAdapter.append(categoryBean.getResult().getData());
        }
        mDataBinding.rlReceiveAddress.getAdapter().notifyDataSetChanged();
        mDataBinding.rlReceiveAddress.loadComplete(receiveAddressAdapter.getItemCount() == 0,
                ((float) findAddressListBean.getResult().getPage().getTotal() / (float) findAddressListBean.getResult().getPage().getPageSize()) > mCurrentPage);
    }

    private class InnerAdapter extends AbsRecyclerViewAdapter<FindAddressListBean.ResultBean.DataBean> {
        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<FindAddressListBean.ResultBean.DataBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ReceiveAddressViewHolder(mInflater.inflate(R.layout.item_receive_address, parent, false), isResult);
        }
    }

    public boolean isResult() {
        return isResult;
    }

    public void setResult(boolean result) {
        isResult = result;
    }
}
