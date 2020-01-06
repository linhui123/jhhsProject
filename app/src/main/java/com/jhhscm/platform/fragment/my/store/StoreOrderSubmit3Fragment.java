package com.jhhscm.platform.fragment.my.store;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.MyApplication;
import com.jhhscm.platform.R;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.bean.UploadImage;
import com.jhhscm.platform.databinding.FragmentStoreOrderSubmit3Binding;
import com.jhhscm.platform.event.FinishEvent;
import com.jhhscm.platform.event.ImageUpdataEvent;
import com.jhhscm.platform.event.RefreshEvent;
import com.jhhscm.platform.fragment.Mechanics.push.OldMechanicsUpImageBean;
import com.jhhscm.platform.fragment.Mechanics.push.UpdateImageBean;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.my.store.action.BusinessFindcategorybyBuscodeBean;
import com.jhhscm.platform.fragment.my.store.action.BusorderCreateOrderAction;
import com.jhhscm.platform.fragment.my.store.action.FindUserGoodsOwnerBean;
import com.jhhscm.platform.fragment.my.store.viewholder.StoreOrderProductItem3ViewHolder;
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
import com.jhhscm.platform.views.recyclerview.DividerItemDecoration;

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


public class StoreOrderSubmit3Fragment extends AbsFragment<FragmentStoreOrderSubmit3Binding> {
    private InnerAdapter mAdapter;

    private String goodsOwnerV1s = "";//设备序列号
    private String goodsOwnerV2s = "";//gps序列号
    private String goodsOwnerV3s = "";//故障类型
    private String brandIds = "";
    private String fixs = "";

    private String goodsCodes = "";//商品编号
    private String goodsList = "";//商品编号对应数量
    private FindUserGoodsOwnerBean dataBean;
    private BusinessFindcategorybyBuscodeBean getPushListBean;
    private String name = "";
    private String phone = "";
    private List<UpdateImageBean> updateImageBeanList = new ArrayList<>();
    private boolean updateImgResult = true;

    public static StoreOrderSubmit3Fragment instance() {
        StoreOrderSubmit3Fragment view = new StoreOrderSubmit3Fragment();
        return view;
    }

    @Override
    protected FragmentStoreOrderSubmit3Binding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentStoreOrderSubmit3Binding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        EventBusUtil.registerEvent(this);
        name = getArguments().getString("name");
        phone = getArguments().getString("phone");
        dataBean = (FindUserGoodsOwnerBean) getArguments().getSerializable("databean");
        getPushListBean = (BusinessFindcategorybyBuscodeBean) getArguments().getSerializable("buscodeBean");
        if (getPushListBean != null) {
            mDataBinding.recyclerview.addItemDecoration(new DividerItemDecoration(getContext()));
            mDataBinding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
            mAdapter = new InnerAdapter(getContext());
            mDataBinding.recyclerview.setAdapter(mAdapter);
            mAdapter.setData(getPushListBean.getData());
            cal();
        }

        mDataBinding.workFee.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                double total = 0.0;
                if (s.toString().length() > 0) {
                    if (mDataBinding.peijianFee.getText().toString().length() > 0) {
                        total = Double.parseDouble(mDataBinding.peijianFee.getText().toString()) + Double.parseDouble(mDataBinding.workFee.getText().toString());
                    } else {
                        total = Double.parseDouble(mDataBinding.workFee.getText().toString());
                    }
                } else {
                    if (mDataBinding.peijianFee.getText().toString().length() > 0) {
                        total = Double.parseDouble(mDataBinding.peijianFee.getText().toString());
                    }
                }
                mDataBinding.total.setText(total + "");
            }
        });

        mDataBinding.tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name != null && phone != null && dataBean != null) {
                    showDialog();
                    if (mDataBinding.isSchemeImage.getUploadImageList().size() > 0) {
                        doUploadAImagesAction();
                    } else {
                        busorder_createOrder();
                    }
                }
            }
        });
    }

    private class InnerAdapter extends AbsRecyclerViewAdapter<BusinessFindcategorybyBuscodeBean.DataBean> {
        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<BusinessFindcategorybyBuscodeBean.DataBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new StoreOrderProductItem3ViewHolder(mInflater.inflate(R.layout.item_store_order_submit3, parent, false));
        }
    }

    public void cal() {
        //计算金额
        double peijian = 0.0;
        for (int i = 0; i < mAdapter.getItemCount(); i++) {
            if (mAdapter.get(i).isSelect()) {
                peijian = peijian + mAdapter.get(i).getCounter_price() * mAdapter.get(i).getNum();
            }
        }
        mDataBinding.peijianFee.setText(peijian + "");
        double total = 0.0;
        if (mDataBinding.workFee.getText().toString().length() > 0) {
            total = peijian + Double.parseDouble(mDataBinding.workFee.getText().toString());
        } else {
            total = peijian;
        }
        mDataBinding.total.setText(total + "");
    }

    /**
     * 我的店铺商户提交订单
     */
    private void busorder_createOrder() {
        if (getContext() != null) {
            showDialog();
            if (dataBean.getData().size() > 0) {
                for (FindUserGoodsOwnerBean.DataBean updateImageBean : dataBean.getData()) {
                    if (updateImageBean.getBrand_id() != null) {
                        if (brandIds.length() > 0) {
                            brandIds = brandIds + "," + updateImageBean.getBrand_id();
                        } else {
                            brandIds = updateImageBean.getBrand_id();
                        }
                    }
                    if (updateImageBean.getFixp17() != null) {
                        if (fixs.length() > 0) {
                            fixs = fixs + "," + updateImageBean.getFixp17();
                        } else {
                            fixs = updateImageBean.getFixp17();
                        }
                    }
                    if (updateImageBean.getNo() != null) {
                        if (goodsOwnerV1s.length() > 0) {
                            goodsOwnerV1s = goodsOwnerV1s + "," + updateImageBean.getNo();
                        } else {
                            goodsOwnerV1s = updateImageBean.getNo();
                        }
                    } else {
                        if (goodsOwnerV1s.length() > 0) {
                            goodsOwnerV1s = goodsOwnerV1s + "," + "null";
                        } else {
                            goodsOwnerV1s = "null";
                        }
                    }

                    if (updateImageBean.getGps_no() != null) {
                        if (goodsOwnerV2s.length() > 0) {
                            goodsOwnerV2s = goodsOwnerV2s + "," + updateImageBean.getGps_no();
                        } else {
                            goodsOwnerV2s = updateImageBean.getGps_no();
                        }
                    } else {
                        if (goodsOwnerV2s.length() > 0) {
                            goodsOwnerV2s = goodsOwnerV2s + "," + "null";
                        } else {
                            goodsOwnerV2s = "null";
                        }
                    }
                    if (updateImageBean.getError_no() != null) {
                        if (goodsOwnerV3s.length() > 0) {
                            goodsOwnerV3s = goodsOwnerV3s + "," + updateImageBean.getError_no();
                        } else {
                            goodsOwnerV3s = updateImageBean.getError_no();
                        }
                    } else {
                        if (goodsOwnerV3s.length() > 0) {
                            goodsOwnerV3s = goodsOwnerV3s + "," + "null";
                        } else {
                            goodsOwnerV3s = "null";
                        }
                    }
                }
            }

            if (getPushListBean.getData().size() > 0) {
                for (BusinessFindcategorybyBuscodeBean.DataBean dataBean : getPushListBean.getData()) {
                    if (dataBean.isSelect() && dataBean.getNum() > 0) {
                        if (goodsCodes.length() > 0) {
                            goodsCodes = goodsCodes + "," + "" + dataBean.getGood_code() + "";
                        } else {
                            goodsCodes = "" + dataBean.getGood_code() + "";
                        }

                        if (goodsList.length() > 0) {
                            goodsList = goodsList + "," + "\"" + dataBean.getGood_code() + "\":" + dataBean.getNum();
                        } else {
                            goodsList = "\"" + dataBean.getGood_code() + "\":" + dataBean.getNum();
                        }
                    }
                }
            }

            String jsonString1 = "";
            if (mDataBinding.isSchemeImage.getUploadImageList().size() > 0) {
                for (UpdateImageBean updateImageBean : updateImageBeanList) {
                    if (jsonString1.length() > 0) {
                        jsonString1 = jsonString1 + "," + "\"" + updateImageBean.getIMG_URL() + "\"";
                    } else {
                        jsonString1 = "\"" + updateImageBean.getIMG_URL() + "\"";
                    }
                }
            }


            Map<String, String> map = new TreeMap<String, String>();
            map.put("userName", name);
            map.put("userMobile", phone);
            map.put("brandIds", brandIds);
            map.put("fixs", fixs);

            map.put("ticket", "[" + jsonString1 + "]");
            map.put("goodsOwnerV1s", goodsOwnerV1s);
            map.put("goodsOwnerV2s", goodsOwnerV2s);
            map.put("goodsOwnerV3s", goodsOwnerV3s);

            map.put("goodsCodes", goodsCodes);
            if (goodsList.length() > 0) {
                map.put("goodsList", "{" + goodsList + "}");
            }

            if (mDataBinding.workFee.getText().toString().length() > 0) {
                map.put("otherPrice", mDataBinding.workFee.getText().toString());
            } else {
                map.put("otherPrice", "0");
            }
            map.put("busMobile", ConfigUtils.getCurrentUser(getContext()).getMobile());
            map.put("busCode", ConfigUtils.getCurrentUser(getContext()).getUserCode());
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = Sign.getSignKey(getActivity(), map, "addGoodsOwner");
            NetBean netBean = new NetBean();
            netBean.setToken(ConfigUtils.getCurrentUser(getContext()).getToken());
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(BusorderCreateOrderAction.newInstance(getContext(), netBean)
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
                                        EventBusUtil.post(new RefreshEvent());
                                        EventBusUtil.post(new FinishEvent());
                                        getActivity().finish();
                                        ToastUtil.show(getContext(), "提交成功");
                                    } else {
                                        ToastUtils.show(getContext(), response.body().getMessage());
                                    }
                                }
                            }
                        }
                    }));
        }
    }

    public void onEvent(ImageUpdataEvent event) {
//        if (event.getType() == 0 && event.getUpdateImageBean().size() > 0) {
//            closeDialog();
//            if (event.isSuccess()) {
//                updateImageBeanList = event.getUpdateImageBean();
//                busorder_createOrder();
//                Log.e("ImageUpdataEvent", "图片上传成功");
//            } else {
//                ToastUtil.show(getContext(), "图片上传失败，请联系管理员");
//            }
//        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }

    public void doUploadAImagesAction() {
        boolean hasImageAToken = doUploadImagesAction();
        if (hasImageAToken) {
            doHasImageTokenSuccess();
        }
    }

    private boolean doUploadImagesAction() {
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
        String token = ConfigUtils.getCurrentUser(getContext()).getToken();
        onNewRequestCall(UploadBusorderImgAction.newInstance(getContext(), file, token).
                request(new AHttpService.IResCallback<OldMechanicsUpImageBean>() {
                    @Override
                    public void onCallback(int resultCode, Response<OldMechanicsUpImageBean> response, BaseErrorInfo baseErrorInfo) {
                        if (getContext() != null) {
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                if (response.body().getErrno().equals("0")) {
                                    if ("0".equals(response.body().getData().getCode())) {
                                        doUploadImageResponse(imageUrl, response.body());
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

    private void doUploadImageResponse(String imageUrl, OldMechanicsUpImageBean response) {
        mDataBinding.isSchemeImage.setImageToken(new UploadImage(imageUrl, response.getData().getCatalogues(), response.getData().getAllfilePath(), response.getData().getCatalogues()));
        doHasImageTokenSuccess();
    }

    protected List<String> getImageTokenList() {
        List<String> imageTokens = new ArrayList<>();
        List<UploadImage> uploadImages = mDataBinding.isSchemeImage.getUploadImageList();
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
        List<UploadImage> uploadAImages = mDataBinding.isSchemeImage.getUploadImageList();
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
                        busorder_createOrder();
//                        EventBusUtil.post(new ImageUpdataEvent(updateImageBeanList, 0));
                    } else {
                        Log.e("ImageUpdataEvent", "2");
                        ToastUtils.show(getContext(), "图片上传失败,请重新提交");
                        EventBusUtil.post(new ImageUpdataEvent(updateImageBeanList, 0, false));
                    }
                } else {
                    Log.e("ImageUpdataEvent", "3");
                    return;
                }
            } else {
                Log.e("ImageUpdataEvent", "4");
                return;
            }
        } else {
            Log.e("ImageUpdataEvent", "5");
            return;
        }
    }
}
