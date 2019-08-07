//package com.jhhscm.platform.fragment.casebase;
//
//import android.content.Context;
//import android.text.Editable;
//import android.text.TextUtils;
//import android.text.TextWatcher;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.inputmethod.EditorInfo;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.TextView;
//
//import com.westcoast.blsapp.R;
//import com.westcoast.blsapp.databinding.FragmentSearchCaseBaseBinding;
//import com.westcoast.blsapp.event.FinishEvent;
//import com.westcoast.blsapp.fragment.base.AbsFragment;
//import com.westcoast.blsapp.utils.EventBusUtil;
//import com.westcoast.blsapp.utils.ToastUtils;
//
//
///**
// * Created by Administrator on 2018/8/18/018.
// */
//
//public class CaseBaseSearchFragment extends AbsFragment<FragmentSearchCaseBaseBinding> {
//
//    private String mType;
//
//    public static CaseBaseSearchFragment instance() {
//        CaseBaseSearchFragment view = new CaseBaseSearchFragment();
//        return view;
//    }
//
//    @Override
//    protected FragmentSearchCaseBaseBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
//        return FragmentSearchCaseBaseBinding.inflate(inflater, container, attachToRoot);
//    }
//
//    @Override
//    protected void setupViews() {
//
//        if(getArguments()!=null){
//            mType=getArguments().getString("type");
//        }
//
//        EventBusUtil.registerEvent(this);
//        mDataBinding.tvCancel.setOnClickListener(createOnClickListener());
//        mDataBinding.editSearch.setOnFocusChangeListener(mInputFocusListener);
//        mDataBinding.editSearch.addTextChangedListener(mCodeTextChangeListener);
//        mDataBinding.ivClearUserNamInput.setOnClickListener(createOnClickListener());
//
//
//        mDataBinding.editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                    String content = mDataBinding.editSearch.getText().toString();
//                    if (TextUtils.isEmpty(content)) {
//                        ToastUtils.show(getContext(),"搜索内容不能为空");
//                        return true;
//                    }
//                    search(content);
//                    View view =getActivity().getWindow().peekDecorView();
//                    if (view != null) {
//                        InputMethodManager inputmanger = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//                        inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
//                    }
//                    //return true;
//                }
//                return false;
//            }
//
//        });
//    }
//
//    private View.OnClickListener createOnClickListener() {
//        return new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int viewId = v.getId();
//                if (R.id.tv_cancel == viewId) {
//                    getActivity().finish();
//                }
//                if (R.id.iv_clear_user_nam_input == viewId) {
//                    mDataBinding.editSearch.setText("");
//                    mDataBinding.ivClearUserNamInput.setVisibility(View.GONE);
//                }
//            }
//        };
//    }
//
//    private View.OnFocusChangeListener mInputFocusListener = new View.OnFocusChangeListener() {
//        @Override
//        public void onFocusChange(View v, boolean hasFocus) {
//            if (R.id.edit_search == v.getId()) {
//                if (!hasFocus) {
//                    mDataBinding.ivClearUserNamInput.setVisibility(View.GONE);
//                } else {
//                    if (mDataBinding.editSearch.getText().toString().length() > 0) {
//                        mDataBinding.ivClearUserNamInput.setVisibility(View.VISIBLE);
//                    }
//                }
//            }
//        }
//    };
//
//
//    private TextWatcher mCodeTextChangeListener = new TextWatcher() {
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//        }
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//            //mDataBinding.inputEditPasswd.setSelection(s.length());
//
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//            mDataBinding.ivClearUserNamInput.setVisibility(TextUtils.isEmpty(s) ? View.GONE : View.VISIBLE);
//        }
//    };
//
//    public void search(String phoneOrName) {
//        getActivity().getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.layFrag, CaseBaseListFragment.instance(phoneOrName,mType), null).commitAllowingStateLoss();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        EventBusUtil.unregisterEvent(this);
//    }
//
//    public void onEvent(FinishEvent event) {
//        getActivity().finish();
//    }
//
//}
