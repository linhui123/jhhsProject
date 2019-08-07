package com.jhhscm.platform.views.selector;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;

import com.jhhscm.platform.R;
import com.jhhscm.platform.adater.AbsAdapter;
import com.jhhscm.platform.adater.AbsViewHolder;
import com.jhhscm.platform.databinding.DialogChoiceBinding;
import com.jhhscm.platform.databinding.ItemChoiceBinding;
import com.jhhscm.platform.tool.DisplayUtils;
import com.jhhscm.platform.tool.StringUtils;
import com.jhhscm.platform.views.dialog.BaseDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * 类说明：
 */
public class ChoiceDialog extends BaseDialog {
    private DialogChoiceBinding mBinding;
    private ChoiceAdapter mAdapter;
    private List<ChoiceItem> items = new ArrayList<>();
    private CallbackListener mListener;
    private boolean showHeader;

    public interface CallbackListener {
        void onItemClick(ChoiceItem item);
    }

    public ChoiceDialog(Context context) {
        super(context);
        setCancelable(true);
    }

    @Override
    public void show() {
        super.show();
        //设置大小
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.width = DisplayUtils.getDeviceWidth(getContext());
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        this.getWindow().setAttributes(lp);

        Window window = getWindow();
        window.setWindowAnimations(R.style.botton_animation);
        window.setGravity(Gravity.BOTTOM);  //此处可以设置dialog显示的位置
    }

    public void setShowHeader(boolean isShowHeader){
        this.showHeader=isShowHeader;
    }

    public void setCallbackListener(CallbackListener listener) {
        this.mListener = listener;
    }

    public void setChoiceItems(List<ChoiceItem> items) {
        this.items = items;
        if (mAdapter != null) {
            mAdapter.setData(items);
        }
    }

    @Override
    protected View onInflateView(LayoutInflater inflater) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_choice, null, false);
        return mBinding.getRoot();
    }

    @Override
    protected void onInitView(View view) {
        if(showHeader) {
            mBinding.lvData.addHeaderView(createHeaderView());
        }
        mAdapter = new ChoiceAdapter(getContext());
        mAdapter.setData(items);
        mBinding.lvData.setAdapter(mAdapter);
        mBinding.lvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int pos = position - mBinding.lvData.getHeaderViewsCount();
                if (pos < 0) return;
                ChoiceDialog.this.dismiss();
                ChoiceItem item = mAdapter.getItem(pos);
                if (mListener != null) mListener.onItemClick(item);
            }
        });
    }

    private View createHeaderView() {
        View headerView = getLayoutInflater().inflate(R.layout.view_choice_title, null);
        return headerView;
    }

    public static class ChoiceItem {
        private int code;
        private String name;
        private int color = R.color.black;

        public ChoiceItem(int code, String name) {
            this.code = code;
            this.name = name;
        }

        public ChoiceItem(int code, String name, int color) {
            this.code = code;
            this.name = name;
            this.color = color;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setColor(int color) {
            this.code = color;
        }

        public int getColor() {
            return color;
        }
    }

    private class ChoiceAdapter extends AbsAdapter<ChoiceItem> {

        public ChoiceAdapter(Context context) {
            super(context);
        }

        @Override
        protected AbsViewHolder onCreateViewHolder(ViewGroup parent, int itemType) {
            return new ChoiceViewHolder(mInflater.inflate(R.layout.item_choice, parent, false));
        }
    }

    private class ChoiceViewHolder extends AbsViewHolder<ChoiceItem> {
        private ItemChoiceBinding mBinding;

        public ChoiceViewHolder(View convertView) {
            super(convertView);
            mBinding = DataBindingUtil.bind(convertView);
        }

        @Override
        protected void onBindView(ChoiceItem item) {
            mBinding.tvName.setText(StringUtils.filterNullAndTrim(item.getName()));
            mBinding.tvName.setTextColor(getContext().getResources().getColor(item.getColor()));
        }
    }
}
