package com.jhhscm.platform.tool;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.jhhscm.platform.R;


/**
 * 仿招商银行loading dialog
 */
public class MerchantBankDialog extends Dialog{

    private Context mContext;
    private static MerchantBankDialog dialog;

    public MerchantBankDialog(Context context) {
        super(context);
        mContext = context;
    }

    public MerchantBankDialog(Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
    }

    public static MerchantBankDialog createDialog(Context context){
        //1设置样式
        dialog = new MerchantBankDialog(context, R.style.load_common_dialog);
        //2设置布局
        dialog.setContentView(R.layout.dialog_layout);
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        dialog.setCanceledOnTouchOutside(false);
        //dialog.setCancelable(false);// 不可以用“返回键”取消
        return dialog;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus && dialog !=null){
            //3加载旋转动画
            ImageView iv_loading_roll = (ImageView) dialog.findViewById(R.id.iv_loading_roll);
            Animation loadingAnim = AnimationUtils.loadAnimation(
                    mContext, R.anim.rotate_loading);
            iv_loading_roll.startAnimation(loadingAnim);
        }
    }
}
