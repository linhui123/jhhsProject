package com.jhhscm.platform.fragment.casebase;

import android.content.Context;
import android.view.ViewGroup;

import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.bean.FindCaseByEntity;

import static android.media.CamcorderProfile.get;


/**
 * Created by Administrator on 2018/4/28.
 */

public class CaseBasePhotoAdapter extends AbsRecyclerViewAdapter<CaseBasePhotoItem> {

    public CaseBasePhotoAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        return get(position).itemType;
    }


    private void addPhotoTop(FindCaseByEntity findCaseByEntity) {
        CaseBasePhotoItem item = new CaseBasePhotoItem(CaseBasePhotoItem.TYPE_WORK_BENCH_TOP);
        item.findCaseByEntity = findCaseByEntity;
        mData.add(item);
    }

    private void addPhoto(FindCaseByEntity.PhotosBean photosBean, boolean pressed, String type, String caseBaseId) {
        CaseBasePhotoItem item = new CaseBasePhotoItem(CaseBasePhotoItem.TYPE_DIAGNOSIS_MANAGEMENT);
        item.photosBean = photosBean;
        item.pressed=pressed;
        item.mType=type;
        item.caseBaseId=caseBaseId;
        mData.add(item);
    }

    public void setDetail(FindCaseByEntity findCaseByEntity, boolean pressed, String type, String caseBaseId) {
        mData.clear();
        //
        addPhotoTop(findCaseByEntity);
        if(findCaseByEntity.getPhotos()!=null) {
            for (int i = 0; i < findCaseByEntity.getPhotos().size(); i++) {
                addPhoto(findCaseByEntity.getPhotos().get(i), pressed, type,caseBaseId);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public AbsRecyclerViewHolder<CaseBasePhotoItem> onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
//            case CaseBasePhotoItem.TYPE_WORK_BENCH_TOP:
//                //添加标题
//                return new CaseBasePhotoTopViewHolder(mInflater.inflate(R.layout.item_case_base_photo_top, parent, false));
//            case CaseBasePhotoItem.TYPE_DIAGNOSIS_MANAGEMENT:
//                return new CaseBasePhotoViewHolder(mInflater.inflate(R.layout.item_case_base_photo, parent, false));
        }
        return null;
    }
}
