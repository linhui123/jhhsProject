package com.jhhscm.platform.fragment.my.book;

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
import com.jhhscm.platform.databinding.FragmentAddBookingBinding;
import com.jhhscm.platform.event.RefreshEvent;
import com.jhhscm.platform.fragment.Mechanics.action.GetComboBoxAction;
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
import com.jhhscm.platform.http.sign.SignObject;
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

public class AddBookingFragment extends AbsFragment<FragmentAddBookingBinding> {

    private int type;
    private GetComboBoxBean incomeBox;
    private GetComboBoxBean payBox;
    /**
     * 上传图片
     */
    private List<UpdateImageBean> updateImageBeanList1;
    private boolean updateImgResult;

    private DetailToolBean dataBean;

    public static AddBookingFragment instance() {
        AddBookingFragment view = new AddBookingFragment();
        return view;
    }

    @Override
    protected FragmentAddBookingBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentAddBookingBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        type = getArguments().getInt("type");
        initData();
        updateImageBeanList1 = new ArrayList<>();
        if (type == 0) {
            getComboBox("bus_tool_in");
            mDataBinding.rlPay.setVisibility(View.GONE);
            mDataBinding.rlTypePay.setVisibility(View.GONE);
        } else {
            getComboBox("bus_tool_out");
            mDataBinding.rlIncome.setVisibility(View.GONE);
            mDataBinding.rlUnIncome.setVisibility(View.GONE);
            mDataBinding.rlTypeIncome.setVisibility(View.GONE);
        }

        mDataBinding.typePay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (payBox != null) {
                    new DropTDialog(getContext(), "支出类型", payBox.getResult(), new DropTDialog.CallbackListener() {
                        @Override
                        public void clickResult(String id, String Nmae) {
                            mDataBinding.typePay.setText(Nmae);
                            mDataBinding.typePay.setTag(id);

                        }
                    }).show();
                } else {
                    getComboBox("bus_tool_out");
                }
            }
        });

        mDataBinding.typeIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (incomeBox != null) {
                    new DropTDialog(getContext(), "收入类型", incomeBox.getResult(), new DropTDialog.CallbackListener() {
                        @Override
                        public void clickResult(String id, String Nmae) {
                            mDataBinding.typeIncome.setText(Nmae);
                            mDataBinding.typeIncome.setTag(id);

                        }
                    }).show();
                } else {
                    getComboBox("bus_tool_in");
                }
            }
        });

        mDataBinding.rlDataIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerShow timePickerShow = new TimePickerShow(getContext());
                timePickerShow.timePickerAlertDialog("");
                timePickerShow.setOnTimePickerListener(new TimePickerShow.OnTimePickerListener() {
                    @Override
                    public void onClicklistener(String dataTime) {
                        mDataBinding.dataIncome.setText(dataTime.trim() + " 00:00:00");
                    }
                });
            }
        });

        mDataBinding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDataBinding.dataIncome.getText().toString().trim().length() > 0
                        && mDataBinding.content.getText().toString().trim().length() > 0
                        && mDataBinding.remark.getText().toString().trim().length() > 0) {
                    if (type == 0 && mDataBinding.typeIncome.getTag() != null
                            && mDataBinding.income.getText().toString().trim().length() > 0
                            && mDataBinding.unIncome.getText().toString().trim().length() > 0) {
                        if (mDataBinding.isSchemeImage.getUploadImageList().size() > 0) {
                            updateImgResult = true;
                            doUploadAImagesAction1();
                        } else {
                            addTool();
                        }

                    } else if (type == 1 && mDataBinding.typePay.getTag() != null
                            && mDataBinding.pay.getText().toString().trim().length() > 0) {
                        if (mDataBinding.isSchemeImage.getUploadImageList().size() > 0) {
                            updateImgResult = true;
                            doUploadAImagesAction1();
                        } else {
                            addTool();
                        }
                    } else {
                        ToastUtil.show(getContext(), "请输入完整数据");
                    }
                } else {
                    ToastUtil.show(getContext(), "请输入完整数据");
                }
            }
        });
    }

    private void initData() {
        dataBean = (DetailToolBean) getArguments().getSerializable("detailToolBean");
        if (dataBean != null) {
            if (type == 0) {//收入
                mDataBinding.income.setText(dataBean.getData().getPrice_1() + "");
                mDataBinding.unIncome.setText(dataBean.getData().getPrice_2() + "");
                mDataBinding.typeIncome.setText(dataBean.getData().getIn_type_name());
                mDataBinding.typeIncome.setTag(dataBean.getData().getIn_type());
            } else {//支出
                mDataBinding.pay.setText(dataBean.getData().getPrice_3() + "");
                mDataBinding.typePay.setText(dataBean.getData().getOut_type_name());
                mDataBinding.typePay.setTag(dataBean.getData().getOut_type());
            }
            mDataBinding.content.setText(dataBean.getData().getData_content());
            mDataBinding.remark.setText(dataBean.getData().getDesc());
            if (dataBean.getData().getData_time() != null && dataBean.getData().getData_time().length() > 10) {
                mDataBinding.dataIncome.setText(dataBean.getData().getData_time().substring(0, 10).trim() + " 00:00:00");
            }

            if (dataBean.getData().getPic_small_url() != null && dataBean.getData().getPic_small_url().length() > 10) {
                List<PbImage> items = new ArrayList<>();
                String[] strs = dataBean.getData().getPic_small_url().split(",");
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

    private void addTool() {
        if (getContext() != null) {
            showDialog();
            Map<String, Object> map = new TreeMap<String, Object>();
            String jsonString1 = "";
            if (mDataBinding.isSchemeImage.getUploadImageList().size() > 0) {
                for (UpdateImageBean updateImageBean : updateImageBeanList1) {
                    if (jsonString1.length() > 0) {
                        jsonString1 = jsonString1 + "," + "" + updateImageBean.getIMG_URL() + "";
                    } else {
                        jsonString1 = "" + updateImageBean.getIMG_URL() + "";
                    }
                }
            }
            if (dataBean != null) {
                map.put("data_code", dataBean.getData().getData_code());
                map.put("id", dataBean.getData().getId());
            }
            map.put("user_code", ConfigUtils.getCurrentUser(getContext()).getUserCode());
            map.put("data_type", type);//0 收入 1 支出
            map.put("price_1", mDataBinding.income.getText().toString().trim());
            map.put("price_2", mDataBinding.unIncome.getText().toString().trim());
            map.put("price_3", mDataBinding.pay.getText().toString().trim());
            map.put("in_type", mDataBinding.typeIncome.getTag());
            map.put("out_type", mDataBinding.typePay.getTag());
            map.put("data_time", mDataBinding.dataIncome.getText().toString().trim());
            map.put("data_content", mDataBinding.content.getText().toString().trim());
            map.put("pic_small_url", jsonString1);
            map.put("desc", mDataBinding.remark.getText().toString().trim());
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = SignObject.getSignKey(getActivity(), map, "addTool");
            NetBean netBean = new NetBean();
            netBean.setToken("");
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(ToolAddAction.newInstance(getContext(), netBean)
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
                                        if (response.body().getData().getData().equals("0")) {

                                            if (dataBean != null) {
                                                ToastUtil.show(getContext(), "编辑成功");
                                            } else {
                                                ToastUtil.show(getContext(), "添加成功");
                                            }
                                            EventBusUtil.post(new RefreshEvent());
                                            getActivity().finish();
                                        }
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
     * 获取下拉框
     */
    private void getComboBox(final String name) {
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
                                    if ("bus_tool_in".equals(name)) {
                                        income(response.body().getData());
                                    } else if ("bus_tool_out".equals(name)) {
                                        pay(response.body().getData());
                                    }
                                } else {
                                    ToastUtils.show(getContext(), "error " + name + ":" + response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
    }

    /**
     * 下拉筛选
     */
    private void income(final GetComboBoxBean getComboBoxBean) {
        getComboBoxBean.getResult().add(0, new GetComboBoxBean.ResultBean("", "不限"));
        incomeBox = getComboBoxBean;
    }

    private void pay(final GetComboBoxBean getComboBoxBean) {
        getComboBoxBean.getResult().add(0, new GetComboBoxBean.ResultBean("", "不限"));
        payBox = getComboBoxBean;
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
                        addTool();
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
}
