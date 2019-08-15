package com.jhhscm.platform.fragment.Mechanics.adapter;

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
import com.jhhscm.platform.fragment.Mechanics.action.FindBrandAction;
import com.jhhscm.platform.fragment.Mechanics.bean.FindBrandBean;
import com.jhhscm.platform.fragment.Mechanics.bean.GetComboBoxBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.ViewHolder> {
    private List<FindBrandBean.ResultBean> list;
    private Context mContext;
    private BrandAdapter.ItemListener myListener;

    public BrandAdapter(List<FindBrandBean.ResultBean> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    public void setMyListener(BrandAdapter.ItemListener myListener) {
        this.myListener = myListener;
    }

    @Override
    public BrandAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BrandAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_drop_brand, parent, false));
    }

    @Override
    public void onBindViewHolder(BrandAdapter.ViewHolder holder, final int position) {
        holder.setData(list.get(position));
        if (list.get(position).getPic_url()!=null){
            ImageLoader.getInstance().displayImage(list.get(position).getPic_url(), holder.im_brand);
        }else {
            holder.im_brand.setVisibility(View.INVISIBLE);
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
        FindBrandBean.ResultBean item;
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

        public void setData(FindBrandBean.ResultBean item) {
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
        void onItemClick(FindBrandBean.ResultBean item);

    }
}

