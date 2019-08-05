package com.jhhscm.platform.fragment.labour;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.jhhscm.platform.R;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.FragmentLabourBinding;
import com.jhhscm.platform.databinding.FragmentMechanicsBinding;
import com.jhhscm.platform.event.GetRegionEvent;
import com.jhhscm.platform.fragment.Mechanics.MechanicsFragment;
import com.jhhscm.platform.fragment.Mechanics.OldMechanicsFragment;
import com.jhhscm.platform.fragment.Mechanics.adapter.SelectedAdapter;
import com.jhhscm.platform.fragment.Mechanics.bean.GetComboBoxBean;
import com.jhhscm.platform.fragment.Mechanics.bean.GetOldPageListBean;
import com.jhhscm.platform.fragment.Mechanics.holder.OldMechanicsViewHolder;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.tool.DisplayUtils;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.views.recyclerview.WrappedRecyclerView;

import java.util.ArrayList;
import java.util.List;


public class LabourFragment extends AbsFragment<FragmentLabourBinding> implements SeekBar.OnSeekBarChangeListener {
    private InnerAdapter mAdapter;
    private SelectedAdapter selectedAdapter;
    private List<GetComboBoxBean.ResultBean> resultBeanList;
    private int type = 1;//1招聘，2求职
    private int mShowCount = 10;
    private int mCurrentPage = 1;
    private final int START_PAGE = mCurrentPage;

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

        mDataBinding.rv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new InnerAdapter(getContext());
        mDataBinding.rv.setAdapter(mAdapter);
//        mDataBinding.rv.autoRefresh();
        mDataBinding.rv.setOnPullListener(new WrappedRecyclerView.OnPullListener() {
            @Override
            public void onRefresh(RecyclerView view) {
//                getOldPageList(true);
            }

            @Override
            public void onLoadMore(RecyclerView view) {
//                getOldPageList(false);
            }
        });

        resultBeanList = new ArrayList<>();

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
                if (mDataBinding.llXiala.getVisibility() == View.GONE) {
                    mDataBinding.llXiala.setVisibility(View.VISIBLE);
                    mDataBinding.llLocation.setVisibility(View.VISIBLE);
                } else {
                    if (mDataBinding.tvLocation.getVisibility() == View.VISIBLE) {
                        mDataBinding.llXiala.setVisibility(View.GONE);
                        closeDrap();
                    } else {
                        closeDrap();
                        mDataBinding.tvLocation.setVisibility(View.VISIBLE);
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

        mDataBinding.tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataBinding.seekbar.setVisibility(View.GONE);
                mDataBinding.tvTotal.setVisibility(View.GONE);
                mDataBinding.tvStart.setVisibility(View.GONE);
                mDataBinding.tvEnd.setVisibility(View.GONE);
            }
        });
        mDataBinding.tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataBinding.seekbar.setVisibility(View.VISIBLE);
                mDataBinding.tvTotal.setVisibility(View.VISIBLE);
                mDataBinding.tvStart.setVisibility(View.VISIBLE);
                mDataBinding.tvEnd.setVisibility(View.VISIBLE);
            }
        });
        mDataBinding.tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataBinding.seekbar.setVisibility(View.VISIBLE);
                mDataBinding.tvTotal.setVisibility(View.VISIBLE);
                mDataBinding.tvStart.setVisibility(View.VISIBLE);
                mDataBinding.tvEnd.setVisibility(View.VISIBLE);
            }
        });

        mDataBinding.seekbar.setOnSeekBarChangeListener(this);

        mDataBinding.tvZhaopin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 1;
                mDataBinding.tvZhaopin.setBackgroundResource(R.color.white);
                mDataBinding.tvZhaopin.setTextColor(getResources().getColor(R.color.a397));
                mDataBinding.tvQiuzhi.setTextColor(getResources().getColor(R.color.white));
                mDataBinding.tvQiuzhi.setBackgroundResource(R.drawable.bg_397_right_ovel);
            }
        });

        mDataBinding.tvQiuzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 2;
                mDataBinding.tvQiuzhi.setBackgroundResource(R.color.white);
                mDataBinding.tvQiuzhi.setTextColor(getResources().getColor(R.color.a397));
                mDataBinding.tvZhaopin.setBackgroundResource(R.drawable.bg_397_left_ovel);
                mDataBinding.tvZhaopin.setTextColor(getResources().getColor(R.color.white));
            }
        });
        initData(type);
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
        }
    }

    private void initData(int Type) {
        List<GetOldPageListBean.DataBean> dataBeans = new ArrayList<>();
        GetOldPageListBean.DataBean dataBean = new GetOldPageListBean.DataBean();
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
        mDataBinding.tvTotal.setText("0-" + total + "元");
    }

    private class InnerAdapter extends AbsRecyclerViewAdapter<GetOldPageListBean.DataBean> {
        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<GetOldPageListBean.DataBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new LabourViewHolder(mInflater.inflate(R.layout.item_labour, parent, false));
        }
    }

    private void closeDrap() {
        mDataBinding.llLocation.setVisibility(View.GONE);
        mDataBinding.llGangwei.setVisibility(View.GONE);
        mDataBinding.llJingyan.setVisibility(View.GONE);
        mDataBinding.llXinzi.setVisibility(View.GONE);
    }
}
