package com.jhhscm.platform.adater;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 类说明：
 * 作者：yangxiaoping on 2016/5/17 16:25
 * 邮箱：82650979@qq.com
 */

public abstract class AbsRecyclerViewHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener {
    protected T mItem;
    protected int mPosition;

    public AbsRecyclerViewHolder(View itemView) {
        super(itemView);
    }

    protected abstract void onBindView(T item);

    protected void onBindView(T item, int position) {
        mItem = item;
        mPosition = position;
        onBindView(item);
    }

    @Override
    public void onClick(View v) {

    }
}
