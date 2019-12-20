package com.jhhscm.platform.fragment.my.mechanics;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.MyApplication;
import com.jhhscm.platform.R;
import com.jhhscm.platform.bean.PbImage;
import com.jhhscm.platform.bean.UploadImage;
import com.jhhscm.platform.databinding.FragmentAddDeviceBinding;
import com.jhhscm.platform.event.DelPhotoEvent;
import com.jhhscm.platform.event.RefreshEvent;
import com.jhhscm.platform.fragment.Mechanics.action.FindBrandAction;
import com.jhhscm.platform.fragment.Mechanics.bean.FindBrandBean;
import com.jhhscm.platform.fragment.Mechanics.bean.GetComboBoxBean;
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
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.StringUtils;
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.dialog.DropTDialog;
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

/**
 * 添加设备
 */
public class AddDeviceFragment extends AbsFragment<FragmentAddDeviceBinding> {
    private int type;
    FindGoodsOwnerBean.DataBean dataBean;
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
        EventBusUtil.registerEvent(this);

        type = getArguments().getInt("type", 0);
        dataBean = new FindGoodsOwnerBean.DataBean();
        findBrand();
        updateImageBeanList1 = new ArrayList<>();
        dataBean = new FindGoodsOwnerBean.DataBean();
        findBrand();
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
                                if (type == 0) {
                                    addGoodsOwner();
                                } else {
                                    updataGoodsOwner();
                                }
                            }
                        } else {
                            ToastUtil.show(getContext(), "设备型号不能为空");
                        }
                    } else {
                        ToastUtil.show(getContext(), "设备品牌不能为空");
                    }
                } else {
                    ToastUtil.show(getContext(), "设备名称不能为空");
                }
            }
        });

        mDataBinding.brand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DropTDialog(getContext(), "品牌", list, new DropTDialog.CallbackListener() {
                    @Override
                    public void clickResult(String id, String Nmae) {
                        mDataBinding.brand.setText(Nmae);
                        mDataBinding.brand.setTag(id);
                    }
                }).show();
            }
        });

        if (type == 1) {
            dataBean = (FindGoodsOwnerBean.DataBean) getArguments().getSerializable("data");
            if (dataBean != null) {
                mDataBinding.brand.setText(dataBean.getBrand_name());
                mDataBinding.brand.setTag(dataBean.getBrand_id());
                mDataBinding.model.setText(dataBean.getFixp17());
                mDataBinding.name.setText(dataBean.getName());
                if (dataBean.getFcatory_time() != null && dataBean.getFcatory_time().length() > 10) {
                    mDataBinding.data.setText(dataBean.getFcatory_time().substring(0, 10));
                }

                if (dataBean.getPic_gallery_url_list() != null && dataBean.getPic_gallery_url_list().length() > 10) {
                    List<PbImage> items = new ArrayList<>();
                    String listString = dataBean.getPic_gallery_url_list().replace("[\"", "").replace("\"]", "");
                    String[] strs = listString.split("\",\"");
                    if (strs.length > 0) {
                        for (int i = 0; i < strs.length; i++) {
                            PbImage pbImage = new PbImage();
                            pbImage.setmUrl(strs[i].trim());
                            pbImage.setmToken(strs[i].trim());
                            items.add(pbImage);
                        }
                        mDataBinding.isSchemeImage.setPbImageList(items);
                    }
                }
            }
        }
    }

    public void onEvent(DelPhotoEvent event) {
        if (event.getUrl() != null) {
        }
    }

    /**
     * 获取品牌列表
     */
    private void findBrand() {
        if (getContext() != null) {
            Map<String, String> map = new TreeMap<String, String>();
            map.put("brand_type", "1");
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = Sign.getSignKey(getActivity(), map, "findBrand");
            NetBean netBean = new NetBean();
            netBean.setToken("");
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(FindBrandAction.newInstance(getContext(), netBean)
                    .request(new AHttpService.IResCallback<BaseEntity<FindBrandBean>>() {
                        @Override
                        public void onCallback(int resultCode, Response<BaseEntity<FindBrandBean>> response,
                                               BaseErrorInfo baseErrorInfo) {
                            if (getView() != null) {
                                closeDialog();
                                if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                    return;
                                }
                                if (response != null) {
                                    new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                    if (response.body().getCode().equals("200")) {
                                        doSuccessResponse(response.body().getData());
                                    } else {
                                        ToastUtils.show(getContext(), response.body().getMessage());
                                    }
                                }
                            }
                        }
                    }));
        }
    }

    private FindBrandBean findBrandBean;
    private List<GetComboBoxBean.ResultBean> list;

    private void doSuccessResponse(FindBrandBean categoryBean) {
        this.findBrandBean = categoryBean;
        list = new ArrayList<>();
        for (FindBrandBean.ResultBean resultBean : findBrandBean.getResult()) {
            list.add(new GetComboBoxBean.ResultBean(resultBean.getId(), resultBean.getName()));
        }
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
                Log.e("imageFile", "imageFile.length() " + image.getImageUrl());
                imageFile(getContext(), image.getImageUrl());
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
                        if (uploadAImages.get(i).getImageUrl() != null && uploadAImages.get(i).getImageUrl().contains("http://")) {
                            UpdateImageBean updateImageBean = new UpdateImageBean();
                            updateImageBean.setIMG_URL(uploadAImages.get(i).getImageUrl());
                            updateImageBean.setPATIENT_IMAGE_NODE("1");
                            updateImageBeanList1.add(updateImageBean);
                        } else {
                            UpdateImageBean updateImageBean = new UpdateImageBean();
                            updateImageBean.setIMG_URL(uploadAImages.get(i).getAllfilePath());
                            updateImageBean.setMENU_CATALOGUES(uploadAImages.get(i).getCatalogues());
                            updateImageBean.setPATIENT_IMAGE_NODE("1");
                            updateImageBeanList1.add(updateImageBean);
                        }
                    }
                    if (updateImgResult) {
                        if (type == 0) {
                            addGoodsOwner();
                        } else {
                            updataGoodsOwner();
                        }
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
     * 个人中心我的设备 添加
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
            map.put("name", mDataBinding.name.getText().toString().trim());
            map.put("fixp17", mDataBinding.model.getText().toString().trim());
            map.put("brand_id", mDataBinding.brand.getTag().toString().trim());
            map.put("fcatory_time", mDataBinding.data.getText().toString().trim());
            map.put("status", "1");
//            map.put("pic", "[" + jsonString1 + "]");
            map.put("pic_gallery_url_list", "[" + jsonString1 + "]");

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
                                        EventBusUtil.post(new RefreshEvent());
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

    /**
     * 个人中心我的设备 更新
     */
    private void updataGoodsOwner() {
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
//            map.put("code", dataBean.getCode());
            map.put("goods_owner_code", dataBean.getCode());
            map.put("name", mDataBinding.name.getText().toString().trim());
            map.put("fixp17", mDataBinding.model.getText().toString().trim());
            map.put("brand_id", mDataBinding.brand.getTag().toString().trim());
            map.put("fcatory_time", mDataBinding.data.getText().toString().trim());
            map.put("status", dataBean.getStatus() + "");
//            map.put("pic", "[" + jsonString1 + "]");
            map.put("pic_gallery_url_list", "[" + jsonString1 + "]");
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = Sign.getSignKey(getActivity(), map, "updateGoodsOwner");
            NetBean netBean = new NetBean();
            netBean.setToken(ConfigUtils.getCurrentUser(getContext()).getToken());
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(UpdateGoodsOwnerAction.newInstance(getContext(), netBean)
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
                                        EventBusUtil.post(new RefreshEvent());
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }
}
