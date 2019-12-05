package com.jhhscm.platform.fragment.msg;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jhhscm.platform.databinding.FragmentMsgDetailBinding;
import com.jhhscm.platform.fragment.base.AbsFragment;

public class MsgDetailFragment extends AbsFragment<FragmentMsgDetailBinding> {
    private GetPushListBean.DataBean dataBean;

    public static MsgDetailFragment instance() {
        MsgDetailFragment view = new MsgDetailFragment();
        return view;
    }

    @Override
    protected FragmentMsgDetailBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentMsgDetailBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        dataBean = (GetPushListBean.DataBean) getArguments().getSerializable("dataBean");
        if (dataBean != null) {
            mDataBinding.title.setText(dataBean.getTitle());
            mDataBinding.data.setText(dataBean.getAdd_time());
            mDataBinding.center.setText(dataBean.getContent());
        } else {
            mDataBinding.title.setText("暂无信息");
        }
    }
}
