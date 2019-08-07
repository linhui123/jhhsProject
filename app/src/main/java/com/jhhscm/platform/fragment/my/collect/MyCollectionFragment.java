package com.jhhscm.platform.fragment.my.collect;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.LoginActivity;
import com.jhhscm.platform.databinding.FragmentMyCollectionBinding;
import com.jhhscm.platform.event.AddressResultEvent;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.http.bean.UserSession;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.Utils;

import java.util.ArrayList;
import java.util.List;

public class MyCollectionFragment extends AbsFragment<FragmentMyCollectionBinding> {
    private UserSession userSession;

    private static final int TAB_COUNT = 3;
    private String type = "0";

    private int mScreenWidth = 0; // 屏幕宽度

    private NewCollectListFragment newListFragment;
    private OldCollectListFragment oldListFragment;
    private PeiJianCollectListFragment peijianListFragment;
    private FragmentTransaction transaction;

    public static MyCollectionFragment instance() {
        MyCollectionFragment view = new MyCollectionFragment();
        return view;
    }

    @Override
    protected FragmentMyCollectionBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentMyCollectionBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        if (ConfigUtils.getCurrentUser(getContext()) != null
                && ConfigUtils.getCurrentUser(getContext()).getMobile() != null) {
            userSession = ConfigUtils.getCurrentUser(getContext());
        } else {
            startNewActivity(LoginActivity.class);
        }

        final FragmentManager fragmentManager = getChildFragmentManager();
        newListFragment = new NewCollectListFragment();
        fragmentManager.beginTransaction().replace(R.id.fl, newListFragment, "newListFragment").commit();


        int count = TAB_COUNT;
        final List<String> tabNameS = new ArrayList<>();
        tabNameS.add("  新机   ");
        tabNameS.add("二手机");
        tabNameS.add("  配件  ");

        mScreenWidth = Utils.getWindowsWidth(getActivity());
        mDataBinding.enhanceTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    type = "0";
                    transaction = fragmentManager.beginTransaction();
                    if (peijianListFragment != null) {
                        transaction.hide(peijianListFragment);
                    }
                    if (oldListFragment != null) {
                        transaction.hide(oldListFragment);
                    }
                    if (newListFragment == null) {
                        newListFragment = new NewCollectListFragment();
                        transaction.add(R.id.fl, newListFragment, "newListFragment");
                    } else {
                        transaction.show(newListFragment);
                    }
                    transaction.commit();
                } else if (tab.getPosition() == 1) {
                    type = "1";
                    transaction = fragmentManager.beginTransaction();
                    if (newListFragment != null) {
                        transaction.hide(newListFragment);
                    }
                    if (peijianListFragment != null) {
                        transaction.hide(peijianListFragment);
                    }
                    if (oldListFragment == null) {
                        oldListFragment = new OldCollectListFragment();
                        transaction.add(R.id.fl, oldListFragment, "oldListFragment");
                    } else {
                        transaction.show(oldListFragment);
                    }
                    transaction.commit();
                } else if (tab.getPosition() == 2) {
                    type = "2";
                    transaction = fragmentManager.beginTransaction();
                    if (newListFragment != null) {
                        transaction.hide(newListFragment);
                    }
                    if (oldListFragment != null) {
                        transaction.hide(oldListFragment);
                    }
                    if (peijianListFragment == null) {
                        peijianListFragment = new PeiJianCollectListFragment();
                        transaction.add(R.id.fl, peijianListFragment, "peijianListFragment");
                    } else {
                        transaction.show(peijianListFragment);
                    }
                    transaction.commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        for (int i = 0; i < count; i++) {
            mDataBinding.enhanceTabLayout.addTab(tabNameS.get(i), count, mScreenWidth);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

