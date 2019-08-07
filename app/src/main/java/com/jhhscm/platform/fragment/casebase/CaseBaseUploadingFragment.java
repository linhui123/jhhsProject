//package com.jhhscm.platform.fragment.casebase;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.google.gson.Gson;
//import com.westcoast.blsapp.R;
//import com.westcoast.blsapp.activity.NewNodeActivity;
//import com.westcoast.blsapp.app.BLSApp;
//import com.westcoast.blsapp.bean.CaseBasePhotoBean;
//import com.westcoast.blsapp.bean.UploadImage;
//import com.westcoast.blsapp.databinding.FragmentCaseBaseUploadingBinding;
//import com.westcoast.blsapp.event.CaseBasePhotoEvent;
//import com.westcoast.blsapp.event.CaseBaseUploadingEvent;
//import com.westcoast.blsapp.fragment.base.AbsFragment;
//import com.westcoast.blsapp.http.AHttpService;
//import com.westcoast.blsapp.http.HttpHelper;
//import com.westcoast.blsapp.http.action.SaveNewCasePhotoAction;
//import com.westcoast.blsapp.http.action.SelectCaseNodeAction;
//import com.westcoast.blsapp.http.action.UploadImgAction;
//import com.westcoast.blsapp.http.bean.BaseEntity;
//import com.westcoast.blsapp.http.bean.BaseErrorInfo;
//import com.westcoast.blsapp.http.bean.SelectCaseNodeEntity;
//import com.westcoast.blsapp.http.bean.UploadImageResponse;
//import com.westcoast.blsapp.selector.IAUploadImageView;
//import com.westcoast.blsapp.utils.ConfigUtils;
//import com.westcoast.blsapp.utils.EventBusUtil;
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
// * Created by Administrator on 2018/10/17/017.
// */
//
//public class CaseBaseUploadingFragment extends AbsFragment<FragmentCaseBaseUploadingBinding> implements IAUploadImageView {
//
//    private String mCaseBaseId;
//    private SelectCaseNodeEntity mSelectCaseNodeEntity;
//    private String mTimeNodeId;
//    private ArrayList<String> images;
//    private String mNodeName = "";
//
//    public static CaseBaseUploadingFragment instance() {
//        CaseBaseUploadingFragment view = new CaseBaseUploadingFragment();
//        return view;
//    }
//
//    @Override
//    protected FragmentCaseBaseUploadingBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
//        return FragmentCaseBaseUploadingBinding.inflate(inflater, container, attachToRoot);
//    }
//
//    @Override
//    protected void setupViews() {
//        mDataBinding.btnUp.setOnClickListener(createOnClickListener());
//        mDataBinding.rlCaseBaseTimeNode.setOnClickListener(createOnClickListener());
//        EventBusUtil.registerEvent(this);
//        images = new ArrayList<>();
//        if (getArguments() != null) {
//            images = getArguments().getStringArrayList("images");
//            mCaseBaseId = getArguments().getString("caseBaseId");
//            mTimeNodeId = getArguments().getString("timeNodeId");
//        }
//        if (images != null) {
//            mDataBinding.isSchemeImage.setPbImageList(images);
//        }
//        selectCaseNode();
//    }
//
//    @Override
//    public AbsFragment getFragment() {
//        return this;
//    }
//
//    @Override
//    public void onHeadActionClick() {
//
//    }
//
//    public void onEvent(CaseBaseUploadingEvent event) {
//        mNodeName = event.mName;
//        selectCaseNode();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        EventBusUtil.unregisterEvent(this);
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
//                    submit();
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
//    private View.OnClickListener createOnClickListener() {
//        return new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int viewId = v.getId();
//                if (R.id.btn_up == viewId) {
//                    if (getUploadImageList().size() != 0) {
//                        doUploadAImagesAction();
//                    } else {
//                        submit();
//                    }
//                }
//                if (R.id.rl_case_base_time_node == viewId) {
//                    showTimeNode();
//                }
//            }
//        };
//    }
//
//    private void showTimeNode() {
//        if (mSelectCaseNodeEntity != null) {
//            if (mSelectCaseNodeEntity.getCASE_NODE() != null) {
//                if (mSelectCaseNodeEntity.getCASE_NODE().size() > 0) {
//                    BottomMenuShow bottomMenuShow = new BottomMenuShow(getContext());
//
//                    final ArrayList<MenuData> menuDatas = new ArrayList<>();
//                    for (int i = 0; i < mSelectCaseNodeEntity.getCASE_NODE().size(); i++) {
//                        MenuData menuData = new MenuData();
//                        menuData.setmSecondary(false);
//                        menuData.setmText(mSelectCaseNodeEntity.getCASE_NODE().get(i).getNAME());
//                        menuDatas.add(menuData);
//                    }
//
//                    MenuData menuData = new MenuData();
//                    menuData.setmSecondary(false);
//                    menuData.setmText("+新增时间节点");
//                    menuData.setAlter(true);
//                    menuDatas.add(menuData);
//
//                    bottomMenuShow.bottomMenuAlertDialog("", menuDatas);
//                    bottomMenuShow.setOnBottomMenuListener(new BottomMenuShow.OnBottomMenuListener() {
//                        @Override
//                        public void onClicklistener(MenuData data) {
//                            if (menuDatas.get(data.getId()).getmText().equals("+新增时间节点")) {
//                                NewNodeActivity.start(getContext(), mCaseBaseId);
//                            } else {
//                                mTimeNodeId = mSelectCaseNodeEntity.getCASE_NODE().get(data.getId()).getNODE_ID();
//                                mDataBinding.tvCaseBaseTimeNode.setText(mSelectCaseNodeEntity.getCASE_NODE().get(data.getId()).getNAME());
//                            }
//                        }
//                    });
//                } else {
//                    selectCaseNode();
//                }
//            } else {
//                selectCaseNode();
//            }
//        }
//    }
//
//    private void submit() {
//        List<UploadImage> uploadAImages = getUploadImageList();
//        List<CaseBasePhotoBean> list = new ArrayList<>();
//        for (int i = 0; i < uploadAImages.size(); i++) {
//            CaseBasePhotoBean caseBasePhotoBean = new CaseBasePhotoBean();
//            caseBasePhotoBean.setCatalogues(uploadAImages.get(i).getCatalogues());
//            caseBasePhotoBean.setAllfilePath(uploadAImages.get(i).getAllfilePath());
//            list.add(caseBasePhotoBean);
//        }
//        if(list.size()==0){
//            ToastUtils.show(getContext(),"请选择图片");
//            return;
//        }else {
//            String jsonString = new Gson().toJson(list).toString();
//            onNewRequestCall(SaveNewCasePhotoAction.newInstance(getContext(),
//                    mCaseBaseId,
//                    mTimeNodeId + "",
//                    jsonString).request(new AHttpService.IResCallback<BaseEntity>() {
//                @Override
//                public void onCallback(int resultCode, Response<BaseEntity> response, BaseErrorInfo baseErrorInfo) {
//                    if (getView() != null) {
//                        if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
//                            return;
//                        }
//                        closeDialog();
//                        if (response != null) {
//                            new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
//                            if (response.body().getCode().equals("200")) {
//                                getActivity().finish();
//                                EventBusUtil.post(new CaseBasePhotoEvent(CaseBasePhotoEvent.TYPE_REFRESH));
//                            } else {
//                                ToastUtils.show(getContext(), response.body().getMessage());
//                            }
//                        }
//                    }
//                }
//            }));
//        }
//    }
//
//    private void selectCaseNode() {
//        showDialog();
//        onNewRequestCall(SelectCaseNodeAction.newInstance(getContext(), mCaseBaseId).request(new AHttpService.IResCallback<BaseEntity<SelectCaseNodeEntity>>() {
//            @Override
//            public void onCallback(int resultCode, Response<BaseEntity<SelectCaseNodeEntity>> response, BaseErrorInfo baseErrorInfo) {
//                if (getView() != null) {
//                    if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
//                        return;
//                    }
//                    closeDialog();
//                    if (response != null) {
//                        new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
//                        if (response.body().getCode().equals("200")) {
//                            mSelectCaseNodeEntity = response.body().getResult();
//                            if (mSelectCaseNodeEntity.getCASE_NODE() != null) {
//                                if (StringUtils.isNullEmpty(mTimeNodeId)) {
//                                    mTimeNodeId = mSelectCaseNodeEntity.getCASE_NODE().get(0).getNODE_ID();
//                                    mDataBinding.tvCaseBaseTimeNode.setText(mSelectCaseNodeEntity.getCASE_NODE().get(0).getNAME());
//                                }
//                                if (mSelectCaseNodeEntity.getCASE_NODE().size() > 0) {
//                                    for (int i = 0; i < mSelectCaseNodeEntity.getCASE_NODE().size(); i++) {
//                                        if (mSelectCaseNodeEntity.getCASE_NODE().get(i).getNODE_ID().equals(mTimeNodeId)) {
//                                            mTimeNodeId = mSelectCaseNodeEntity.getCASE_NODE().get(i).getNODE_ID();
//                                            mDataBinding.tvCaseBaseTimeNode.setText(mSelectCaseNodeEntity.getCASE_NODE().get(i).getNAME());
//                                        }
//                                    }
//
//                                }
//
//                                if (!StringUtils.isNullEmpty(mNodeName)) {
//                                    for (int i = 0; i < mSelectCaseNodeEntity.getCASE_NODE().size(); i++) {
//                                        if (mSelectCaseNodeEntity.getCASE_NODE().get(i).getNAME().equals(mNodeName)) {
//                                            mTimeNodeId = mSelectCaseNodeEntity.getCASE_NODE().get(i).getNODE_ID();
//                                            mDataBinding.tvCaseBaseTimeNode.setText(mSelectCaseNodeEntity.getCASE_NODE().get(i).getNAME());
//                                        }
//                                    }
//                                }
//                            }
//                        } else {
//                            ToastUtils.show(getContext(), response.body().getMessage());
//                        }
//                    }
//                }
//            }
//        }));
//    }
//
//}
