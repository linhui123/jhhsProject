package com.jhhscm.platform.fragment.my.book;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.FragmentBookingDetailBinding;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.home.action.GetArticleListAction;
import com.jhhscm.platform.fragment.home.bean.GetPageArticleListBean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.sign.SignObject;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.recyclerview.DividerItemDecoration;

import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

public class BookingDetailFragment extends AbsFragment<FragmentBookingDetailBinding> {

    private int type;
    private InnerAdapter mAdapter;

    public static BookingDetailFragment instance() {
        BookingDetailFragment view = new BookingDetailFragment();
        return view;
    }

    @Override
    protected FragmentBookingDetailBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentBookingDetailBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        type = getArguments().getInt("type");
        if (type == 0) {
            mDataBinding.rlPay.setVisibility(View.GONE);
            mDataBinding.rlTypePay.setVisibility(View.GONE);
        } else {
            mDataBinding.rlIncome.setVisibility(View.GONE);
            mDataBinding.rlUnIncome.setVisibility(View.GONE);
            mDataBinding.rlTypeIncome.setVisibility(View.GONE);
        }
        mDataBinding.recyclerview.addItemDecoration(new DividerItemDecoration(getContext()));
        mDataBinding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new InnerAdapter(getContext());
        mDataBinding.recyclerview.setAdapter(mAdapter);

//        getCouponList();

        mDataBinding.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        mDataBinding.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }


    private void getCouponList() {
        if (getContext() != null) {
            Map<String, Object> map = new TreeMap<String, Object>();
            map.put("article_type_list", 1);
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = SignObject.getSignKey(getActivity(), map, "getArticleList");
            NetBean netBean = new NetBean();
            netBean.setToken("");
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(GetArticleListAction.newInstance(getContext(), netBean)
                    .request(new AHttpService.IResCallback<BaseEntity<GetPageArticleListBean>>() {
                        @Override
                        public void onCallback(int resultCode, Response<BaseEntity<GetPageArticleListBean>> response,
                                               BaseErrorInfo baseErrorInfo) {
                            if (getView() != null) {
                                closeDialog();
                                if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                    return;
                                }
                                if (response != null) {
                                    new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                    if (response.body().getCode().equals("200")) {

                                        initView(response.body().getData());
                                    } else {
                                        ToastUtils.show(getContext(), response.body().getMessage());
                                    }
                                }
                            }
                        }
                    }));
        }
    }


    private void initView(GetPageArticleListBean pushListBean) {
        mAdapter.setData(pushListBean.getData());
        //   PhotoPickerActivity.startActivity(getContext(), ImageSelector.this.hashCode(), getPhotoPickerList(), getPhotoPickerMaxCount(), isWithCarmera);
    }


    private class InnerAdapter extends AbsRecyclerViewAdapter<GetPageArticleListBean.DataBean> {
        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<GetPageArticleListBean.DataBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ItemBookingListViewHolder(mInflater.inflate(R.layout.item_booking_list, parent, false));
        }
    }
}
