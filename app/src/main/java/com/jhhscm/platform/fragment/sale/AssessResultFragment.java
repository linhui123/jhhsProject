package com.jhhscm.platform.fragment.sale;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.jhhscm.platform.R;
import com.jhhscm.platform.databinding.FragmentAssessResultBinding;
import com.jhhscm.platform.event.ConsultationEvent;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.dialog.DropDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

public class AssessResultFragment extends AbsFragment<FragmentAssessResultBinding> {
    FindGoodsAssessBean findGoodsAssessBean;

    public static AssessResultFragment instance() {
        AssessResultFragment view = new AssessResultFragment();
        return view;
    }

    @Override
    protected FragmentAssessResultBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentAssessResultBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        findGoodsAssessBean = (FindGoodsAssessBean) getArguments().getSerializable("findGoodsAssessBean");
        initView();

        mDataBinding.tvAssess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    private void initView() {
        intChart();
        if (findGoodsAssessBean != null) {
            mDataBinding.tv1.setText(findGoodsAssessBean.getData().getName());
            String data = findGoodsAssessBean.getData().getFactory_time() == null ? "" : findGoodsAssessBean.getData().getFactory_time() + "年 | ";
            String Old_time = findGoodsAssessBean.getData().getOld_time() == null ? "" : findGoodsAssessBean.getData().getOld_time() + "小时 | ";
            String Province = findGoodsAssessBean.getData().getProvince() == null ? "" : findGoodsAssessBean.getData().getProvince() + "-";
            String City = findGoodsAssessBean.getData().getCity() == null ? "" : findGoodsAssessBean.getData().getCity();
            mDataBinding.tv2.setText(data + Old_time + Province + City);
            mDataBinding.tv3.setText(findGoodsAssessBean.getData().getCounter_price());
            if (findGoodsAssessBean.getData().getCounter_price() != null) {
                double price = Double.parseDouble(findGoodsAssessBean.getData().getCounter_price()) / 2;
                mDataBinding.tvPrice1.setText(findGoodsAssessBean.getData().getOriginal_price());
            }

            mDataBinding.tvPrice2.setText(findGoodsAssessBean.getData().getCounter_price());
            mDataBinding.tvPrice3.setText(findGoodsAssessBean.getData().getOriginal_price());
        }
    }

    ArrayList<Integer> colors;

    private void intChart() {
        mDataBinding.barchart.setDrawBarShadow(false);     //表不要阴影
//        mDataBinding.barchart.setDrawValueAboveBar(false); //数据显示上方
        Description description = new Description();
        description.setText(" ");
        mDataBinding.barchart.setDescription(description);  //表的描述信息
        mDataBinding.barchart.setBackgroundColor(Color.WHITE);//背景颜色
        mDataBinding.barchart.setDrawGridBackground(false);  //不显示图表网格
        mDataBinding.barchart.setPinchZoom(false);
        mDataBinding.barchart.setMaxVisibleValueCount(10); //最大显示的个数。超过60个将不再显示
        mDataBinding.barchart.setScaleEnabled(false);     //禁止缩放
        mDataBinding.barchart.setDragEnabled(false);// 是否可以拖拽

        draw();
    }

    public void draw() {
        //X轴 样式
        final XAxis xAxis = mDataBinding.barchart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setEnabled(true);//是否显示X坐标轴
        xAxis.setDrawGridLines(false);////是否显示网格线
        xAxis.setLabelRotationAngle(0);//柱的下面描述文字  旋转90度
        xAxis.setGranularity(1f);//设置最小间隔，防止当放大时，出现重复标签。
        xAxis.setCenterAxisLabels(true);//字体下面的标签 显示在每个直方图的中间
        xAxis.setLabelCount(6, true);//一个界面显示10个Lable。那么这里要设置11个
        xAxis.setTextSize(10f);
        //Y轴样式
        YAxis leftAxis = mDataBinding.barchart.getAxisLeft();
        leftAxis.setEnabled(false);//是否显示X坐标轴
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawLabels(false);
        leftAxis.setDrawGridLines(false);
        //Y轴右侧
        YAxis rightAxis = mDataBinding.barchart.getAxisRight();
        rightAxis.setEnabled(false);//是否显示X坐标轴
        rightAxis.setDrawAxisLine(false);
        rightAxis.setDrawLabels(false);
        rightAxis.setDrawGridLines(false);

        //.设置比例图标的显示隐藏
        Legend l = mDataBinding.barchart.getLegend();
        l.setForm(Legend.LegendForm.NONE);//不显示图例

        //模拟数据
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        yVals1.add(new BarEntry(2020, 80));
        yVals1.add(new BarEntry(2021, 70));
        yVals1.add(new BarEntry(2022, 60));
        yVals1.add(new BarEntry(2023, 50));
        yVals1.add(new BarEntry(2024, 40));
        setData(yVals1);
    }

    ArrayList<String> mlist = new ArrayList<String>();

    private void setData(ArrayList yVals1) {
        for (int i = 0; i < yVals1.size(); i++) {
            BarEntry barEntry = (BarEntry) yVals1.get(i);
            mlist.add(barEntry.getX() + "");
        }
        IAxisValueFormatter ix = new MyXAxisValueFormatter(mlist);
        mDataBinding.barchart.getXAxis().setValueFormatter(ix);

        BarDataSet set1;
        if (mDataBinding.barchart.getData() != null &&
                mDataBinding.barchart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mDataBinding.barchart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mDataBinding.barchart.getData().notifyDataChanged();
            mDataBinding.barchart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "");
            set1.setColors(ColorTemplate.MATERIAL_COLORS);
            //数据和颜色
//            colors = new ArrayList<Integer>();
//            colors.add(R.color.a397);
//            colors.add(R.color.a397);
//            set1.setColors(colors);
            set1.setColor(Color.parseColor("#3977FE"));
            set1.setDrawValues(false);
//            set1.setBarBorderWidth(10f);
//            set1.setBarBorderColor(Color.WHITE);
            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);
            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.6f);
            mDataBinding.barchart.setData(data);
        }
    }
}
