package com.jhhscm.platform.adater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.List;

/**
 * 类说明：
 * 作者：yangxiaoping on 2016/5/17 16:25
 * 邮箱：82650979@qq.com
 */

public abstract class AbsRecyclerViewAdapter<T> extends RecyclerView.Adapter<AbsRecyclerViewHolder<T>> {

    protected List<T> mData = new ArrayList();

    protected LayoutInflater mInflater;

    public AbsRecyclerViewAdapter(Context context) {
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

    public T get(int index) {
        if (index <= mData.size()) {
            return mData.get(index);
        }
        return null;
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
    public void onBindViewHolder(AbsRecyclerViewHolder<T> holder, int position) {
        holder.onBindView(mData.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
