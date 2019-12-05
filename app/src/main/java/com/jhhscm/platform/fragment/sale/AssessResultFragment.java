package com.jhhscm.platform.fragment.sale;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.jhhscm.platform.databinding.FragmentAssessResultBinding;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.tool.CalculationUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

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
            if (findGoodsAssessBean.getPic_url()!=null){
                ImageLoader.getInstance().displayImage(findGoodsAssessBean.getPic_url(), mDataBinding.im);
            }

            mDataBinding.tv1.setText(findGoodsAssessBean.getName());
            String data = findGoodsAssessBean.getFactory_time() == null ? "" : findGoodsAssessBean.getFactory_time() + "年 | ";
            String Old_time = findGoodsAssessBean.getOld_time() == null ? "" : findGoodsAssessBean.getOld_time() + "小时 | ";
            String Province = findGoodsAssessBean.getProvince() == null ? "" : findGoodsAssessBean.getProvince() + "-";
            String City = findGoodsAssessBean.getCity() == null ? "" : findGoodsAssessBean.getCity();
            mDataBinding.tv2.setText(data + Old_time + Province + City);

            mDataBinding.tv3.setText(CalculationUtils.wan(findGoodsAssessBean.getCounter_price()));
            if (findGoodsAssessBean.getCounter_price() != null) {
                double price = Double.parseDouble(findGoodsAssessBean.getCounter_price()) / 2;
                mDataBinding.tvPrice1.setText(CalculationUtils.wan(price + ""));
            }
            mDataBinding.tvPrice2.setText(CalculationUtils.wan(findGoodsAssessBean.getCounter_price()));
            if (findGoodsAssessBean.getCounter_price() != null) {
                double price = Double.parseDouble(findGoodsAssessBean.getCounter_price()) * 2;
                mDataBinding.tvPrice3.setText(CalculationUtils.wan(price + ""));
            }

            if (findGoodsAssessBean.getFix_p_13() != null && findGoodsAssessBean.getFix_p_13().equals("0")) {
                mDataBinding.tv4.setText("未打锤");
            } else {
                mDataBinding.tv4.setText("打过锤");
            }
            if (findGoodsAssessBean.getFix_p_14() != null && findGoodsAssessBean.getFix_p_14().equals("0")) {
                mDataBinding.tv5.setText("土方");
            } else {
                mDataBinding.tv5.setText("石方");
            }
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
        mDataBinding.barchart.setPinchZoom(false);  //设置x轴和y轴能否同时缩放。默认否
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
        yVals1.add(new BarEntry(2021, 80));
        yVals1.add(new BarEntry(2022, 75));
        yVals1.add(new BarEntry(2023, 70));
        yVals1.add(new BarEntry(2024, 65));
        yVals1.add(new BarEntry(2025, 60));
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
            set1.setColor(Color.parseColor("#3977FE"));
            set1.setDrawValues(false);
            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);
            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.6f);
            mDataBinding.barchart.setData(data);
        }
    }
}
