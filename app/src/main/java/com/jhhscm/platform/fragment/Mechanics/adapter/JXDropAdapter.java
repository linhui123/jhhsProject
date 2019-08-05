package com.jhhscm.platform.fragment.Mechanics.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhhscm.platform.R;
import com.jhhscm.platform.fragment.Mechanics.bean.GetComboBoxBean;

import java.util.List;

public class JXDropAdapter extends RecyclerView.Adapter<JXDropAdapter.ViewHolder> {
    private List<GetComboBoxBean.ResultBean> list;
    private Context mContext;
    private ItemListener myListener;

    public JXDropAdapter(List<GetComboBoxBean.ResultBean> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    public void setMyListener(ItemListener myListener) {
        this.myListener = myListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_mechanics_jx, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.setData(list.get(position));
        if (list.get(position).isSelect()) {
            holder.tv_name.setTextColor(Color.parseColor("#3977FE"));
            holder.im_select.setVisibility(View.VISIBLE);
        } else {
            holder.tv_name.setTextColor(Color.parseColor("#333333"));
            holder.im_select.setVisibility(View.GONE);
        }
        holder.tv_name.setText(list.get(position).getKey_value());
        holder.tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setSelect(false);
                }
                list.get(position).setSelect(true);
                if (myListener != null) {
                    myListener.onItemClick(list.get(position));
                }
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        GetComboBoxBean.ResultBean item;
        TextView tv_name;
        ImageView im_select;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            im_select = (ImageView) itemView.findViewById(R.id.im_select);
            tv_name.setOnClickListener(this);
        }

        public void setData(GetComboBoxBean.ResultBean item) {
            this.item = item;

        }

        @Override
        public void onClick(View v) {
            if (myListener != null) {
                myListener.onItemClick(item);
            }
        }
    }

    public interface ItemListener {
        void onItemClick(GetComboBoxBean.ResultBean item);

    }
}
