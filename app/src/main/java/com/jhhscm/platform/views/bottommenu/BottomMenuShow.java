package com.jhhscm.platform.views.bottommenu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

import com.jhhscm.platform.R;
import com.jhhscm.platform.views.bottommenu.bean.MenuData;
import com.jhhscm.platform.views.timePickets.TimePicketAlertDialog;

import java.util.ArrayList;

@SuppressLint("SimpleDateFormat")
public class BottomMenuShow {

    private Context context;
    private WheelMain wheelMain;
    private OnBottomMenuListener bottomMenuListener;

    public BottomMenuShow(Context context) {
        super();
        this.context = context;
    }

    /**
     * 获得选中的数据
     *
     * @return
     */
    public MenuData getData() {
        return wheelMain.getData();
    }

    /**
     * 选择控件
     *
     * @return
     */
    public View bottomMenuView(String dateStr, ArrayList<MenuData> menuDatas) {
        View bottommenuview = View.inflate(context, R.layout.item_bottom_menu, null);
        wheelMain = new WheelMain(bottommenuview);
        // 若为空显示当前时间
        if (dateStr != null && !dateStr.equals("")) {

            wheelMain.initDateBottomMenu(dateStr, menuDatas);// 传-1表示不显示

        } else {
            wheelMain.initDateBottomMenu(dateStr, menuDatas);
        }
        return bottommenuview;
    }

    /**
     * alertDialog时间选择
     *
     * @param textView
     */
    public void bottomMenuAlertDialog(final String textView, final ArrayList<MenuData> menuDatas) {
        TimePicketAlertDialog dialog = new TimePicketAlertDialog(context);
        dialog.builder();
        dialog.setView(bottomMenuView(textView, menuDatas));
        dialog.setOptionTv("请选择");
        dialog.setNegativeButton("取消", new OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        dialog.setPositiveButton("完成", new OnClickListener() {
            @Override
            public void onClick(View v) {
//				textView.setText(getTxtTime("-", "-", "", "", "", ""));
                bottomMenuListener.onClicklistener(getData());
            }
        });
        dialog.show();
    }

    public void setOnBottomMenuListener(OnBottomMenuListener onBottomMenuListener) {
        bottomMenuListener = onBottomMenuListener;
    }

    public interface OnBottomMenuListener {
        void onClicklistener(MenuData menuData);
    }
}
