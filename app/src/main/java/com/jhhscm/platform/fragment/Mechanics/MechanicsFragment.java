package com.jhhscm.platform.fragment.Mechanics;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.SearchActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.FragmentHomePageBinding;
import com.jhhscm.platform.databinding.FragmentMechanicsBinding;
import com.jhhscm.platform.event.ConsultationEvent;
import com.jhhscm.platform.event.GetRegionEvent;
import com.jhhscm.platform.fragment.Mechanics.action.GetOldPageListAction;
import com.jhhscm.platform.fragment.Mechanics.action.GetRegionAction;
import com.jhhscm.platform.fragment.Mechanics.bean.GetOldPageListBean;
import com.jhhscm.platform.fragment.Mechanics.bean.GetRegionBean;
import com.jhhscm.platform.fragment.Mechanics.holder.GetRegionViewHolder;
import com.jhhscm.platform.fragment.Mechanics.holder.OldMechanicsViewHolder;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.home.HomePageAdapter;
import com.jhhscm.platform.fragment.home.HomePageFragment;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.DisplayUtils;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

public class MechanicsFragment extends AbsFragment<FragmentMechanicsBinding> {
    private NewMechanicsFragment newMechanicsFragment;
    private OldMechanicsFragment oldMechanicsFragment;

    private FragmentManager fm;
    FragmentTransaction transaction;

    String pID = "";
    String cID = "";

    public static MechanicsFragment instance() {
        MechanicsFragment view = new MechanicsFragment();
        return view;
    }

    @Override
    protected FragmentMechanicsBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentMechanicsBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        EventBusUtil.registerEvent(this);

        RelativeLayout.LayoutParams llParams = (RelativeLayout.LayoutParams) mDataBinding.rlTop.getLayoutParams();
        llParams.topMargin += DisplayUtils.getStatusBarHeight(getContext());
        mDataBinding.rlTop.setLayoutParams(llParams);

        final FragmentManager fragmentManager = getChildFragmentManager();
        newMechanicsFragment = new NewMechanicsFragment();
        fragmentManager.beginTransaction().replace(R.id.fl, newMechanicsFragment, "NewMechanicsFragment").commit();
        mDataBinding.tvNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataBinding.tvNew.setBackgroundResource(R.color.white);
                mDataBinding.tvNew.setTextColor(getResources().getColor(R.color.a397));
                mDataBinding.tvOld.setTextColor(getResources().getColor(R.color.white));
                mDataBinding.tvOld.setBackgroundResource(R.drawable.bg_397_right_ovel);
                mDataBinding.tvArea.setVisibility(View.GONE);
                mDataBinding.llArea.setVisibility(View.GONE);

                transaction = fragmentManager.beginTransaction();
                if (oldMechanicsFragment != null) {
                    transaction.hide(oldMechanicsFragment);
                }
                if (newMechanicsFragment == null) {
                    newMechanicsFragment = new NewMechanicsFragment();
                    transaction.add(R.id.fl, newMechanicsFragment, "NewMechanicsFragment");
                } else {
                    transaction.show(newMechanicsFragment);
                }
                transaction.commit();
            }
        });

        mDataBinding.tvOld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataBinding.tvOld.setBackgroundResource(R.color.white);
                mDataBinding.tvOld.setTextColor(getResources().getColor(R.color.a397));
                mDataBinding.tvNew.setBackgroundResource(R.drawable.bg_397_left_ovel);
                mDataBinding.tvNew.setTextColor(getResources().getColor(R.color.white));
                mDataBinding.tvArea.setVisibility(View.VISIBLE);
                transaction = fragmentManager.beginTransaction();
                if (newMechanicsFragment != null) {
                    transaction.hide(newMechanicsFragment);
                }
                if (oldMechanicsFragment == null) {
                    oldMechanicsFragment = new OldMechanicsFragment();
                    transaction.add(R.id.fl, oldMechanicsFragment, "OldMechanicsFragment");
                } else {
                    transaction.show(oldMechanicsFragment);
                }
                transaction.commit();
            }
        });

        mDataBinding.tvArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDataBinding.llArea.getVisibility() == View.GONE) {
                    mDataBinding.llArea.setVisibility(View.VISIBLE);
                } else if (mDataBinding.llArea.getVisibility() == View.VISIBLE) {
                    mDataBinding.llArea.setVisibility(View.GONE);
                }
            }
        });

        mDataBinding.imSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.start(getContext());
            }
        });
        initPrivince();
        getRegion("1", "");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }

    private String pName;
    private String cName;

    /**
     * 更新地区选择
     */
    public void onEvent(GetRegionEvent event) {
        if (event.pid != null && event.type != null) {
            if (event.type.equals("1")) {//省点击，获取市
                pID = event.pid;
                pName = event.name;
                getRegion("2", event.pid);
            } else if (event.type.equals("2")) {//市点击
                cID = event.pid;
                cName = event.name;
                mDataBinding.tvArea.setText(pName + " " + cName);
                mDataBinding.llArea.setVisibility(View.GONE);
            } else if (event.type.equals("0")) {//全部点击
                cID = event.pid;
                cName = event.name;
                mDataBinding.tvArea.setText("选择地区>");
                mDataBinding.llArea.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 获取行政区域列表
     */
    private void getRegion(final String type, final String pid) {
        if (getContext() != null) {
            Map<String, String> map = new TreeMap<String, String>();
            map.put("type", type);
            map.put("pid", pid);
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = Sign.getSignKey(getActivity(), map, "getRegion");
            NetBean netBean = new NetBean();
            netBean.setToken("");
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(GetRegionAction.newInstance(getContext(), netBean)
                    .request(new AHttpService.IResCallback<BaseEntity<GetRegionBean>>() {
                        @Override
                        public void onCallback(int resultCode, Response<BaseEntity<GetRegionBean>> response,
                                               BaseErrorInfo baseErrorInfo) {
                            if (getView() != null) {
                                closeDialog();
                                if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                    return;
                                }
                                if (response != null) {
                                    new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                    if (response.body().getCode().equals("200")) {
                                        GetRegionBean getRegionBean = response.body().getData();
                                        if (getRegionBean.getResult() != null) {
                                            if (type.equals("1")) {//初次加载
                                                pRegionBean = getRegionBean;
                                                GetRegionBean.ResultBean resultBean = new GetRegionBean.ResultBean();
                                                resultBean.setName("全部");
                                                resultBean.setId(0);
                                                resultBean.setType(0);
                                                pRegionBean.getResult().add(0, resultBean);
                                                pID = getRegionBean.getResult().get(0).getId() + "";
                                                pAdapter.setData(pRegionBean.getResult());
                                                getRegion("2", getRegionBean.getResult().get(0).getId() + "");
                                            } else {
                                                if (getRegionBean.getResult().size() > 0) {
                                                    cRegionBean = getRegionBean;
                                                    cID = getRegionBean.getResult().get(0).getId() + "";
                                                    cAdapter.setData(cRegionBean.getResult());
                                                } else {
//                                                    ToastUtils.show(getContext(), "无数据");
                                                }
                                            }
                                            Log.e("pAdapter", "  pAdapter.getItemCount() " + pAdapter.getItemCount());
                                            Log.e("cAdapter", "  cAdapter.getItemCount() " + cAdapter.getItemCount());

                                        }
                                    } else {
                                        ToastUtils.show(getContext(), response.body().getMessage());
                                    }
                                }
                            }
                        }
                    }));
        }
    }

    PrinviceAdapter pAdapter;
    CityAdapter cAdapter;

    GetRegionBean pRegionBean;
    GetRegionBean cRegionBean;

    private void initPrivince() {
        mDataBinding.rlPrivince.setLayoutManager(new LinearLayoutManager(getContext()));
        pAdapter = new PrinviceAdapter(getContext());
        mDataBinding.rlPrivince.setAdapter(pAdapter);

        mDataBinding.rlCity.setLayoutManager(new LinearLayoutManager(getContext()));
        cAdapter = new CityAdapter(getContext());
        mDataBinding.rlCity.setAdapter(cAdapter);
    }

    private class PrinviceAdapter extends AbsRecyclerViewAdapter<GetRegionBean.ResultBean> {
        public PrinviceAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<GetRegionBean.ResultBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new GetRegionViewHolder(mInflater.inflate(R.layout.item_mechanics_privince, parent, false));
        }
    }

    private class CityAdapter extends AbsRecyclerViewAdapter<GetRegionBean.ResultBean> {
        public CityAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<GetRegionBean.ResultBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new GetRegionViewHolder(mInflater.inflate(R.layout.item_mechanics_privince, parent, false));
        }
    }
}
