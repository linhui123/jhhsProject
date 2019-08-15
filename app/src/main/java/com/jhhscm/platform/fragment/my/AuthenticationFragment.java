package com.jhhscm.platform.fragment.my;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.jhhscm.platform.MyApplication;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.LoginActivity;
import com.jhhscm.platform.bean.LogingResultBean;
import com.jhhscm.platform.bean.UploadImage;
import com.jhhscm.platform.databinding.FragmentAuthenticationBinding;
import com.jhhscm.platform.databinding.FragmentLoginBinding;
import com.jhhscm.platform.fragment.Mechanics.push.OldMechanicsUpImageBean;
import com.jhhscm.platform.fragment.Mechanics.push.SaveOldGoodAction;
import com.jhhscm.platform.fragment.Mechanics.push.UpdateImageBean;
import com.jhhscm.platform.fragment.Mechanics.push.UploadOldMechanicsImgAction;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.action.GetCodeAction;
import com.jhhscm.platform.http.action.GetUserAction;
import com.jhhscm.platform.http.action.LoginAction;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.ResultBean;
import com.jhhscm.platform.http.bean.UserBean;
import com.jhhscm.platform.http.bean.UserSession;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.http.sign.SignObject;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.DisplayUtils;
import com.jhhscm.platform.tool.StringUtils;
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.tool.ToastUtils;

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

public class AuthenticationFragment extends AbsFragment<FragmentAuthenticationBinding> {
    private boolean updateImgResult;
    private UserSession userSession;

    public static AuthenticationFragment instance() {
        AuthenticationFragment view = new AuthenticationFragment();
        return view;
    }

    @Override
    protected FragmentAuthenticationBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentAuthenticationBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        if (ConfigUtils.getCurrentUser(getContext()) != null
                && ConfigUtils.getCurrentUser(getContext()).getMobile() != null) {
            userSession = ConfigUtils.getCurrentUser(getContext());
        } else {
            startNewActivity(LoginActivity.class);
        }

        mDataBinding.tvTijiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDataBinding.etId.getText().toString().length() > 0
                        && mDataBinding.etUser.getText().toString().length() > 0
                        && mDataBinding.selector.getUploadImageList().size() == 2) {
                    ToastUtil.show(getContext(), "测试认证");
                } else {
                    ToastUtil.show(getContext(), "请输入完整信息");
                }
            }
        });
    }

    /**
     * 二手车上传图片
     */
    private List<UpdateImageBean> updateImageBeanList1;

    protected void doUploadAImagesAction1() {
        showDialog();
        boolean hasImageAToken = doUploadImagesAction1();
        if (hasImageAToken) {
            doHasImageTokenSuccess1();
        }
    }

    private boolean doUploadImagesAction1() {
        List<UploadImage> uploadImages = mDataBinding.selector.getUploadImageList();
        if (uploadImages == null) return true;
        boolean hasImageToken = true;
        for (int i = 0; i < uploadImages.size(); i++) {
            UploadImage image = uploadImages.get(i);
            if (StringUtils.isNullEmpty(image.getImageToken())) {
                hasImageToken = false;
                imageFile(getContext(), image.getImageUrl());
            }
        }
        return hasImageToken;
    }

    public void imageFile(Context context, final String imagePath) {
        final long UPLOAD_IMAGE_SIZE_LIMIT = 1024 * 1024;//1M
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
                                doUploadImageAction1(file, imagePath);
                            }
                        });    //启动压缩
                return;
            } else {
                doUploadImageAction1(imageFile, imagePath);
                return;
            }
        } catch (Throwable e) {
            ToastUtils.show(getContext(), "上传失败");
            return;
        }
    }

    private void doUploadImageAction1(final File file, final String imageUrl) {
        showDialog();
        String token = ConfigUtils.getCurrentUser(getContext()).getToken();
        onNewRequestCall(UploadOldMechanicsImgAction.newInstance(getContext(), file, token).
                request(new AHttpService.IResCallback<OldMechanicsUpImageBean>() {
                    @Override
                    public void onCallback(int resultCode, Response<OldMechanicsUpImageBean> response, BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            closeDialog();
                            if (response != null) {
                                if (response.body().getErrno().equals("0")) {
                                    if ("0".equals(response.body().getData().getCode())) {
                                        doUploadImageResponse1(imageUrl, response.body());
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

    private void doUploadImageResponse1(String imageUrl, OldMechanicsUpImageBean response) {
        mDataBinding.selector.setImageToken(new UploadImage(imageUrl, response.getData().getCatalogues(), response.getData().getAllfilePath(), response.getData().getCatalogues()));
        doHasImageTokenSuccess1();
    }

    protected List<String> getImageTokenList1() {
        List<String> imageTokens = new ArrayList<>();
        List<UploadImage> uploadImages = mDataBinding.selector.getUploadImageList();
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
    private void doHasImageTokenSuccess1() {
        List<UploadImage> uploadAImages = mDataBinding.selector.getUploadImageList();
        List<String> imageATokens = getImageTokenList1();
        if (uploadAImages != null && uploadAImages.size() > 0) {
            if (imageATokens != null && imageATokens.size() > 0) {
                if (uploadAImages.size() == imageATokens.size()) {
                    updateImageBeanList1.clear();
                    for (int i = 0; i < uploadAImages.size(); i++) {
                        UpdateImageBean updateImageBean = new UpdateImageBean();
                        updateImageBean.setIMG_URL(uploadAImages.get(i).getAllfilePath());
                        updateImageBean.setMENU_CATALOGUES(uploadAImages.get(i).getCatalogues());
                        updateImageBean.setPATIENT_IMAGE_NODE("1");
                        updateImageBeanList1.add(updateImageBean);
                    }
                    if (updateImgResult) {
                        saveOldGood();
                    } else {
                        ToastUtils.show(getContext(), "图片上传失败,请重新提交");
                    }
                } else {
                    ToastUtils.show(getContext(), R.string.error_net);
                    closeDialog();
                    return;
                }
            } else {
                return;
            }
        }
    }

    /**
     * 发布二手机
     */
    private void saveOldGood() {
        if (getContext() != null) {
            showDialog();
            String jsonString1 = "";
            for (UpdateImageBean updateImageBean : updateImageBeanList1) {
                if (jsonString1.length() > 0) {
                    jsonString1 = jsonString1 + "," + "\"" + updateImageBean.getIMG_URL() + "\"";
                } else {
                    jsonString1 = "\"" + updateImageBean.getIMG_URL() + "\"";
                }
            }
            Map<String, Object> map = new TreeMap<String, Object>();
            map.put("pic_gallery_url_list", "[" + jsonString1 + "]");
            String content = JSON.toJSONString(map);
            Log.e("saveOldGood", "content: " + content);
            content = Des.encryptByDes(content);
            String sign = SignObject.getSignKey(getActivity(), map, "saveOldGood");
            NetBean netBean = new NetBean();
            netBean.setToken(userSession.getToken());
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(SaveOldGoodAction.newInstance(getContext(), netBean)
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
                                        ToastUtils.show(getContext(), "二手机发布成功");
                                        getActivity().finish();
                                    } else {
                                        ToastUtils.show(getContext(), response.body().getMessage());
                                    }
                                }
                            }
                        }
                    }));
        }
    }
}