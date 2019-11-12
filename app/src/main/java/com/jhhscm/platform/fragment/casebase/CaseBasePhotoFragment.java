//package com.jhhscm.platform.fragment.casebase;
//
//import android.support.v7.widget.LinearLayoutManager;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.google.gson.Gson;
//import com.jhhscm.platform.bean.FindCaseByEntity;
//import com.jhhscm.platform.bean.UploadImage;
//import com.jhhscm.platform.databinding.FragmentCaseBasePhotoBinding;
//import com.jhhscm.platform.fragment.base.AbsFragment;
//import com.jhhscm.platform.photopicker.ConfirmOrderDialog;
//
//
//import java.util.ArrayList;
//import java.util.List;
//
//import retrofit2.Response;
//
///**
// * Created by Administrator on 2018/10/16/016.
// */
//
//public class CaseBasePhotoFragment extends AbsFragment<FragmentCaseBasePhotoBinding> {
//
//    private CaseBasePhotoAdapter mAdapter;
//    private FindCaseByEntity caseByEntity;
//    private boolean pressed = false;
//    private String mCaseId;
//    private List<UploadImage> mUploadImageList;
//    private String mType;
//    private SelectCaseNodeEntity mSelectCaseNodeEntity;
//    private String mTimeNodeId;
//    private ConfirmOrderDialog mDelDialog;
//
//    public static CaseBasePhotoFragment instance() {
//        CaseBasePhotoFragment view = new CaseBasePhotoFragment();
//        return view;
//    }
//
//    @Override
//    protected FragmentCaseBasePhotoBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
//        return FragmentCaseBasePhotoBinding.inflate(inflater, container, attachToRoot);
//    }
//
//    @Override
//    protected void setupViews() {
//        //新建的时候传入的type是1
//        if (getArguments() != null) {
//            mCaseId = getArguments().getString("caseId");
//            mType = getArguments().getString("type");
//        }
//        EventBusUtil.registerEvent(this);
//        if (mType.equals("2")) {
//            mDataBinding.llButton.setVisibility(View.GONE);
//        } else {
//            mDataBinding.llButton.setVisibility(View.VISIBLE);
//        }
//
//        mUploadImageList = new ArrayList<>();
//        mDataBinding.rlUploading.setOnClickListener(createOnClickListener());
//        mDataBinding.rlManage.setOnClickListener(createOnClickListener());
//        mDataBinding.rlBack.setOnClickListener(createOnClickListener());
//        mDataBinding.rlDelete.setOnClickListener(createOnClickListener());
//        mDataBinding.rlPhotoCompile.setOnClickListener(createOnClickListener());
//        mDataBinding.rlAmend.setOnClickListener(createOnClickListener());
//        //初始化列表
//        mDataBinding.recycler.setLayoutManager(new LinearLayoutManager(getContext()));
//        mDataBinding.recycler.setPtrFrameEnabled(false);
//        mAdapter = new CaseBasePhotoAdapter(getContext());
//        mDataBinding.recycler.setAdapter(mAdapter);
//        requestDetail();
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        EventBusUtil.unregisterEvent(this);
//    }
//
//    private void requestDetail() {
//        if (getContext() != null) {
//            UserSession userSession = ConfigUtils.getCurrentUser(getContext());
//            if (userSession != null) {
//                onNewRequestCall(FindCaseByAction.newInstance(getContext(), mCaseId)
//                        .request(new AHttpService.IResCallback<BaseEntity<FindCaseByEntity>>() {
//                            @Override
//                            public void onCallback(int resultCode, Response<BaseEntity<FindCaseByEntity>> response, BaseErrorInfo baseErrorInfo) {
//                                if (getView() != null) {
//                                    if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
//                                        return;
//                                    }
//                                    if (response != null) {
//                                        new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
//                                        if (response.body().getCode().equals("200")) {
//                                            doSuccessResponse(response.body().getResult());
//                                        } else {
//                                        }
//                                    }
//                                }
//                            }
//                        }));
//            }
//        }
//    }
//
//    private void doSuccessResponse(FindCaseByEntity caseByEntity) {
//        if (caseByEntity != null) {
//            boolean isPhotoNull = true;
//            if (mType.equals("2")) {
//                for (int i = 0; i < caseByEntity.getPhotos().size(); i++) {
//                    if (caseByEntity.getPhotos().get(i).getPhoto() == null) {
//                        isPhotoNull = false;
//                    } else {
//                        isPhotoNull = true;
//                        break;
//                    }
//                }
//            }
//            if (isPhotoNull) {
//                mAdapter.setDetail(caseByEntity, pressed, mType, mCaseId);
//                mDataBinding.recycler.loadComplete(mAdapter.getItemCount() == 0, false);
//                mDataBinding.recycler.setLoadmoreText("");
//                mDataBinding.recycler.hideLoad();
//            } else {
//                mDataBinding.rlCaseBasePhotoNull.setVisibility(View.VISIBLE);
//                mDataBinding.tvName.setText(caseByEntity.getCASE_NAME());
//            }
//
//        }
//    }
//
//    private View.OnClickListener createOnClickListener() {
//        return new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int viewId = v.getId();
//                if (R.id.rl_uploading == viewId) {
//                    CaseBaseUploadingActivity.start(getContext(), mCaseId);
//                }
//                if (R.id.rl_manage == viewId) {
//                    mDataBinding.llSubbutton1.setVisibility(View.GONE);
//                    mDataBinding.llSubbutton2.setVisibility(View.VISIBLE);
//                    pressed = true;
//                    initView();
//                }
//                if (R.id.rl_back == viewId) {
//                    mDataBinding.llSubbutton1.setVisibility(View.VISIBLE);
//                    mDataBinding.llSubbutton2.setVisibility(View.GONE);
//                    pressed = false;
//                    initView();
//                }
//                if (R.id.rl_delete == viewId) {
//                    showDelAllPhotoDialog();
//                }
//                if (R.id.rl_photo_compile == viewId) {
//                    AmendCaseBaseActivity.start(getContext(), mCaseId);
//                }
//                if (R.id.rl_amend == viewId) {
//                    showTimeNode();
//                }
//            }
//        };
//    }
//
//    private void initView() {
//        requestDetail();
//        selectCaseNode();
//    }
//
//    public void onEventMainThread(CaseBasePhotoEvent event) {
//        if (event.mType == CaseBasePhotoEvent.TYPE_UPLOAD) {
//            deleteAllPhoto(event.mUploadImageList);
//        }
//        if (event.mType == CaseBasePhotoEvent.TYPE_REFRESH) {
//            requestDetail();
//        }
//        if (event.mType == CaseBasePhotoEvent.TYPE_CLOSE) {
//            EventBusUtil.post(new CaseBaseEvent());
//            getActivity().finish();
//        }
//        if (event.mType == CaseBasePhotoEvent.TYPE_DELETE_NODE) {
//            showDeletelNodeDialog(event.mNodeId);
//        }
//        if(event.mType==CaseBasePhotoEvent.TYPE_UP_NODE){
//            updatePhoto(event.mUploadImageList);
//        }
//    }
//
//    public void append(List<UploadImage> items) {
//        if (items != null && !items.isEmpty()) {
//            mUploadImageList.addAll(items);
//        }
//    }
//
//    private void deleteAllPhoto(List<UploadImage> items) {
//        if (items.size() == 0) {
//            return;
//        }
//        showDialog();
//        List<CaseBaseAllPhotoBean> caseBaseAllPhotoBeanList = new ArrayList<>();
//        for (int i = 0; i < items.size(); i++) {
//            CaseBaseAllPhotoBean caseBaseAllPhotoBean = new CaseBaseAllPhotoBean();
//            caseBaseAllPhotoBean.setPHOTO_ID(items.get(i).getImageToken());
//            caseBaseAllPhotoBeanList.add(caseBaseAllPhotoBean);
//        }
//        String listId = new Gson().toJson(caseBaseAllPhotoBeanList).toString();
//        onNewRequestCall(DeleteAllPhotoAction.newInstance(getContext(), listId).request(new AHttpService.IResCallback<BaseEntity>() {
//            @Override
//            public void onCallback(int resultCode, Response<BaseEntity> response, BaseErrorInfo baseErrorInfo) {
//                if (getView() != null) {
//                    closeDialog();
//                    if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
//                        return;
//                    }
//                    if (response != null) {
//                        new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
//                        if (response.body().getCode().equals("200")) {
////                            mDataBinding.llSubbutton1.setVisibility(View.VISIBLE);
////                            mDataBinding.llSubbutton2.setVisibility(View.GONE);
//                            mDataBinding.llSubbutton1.setVisibility(View.GONE);
//                            mDataBinding.llSubbutton2.setVisibility(View.VISIBLE);
//                            pressed = true;
//                            initView();
//                        } else {
//                            ToastUtils.show(getContext(), response.body().getMessage());
//                        }
//                    }
//                }
//            }
//        }));
//    }
//
//    private void deletelNode(String nodeId) {
//        showDialog();
//        onNewRequestCall(DeleteNodeORPhotoAction.newInstance(getContext(), nodeId).request(new AHttpService.IResCallback<BaseEntity>() {
//            @Override
//            public void onCallback(int resultCode, Response<BaseEntity> response, BaseErrorInfo baseErrorInfo) {
//                if (getView() != null) {
//                    closeDialog();
//                    if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
//                        return;
//                    }
//                    if (response != null) {
//                        new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
//                        if (response.body().getCode().equals("200")) {
//                            ToastUtils.show(getContext(), response.body().getMessage());
//                            mDataBinding.llSubbutton1.setVisibility(View.VISIBLE);
//                            mDataBinding.llSubbutton2.setVisibility(View.GONE);
//                            pressed = false;
//                            initView();
//                        } else {
//                            ToastUtils.show(getContext(), response.body().getMessage());
//                        }
//                    }
//                }
//            }
//        }));
//    }
//
//
//    private void updatePhoto(List<UploadImage> items) {
//        if (items.size() == 0) {
//            return;
//        }
//        showDialog();
//        List<CaseBaseAllPhotoBean> caseBaseAllPhotoBeanList = new ArrayList<>();
//        for (int i = 0; i < items.size(); i++) {
//            CaseBaseAllPhotoBean caseBaseAllPhotoBean = new CaseBaseAllPhotoBean();
//            caseBaseAllPhotoBean.setPHOTO_ID(items.get(i).getImageToken());
//            caseBaseAllPhotoBeanList.add(caseBaseAllPhotoBean);
//        }
//        String listId = new Gson().toJson(caseBaseAllPhotoBeanList).toString();
//        onNewRequestCall(UpdatePhotoAction.newInstance(getContext(), listId,mTimeNodeId+"").request(new AHttpService.IResCallback<BaseEntity>() {
//            @Override
//            public void onCallback(int resultCode, Response<BaseEntity> response, BaseErrorInfo baseErrorInfo) {
//                if (getView() != null) {
//                    closeDialog();
//                    if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
//                        return;
//                    }
//                    if (response != null) {
//                        new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
//                        if (response.body().getCode().equals("200")) {
//                            mDataBinding.llSubbutton1.setVisibility(View.VISIBLE);
//                            mDataBinding.llSubbutton2.setVisibility(View.GONE);
//                            pressed = false;
//                            initView();
//                        } else {
//                            ToastUtils.show(getContext(), response.body().getMessage());
//                        }
//                    }
//                }
//            }
//        }));
//    }
//
//    private void showTimeNode() {
//        if (mSelectCaseNodeEntity != null) {
//            if (mSelectCaseNodeEntity.getCASE_NODE() != null) {
//                if (mSelectCaseNodeEntity.getCASE_NODE().size() > 0) {
//                    BottomMenuShow bottomMenuShow = new BottomMenuShow(getContext());
//
//                    final ArrayList<MenuData> menuDatas = new ArrayList<>();
//                    for (int i = 0; i < mSelectCaseNodeEntity.getCASE_NODE().size(); i++) {
//                        MenuData menuData = new MenuData();
//                        menuData.setmSecondary(false);
//                        menuData.setmText(mSelectCaseNodeEntity.getCASE_NODE().get(i).getNAME());
//                        menuDatas.add(menuData);
//                    }
//
//                    bottomMenuShow.bottomMenuAlertDialog("", menuDatas);
//                    bottomMenuShow.setOnBottomMenuListener(new BottomMenuShow.OnBottomMenuListener() {
//                        @Override
//                        public void onClicklistener(MenuData data) {
//                            mTimeNodeId = mSelectCaseNodeEntity.getCASE_NODE().get(data.getId()).getNODE_ID();
//                            EventBusUtil.post(new CaseBasePhotoEvent(CaseBasePhotoEvent.TYPE_AMEND));
//                        }
//                    });
//                } else {
//                    selectCaseNode();
//                }
//            } else {
//                selectCaseNode();
//            }
//        }
//    }
//
//    private void selectCaseNode() {
//        showDialog();
//        onNewRequestCall(SelectCaseNodeAction.newInstance(getContext(), mCaseId).request(new AHttpService.IResCallback<BaseEntity<SelectCaseNodeEntity>>() {
//            @Override
//            public void onCallback(int resultCode, Response<BaseEntity<SelectCaseNodeEntity>> response, BaseErrorInfo baseErrorInfo) {
//                if (getView() != null) {
//                    if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
//                        return;
//                    }
//                    closeDialog();
//                    if (response != null) {
//                        new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
//                        if (response.body().getCode().equals("200")) {
//                            mSelectCaseNodeEntity = response.body().getResult();
//                        } else {
//                            ToastUtils.show(getContext(), response.body().getMessage());
//                        }
//                    }
//                }
//            }
//        }));
//    }
//
//    private void showDelAllPhotoDialog() {
//        if (mDelDialog == null) {
//            mDelDialog = new ConfirmOrderDialog(getContext(), "是否确认删除？", getString(R.string.photopicker_del_cancel), getString(R.string.photopicker_del_sure));
//        }
//        mDelDialog.setCallbackListener(new ConfirmOrderDialog.CallbackListener() {
//            @Override
//            public void clickNo() {
//
//            }
//
//            @Override
//            public void clickYes() {
//                EventBusUtil.post(new CaseBasePhotoEvent(CaseBasePhotoEvent.TYPE_DELETE));
//            }
//        });
//        mDelDialog.show();
//    }
//
//    private void showDeletelNodeDialog(final String nodeId) {
//        if (mDelDialog == null) {
//            mDelDialog = new ConfirmOrderDialog(getContext(), "是否确认删除？", getString(R.string.photopicker_del_cancel), getString(R.string.photopicker_del_sure));
//        }
//        mDelDialog.setCallbackListener(new ConfirmOrderDialog.CallbackListener() {
//            @Override
//            public void clickNo() {
//
//            }
//
//            @Override
//            public void clickYes() {
//               deletelNode(nodeId);
//            }
//        });
//        mDelDialog.show();
//    }
//
//}
