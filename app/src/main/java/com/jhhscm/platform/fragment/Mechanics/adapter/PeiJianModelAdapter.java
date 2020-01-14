package com.jhhscm.platform.fragment.Mechanics.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jhhscm.platform.R;
import com.jhhscm.platform.fragment.Mechanics.bean.FindBrandBean;
import com.jhhscm.platform.fragment.Mechanics.bean.GetComboBoxBean;
import com.jhhscm.platform.fragment.Mechanics.bean.GoodsCatatoryListBean;

import java.util.List;

public class PeiJianModelAdapter extends RecyclerView.Adapter<PeiJianModelAdapter.ViewHolder> {
    private List<GoodsCatatoryListBean.DataBean> list;
    private Context mContext;
    private PeiJianModelAdapter.ItemListener myListener;
    public int mCheckedPosition = 0;

    public PeiJianModelAdapter(List<GoodsCatatoryListBean.DataBean> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    public List<GoodsCatatoryListBean.DataBean> getList() {
        return list;
    }

    public void setList(List<GoodsCatatoryListBean.DataBean> list1) {
        this.list = list1;
        notifyDataSetChanged();
    }
    public void append(List<GoodsCatatoryListBean.DataBean> list1) {
        this.list.addAll(list1);
        notifyDataSetChanged();
    }

    public void setMyListener(PeiJianModelAdapter.ItemListener myListener) {
        this.myListener = myListener;
    }

    @Override
    public PeiJianModelAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PeiJianModelAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_tv, parent, false));
    }

    @Override
    public void onBindViewHolder(PeiJianModelAdapter.ViewHolder holder, final int position) {
        holder.setData(list.get(position));
        if (mCheckedPosition == position ) {
            holder.tv_name.setTextColor(Color.parseColor("#3977FE"));
            holder.rl.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        } else {
            holder.tv_name.setTextColor(Color.parseColor("#333333"));
            holder.rl.setBackgroundColor(mContext.getResources().getColor(R.color.cf5));
        }
        holder.tv_name.setText(list.get(position).getName());
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setSelect(false);
                }
                list.get(position).setSelect(true);
                if (myListener != null) {
                    myListener.onItemClick(list.get(position), position);
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
        GoodsCatatoryListBean.DataBean item;
        TextView tv_name;
        LinearLayout rl;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            rl = (LinearLayout) itemView.findViewById(R.id.rl);
        }

        public void setData(GoodsCatatoryListBean.DataBean item) {
            this.item = item;

        }

        @Override
        public void onClick(View v) {
            if (myListener != null) {
                myListener.onItemClick(item, getAdapterPosition());
            }
        }
    }

    public interface ItemListener {
        void onItemClick(GoodsCatatoryListBean.DataBean item, int position);

    }
}
