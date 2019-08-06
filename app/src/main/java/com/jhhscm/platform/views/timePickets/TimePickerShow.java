package com.jhhscm.platform.views.timePickets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.jhhscm.platform.MyApplication;
import com.jhhscm.platform.R;
import com.jhhscm.platform.tool.StringUtils;
import com.jhhscm.platform.tool.ToastUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class TimePickerShow {

    private Context context;
    private WheelMain wheelMain;
    private OnTimePickerListener timePickerListener;

    public TimePickerShow(Context context) {
        super();
        this.context = context;
    }

    /**
     * 获得选中的时间
     *
     * @param strYear   间隔符号
     * @param strMon
     * @param strDay
     * @param strHour
     * @param strMins
     * @param strSecond
     * @return
     */
    public String getTxtTime(String strYear, String strMon, String strDay, String strHour, String strMins, String strSecond) {
        return wheelMain.getTime(strYear, strMon, strDay, strHour, strMins, strSecond);
    }

    public View timePickerView() {
        View timepickerview = View.inflate(context, R.layout.item_timepickers, null);
        wheelMain = new WheelMain(timepickerview);
        // 获取当前时间
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
//		int second = calendar.get(Calendar.SECOND);

        wheelMain.setEND_YEAR(2888);// 设置最大年份
        wheelMain.setSTART_YEAR(year);
        wheelMain.initDateTimePicker(year, month, day, hour, min, -1);

        return timepickerview;
    }

    /**
     * 时间选择控件
     *
     * @param dateStr 需显示的日期
     * @return
     */
    public View timePickerView(String dateStr) {
        View timepickerview = View.inflate(context, R.layout.item_timepickers, null);
        wheelMain = new WheelMain(timepickerview);
        // 获取当前时间
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        // int hour = calendar.get(Calendar.HOUR_OF_DAY);
        // int min = calendar.get(Calendar.MINUTE);
        // int second = calendar.get(Calendar.SECOND);
        wheelMain.setEND_YEAR(2088);
        // 若为空显示当前时间
        if (dateStr != null && !dateStr.equals("")) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = format.parse(dateStr);
                calendar.setTime(date);
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                wheelMain.initDateTimePicker(year, month, day, -1, -1, -1);// 传-1表示不显示
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            wheelMain.initDateTimePicker(year, month, day, -1, -1, -1);
        }
        return timepickerview;
    }

    public View timePickerView(String dateStr, int type) {
        View timepickerview = View.inflate(context, R.layout.item_timepickers, null);
        wheelMain = new WheelMain(timepickerview);
        // 获取当前时间
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        // int second = calendar.get(Calendar.SECOND);
        wheelMain.setEND_YEAR(2088);
        // 若为空显示当前时间
        if (dateStr != null && !dateStr.equals("")) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = format.parse(dateStr);
                calendar.setTime(date);
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                hour = calendar.get(Calendar.HOUR_OF_DAY);
                min = calendar.get(Calendar.MINUTE);
                if (type == 1) {
                    wheelMain.initDateTimePicker(year, month, day, hour, min, -1);// 传-1表示不显示
                } else if (type == 2) {
                    wheelMain.initDateTimePicker(year, month, -1, -1, -1, -1);// 传-1表示不显示
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            if (type == 1) {
                wheelMain.initDateTimePicker(year, month, day, hour, min, -1);// 传-1表示不显示
            } else if (type == 2) {
                wheelMain.initDateTimePicker(year, month, -1, -1, -1, -1);// 传-1表示不显示
            }
        }
        return timepickerview;
    }


    /**
     * alertDialog时间选择
     *
     * @param textView
     */
    public void timePickerAlertDialog(final String textView) {
        TimePicketAlertDialog dialog = new TimePicketAlertDialog(context);
        dialog.builder();
        dialog.setView(timePickerView(textView));
        dialog.setOptionTv("");
        dialog.setNegativeButton("取消", new OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        dialog.setPositiveButton("完成", new OnClickListener() {
            @Override
            public void onClick(View v) {
//				textView.setText(getTxtTime("-", "-", "", "", "", ""));
                timePickerListener.onClicklistener(getTxtTime("-", "-", "", "", "", ""));
            }
        });
        dialog.show();
    }

    public void timePickerAlertDialog(final String textView, final int type) {
        TimePicketAlertDialog dialog = new TimePicketAlertDialog(context);
        dialog.builder();
        dialog.setView(timePickerView(textView, type));
        dialog.setOptionTv("");
        dialog.setNegativeButton("取消", new OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        dialog.setPositiveButton("完成", new OnClickListener() {
            @Override
            public void onClick(View v) {
//				textView.setText(getTxtTime("-", "-", "", "", "", ""));
                if (type == 1) {
                    timePickerListener.onClicklistener(getTxtTime("-", "-", " ", ":", "", ""));
                } else if (type == 2) {
                    timePickerListener.onClicklistener(getTxtTime(".", "", "", "", "", ""));
                }
            }
        });
        dialog.show();
    }


    /**
     * alertDialog时间选择
     * 增加判断条件；时间不能大于当前时间
     *
     * @param textView
     */
    public void timePickerAlertDialogs(final String textView) {
        TimePicketAlertDialog dialog = new TimePicketAlertDialog(context);
        dialog.builder();
        dialog.setView(timePickerView());
        dialog.setOptionTv("");
        dialog.setNegativeButton("取消", new OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        dialog.setPositiveButton("完成", new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.getTimeCompareSize(getTxtTime("-", "-", " ", ":", "", "")) != 3) {
                    ToastUtils.show(MyApplication.getInstance(), "预约时间不能小于当前时间");
                    return;
                }
                timePickerListener.onClicklistener(getTxtTime("-", "-", " ", ":", "", ""));
            }
        });
        dialog.show();
    }

    public void timePickerAlertDialogs(final String textView, final int type) {
        TimePicketAlertDialog dialog = new TimePicketAlertDialog(context);
        dialog.builder();
        dialog.setView(timePickerView(textView));
        dialog.setOptionTv("");
        dialog.setNegativeButton("取消", new OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        dialog.setPositiveButton("完成", new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 1) {
                    if (StringUtils.getTimeCompareSize(getTxtTime("-", "-", " ", ":", "", ""), "yyyy-MM-dd") == 1) {
                        ToastUtils.show(MyApplication.getInstance(), "选择时间不能小于当前时间");
                        return;
                    }
                    timePickerListener.onClicklistener(getTxtTime("-", "-", " ", ":", "", ""));
                } else if (type == 2) {
                    if (StringUtils.getTimeCompareSize(getTxtTime("-", "-", "", "", "", ""), "yyyy-MM-dd") == 3) {
                        ToastUtils.show(MyApplication.getInstance(), "选择时间不能大于当前时间");
                        return;
                    }
                    timePickerListener.onClicklistener(getTxtTime("-", "-", "", "", "", ""));
                }
            }
        });
        dialog.show();
    }

    public void setOnTimePickerListener(OnTimePickerListener timePickerListeners) {
        timePickerListener = timePickerListeners;
    }

    public interface OnTimePickerListener {
        void onClicklistener(String dataTime);
    }
}
