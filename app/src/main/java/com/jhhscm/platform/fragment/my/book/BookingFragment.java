package com.jhhscm.platform.fragment.my.book;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.alibaba.fastjson.JSON;
import com.donkingliang.labels.LabelsView;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.AddBookingActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.FragmentBookingBinding;
import com.jhhscm.platform.databinding.FragmentMyMemberBinding;
import com.jhhscm.platform.event.RefreshEvent;
import com.jhhscm.platform.fragment.Mechanics.action.GetComboBoxAction;
import com.jhhscm.platform.fragment.Mechanics.adapter.SXDropAdapter;
import com.jhhscm.platform.fragment.Mechanics.bean.GetComboBoxBean;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.home.action.GetArticleListAction;
import com.jhhscm.platform.fragment.home.bean.GetPageArticleListBean;
import com.jhhscm.platform.fragment.my.store.MyMemberFragment;
import com.jhhscm.platform.fragment.my.store.viewholder.MyMemberItemViewHolder;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.http.sign.SignObject;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.DataUtil;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.recyclerview.DividerItemDecoration;
import com.jhhscm.platform.views.recyclerview.WrappedRecyclerView;
import com.jhhscm.platform.views.timePickets.TimePickerShow;
import com.jhhscm.platform.views.timePickets.TimePicketAlertDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

public class BookingFragment extends AbsFragment<FragmentBookingBinding> {

    private InnerAdapter mAdapter;
    private int mShowCount = 10;
    private int mCurrentPage = 1;
    private final int START_PAGE = mCurrentPage;

    private String incomeType = "";
    private String payType = "";

    public static BookingFragment instance() {
        BookingFragment view = new BookingFragment();
        return view;
    }

    @Override
    protected FragmentBookingBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentBookingBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        EventBusUtil.registerEvent(this);
        mDataBinding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new InnerAdapter(getContext());
        mDataBinding.recyclerview.setAdapter(mAdapter);
        mDataBinding.recyclerview.autoRefresh();
        mDataBinding.recyclerview.setOnPullListener(new WrappedRecyclerView.OnPullListener() {
            @Override
            public void onRefresh(RecyclerView view) {
                allSum();
                allSumByDataTime(true);
            }

            @Override
            public void onLoadMore(RecyclerView view) {
                allSumByDataTime(false);
            }
        });

        initSelect();

        mDataBinding.income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddBookingActivity.start(getContext(), 0);
            }
        });

        mDataBinding.pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddBookingActivity.start(getContext(), 1);
            }
        });

        getComboBox("bus_tool_in");
        getComboBox("bus_tool_out");
    }

    private void initSelect() {
        mDataBinding.startTime.setText(DataUtil.getTimesMonthmorning().trim());
        mDataBinding.endTime.setText(DataUtil.getTimesMonthnight().trim());
        mDataBinding.data.setText(DataUtil.getTimesMonthmorning().trim() + " - " + DataUtil.getTimesMonthnight().trim() + " 明细");
        mDataBinding.startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerShow timePickerShow = new TimePickerShow(getContext());
                timePickerShow.timePickerAlertDialog("");
                timePickerShow.setOnTimePickerListener(new TimePickerShow.OnTimePickerListener() {
                    @Override
                    public void onClicklistener(String dataTime) {
                        String data = DataUtil.getDateStr(DataUtil.getStringToData(dataTime, "yyyy-MM-dd"), "yyyy.MM.dd");
                        mDataBinding.startTime.setText(data);
                    }
                });
            }
        });
        mDataBinding.endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerShow timePickerShow = new TimePickerShow(getContext());
                timePickerShow.timePickerAlertDialog("");
                timePickerShow.setOnTimePickerListener(new TimePickerShow.OnTimePickerListener() {
                    @Override
                    public void onClicklistener(String dataTime) {
                        String data = DataUtil.getDateStr(DataUtil.getStringToData(dataTime, "yyyy-MM-dd"), "yyyy.MM.dd");
                        mDataBinding.endTime.setText(data);
                    }
                });
            }
        });

        mDataBinding.lastM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("lastM", "时间 " + DataUtil.getLastMonthmorning());
                Log.e("lastM", "时间 " + DataUtil.getLastMonthnight());
                mDataBinding.startTime.setText(DataUtil.getLastMonthmorning().trim());
                mDataBinding.endTime.setText(DataUtil.getLastMonthnight().trim());
                mDataBinding.lastM.setTextColor(getResources().getColor(R.color.white));
                mDataBinding.lastM.setBackgroundResource(R.drawable.button_c397);
                mDataBinding.thisM.setTextColor(getResources().getColor(R.color.acc3));
                mDataBinding.thisM.setBackgroundResource(R.drawable.bg_line_de);
                mDataBinding.year.setTextColor(getResources().getColor(R.color.acc3));
                mDataBinding.year.setBackgroundResource(R.drawable.bg_line_de);
            }
        });

        mDataBinding.year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("year", "时间 " + DataUtil.getTimesYearmorning());
                Log.e("year", "时间 " + DataUtil.getTimesYearnight());
                mDataBinding.startTime.setText(DataUtil.getTimesYearmorning().trim());
                mDataBinding.endTime.setText(DataUtil.getTimesYearnight().trim());
                mDataBinding.year.setTextColor(getResources().getColor(R.color.white));
                mDataBinding.year.setBackgroundResource(R.drawable.button_c397);
                mDataBinding.lastM.setTextColor(getResources().getColor(R.color.acc3));
                mDataBinding.lastM.setBackgroundResource(R.drawable.bg_line_de);
                mDataBinding.thisM.setTextColor(getResources().getColor(R.color.acc3));
                mDataBinding.thisM.setBackgroundResource(R.drawable.bg_line_de);
            }
        });

        mDataBinding.thisM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("thisM", "时间 " + DataUtil.getTimesMonthmorning());
                Log.e("thisM", "时间 " + DataUtil.getTimesMonthnight());
                mDataBinding.startTime.setText(DataUtil.getTimesMonthmorning().trim());
                mDataBinding.endTime.setText(DataUtil.getTimesMonthnight().trim());
                mDataBinding.thisM.setTextColor(getResources().getColor(R.color.white));
                mDataBinding.thisM.setBackgroundResource(R.drawable.button_c397);
                mDataBinding.year.setTextColor(getResources().getColor(R.color.acc3));
                mDataBinding.year.setBackgroundResource(R.drawable.bg_line_de);
                mDataBinding.lastM.setTextColor(getResources().getColor(R.color.acc3));
                mDataBinding.lastM.setBackgroundResource(R.drawable.bg_line_de);
            }
        });

        mDataBinding.timeConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataBinding.llXiala.setVisibility(View.GONE);
                mDataBinding.llTime.setVisibility(View.GONE);
                mDataBinding.llType.setVisibility(View.GONE);
                mDataBinding.recyclerview.autoRefresh();
                mDataBinding.data.setText(mDataBinding.startTime.getText().toString() + " - " + mDataBinding.endTime.getText().toString() + " 明细");
                allSum();
            }
        });

        mDataBinding.typeConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataBinding.llXiala.setVisibility(View.GONE);
                mDataBinding.llTime.setVisibility(View.GONE);
                mDataBinding.llType.setVisibility(View.GONE);
                mDataBinding.recyclerview.autoRefresh();
                allSum();
            }
        });

        mDataBinding.tvData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDataBinding.llTime.getVisibility() == View.GONE) {
                    mDataBinding.llXiala.setVisibility(View.VISIBLE);
                    mDataBinding.llTime.setVisibility(View.VISIBLE);
                    mDataBinding.llType.setVisibility(View.GONE);
                } else {
                    mDataBinding.llXiala.setVisibility(View.GONE);
                    mDataBinding.llTime.setVisibility(View.GONE);
                    mDataBinding.llType.setVisibility(View.GONE);
                }
            }
        });

        mDataBinding.tvType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDataBinding.llType.getVisibility() == View.GONE) {
                    mDataBinding.llXiala.setVisibility(View.VISIBLE);
                    mDataBinding.llTime.setVisibility(View.GONE);
                    mDataBinding.llType.setVisibility(View.VISIBLE);
                } else {
                    mDataBinding.llXiala.setVisibility(View.GONE);
                    mDataBinding.llTime.setVisibility(View.GONE);
                    mDataBinding.llType.setVisibility(View.GONE);
                }

            }
        });

        mDataBinding.llOhter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataBinding.llXiala.setVisibility(View.GONE);
                mDataBinding.llTime.setVisibility(View.GONE);
                mDataBinding.llType.setVisibility(View.GONE);
            }
        });
    }

    private void allSum() {
        if (getContext() != null) {
            Map<String, Object> map = new TreeMap<String, Object>();
            map.put("user_code", ConfigUtils.getCurrentUser(getContext()).getUserCode());
            map.put("in_type", incomeType);
            map.put("out_type", payType);
            map.put("startTime", mDataBinding.startTime.getText().toString().trim());
            map.put("endTime", mDataBinding.endTime.getText().toString().trim());
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = SignObject.getSignKey(getActivity(), map, "allSum");
            NetBean netBean = new NetBean();
            netBean.setToken("");
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(AllSumAction.newInstance(getContext(), netBean)
                    .request(new AHttpService.IResCallback<BaseEntity<AllSumBean>>() {
                        @Override
                        public void onCallback(int resultCode, Response<BaseEntity<AllSumBean>> response,
                                               BaseErrorInfo baseErrorInfo) {
                            if (getView() != null) {
                                closeDialog();
                                if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                    return;
                                }
                                if (response != null) {
                                    new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                    if (response.body().getCode().equals("200")) {
                                        AllSumBean allSumBean = response.body().getData();
                                        if (allSumBean != null && allSumBean.getData() != null) {
                                            mDataBinding.tvUn.setText(allSumBean.getData().getPrice_2());
                                            mDataBinding.tvAccepted.setText(allSumBean.getData().getPrice_1());
                                            mDataBinding.tvPay.setText(allSumBean.getData().getPrice_3());
                                        }else {
                                            mDataBinding.tvUn.setText("0");
                                            mDataBinding.tvAccepted.setText("0");
                                            mDataBinding.tvPay.setText("0");
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

    private void allSumByDataTime(final boolean refresh) {
        if (getContext() != null) {
            mCurrentPage = refresh ? START_PAGE : ++mCurrentPage;
            Map<String, Object> map = new TreeMap<String, Object>();
            map.put("page", mCurrentPage);
            map.put("limit", mShowCount);
            map.put("user_code", ConfigUtils.getCurrentUser(getContext()).getUserCode());
            map.put("in_type", incomeType);
            map.put("out_type", payType);
            map.put("startTime", mDataBinding.startTime.getText().toString().trim());
            map.put("endTime", mDataBinding.endTime.getText().toString().trim());
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = SignObject.getSignKey(getActivity(), map, "allSumByDataTime");
            NetBean netBean = new NetBean();
            netBean.setToken(ConfigUtils.getCurrentUser(getContext()).getToken());
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(AllSumByDataTimeAction.newInstance(getContext(), netBean)
                    .request(new AHttpService.IResCallback<BaseEntity<AllSumByDataTimeBean>>() {
                        @Override
                        public void onCallback(int resultCode, Response<BaseEntity<AllSumByDataTimeBean>> response,
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

    AllSumByDataTimeBean getPushListBean;

    private void initView(boolean refresh, AllSumByDataTimeBean pushListBean) {

        this.getPushListBean = pushListBean;
        if (refresh) {
            mAdapter.setData(pushListBean.getData());
        } else {
            mAdapter.append(pushListBean.getData());
        }
        mDataBinding.recyclerview.loadComplete(mAdapter.getItemCount() == 0,
                ((float) getPushListBean.getPage().getTotal() / (float) getPushListBean.getPage().getPageSize()) > mCurrentPage);
    }

    private class InnerAdapter extends AbsRecyclerViewAdapter<AllSumByDataTimeBean.DataBean> {
        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<AllSumByDataTimeBean.DataBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ItemBookingViewHolder(mInflater.inflate(R.layout.item_booking, parent, false));
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
                                    if ("bus_tool_in".equals(name)) {
                                        income(response.body().getData());
                                    } else if ("bus_tool_out".equals(name)) {
                                        pay(response.body().getData());
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
     * 下拉筛选
     */
    private void income(final GetComboBoxBean getComboBoxBean) {
//        getComboBoxBean.getResult().add(0, new GetComboBoxBean.ResultBean("", "不限"));
        mDataBinding.incomeLabels.setLabels(getComboBoxBean.getResult(), new LabelsView.LabelTextProvider<GetComboBoxBean.ResultBean>() {
            @Override
            public CharSequence getLabelText(TextView label, int position, GetComboBoxBean.ResultBean data) {
                //根据data和position返回label需要显示的数据。
                return data.getKey_value();
            }
        });

        mDataBinding.incomeLabels.setOnLabelSelectChangeListener(new LabelsView.OnLabelSelectChangeListener() {
            @Override
            public void onLabelSelectChange(TextView label, Object data, boolean isSelect, int position) {
                GetComboBoxBean.ResultBean re = (GetComboBoxBean.ResultBean) data;
                incomeType = re.getKey_name();
                allSum();
            }
        });
    }

    private void pay(final GetComboBoxBean getComboBoxBean) {
//        getComboBoxBean.getResult().add(0, new GetComboBoxBean.ResultBean("", "不限"));
        mDataBinding.payLabels.setLabels(getComboBoxBean.getResult(), new LabelsView.LabelTextProvider<GetComboBoxBean.ResultBean>() {
            @Override
            public CharSequence getLabelText(TextView label, int position, GetComboBoxBean.ResultBean data) {
                //根据data和position返回label需要显示的数据。
                return data.getKey_value();
            }
        });

        mDataBinding.payLabels.setOnLabelSelectChangeListener(new LabelsView.OnLabelSelectChangeListener() {
            @Override
            public void onLabelSelectChange(TextView label, Object data, boolean isSelect, int position) {
                GetComboBoxBean.ResultBean re = (GetComboBoxBean.ResultBean) data;
                payType = re.getKey_name();
                allSum();
            }
        });
    }

    public void onEvent(RefreshEvent event) {
        mDataBinding.recyclerview.autoRefresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }
}
