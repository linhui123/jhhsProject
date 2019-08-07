package com.jhhscm.platform.fragment.casebase;

import com.jhhscm.platform.bean.FindCaseByEntity;


/**
 * Created by Administrator on 2018/8/23/023.
 */

public class CaseBasePhotoItem {
    public static final int TYPE_WORK_BENCH_TOP=1;
    public static final int TYPE_DIAGNOSIS_MANAGEMENT = 2;
    public static final int TYPE_POSTOPERATION_MANAGEMENT = 3;

    public FindCaseByEntity findCaseByEntity;
    public FindCaseByEntity.PhotosBean photosBean;
//    public MenuEntity menuEntity;

    public String mTitle;

    public String mMakeDate;
    public boolean pressed;
    public String mType;
    public int itemType;
    public String caseBaseId;
//    public PatientsImageBean patientsImageBean;

    public CaseBasePhotoItem(int itemType) {
        this.itemType = itemType;
    }


}
