package com.jhhscm.platform.views.dlflipviewpage.Adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

/**
 * 页面管理控件适配器
 * 每个页面就是一个GridView
 * @author  dlong
 * created at 2019/3/20 4:18 PM
 */
public class DLViewPagerAdapter extends PagerAdapter {
    /** 页面列表 */
    private List<GridView> gridList;

    public DLViewPagerAdapter() {
        gridList = new ArrayList<>();
    }

    public void set(List<GridView> datas) {
        if (gridList.size() > 0) {
            gridList.clear();
        }
        gridList.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return gridList.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(gridList.get(position));
        return gridList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
