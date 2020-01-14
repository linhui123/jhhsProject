package com.jhhscm.platform.fragment.Mechanics.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jhhscm.platform.R;
import com.jhhscm.platform.fragment.Mechanics.bean.GetComboBoxBean;

import java.util.List;

public class SXDropAdapter extends RecyclerView.Adapter<SXDropAdapter.ViewHolder> {
    private List<GetComboBoxBean.ResultBean> list;
    private Context mContext;
    private ItemListener myListener;

    public SXDropAdapter(List<GetComboBoxBean.ResultBean> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    public List<GetComboBoxBean.ResultBean> getList() {
        return list;
    }

    public void setList(List<GetComboBoxBean.ResultBean> list1) {
        this.list = list1;
        notifyDataSetChanged();
    }

    public void setMyListener(ItemListener myListener) {
        this.myListener = myListener;
    }

    @Override
    public SXDropAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SXDropAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_mechanics_sx, parent, false));
    }

    @Override
    public void onBindViewHolder(SXDropAdapter.ViewHolder holder, final int position) {
        holder.setData(list.get(position));
        if (list.get(position).isSelect()) {
            holder.tv_name.setTextColor(Color.parseColor("#3977FE"));
            holder.tv_name.setBackground(mContext.getResources().getDrawable(R.drawable.bg_d9e5));
        } else {
            holder.tv_name.setTextColor(Color.parseColor("#333333"));
            holder.tv_name.setBackground(mContext.getResources().getDrawable(R.drawable.bg_eaea));
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
                    myListener.onItemClick(list.get(position),position);
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

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_name.setOnClickListener(this);
        }

        public void setData(GetComboBoxBean.ResultBean item) {
            this.item = item;

        }

        @Override
        public void onClick(View v) {
            if (myListener != null) {
                myListener.onItemClick(item,getAdapterPosition());
            }
        }
    }

    public interface ItemListener {
        void onItemClick(GetComboBoxBean.ResultBean item,int pos);
    }
}
