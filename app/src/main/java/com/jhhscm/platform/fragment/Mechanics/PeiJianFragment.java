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
import com.jhhscm.platform.BuildConfig;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.BrandModelActivity;
import com.jhhscm.platform.activity.SearchActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.FragmentPeiJianBinding;
import com.jhhscm.platform.event.BrandResultEvent;
import com.jhhscm.platform.event.JumpEvent;
import com.jhhscm.platform.event.ShowBackEvent;
import com.jhhscm.platform.fragment.Mechanics.action.BrandModelListAction;
import com.jhhscm.platform.fragment.Mechanics.action.FindBrandAction;
import com.jhhscm.platform.fragment.Mechanics.action.FindCategoryAction;
import com.jhhscm.platform.fragment.Mechanics.adapter.BrandAdapter;
import com.jhhscm.platform.fragment.Mechanics.adapter.BrandModelAdapter;
import com.jhhscm.platform.fragment.Mechanics.adapter.JXDropAdapter;
import com.jhhscm.platform.fragment.Mechanics.adapter.SelectedAdapter;
import com.jhhscm.platform.fragment.Mechanics.bean.BrandModelBean;
import com.jhhscm.platform.fragment.Mechanics.bean.FindBrandBean;
import com.jhhscm.platform.fragment.Mechanics.bean.FindCategoryBean;
import com.jhhscm.platform.fragment.Mechanics.bean.GetComboBoxBean;
import com.jhhscm.platform.fragment.Mechanics.holder.PeiJian2ViewHolder;
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
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtil;
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
    private Inner2Adapter mAdapter2;
    private SelectedAdapter selectedAdapter;
    private List<GetComboBoxBean.ResultBean> resultBeanList;
    private int mShowCount = 10;
    private int mCurrentPage = 1;
    private final int START_PAGE = mCurrentPage;

    private boolean isShowBack;
    private boolean showType = false;//false 单列；ture 双列
    private String sort_type = "";//排序
    private String category_id = "";//类型
    private String brand_id = "";//品牌
    private String model_ids = "";//机型

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
        EventBusUtil.registerEvent(this);
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

        mDataBinding.rv2.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mAdapter2 = new Inner2Adapter(getContext());
        mDataBinding.rv2.setAdapter(mAdapter2);
        mDataBinding.rv2.autoRefresh();
        mDataBinding.rv2.setOnPullListener(new WrappedRecyclerView.OnPullListener() {
            @Override
            public void onRefresh(RecyclerView view) {
                findCategory(true);
            }

            @Override
            public void onLoadMore(RecyclerView view) {
                findCategory(false);
            }
        });
        resultBeanList = new ArrayList<>();
        initDrop();


        mDataBinding.tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBusUtil.post(new JumpEvent("HOME_PAGE", null));
//                getActivity().finish();
            }
        });

        mDataBinding.imSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.start(getContext(), 2);
            }
        });

        mDataBinding.showType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showType) {
                    showType = false;
                    mDataBinding.rv2.setVisibility(View.GONE);
                    mDataBinding.wrvRecycler.setVisibility(View.VISIBLE);
                    mDataBinding.showType.setImageResource(R.mipmap.ic_peijian_list1);
                } else {
                    showType = true;
                    mDataBinding.rv2.setVisibility(View.VISIBLE);
                    mDataBinding.wrvRecycler.setVisibility(View.GONE);
                    mDataBinding.showType.setImageResource(R.mipmap.ic_peijian_list2);
                }
            }
        });
    }

    /**
     * 初始化下拉
     */
    private void initDrop() {
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
                findBrand();//品牌
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

        mDataBinding.tvJixing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDataBinding.llXiala.getVisibility() == View.GONE) {
                    mDataBinding.llXiala.setVisibility(View.VISIBLE);
                    mDataBinding.llJixing.setVisibility(View.VISIBLE);
                } else {
                    if (mDataBinding.llJixing.getVisibility() == View.VISIBLE) {
                        mDataBinding.llXiala.setVisibility(View.GONE);
                        closeDrap();
                    } else {
                        closeDrap();
                        mDataBinding.llJixing.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
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
        findBrand();//品牌
        findCategoryHomePage();//类型
        brandModel();//机型
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
            map.put("model_ids", model_ids);
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
                                    } else if (!BuildConfig.DEBUG && response.body().getCode().equals("1006")) {
                                        ToastUtils.show(getContext(), "网络错误");
                                        mDataBinding.wrvRecycler.loadComplete(true, false);
                                    } else {
                                        ToastUtils.show(getContext(), response.body().getMessage());
                                        mDataBinding.wrvRecycler.loadComplete(true, false);
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
            mAdapter2.setData(categoryBean.getData());
        } else {
            mAdapter.append(categoryBean.getData());
            mAdapter2.append(categoryBean.getData());
        }
        mDataBinding.rv2.loadComplete(mAdapter.getItemCount() == 0, ((float) findCategoryBean.getPage().getTotal() / (float) findCategoryBean.getPage().getPageSize()) > mCurrentPage);
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

    private class Inner2Adapter extends AbsRecyclerViewAdapter<FindCategoryBean.DataBean> {
        public Inner2Adapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<FindCategoryBean.DataBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new PeiJian2ViewHolder(mInflater.inflate(R.layout.item_mechanics_peijian2, parent, false));
        }
    }


    /**
     * 获取品牌列表 findBrand
     */
    private void findBrand() {
        if (getContext() != null) {
            Map<String, String> map = new TreeMap<String, String>();
            map.put("brand_type", "2");
            map.put("category_id", category_id);
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

    /**
     * 获取品牌 + 机型列表
     */
    private void brandModel() {
        if (getContext() != null) {
            Map<String, String> map = new TreeMap<String, String>();
            map.put("type", "2");
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = Sign.getSignKey(getActivity(), map, "brandModel");
            NetBean netBean = new NetBean();
            netBean.setToken("");
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(BrandModelListAction.newInstance(getContext(), netBean)
                    .request(new AHttpService.IResCallback<BaseEntity<BrandModelBean>>() {
                        @Override
                        public void onCallback(int resultCode, Response<BaseEntity<BrandModelBean>> response,
                                               BaseErrorInfo baseErrorInfo) {
                            if (getView() != null) {
                                closeDialog();
                                if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                    return;
                                }
                                if (response != null) {
                                    new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                    if (response.body().getCode().equals("200")) {
                                        jixing(response.body().getData());
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
     * 下拉全部 品类
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
                brand_id = "";
                mDataBinding.tvPinpai.setText("品牌");
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

    /**
     * 下拉机型
     */
    private void jixing(BrandModelBean brandModelBean) {
        BrandModelBean.DataBean resultBean = new BrandModelBean.DataBean("全部", "");
        BrandModelBean.DataBean resultBean1 = new BrandModelBean.DataBean("机型通用", "-1");
        if (brandModelBean.getData() != null && brandModelBean.getData().size() > 0) {
            brandModelBean.getData().add(0, resultBean);
            brandModelBean.getData().add(1, resultBean1);
        }
        mDataBinding.rlJixing.setLayoutManager(new GridLayoutManager(getContext(), 4));
        BrandModelAdapter bAdapter = new BrandModelAdapter(brandModelBean.getData(), getContext());
        mDataBinding.rlJixing.setAdapter(bAdapter);
        bAdapter.setMyListener(new BrandModelAdapter.ItemListener() {
            @Override
            public void onItemClick(BrandModelBean.DataBean item) {
                if (item.getBrand_id().length() > 0) {
                    if (item.getBrand_id().equals("-1")) {
                        model_ids = "-1";
                        mDataBinding.tvJixing.setText("机型通用");
                        mDataBinding.llXiala.setVisibility(View.GONE);
                        closeDrap();
                        mDataBinding.wrvRecycler.autoRefresh();
                    } else {
                        if (item.getBrand_model_list() != null && item.getBrand_model_list().size() > 0) {
                            BrandModelActivity.start(getContext(), item);
                        } else {
                            model_ids = "";
                            ToastUtil.show(getContext(), "该品牌下没有机型数据");
                            mDataBinding.llXiala.setVisibility(View.GONE);
                            closeDrap();
                            mDataBinding.wrvRecycler.autoRefresh();
                        }
                    }
                } else {
                    model_ids = "";
                    mDataBinding.tvJixing.setText("全部");
                    mDataBinding.llXiala.setVisibility(View.GONE);
                    closeDrap();
                    mDataBinding.wrvRecycler.autoRefresh();
                }
            }
        });
    }

    private void closeDrap() {
        mDataBinding.llZonghe.setVisibility(View.GONE);
        mDataBinding.llQuanbu.setVisibility(View.GONE);
        mDataBinding.llPinpai.setVisibility(View.GONE);
        mDataBinding.llJixing.setVisibility(View.GONE);
    }

    public void onEvent(BrandResultEvent event) {
        if (event.getBrand_id() != null && event.getBrand_name() != null) {//品牌
            if (event.getType() == 1) {
                model_ids = event.getBrand_id();
                mDataBinding.tvJixing.setText(event.getBrand_name());
                mDataBinding.llXiala.setVisibility(View.GONE);
                closeDrap();
                mDataBinding.wrvRecycler.autoRefresh();
            } else if (event.getType() == 2) {//品类
                category_id = event.getBrand_id();
                mDataBinding.tvQuanbu.setText(event.getBrand_name());
                brand_id = "";
                mDataBinding.tvPinpai.setText("品牌");
                mDataBinding.llXiala.setVisibility(View.GONE);
                closeDrap();
                mDataBinding.wrvRecycler.autoRefresh();
            }
        }
    }

    public void onEvent(ShowBackEvent event) {
        if (event.getType() == 2) {
            isShowBack = true;
            mDataBinding.tvBack.setVisibility(View.VISIBLE);
        } else if (event.getType() == 0) {
            isShowBack = false;
            mDataBinding.tvBack.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }
}
