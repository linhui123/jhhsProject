package com.jhhscm.platform.fragment.my.store;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.InvitationRegisterActivity;
import com.jhhscm.platform.activity.LoginActivity;
import com.jhhscm.platform.activity.StoreOrderSubmit1Activity;
import com.jhhscm.platform.databinding.FragmentMyStoreBinding;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.my.store.action.BusinessSumdataAction;
import com.jhhscm.platform.fragment.my.store.action.BusinessSumdataBean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.UserSession;
import com.jhhscm.platform.http.sign.SignObject;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.tool.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

public class MyStoreFragment extends AbsFragment<FragmentMyStoreBinding> {
    private UserSession userSession;
    private int mScreenWidth = 0; // 屏幕宽度

    private MyProductFragment unCounponFragment;
    private MyMemberFragment useCounponFragment;
    private MyStoreOrderFragment oldCounponFragment;

    private int type = 0;

    public static MyStoreFragment instance() {
        MyStoreFragment view = new MyStoreFragment();
        return view;
    }

    @Override
    protected FragmentMyStoreBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentMyStoreBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        if (ConfigUtils.getCurrentUser(getContext()) != null
                && ConfigUtils.getCurrentUser(getContext()).getMobile() != null) {
            userSession = ConfigUtils.getCurrentUser(getContext());
        } else {
            startNewActivity(LoginActivity.class);
        }
        type = getArguments().getInt("type", 0);

        unCounponFragment = MyProductFragment.instance();
        useCounponFragment = MyMemberFragment.instance();
        oldCounponFragment = MyStoreOrderFragment.instance();

        initTab();

        mDataBinding.invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //邀请注册
                InvitationRegisterActivity.start(getContext());
            }
        });

        mDataBinding.settlement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoreOrderSubmit1Activity.start(getContext());
            }
        });
        businessSumdata();
    }

    /**
     * 搜索结果 头部tab
     */
    private void initTab() {
        mScreenWidth = Utils.getWindowsWidth(getActivity());
        if (mDataBinding.enhanceTabLayout.getTabLayout().getTabCount() == 0) {
            mDataBinding.enhanceTabLayout.addTab("产品", 3, mScreenWidth);
            mDataBinding.enhanceTabLayout.addTab("会员", 3, mScreenWidth);
            mDataBinding.enhanceTabLayout.addTab("订单", 3, mScreenWidth);
        }

        final List<Fragment> fragments = new ArrayList<>();
        fragments.add(unCounponFragment);
        fragments.add(useCounponFragment);
        fragments.add(oldCounponFragment);

        mDataBinding.vpM.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mDataBinding.enhanceTabLayout.getTabLayout()));
        mDataBinding.vpM.setOffscreenPageLimit(3);
        mDataBinding.enhanceTabLayout.setupWithViewPager(mDataBinding.vpM);
        mDataBinding.vpM.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mDataBinding.enhanceTabLayout.getTabLayout().getTabAt(position).getText();
            }
        });

        mDataBinding.enhanceTabLayout.getTabLayout().getTabAt(type).select();
    }

    private void businessSumdata() {
        if (getContext() != null) {
            Map<String, Object> map = new TreeMap<String, Object>();
            map.put("user_code", ConfigUtils.getCurrentUser(getContext()).getUserCode());
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = SignObject.getSignKey(getActivity(), map, "businessSumdata");
            NetBean netBean = new NetBean();
            netBean.setToken(ConfigUtils.getCurrentUser(getContext()).getToken());
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(BusinessSumdataAction.newInstance(getContext(), netBean)
                    .request(new AHttpService.IResCallback<BaseEntity<BusinessSumdataBean>>() {
                        @Override
                        public void onCallback(int resultCode, Response<BaseEntity<BusinessSumdataBean>> response,
                                               BaseErrorInfo baseErrorInfo) {
                            if (getView() != null) {
                                closeDialog();
                                if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                    return;
                                }
                                if (response != null) {
                                    new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                    if (response.body().getCode().equals("200")) {
                                        if (response.body().getData().getResult() != null
                                                && response.body().getData().getResult().size() > 0) {
                                            mDataBinding.tvIncomeNum.setText(response.body().getData().getResult().get(0).getSum_goods_price() + "");
                                            mDataBinding.tvMIncomeNum.setText(response.body().getData().getResult().get(0).getSum_goods_price_all() + "");
                                            mDataBinding.tvMMemberNum.setText(response.body().getData().getResult().get(0).getSum_users() + "");
                                            mDataBinding.tvGMemberNum.setText(response.body().getData().getResult().get(0).getSum_users_all() + "");
                                            mDataBinding.username.setText(response.body().getData().getResult().get(0).getBus_name());
                                            String location = "";
                                            if (response.body().getData().getResult().get(0).getProvince_name() != null) {
                                                location = response.body().getData().getResult().get(0).getProvince_name() + " ";
                                            }
                                            if (response.body().getData().getResult().get(0).getCity_name() != null) {
                                                location = location + response.body().getData().getResult().get(0).getCity_name() + " ";
                                            }
                                            if (response.body().getData().getResult().get(0).getCounty_name() != null) {
                                                location = location + response.body().getData().getResult().get(0).getCounty_name() + " ";
                                            }
                                            if (response.body().getData().getResult().get(0).getAddress_detail() != null) {
                                                location = location + response.body().getData().getResult().get(0).getAddress_detail() + " ";
                                            }
                                            mDataBinding.tvStoreNum.setText(location);
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
}
