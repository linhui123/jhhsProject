package com.jhhscm.platform.fragment.Mechanics;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.SearchActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.FragmentPeiJianBinding;
import com.jhhscm.platform.fragment.Mechanics.action.FindBrandAction;
import com.jhhscm.platform.fragment.Mechanics.action.FindCategoryAction;
import com.jhhscm.platform.fragment.Mechanics.adapter.BrandAdapter;
import com.jhhscm.platform.fragment.Mechanics.adapter.JXDropAdapter;
import com.jhhscm.platform.fragment.Mechanics.adapter.SelectedAdapter;
import com.jhhscm.platform.fragment.Mechanics.bean.FindBrandBean;
import com.jhhscm.platform.fragment.Mechanics.bean.FindCategoryBean;
import com.jhhscm.platform.fragment.Mechanics.bean.GetComboBoxBean;
import com.jhhscm.platform.fragment.Mechanics.holder.PeiJianViewHolder;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.home.action.FindCategoryHomePageAction;
import com.jhhscm.platform.fragment.home.bean.FindCategoryHomePageBean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.jpush.ExampleUtil;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.DisplayUtils;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.recyclerview.DividerItemStrokeDecoration;
import com.jhhscm.platform.views.recyclerview.WrappedRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

public class PeiJianFragment extends AbsFragment<FragmentPeiJianBinding> {
    private InnerAdapter mAdapter;
    private SelectedAdapter selectedAdapter;
    private List<GetComboBoxBean.ResultBean> resultBeanList;
    private int mShowCount = 10;
    private int mCurrentPage = 1;
    private final int START_PAGE = mCurrentPage;

    private String sort_type = "";//排序
    private String category_id = "";//类型
    private String brand_id = "";//品牌

    public static PeiJianFragment instance() {
        PeiJianFragment view = new PeiJianFragment();
        return view;
    }

    @Override
    protected FragmentPeiJianBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentPeiJianBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        RelativeLayout.LayoutParams llParams = (RelativeLayout.LayoutParams) mDataBinding.rlTop.getLayoutParams();
        llParams.topMargin += DisplayUtils.getStatusBarHeight(getContext());
        mDataBinding.rlTop.setLayoutParams(llParams);

        if (getArguments() != null) {
            category_id = getArguments().getString("category_id");
            if (getArguments().getString("category_namw") != null
                    && getArguments().getString("category_namw").length() > 0) {
                mDataBinding.tvQuanbu.setText(getArguments().getString("category_namw"));
            }
        }

        mDataBinding.wrvRecycler.addItemDecoration(new DividerItemStrokeDecoration(getContext()));
        mDataBinding.wrvRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new InnerAdapter(getContext());
        mDataBinding.wrvRecycler.setAdapter(mAdapter);
        mDataBinding.wrvRecycler.autoRefresh();
        mDataBinding.wrvRecycler.setOnPullListener(new WrappedRecyclerView.OnPullListener() {
            @Override
            public void onRefresh(RecyclerView view) {
                findCategory(true);
            }

            @Override
            public void onLoadMore(RecyclerView view) {
                findCategory(false);
            }
        });

        initDrop();
        resultBeanList = new ArrayList<>();
        mDataBinding.llOhter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataBinding.llXiala.setVisibility(View.GONE);
                closeDrap();
            }
        });
        mDataBinding.tvZonghe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDataBinding.llXiala.getVisibility() == View.GONE) {
                    mDataBinding.llXiala.setVisibility(View.VISIBLE);
                    mDataBinding.llZonghe.setVisibility(View.VISIBLE);
                } else {
                    if (mDataBinding.llZonghe.getVisibility() == View.VISIBLE) {
                        mDataBinding.llXiala.setVisibility(View.GONE);
                        closeDrap();
                    } else {
                        closeDrap();
                        mDataBinding.llZonghe.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        mDataBinding.tvQuanbu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDataBinding.llXiala.getVisibility() == View.GONE) {
                    mDataBinding.llXiala.setVisibility(View.VISIBLE);
                    mDataBinding.llQuanbu.setVisibility(View.VISIBLE);
                } else {
                    if (mDataBinding.llQuanbu.getVisibility() == View.VISIBLE) {
                        mDataBinding.llXiala.setVisibility(View.GONE);
                        closeDrap();
                    } else {
                        closeDrap();
                        mDataBinding.llQuanbu.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        mDataBinding.tvPinpai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDataBinding.llXiala.getVisibility() == View.GONE) {
                    mDataBinding.llXiala.setVisibility(View.VISIBLE);
                    mDataBinding.llPinpai.setVisibility(View.VISIBLE);
                } else {
                    if (mDataBinding.llPinpai.getVisibility() == View.VISIBLE) {
                        mDataBinding.llXiala.setVisibility(View.GONE);
                        closeDrap();
                    } else {
                        closeDrap();
                        mDataBinding.llPinpai.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        mDataBinding.tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        mDataBinding.imSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.start(getContext());
            }
        });
    }

    /**
     * 获取配件列表
     */
    private void findCategory(final boolean refresh) {
        if (getContext() != null) {
            mCurrentPage = refresh ? START_PAGE : ++mCurrentPage;
            Map<String, String> map = new TreeMap<String, String>();
            map.put("keyword", "");
            map.put("category_id", category_id);
            map.put("brand_id", brand_id);
            map.put("sort_type", sort_type);//1是价格降序 2是价格升序
            map.put("page", mCurrentPage + "");
            map.put("limit", mShowCount + "");
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = Sign.getSignKey(getActivity(), map, "findCategory");
            NetBean netBean = new NetBean();
            netBean.setToken("");
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(FindCategoryAction.newInstance(getContext(), netBean)
                    .request(new AHttpService.IResCallback<BaseEntity<FindCategoryBean>>() {
                        @Override
                        public void onCallback(int resultCode, Response<BaseEntity<FindCategoryBean>> response,
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

    private FindCategoryBean findCategoryBean;

    private void doSuccessResponse(boolean refresh, FindCategoryBean categoryBean) {
        this.findCategoryBean = categoryBean;
        if (refresh) {
            mAdapter.setData(categoryBean.getData());
        } else {
            mAdapter.append(categoryBean.getData());
        }
        mDataBinding.wrvRecycler.getAdapter().notifyDataSetChanged();
        mDataBinding.wrvRecycler.loadComplete(mAdapter.getItemCount() == 0, ((float) findCategoryBean.getPage().getTotal() / (float) findCategoryBean.getPage().getPageSize()) > mCurrentPage);
    }

    private class InnerAdapter extends AbsRecyclerViewAdapter<FindCategoryBean.DataBean> {
        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<FindCategoryBean.DataBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new PeiJianViewHolder(mInflater.inflate(R.layout.item_mechanics_peijian, parent, false));
        }
    }

    /**
     * 初始化下拉
     */
    private void initDrop() {
        GetComboBoxBean getComboBoxBean = new GetComboBoxBean();
        List<GetComboBoxBean.ResultBean> resultBeans = new ArrayList<>();
        GetComboBoxBean.ResultBean resultBean1 =
                new GetComboBoxBean.ResultBean("1", "价格降序 ");
        GetComboBoxBean.ResultBean resultBean2 =
                new GetComboBoxBean.ResultBean("2", "价格升序 ");
        resultBeans.add(resultBean1);
        resultBeans.add(resultBean2);
        getComboBoxBean.setResult(resultBeans);
        zonghe(getComboBoxBean);

        findBrand();
        findCategoryHomePage();
    }

    /**
     * 获取品牌列表 findBrand
     */
    private void findBrand() {
        if (getContext() != null) {
            Map<String, String> map = new TreeMap<String, String>();
            map.put("brand_type", "2");
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = Sign.getSignKey(getActivity(), map, "findBrand");
            NetBean netBean = new NetBean();
            netBean.setToken("");
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(FindBrandAction.newInstance(getContext(), netBean)
                    .request(new AHttpService.IResCallback<BaseEntity<FindBrandBean>>() {
                        @Override
                        public void onCallback(int resultCode, Response<BaseEntity<FindBrandBean>> response,
                                               BaseErrorInfo baseErrorInfo) {
                            if (getView() != null) {
                                closeDialog();
                                if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                    return;
                                }
                                if (response != null) {
                                    new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                    if (response.body().getCode().equals("200")) {
                                        pinpai(response.body().getData());
                                    } else {
                                        ToastUtils.show(getContext(), response.body().getMessage());
                                    }
                                }
                            }
                        }
                    }));
        }
    }

    /**
     * 获取配件类型（首页）
     */
    private void findCategoryHomePage() {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("app_version", ExampleUtil.GetVersion(getContext()));
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getActivity(), map, "findCategoryHomePage");
        NetBean netBean = new NetBean();
        netBean.setToken("");
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(FindCategoryHomePageAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<FindCategoryHomePageBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<FindCategoryHomePageBean>> response, BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                if (response.body().getCode().equals("200")) {
                                    FindCategoryHomePageBean findCategoryHomePageBean = response.body().getData();
                                    if (findCategoryHomePageBean != null) {
                                        GetComboBoxBean getComboBoxBean = new GetComboBoxBean();
                                        List<GetComboBoxBean.ResultBean> resultBeans = new ArrayList<>();
                                        for (FindCategoryHomePageBean.ResultBean resultBean : findCategoryHomePageBean.getResult()) {
                                            GetComboBoxBean.ResultBean resultBean1 =
                                                    new GetComboBoxBean.ResultBean(resultBean.getId(), resultBean.getName());
                                            resultBeans.add(resultBean1);
                                        }
                                        getComboBoxBean.setResult(resultBeans);
                                        quanbu(getComboBoxBean);
                                    }
                                } else {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
    }

    /**
     * 下拉综合
     */
    private void zonghe(final GetComboBoxBean getComboBoxBean) {
        GetComboBoxBean.ResultBean resultBean = new GetComboBoxBean.ResultBean("", "全部");
        if (getComboBoxBean.getResult() != null && getComboBoxBean.getResult().size() > 0) {
            getComboBoxBean.getResult().add(0, resultBean);
        }
        mDataBinding.rlZonghe.setLayoutManager(new LinearLayoutManager(getContext()));
        JXDropAdapter JXAdapter = new JXDropAdapter(getComboBoxBean.getResult(), getContext());
        mDataBinding.rlZonghe.setAdapter(JXAdapter);
        JXAdapter.setMyListener(new JXDropAdapter.ItemListener() {
            @Override
            public void onItemClick(GetComboBoxBean.ResultBean item) {
                sort_type = item.getKey_name();
                mDataBinding.tvZonghe.setText(item.getKey_value());
                mDataBinding.llXiala.setVisibility(View.GONE);
                closeDrap();
                mDataBinding.wrvRecycler.autoRefresh();
            }
        });
    }

    /**
     * 下拉全部
     */
    private void quanbu(final GetComboBoxBean getComboBoxBean) {
        GetComboBoxBean.ResultBean resultBean = new GetComboBoxBean.ResultBean("", "全部");
        if (getComboBoxBean.getResult() != null && getComboBoxBean.getResult().size() > 0) {
            getComboBoxBean.getResult().add(0, resultBean);
        }
        mDataBinding.rlQuanbu.setLayoutManager(new LinearLayoutManager(getContext()));
        JXDropAdapter JXAdapter = new JXDropAdapter(getComboBoxBean.getResult(), getContext());
        mDataBinding.rlQuanbu.setAdapter(JXAdapter);
        JXAdapter.setMyListener(new JXDropAdapter.ItemListener() {
            @Override
            public void onItemClick(GetComboBoxBean.ResultBean item) {
                category_id = item.getKey_name();
                mDataBinding.tvQuanbu.setText(item.getKey_value());
                mDataBinding.llXiala.setVisibility(View.GONE);
                closeDrap();
                mDataBinding.wrvRecycler.autoRefresh();
            }
        });
    }

    /**
     * 下拉品牌
     */
    private void pinpai(FindBrandBean findBrandBean) {
        FindBrandBean.ResultBean resultBean = new FindBrandBean.ResultBean("全部", "");
        if (findBrandBean.getResult() != null && findBrandBean.getResult().size() > 0) {
            findBrandBean.getResult().add(0, resultBean);
        }
        mDataBinding.rlPinpai.setLayoutManager(new GridLayoutManager(getContext(), 4));
        BrandAdapter bAdapter = new BrandAdapter(findBrandBean.getResult(), getContext());
        mDataBinding.rlPinpai.setAdapter(bAdapter);
        bAdapter.setMyListener(new BrandAdapter.ItemListener() {
            @Override
            public void onItemClick(FindBrandBean.ResultBean item) {
                brand_id = item.getId();
                mDataBinding.tvPinpai.setText(item.getName());
                mDataBinding.llXiala.setVisibility(View.GONE);
                closeDrap();
                mDataBinding.wrvRecycler.autoRefresh();
            }
        });
    }

    private void closeDrap() {
        mDataBinding.llZonghe.setVisibility(View.GONE);
        mDataBinding.llQuanbu.setVisibility(View.GONE);
        mDataBinding.llPinpai.setVisibility(View.GONE);
    }
}
