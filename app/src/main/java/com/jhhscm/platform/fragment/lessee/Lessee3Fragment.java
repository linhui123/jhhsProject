package com.jhhscm.platform.fragment.lessee;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jhhscm.platform.MyApplication;
import com.jhhscm.platform.R;
import com.jhhscm.platform.bean.UploadImage;
import com.jhhscm.platform.databinding.FragmentLessee1Binding;
import com.jhhscm.platform.databinding.FragmentLessee3Binding;
import com.jhhscm.platform.event.LesseeFinishEvent;
import com.jhhscm.platform.fragment.Mechanics.push.OldMechanicsUpImageBean;
import com.jhhscm.platform.fragment.Mechanics.push.SaveOldGoodAction;
import com.jhhscm.platform.fragment.Mechanics.push.UpdateImageBean;
import com.jhhscm.platform.fragment.Mechanics.push.UploadOldMechanicsImgAction;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.labour.FindLabourReleaseListBean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.ResultBean;
import com.jhhscm.platform.http.bean.UserSession;
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
    private List<LesseeBean.WBankleaseFileListBean> itemsBeans;

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
        lesseeBean = (LesseeBean) getArguments().getSerializable("lesseeBean");
        itemsBeans = new ArrayList<>();
        itemsBeans.add(new LesseeBean.WBankleaseFileListBean());//人像面
        itemsBeans.add(new LesseeBean.WBankleaseFileListBean());//国徽面
        mDataBinding.tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    /**
     * 二手车上传图片
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
            wBankleaseFileListBean.setFileType("0");
            wBankleaseFileListBean.setFileUrl(updateImageBeanList.get(0).getIMG_URL());
            itemsBeans.set(0, wBankleaseFileListBean);
            //上传国徽面
            updateImgResult = true;
            doUploadAImagesAction(mDataBinding.isGuohui);
        } else if (imageSelector == mDataBinding.isGuohui) {
            LesseeBean.WBankleaseFileListBean wBankleaseFileListBean = new LesseeBean.WBankleaseFileListBean();
            wBankleaseFileListBean.setType(1);
            wBankleaseFileListBean.setFileType("0");
            wBankleaseFileListBean.setFileUrl(updateImageBeanList.get(0).getIMG_URL());
            itemsBeans.set(1, wBankleaseFileListBean);

            if (itemsBeans.get(0).getFileUrl() != null && itemsBeans.get(0).getFileUrl().length() > 0
                    && itemsBeans.get(1).getFileUrl() != null && itemsBeans.get(1).getFileUrl().length() > 0) {
                lesseeBean.setWBankleaseFileList(itemsBeans);
                //提交
                createLessee();
            } else {
                ToastUtil.show(getContext(), "网络错误");
            }
        }
    }

    /**
     * 提交 lease/create
     */
    private void createLessee() {
        if (getContext() != null) {
            showDialog();
            Map<String, Object> map = new TreeMap<String, Object>();
            map.put("wBankLeaseFileList", JSON.toJSONString(lesseeBean.getWBankleaseFileList()));
            map.put("wBankLeasePerson", JSON.toJSONString(lesseeBean.getWBankLeasePerson()));
            map.put("wBankLeaseItems", JSON.toJSONString(lesseeBean.getWBankLeaseItems()));
//            map.put("wBankLeaseFileList", lesseeBean.getWBankleaseFileList());
//            map.put("wBankLeasePerson", lesseeBean.getWBankLeasePerson());
//            map.put("wBankLeaseItems", lesseeBean.getWBankLeaseItems());
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
                                        ToastUtils.show(getContext(), "租赁信息提交成功");
                                        EventBusUtil.post(new LesseeFinishEvent());
                                        getActivity().finish();
                                    } else {
//                                        ToastUtils.show(getContext(), response.body().getMessage());
                                        ToastUtils.show(getContext(), "网络错误");
                                    }
                                }
                            }
                        }
                    }));
        }
    }

}
