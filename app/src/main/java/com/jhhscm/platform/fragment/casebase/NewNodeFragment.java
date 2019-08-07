//package com.jhhscm.platform.fragment.casebase;
//
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.westcoast.blsapp.R;
//import com.westcoast.blsapp.databinding.FragmentNewNodeBinding;
//import com.westcoast.blsapp.event.CaseBaseUploadingEvent;
//import com.westcoast.blsapp.fragment.base.AbsFragment;
//import com.westcoast.blsapp.http.AHttpService;
//import com.westcoast.blsapp.http.HttpHelper;
//import com.westcoast.blsapp.http.action.SaveNewNodeAction;
//import com.westcoast.blsapp.http.bean.BaseEntity;
//import com.westcoast.blsapp.http.bean.BaseErrorInfo;
//import com.westcoast.blsapp.utils.EventBusUtil;
//import com.westcoast.blsapp.utils.StringUtils;
//import com.westcoast.blsapp.utils.ToastUtils;
//
//import retrofit2.Response;
//
///**
// * Created by Administrator on 2018/10/23/023.
// */
//
//public class NewNodeFragment extends AbsFragment<FragmentNewNodeBinding> {
//
//    private String mCaseId;
//
//    public static NewNodeFragment instance() {
//        NewNodeFragment view = new NewNodeFragment();
//        return view;
//    }
//
//    @Override
//    protected FragmentNewNodeBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
//        return FragmentNewNodeBinding.inflate(inflater,container,attachToRoot);
//    }
//
//    @Override
//    protected void setupViews() {
//        if(getArguments()!=null) {
//            mCaseId = getArguments().getString("caseId");
//        }
//        mDataBinding.editCaseBaseName.addTextChangedListener(mEditChangeListener);
//        mDataBinding.btnSave.setOnClickListener(createOnClickListener());
//    }
//
//    private View.OnClickListener createOnClickListener() {
//        return new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int viewId = v.getId();
//                if(R.id.btn_save==viewId){
//                    saveNewNodeAction();
//                }
//            }
//        };
//    }
//
//    private TextWatcher mEditChangeListener = new TextWatcher() {
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//        }
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//            if (s.length() > 0) {
//                judgeButton();
//            }else {
//                mDataBinding.btnSave.setEnabled(false);
//            }
//
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//
//        }
//    };
//
//    private void judgeButton() {
//        String name=mDataBinding.editCaseBaseName.getText().toString();
//        if (!StringUtils.isNullEmpty(name)) {
//            mDataBinding.btnSave.setEnabled(true);
//        } else {
//            mDataBinding.btnSave.setEnabled(false);
//        }
//    }
//
//    private void saveNewNodeAction(){
//        showDialog();
//        int i= Integer.parseInt(mDataBinding.editCaseBaseName.getText().toString());
//        if(i<=0){
//            closeDialog();
//            ToastUtils.show(getContext(),"请输入正确的天数");
//            return;
//        }else {
//            final String name = "手术后" + i + "天";
//            onNewRequestCall(SaveNewNodeAction.newInstance(getContext(), mCaseId, name).request(new AHttpService.IResCallback<BaseEntity>() {
//                @Override
//                public void onCallback(int resultCode, Response<BaseEntity> response, BaseErrorInfo baseErrorInfo) {
//                    if (getView() != null) {
//                        closeDialog();
//                        if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
//                            return;
//                        }
//                        if (response != null) {
//                            new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
//                            if (response.body().getCode().equals("200")) {
//                                ToastUtils.show(getContext(), response.body().getMessage());
//                                getActivity().finish();
//                                EventBusUtil.post(new CaseBaseUploadingEvent(name));
//                            } else {
//                                ToastUtils.show(getContext(), response.body().getMessage());
//                            }
//                        }
//                    }
//                }
//            }));
//        }
//    }
//}
