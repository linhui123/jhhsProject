package com.jhhscm.platform.views.timePickets;

/**
 * wheel适配器
 */
public class NumericWheelAdapter implements WheelAdapter {

	/** 默认的最大值 */
	public static final int DEFAULT_MAX_VALUE = 9;

	/** 默认的最小值 */
	private static final int DEFAULT_MIN_VALUE = 0;

	private int minValue;
	private int maxValue;

	private String mMinValue;
	private String mMaxValue;

	private String format;

	public NumericWheelAdapter() {
		this(DEFAULT_MIN_VALUE, DEFAULT_MAX_VALUE);
	}

	public NumericWheelAdapter(int minValue, int maxValue) {
		this(minValue, maxValue, null);
	}

	public NumericWheelAdapter(String minValue, String maxValue) {
		this(minValue, maxValue, null);
	}

	public NumericWheelAdapter(int minValue, int maxValue, String format) {
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.format = format;
	}

	public NumericWheelAdapter(String minValue, String maxValue, String format) {
		String min=minValue.substring(0,minValue.length()-1);
		String max=maxValue.substring(0,maxValue.length()-1);
		this.mMinValue = minValue;
		this.mMaxValue = maxValue;
		this.minValue = Integer.parseInt(min);
		this.maxValue = Integer.parseInt(max);
		this.format = format;
	}

	@Override
	public String getItem(int index) {
		if (index >= 0 && index < getItemsCount()) {
			String min=mMinValue.substring(mMinValue.length()-1,mMinValue.length());
			int value = minValue + index;
			return format != null ? String.format(format, value)+min : Integer.toString(value)+min;

		}
		return null;
	}

	@Override
	public int getItemsCount() {
		return maxValue - minValue + 1;
	}

	@Override
	public int getMaximumLength() {
		int max = Math.max(Math.abs(maxValue), Math.abs(minValue));
		int maxLen = Integer.toString(max).length()+4;
		if (minValue < 0) {
			maxLen++;
		}
		return maxLen;
	}
}
