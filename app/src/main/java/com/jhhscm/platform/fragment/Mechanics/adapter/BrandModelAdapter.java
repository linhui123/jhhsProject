package com.jhhscm.platform.fragment.Mechanics.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jhhscm.platform.R;
import com.jhhscm.platform.fragment.Mechanics.bean.BrandModel1Bean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class BrandModelAdapter extends RecyclerView.Adapter<BrandModelAdapter.ViewHolder> {
    private List<BrandModel1Bean.DataBean> list;
    private Context mContext;
    private ItemListener myListener;

    public BrandModelAdapter(List<BrandModel1Bean.DataBean> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    public void setMyListener(ItemListener myListener) {
        this.myListener = myListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_drop_brand, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.setData(list.get(position));
        if (list.get(position).getBrand_id().equals("-1")) {
            holder.im_brand.setImageResource(R.mipmap.ic_wakuanlai);
        } else if (list.get(position).getBrand_id().equals("")) {
            holder.im_brand.setImageResource(R.mipmap.ic_brand_all);
        } else {
            ImageLoader.getInstance().displayImage(list.get(position).getBrand_pic(), holder.im_brand);
        }

        holder.tv_name.setText(list.get(position).getBrand_name());
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        BrandModel1Bean.DataBean item;
        TextView tv_name;
        ImageView im_brand;
        RelativeLayout rl;

        public ViewHolder(View itemView) {
            super(itemView);
            rl = (RelativeLayout) itemView.findViewById(R.id.rl);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            im_brand = (ImageView) itemView.findViewById(R.id.im_brand);
            tv_name.setOnClickListener(this);
        }

        public void setData(BrandModel1Bean.DataBean item) {
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
        void onItemClick(BrandModel1Bean.DataBean item);

    }
}

