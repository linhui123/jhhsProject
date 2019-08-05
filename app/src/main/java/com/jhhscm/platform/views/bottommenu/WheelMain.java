package com.jhhscm.platform.views.bottommenu;

import android.view.View;

import com.jhhscm.platform.R;
import com.jhhscm.platform.views.bottommenu.bean.MenuData;
import com.jhhscm.platform.views.timePickets.OnWheelChangedListener;
import com.jhhscm.platform.views.timePickets.WheelView;

import java.util.ArrayList;


public class WheelMain {

	private View view;
	private WheelView wv_content1;
	private WheelView wv_content2;
	private int START_YEAR = 1900, END_YEAR;

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public int getSTART_YEAR() {
		return START_YEAR;
	}

	public void setSTART_YEAR(int sTART_YEAR) {
		START_YEAR = sTART_YEAR;
	}

	public int getEND_YEAR() {
		return END_YEAR;
	}

	public void setEND_YEAR(int eND_YEAR) {
		END_YEAR = eND_YEAR;
	}

	public WheelMain(View view) {
		super();
		this.view = view;
		setView(view);
	}

	public WheelMain(View view, boolean hasSelectTime) {
		super();
		this.view = view;
		setView(view);
	}

	/**
	 * 加载时间选择器,可选显示，不使用传-1

	 */
	public void initDateBottomMenu(String dateStr, final ArrayList<MenuData> menuDatas) {

		wv_content1 = (WheelView) view.findViewById(R.id.content1);
		wv_content2 = (WheelView) view.findViewById(R.id.content2);


		ArrayList<String> arrayList=new ArrayList<>();
		for(int i=0;i<menuDatas.size();i++) {
			arrayList.add(menuDatas.get(i).getmText());
		}

		wv_content1.setAdapter(new BottomMenuAdapter(arrayList));// 设置显示数据
		if(menuDatas.size()<5){
			wv_content1.setCyclic(false);// 可循环滚动
			if(menuDatas.size()%2==0) {
				wv_content1.setVisibleItems(menuDatas.size() + 1);
			}else {
				wv_content1.setVisibleItems(menuDatas.size());
			}
			if(menuDatas.size()==1){
				wv_content1.setVisibleItems(3);
			}
		}else {
			wv_content1.setCyclic(false);
		}

		wv_content1.setCurrentItem(0);

		if(menuDatas.get(0).ismSecondary()){
			wv_content2.setAdapter(new BottomMenuAdapter(menuDatas.get(0).getmData()));
			wv_content2.setCyclic(false);// 可循环滚动
			wv_content2.setCurrentItem(0);
			OnWheelChangedListener onWheelChangedListener=new OnWheelChangedListener() {
				@Override
				public void onChanged(WheelView wheel, int oldValue, int newValue) {
					wv_content2.setAdapter(new BottomMenuAdapter(menuDatas.get(newValue).getmData()));
				}
			};
			wv_content1.addChangingListener(onWheelChangedListener);
		}else {
			wv_content2.setVisibility(View.GONE);
		}
	}

	/**
	 * 获得选中数据
	 *
	 * @return
	 */
	public MenuData getData() {
		MenuData menuData=new MenuData();
		if (wv_content1.getVisibility() != View.GONE) {
			menuData.setmSelectText("");
			menuData.setId(wv_content1.getCurrentItem());
			menuData.setChecked(true);
		}
		if (wv_content2.getVisibility() != View.GONE) {
			menuData.setmSelectText("");
			menuData.setIds(wv_content2.getCurrentItem());
			menuData.setChecked(true);
		}
		return menuData;
	}
}
