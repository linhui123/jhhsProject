package com.jhhscm.platform.fragment.my.mechanics;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.BuildConfig;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.AddDeviceActivity;
import com.jhhscm.platform.activity.LoginActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.FragmentMyMechanicsBinding;
import com.jhhscm.platform.event.DelDeviceEvent;
import com.jhhscm.platform.event.LesseeFinishEvent;
import com.jhhscm.platform.event.RefreshEvent;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.ResultBean;
import com.jhhscm.platform.http.bean.UserSession;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.dialog.OrderSuccessDialog;
import com.jhhscm.platform.views.recyclerview.WrappedRecyclerView;

import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;


public class MyMechanicsFragment extends AbsFragment<FragmentMyMechanicsBinding> {
    private InnerAdapter mAdapter;
    private int mShowCount = 10;
    private int mCurrentPage = 1;
    private final int START_PAGE = mCurrentPage;
    private UserSession userSession;


    public static MyMechanicsFragment instance() {
        MyMechanicsFragment view = new MyMechanicsFragment();
        return view;
    }

    @Override
    protected FragmentMyMechanicsBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentMyMechanicsBinding.inflate(inflater, container, attachToRoot);
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

        mDataBinding.wrvRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new InnerAdapter(getContext());
        mAdapter.setHasStableIds(true);
        mDataBinding.wrvRecycler.setAdapter(mAdapter);
        mDataBinding.wrvRecycler.autoRefresh();
        mDataBinding.wrvRecycler.setOnPullListener(new WrappedRecyclerView.OnPullListener() {
            @Override
            public void onRefresh(RecyclerView view) {
                findGoodsOwner(true);
            }

            @Override
            public void onLoadMore(RecyclerView view) {
                findGoodsOwner(false);
            }
        });

        mDataBinding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDeviceActivity.start(getContext(), 0);
            }
        });
    }

    /**
     * 个人中心我的设备列表
     */
    private void findGoodsOwner(final boolean refresh) {
        if (getContext() != null) {
            mCurrentPage = refresh ? START_PAGE : ++mCurrentPage;
            Map<String, String> map = new TreeMap<String, String>();
            map.put("user_code", ConfigUtils.getCurrentUser(getContext()).getUserCode());
            map.put("page", mCurrentPage + "");
            map.put("limit", mShowCount + "");
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = Sign.getSignKey(getActivity(), map, "findOldGoodByUserCode");
            NetBean netBean = new NetBean();
            netBean.setToken(ConfigUtils.getCurrentUser(getContext()).getToken());
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(FindGoodsOwnerAction.newInstance(getContext(), netBean)
                    .request(new AHttpService.IResCallback<BaseEntity<FindGoodsOwnerBean>>() {
                        @Override
                        public void onCallback(int resultCode, Response<BaseEntity<FindGoodsOwnerBean>> response,
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
                                    } else if (!BuildConfig.DEBUG && response.body().getCode().equals("1006")) {
                                        ToastUtils.show(getContext(), "网络错误");
                                    } else {
                                        ToastUtils.show(getContext(), response.body().getMessage());
                                    }
                                }
                            }
                        }
                    }));
        }
    }

    FindGoodsOwnerBean findOldGoodByUserCodeBean;

    private void doSuccessResponse(boolean refresh, FindGoodsOwnerBean getGoodsPageList) {
        this.findOldGoodByUserCodeBean = getGoodsPageList;
        if (refresh) {
            mAdapter.setData(getGoodsPageList.getData());
        } else {
            mAdapter.append(getGoodsPageList.getData());
        }
        mDataBinding.wrvRecycler.getAdapter().notifyDataSetChanged();
        mDataBinding.wrvRecycler.loadComplete(mAdapter.getItemCount() == 0, ((float) findOldGoodByUserCodeBean.getPage().getTotal() / (float) findOldGoodByUserCodeBean.getPage().getPageSize()) > mCurrentPage);
    }

    public void onEvent(LesseeFinishEvent event) {
        new OrderSuccessDialog(getContext(), "您的资料已提交成功！", new OrderSuccessDialog.CallbackListener() {

            @Override
            public void clickYes() {

            }
        }).show();
    }

    public void onEvent(DelDeviceEvent event) {
        if (event.code != null) {
            delGoodsOwner(event.code);
        }
    }

    public void onEvent(RefreshEvent event) {
        mAdapter.clear();
        mDataBinding.wrvRecycler.autoRefresh();
    }

    /**
     * 个人中心我的设备 删除
     */
    private void delGoodsOwner(String code) {
        if (getContext() != null) {
            Map<String, String> map = new TreeMap<String, String>();
            map.put("user_code", ConfigUtils.getCurrentUser(getContext()).getUserCode());
            map.put("goods_owner_code", code);
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = Sign.getSignKey(getActivity(), map, "delGoodsOwner");
            NetBean netBean = new NetBean();
            netBean.setToken(ConfigUtils.getCurrentUser(getContext()).getToken());
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(DelGoodsOwnerAction.newInstance(getContext(), netBean)
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
                                        mDataBinding.wrvRecycler.autoRefresh();
                                        ToastUtil.show(getContext(), "删除成功");
                                    }else if (!BuildConfig.DEBUG && response.body().getCode().equals("1006")) {
                                        ToastUtils.show(getContext(), "网络错误");
                                    } else {
                                        ToastUtils.show(getContext(), response.body().getMessage());
                                    }
                                }
                            }
                        }
                    }));
        }
    }

    private class InnerAdapter extends AbsRecyclerViewAdapter<FindGoodsOwnerBean.DataBean> {
        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public AbsRecyclerViewHolder<FindGoodsOwnerBean.DataBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyMechanicsViewHolder(mInflater.inflate(R.layout.item_device, parent, false));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }
}
