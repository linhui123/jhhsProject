package com.jhhscm.platform.fragment.coupon;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jhhscm.platform.R;
import com.jhhscm.platform.event.GetRegionEvent;
import com.jhhscm.platform.fragment.Mechanics.bean.GetRegionBean;
import com.jhhscm.platform.fragment.address.LocationAdapter;
import com.jhhscm.platform.tool.EventBusUtil;

import java.util.List;

public class CouponListDialogAdapter extends RecyclerView.Adapter<CouponListDialogAdapter.ViewHolder> {
    private List<CouponListBean.ResultBean> list;
    private Context mContext;
    private ItemListener myListener;

    public CouponListDialogAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public CouponListDialogAdapter(List<CouponListBean.ResultBean> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    public void setMyListener(ItemListener myListener) {
        this.myListener = myListener;
    }

    public void setData(List<CouponListBean.ResultBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public CouponListDialogAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_dialog_coupon, parent, false);

//        int parentHeight = parent.getHeight();
//        parent.getWidth();
//        ViewGroup.LayoutParams layoutParams = parent.getLayoutParams();
//        layoutParams.height = (parentHeight / 2);

        return new CouponListDialogAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.setData(list.get(position));
//        if (list.get(position).isSelect()) {
//            holder.tv_name.setTextColor(Color.parseColor("#3977FE"));
//            holder.im_select.setVisibility(View.VISIBLE);
//        } else {
//            holder.tv_name.setTextColor(Color.parseColor("#333333"));
//            holder.im_select.setVisibility(View.GONE);
//        }
        holder.tv_name.setText(list.get(position).getName());
        holder.tv_condition.setText(list.get(position).getDesc());
        holder.tv_count.setText(list.get(position).getDiscount() + "元");
        holder.tv_data.setText(list.get(position).getStart_time().substring(0, 10) + "至"
                + list.get(position).getEnd_time().substring(0, 10));

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
        CouponListBean.ResultBean item;
        TextView tv_condition;
        TextView tv_count;
        TextView tv_name;
        TextView tv_data;
        LinearLayout rl;

        public ViewHolder(View itemView) {
            super(itemView);
            rl = (LinearLayout) itemView.findViewById(R.id.ll);
            tv_condition = (TextView) itemView.findViewById(R.id.tv_condition);
            tv_count = (TextView) itemView.findViewById(R.id.tv_count);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_data = (TextView) itemView.findViewById(R.id.tv_data);
            tv_name.setOnClickListener(this);
        }

        public void setData(CouponListBean.ResultBean item) {
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
        void onItemClick(CouponListBean.ResultBean item);
    }
}


