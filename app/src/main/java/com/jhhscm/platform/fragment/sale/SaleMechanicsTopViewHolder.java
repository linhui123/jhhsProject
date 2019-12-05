package com.jhhscm.platform.fragment.sale;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import com.jhhscm.platform.activity.AssessActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemSaleMechanicsTopBinding;
import com.jhhscm.platform.event.OrderSussessEvent;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtil;
import com.umeng.analytics.MobclickAgent;

public class SaleMechanicsTopViewHolder extends AbsRecyclerViewHolder<SaleItem> {

    private ItemSaleMechanicsTopBinding mBinding;

    public SaleMechanicsTopViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemSaleMechanicsTopBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final SaleItem item) {
        initText("2000");
        mBinding.tvPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBinding.etPhone.getText().toString().length() > 8) {
                    EventBusUtil.post(new OrderSussessEvent(mBinding.etPhone.getText().toString()));
                } else {
                    ToastUtil.show(itemView.getContext(), "请输入正确手机号");
                }
            }
        });

        mBinding.tvGujia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MobclickAgent.onEvent(itemView.getContext(), "old_mechanics_assess");
                AssessActivity.start(itemView.getContext());
            }
        });
    }

    private void initText(String num) {
        String content = "已经有" + num + "台机子成功在挖矿来售出";
        SpannableString spannableString = new SpannableString(content);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#333333")), 0, 3 + num.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF1A1A")), 3, 3 + num.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#333333")), 3 + num.length(), content.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mBinding.tvNum.setText(spannableString);
    }
}
