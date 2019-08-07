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
//import com.westcoast.blsapp.activity.CaseBasePhotoActivity;
//import com.westcoast.blsapp.app.BLSApp;
//import com.westcoast.blsapp.bean.CaseBasePhotoBean;
//import com.westcoast.blsapp.bean.UploadImage;
//import com.westcoast.blsapp.bean.UserSession;
//import com.westcoast.blsapp.databinding.FragmentNewCaseBaseBinding;
//import com.westcoast.blsapp.fragment.base.AbsFragment;
//import com.westcoast.blsapp.http.AHttpService;
//import com.westcoast.blsapp.http.HttpHelper;
//import com.westcoast.blsapp.http.action.FindCaseLevelAction;
//import com.westcoast.blsapp.http.action.SaveNewCaseAction;
//import com.westcoast.blsapp.http.action.UploadImgAction;
//import com.westcoast.blsapp.http.bean.BaseEntity;
//import com.westcoast.blsapp.http.bean.BaseErrorInfo;
//import com.westcoast.blsapp.http.bean.FindCaseLevelEntity;
//import com.westcoast.blsapp.http.bean.SaveNewCaseEntity;
//import com.westcoast.blsapp.http.bean.UploadImageResponse;
//import com.westcoast.blsapp.selector.IAUploadImageView;
//import com.westcoast.blsapp.utils.ConfigUtils;
//import com.westcoast.blsapp.utils.StringUtils;
//import com.westcoast.blsapp.utils.ToastUtils;
//import com.westcoast.blsapp.views.bottommenu.BottomMenuShow;
//import com.westcoast.blsapp.views.bottommenu.bean.MenuData;
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
// * Created by Administrator on 2018/10/19/019.
// */
//
//public class NewCaseBaseFragment extends AbsFragment<FragmentNewCaseBaseBinding>  implements IAUploadImageView {
//
//    private String mLevel;
//    private FindCaseLevelEntity mFindCaseLevelEntity;
//
//    public static NewCaseBaseFragment instance() {
//        NewCaseBaseFragment view = new NewCaseBaseFragment();
//        return view;
//    }
//
//    @Override
//    protected FragmentNewCaseBaseBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
//        return FragmentNewCaseBaseBinding.inflate(inflater,container,attachToRoot);
//    }
//
//    @Override
//    protected void setupViews() {
//        findLevel();
//        mDataBinding.btnSave.setOnClickListener(createOnClickListener());
//        mDataBinding.editCaseBaseName.addTextChangedListener(mEditChangeListener);
//        mDataBinding.editCaseBaseName.setFilters(new InputFilter[]{StringUtils.inputFilter(getContext())});
//        mDataBinding.rlCaseBaseType.setOnClickListener(createOnClickListener());
//    }
//
//    private View.OnClickListener createOnClickListener() {
//        return new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int viewId = v.getId();
//              if(R.id.btn_save==viewId){
////                    saveNewCase();
//                  if (getUploadImageList().size() != 0) {
//                      doUploadAImagesAction();
//                  } else {
//                      showDialog();
//                      saveNewCase();
//                  }
//              }
//              if(R.id.rl_case_base_type==viewId){
//                  showLevel();
//              }
//            }
//        };
//    }
//
//    private void judgeButton() {
//        String name=mDataBinding.editCaseBaseName.getText().toString();
//        String remark=mDataBinding.editRemark.getText().toString();
//            if (!StringUtils.isNullEmpty(name) &&!StringUtils.isNullEmpty(mLevel)) {
//                mDataBinding.btnSave.setEnabled(true);
//            } else {
//                mDataBinding.btnSave.setEnabled(false);
//            }
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
//               judgeButton();
//            }else {
//                mDataBinding.btnSave.setEnabled(false);
//            }
//
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//
//        }
//    };
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
//    private void saveNewCase(){
//        List<UploadImage> uploadAImages = getUploadImageList();
//        List<CaseBasePhotoBean> list = new ArrayList<>();
//        for (int i = 0; i < uploadAImages.size(); i++) {
//            CaseBasePhotoBean caseBasePhotoBean = new CaseBasePhotoBean();
//            caseBasePhotoBean.setCatalogues(uploadAImages.get(i).getCatalogues());
//            caseBasePhotoBean.setAllfilePath(uploadAImages.get(i).getAllfilePath());
//            list.add(caseBasePhotoBean);
//        }
//            String jsonString = new Gson().toJson(list).toString();
//            String name = mDataBinding.editCaseBaseName.getText().toString();
//            String remark=mDataBinding.editRemark.getText().toString();
//            onNewRequestCall(SaveNewCaseAction.newInstance(getContext(), "1", mLevel, name,jsonString,remark).request(new AHttpService.IResCallback<BaseEntity<SaveNewCaseEntity>>() {
//                @Override
//                public void onCallback(int resultCode, Response<BaseEntity<SaveNewCaseEntity>> response, BaseErrorInfo baseErrorInfo) {
//                    if (getView() != null) {
//                        closeDialog();
//                        if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
//                            return;
//                        }
//                        if (response != null) {
//                            new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
//                            if (response.body().getCode().equals("200")) {
//                                ToastUtils.show(getContext(), response.body().getMessage());
//                                getActivity().finish();
//                                CaseBasePhotoActivity.start(getContext(), response.body().getResult().getCASE_ID(), "1");
//                            } else {
//                                ToastUtils.show(getContext(), response.body().getMessage());
//                            }
//                        }
//                    }
//                }
//            }));
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
//                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
//                                return;
//                            }
//                            if (response != null) {
//                                new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
//                                if (response.body().getCode().equals("200")) {
//                                    mFindCaseLevelEntity=response.body().getResult();
//                                    mLevel = mFindCaseLevelEntity.getCASE_PAGE2().get(0).getCASELEVEL();
//                                    mDataBinding.tvCaseBaseType.setText(mFindCaseLevelEntity.getCASE_PAGE2().get(0).getCASELEVELNAME());
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
//
//    protected void doUploadAImagesAction() {
//        showDialog();
//        boolean hasImageAToken = doUploadImagesAction();
//        if (hasImageAToken) {
//            doHasImageTokenSuccess();
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
//                    saveNewCase();
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
//    @Override
//    public AbsFragment getFragment() {
//            return this;
//    }
//
//    @Override
//    public void onHeadActionClick() {
//
//    }
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
