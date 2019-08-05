package com.jhhscm.platform.views.bottommenu;

import com.jhhscm.platform.views.timePickets.WheelAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/8/20/020.
 */

public class BottomMenuAdapter implements WheelAdapter {


    private ArrayList<String> mData;
    private String mText;


    public BottomMenuAdapter(ArrayList<String> arrayList) {
        this(arrayList, null);
    }

    public BottomMenuAdapter(ArrayList<String> arrayList, String text) {
        this.mData = arrayList;
        this.mText = text;
    }


    @Override
    public String getItem(int index) {
        if (index >= 0 && index < getItemsCount()) {
            return mText = mData.get(index);
        }
        return null;
    }

    @Override
    public int getMaximumLength() {
        return 18;
    }

    @Override
    public int getItemsCount() {
        return mData.size();
    }

}
