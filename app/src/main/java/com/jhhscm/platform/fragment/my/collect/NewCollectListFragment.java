package com.jhhscm.platform.fragment.my.collect;


import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.LoginActivity;
import com.jhhscm.platform.databinding.FragmentCollectListBinding;
import com.jhhscm.platform.event.AddressResultEvent;
import com.jhhscm.platform.event.ConsultationEvent;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.home.action.SaveMsgAction;
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
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.dialog.SimpleDialog;
import com.jhhscm.platform.views.dialog.TelPhoneDialog;
import com.jhhscm.platform.views.recyclerview.DividerItemStrokeDecoration;
import com.jhhscm.platform.views.slideswaphelper.PlusItemSlideCallback;
import com.jhhscm.platform.views.slideswaphelper.WItemTouchHelperPlus;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;


public class NewCollectListFragment extends AbsFragment<FragmentCollectListBinding> implements MyNewCollectionAdapter.DeletedItemListener {
    private MyNewCollectionAdapter recAdapter;
    private UserSession userSession;
    private int mShowCount = 20;
    private int mCurrentPage = 1;
    private final int START_PAGE = mCurrentPage;

    private static final int TAB_COUNT = 3;
    private String type = "0";

    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    private int mScreenWidth = 0; // 屏幕宽度

    public static NewCollectListFragment instance() {
        NewCollectListFragment view = new NewCollectListFragment();
        return view;
    }

    @Override
    protected FragmentCollectListBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentCollectListBinding.inflate(inflater, container, attachToRoot);
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

        mDataBinding.refresh.setEnableLastTime(false);
        mDataBinding.load.setEnableLastTime(false);
        mDataBinding.refreshlayout.setEnableRefresh(true);
        mDataBinding.refreshlayout.setEnableLoadMore(false);
        mDataBinding.refreshlayout.autoRefresh();
        mDataBinding.refreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                findCollectList(true, type);
            }
        });

        mDataBinding.refreshlayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                findCollectList(false, type);

            }
        });
        initView();
    }

    private void initView() {
        mDataBinding.rvGouwuche.addItemDecoration(new DividerItemStrokeDecoration(getContext()));
        mDataBinding.rvGouwuche.setLayoutManager(new LinearLayoutManager(getActivity()));
        recAdapter = new MyNewCollectionAdapter(getContext());
        recAdapter.setDeletedItemListener(this);
        recAdapter.setHasStableIds(true);
        mDataBinding.rvGouwuche.setAdapter(recAdapter);

        PlusItemSlideCallback callback = new PlusItemSlideCallback();
        WItemTouchHelperPlus extension = new WItemTouchHelperPlus(callback);
        extension.attachToRecyclerView(mDataBinding.rvGouwuche);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }

    public void onEvent(AddressResultEvent messageEvent) {
        mDataBinding.refreshlayout.autoRefresh();
    }

    public void onEvent(ConsultationEvent event) {
        if (event != null && event.type == 2) {
            new TelPhoneDialog(getContext(), new TelPhoneDialog.CallbackListener() {

                @Override
                public void clickYes(String phone) {
                    saveMsg(phone, "2");
                }
            }).show();
        }
    }


    /**
     * 根据用户编号查看收藏列表
     */
    private void findCollectList(final boolean refresh, final String type) {
        mCurrentPage = refresh ? START_PAGE : ++mCurrentPage;
        Map<String, String> map = new TreeMap<String, String>();
        map.put("user_code", ConfigUtils.getCurrentUser(getContext()).getUserCode());
        map.put("goods_type", type);
        map.put("page", mCurrentPage + "");
        map.put("limit", mShowCount + "");
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getActivity(), map, "findCollectList: " + "type" + type);
        NetBean netBean = new NetBean();
        netBean.setToken(ConfigUtils.getCurrentUser(getContext()).getToken());
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(FindCollectListAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<FindCollectListBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<FindCollectListBean>> response, BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                if (response.body().getCode().equals("200")) {
                                    initData(response.body().getData().getData(), refresh);
                                } else if (response.body().getCode().equals("1003")) {
                                    ToastUtils.show(getContext(), "登录信息过期，请重新登录");
                                    startNewActivity(LoginActivity.class);
                                } else {
                                    ToastUtils.show(getContext(), "error " + type + ":" + response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
    }

    List<FindCollectListBean.DataBean> getCartGoodsByUserCodeBean;

    private void initData(List<FindCollectListBean.DataBean> resultBeans, boolean refresh) {
        if (resultBeans.size() == 1) {
            resultBeans.get(0).setType(type);
            recAdapter.setList(resultBeans, refresh);
        } else if (resultBeans.size() > 1) {
            for (int i = 0; i < resultBeans.size(); i++) {
                resultBeans.get(i).setType(type);
                if (i == resultBeans.size() - 1) {
                    recAdapter.setList(resultBeans, refresh);
                }
            }
        } else {
            recAdapter.setList(resultBeans, refresh);
        }
        if (resultBeans.size() > 0) {
            mDataBinding.rlCaseBaseNull.setVisibility(View.GONE);
        }
        if (refresh) {
            getCartGoodsByUserCodeBean = resultBeans;
            mDataBinding.refreshlayout.finishRefresh();
        } else {
            getCartGoodsByUserCodeBean.addAll(resultBeans);
            mDataBinding.refreshlayout.finishLoadMore();
        }
        if (recAdapter.getItemCount() == 0) {
            mDataBinding.rlCaseBaseNull.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void deleted(FindCollectListBean.DataBean resultBean) {
        collectDelete(resultBean.getGood_code());
    }

    /**
     * 取消收藏
     */
    private void collectDelete(final String good_code) {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("user_code", userSession.getUserCode());
        map.put("good_code", good_code);
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getActivity(), map, "collectDelete: ");
        NetBean netBean = new NetBean();
        netBean.setToken(userSession.getToken());
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(CollectDeleteAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<ResultBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<ResultBean>> response, BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                if (response.body().getCode().equals("200")) {
                                    mDataBinding.refreshlayout.autoRefresh();
                                } else {
                                    ToastUtils.show(getContext(), "error " + type + ":" + response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
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
}

