package com.jhhscm.platform.views.dlflipviewpage.utils;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.jhhscm.platform.views.dlflipviewpage.Adapter.DLGridViewAdapter;
import com.jhhscm.platform.views.dlflipviewpage.Adapter.DLViewPagerAdapter;
import com.jhhscm.platform.views.dlflipviewpage.bean.DLGridViewBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 管理工具
 * @author  dlong
 * created at 2019/3/21 10:40 AM
 */
public class DLVPSetting {
    /** 上下文 */
    private Context mContext;
    /** 行数 */
    private int mGridRows = 4;
    /** 列数 */
    private int mGridColumns = 4;
    /** 每页总数 */
    private int mGridItems;
    /** 接口声明 */
    private OnClickItemListener mListener;
    /** 自定义接口 */
    public interface OnClickItemListener{
        void OnClickItem(int position, DLGridViewBean bean);
        void OnClickItem(int position, Map<String, Object> map);
    }

    /**
     * 初始化
     * @param context
     * @param gridRows
     * @param gridColumns
     * @param listener
     */
    public DLVPSetting(Context context, int gridRows, int gridColumns, OnClickItemListener listener){
        mContext = context;
        mGridRows = gridRows;
        mGridColumns = gridColumns;
        mListener = listener;
        mGridItems = mGridRows * mGridColumns;
    }

    /**
     * 获得适配器
     * @param list
     * @return
     */
    public DLViewPagerAdapter getAdapter(List<DLGridViewBean> list){
        // 初始化一个适配器
        DLViewPagerAdapter viewPagerAdapter = new DLViewPagerAdapter();
        // 初始化一个页面列表
        List<GridView> gridList = new ArrayList<>();
        // 计算页面数
        int pageSize = list.size() % mGridItems == 0
                ? list.size() / mGridItems
                : list.size() / mGridItems + 1;
        // 循环添加页面
        for (int i = 0; i < pageSize; i++) {
            // 初始化一个数据列表
            final List<DLGridViewBean> newList = new ArrayList<>();
            // 计算当前页面的第一个item位置
            final int start = i * mGridItems;
            // 计算当前页面的最后一个item位置
            final int end = start + mGridItems;
            // 循环将当前页面的数据一个个累加到新的数据列表
            for (int j = start; j < (end > list.size()? list.size(): end); j++) {
                newList.add(list.get(j));
            }
            // 初始化一个gridView页面
            GridView gridView = new GridView(mContext);
            // 初始化一个gridView页面适配器
            DLGridViewAdapter adapter = new DLGridViewAdapter(mContext, newList);
            // 设置列数
            gridView.setNumColumns(mGridColumns);
            // 设置gridView的适配器
            gridView.setAdapter(adapter);
            // 设置点击item事件，回调到自定义接口
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mListener.OnClickItem(start + position, newList.get(position));
                }
            });
            // 页面列表添加页面
            gridList.add(gridView);
        }
        // 页面适配器设置页面列表
        viewPagerAdapter.set(gridList);

        return  viewPagerAdapter;
    }

    /**
     * 获得适配器
     * @param list
     * @param resource
     * @param from
     * @param to
     * @return
     */
    public DLViewPagerAdapter getAdapter(ArrayList<Map<String, Object>> list, @LayoutRes int resource, String[] from, int[] to){
        // 初始化一个适配器
        DLViewPagerAdapter viewPagerAdapter = new DLViewPagerAdapter();
        // 初始化一个页面列表
        List<GridView> gridList = new ArrayList<>();
        // 计算页面数
        int pageSize = list.size() % mGridItems == 0
                ? list.size() / mGridItems
                : list.size() / mGridItems + 1;
        // 循环添加页面
        for (int i = 0; i < pageSize; i++) {
            // 初始化一个数据列表
            final ArrayList<Map<String, Object>> newList = new ArrayList<>();
            // 计算当前页面的第一个item位置
            final int start = i * mGridItems;
            // 计算当前页面的最后一个item位置
            final int end = start + mGridItems;
            // 循环将当前页面的数据一个个累加到新的数据列表
            for (int j = start; j < (end > list.size()? list.size(): end); j++) {
                newList.add(list.get(j));
            }
            // 初始化一个gridView页面
            GridView gridView = new GridView(mContext);
            // 初始化一个gridView页面SimpleAdapter适配器
            SimpleAdapter adapter = new SimpleAdapter(mContext, newList, resource, from, to);
            // 设置列数
            gridView.setNumColumns(mGridColumns);
            // 设置gridView的适配器
            gridView.setAdapter(adapter);
            // 设置点击item事件，回调到自定义接口
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mListener.OnClickItem(start + position, newList.get(position));
                }
            });
            // 页面列表添加页面
            gridList.add(gridView);
        }
        // 页面适配器设置页面列表
        viewPagerAdapter.set(gridList);

        return  viewPagerAdapter;
    }
}
