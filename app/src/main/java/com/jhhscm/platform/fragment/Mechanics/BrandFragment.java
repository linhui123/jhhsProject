package com.jhhscm.platform.fragment.Mechanics;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.FragmentBrandBinding;
import com.jhhscm.platform.databinding.FragmentPeiJianBinding;
import com.jhhscm.platform.event.CompMechanicsEvent;
import com.jhhscm.platform.event.FinishEvent;
import com.jhhscm.platform.fragment.Mechanics.action.FindBrandAction;
import com.jhhscm.platform.fragment.Mechanics.action.FindCategoryAction;
import com.jhhscm.platform.fragment.Mechanics.action.GetComboBoxAction;
import com.jhhscm.platform.fragment.Mechanics.adapter.JXDropAdapter;
import com.jhhscm.platform.fragment.Mechanics.adapter.SelectedAdapter;
import com.jhhscm.platform.fragment.Mechanics.bean.FindBrandBean;
import com.jhhscm.platform.fragment.Mechanics.bean.FindCategoryBean;
import com.jhhscm.platform.fragment.Mechanics.bean.GetComboBoxBean;
import com.jhhscm.platform.fragment.Mechanics.holder.BrandViewHolder;
import com.jhhscm.platform.fragment.Mechanics.holder.PeiJianViewHolder;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.jpush.ExampleUtil;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.DisplayUtils;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.recyclerview.WrappedRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

public class BrandFragment extends AbsFragment<FragmentBrandBinding> {
    private InnerAdapter mAdapter;

    public static BrandFragment instance() {
        BrandFragment view = new BrandFragment();
        return view;
    }

    @Override
    protected FragmentBrandBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentBrandBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
//        RelativeLayout.LayoutParams llParams = (RelativeLayout.LayoutParams) mDataBinding.recyclerview.getLayoutParams();
//        llParams.topMargin += DisplayUtils.getStatusBarHeight(getContext());
//        mDataBinding.recyclerview.setLayoutParams(llParams);
        EventBusUtil.registerEvent(this);
        mDataBinding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new InnerAdapter(getContext());
        mDataBinding.recyclerview.setAdapter(mAdapter);
        findBrand(true);
    }

    /**
     * 获取品牌列表
     */
    private void findBrand(final boolean refresh) {
        if (getContext() != null) {
            Map<String, String> map = new TreeMap<String, String>();
            map.put("brand_type", "1");
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = Sign.getSignKey(getActivity(), map, "findBrand");
            NetBean netBean = new NetBean();
            netBean.setToken("");
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(FindBrandAction.newInstance(getContext(), netBean)
                    .request(new AHttpService.IResCallback<BaseEntity<FindBrandBean>>() {
                        @Override
                        public void onCallback(int resultCode, Response<BaseEntity<FindBrandBean>> response,
                                               BaseErrorInfo baseErrorInfo) {
                            if (getView() != null) {
                                closeDialog();
                                if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                    return;
                                }
                                if (response != null) {
                                    new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                    if (response.body().getCode().equals("200")) {
                                        doSuccessResponse(refresh, response.body().getData());
                                    } else {
                                        ToastUtils.show(getContext(), response.body().getMessage());
                                    }
                                }
                            }
                        }
                    }));
        }
    }

    private FindBrandBean findCategoryBean;

    private void doSuccessResponse(boolean refresh, FindBrandBean categoryBean) {
        this.findCategoryBean = categoryBean;
        if (refresh) {
            mAdapter.setData(categoryBean.getResult());
        } else {
            mAdapter.append(categoryBean.getResult());
        }
        mDataBinding.recyclerview.getAdapter().notifyDataSetChanged();
//      mDataBinding.recyclerview.loadComplete(mAdapter.getItemCount() == 0, ((float) findCategoryBean.getPage().getTotal() / (float) findCategoryBean.getPage().getPageSize()) > mCurrentPage);
    }

    private class InnerAdapter extends AbsRecyclerViewAdapter<FindBrandBean.ResultBean> {
        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<FindBrandBean.ResultBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BrandViewHolder(mInflater.inflate(R.layout.item_mechanics_brand, parent, false));
        }
    }

    public void onEvent(FinishEvent event) {
        getActivity().finish();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }
}
