package com.jhhscm.platform.fragment.Mechanics.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhhscm.platform.R;
import com.jhhscm.platform.fragment.Mechanics.bean.GetComboBoxBean;

import java.util.List;

public class SelectedAdapter extends RecyclerView.Adapter<SelectedAdapter.ViewHolder> {
    private List<GetComboBoxBean.ResultBean> list;
    private Context mContext;
    private ItemListener myListener;

    public SelectedAdapter(List<GetComboBoxBean.ResultBean> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    public void setMyListener(ItemListener myListener) {
        this.myListener = myListener;
    }

    @Override
    public SelectedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SelectedAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_mechanics_selected, parent, false));
    }

    @Override
    public void onBindViewHolder(SelectedAdapter.ViewHolder holder, final int position) {
        holder.setData(list.get(position));
//        if (list.get(position).isSelect()) {
//            holder.tv_name.setTextColor(Color.parseColor("#3977FE"));
//            holder.im_del.setVisibility(View.VISIBLE);
//        } else {
//            holder.tv_name.setTextColor(Color.parseColor("#333333"));
//            holder.im_del.setVisibility(View.GONE);
//        }
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

        holder.im_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myListener != null) {
                    myListener.onItemClick(list.get(position));
                }
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
        ImageView im_del;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            im_del = (ImageView) itemView.findViewById(R.id.im_close);
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
