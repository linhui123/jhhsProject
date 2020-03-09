package com.jhhscm.platform.fragment.address;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jhhscm.platform.R;
import com.jhhscm.platform.event.GetRegionEvent;
import com.jhhscm.platform.fragment.Mechanics.bean.GetRegionBean;
import com.jhhscm.platform.tool.EventBusUtil;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {
    private List<GetRegionBean.ResultBean> list;
    private Context mContext;
    private ItemListener myListener;

    public LocationAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public LocationAdapter(List<GetRegionBean.ResultBean> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    public void setMyListener(ItemListener myListener) {
        this.myListener = myListener;
    }

    public void setData(List<GetRegionBean.ResultBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_location, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.setData(list.get(position));
        if (list.get(position).isSelect()) {
            holder.tv_name.setTextColor(Color.parseColor("#3977FE"));
            holder.im_select.setVisibility(View.VISIBLE);
        } else {
            holder.tv_name.setTextColor(Color.parseColor("#333333"));
            holder.im_select.setVisibility(View.GONE);
        }
        holder.tv_name.setText(list.get(position).getName());
        holder.tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.rl.performClick();
            }
        });
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setSelect(false);
                }
                list.get(position).setSelect(true);
                if (myListener != null) {
                    myListener.onItemClick(list.get(position));
                }
                EventBusUtil.post(new GetRegionEvent(list.get(position).getId() + "", list.get(position).getName(), list.get(position).getType() + "", 4));
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        GetRegionBean.ResultBean item;
        TextView tv_name;
        ImageView im_select;
        RelativeLayout rl;

        public ViewHolder(View itemView) {
            super(itemView);
            rl = (RelativeLayout) itemView.findViewById(R.id.rl);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            im_select = (ImageView) itemView.findViewById(R.id.im_select);
            tv_name.setOnClickListener(this);
        }

        public void setData(GetRegionBean.ResultBean item) {
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
        void onItemClick(GetRegionBean.ResultBean item);

    }
}

