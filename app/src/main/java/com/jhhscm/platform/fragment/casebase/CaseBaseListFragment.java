package com.jhhscm.platform.fragment.casebase;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.SearchEvent;
import android.view.View;
import android.view.ViewGroup;

import com.jhhscm.platform.R;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.FragmentCaseBaseListBinding;
import com.jhhscm.platform.event.ScrollEvent;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.http.bean.UserSession;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.views.recyclerview.WrappedRecyclerView;


import retrofit2.Response;

/**
 * Created by Administrator on 2018/8/21/021.
 */

public class CaseBaseListFragment extends AbsFragment<FragmentCaseBaseListBinding> {

    private InnerAdapter mAdapter;
    private static final String SEARCH = "search";
    private String mPhoneOrName;
    private int mShowCount = 10;
    private int mCurrentPage = 1;
    private final int START_PAGE = mCurrentPage;
    private String mType;


    public static CaseBaseListFragment instance(String phoneOrName, String type) {
        CaseBaseListFragment view = new CaseBaseListFragment();
        Bundle args = new Bundle();
        args.putString(SEARCH, phoneOrName);
        args.putString("type",type);
        view.setArguments(args);
        return view;
    }

    @Override
    protected FragmentCaseBaseListBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentCaseBaseListBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {

        if (getArguments() != null) {
            mPhoneOrName = getArguments().getString(SEARCH);
            mType=getArguments().getString("type");
        }

        EventBusUtil.registerEvent(this);
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new InnerAdapter(getContext());
        mDataBinding.recyclerView.setAdapter(mAdapter);
        mDataBinding.recyclerView.loadComplete(false, false);
        mDataBinding.recyclerView.autoRefresh();
        mDataBinding.recyclerView.setLoadmoreText("");
        mDataBinding.recyclerView.setOnPullListener(new WrappedRecyclerView.OnPullListener() {
            @Override
            public void onRefresh(RecyclerView view) {
                getOrderList(true);
            }

            @Override
            public void onLoadMore(RecyclerView view) {
                getOrderList(false);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }

    public void onEventMainThread(ScrollEvent e) {
        mDataBinding.recyclerView.autoRefresh();
    }

    private void getOrderList(final boolean refresh) {
//        UserSession userSession = ConfigUtils.getCurrentUser(getContext());
//        if (userSession != null) {
//            if (mAdapter.getItemCount() == 0) {
//            }
//            mCurrentPage = refresh ? START_PAGE : ++mCurrentPage;
//            onNewRequestCall(FindCostomerByNameMhAction.newInstance(getContext(), mShowCount, mCurrentPage, mPhoneOrName, mType)
//                    .request(new AHttpService.IResCallback<BaseEntity<FindCostomerByNameMhEntity>>() {
//                        @Override
//                        public void onCallback(int resultCode, Response<BaseEntity<FindCostomerByNameMhEntity>> response, BaseErrorInfo baseErrorInfo) {
//                            if (getView() != null) {
//                                closeDialog();
//                                if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
//                                    contentEmpty();
//                                    mDataBinding.recyclerView.loadComplete(false, true);
//                                    return;
//                                }
//                                if (response != null) {
//                                    new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
//                                    if (response.body().getCode().equals("200")) {
//                                        doSuccessResponse(refresh, response.body().getResult());
//                                    } else {
//                                        mDataBinding.recyclerView.loadComplete(false, true);
//                                        ToastUtils.show(getContext(), response.body().getMessage());
//                                    }
//                                }
//                            }
//                        }
//                    }));
//        }

    }

    private void doSuccessResponse(boolean refresh, FindCostomerByNameMhEntity findCustomerEntity) {
        if (refresh) {
            mAdapter.setData(FindCostomerByNameMhItem.convert(findCustomerEntity.getCUSTOMERDETAILS(), mType));
        } else {
            mAdapter.append(FindCostomerByNameMhItem.convert(findCustomerEntity.getCUSTOMERDETAILS(), mType));
        }
        mDataBinding.recyclerView.getAdapter().notifyDataSetChanged();
        mDataBinding.recyclerView.loadComplete(mAdapter.getItemCount() == 0, ((float) findCustomerEntity.getRECORD().getTOTALRESULT() / (float) findCustomerEntity.getRECORD().getSHOWCOUNT()) > mCurrentPage);

        if (mAdapter.getItemCount() == 0) {
            mDataBinding.recyclerView.setLoadmoreText("");
            contentEmpty();
        } else {
//            EventBusUtil.post(new SearchEvent());

        }
    }

    private void contentEmpty() {
        mDataBinding.llProject.setVisibility(View.VISIBLE);
    }

    private View.OnClickListener createOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int viewId = v.getId();
            }
        };
    }

    private class InnerAdapter extends AbsRecyclerViewAdapter<FindCostomerByNameMhItem> {
        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<FindCostomerByNameMhItem> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CaseBaseViewHolder(mInflater.inflate(R.layout.item_case_base, parent, false));
        }
    }
}
