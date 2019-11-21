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
import com.jhhscm.platform.event.GetCouponEvent;
import com.jhhscm.platform.event.GetRegionEvent;
import com.jhhscm.platform.fragment.Mechanics.bean.GetRegionBean;
import com.jhhscm.platform.fragment.address.LocationAdapter;
import com.jhhscm.platform.tool.EventBusUtil;

import java.util.List;

public class CouponListDialogAdapter extends RecyclerView.Adapter<CouponListDialogAdapter.ViewHolder> {
    private List<GetNewCouponslistBean.ResultBean.DataBean> list;
    private Context mContext;
    private ItemListener myListener;

    public CouponListDialogAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public CouponListDialogAdapter(List<GetNewCouponslistBean.ResultBean.DataBean> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    public void setMyListener(ItemListener myListener) {
        this.myListener = myListener;
    }

    public void setData(List<GetNewCouponslistBean.ResultBean.DataBean> list) {
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
        if (list.get(position).isSelect()) {
            holder.tv_receive.setTextColor(Color.parseColor("#333333"));
            holder.tv_receive.setBackgroundResource(R.drawable.edit_bg_acc9);
            holder.tv_receive.setText("已领取");
        } else {
            holder.tv_receive.setTextColor(Color.parseColor("#3977FE"));
            holder.tv_receive.setBackgroundResource(R.drawable.edit_bg_397);
            holder.tv_receive.setText("领取");
        }
        holder.tv_name.setText(list.get(position).getName());
        holder.tv_condition.setText(list.get(position).getDesc());
        holder.tv_count.setText(list.get(position).getDiscount() + "元");
        if (list.get(position).getStartTime() != null
                && list.get(position).getStartTime().length() > 10
                && list.get(position).getEndTime() != null
                && list.get(position).getEndTime().length() > 10) {
            holder.tv_data.setText(list.get(position).getStartTime().substring(0, 10) + "至"
                    + list.get(position).getEndTime().substring(0, 10));
        }


        holder.tv_receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.get(position).setSelect(true);
                EventBusUtil.post(new GetCouponEvent(list.get(position).getCode(), list.get(position).getStartTime(), list.get(position).getEndTime()));
//                if (myListener != null) {
//                    myListener.onItemClick(list.get(position));
//                }
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
        GetNewCouponslistBean.ResultBean.DataBean item;
        TextView tv_condition;
        TextView tv_count;
        TextView tv_receive;
        TextView tv_name;
        TextView tv_data;
        LinearLayout rl;

        public ViewHolder(View itemView) {
            super(itemView);
            rl = (LinearLayout) itemView.findViewById(R.id.ll);
            tv_condition = (TextView) itemView.findViewById(R.id.tv_condition);
            tv_count = (TextView) itemView.findViewById(R.id.tv_count);
            tv_receive = (TextView) itemView.findViewById(R.id.tv_receive);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_data = (TextView) itemView.findViewById(R.id.tv_data);
            tv_name.setOnClickListener(this);
        }

        public void setData(GetNewCouponslistBean.ResultBean.DataBean item) {
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
        void onItemClick(GetNewCouponslistBean.ResultBean.DataBean item);
    }
}


