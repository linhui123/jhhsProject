package com.jhhscm.platform.fragment.invitation;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jhhscm.platform.R;
import com.jhhscm.platform.databinding.FragmentAfterSaleBinding;
import com.jhhscm.platform.databinding.FragmentInvitationRegisterBinding;
import com.jhhscm.platform.fragment.aftersale.AfterSaleFragment;
import com.jhhscm.platform.fragment.base.AbsFragment;

public class InvitationRegisterFragment extends AbsFragment<FragmentInvitationRegisterBinding> {

    public static InvitationRegisterFragment instance() {
        InvitationRegisterFragment view = new InvitationRegisterFragment();
        return view;
    }

    @Override
    protected FragmentInvitationRegisterBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentInvitationRegisterBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {

    }


}
