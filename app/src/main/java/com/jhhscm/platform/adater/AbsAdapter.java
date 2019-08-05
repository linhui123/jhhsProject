package com.jhhscm.platform.adater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 类说明：
 * 作者：yangxiaoping on 2016/5/17 16:25
 * 邮箱：82650979@qq.com
 */
public abstract class AbsAdapter<T> extends BaseAdapter {
    protected List<T> mData = new ArrayList();
    protected LayoutInflater mInflater;

    public AbsAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }
    public void append(List<T> items) {
        if (items != null && !items.isEmpty()) {
            mData.addAll(items);
            notifyDataSetChanged();
        }
    }
    public void add(T item) {
        if (item != null) {
            mData.add(item);
            notifyDataSetChanged();
        }
    }
    public void remove(T item) {
        if (item != null) {
            mData.remove(item);
            notifyDataSetChanged();
        }
    }
    public void remove(int position) {
        mData.remove(position);
        notifyDataSetChanged();
    }
    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }
    public void setData(List<T> items) {
        if (items != null) {
            mData.clear();
            mData.addAll(items);
            notifyDataSetChanged();
        }
    }
    @Override
    public int getCount() {
        return mData.size();
    }
    @Override
    public T getItem(int position) {
        return mData.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int itemType = getItemViewType(position);
        AbsViewHolder holder;
        if (convertView == null) {
            holder = onCreateViewHolder(parent, itemType);
            convertView = holder.getConvertView();
            convertView.setTag(holder);
        } else {
            holder = (AbsViewHolder) convertView.getTag();
        }
        T item = getItem(position);
        holder.onBindView(item,position);
        return convertView;
    }
    protected abstract AbsViewHolder onCreateViewHolder(ViewGroup parent, int itemType);
}
