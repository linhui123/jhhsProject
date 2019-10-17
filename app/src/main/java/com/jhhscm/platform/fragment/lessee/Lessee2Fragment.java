package com.jhhscm.platform.fragment.lessee;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.MyApplication;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.Lessee3Activity;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.bean.UploadImage;
import com.jhhscm.platform.databinding.FragmentLessee1Binding;
import com.jhhscm.platform.databinding.FragmentLessee2Binding;
import com.jhhscm.platform.event.ImageSelectorUpdataEvent;
import com.jhhscm.platform.event.LesseeFinishEvent;
import com.jhhscm.platform.fragment.Mechanics.action.FindBrandAction;
import com.jhhscm.platform.fragment.Mechanics.bean.FindBrandBean;
import com.jhhscm.platform.fragment.Mechanics.bean.GetComboBoxBean;
import com.jhhscm.platform.fragment.Mechanics.push.OldMechanicsUpImageBean;
import com.jhhscm.platform.fragment.Mechanics.push.SaveOldGoodAction;
import com.jhhscm.platform.fragment.Mechanics.push.UpdateImageBean;
import com.jhhscm.platform.fragment.Mechanics.push.UploadOldMechanicsImgAction;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.labour.FindLabourReleaseListBean;
import com.jhhscm.platform.fragment.labour.LabourFragment;
import com.jhhscm.platform.fragment.labour.LabourViewHolder;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.ResultBean;
import com.jhhscm.platform.http.bean.UserSession;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.http.sign.SignObject;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.StringUtils;
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.recyclerview.DividerItemStrokeDecoration;
import com.jhhscm.platform.views.selector.ImageSelector;
import com.jhhscm.platform.views.selector.ImageSelectorItem;

import org.apache.commons.lang3.ObjectUtils;

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

public class Lessee2Fragment extends AbsFragment<FragmentLessee2Binding> {
    private FindLabourReleaseListBean.DataBean dataBean;
    private int type;
    private UserSession userSession;
    private String id;
    private InnerAdapter mAdapter;
    private LesseeBean lesseeBean;
    private List<LesseeBean.WBankLeaseItemsBean> itemsBeans;
    private List<ImageSelector> imageSelectors;

    public static Lessee2Fragment instance() {
        Lessee2Fragment view = new Lessee2Fragment();
        return view;
    }

    @Override
    protected FragmentLessee2Binding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentLessee2Binding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        EventBusUtil.registerEvent(this);
        findBrand();
        lesseeBean = (LesseeBean) getArguments().getSerializable("lesseeBean");

        mDataBinding.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LesseeBean.WBankLeaseItemsBean dataBean = new LesseeBean.WBankLeaseItemsBean();
                itemsBeans.add(dataBean);
                mAdapter.add(dataBean);
                imageSelectors.add(new ImageSelector(getContext()));
                mDataBinding.tvDel.setVisibility(View.VISIBLE);
            }
        });

        mDataBinding.tvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAdapter.getItemCount() > 1) {
                    mAdapter.remove(mAdapter.getItemCount() - 1);
                    imageSelectors.remove(imageSelectors.size() - 1);
                    itemsBeans.remove(mAdapter.getItemCount());
                }
                if (mAdapter.getItemCount() == 1) {
                    mDataBinding.tvDel.setVisibility(View.GONE);
                }
            }
        });

        mDataBinding.tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lesseeBean.setWBankLeaseItems(itemsBeans);
                if (judge()) {
                    Lessee3Activity.start(getContext(), lesseeBean);
                }
            }
        });
    }

    private boolean judge() {
        for (LesseeBean.WBankLeaseItemsBean wBankLeaseItemsBean : itemsBeans) {
            if (StringUtils.isNullEmpty(wBankLeaseItemsBean.getBrandId())) {
                ToastUtil.show(getContext(), "设备品牌不能为空");
                return false;
            }
            if (StringUtils.isNullEmpty(wBankLeaseItemsBean.getFactoryTime())) {
                ToastUtil.show(getContext(), "出厂日期不能为空");
                return false;
            }
            if (StringUtils.isNullEmpty(wBankLeaseItemsBean.getMachinePrice())) {
                ToastUtil.show(getContext(), "单价不能为空");
                return false;
            }
            if (StringUtils.isNullEmpty(wBankLeaseItemsBean.getMachineNum())) {
                ToastUtil.show(getContext(), "设备序列号不能为空");
                return false;
            }
            if (StringUtils.isNullEmpty(wBankLeaseItemsBean.getName())) {
                ToastUtil.show(getContext(), "设备名称不能为空");
                return false;
            }
            if (StringUtils.isNullEmpty(wBankLeaseItemsBean.getFixP17())) {
                ToastUtil.show(getContext(), "设备型号不能为空");
                return false;
            }
        }
        return true;
    }

    public void onEvent(LesseeFinishEvent event) {
        getActivity().finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }

    private void initList(List<GetComboBoxBean.ResultBean> list) {
        mDataBinding.rv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new InnerAdapter(getContext(), list);
        mDataBinding.rv.setAdapter(mAdapter);

        itemsBeans = new ArrayList<>();
        imageSelectors = new ArrayList<>();
        LesseeBean.WBankLeaseItemsBean dataBean = new LesseeBean.WBankLeaseItemsBean();
        itemsBeans.add(dataBean);
        mAdapter.setData(itemsBeans);
        imageSelectors.add(new ImageSelector(getContext()));
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

    private void doSuccessResponse(FindBrandBean categoryBean) {
        this.findBrandBean = categoryBean;
        List<GetComboBoxBean.ResultBean> list = new ArrayList<>();
        for (FindBrandBean.ResultBean resultBean : findBrandBean.getResult()) {
            list.add(new GetComboBoxBean.ResultBean(resultBean.getId(), resultBean.getName()));
        }
        initList(list);
    }

    public void onEvent(ImageSelectorUpdataEvent event) {
        if (event.getImageSelector() != null) {
            imageSelectors.remove(event.getPosition());
            imageSelectors.add(event.getImageSelector());
            if (imageSelectors.get(event.getPosition()) != null) {
                updateImgResult = true;
                doUploadAImagesAction(event.getPosition());
            }
        }
    }

    /**
     * 二手车上传图片
     */
    private List<UpdateImageBean> updateImageBeanList1 = new ArrayList<>();
    private boolean updateImgResult;

    protected void doUploadAImagesAction(int position) {
        showDialog();
        boolean hasImageAToken = doUploadImagesAction(position);
        if (hasImageAToken) {
            doHasImageTokenSuccess(position);
        }
    }

    private boolean doUploadImagesAction(int position) {
        List<UploadImage> uploadImages = imageSelectors.get(position).getUploadImageList();
        if (uploadImages == null) return true;
        boolean hasImageToken = true;
        for (int i = 0; i < uploadImages.size(); i++) {
            UploadImage image = uploadImages.get(i);
            if (StringUtils.isNullEmpty(image.getImageToken())) {
                hasImageToken = false;
                imageFile(position, getContext(), image.getImageUrl());
            }
        }
        return hasImageToken;
    }

    public void imageFile(final int position, Context context, final String imagePath) {
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
                                doUploadImageAction(position, file, imagePath);
                            }
                        });    //启动压缩
                return;
            } else {
                doUploadImageAction(position, imageFile, imagePath);
                return;
            }
        } catch (Throwable e) {
            closeDialog();
            e.printStackTrace();
            ToastUtils.show(getContext(), "上传失败");
            return;
        }
    }

    private void doUploadImageAction(final int position, final File file, final String imageUrl) {
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
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            closeDialog();
                            if (response != null) {
                                if (response.body().getCode().equals("200")) {
                                    if ("0".equals(response.body().getData().getCode())) {
//                                        Log.e("UploadLesseeImgAction", "response : " + response.toString());
                                        doUploadImageResponse(position, imageUrl, response.body());
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

    private void doUploadImageResponse(int position, String imageUrl, OldMechanicsUpImageBean response) {
        imageSelectors.get(position).setImageToken(new UploadImage(imageUrl, response.getData().getCatalogues(), response.getData().getAllfilePath(), response.getData().getCatalogues()));
        doHasImageTokenSuccess(position);
    }

    protected List<String> getImageTokenList(int position) {
        List<String> imageTokens = new ArrayList<>();
        List<UploadImage> uploadImages = imageSelectors.get(position).getUploadImageList();
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
    private void doHasImageTokenSuccess(int position) {
        List<UploadImage> uploadAImages = imageSelectors.get(position).getUploadImageList();
        List<String> imageATokens = getImageTokenList(position);
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
                        saveImagesData(position, updateImageBeanList1);
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

    /**
     * 数据合并处理
     */
    private void saveImagesData(int position, List<UpdateImageBean> updateImageBeanList) {
        String jsonString = "";
        for (UpdateImageBean updateImageBean : updateImageBeanList) {
            if (jsonString.length() > 0) {
                jsonString = jsonString + "," + "\"" + updateImageBean.getIMG_URL() + "\"";
            } else {
                jsonString = "\"" + updateImageBean.getIMG_URL() + "\"";
            }
        }

        mAdapter.get(position).setItemUrl(jsonString);
    }

    private class InnerAdapter extends AbsRecyclerViewAdapter<LesseeBean.WBankLeaseItemsBean> {
        private List<GetComboBoxBean.ResultBean> list;

        public InnerAdapter(Context context, List<GetComboBoxBean.ResultBean> list) {
            super(context);
            this.list = list;
        }

        @Override
        public AbsRecyclerViewHolder<LesseeBean.WBankLeaseItemsBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new LesseeViewHolder(mInflater.inflate(R.layout.item_lessee_mechanics, parent, false), list);
        }
    }

}
