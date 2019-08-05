package com.jhhscm.platform.views.timePickets;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.jhhscm.platform.R;


public class TimePicketAlertDialog {
	private Context context;
	private Dialog dialog;
	private LinearLayout lLayout_bg;
	private LinearLayout dialog_Group;
	private TextView btn_neg;
	private TextView btn_pos;
	private TextView tv_option;
	private Display display;
	private boolean showTitle = false;
	private boolean showMsg = false;
	private boolean showEditText = false;
	private boolean showLayout = false;
	private boolean showPosBtn = false;
	private boolean showNegBtn = false;

	public TimePicketAlertDialog(Context context) {
		this.context = context;
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		display = windowManager.getDefaultDisplay();
	}

	public TimePicketAlertDialog builder() {
		// 获取Dialog布局
		View view = LayoutInflater.from(context).inflate(R.layout.dialog_timepickets, null);

		// 获取自定义Dialog布局中的控件
		lLayout_bg = (LinearLayout) view.findViewById(R.id.lLayout_bg);
		dialog_Group = (LinearLayout) view.findViewById(R.id.dialog_Group);
		dialog_Group.setVisibility(View.GONE);
		btn_neg = (TextView) view.findViewById(R.id.btn_neg);
		btn_neg.setVisibility(View.GONE);
		btn_pos = (TextView) view.findViewById(R.id.btn_pos);
		btn_pos.setVisibility(View.GONE);
		tv_option = (TextView) view.findViewById(R.id.tv_option);

		// 定义Dialog布局和参数
		dialog = new Dialog(context, R.style.AlertDialogStyle);
		dialog.setContentView(view);

		// 调整dialog背景大小
		Window dialogWindow = dialog.getWindow();
		dialogWindow.setGravity( Gravity.BOTTOM);
		lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display.getWidth()), LayoutParams.WRAP_CONTENT));

		return this;
	}



	public TimePicketAlertDialog setView(View view) {
		showLayout = true;
		if (view == null) {
			showLayout = false;
		} else
			dialog_Group.addView(view, android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		return this;
	}

	public TimePicketAlertDialog setCancelable(boolean cancel) {
		dialog.setCancelable(cancel);
		return this;
	}

	public TimePicketAlertDialog setOptionTv(String text) {
		showPosBtn = true;
		if ("".equals(text)) {
			tv_option.setText("");
		} else {
			tv_option.setText(text);
		}
		return this;
	}

	public TimePicketAlertDialog setPositiveButton(String text, final OnClickListener listener) {
		showPosBtn = true;
		if ("".equals(text)) {
			btn_pos.setText("完成");
		} else {
			btn_pos.setText(text);
		}
		btn_pos.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onClick(v);
				dialog.dismiss();
			}
		});
		return this;
	}

	public TimePicketAlertDialog setNegativeButton(String text, final OnClickListener listener) {
		showNegBtn = true;
		if ("".equals(text)) {
			btn_neg.setText("取消");
		} else {
			btn_neg.setText(text);
		}
		btn_neg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onClick(v);
				dialog.dismiss();
			}
		});
		return this;
	}

	private void setLayout() {
		if (showLayout) {
			dialog_Group.setVisibility(View.VISIBLE);
		}

		if (!showPosBtn && !showNegBtn) {
			btn_pos.setText("完成");
			btn_pos.setVisibility(View.VISIBLE);
			// btn_pos.setBackgroundResource(R.drawable.alertdialog_single_selector);
			btn_pos.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
		}

		if (showPosBtn && showNegBtn) {
			btn_pos.setVisibility(View.VISIBLE);
//			btn_pos.setBackgroundResource(R.drawable.dialog_right_btn_select);
			btn_neg.setVisibility(View.VISIBLE);
//			btn_neg.setBackgroundResource(R.drawable.dialog_left_btn_select);
		}

		if (showPosBtn && !showNegBtn) {
			btn_pos.setVisibility(View.VISIBLE);
			// btn_pos.setBackgroundResource(R.drawable.alertdialog_single_selector);
		}

		if (!showPosBtn && showNegBtn) {
			btn_neg.setVisibility(View.VISIBLE);
			// btn_neg.setBackgroundResource(R.drawable.alertdialog_single_selector);
		}
	}

	public void show() {
		setLayout();
		dialog.show();
	}
}
