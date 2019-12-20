package com.jhhscm.platform.fragment.Mechanics;


import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.MyApplication;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.BrandActivity;
import com.jhhscm.platform.activity.LoginActivity;
import com.jhhscm.platform.bean.PbImage;
import com.jhhscm.platform.bean.UploadImage;
import com.jhhscm.platform.databinding.FragmentPushOldMechanicsBinding;
import com.jhhscm.platform.event.BrandResultEvent;
import com.jhhscm.platform.fragment.Mechanics.action.GetComboBoxAction;
import com.jhhscm.platform.fragment.Mechanics.bean.GetComboBoxBean;
import com.jhhscm.platform.fragment.Mechanics.push.OldMechanicsUpImageBean;
import com.jhhscm.platform.fragment.Mechanics.push.SaveOldGoodAction;
import com.jhhscm.platform.fragment.Mechanics.push.UpdateImageBean;
import com.jhhscm.platform.fragment.Mechanics.push.UploadOldMechanicsImgAction;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.my.mechanics.FindGoodsOwnerBean;
import com.jhhscm.platform.fragment.sale.OldGoodOrderHistoryBean;
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
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.dialog.AddressDialog;
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

public class PushOldMechanicsFragment extends AbsFragment<FragmentPushOldMechanicsBinding> implements View.OnClickListener {
    private FindGoodsOwnerBean.DataBean dataBean;
    private OldGoodOrderHistoryBean oldGoodOrderHistoryBean;
    private String brand_id, goods_factory, fix_p_9, factory_time, fix_p_13, fix_p_14, province, city;
    private String tel, old_time, price, biaopai, name;
    private UserSession userSession;

    private GetComboBoxBean getComboBoxBean;
    private boolean updateImgResult;

    public static PushOldMechanicsFragment instance() {
        PushOldMechanicsFragment view = new PushOldMechanicsFragment();
        return view;
    }

    @Override
    protected FragmentPushOldMechanicsBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentPushOldMechanicsBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        EventBusUtil.registerEvent(this);
        if (ConfigUtils.getCurrentUser(getContext()) != null
                && ConfigUtils.getCurrentUser(getContext()).getMobile() != null) {
            userSession = ConfigUtils.getCurrentUser(getContext());
        } else {
            startNewActivity(LoginActivity.class);
        }
        updateImageBeanList1 = new ArrayList<>();

        initData();
        initView();
        judgeButton();
    }

    private void initData() {
        dataBean = (FindGoodsOwnerBean.DataBean) getArguments().getSerializable("data");
        if (dataBean != null) {
            brand_id = dataBean.getBrand_id() + "";
            fix_p_9 = dataBean.getFixp17().trim();
            name = dataBean.getName().trim();
            mDataBinding.tv1.setText(dataBean.getBrand_name());
            mDataBinding.tv1.setTag(dataBean.getBrand_id());
            mDataBinding.tv2.setText(dataBean.getFixp17());
            if (dataBean.getFcatory_time() != null && dataBean.getFcatory_time().length() > 10) {
                mDataBinding.tv4.setText(dataBean.getFcatory_time().substring(0, 10));
                factory_time = dataBean.getFcatory_time().substring(0, 10).trim();
            } else {
                mDataBinding.tv4.setText(dataBean.getFcatory_time());
                factory_time = dataBean.getFcatory_time().trim();
            }
            mDataBinding.tvName.setText(dataBean.getName());

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
                    mDataBinding.selector.setPbImageList(items);
                }
            }
        }
    }

    private void initView() {
        mDataBinding.tv1.setOnClickListener(this);
        mDataBinding.tvChanshang.setOnClickListener(this);
//        mDataBinding.tv2.setOnClickListener(this);
        mDataBinding.tv3.setOnClickListener(this);
        mDataBinding.tv4.setOnClickListener(this);
        mDataBinding.tv6.setOnClickListener(this);
        mDataBinding.tv7.setOnClickListener(this);
        mDataBinding.tvBiaopai.setOnClickListener(this);
        mDataBinding.tvAssess.setOnClickListener(this);
        mDataBinding.tv2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                fix_p_9 = s.toString().trim();
                judgeButton();
            }
        });
        mDataBinding.tv5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                old_time = editable.toString().trim();
                judgeButton();
            }
        });

        mDataBinding.tvTel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                tel = editable.toString().trim();
                judgeButton();
            }
        });
//        mDataBinding.tvBiaopai.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                biaopai = editable.toString().trim();
//                judgeButton();
//            }
//        });

        mDataBinding.tvPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                price = editable.toString().trim();
                judgeButton();
            }
        });

        mDataBinding.tvName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                name = s.toString().trim();
                judgeButton();
            }
        });
    }

    /**
     * 咨询
     */
    public void onEvent(BrandResultEvent event) {
        if (event.getBrand_id() != null) {
            brand_id = event.getBrand_id();
        }
        if (event.getBrand_name() != null && event.getBrand_name().length() > 0) {
            mDataBinding.tv1.setText(event.getBrand_name());
        }
//        if (event.getFix_p_9() != null) {
//            fix_p_9 = event.getFix_p_9();
//            mDataBinding.tv2.setText(event.getFix_p_9_name());
//        }
        judgeButton();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_1://品牌 brand_id
                BrandActivity.start(getContext(), 1);
                break;
            case R.id.tv_chanshang://厂商  goods_factory
                getComboBox("goods_factory");
                break;
//            case R.id.tv_2://型号 fix_p_9
//                if (brand_id != null && brand_id.length() > 0) {
//                    MechanicsByBrandActivity.start(getContext(), brand_id, 0);
//                } else {
//                    ToastUtil.show(getContext(), "请先选择品牌");
//                }
//                break;
            case R.id.tv_3://施工地区 province city
                new AddressDialog(getActivity(), "施工地区", new AddressDialog.CallbackListener() {
                    @Override
                    public void clickResult(String pid, String pNmae, String cityId, String cName, String countryID, String countryName) {
                        mDataBinding.tv3.setText(pNmae + " " + cName + " " + countryName);
                        province = pid;
                        city = cityId;
                        judgeButton();
                    }
                }).show();
                break;
            case R.id.tv_4://年限   factory_time
                TimePickerShow timePickerShow = new TimePickerShow(getContext());
                timePickerShow.timePickerAlertDialogs("", 2);
                timePickerShow.setOnTimePickerListener(new TimePickerShow.OnTimePickerListener() {
                    @Override
                    public void onClicklistener(String dataTime) {
                        factory_time = dataTime.trim();
                        mDataBinding.tv4.setText(dataTime.trim());
                        judgeButton();
                    }
                });
                break;
            case R.id.tv_6://是否大锤  fix_p_13    is_hammer
                getComboBox("is_hammer");
                break;
            case R.id.tv_7://作业方式  fix_p_14    work_type
                getComboBox("work_type");
                break;
            case R.id.tv_biaopai://环保标牌  biaopai goods_second_envir
                getComboBox("goods_second_envir");
                break;
            case R.id.tv_assess:
                if (mDataBinding.selector.getUploadImageList().size() > 0) {
                    updateImgResult = true;
                    doUploadAImagesAction1();
                } else {
                    ToastUtils.show(getContext(), "请上传图片");
                }
                break;
        }
    }

    /**
     * 获取下拉框
     */
    private void getComboBox(final String name) {
        showDialog();
        Map<String, String> map = new TreeMap<String, String>();
        map.put("key_group_name", name);
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getActivity(), map, "getComboBox:" + name);
        NetBean netBean = new NetBean();
        netBean.setToken("");
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(GetComboBoxAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<GetComboBoxBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<GetComboBoxBean>> response, BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                if (response.body().getCode().equals("200")) {
                                    getComboBoxBean = response.body().getData();
                                    if ("is_hammer".equals(name)) {
                                        new DropTDialog(getActivity(), "是否打锤", getComboBoxBean.getResult(), new DropTDialog.CallbackListener() {
                                            @Override
                                            public void clickResult(String id, String Nmae) {
                                                fix_p_13 = id;
                                                mDataBinding.tv6.setText(Nmae);
                                                mDataBinding.tv6.setTag(id);
                                                judgeButton();
                                            }
                                        }).show();
                                    } else if ("work_type".equals(name)) {
                                        new DropTDialog(getContext(), "作业方式", getComboBoxBean.getResult(), new DropTDialog.CallbackListener() {
                                            @Override
                                            public void clickResult(String id, String Nmae) {
                                                fix_p_14 = id;
                                                mDataBinding.tv7.setText(Nmae);
                                                mDataBinding.tv7.setTag(id);
                                                judgeButton();
                                            }
                                        }).show();
                                    } else if ("goods_factory".equals(name)) {
                                        new DropTDialog(getActivity(), "厂商", getComboBoxBean.getResult(), new DropTDialog.CallbackListener() {
                                            @Override
                                            public void clickResult(String id, String Nmae) {
                                                goods_factory = id;
                                                mDataBinding.tvChanshang.setText(Nmae);
                                                mDataBinding.tvChanshang.setTag(id);
                                                judgeButton();
                                            }
                                        }).show();
                                    } else if ("goods_second_envir".equals(name)) {
                                        new DropTDialog(getActivity(), "环保标牌", getComboBoxBean.getResult(), new DropTDialog.CallbackListener() {
                                            @Override
                                            public void clickResult(String id, String Nmae) {
                                                biaopai = id;
                                                mDataBinding.tvBiaopai.setText(Nmae);
                                                mDataBinding.tvBiaopai.setTag(id);
                                                judgeButton();
                                            }
                                        }).show();
                                    }
                                } else {
                                    ToastUtils.show(getContext(), "error " + name + ":" + response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
    }

    private void judgeButton() {
        if (brand_id != null && brand_id.length() > 0
//                && goods_factory != null && goods_factory.length() > 0
                && fix_p_9 != null && fix_p_9.length() > 0
                && factory_time != null && factory_time.length() > 0
//                && name != null && name.length() > 0
                && old_time != null && old_time.length() > 0
                && fix_p_13 != null && fix_p_13.length() > 0
                && fix_p_14 != null && fix_p_14.length() > 0
                && province != null && province.length() > 0
                && city != null && city.length() > 0
                && tel != null && tel.length() > 0
                && price != null && price.length() > 0
                && biaopai != null && biaopai.length() > 0) {
            mDataBinding.tvAssess.setEnabled(true);
            mDataBinding.tvAssess.setBackgroundResource(R.drawable.button_c397);
        } else {
            mDataBinding.tvAssess.setEnabled(false);
            mDataBinding.tvAssess.setBackgroundResource(R.drawable.button_b0c);
        }
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
        String token = ConfigUtils.getCurrentUser(getContext()).getToken();
        onNewRequestCall(UploadOldMechanicsImgAction.newInstance(getContext(), file, token).
                request(new AHttpService.IResCallback<OldMechanicsUpImageBean>() {
                    @Override
                    public void onCallback(int resultCode, Response<OldMechanicsUpImageBean> response, BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
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
                        saveOldGood();
                    } else {
                        ToastUtils.show(getContext(), "图片上传失败,请重新提交");
                    }
                } else {
                    error();
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

            if (mDataBinding.selector.getUploadImageList().size() > 0) {
                for (UpdateImageBean updateImageBean : updateImageBeanList1) {
                    if (jsonString1.length() > 0) {
                        jsonString1 = jsonString1 + "," + "\"" + updateImageBean.getIMG_URL() + "\"";
                    } else {
                        jsonString1 = "\"" + updateImageBean.getIMG_URL() + "\"";
                    }
                }
            }
            Map<String, Object> map = new TreeMap<String, Object>();
            map.put("user_code", ConfigUtils.getCurrentUser(getContext()).getUserCode());
            map.put("name", mDataBinding.tv1.getText().toString() + mDataBinding.tv2.getText().toString());
            map.put("brand_id", brand_id);
            map.put("fix_p_17", fix_p_9);
            map.put("province", province);
            map.put("city", city);
            map.put("factory_time", factory_time);
            map.put("old_time", old_time);
            map.put("counter_price", price);
            map.put("fix_p_13", fix_p_13);
            map.put("fix_p_14", fix_p_14);
            map.put("fix_p_18", mDataBinding.tvTel.getText().toString().trim());
//            map.put("merchant_id", Integer.parseInt(goods_factory));
            map.put("fix_p_15", biaopai);
            map.put("pic_gallery_url_list", "[" + jsonString1 + "]");
            String content = JSON.toJSONString(map);
            Log.e("saveOldGood", "content: " + content);
            content = Des.encryptByDes(content);
            String sign = SignObject.getSignKey(getActivity(), map, "saveOldGood");
            NetBean netBean = new NetBean();
            netBean.setToken(ConfigUtils.getCurrentUser(getContext()).getToken());
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

    public void error() {
        ToastUtils.show(getContext(), R.string.error_net);
    }
}

