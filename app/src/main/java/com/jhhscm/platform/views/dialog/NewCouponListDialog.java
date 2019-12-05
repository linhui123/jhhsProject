package com.jhhscm.platform.views.dialog;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;

import com.jhhscm.platform.R;
import com.jhhscm.platform.databinding.DialogCouponListNewBinding;
import com.jhhscm.platform.fragment.coupon.CouponListDialogAdapter;
import com.jhhscm.platform.fragment.coupon.GetNewCouponslistBean;
import com.jhhscm.platform.tool.ConfigUtils;

import java.util.ArrayList;
import java.util.List;

public class NewCouponListDialog extends BaseDialog {
    private DialogCouponListNewBinding mDataBinding;
    private String title;
    private String count;
    private String condition;
    private boolean isCheck;
    List<GetNewCouponslistBean.ResultBean.DataBean> list;

    private CouponListDialogAdapter pAdapter;
    private CallbackListener mListener;
    private boolean mCancelable = true;

    public interface CallbackListener {
        void clickYes();
    }

    public NewCouponListDialog(Context context, CallbackListener listener) {
        this(context, null, null, listener);
    }

    public NewCouponListDialog(Context context, String title, List<GetNewCouponslistBean.ResultBean.DataBean> list, CallbackListener listener) {
        super(context);
        setCanceledOnTouchOutside(false);
        this.list = list;
        this.mListener = listener;
        this.title = title;
    }

    @Override
    protected View onInflateView(LayoutInflater inflater) {
        mDataBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_coupon_list_new, null, false);
        return mDataBinding.getRoot();
    }

    @Override
    public void show() {
        super.show();
//        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
//        layoutParams.gravity = Gravity.CENTER;
//        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
//        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
//        getWindow().getDecorView().setPadding(0, 0, 0, 0);
//        getWindow().setAttributes(layoutParams);
    }

    @Override
    protected void onInitView(View view) {
        if (title!=null){
            mDataBinding.title.setText(title);
        }

        mDataBinding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        pAdapter = new CouponListDialogAdapter(getContext());
        mDataBinding.recyclerview.setAdapter(pAdapter);
        pAdapter.setData(list);

        mDataBinding.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

        mDataBinding.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) mListener.clickYes();
            }
        });

        mDataBinding.imClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDataBinding.check.isChecked()) {
                    List<String> resultBean = ConfigUtils.getCoupon(getContext());
                    if (resultBean != null && resultBean.size() > 0) {
                        for (GetNewCouponslistBean.ResultBean.DataBean dataBean : list) {
                            if (!resultBean.contains(dataBean.getCode())) {
                                resultBean.add(dataBean.getCode());
                            }
                        }
                    } else {
                        resultBean = new ArrayList<>();
                        for (GetNewCouponslistBean.ResultBean.DataBean dataBean : list) {
                            resultBean.add(dataBean.getCode());
                        }
                    }
                    ConfigUtils.setCoupon(getContext(), resultBean);
                }
                dismiss();
            }
        });
    }

    public void setCanDissmiss(boolean cancelable) {
        this.mCancelable = cancelable;
    }


    @Override
    public void onBackPressed() {
        if (mCancelable) {
            super.onBackPressed();
        }
    }

}
