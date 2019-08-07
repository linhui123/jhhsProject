//package com.jhhscm.platform.fragment.casebase;
//
//import android.content.Context;
//import android.text.Editable;
//import android.text.InputFilter;
//import android.text.TextWatcher;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.google.gson.Gson;
//import com.westcoast.blsapp.R;
//import com.westcoast.blsapp.app.BLSApp;
//import com.westcoast.blsapp.bean.AmendCaseBaseBean;
//import com.westcoast.blsapp.bean.PbImage;
//import com.westcoast.blsapp.bean.UploadImage;
//import com.westcoast.blsapp.bean.UserSession;
//import com.westcoast.blsapp.databinding.FragmentAmendCaseBaseBinding;
//import com.westcoast.blsapp.event.AmendCaseBaseEvent;
//import com.westcoast.blsapp.event.CaseBasePhotoEvent;
//import com.westcoast.blsapp.fragment.base.AbsFragment;
//import com.westcoast.blsapp.http.AHttpService;
//import com.westcoast.blsapp.http.HttpHelper;
//import com.westcoast.blsapp.http.action.DeleteCaseSYAction;
//import com.westcoast.blsapp.http.action.DeleteNodeORPhotoAction;
//import com.westcoast.blsapp.http.action.FindCaseKBAction;
//import com.westcoast.blsapp.http.action.FindCaseLevelAction;
//import com.westcoast.blsapp.http.action.UpdateCaseAction;
//import com.westcoast.blsapp.http.action.UploadImgAction;
//import com.westcoast.blsapp.http.bean.BaseEntity;
//import com.westcoast.blsapp.http.bean.BaseErrorInfo;
//import com.westcoast.blsapp.http.bean.FindCaseKBEntity;
//import com.westcoast.blsapp.http.bean.FindCaseLevelEntity;
//import com.westcoast.blsapp.http.bean.UploadImageResponse;
//import com.westcoast.blsapp.selector.IAUploadImageView;
//import com.westcoast.blsapp.utils.ConfigUtils;
//import com.westcoast.blsapp.utils.EventBusUtil;
//import com.westcoast.blsapp.utils.StringUtils;
//import com.westcoast.blsapp.utils.ToastUtils;
//import com.westcoast.blsapp.views.bottommenu.BottomMenuShow;
//import com.westcoast.blsapp.views.bottommenu.bean.MenuData;
//import com.westcoast.blsapp.views.dialog.ConfirmDialog;
//
//import java.io.File;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.util.ArrayList;
//import java.util.List;
//
//import retrofit2.Response;
//import rx.Observable;
//import rx.android.schedulers.AndroidSchedulers;
//import rx.functions.Action1;
//import rx.functions.Func1;
//import rx.schedulers.Schedulers;
//import top.zibin.luban.Luban;
//
///**
// * Created by Administrator on 2018/10/23/023.
// */
//
//public class AmendCaseBaseFragment extends AbsFragment<FragmentAmendCaseBaseBinding> implements IAUploadImageView {
//
//    private FindCaseLevelEntity mFindCaseLevelEntity;
//    private String mLevel;
//    private String mCaseId;
//    private String mPhotoId;
//
//    public static AmendCaseBaseFragment instance() {
//        AmendCaseBaseFragment view = new AmendCaseBaseFragment();
//        return view;
//    }
//
//    @Override
//    protected FragmentAmendCaseBaseBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
//        return FragmentAmendCaseBaseBinding.inflate(inflater,container,attachToRoot);
//    }
//
//    @Override
//    protected void setupViews() {
//        EventBusUtil.registerEvent(this);
//        if(getArguments()!=null){
//            mCaseId=getArguments().getString("caseId");
//        }
//        findCaseKB();
//        mDataBinding.btnAmend.setOnClickListener(createOnClickListener());
//        mDataBinding.editCaseBaseName.addTextChangedListener(mEditChangeListener);
//        mDataBinding.editCaseBaseName.setFilters(new InputFilter[]{StringUtils.inputFilter(getContext())});
//        mDataBinding.rlCaseBaseType.setOnClickListener(createOnClickListener());
//    }
//
//    @Override
//    public AbsFragment getFragment() {
//        return this;
//    }
//
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        EventBusUtil.unregisterEvent(this);
//    }
//
//    public void onEvent(AmendCaseBaseEvent event){
//        if(!StringUtils.isNullEmpty(mPhotoId)){
//            deletelNode(mPhotoId);
//        }
//    }
//
//    public void doUploadAImagesAction() {
//
//        if (getUploadImageList().size() != 0) {
//            showDialog();
//            boolean hasImageAToken = doUploadImagesAction();
//            if (hasImageAToken) {
//                doHasImageTokenSuccess();
//            }
//        } else {
//          onHeadActionClick();
//        }
//    }
//
//    private boolean doUploadImagesAction() {
//        List<UploadImage> uploadImages = getUploadImageList();
//        if (uploadImages == null) return true;
//        boolean hasImageToken = true;
//        for (int i = 0; i < uploadImages.size(); i++) {
//            UploadImage image = uploadImages.get(i);
//            if (StringUtils.isNullEmpty(image.getImageToken())) {
//                hasImageToken = false;
//                imageFile(getContext(), image.getImageUrl());
//            }
//        }
//        return hasImageToken;
//    }
//
//    public void imageFile(Context context, final String imagePath) {
//        final long UPLOAD_IMAGE_SIZE_LIMIT = 1024 * 1024;//1M
//        File imageFile = null;
//        try {
//            imageFile = new File(new URI(imagePath));
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//        if (imageFile == null || !imageFile.exists()) {
//            ToastUtils.show(context, "上传文件不存在！");
//            return;
//        }
//        try {
//            //1M以上图片进行压缩处理
//            if (imageFile.length() > UPLOAD_IMAGE_SIZE_LIMIT) {
//                Luban.get(BLSApp.getInstance())
//                        .load(imageFile)
//                        .putGear(Luban.THIRD_GEAR)
//                        .setFilename(imageFile.getAbsolutePath())
//                        .asObservable()
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .doOnError(new Action1<Throwable>() {
//                            @Override
//                            public void call(Throwable throwable) {
//                                throwable.printStackTrace();
//                            }
//                        })
//                        .onErrorResumeNext(new Func1<Throwable, Observable<? extends File>>() {
//                            @Override
//                            public Observable<? extends File> call(Throwable throwable) {
//                                return Observable.empty();
//                            }
//                        })
//                        .subscribe(new Action1<File>() {
//                            @Override
//                            public void call(File file) {
//                                // TODO 压缩成功后调用，返回压缩后的图片文件
////                                return file;
////                                return photos;
//                                doUploadImageAction(file, imagePath);
//                            }
//                        });    //启动压缩
//                return;
//            } else {
//                doUploadImageAction(imageFile, imagePath);
//                return;
//            }
//        } catch (Throwable e) {
//            ToastUtils.show(getContext(), "上传失败");
//            return;
//        }
//    }
//
//    private void doUploadImageAction(final File file, final String imageUrl) {
//        String token = ConfigUtils.getCurrentUser(getContext()).getToken();
//        onNewRequestCall(UploadImgAction.newInstance(getContext(), file, token).request(new AHttpService.IResCallback<BaseEntity<UploadImageResponse>>() {
//            @Override
//            public void onCallback(int resultCode, Response<BaseEntity<UploadImageResponse>> response, BaseErrorInfo baseErrorInfo) {
//                if (getView() != null) {
//                    if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
//                        return;
//                    }
////                        closeDialog();
//                    if (response != null) {
//                        new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
//                        if (response.body().getCode().equals("200")) {
//                            doUploadImageResponse(imageUrl, response.body().getResult());
//                        } else {
//                            ToastUtils.show(getContext(), response.body().getMessage());
//                        }
//                    }
//                }
//            }
//        }));
//    }
//
//
//    private void doUploadImageResponse(String imageUrl, UploadImageResponse response) {
//        setImageToken(new UploadImage(imageUrl, response.getCatalogues(), response.getAllfilePath(), response.getCatalogues()));
//        doHasImageTokenSuccess();
//    }
//
//    //判断是否图片已经上传完成
//    private void doHasImageTokenSuccess() {
//        List<UploadImage> uploadAImages = getUploadImageList();
//        List<String> imageATokens = getImageTokenList();
//        if (uploadAImages != null && uploadAImages.size() > 0) {
//            if (imageATokens != null && imageATokens.size() > 0) {
//                if (uploadAImages.size() == imageATokens.size()) {
//                    onHeadActionClick();
//                } else {
//                    error();
//                    closeDialog();
//                    return;
//                }
//            } else {
//                return;
//            }
//        }
//
//    }
//
//    protected List<String> getImageTokenList() {
//        List<String> imageTokens = new ArrayList<>();
//        List<UploadImage> uploadImages = getUploadImageList();
//        if (uploadImages == null) return imageTokens;
//        for (int i = 0; i < uploadImages.size(); i++) {
//            UploadImage image = uploadImages.get(i);
//            if (!StringUtils.isNullEmpty(image.getImageToken())) {
//                imageTokens.add(image.getImageToken());
//            } else {
//                return null;
//            }
//        }
//        return imageTokens;
//    }
//
//    public void error() {
//        ToastUtils.show(getContext(), R.string.error_net);
//    }
//
//
//    public void onHeadActionClick(){
//        showDialog();
//        List<UploadImage> uploadAImages = getUploadImageList();
//        List<AmendCaseBaseBean> list = new ArrayList<>();
//        for (int i = 0; i < uploadAImages.size(); i++) {
//            AmendCaseBaseBean amendCaseBaseBean = new AmendCaseBaseBean();
//            amendCaseBaseBean.setIMG_URL(uploadAImages.get(i).getAllfilePath());
//            amendCaseBaseBean.setMENU_CATALOGUES(uploadAImages.get(i).getCatalogues());
//            list.add(amendCaseBaseBean);
//        }
//        String jsonString;
//        if(list.size()!=0) {
//            jsonString = new Gson().toJson(list).toString();
//        }else {
//            jsonString="";
//        }
//
//        String name=mDataBinding.editCaseBaseName.getText().toString();
//        String remark=mDataBinding.editRemark.getText().toString();
//        onNewRequestCall(UpdateCaseAction.newInstance(getContext(),mCaseId,name,mLevel,remark,jsonString).request(new AHttpService.IResCallback<BaseEntity>() {
//            @Override
//            public void onCallback(int resultCode, Response<BaseEntity> response, BaseErrorInfo baseErrorInfo) {
//                if (getView() != null) {
//                    closeDialog();
//                    if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
//                        return;
//                    }
//                    if (response != null) {
//                        new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
//                        if (response.body().getCode().equals("200")) {
//                            EventBusUtil.post(new CaseBasePhotoEvent(CaseBasePhotoEvent.TYPE_REFRESH));
//                            getActivity().finish();
//                        } else {
//                            ToastUtils.show(getContext(), response.body().getMessage());
//                        }
//                    }
//                }
//            }
//        }));
//    }
//
//    private View.OnClickListener createOnClickListener() {
//        return new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int viewId = v.getId();
//                if(R.id.btn_amend==viewId){
//                    ConfirmDialog mDelDialog=null;
//                        if (mDelDialog == null) {
//                            mDelDialog = new ConfirmDialog(getContext(), "是否确认删除案例？", getString(R.string.photopicker_del_cancel), getString(R.string.photopicker_del_sure));
//                        }
//                        mDelDialog.setCallbackListener(new ConfirmDialog.CallbackListener() {
//                            @Override
//                            public void clickNo() {
//
//                            }
//
//                            @Override
//                            public void clickYes() {
//                                amend();
//                            }
//                        });
//                        mDelDialog.show();
//                }
//                if(R.id.rl_case_base_type==viewId){
//                    showLevel();
//                }
//            }
//        };
//    }
//
//    private void showLevel(){
//        if(mFindCaseLevelEntity!=null) {
//            BottomMenuShow bottomMenuShow = new BottomMenuShow(getContext());
//
//            final ArrayList<MenuData> menuDatas = new ArrayList<>();
//            for (int i = 0; i < mFindCaseLevelEntity.getCASE_PAGE2().size(); i++) {
//                MenuData menuData = new MenuData();
//                menuData.setmSecondary(false);
//                menuData.setmText(mFindCaseLevelEntity.getCASE_PAGE2().get(i).getCASELEVELNAME());
//                menuDatas.add(menuData);
//            }
//
//            bottomMenuShow.bottomMenuAlertDialog(mLevel, menuDatas);
//            bottomMenuShow.setOnBottomMenuListener(new BottomMenuShow.OnBottomMenuListener() {
//                @Override
//                public void onClicklistener(MenuData data) {
//                    mLevel = mFindCaseLevelEntity.getCASE_PAGE2().get(data.getId()).getCASELEVEL();
//                    mDataBinding.tvCaseBaseType.setText(menuDatas.get(data.getId()).getmText());
//                    judgeButton();
//                }
//            });
//        }else {
//            findLevel();
//        }
//    }
//
//    private void judgeButton() {
//        String name=mDataBinding.editCaseBaseName.getText().toString();
//        if (!StringUtils.isNullEmpty(name) &&!StringUtils.isNullEmpty(mLevel)) {
//            mDataBinding.btnAmend.setEnabled(true);
//        } else {
//            mDataBinding.btnAmend.setEnabled(false);
//        }
//    }
//
//    private void findLevel() {
//        if (getContext() != null) {
//            UserSession userSession = ConfigUtils.getCurrentUser(getContext());
//            if (userSession != null) {
//                onNewRequestCall(FindCaseLevelAction.newInstance(getContext()).request(new AHttpService.IResCallback<BaseEntity<FindCaseLevelEntity>>() {
//                    @Override
//                    public void onCallback(int resultCode, Response<BaseEntity<FindCaseLevelEntity>> response, BaseErrorInfo baseErrorInfo) {
//                        if (getView() != null) {
//                            closeDialog();
//                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
//                                return;
//                            }
//                            if (response != null) {
//                                new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
//                                if (response.body().getCode().equals("200")) {
//                                    mFindCaseLevelEntity=response.body().getResult();
//                                    mDataBinding.tvCaseBaseType.setText(mFindCaseLevelEntity.getCASE_PAGE2().get(0).getCASELEVELNAME());
//                                    for (int i=0;i<mFindCaseLevelEntity.getCASE_PAGE2().size();i++){
//                                        if(mLevel.equals(mFindCaseLevelEntity.getCASE_PAGE2().get(i).getCASELEVEL())){
//                                            mDataBinding.tvCaseBaseType.setText(mFindCaseLevelEntity.getCASE_PAGE2().get(i).getCASELEVELNAME());
//                                        }
//                                    }
//                                    judgeButton();
//                                } else {
//                                    ToastUtils.show(getContext(), response.body().getMessage());
//                                }
//                            }
//                        }
//                    }
//                }));
//            }
//        }
//    }
//
//    private void findCaseKB(){
//        showDialog();
//        onNewRequestCall(FindCaseKBAction.newInstance(getContext(),mCaseId).request(new AHttpService.IResCallback<BaseEntity<FindCaseKBEntity>>() {
//            @Override
//            public void onCallback(int resultCode, Response<BaseEntity<FindCaseKBEntity>> response, BaseErrorInfo baseErrorInfo) {
//                if (getView() != null) {
//                    if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
//                        return;
//                    }
//                    if (response != null) {
//                        new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
//                        if (response.body().getCode().equals("200")) {
//                            mDataBinding.editCaseBaseName.setText(response.body().getResult().getNAME());
//                            mLevel=response.body().getResult().getLEVEL();
//                            mDataBinding.editRemark.setText(response.body().getResult().getREMARK());
//                            List<PbImage> uploadImageList=new ArrayList<>();
//                            PbImage pbImage = new PbImage();
//                            if(response.body().getResult().getADDRESS()!=null && response.body().getResult().getADDRESS().size()>0 && !StringUtils.isNullEmpty(response.body().getResult().getADDRESS().get(0).getIMG_URL())) {
//                                pbImage.setmToken(response.body().getResult().getADDRESS().get(0).getMENU_CATALOGUES());
//                                pbImage.setmUrl(response.body().getResult().getADDRESS().get(0).getIMG_URL());
//                                uploadImageList.add(pbImage);
//                                mPhotoId=response.body().getResult().getADDRESS().get(0).getPHOTO_ID();
//                            }
//                            mDataBinding.isSchemeImage.setPbImageList(uploadImageList);
//                           findLevel();
//                        } else {
//                            ToastUtils.show(getContext(), response.body().getMessage());
//                        }
//                    }
//                }
//            }
//        }));
//    }
//
//    private void amend(){
//        showDialog();
//        onNewRequestCall(DeleteCaseSYAction.newInstance(getContext(),mCaseId).request(new AHttpService.IResCallback<BaseEntity>() {
//            @Override
//            public void onCallback(int resultCode, Response<BaseEntity> response, BaseErrorInfo baseErrorInfo) {
//                if (getView() != null) {
//                    if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
//                        return;
//                    }
//                    if (response != null) {
//                        new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
//                        if (response.body().getCode().equals("200")) {
//                            EventBusUtil.post(new CaseBasePhotoEvent(CaseBasePhotoEvent.TYPE_CLOSE));
//                            getActivity().finish();
//                        } else {
//                            ToastUtils.show(getContext(), response.body().getMessage());
//                        }
//                    }
//                }
//            }
//        }));
//    }
//
//    private void deletelNode(String nodeId) {
//        showDialog();
//        onNewRequestCall(DeleteNodeORPhotoAction.newInstance(getContext(), nodeId).request(new AHttpService.IResCallback<BaseEntity>() {
//            @Override
//            public void onCallback(int resultCode, Response<BaseEntity> response, BaseErrorInfo baseErrorInfo) {
//                if (getView() != null) {
//                    closeDialog();
//                    if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
//                        return;
//                    }
//                    if (response != null) {
//                        new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
//                        if (response.body().getCode().equals("200")) {
//                            ToastUtils.show(getContext(), response.body().getMessage());
//                        } else {
//                            ToastUtils.show(getContext(), response.body().getMessage());
//                        }
//                    }
//                }
//            }
//        }));
//    }
//
//    private TextWatcher mEditChangeListener = new TextWatcher() {
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//        }
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//            //mDataBinding.inputEditUserName.setSelection(s.length());
//            if (s.length() > 0) {
//                judgeButton();
//            }else {
//                mDataBinding.btnAmend.setEnabled(false);
//            }
//
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//
//        }
//    };
//
//    @Override
//    public String getContent() {
//        return null;
//    }
//
//    @Override
//    public List<UploadImage> getUploadImageList() {
//        return mDataBinding.isSchemeImage.getUploadImageList();
//    }
//
//    @Override
//    public void setImageToken(UploadImage uploadImage) {
//        mDataBinding.isSchemeImage.setImageToken(uploadImage);
//    }
//
//    @Override
//    public String getDesignerId() {
//        return null;
//    }
//}
