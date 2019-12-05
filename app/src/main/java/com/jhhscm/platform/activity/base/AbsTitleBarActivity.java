package com.jhhscm.platform.activity.base;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.jhhscm.platform.R;
import com.jhhscm.platform.databinding.ActivityAbsTitleBinding;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.tool.StringUtils;
import com.jhhscm.platform.tool.UdaUtils;

/**
 * Created by Administrator on 2016/5/16.
 */
public abstract class AbsTitleBarActivity extends AbsActivity {
    /**
     * 设置标题头信息
     *
     * @return
     */
    protected abstract String getTopTitle();

    /**
     * 创建新的fragment
     *
     * @return
     */
    protected abstract AbsFragment onCreateContentFragment();

    protected AbsFragment mFragment;
    protected Bundle mArgs;
    protected ActivityAbsTitleBinding mDataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_abs_title);
        mDataBinding.setBackActivity(this);
        mDataBinding.setShouldShowBack(shouldShowBack());
        mDataBinding.setShouldShowTitleBar(shouldShowTitleBar());
        mDataBinding.setShouldShowQuestionBack(shouldShowQuestionBack());
        mDataBinding.lyTitle.tvHeadTitle.setText(StringUtils.filterNullAndTrim(getTopTitle()));
        setupTitleView();
        setupContentView();
    }

    protected void setupTitleView() {

    }

    protected boolean shouldShowBack() {
        return true;
    }

    protected boolean shouldShowTitleBar() {
        return true;
    }

    protected boolean shouldShowQuestionBack() {
        return false;
    }

    protected void setupContentView() {
        mFragment = onCreateContentFragment();
        if (mFragment != null) {
            mArgs = onPutArguments();
            if (mArgs != null) {
                mFragment.setArguments(mArgs);
            }
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fl_content, mFragment, null).commitAllowingStateLoss();
        }
    }

    protected void setTopTitle(String title) {
        mDataBinding.lyTitle.tvHeadTitle.setText(title);
    }

    protected void setTopTitle(String title, int secondaryVisibility) {
        mDataBinding.lyTitle.tvHeadTitle.setText(title);
        mDataBinding.lyTitle.tvQuickReply.setVisibility(secondaryVisibility);
    }

    protected void setSecondaryTitle(String text){
        mDataBinding.lyTitle.tvQuickReply.setText(text);
    }

    protected int getSecondaryTitleVisibility(){ return mDataBinding.lyTitle.tvQuickReply.getVisibility(); }

    protected void setOperateTitle(String operateTitle) {
        mDataBinding.lyTitle.flHeadAction.setVisibility(View.VISIBLE);
        mDataBinding.lyTitle.tvHeadOperateAction.setVisibility(View.GONE);
        mDataBinding.lyTitle.tvHeadOperate.setText(StringUtils.filterNullAndTrim(operateTitle));
    }

    protected void setOperateTitleAction(String operateTitle) {
        mDataBinding.lyTitle.flHeadAction.setVisibility(View.GONE);
        mDataBinding.lyTitle.tvHeadOperateAction.setVisibility(View.VISIBLE);
        mDataBinding.lyTitle.tvHeadOperateAction.setText(StringUtils.filterNullAndTrim(operateTitle));
    }

    protected void setOperateTitleIconAction(int resId) {
        mDataBinding.lyTitle.flHeadIconAction.setVisibility(View.VISIBLE);
        mDataBinding.lyTitle.ivHeadOperate.setImageResource(resId);
    }

    protected Bundle onPutArguments() {
        return new Bundle();
    }

    //回退
    public void onHeadBackClick(View view) {
        UdaUtils.hideInputMethod(this, view);
        FragmentManager manager = getSupportFragmentManager();
        int count = manager.getBackStackEntryCount();
        if (count == 0) {
            finish();
        } else {
            try {
                manager.popBackStackImmediate();
            } catch (IllegalStateException ignored) {
            }
        }
    }

    //右上角按钮
    public void onHeadActionClick(View view) {
        //TODO
    }

    public void onTitleClick(View view) {
    }

    protected void switchFragment(Fragment fragment) {
        switchFragment(fragment, null, "", "");
    }

    protected void switchFragment(Fragment fragment, String stackName) {
        switchFragment(fragment, null, "", stackName);
    }

    protected void switchFragment(Fragment fragment, Bundle args) {
        switchFragment(fragment, args, "", "");
    }

    protected void switchFragment(Fragment fragment, Bundle args, String stackName) {
        switchFragment(fragment, args, "", stackName);
    }

    private void switchFragment(Fragment fragment, Bundle args, String tag, String stackName) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out, R.anim.slide_left_in, R.anim.slide_right_out);
        fragment.setArguments(args);
        transaction.add(R.id.fl_content, fragment, tag);
        transaction.addToBackStack(stackName);
        transaction.commitAllowingStateLoss();
        getSupportFragmentManager().executePendingTransactions();
    }
}
