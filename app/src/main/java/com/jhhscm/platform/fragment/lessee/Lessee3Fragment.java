package com.jhhscm.platform.fragment.lessee;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.BuildConfig;
import com.jhhscm.platform.MyApplication;
import com.jhhscm.platform.R;
import com.jhhscm.platform.bean.PbImage;
import com.jhhscm.platform.bean.UploadImage;
import com.jhhscm.platform.databinding.FragmentLessee3Binding;
import com.jhhscm.platform.event.ImageSelectorUpdataEvent;
import com.jhhscm.platform.event.LesseeFinishEvent;
import com.jhhscm.platform.fragment.Mechanics.push.OldMechanicsUpImageBean;
import com.jhhscm.platform.fragment.Mechanics.push.UpdateImageBean;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.ResultBean;
import com.jhhscm.platform.http.sign.SignObject;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.StringUtils;
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.selector.ImageSelector;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import top.zibin.luban.Luban;

public class Lessee3Fragment extends AbsFragment<FragmentLessee3Binding> {
    private LesseeBean lesseeBean;
    private LesseeBean lessee;
    private List<LesseeBean.WBankleaseFileListBean> itemsBeans;
    private LesseeBean.WBankLeaseSuretyBean suretyBean;
    private boolean isUpdateResult;//是否提交完整信息；false 只上传图片，不提交全信息

    public static Lessee3Fragment instance() {
        Lessee3Fragment view = new Lessee3Fragment();
        return view;
    }

    @Override
    protected FragmentLessee3Binding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentLessee3Binding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        EventBusUtil.registerEvent(this);
        lesseeBean = new LesseeBean();
        lesseeBean = (LesseeBean) getArguments().getSerializable("lesseeBean");
        itemsBeans = new ArrayList<>();
        suretyBean = new LesseeBean.WBankLeaseSuretyBean();
//        itemsBeans.add(new LesseeBean.WBankleaseFileListBean());//人像面
//        itemsBeans.add(new LesseeBean.WBankleaseFileListBean());//国徽面
        mDataBinding.tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isUpdateResult = true;
                suretyBean.setM1(mDataBinding.etEmergency.getText().toString().trim());
                suretyBean.setM2(mDataBinding.etEmergencyPhone.getText().toString().trim());
                suretyBean.setName(mDataBinding.etGuarantee.getText().toString().trim());
                suretyBean.setIdCard(mDataBinding.etGuaranteeId.getText().toString().trim());
                suretyBean.setPhone(mDataBinding.etGuaranteePhone.getText().toString().trim());
                lesseeBean.setwBankLeaseSurety(suretyBean);
                if (mDataBinding.isFace.getUploadImageList().size() > 0) {
                    if (mDataBinding.isGuohui.getUploadImageList().size() > 0) {
                        updateImgResult = true;
                        doUploadAImagesAction(mDataBinding.isFace);
                    } else {
                        ToastUtil.show(getActivity(), "请上传身份证国徽面");
                    }
                } else {
                    ToastUtil.show(getActivity(), "请上传身份证人面像");
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        //判断是否从我的设备进来；判断草稿箱是否有内容
        if (ConfigUtils.getLesseeData(getContext()) != null) {
            lesseeBean = ConfigUtils.getLesseeData(getContext());
            suretyBean = lesseeBean.getwBankLeaseSurety();
            itemsBeans = lesseeBean.getWBankleaseFileList();
            initData();
        } else {
            if (BuildConfig.DEBUG) {//测试数据
                mDataBinding.etEmergency.setText("111");
                mDataBinding.etEmergencyPhone.setText("111");
                mDataBinding.etGuarantee.setText("111");
                mDataBinding.etGuaranteeId.setText("111");
                mDataBinding.etGuaranteePhone.setText("111");
            }
        }
    }

    private void initData() {
        mDataBinding.etEmergency.setText(suretyBean.getM1());
        mDataBinding.etEmergencyPhone.setText(suretyBean.getM2());
        mDataBinding.etGuarantee.setText(suretyBean.getName());
        mDataBinding.etGuaranteeId.setText(suretyBean.getIdCard());
        mDataBinding.etGuaranteePhone.setText(suretyBean.getPhone());
//        List<PbImage> items1 = new ArrayList<>();
//        List<PbImage> items2 = new ArrayList<>();
//        List<PbImage> items3 = new ArrayList<>();
//        Log.e("initData", "itemsBeans " + itemsBeans.size());
//        for (LesseeBean.WBankleaseFileListBean image : itemsBeans) {
//            if (image.getState() == 1) {
//                List<PbImage> items = new ArrayList<>();
//                PbImage pbImage = new PbImage();
//                pbImage.setmUrl(image.getFileUrl());
//                pbImage.setmToken(image.getFileUrl());
//                items.add(pbImage);
//                mDataBinding.isFace.setPbImageList(items);
//            } else if (image.getState() == 2) {
//                List<PbImage> items = new ArrayList<>();
//                PbImage pbImage = new PbImage();
//                pbImage.setmUrl(image.getFileUrl());
//                pbImage.setmToken(image.getFileUrl());
//                items.add(pbImage);
//                mDataBinding.isGuohui.setPbImageList(items);
//            } else if (image.getState() == 3) {
//                PbImage pbImage = new PbImage();
//                pbImage.setmUrl(image.getFileUrl());
//                pbImage.setmToken(image.getFileUrl());
//                items1.add(pbImage);
//                mDataBinding.is1.setPbImageList(items1);
//            } else if (image.getState() == 4) {
//                PbImage pbImage = new PbImage();
//                pbImage.setmUrl(image.getFileUrl());
//                pbImage.setmToken(image.getFileUrl());
//                items2.add(pbImage);
//                mDataBinding.is2.setPbImageList(items2);
//            } else if (image.getState() == 5) {
//                PbImage pbImage = new PbImage();
//                pbImage.setmUrl(image.getFileUrl());
//                pbImage.setmToken(image.getFileUrl());
//                items3.add(pbImage);
//                mDataBinding.is3.setPbImageList(items3);
//            }
//    }

}

    @Override
    public void onPause() {
        super.onPause();
        if (ConfigUtils.getLesseeData(getContext()) != null) {
            lessee = ConfigUtils.getLesseeData(getContext());
            suretyBean.setPhone(mDataBinding.etGuaranteePhone.getText().toString().trim());
            suretyBean.setName(mDataBinding.etGuarantee.getText().toString().trim());
            suretyBean.setIdCard(mDataBinding.etGuaranteeId.getText().toString().trim());
            suretyBean.setM1(mDataBinding.etEmergency.getText().toString().trim());
            suretyBean.setM2(mDataBinding.etEmergencyPhone.getText().toString().trim());
            lesseeBean.setwBankLeaseSurety(suretyBean);
            lesseeBean.setWBankleaseFileList(itemsBeans);
            ConfigUtils.setLesseeData(getContext(), lessee);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }

    public void onEvent(ImageSelectorUpdataEvent event) {
        if (event.getImageSelector() != null) {
            isUpdateResult = false;
            if (event.getImageSelector() == mDataBinding.is1) {
                updateImgResult = true;
                doUploadAImagesAction(mDataBinding.is1);
            } else if (event.getImageSelector() == mDataBinding.is2) {
                updateImgResult = true;
                doUploadAImagesAction(mDataBinding.is2);
            } else if (event.getImageSelector() == mDataBinding.is3) {
                updateImgResult = true;
                doUploadAImagesAction(mDataBinding.is3);
            }
        }
    }

    /**
     * 上传身份证照片
     */
    private List<UpdateImageBean> updateImageBeanList1 = new ArrayList<>();
    private boolean updateImgResult;

    protected void doUploadAImagesAction(SingleImageSelector imageSelector) {
        showDialog();
        boolean hasImageAToken = doUploadImagesAction(imageSelector);
        if (hasImageAToken) {
            doHasImageTokenSuccess(imageSelector);
        }
    }

    private boolean doUploadImagesAction(SingleImageSelector imageSelector) {
        List<UploadImage> uploadImages = imageSelector.getUploadImageList();
        if (uploadImages == null) return true;
        boolean hasImageToken = true;
        for (int i = 0; i < uploadImages.size(); i++) {
            UploadImage image = uploadImages.get(i);
            if (StringUtils.isNullEmpty(image.getImageToken())) {
                hasImageToken = false;
                imageFile(imageSelector, getContext(), image.getImageUrl());
            }
        }
        return hasImageToken;
    }

    public void imageFile(final SingleImageSelector imageSelector, Context context, final String imagePath) {
        final long UPLOAD_IMAGE_SIZE_LIMIT = 100 * 1024;//1M
        File imageFile = null;
        try {
            imageFile = new File(new URI(imagePath));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        if (imageFile == null || !imageFile.exists()) {
            ToastUtils.show(context, "上传文件不存在！");
            return;
        }
        try {
            //1M以上图片进行压缩处理
            Log.e("imageFile", "imageFile.length() " + imageFile.length());
            if (imageFile.length() > UPLOAD_IMAGE_SIZE_LIMIT) {
                Luban.get(MyApplication.getInstance())
                        .load(imageFile)
                        .putGear(Luban.THIRD_GEAR)
                        .setFilename(imageFile.getAbsolutePath())
                        .asObservable()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnError(new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        })
                        .onErrorResumeNext(new Func1<Throwable, Observable<? extends File>>() {
                            @Override
                            public Observable<? extends File> call(Throwable throwable) {
                                return Observable.empty();
                            }
                        })
                        .subscribe(new Action1<File>() {
                            @Override
                            public void call(File file) {
                                // TODO 压缩成功后调用，返回压缩后的图片文件
//                                return file;
//                                return photos;
                                Log.e("file", "file.length() " + file.length());
                                doUploadImageAction(imageSelector, file, imagePath);
                            }
                        });    //启动压缩
                return;
            } else {
                doUploadImageAction(imageSelector, imageFile, imagePath);
                return;
            }
        } catch (Throwable e) {
            closeDialog();
            e.printStackTrace();
            ToastUtils.show(getContext(), "图片上传失败");
            return;
        }
    }

    private void doUploadImageAction(final SingleImageSelector imageSelector, final File file, final String imageUrl) {
        showDialog();
        String token = "";
        if (ConfigUtils.getCurrentUser(getContext()) != null) {
            token = ConfigUtils.getCurrentUser(getContext()).getToken();
        }
        onNewRequestCall(UploadLesseeImgAction.newInstance(getContext(), file, token).
                request(new AHttpService.IResCallback<OldMechanicsUpImageBean>() {
                    @Override
                    public void onCallback(int resultCode, Response<OldMechanicsUpImageBean> response, BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                if (response.body().getCode().equals("200")) {
                                    if ("0".equals(response.body().getData().getCode())) {
//                                        Log.e("UploadLesseeImgAction", "response : " + response.toString());
                                        doUploadImageResponse(imageSelector, imageUrl, response.body());
                                    } else {
                                        updateImgResult = false;
                                        ToastUtils.show(getContext(), response.body().getData().getMsg());
                                    }
                                } else {
                                    updateImgResult = false;
                                    ToastUtils.show(getContext(), "图片上传失败");
                                }
                            }
                        }
                    }
                }));
    }

    private void doUploadImageResponse(SingleImageSelector imageSelector, String imageUrl, OldMechanicsUpImageBean response) {
        imageSelector.setImageToken(new UploadImage(imageUrl, response.getData().getCatalogues(), response.getData().getAllfilePath(), response.getData().getCatalogues()));
        doHasImageTokenSuccess(imageSelector);
    }

    protected List<String> getImageTokenList(SingleImageSelector imageSelector) {
        List<String> imageTokens = new ArrayList<>();
        List<UploadImage> uploadImages = imageSelector.getUploadImageList();
        if (uploadImages == null) return imageTokens;
        for (int i = 0; i < uploadImages.size(); i++) {
            UploadImage image = uploadImages.get(i);
            if (!StringUtils.isNullEmpty(image.getImageToken())) {
                imageTokens.add(image.getImageToken());
            } else {
                return null;
            }
        }
        return imageTokens;
    }

    //判断是否图片已经上传完成
    private void doHasImageTokenSuccess(SingleImageSelector imageSelector) {
        List<UploadImage> uploadAImages = imageSelector.getUploadImageList();
        List<String> imageATokens = getImageTokenList(imageSelector);
        if (uploadAImages != null && uploadAImages.size() > 0) {
            if (imageATokens != null && imageATokens.size() > 0) {
                if (uploadAImages.size() == imageATokens.size()) {
                    updateImageBeanList1.clear();
                    for (int i = 0; i < uploadAImages.size(); i++) {
                        UpdateImageBean updateImageBean = new UpdateImageBean();
                        updateImageBean.setIMG_URL(uploadAImages.get(i).getAllfilePath());
                        updateImageBean.setMENU_CATALOGUES(uploadAImages.get(i).getCatalogues());
                        updateImageBean.setPATIENT_IMAGE_NODE("0");
                        updateImageBeanList1.add(updateImageBean);
                    }
                    if (updateImgResult) {
                        saveImagesData(imageSelector, updateImageBeanList1);
                    } else {
                        ToastUtils.show(getContext(), "图片上传失败,请重新提交");
                    }
                } else {
//                    error();
                    closeDialog();
                    return;
                }
            } else {
                return;
            }
        }
    }

    private void saveImagesData(SingleImageSelector imageSelector, List<UpdateImageBean> updateImageBeanList) {
        if (imageSelector == mDataBinding.isFace) {
            LesseeBean.WBankleaseFileListBean wBankleaseFileListBean = new LesseeBean.WBankleaseFileListBean();
            wBankleaseFileListBean.setType(1);
            wBankleaseFileListBean.setState(1);
            wBankleaseFileListBean.setFileType("0");
            wBankleaseFileListBean.setFileUrl(updateImageBeanList.get(0).getIMG_URL());
            itemsBeans.add(wBankleaseFileListBean);
            //上传国徽面
            updateImgResult = true;
            doUploadAImagesAction(mDataBinding.isGuohui);
        } else if (imageSelector == mDataBinding.isGuohui) {
            LesseeBean.WBankleaseFileListBean wBankleaseFileListBean = new LesseeBean.WBankleaseFileListBean();
            wBankleaseFileListBean.setType(1);
            wBankleaseFileListBean.setState(2);
            wBankleaseFileListBean.setFileType("0");
            wBankleaseFileListBean.setFileUrl(updateImageBeanList.get(0).getIMG_URL());
            itemsBeans.add(wBankleaseFileListBean);

            if (itemsBeans.get(0).getFileUrl() != null && itemsBeans.get(0).getFileUrl().length() > 0
                    && itemsBeans.get(1).getFileUrl() != null && itemsBeans.get(1).getFileUrl().length() > 0) {
                lesseeBean.setWBankleaseFileList(itemsBeans);
                //提交其他照片
                updataImages();
            } else {
                ToastUtil.show(getContext(), "网络错误");
            }
        }
    }

    private void updataImages() {
        //上传财产图片
        if (mDataBinding.is1.getUploadImageList().size() > 0) {
            doUploadAImagesAction(mDataBinding.is1);
        }
        //上传无产权图片
        else if (mDataBinding.is2.getUploadImageList().size() > 0) {
            doUploadAImagesAction(mDataBinding.is2);
        }
        //上传征信报告图片
        else if (mDataBinding.is3.getUploadImageList().size() > 0) {
            doUploadAImagesAction(mDataBinding.is3);
        } else {
            createLessee();
        }
    }

    private List<UpdateImageBean> updateImageBeanList2 = new ArrayList<>();
    private List<UpdateImageBean> updateImageBeanList3 = new ArrayList<>();
    private List<UpdateImageBean> updateImageBeanList4 = new ArrayList<>();

    protected void doUploadAImagesAction(ImageSelector imageSelector) {
        showDialog();
        boolean hasImageAToken = doUploadImagesAction(imageSelector);
        if (hasImageAToken) {
            doHasImageTokenSuccess(imageSelector);
        }
    }

    private boolean doUploadImagesAction(ImageSelector imageSelector) {
        List<UploadImage> uploadImages = imageSelector.getUploadImageList();
        if (uploadImages == null) return true;
        boolean hasImageToken = true;
        for (int i = 0; i < uploadImages.size(); i++) {
            UploadImage image = uploadImages.get(i);
            if (StringUtils.isNullEmpty(image.getImageToken())) {
                hasImageToken = false;
                imageFile(imageSelector, getContext(), image.getImageUrl());
            }
        }
        return hasImageToken;
    }

    public void imageFile(final ImageSelector imageSelector, Context context, final String imagePath) {
        final long UPLOAD_IMAGE_SIZE_LIMIT = 100 * 1024;//1M
        File imageFile = null;
        try {
            imageFile = new File(new URI(imagePath));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        if (imageFile == null || !imageFile.exists()) {
            ToastUtils.show(context, "上传文件不存在！");
            return;
        }
        try {
            //1M以上图片进行压缩处理
            Log.e("imageFile", "imageFile.length() " + imageFile.length());
            if (imageFile.length() > UPLOAD_IMAGE_SIZE_LIMIT) {
                Luban.get(MyApplication.getInstance())
                        .load(imageFile)
                        .putGear(Luban.THIRD_GEAR)
                        .setFilename(imageFile.getAbsolutePath())
                        .asObservable()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnError(new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        })
                        .onErrorResumeNext(new Func1<Throwable, Observable<? extends File>>() {
                            @Override
                            public Observable<? extends File> call(Throwable throwable) {
                                return Observable.empty();
                            }
                        })
                        .subscribe(new Action1<File>() {
                            @Override
                            public void call(File file) {
                                // TODO 压缩成功后调用，返回压缩后的图片文件
                                Log.e("file", "file.length() " + file.length());
                                doUploadImageAction(imageSelector, file, imagePath);
                            }
                        });
                return;
            } else {
                doUploadImageAction(imageSelector, imageFile, imagePath);
                return;
            }
        } catch (Throwable e) {
            closeDialog();
            e.printStackTrace();
            ToastUtils.show(getContext(), "图片上传失败");
            return;
        }
    }

    private void doUploadImageAction(final ImageSelector imageSelector, final File file, final String imageUrl) {
        showDialog();
        String token = "";
        if (ConfigUtils.getCurrentUser(getContext()) != null) {
            token = ConfigUtils.getCurrentUser(getContext()).getToken();
        }
        onNewRequestCall(UploadLesseeImgAction.newInstance(getContext(), file, token).
                request(new AHttpService.IResCallback<OldMechanicsUpImageBean>() {
                    @Override
                    public void onCallback(int resultCode, Response<OldMechanicsUpImageBean> response, BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                if (response.body().getCode().equals("200")) {
                                    if ("0".equals(response.body().getData().getCode())) {
                                        doUploadImageResponse(imageSelector, imageUrl, response.body());
                                    } else {
                                        ToastUtils.show(getContext(), response.body().getData().getMsg());
                                    }
                                } else {
                                    ToastUtils.show(getContext(), "图片上传失败");
                                }
                            }
                        }
                    }
                }));
    }

    private void doUploadImageResponse(ImageSelector imageSelector, String imageUrl, OldMechanicsUpImageBean response) {
        imageSelector.setImageToken(new UploadImage(imageUrl, response.getData().getCatalogues(), response.getData().getAllfilePath(), response.getData().getCatalogues()));
        doHasImageTokenSuccess(imageSelector);
    }

    protected List<String> getImageTokenList(ImageSelector imageSelector) {
        List<String> imageTokens = new ArrayList<>();
        List<UploadImage> uploadImages = imageSelector.getUploadImageList();
        if (uploadImages == null) return imageTokens;
        for (int i = 0; i < uploadImages.size(); i++) {
            UploadImage image = uploadImages.get(i);
            if (!StringUtils.isNullEmpty(image.getImageToken())) {
                imageTokens.add(image.getImageToken());
            } else {
                return null;
            }
        }
        return imageTokens;
    }

    //判断是否图片已经上传完成
    private void doHasImageTokenSuccess(ImageSelector imageSelector) {
        List<UploadImage> uploadAImages = imageSelector.getUploadImageList();
        List<String> imageATokens = getImageTokenList(imageSelector);
        if (uploadAImages != null && uploadAImages.size() > 0) {
            if (imageATokens != null && imageATokens.size() > 0) {
                if (uploadAImages.size() == imageATokens.size()) {
                    if (imageSelector == mDataBinding.is1) {
                        updateImageBeanList2.clear();
                        for (int i = 0; i < uploadAImages.size(); i++) {
                            if (uploadAImages.get(i).getImageUrl() != null && uploadAImages.get(i).getImageUrl().contains("http://")) {
                                UpdateImageBean updateImageBean = new UpdateImageBean();
                                updateImageBean.setIMG_URL(uploadAImages.get(i).getImageUrl());
                                updateImageBean.setPATIENT_IMAGE_NODE("0");
                                updateImageBeanList2.add(updateImageBean);
                            } else {
                                UpdateImageBean updateImageBean = new UpdateImageBean();
                                updateImageBean.setIMG_URL(uploadAImages.get(i).getAllfilePath());
                                updateImageBean.setMENU_CATALOGUES(uploadAImages.get(i).getCatalogues());
                                updateImageBean.setPATIENT_IMAGE_NODE("0");
                                updateImageBeanList2.add(updateImageBean);
                            }
                        }
                        saveImagesData(imageSelector, updateImageBeanList2);
                    } else if (imageSelector == mDataBinding.is2) {
                        updateImageBeanList3.clear();
                        for (int i = 0; i < uploadAImages.size(); i++) {
                            if (uploadAImages.get(i).getImageUrl() != null && uploadAImages.get(i).getImageUrl().contains("http://")) {
                                UpdateImageBean updateImageBean = new UpdateImageBean();
                                updateImageBean.setIMG_URL(uploadAImages.get(i).getImageUrl());
                                updateImageBean.setPATIENT_IMAGE_NODE("0");
                                updateImageBeanList3.add(updateImageBean);
                            } else {
                                UpdateImageBean updateImageBean = new UpdateImageBean();
                                updateImageBean.setIMG_URL(uploadAImages.get(i).getAllfilePath());
                                updateImageBean.setMENU_CATALOGUES(uploadAImages.get(i).getCatalogues());
                                updateImageBean.setPATIENT_IMAGE_NODE("0");
                                updateImageBeanList3.add(updateImageBean);
                            }
                        }
                        saveImagesData(imageSelector, updateImageBeanList3);
                    } else if (imageSelector == mDataBinding.is3) {
                        updateImageBeanList4.clear();
                        for (int i = 0; i < uploadAImages.size(); i++) {
                            if (uploadAImages.get(i).getImageUrl() != null && uploadAImages.get(i).getImageUrl().contains("http://")) {
                                UpdateImageBean updateImageBean = new UpdateImageBean();
                                updateImageBean.setIMG_URL(uploadAImages.get(i).getImageUrl());
                                updateImageBean.setPATIENT_IMAGE_NODE("0");
                                updateImageBeanList4.add(updateImageBean);
                            } else {
                                UpdateImageBean updateImageBean = new UpdateImageBean();
                                updateImageBean.setIMG_URL(uploadAImages.get(i).getAllfilePath());
                                updateImageBean.setMENU_CATALOGUES(uploadAImages.get(i).getCatalogues());
                                updateImageBean.setPATIENT_IMAGE_NODE("0");
                                updateImageBeanList4.add(updateImageBean);
                            }
                        }
                        saveImagesData(imageSelector, updateImageBeanList4);
                    }
                } else {
                    closeDialog();
                    return;
                }
            } else {
                closeDialog();
                return;
            }
        }
    }

    private void saveImagesData(ImageSelector imageSelector, List<UpdateImageBean> updateImageBeanList) {
        if (imageSelector == mDataBinding.is1) {//财产
            LesseeBean.WBankleaseFileListBean wBankleaseFileListBean = new LesseeBean.WBankleaseFileListBean();
            wBankleaseFileListBean.setType(1);
            wBankleaseFileListBean.setState(3);
            wBankleaseFileListBean.setFileType("5");
            wBankleaseFileListBean.setFileUrl(updateImageBeanList.get(0).getIMG_URL());
            itemsBeans.add(wBankleaseFileListBean);
            updateImgResult = true;
            if (mDataBinding.is2.getUploadImageList().size() > 0) {
                doUploadAImagesAction(mDataBinding.is2);
            } else if (mDataBinding.is3.getUploadImageList().size() > 0) {
                doUploadAImagesAction(mDataBinding.is3);
            } else {
                lesseeBean.setWBankleaseFileList(itemsBeans);
                //提交
                createLessee();
            }
        } else if (imageSelector == mDataBinding.is2) {//物产
            LesseeBean.WBankleaseFileListBean wBankleaseFileListBean = new LesseeBean.WBankleaseFileListBean();
            wBankleaseFileListBean.setType(1);
            wBankleaseFileListBean.setState(4);
            wBankleaseFileListBean.setFileType("3");
            wBankleaseFileListBean.setFileUrl(updateImageBeanList.get(0).getIMG_URL());
            itemsBeans.add(wBankleaseFileListBean);
            Log.e("saveImagesData", "itemsBeans " + itemsBeans.size());
            if (mDataBinding.is3.getUploadImageList().size() > 0) {
                doUploadAImagesAction(mDataBinding.is3);
            } else {
                lesseeBean.setWBankleaseFileList(itemsBeans);
                //提交
                createLessee();
            }
        } else if (imageSelector == mDataBinding.is3) {//征信
            LesseeBean.WBankleaseFileListBean wBankleaseFileListBean = new LesseeBean.WBankleaseFileListBean();
            wBankleaseFileListBean.setType(1);
            wBankleaseFileListBean.setState(5);
            wBankleaseFileListBean.setFileType("7");
            wBankleaseFileListBean.setFileUrl(updateImageBeanList.get(0).getIMG_URL());
            itemsBeans.add(wBankleaseFileListBean);
            lesseeBean.setWBankleaseFileList(itemsBeans);
            //提交
            createLessee();
        }
    }

    /**
     * 提交 lease/create
     */
    private void createLessee() {
        if (getContext() != null && isUpdateResult) {
            showDialog();
            Map<String, Object> map = new TreeMap<String, Object>();
            map.put("wBankLeaseFileList", JSON.toJSONString(lesseeBean.getWBankleaseFileList()));
            map.put("wBankLeasePerson", JSON.toJSONString(lesseeBean.getWBankLeasePerson()));
            map.put("wBankLeaseItems", JSON.toJSONString(lesseeBean.getWBankLeaseItems()));
            map.put("wBankLeaseSurety", JSON.toJSONString(lesseeBean.getwBankLeaseSurety()));
            String content = JSON.toJSONString(map);
            Log.e("createLessee", "content : " + content);
            content = Des.encryptByDes(content);
            String sign = SignObject.getSignKey(getActivity(), map, "lease/create");
            NetBean netBean = new NetBean();
            netBean.setToken("");
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(CreateLeaseAction.newInstance(getContext(), netBean)
                    .request(new AHttpService.IResCallback<BaseEntity<ResultBean>>() {
                        @Override
                        public void onCallback(int resultCode, Response<BaseEntity<ResultBean>> response,
                                               BaseErrorInfo baseErrorInfo) {
                            if (getView() != null) {
                                closeDialog();
                                if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                    return;
                                }
                                if (response != null) {
                                    new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                    if (response.body().getCode().equals("200")) {
                                        EventBusUtil.post(new LesseeFinishEvent());
                                        getActivity().finish();
                                    } else {
                                        ToastUtils.show(getContext(), "网络错误");
                                    }
                                }
                            }
                        }
                    }));
        }
    }
}
