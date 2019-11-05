package com.jhhscm.platform.fragment.my.book;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.AddBookingActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.FragmentBookingBinding;
import com.jhhscm.platform.databinding.FragmentMyMemberBinding;
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
import com.jhhscm.platform.http.sign.SignObject;
import com.jhhscm.platform.tool.DataUtil;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.recyclerview.DividerItemDecoration;
import com.jhhscm.platform.views.recyclerview.WrappedRecyclerView;
import com.jhhscm.platform.views.timePickets.TimePickerShow;
import com.jhhscm.platform.views.timePickets.TimePicketAlertDialog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

public class BookingFragment extends AbsFragment<FragmentBookingBinding> {

    private InnerAdapter mAdapter;
    private int mShowCount = 10;
    private int mCurrentPage = 1;
    private final int START_PAGE = mCurrentPage;

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

        mDataBinding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new InnerAdapter(getContext());
        mDataBinding.recyclerview.setAdapter(mAdapter);
        mDataBinding.recyclerview.autoRefresh();
        mDataBinding.recyclerview.setOnPullListener(new WrappedRecyclerView.OnPullListener() {
            @Override
            public void onRefresh(RecyclerView view) {
                getCouponList(true);
            }

            @Override
            public void onLoadMore(RecyclerView view) {
                getCouponList(false);
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

            }
        });

        mDataBinding.typeConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataBinding.llXiala.setVisibility(View.GONE);
                mDataBinding.llTime.setVisibility(View.GONE);
                mDataBinding.llType.setVisibility(View.GONE);
                mDataBinding.recyclerview.autoRefresh();
            }
        });
        mDataBinding.tvData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataBinding.llXiala.setVisibility(View.VISIBLE);
                mDataBinding.llTime.setVisibility(View.VISIBLE);
                mDataBinding.llType.setVisibility(View.GONE);
            }
        });

        mDataBinding.tvType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataBinding.llXiala.setVisibility(View.VISIBLE);
                mDataBinding.llTime.setVisibility(View.GONE);
                mDataBinding.llType.setVisibility(View.VISIBLE);
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

    private void getCouponList(final boolean refresh) {
        if (getContext() != null) {
            mCurrentPage = refresh ? START_PAGE : ++mCurrentPage;
            Map<String, Object> map = new TreeMap<String, Object>();
            map.put("page", mCurrentPage);
            map.put("limit", mShowCount);
            map.put("article_type_list", 1);
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = SignObject.getSignKey(getActivity(), map, "getArticleList");
            NetBean netBean = new NetBean();
            netBean.setToken("");
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(GetArticleListAction.newInstance(getContext(), netBean)
                    .request(new AHttpService.IResCallback<BaseEntity<GetPageArticleListBean>>() {
                        @Override
                        public void onCallback(int resultCode, Response<BaseEntity<GetPageArticleListBean>> response,
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

    GetPageArticleListBean getPushListBean;

    private void initView(boolean refresh, GetPageArticleListBean pushListBean) {

        this.getPushListBean = pushListBean;
        if (refresh) {
            mAdapter.setData(pushListBean.getData());
        } else {
            mAdapter.append(pushListBean.getData());
        }
        mDataBinding.recyclerview.loadComplete(mAdapter.getItemCount() == 0,
                ((float) getPushListBean.getPage().getTotal() / (float) getPushListBean.getPage().getPageSize()) > mCurrentPage);
    }

    private class InnerAdapter extends AbsRecyclerViewAdapter<GetPageArticleListBean.DataBean> {
        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<GetPageArticleListBean.DataBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ItemBookingViewHolder(mInflater.inflate(R.layout.item_booking, parent, false));
        }
    }
}