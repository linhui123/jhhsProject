package com.jhhscm.platform.fragment.labour;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.PushQiuZhiActivity;
import com.jhhscm.platform.activity.PushZhaoPinActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.FragmentLabourBinding;
import com.jhhscm.platform.event.GetRegionEvent;
import com.jhhscm.platform.fragment.Mechanics.action.GetComboBoxAction;
import com.jhhscm.platform.fragment.Mechanics.action.GetRegionAction;
import com.jhhscm.platform.fragment.Mechanics.adapter.JXDropAdapter;
import com.jhhscm.platform.fragment.Mechanics.adapter.SelectedAdapter;
import com.jhhscm.platform.fragment.Mechanics.bean.FindBrandBean;
import com.jhhscm.platform.fragment.Mechanics.bean.GetComboBoxBean;
import com.jhhscm.platform.fragment.Mechanics.bean.GetOldPageListBean;
import com.jhhscm.platform.fragment.Mechanics.bean.GetRegionBean;
import com.jhhscm.platform.fragment.Mechanics.holder.GetRegionViewHolder;
import com.jhhscm.platform.fragment.Mechanics.holder.OldMechanicsViewHolder;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.http.sign.SignObject;
import com.jhhscm.platform.jpush.ExampleUtil;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.DisplayUtils;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.recyclerview.DividerItemStrokeDecoration;
import com.jhhscm.platform.views.recyclerview.WrappedRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;


public class LabourFragment extends AbsFragment<FragmentLabourBinding> implements SeekBar.OnSeekBarChangeListener {
    private InnerAdapter mAdapter;
    private SelectedAdapter selectedAdapter;
    private List<GetComboBoxBean.ResultBean> resultBeanList;
    private int type = 0;//0招聘，1求职
    private int mShowCount = 10;
    private int mCurrentPage = 1;
    private final int START_PAGE = mCurrentPage;

    private String province = "";
    private String city = "";
    private String job = "";
    private String salay_money = "";
    private String work_time = "";

    public static LabourFragment instance() {
        LabourFragment view = new LabourFragment();
        return view;
    }

    @Override
    protected FragmentLabourBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentLabourBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        EventBusUtil.registerEvent(this);

        RelativeLayout.LayoutParams llParams = (RelativeLayout.LayoutParams) mDataBinding.rlTop.getLayoutParams();
        llParams.topMargin += DisplayUtils.getStatusBarHeight(getContext());
        mDataBinding.rlTop.setLayoutParams(llParams);

        mDataBinding.rv.addItemDecoration(new DividerItemStrokeDecoration(getContext()));
        mDataBinding.rv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new InnerAdapter(getContext());
        mDataBinding.rv.setAdapter(mAdapter);
        mDataBinding.rv.autoRefresh();
        mDataBinding.rv.setOnPullListener(new WrappedRecyclerView.OnPullListener() {
            @Override
            public void onRefresh(RecyclerView view) {
                if (type == 0) {
                    findLabourReleaseList(true);
                } else {
                    findLabourWorkList(true);
                }

            }

            @Override
            public void onLoadMore(RecyclerView view) {
                if (type == 0) {
                    findLabourReleaseList(false);
                } else {
                    findLabourWorkList(false);
                }
            }
        });
        resultBeanList = new ArrayList<>();
        initDrop();
        initPrivince();
        getRegion("1", "");

        initTop();

        mDataBinding.tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 0) {
                    PushZhaoPinActivity.start(getContext(), "", "", 0);
                } else {
                    PushQiuZhiActivity.start(getContext(), "", "", 0);
                }
            }
        });
    }

    private void initTop() {
        mDataBinding.tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        mDataBinding.llOhter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataBinding.llXiala.setVisibility(View.GONE);
                closeDrap();
            }
        });

        mDataBinding.tvLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  mDataBinding.rlPrivince.bringToFront();
                if (mDataBinding.llXiala.getVisibility() == View.GONE) {
                    mDataBinding.llXiala.setVisibility(View.VISIBLE);
                    mDataBinding.llArea.setVisibility(View.VISIBLE);
                } else {
                    if (mDataBinding.llArea.getVisibility() == View.GONE) {
                        closeDrap();
                        mDataBinding.llArea.setVisibility(View.VISIBLE);
                    } else if (mDataBinding.llArea.getVisibility() == View.VISIBLE) {
                        mDataBinding.llXiala.setVisibility(View.GONE);
                        closeDrap();
                    }
                }
            }
        });

        mDataBinding.tvGangwei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDataBinding.llXiala.getVisibility() == View.GONE) {
                    mDataBinding.llXiala.setVisibility(View.VISIBLE);
                    mDataBinding.llGangwei.setVisibility(View.VISIBLE);
                } else {
                    if (mDataBinding.llGangwei.getVisibility() == View.VISIBLE) {
                        mDataBinding.llXiala.setVisibility(View.GONE);
                        closeDrap();
                    } else {
                        closeDrap();
                        mDataBinding.llGangwei.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        mDataBinding.tvXinzi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDataBinding.llXiala.getVisibility() == View.GONE) {
                    mDataBinding.llXiala.setVisibility(View.VISIBLE);
                    mDataBinding.llXinzi.setVisibility(View.VISIBLE);
                } else {
                    if (mDataBinding.llXinzi.getVisibility() == View.VISIBLE) {
                        mDataBinding.llXiala.setVisibility(View.GONE);
                        closeDrap();
                    } else {
                        closeDrap();
                        mDataBinding.llXinzi.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        mDataBinding.tvJingyan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDataBinding.llXiala.getVisibility() == View.GONE) {
                    mDataBinding.llXiala.setVisibility(View.VISIBLE);
                    mDataBinding.llJingyan.setVisibility(View.VISIBLE);
                } else {
                    if (mDataBinding.llJingyan.getVisibility() == View.VISIBLE) {
                        mDataBinding.llXiala.setVisibility(View.GONE);
                        closeDrap();
                    } else {
                        closeDrap();
                        mDataBinding.llJingyan.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

//        mDataBinding.tv3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDataBinding.seekbar.setVisibility(View.GONE);
//                mDataBinding.tvTotal.setVisibility(View.GONE);
//                mDataBinding.tvStart.setVisibility(View.GONE);
//                mDataBinding.tvEnd.setVisibility(View.GONE);
//            }
//        });
//        mDataBinding.tv2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDataBinding.seekbar.setVisibility(View.VISIBLE);
//                mDataBinding.tvTotal.setVisibility(View.VISIBLE);
//                mDataBinding.tvStart.setVisibility(View.VISIBLE);
//                mDataBinding.tvEnd.setVisibility(View.VISIBLE);
//            }
//        });
//        mDataBinding.tv1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDataBinding.seekbar.setVisibility(View.VISIBLE);
//                mDataBinding.tvTotal.setVisibility(View.VISIBLE);
//                mDataBinding.tvStart.setVisibility(View.VISIBLE);
//                mDataBinding.tvEnd.setVisibility(View.VISIBLE);
//            }
//        });

//        mDataBinding.seekbar.setOnSeekBarChangeListener(this);

        mDataBinding.tvZhaopin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 0;
                mDataBinding.tel.setImageResource(R.mipmap.ic_push_zhaopin);
                mDataBinding.rv.autoRefresh();
                mDataBinding.tvZhaopin.setBackgroundResource(R.color.white);
                mDataBinding.tvZhaopin.setTextColor(getResources().getColor(R.color.a397));
                mDataBinding.tvQiuzhi.setTextColor(getResources().getColor(R.color.white));
                mDataBinding.tvQiuzhi.setBackgroundResource(R.drawable.bg_397_right_ovel);
            }
        });

        mDataBinding.tvQiuzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 1;
                mDataBinding.tel.setImageResource(R.mipmap.ic_push_qiuzhi);
                mDataBinding.rv.autoRefresh();
                mDataBinding.tvQiuzhi.setBackgroundResource(R.color.white);
                mDataBinding.tvQiuzhi.setTextColor(getResources().getColor(R.color.a397));
                mDataBinding.tvZhaopin.setBackgroundResource(R.drawable.bg_397_left_ovel);
                mDataBinding.tvZhaopin.setTextColor(getResources().getColor(R.color.white));
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }

    /**
     * 更新地区选择
     */
    public void onEvent(GetRegionEvent event) {
        if (event.pid != null && event.type != null) {
            if (event.type.equals("1")) {//省点击，获取市
                province = event.pid;
                getRegion("2", event.pid);
            } else if (event.type.equals("2")) {//市点击
                city = event.pid;
                mDataBinding.tvLocation.setText(event.name);
                mDataBinding.llArea.setVisibility(View.GONE);
                mDataBinding.llXiala.setVisibility(View.GONE);
                mDataBinding.rv.autoRefresh();
            } else if (event.type.equals("0")) {//市点击
                city = "";
                province = "";
                mDataBinding.tvLocation.setText("全国");
                mDataBinding.llArea.setVisibility(View.GONE);
                mDataBinding.llXiala.setVisibility(View.GONE);
                mDataBinding.rv.autoRefresh();
            }
        }
    }

    private void initData(int Type) {
        List<FindLabourReleaseListBean.DataBean> dataBeans = new ArrayList<>();
        FindLabourReleaseListBean.DataBean dataBean = new FindLabourReleaseListBean.DataBean();
        dataBeans.add(dataBean);
        dataBeans.add(dataBean);
        dataBeans.add(dataBean);
        dataBeans.add(dataBean);
        mAdapter.setData(dataBeans);
    }

    //拖动进度
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    //拖动停止
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        seekBar.getProgress();
        String total = seekBar.getProgress() * 100 + "";
//        mDataBinding.tvTotal.setText("0-" + total + "元");
    }

    private void closeDrap() {
//        mDataBinding.llLocation.setVisibility(View.GONE);
        mDataBinding.llGangwei.setVisibility(View.GONE);
        mDataBinding.llJingyan.setVisibility(View.GONE);
        mDataBinding.llXinzi.setVisibility(View.GONE);
        mDataBinding.llArea.setVisibility(View.GONE);
    }

    /**
     * 查询劳务招聘列表
     */
    private void findLabourReleaseList(final boolean refresh) {
        if (getContext() != null) {
            mCurrentPage = refresh ? START_PAGE : ++mCurrentPage;
            Map<String, Object> map = new TreeMap<String, Object>();
            map.put("province", province);
            map.put("city", city);
            map.put("job", job);
            map.put("salay_money", salay_money);
            map.put("work_time", work_time);
            map.put("page", mCurrentPage);
            map.put("limit", mShowCount);
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = SignObject.getSignKey(getActivity(), map, "findLabourReleaseList");
            NetBean netBean = new NetBean();
            netBean.setToken("");
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(FindLabourReleaseListAction.newInstance(getContext(), netBean)
                    .request(new AHttpService.IResCallback<BaseEntity<FindLabourReleaseListBean>>() {
                        @Override
                        public void onCallback(int resultCode, Response<BaseEntity<FindLabourReleaseListBean>> response,
                                               BaseErrorInfo baseErrorInfo) {
                            if (getView() != null) {
                                closeDialog();
                                if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                    return;
                                }
                                if (response != null) {
                                    new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                    if (response.body().getCode().equals("200")) {
                                        FindLabourReleaseListBean findLabourReleaseListBean = response.body().getData();
                                        for (FindLabourReleaseListBean.DataBean dataBean : findLabourReleaseListBean.getData()) {
                                            dataBean.setType("0");
                                        }
                                        doSuccessResponse(refresh, findLabourReleaseListBean);
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
     * 查询劳务求职列表
     */
    private void findLabourWorkList(final boolean refresh) {
        if (getContext() != null) {
            mCurrentPage = refresh ? START_PAGE : ++mCurrentPage;
            Map<String, Object> map = new TreeMap<String, Object>();
            map.put("province", province);
            map.put("city", city);
            map.put("job", job);
            map.put("salay_money", salay_money);
            map.put("work_time", work_time);
            map.put("page", mCurrentPage);
            map.put("limit", mShowCount);
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = SignObject.getSignKey(getActivity(), map, "findLabourWorkList");
            NetBean netBean = new NetBean();
            netBean.setToken("");
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(FindLabourWorkListAction.newInstance(getContext(), netBean)
                    .request(new AHttpService.IResCallback<BaseEntity<FindLabourReleaseListBean>>() {
                        @Override
                        public void onCallback(int resultCode, Response<BaseEntity<FindLabourReleaseListBean>> response,
                                               BaseErrorInfo baseErrorInfo) {
                            if (getView() != null) {
                                closeDialog();
                                if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                    return;
                                }
                                if (response != null) {
                                    new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                    if (response.body().getCode().equals("200")) {
                                        FindLabourReleaseListBean findLabourReleaseListBean = response.body().getData();
                                        for (FindLabourReleaseListBean.DataBean dataBean : findLabourReleaseListBean.getData()) {
                                            dataBean.setType("1");
                                        }
                                        doSuccessResponse(refresh, findLabourReleaseListBean);
                                    } else {
                                        ToastUtils.show(getContext(), response.body().getMessage());
                                    }
                                }
                            }
                        }
                    }));
        }
    }

    FindLabourReleaseListBean findCategoryBean;

    private void doSuccessResponse(boolean refresh, FindLabourReleaseListBean categoryBean) {
        this.findCategoryBean = categoryBean;
        if (refresh) {
//            mAdapter.clear();
            mAdapter.setData(categoryBean.getData());
        } else {
            mAdapter.append(categoryBean.getData());
        }
        mDataBinding.rv.loadComplete(mAdapter.getItemCount() == 0, ((float) findCategoryBean.getPage().getTotal() / (float) findCategoryBean.getPage().getPageSize()) > mCurrentPage);
    }

    private class InnerAdapter extends AbsRecyclerViewAdapter<FindLabourReleaseListBean.DataBean> {
        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<FindLabourReleaseListBean.DataBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new LabourViewHolder(mInflater.inflate(R.layout.item_labour, parent, false));
        }
    }


    /**
     * 获取下拉框
     */
    private void getComboBox(final String name) {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("key_group_name", name);
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getActivity(), map, "getComboBox:" + name);
        NetBean netBean = new NetBean();
        netBean.setToken("");
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(GetComboBoxAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<GetComboBoxBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<GetComboBoxBean>> response, BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                if (response.body().getCode().equals("200")) {
                                    if ("job".equals(name)) {
                                        gangwei(response.body().getData());
                                    } else if ("work_time".equals(name)) {
                                        jinyan(response.body().getData());
                                    } else if ("salay_money".equals(name)) {
                                        xinzi(response.body().getData());
                                    }
                                } else {
                                    ToastUtils.show(getContext(), "error " + name + ":" + response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
    }

    /**
     * 初始化下拉
     */
    private void initDrop() {
        /**
         * job	   		工作岗位
         * work_tim		经验
         * salay_money  薪资
         */
        getComboBox("work_time");
        getComboBox("job");
        getComboBox("salay_money");
    }

    /**
     * 下拉机型
     */
    private void gangwei(GetComboBoxBean getComboBoxBean) {
        GetComboBoxBean.ResultBean resultBean = new GetComboBoxBean.ResultBean("", "全部");
        if (getComboBoxBean.getResult() != null && getComboBoxBean.getResult().size() > 0) {
            getComboBoxBean.getResult().add(0, resultBean);
        }
        mDataBinding.rlGangwei.setLayoutManager(new LinearLayoutManager(getContext()));
        JXDropAdapter JXAdapter = new JXDropAdapter(getComboBoxBean.getResult(), getContext());
        mDataBinding.rlGangwei.setAdapter(JXAdapter);
        JXAdapter.setMyListener(new JXDropAdapter.ItemListener() {
            @Override
            public void onItemClick(GetComboBoxBean.ResultBean item) {
                job = item.getKey_name();
                mDataBinding.tvGangwei.setText(item.getKey_value());
                mDataBinding.llXiala.setVisibility(View.GONE);
                closeDrap();
                mDataBinding.rv.autoRefresh();
            }
        });
    }

    /**
     * 下拉排序
     */
    private void jinyan(GetComboBoxBean getComboBoxBean) {
        GetComboBoxBean.ResultBean resultBean = new GetComboBoxBean.ResultBean("", "全部");
        if (getComboBoxBean.getResult() != null && getComboBoxBean.getResult().size() > 0) {
            getComboBoxBean.getResult().add(0, resultBean);
        }
        mDataBinding.rlJingyan.setLayoutManager(new LinearLayoutManager(getContext()));
        JXDropAdapter JXAdapter = new JXDropAdapter(getComboBoxBean.getResult(), getContext());
        mDataBinding.rlJingyan.setAdapter(JXAdapter);
        JXAdapter.setMyListener(new JXDropAdapter.ItemListener() {
            @Override
            public void onItemClick(GetComboBoxBean.ResultBean item) {
                work_time = item.getKey_name();
                mDataBinding.tvJingyan.setText(item.getKey_value());
                mDataBinding.llXiala.setVisibility(View.GONE);
                closeDrap();
                mDataBinding.rv.autoRefresh();
            }
        });
    }

    /**
     * 下拉薪资
     */
    private void xinzi(GetComboBoxBean getComboBoxBean) {
        GetComboBoxBean.ResultBean resultBean = new GetComboBoxBean.ResultBean("", "全部");
        if (getComboBoxBean.getResult() != null && getComboBoxBean.getResult().size() > 0) {
            getComboBoxBean.getResult().add(0, resultBean);
        }
        mDataBinding.rlXinzi.setLayoutManager(new LinearLayoutManager(getContext()));
        JXDropAdapter JXAdapter = new JXDropAdapter(getComboBoxBean.getResult(), getContext());
        mDataBinding.rlXinzi.setAdapter(JXAdapter);
        JXAdapter.setMyListener(new JXDropAdapter.ItemListener() {
            @Override
            public void onItemClick(GetComboBoxBean.ResultBean item) {
                salay_money = item.getKey_name();
                mDataBinding.tvXinzi.setText(item.getKey_value());
                mDataBinding.llXiala.setVisibility(View.GONE);
                closeDrap();
                mDataBinding.rv.autoRefresh();
            }
        });
    }

    /**
     * 获取行政区域列表
     */
    private void getRegion(final String type, final String pid) {
        if (getContext() != null) {
            Map<String, String> map = new TreeMap<String, String>();
            map.put("type", type);
            map.put("pid", pid);
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = Sign.getSignKey(getActivity(), map, "getRegion");
            NetBean netBean = new NetBean();
            netBean.setToken("");
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(GetRegionAction.newInstance(getContext(), netBean)
                    .request(new AHttpService.IResCallback<BaseEntity<GetRegionBean>>() {
                        @Override
                        public void onCallback(int resultCode, Response<BaseEntity<GetRegionBean>> response,
                                               BaseErrorInfo baseErrorInfo) {
                            if (getView() != null) {
                                closeDialog();
                                if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                    return;
                                }
                                if (response != null) {
                                    new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                    if (response.body().getCode().equals("200")) {
                                        GetRegionBean getRegionBean = response.body().getData();
                                        if (getRegionBean.getResult() != null) {
                                            if (type.equals("1")) {//初次加载
                                                pRegionBean = getRegionBean;
                                                GetRegionBean.ResultBean resultBean = new GetRegionBean.ResultBean();
                                                resultBean.setName("全部");
                                                resultBean.setId(0);
                                                resultBean.setType(0);
                                                pRegionBean.getResult().add(0, resultBean);
                                                province = "";
//                                                province = getRegionBean.getResult().get(0).getId() + "";
                                                pAdapter.setData(pRegionBean.getResult());
                                                getRegion("2", getRegionBean.getResult().get(0).getId() + "");
                                            } else {
                                                if (getRegionBean.getResult().size() > 0) {
                                                    cRegionBean = getRegionBean;
                                                    city = getRegionBean.getResult().get(0).getId() + "";
                                                    cAdapter.setData(cRegionBean.getResult());
                                                } else {
//                                                    ToastUtils.show(getContext(), "无数据");
                                                }
                                            }
//                                            Log.e("pAdapter", "  pAdapter.getItemCount() " + pAdapter.getItemCount());
//                                            Log.e("cAdapter", "  cAdapter.getItemCount() " + cAdapter.getItemCount());

                                        }
                                    } else {
                                        ToastUtils.show(getContext(), response.body().getMessage());
                                    }
                                }
                            }
                        }
                    }));
        }
    }

    PrinviceAdapter pAdapter;
    CityAdapter cAdapter;

    GetRegionBean pRegionBean;
    GetRegionBean cRegionBean;

    private void initPrivince() {
        mDataBinding.rlPrivince.setLayoutManager(new LinearLayoutManager(getContext()));
        pAdapter = new PrinviceAdapter(getContext());
        mDataBinding.rlPrivince.setAdapter(pAdapter);

        mDataBinding.rlCity.setLayoutManager(new LinearLayoutManager(getContext()));
        cAdapter = new CityAdapter(getContext());
        mDataBinding.rlCity.setAdapter(cAdapter);
    }


    private class PrinviceAdapter extends AbsRecyclerViewAdapter<GetRegionBean.ResultBean> {
        public PrinviceAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<GetRegionBean.ResultBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new GetRegionViewHolder(mInflater.inflate(R.layout.item_mechanics_privince, parent, false));
        }
    }

    private class CityAdapter extends AbsRecyclerViewAdapter<GetRegionBean.ResultBean> {
        public CityAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<GetRegionBean.ResultBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new GetRegionViewHolder(mInflater.inflate(R.layout.item_mechanics_privince, parent, false));
        }
    }
}
