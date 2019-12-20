package com.jhhscm.platform.tool;

import android.content.Context;
import android.util.Log;

import com.jhhscm.platform.MyApplication;
import com.jhhscm.platform.R;
import com.jhhscm.platform.bean.UploadImage;
import com.jhhscm.platform.event.ImageUpdataEvent;
import com.jhhscm.platform.fragment.Mechanics.push.OldMechanicsUpImageBean;
import com.jhhscm.platform.fragment.Mechanics.push.UpdateImageBean;
import com.jhhscm.platform.fragment.Mechanics.push.UploadOldMechanicsImgAction;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.my.store.UploadBusorderImgAction;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.views.selector.ImageSelector;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import top.zibin.luban.Luban;

public class ImageUpdataUtil {

    private ImageSelector imageSelector;
    private Context context;
    private AbsFragment fragment;
    private boolean updateImgResult = true;
    /**
     * 二手车上传图片
     */
    private List<UpdateImageBean> updateImageBeanList = new ArrayList<>();

    public ImageUpdataUtil(Context context, AbsFragment fragment, ImageSelector imageSelector) {
        this.imageSelector = imageSelector;
        this.context = context;
        this.fragment = fragment;
        doUploadAImagesAction();
    }

    public void doUploadAImagesAction() {
        boolean hasImageAToken = doUploadImagesAction();
        if (hasImageAToken) {
            doHasImageTokenSuccess();
        }
    }

    private boolean doUploadImagesAction() {
        List<UploadImage> uploadImages = imageSelector.getUploadImageList();
        if (uploadImages == null) return true;
        boolean hasImageToken = true;
        for (int i = 0; i < uploadImages.size(); i++) {
            UploadImage image = uploadImages.get(i);
            if (StringUtils.isNullEmpty(image.getImageToken())) {
                hasImageToken = false;
                imageFile(context, image.getImageUrl());
            }
        }
        return hasImageToken;
    }

    public void imageFile(Context context, final String imagePath) {
        final long UPLOAD_IMAGE_SIZE_LIMIT = 100 * 1024;//1M  -100k
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
                                doUploadImageAction(file, imagePath);
                            }
                        });    //启动压缩
                return;
            } else {
                doUploadImageAction(imageFile, imagePath);
                return;
            }
        } catch (Throwable e) {
            ToastUtils.show(context, "上传失败");
            return;
        }
    }

    private void doUploadImageAction(final File file, final String imageUrl) {
        String token = ConfigUtils.getCurrentUser(context).getToken();
        fragment.onNewRequestCall(UploadBusorderImgAction.newInstance(context, file, token).
                request(new AHttpService.IResCallback<OldMechanicsUpImageBean>() {
                    @Override
                    public void onCallback(int resultCode, Response<OldMechanicsUpImageBean> response, BaseErrorInfo baseErrorInfo) {
                        if (context != null) {
                            if (new HttpHelper().showError(context, resultCode, baseErrorInfo, context.getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                if (response.body().getErrno().equals("0")) {
                                    if ("0".equals(response.body().getData().getCode())) {
                                        doUploadImageResponse(imageUrl, response.body());
                                    } else {
                                        updateImgResult = false;
                                        ToastUtils.show(context, response.body().getData().getMsg());
                                    }
                                } else {
                                    updateImgResult = false;
                                    ToastUtils.show(context, "图片上传失败");
                                }
                            }
                        }
                    }
                }));
    }

    private void doUploadImageResponse(String imageUrl, OldMechanicsUpImageBean response) {
        Log.e("ImageUpdataEvent", "imageUrl " + imageUrl);//本地地址
        imageSelector.setImageToken(new UploadImage(imageUrl, response.getData().getCatalogues(), response.getData().getAllfilePath(), response.getData().getCatalogues()));
        doHasImageTokenSuccess();
    }

    protected List<String> getImageTokenList() {
        List<String> imageTokens = new ArrayList<>();
        List<UploadImage> uploadImages = imageSelector.getUploadImageList();
        if (uploadImages == null) return imageTokens;
        for (int i = 0; i < uploadImages.size(); i++) {
            UploadImage image = uploadImages.get(i);
            Log.e("ImageUpdataEvent", "getImageToken " + image.getImageToken());
            if (!StringUtils.isNullEmpty(image.getImageToken())) {
                imageTokens.add(image.getImageToken());
            } else {
                Log.e("ImageUpdataEvent", "null ");
                return null;
            }
        }
        return imageTokens;
    }

    //判断是否图片已经上传完成
    private void doHasImageTokenSuccess() {
        List<UploadImage> uploadAImages = imageSelector.getUploadImageList();
        List<String> imageATokens = getImageTokenList();
        Log.e("ImageUpdataEvent", "imageATokens " + imageATokens);
        if (uploadAImages != null && uploadAImages.size() > 0) {
            if (imageATokens != null && imageATokens.size() > 0) {
                if (uploadAImages.size() == imageATokens.size()) {
                    updateImageBeanList.clear();
                    for (int i = 0; i < uploadAImages.size(); i++) {
                        if (uploadAImages.get(i).getImageUrl() != null && uploadAImages.get(i).getImageUrl().contains("http://")) {
                            UpdateImageBean updateImageBean = new UpdateImageBean();
                            updateImageBean.setIMG_URL(uploadAImages.get(i).getImageUrl());
                            updateImageBean.setPATIENT_IMAGE_NODE("1");
                            updateImageBeanList.add(updateImageBean);
                        } else {
                            UpdateImageBean updateImageBean = new UpdateImageBean();
                            updateImageBean.setIMG_URL(uploadAImages.get(i).getAllfilePath());
                            updateImageBean.setMENU_CATALOGUES(uploadAImages.get(i).getCatalogues());
                            updateImageBean.setPATIENT_IMAGE_NODE("1");
                            updateImageBeanList.add(updateImageBean);
                        }
                    }
                    if (updateImgResult) {
                        EventBusUtil.post(new ImageUpdataEvent(updateImageBeanList, 0));
                    } else {
                        Log.e("ImageUpdataEvent", "2");
                        ToastUtils.show(context, "图片上传失败,请重新提交");
                        EventBusUtil.post(new ImageUpdataEvent(updateImageBeanList, 0, false));
                    }
                } else {
                    Log.e("ImageUpdataEvent", "3");
//                    ToastUtils.show(context, "图片上传失败,请重新提交");
//                    EventBusUtil.post(new ImageUpdataEvent(updateImageBeanList, 0, false));
                    return;
                }
            } else {
                Log.e("ImageUpdataEvent", "4");
//                EventBusUtil.post(new ImageUpdataEvent(updateImageBeanList, 0, false));
                return;
            }
        } else {
            Log.e("ImageUpdataEvent", "5");
            EventBusUtil.post(new ImageUpdataEvent(updateImageBeanList, 0, false));
            return;
        }
    }
}
