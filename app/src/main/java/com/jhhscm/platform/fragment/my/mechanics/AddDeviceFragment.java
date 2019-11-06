package com.jhhscm.platform.fragment.my.mechanics;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.MyApplication;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.TraceReloadActivity;
import com.jhhscm.platform.bean.UploadImage;
import com.jhhscm.platform.databinding.FragmentAddDeviceBinding;
import com.jhhscm.platform.databinding.FragmentMyMechanicsBinding;
import com.jhhscm.platform.fragment.Mechanics.push.OldMechanicsUpImageBean;
import com.jhhscm.platform.fragment.Mechanics.push.UpdateImageBean;
import com.jhhscm.platform.fragment.Mechanics.push.UploadOldMechanicsImgAction;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.ResultBean;
import com.jhhscm.platform.http.bean.UserSession;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.StringUtils;
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.timePickets.TimePickerShow;

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

public class AddDeviceFragment extends AbsFragment<FragmentAddDeviceBinding> {
    private int type;
    private boolean updateImgResult;
    /**
     * 二手车上传图片
     */
    private List<UpdateImageBean> updateImageBeanList1;

    public static AddDeviceFragment instance() {
        AddDeviceFragment view = new AddDeviceFragment();
        return view;
    }

    @Override
    protected FragmentAddDeviceBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentAddDeviceBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        type = getArguments().getInt("type", 0);
        updateImageBeanList1 = new ArrayList<>();
        mDataBinding.data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerShow timePickerShow = new TimePickerShow(getContext());
                timePickerShow.timePickerAlertDialog("");
                timePickerShow.setOnTimePickerListener(new TimePickerShow.OnTimePickerListener() {
                    @Override
                    public void onClicklistener(String dataTime) {
                        mDataBinding.data.setText(dataTime.trim());
                    }
                });
            }
        });

        mDataBinding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDataBinding.name.getText().toString().length() > 0) {
                    if (mDataBinding.brand.getText().toString().length() > 0) {
                        if (mDataBinding.model.getText().toString().length() > 0) {
                            if (mDataBinding.isSchemeImage.getUploadImageList().size() > 0) {
                                updateImgResult = true;
                                doUploadAImagesAction1();
                            } else {
                                addGoodsOwner();
                            }
                        } else {
                            ToastUtil.show(getContext(), "型号不能为空");
                        }
                    } else {
                        ToastUtil.show(getContext(), "品牌不能为空");
                    }
                } else {
                    ToastUtil.show(getContext(), "名称不能为空");
                }
            }
        });

    }

    protected void doUploadAImagesAction1() {
        showDialog();
        boolean hasImageAToken = doUploadImagesAction1();
        if (hasImageAToken) {
            doHasImageTokenSuccess1();
        }
    }

    private boolean doUploadImagesAction1() {
        List<UploadImage> uploadImages = mDataBinding.isSchemeImage.getUploadImageList();
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
        mDataBinding.isSchemeImage.setImageToken(new UploadImage(imageUrl, response.getData().getCatalogues(), response.getData().getAllfilePath(), response.getData().getCatalogues()));
        doHasImageTokenSuccess1();
    }

    protected List<String> getImageTokenList1() {
        List<String> imageTokens = new ArrayList<>();
        List<UploadImage> uploadImages = mDataBinding.isSchemeImage.getUploadImageList();
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
        List<UploadImage> uploadAImages = mDataBinding.isSchemeImage.getUploadImageList();
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
                        addGoodsOwner();
                    } else {
                        ToastUtils.show(getContext(), "图片上传失败,请重新提交");
                    }
                } else {
                    ToastUtils.show(getContext(), "图片上传失败,请重新提交");
                    closeDialog();
                    return;
                }
            } else {
                return;
            }
        }

    }


    /**
     * 个人中心我的设备列表
     */
    private void addGoodsOwner() {
        if (getContext() != null) {
            showDialog();
            String jsonString1 = "";
            if (mDataBinding.isSchemeImage.getUploadImageList().size() > 0) {
                for (UpdateImageBean updateImageBean : updateImageBeanList1) {
                    if (jsonString1.length() > 0) {
                        jsonString1 = jsonString1 + "," + "\"" + updateImageBean.getIMG_URL() + "\"";
                    } else {
                        jsonString1 = "\"" + updateImageBean.getIMG_URL() + "\"";
                    }
                }
            }

            Map<String, String> map = new TreeMap<String, String>();
            map.put("user_code", ConfigUtils.getCurrentUser(getContext()).getUserCode());
            map.put("name", mDataBinding.name.getText().toString());
            map.put("fixp17", mDataBinding.model.getText().toString());
            map.put("brand_id", mDataBinding.brand.getText().toString());
            map.put("fcatory_time", mDataBinding.data.getText().toString());
            map.put("status", "1");
            map.put("pic", "[" + jsonString1 + "]");
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = Sign.getSignKey(getActivity(), map, "addGoodsOwner");
            NetBean netBean = new NetBean();
            netBean.setToken(ConfigUtils.getCurrentUser(getContext()).getToken());
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(AddGoodsOwnerAction.newInstance(getContext(), netBean)
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
                                        ToastUtils.show(getContext(), "保存成功");
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
