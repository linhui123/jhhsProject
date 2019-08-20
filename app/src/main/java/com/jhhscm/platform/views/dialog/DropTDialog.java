package com.jhhscm.platform.views.dialog;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import com.jhhscm.platform.R;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.DialogDropTBinding;
import com.jhhscm.platform.databinding.ItemLocationBinding;
import com.jhhscm.platform.fragment.Mechanics.bean.GetComboBoxBean;
import com.jhhscm.platform.tool.DisplayUtils;
import com.jhhscm.platform.views.recyclerview.DividerItemDecoration;

import java.util.List;

public class DropTDialog extends BaseDialog {
    private DialogDropTBinding mDataBinding;
    private String title;
    private List<GetComboBoxBean.ResultBean> list;
    private CallbackListener mListener;
    private boolean mCancelable = true;

    public interface CallbackListener {
        void clickResult(String id, String Nmae);
    }

    public DropTDialog(Context context) {
        this(context, null, null, null);
    }

    public DropTDialog(Context context, CallbackListener listener) {
        this(context, null, null, listener);
    }

    public DropTDialog(Context context, String title, List<GetComboBoxBean.ResultBean> list, CallbackListener listener) {
        super(context);
        setCanceledOnTouchOutside(true);
        this.title = title;
        this.list = list;
        this.mListener = listener;
    }

    @Override
    protected View onInflateView(LayoutInflater inflater) {
        mDataBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_drop_t, null, false);
        return mDataBinding.getRoot();
    }

    @Override
    protected void onInitView(View view) {
        mDataBinding.tvTitle.setText(title);
        mDataBinding.imClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        initDrop();
    }

    private InnerAdapter pAdapter;

    private void initDrop() {
        mDataBinding.rv1.addItemDecoration(new DividerItemDecoration(getContext()));
        mDataBinding.rv1.setLayoutManager(new LinearLayoutManager(getContext()));
        pAdapter = new InnerAdapter(getContext());
        mDataBinding.rv1.setAdapter(pAdapter);
        pAdapter.setData(list);
    }

    public void setCanDissmiss(boolean cancelable) {
        this.mCancelable = cancelable;
    }

    @Override
    public void show() {
        super.show();
        Display d = this.getWindow().getWindowManager().getDefaultDisplay();
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        int h = (int) (DisplayUtils.getDeviceHeight(getContext()) * 0.7);
//        lp.height = h;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        this.getWindow().setAttributes(lp);
        Window window = getWindow();
        window.setWindowAnimations(R.style.botton_animation);
        window.setGravity(Gravity.BOTTOM);
    }

    @Override
    public void onBackPressed() {
        if (mCancelable) {
            super.onBackPressed();
        }
    }

    private class InnerAdapter extends AbsRecyclerViewAdapter<GetComboBoxBean.ResultBean> {
        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<GetComboBoxBean.ResultBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new DropViewHolder(mInflater.inflate(R.layout.item_location, parent, false));
        }
    }

    public class DropViewHolder extends AbsRecyclerViewHolder<GetComboBoxBean.ResultBean> {

        private ItemLocationBinding mBinding;

        public DropViewHolder(View itemView) {
            super(itemView);
            mBinding = ItemLocationBinding.bind(itemView);
        }

        @Override
        protected void onBindView(final GetComboBoxBean.ResultBean item) {
            mBinding.tvName.setText(item.getKey_value());
            mBinding.imSelect.setVisibility(View.GONE);
            mBinding.rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.clickResult(item.getKey_name(), item.getKey_value());
                        dismiss();
                    }
                }
            });
        }
    }
}