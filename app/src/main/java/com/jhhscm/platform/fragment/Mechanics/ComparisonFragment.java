package com.jhhscm.platform.fragment.Mechanics;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.BrandActivity;
import com.jhhscm.platform.activity.ComparisonDetailActivity;
import com.jhhscm.platform.activity.LoginActivity;
import com.jhhscm.platform.databinding.FragmentComparisonBinding;
import com.jhhscm.platform.event.CompMechanicsEvent;
import com.jhhscm.platform.fragment.Mechanics.adapter.CompairsonAdapter;
import com.jhhscm.platform.fragment.Mechanics.bean.GetGoodsDetailsBean;
import com.jhhscm.platform.fragment.Mechanics.bean.GetGoodsPageListBean;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.my.collect.FindCollectListAction;
import com.jhhscm.platform.fragment.my.collect.FindCollectListBean;
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
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.recyclerview.DividerItemDecoration;
import com.jhhscm.platform.views.recyclerview.WrappedRecyclerView;
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

/**
 * 机型对比
 */
public class ComparisonFragment extends AbsFragment<FragmentComparisonBinding> implements CompairsonAdapter.DeletedItemListener, CompairsonAdapter.SelectedListener {
    private CompairsonAdapter compairsonAdapter;//添加机型
    private CompairsonAdapter wAdapter;//历史
    private CompairsonAdapter sAdapter;//收藏
    private GetGoodsDetailsBean getGoodsDetailsBean;
    private UserSession userSession;
    private int mShowCount = 10;
    private int mCurrentPage = 1;
    private final int START_PAGE = mCurrentPage;
    private List<GetGoodsPageListBean.DataBean> selectDataBean;//选中机型
    private List<GetGoodsPageListBean.DataBean> historyDataBean;//历史浏览机型
    private List<GetGoodsPageListBean.DataBean> shoucangDataBean;//收藏机型

    public static ComparisonFragment instance() {
        ComparisonFragment view = new ComparisonFragment();
        return view;
    }

    @Override
    protected FragmentComparisonBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentComparisonBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ConfigUtils.getCurrentUser(getContext()) != null
                && ConfigUtils.getCurrentUser(getContext()).getMobile() != null) {
            userSession = ConfigUtils.getCurrentUser(getContext());
        } else {
            startNewActivity(LoginActivity.class);
        }
    }

    @Override
    protected void setupViews() {
        EventBusUtil.registerEvent(this);
        selectDataBean = new ArrayList<>();
        historyDataBean = new ArrayList<>();
        shoucangDataBean = new ArrayList<>();

        mDataBinding.refreshlayout.setEnableRefresh(false);
        mDataBinding.refreshlayout.setEnableLoadMore(false);
        mDataBinding.refreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
            }
        });

        mDataBinding.refreshlayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
            }
        });

        initView();
        initBottom();
        //选中机型
        getGoodsDetailsBean = (GetGoodsDetailsBean) getArguments().getSerializable("getGoodsDetailsBean");
        if (getGoodsDetailsBean != null) {
            GetGoodsPageListBean.DataBean dataBean = new GetGoodsPageListBean.DataBean();
            dataBean.setGood_code(getGoodsDetailsBean.getResult().getGoodsDetails().getGood_code());
            dataBean.setName(getGoodsDetailsBean.getResult().getGoodsDetails().getName());
            dataBean.setCounter_price(getGoodsDetailsBean.getResult().getGoodsDetails().getCounter_price());
            dataBean.setSelect(true);
            compairsonAdapter.setData(dataBean);
            selectDataBean.add(dataBean);
        }
        //从浏览记录中获取
        GetGoodsPageListBean getGoodsPageListBean = ConfigUtils.getNewMechanics(getContext());
        if (getGoodsPageListBean != null && getGoodsPageListBean.getData() != null && getGoodsPageListBean.getData().size() > 0) {
            if (getGoodsPageListBean.getData().size() > 10) {
                wAdapter.setList(getGoodsPageListBean.getData().subList(1, 10), true);
            } else {
                wAdapter.setList(getGoodsPageListBean.getData().subList(1, getGoodsPageListBean.getData().size()), true);
            }
            //去掉和选中相同项
        }

        mDataBinding.tvHostory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataBinding.rvShoucang.setVisibility(View.GONE);
                mDataBinding.rvWatch.setVisibility(View.VISIBLE);
                mDataBinding.tvHostory.setTextSize(18);
                mDataBinding.tvShoucang.setTextSize(14);
            }
        });
        mDataBinding.tvShoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataBinding.rvWatch.setVisibility(View.GONE);
                mDataBinding.rvShoucang.setVisibility(View.VISIBLE);
                if (userSession == null) {
                    startNewActivity(LoginActivity.class);
                } else {
                    mDataBinding.tvShoucang.setTextSize(18);
                    mDataBinding.tvHostory.setTextSize(14);
                }
            }
        });
    }

    private void initBottom() {
        mDataBinding.tvCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((selectDataBean.size() + historyDataBean.size() + shoucangDataBean.size()) < 2) {
                    ToastUtil.show(getContext(), "请选择两台机型对比");
                } else if ((selectDataBean.size() + historyDataBean.size() + shoucangDataBean.size()) > 2) {
                    ToastUtil.show(getContext(), "最多只能选择两台机型对比");
                } else if ((selectDataBean.size() + historyDataBean.size() + shoucangDataBean.size()) == 2) {
                    String good1 = "";
                    String good2 = "";
                    if (selectDataBean.size() == 1) {
                        if (good1.length() > 0) {
                            good2 = selectDataBean.get(0).getGood_code();
                        } else {
                            good1 = selectDataBean.get(0).getGood_code();
                        }
                    }
                    if (selectDataBean.size() == 2) {
                        good1 = selectDataBean.get(0).getGood_code();
                        good2 = selectDataBean.get(1).getGood_code();
                    }
                    if (historyDataBean.size() == 1) {
                        if (good1.length() > 0) {
                            good2 = historyDataBean.get(0).getGood_code();
                        } else {
                            good1 = historyDataBean.get(0).getGood_code();
                        }
                    }
                    if (historyDataBean.size() == 2) {
                        good1 = historyDataBean.get(0).getGood_code();
                        good2 = historyDataBean.get(1).getGood_code();
                    }
                    if (shoucangDataBean.size() == 1) {
                        if (good1.length() > 0) {
                            good2 = shoucangDataBean.get(0).getGood_code();
                        } else {
                            good1 = shoucangDataBean.get(0).getGood_code();
                        }
                    }
                    if (shoucangDataBean.size() == 2) {
                        good1 = shoucangDataBean.get(0).getGood_code();
                        good2 = shoucangDataBean.get(1).getGood_code();
                    }
                    ComparisonDetailActivity.start(getContext(), good1, good2);
                }
            }
        });

        mDataBinding.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrandActivity.start(getContext(), 3);
            }
        });
    }

    private void initView() {
        mDataBinding.rvSelect.addItemDecoration(new DividerItemDecoration(getContext()));
        mDataBinding.rvSelect.setLayoutManager(new LinearLayoutManager(getActivity()));
        compairsonAdapter = new CompairsonAdapter(getContext());
        compairsonAdapter.setDeletedItemListener(this);
        compairsonAdapter.setSelectedListener(this);
        mDataBinding.rvSelect.setAdapter(compairsonAdapter);

        PlusItemSlideCallback callback = new PlusItemSlideCallback();
        WItemTouchHelperPlus extension = new WItemTouchHelperPlus(callback);
        extension.attachToRecyclerView(mDataBinding.rvSelect);

        mDataBinding.rvWatch.addItemDecoration(new DividerItemDecoration(getContext()));
        mDataBinding.rvWatch.setLayoutManager(new LinearLayoutManager(getActivity()));
        wAdapter = new CompairsonAdapter(getContext());
        mDataBinding.rvWatch.setAdapter(wAdapter);
        wAdapter.setDeletedItemListener(new CompairsonAdapter.DeletedItemListener() {
            @Override
            public void deleted(List<GetGoodsPageListBean.DataBean> resultBeans, int position) {

            }
        });
        wAdapter.setSelectedListener(new CompairsonAdapter.SelectedListener() {
            @Override
            public void select(List<GetGoodsPageListBean.DataBean> resultBeans) {
                historyDataBean.clear();
                for (GetGoodsPageListBean.DataBean dataBean : resultBeans) {
                    if (dataBean.isSelect()) {
                        historyDataBean.add(dataBean);
                    }
                }
            }
        });

        mDataBinding.rvShoucang.addItemDecoration(new DividerItemDecoration(getContext()));
        mDataBinding.rvShoucang.setLayoutManager(new LinearLayoutManager(getActivity()));
        sAdapter = new CompairsonAdapter(getContext());
        mDataBinding.rvShoucang.setAdapter(sAdapter);
        mDataBinding.rvShoucang.loadComplete(true, false);
        mDataBinding.rvShoucang.autoRefresh();
        mDataBinding.rvShoucang.setOnPullListener(new WrappedRecyclerView.OnPullListener() {
            @Override
            public void onRefresh(RecyclerView view) {
                findCollectList(true);
            }

            @Override
            public void onLoadMore(RecyclerView view) {
                findCollectList(false);
            }
        });

        sAdapter.setDeletedItemListener(new CompairsonAdapter.DeletedItemListener() {
            @Override
            public void deleted(List<GetGoodsPageListBean.DataBean> resultBeans, int position) {

            }
        });
        sAdapter.setSelectedListener(new CompairsonAdapter.SelectedListener() {
            @Override
            public void select(List<GetGoodsPageListBean.DataBean> resultBeans) {
                shoucangDataBean.clear();
                for (GetGoodsPageListBean.DataBean dataBean : resultBeans) {
                    if (dataBean.isSelect()) {
                        shoucangDataBean.add(dataBean);
                    }
                }
            }
        });
    }

    public void onEvent(CompMechanicsEvent event) {
        if (event.resultBean != null) {
            if (compairsonAdapter.getItemCount() < 2) {
                GetGoodsPageListBean.DataBean resultBean = new GetGoodsPageListBean.DataBean();
                resultBean.setSelect(true);
                resultBean.setName(event.resultBean.getName());
                resultBean.setGood_code(event.resultBean.getCode());
                compairsonAdapter.setData(resultBean);
                selectDataBean.add(resultBean);
            } else {
                ToastUtil.show(getContext(), "最多只允许添加两台机型");
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }


    /**
     * 根据用户编号查看收藏列表
     */
    private void findCollectList(final boolean refresh) {
        mCurrentPage = refresh ? START_PAGE : ++mCurrentPage;
        Map<String, Object> map = new TreeMap<String, Object>();
        map.put("user_code", ConfigUtils.getCurrentUser(getContext()).getUserCode());
        map.put("goods_type", "1");
        map.put("page", mCurrentPage);
        map.put("limit", mShowCount);
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = SignObject.getSignKey(getActivity(), map, "findCollectList: 1");
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
                                    FindCollectListBean findCollectListBean = response.body().getData();
                                    List<GetGoodsPageListBean.DataBean> dataBeans = new ArrayList<>();
                                    if (findCollectListBean.getData() != null
                                            && findCollectListBean.getData().size() > 0) {
                                        for (int i = 0; i < findCollectListBean.getData().size(); i++) {
                                            GetGoodsPageListBean.DataBean resultBean = new GetGoodsPageListBean.DataBean();
                                            resultBean.setSelect(false);
                                            resultBean.setName(findCollectListBean.getData().get(i).getName());
                                            resultBean.setGood_code(findCollectListBean.getData().get(i).getCode());
                                            dataBeans.add(resultBean);
                                            if (i == findCollectListBean.getData().size() - 1) {
                                                sAdapter.setList(dataBeans, refresh);
                                            }
                                        }
                                    } else {
                                        sAdapter.setList(dataBeans, refresh);
                                    }
                                    mDataBinding.rvShoucang.getAdapter().notifyDataSetChanged();
                                    mDataBinding.rvShoucang.loadComplete(sAdapter.getItemCount() == 0, ((float) findCollectListBean.getPage().getTotal() / (float) findCollectListBean.getPage().getPageSize()) > mCurrentPage);

                                } else {
                                    if (response.body().getCode().equals("1003")) {
                                        ToastUtils.show(getContext(), "登录过期，请重新登录");
                                        LoginActivity.start(getContext());
                                    } else {
                                        ToastUtils.show(getContext(), response.body().getMessage());
                                    }
                                    mDataBinding.rvShoucang.loadComplete(true, false);
                                }
                            }
                        }
                    }
                }));
    }

    @Override
    public void deleted(List<GetGoodsPageListBean.DataBean> resultBean, int position) {
        if (selectDataBean.contains(resultBean.get(position))) {
            selectDataBean.remove(resultBean.get(position));
        }
        compairsonAdapter.removeDataByPosition(position);
    }

    @Override
    public void select(List<GetGoodsPageListBean.DataBean> resultBeans) {
        selectDataBean.clear();
        for (GetGoodsPageListBean.DataBean dataBean : resultBeans) {
            if (dataBean.isSelect()) {
                selectDataBean.add(dataBean);
            }
        }
        compairsonAdapter.notifyDataSetChanged();
    }
}

