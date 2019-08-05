package com.jhhscm.platform.views.bottommenu.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/8/20/020.
 */

public class MenuData {
    private String mText;
    private boolean mSecondary;
    private ArrayList<String> mData;
    private boolean isAlter=false;
    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public boolean isAlter() {
        return isAlter;
    }

    public void setAlter(boolean alter) {
        isAlter = alter;
    }

    private String mSelectText;
    private int Id;

    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    private String stringId;

    public String getStringId() {
        return stringId;
    }

    public void setStringId(String stringId) {
        this.stringId = stringId;
    }

    private String mSelectTexts;

    public String getmSelectTexts() {
        return mSelectTexts;
    }

    public void setmSelectTexts(String mSelectTexts) {
        this.mSelectTexts = mSelectTexts;
    }

    public int getIds() {
        return Ids;
    }

    public void setIds(int ids) {
        Ids = ids;
    }

    private int Ids;

    public String getmSelectText() {
        return mSelectText;
    }

    public void setmSelectText(String mSelectText) {
        this.mSelectText = mSelectText;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getmText() {
        return mText;
    }

    public void setmText(String mText) {
        this.mText = mText;
    }

    public ArrayList<String> getmData() {
        return mData;
    }

    public void setmData(ArrayList<String> mData) {
        this.mData = mData;
    }

    public boolean ismSecondary() {
        return mSecondary;
    }

    public void setmSecondary(boolean mSecondary) {
        this.mSecondary = mSecondary;
    }
}
