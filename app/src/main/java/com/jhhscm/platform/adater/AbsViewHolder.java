package com.jhhscm.platform.adater;

import android.view.View;

/**
 * 类说明：
 * 作者：yangxiaoping on 2016/5/17 16:25
 * 邮箱：82650979@qq.com
 */
public abstract class AbsViewHolder<T> implements View.OnClickListener{
    protected T item;
    protected View convertView;
    private int position;

    public AbsViewHolder(View convertView) {
        this.convertView = convertView;
    }
    public View getConvertView() {
        return convertView;
    }
    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }
    protected void onBindView(T item, int position) {
        this.item = item;

        setPosition(position);
        onBindView(item);
    }
    protected abstract void onBindView(T item);
    @Override
    public void onClick(View v) {

    }
}
