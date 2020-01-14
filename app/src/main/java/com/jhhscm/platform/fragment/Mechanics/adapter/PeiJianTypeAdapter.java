package com.jhhscm.platform.fragment.Mechanics.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jhhscm.platform.R;
import com.jhhscm.platform.fragment.Mechanics.bean.BrandModelBean;
import com.jhhscm.platform.fragment.Mechanics.bean.FindBrandBean;
import com.jhhscm.platform.fragment.Mechanics.bean.GoodsCatatoryListBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class PeiJianTypeAdapter extends RecyclerView.Adapter<PeiJianTypeAdapter.ViewHolder> {
    private List<GoodsCatatoryListBean.DataBean.SecendBrandListBean> list;
    private Context mContext;
    private PeiJianTypeAdapter.ItemListener myListener;

    public PeiJianTypeAdapter(List<GoodsCatatoryListBean.DataBean.SecendBrandListBean> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    public void setMyListener(PeiJianTypeAdapter.ItemListener myListener) {
        this.myListener = myListener;
    }

    @Override
    public PeiJianTypeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new PeiJianTypeAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_peijian_type, parent, false));

    }

    @Override
    public void onBindViewHolder(PeiJianTypeAdapter.ViewHolder holder, final int position) {
        holder.setData(list.get(position));
        ImageLoader.getInstance().displayImage(list.get(position).getPic_url(), holder.im_brand);
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
        GoodsCatatoryListBean.DataBean.SecendBrandListBean item;
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

        public void setData(GoodsCatatoryListBean.DataBean.SecendBrandListBean item) {
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
        void onItemClick(GoodsCatatoryListBean.DataBean.SecendBrandListBean item);
    }
}

