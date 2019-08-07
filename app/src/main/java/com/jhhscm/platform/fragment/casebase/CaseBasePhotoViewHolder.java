//package com.jhhscm.platform.fragment.casebase;
//
//
//import android.view.View;
//
//import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
//import com.westcoast.blsapp.adapter.AbsRecyclerViewHolder;
//import com.westcoast.blsapp.bean.PbImage;
//import com.westcoast.blsapp.bean.UploadImage;
//import com.westcoast.blsapp.databinding.ItemCaseBasePhotoBinding;
//import com.westcoast.blsapp.event.CaseBasePhotoEvent;
//import com.westcoast.blsapp.http.bean.FindCaseByEntity;
//import com.westcoast.blsapp.utils.EventBusUtil;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
///**
// * Created by Administrator on 2018/5/9.
// */
//
//public class CaseBasePhotoViewHolder extends AbsRecyclerViewHolder<CaseBasePhotoItem> {
//
//    private ItemCaseBasePhotoBinding mBinding;
//    private List<PbImage> uploadImageList;
//
//    public CaseBasePhotoViewHolder(View itemView) {
//        super(itemView);
//        mBinding = ItemCaseBasePhotoBinding.bind(itemView);
//        EventBusUtil.registerEvent(this);
//    }
//
//    @Override
//    protected void onBindView(final CaseBasePhotoItem item) {
//        final FindCaseByEntity.PhotosBean photosBean = item.photosBean;
//        if (photosBean != null) {
//            //设置标题,例如手术前
//            mBinding.tvCasePhotoName.setText(photosBean.getNAME());
//            uploadImageList = new ArrayList<>();
//            if (photosBean.getPhoto() != null) {
//                for (int i = 0; i < photosBean.getPhoto().size(); i++) {
//                    PbImage pbImage = new PbImage();
//                    pbImage.setmToken(photosBean.getPhoto().get(i).getPHOTO_ID()+"");
//                    pbImage.setmUrl(photosBean.getPhoto().get(i).getIMG_URL());
//                    uploadImageList.add(pbImage);
//                }
//                mBinding.selector.setPbImageList(uploadImageList, item.pressed,item.mType,item.caseBaseId,photosBean.getNODE_ID());
//            } else {
//
//                if(item.mType.equals("2")){
////                    mBinding.tvCasePhotoName.setVisibility(View.GONE);
//                }
//                //设置照片数据
//                mBinding.selector.setPbImageList(uploadImageList, item.pressed,item.mType,item.caseBaseId,photosBean.getNODE_ID());
//            }
//
//            if(item.pressed){
//                mBinding.selector.setPbImageList(uploadImageList, item.pressed,"2",item.caseBaseId,photosBean.getNODE_ID());
//            }
//
//            mBinding.tvDelete.setVisibility(item.pressed ? View.VISIBLE : View.GONE);
//            mBinding.tvDelete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    EventBusUtil.post(new CaseBasePhotoEvent(CaseBasePhotoEvent.TYPE_DELETE_NODE,photosBean.getNODE_ID()+""));
//                }
//            });
//        }
//    }
//
//    public void onEventMainThread(CaseBasePhotoEvent event) {
//       if(event.mType==CaseBasePhotoEvent.TYPE_DELETE){
//           List<UploadImage> uploadImageList=mBinding.selector.getSelectUploadImageList();
//           EventBusUtil.post(new CaseBasePhotoEvent(CaseBasePhotoEvent.TYPE_UPLOAD,uploadImageList,mPosition));
//       }
//       if(event.mType==CaseBasePhotoEvent.TYPE_AMEND){
//           List<UploadImage> uploadImageList=mBinding.selector.getSelectUploadImageList();
//           if(uploadImageList.size()>0) {
//               EventBusUtil.post(new CaseBasePhotoEvent(CaseBasePhotoEvent.TYPE_UP_NODE, uploadImageList, mPosition));
//           }
//       }
//    }
//}
