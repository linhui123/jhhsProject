package com.jhhscm.platform.fragment.Mechanics;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jhhscm.platform.R;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.FragmentBrandModelBinding;
import com.jhhscm.platform.databinding.ItemLocationBinding;
import com.jhhscm.platform.event.BrandResultEvent;
import com.jhhscm.platform.fragment.Mechanics.bean.BrandModelBean;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.views.recyclerview.DividerItemDecoration;

/**
 * 品牌机型列表
 */
public class BrandModelFragment extends AbsFragment<FragmentBrandModelBinding> {
    private BrandModelBean.DataBean item;
    private InnerAdapter mAdapter;

    public static BrandModelFragment instance() {
        BrandModelFragment view = new BrandModelFragment();
        return view;
    }

    @Override
    protected FragmentBrandModelBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentBrandModelBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        item = (BrandModelBean.DataBean) getArguments().getSerializable("dataBean");
        if (item != null) {
            mDataBinding.tvName.setText(item.getBrand_name());
            mDataBinding.recyclerview.addItemDecoration(new DividerItemDecoration(getContext()));
            mDataBinding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
            mAdapter = new InnerAdapter(getContext());
            mDataBinding.recyclerview.setAdapter(mAdapter);
            mAdapter.setData(item.getBrand_model_list());
        } else {
            getActivity().finish();
        }
    }


    private class InnerAdapter extends AbsRecyclerViewAdapter<BrandModelBean.DataBean.BrandModelListBean> {
        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<BrandModelBean.DataBean.BrandModelListBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BrandModelItemViewHolder(mInflater.inflate(R.layout.item_location, parent, false));
        }
    }

    private class BrandModelItemViewHolder extends AbsRecyclerViewHolder<BrandModelBean.DataBean.BrandModelListBean> {

        private ItemLocationBinding mBinding;

        public BrandModelItemViewHolder(View itemView) {
            super(itemView);
            mBinding = ItemLocationBinding.bind(itemView);
        }

        @Override
        protected void onBindView(final BrandModelBean.DataBean.BrandModelListBean item) {
            if (item != null) {
                mBinding.tvName.setText(item.getModel_name());
                mBinding.imSelect.setVisibility(View.GONE);
                mBinding.rl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventBusUtil.post(new BrandResultEvent(item.getModel_id() + "", item.getModel_name(),1));
                        getActivity().finish();
                    }
                });
            }
        }
    }

}

