package com.jhhscm.platform.fragment.my.collect;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.StoreDetailActivity;
import com.jhhscm.platform.activity.h5.MechanicsH5Activity;
import com.jhhscm.platform.event.ConsultationEvent;
import com.jhhscm.platform.fragment.aftersale.AfterSaleViewHolder;
import com.jhhscm.platform.fragment.aftersale.FindBusListBean;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.UdaUtils;
import com.jhhscm.platform.tool.UrlUtils;
import com.jhhscm.platform.views.slideswaphelper.SlideSwapAction;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class MyBusCollectionAdapter extends RecyclerView.Adapter<MyBusCollectionAdapter.RecViewholder> {

    private Context context;
    private List<FindBusListBean.DataBean> data = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private DeletedItemListener deletedItemListener;

    public void setDeletedItemListener(DeletedItemListener deletedItemListener) {
        this.deletedItemListener = deletedItemListener;
    }

    public MyBusCollectionAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setList(List<FindBusListBean.DataBean> list, boolean refresh) {
        if (refresh) {
            data.clear();
        }
        data.addAll(list);
        notifyDataSetChanged();
    }

    public void removeDataByPosition(int position) {
        if (position >= 0 && position < data.size()) {
            data.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, data.size() - 1);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public RecViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_my_collect, parent, false);
        return new RecViewholder(view);
    }

    @Override
    public void onBindViewHolder(final MyBusCollectionAdapter.RecViewholder holder, final int position) {
        final FindBusListBean.DataBean item = data.get(position);
        holder.rl_bus.setVisibility(View.VISIBLE);
        ImageLoader.getInstance().displayImage(item.getPic_url(), holder.im_bus);

        if (position == 0) {
            holder.tv_3.setVisibility(View.VISIBLE);
        } else {
            holder.tv_3.setVisibility(View.GONE);
        }
        holder.tv_1.setText(item.getBus_name());
        String location = "";
        if (item.getProvince_name() != null) {
            location = item.getProvince_name() + " ";
        }
        if (item.getCity_name() != null) {
            location = location + item.getCity_name() + " ";
        }
        if (item.getCounty_name() != null) {
            location = location + item.getCounty_name() + " ";
        }
        if (item.getAddress_detail() != null) {
            location = location + item.getAddress_detail() + " ";
        }
        holder.tv_2.setText("地址：" + location);
        if (item.getMobile() != null) {
            holder.tv_4.setText("电话：" + UdaUtils.hiddenPhoneNumber(item.getMobile()));
        } else {
            holder.tv_4.setText("电话：--");
        }
        if (item.getDistance() != null) {
            holder.tv_store.setVisibility(View.VISIBLE);
            holder.tv_store.setText("距离" + item.getDistance() + "km");
        } else {
            holder.tv_store.setVisibility(View.GONE);
        }

        if (item.getPic_url() != null) {
            String jsonString = "{\"pic_url\":" + item.getPic_url() + "}";
            Log.e("jsonString", "jsonString  " + jsonString);
            AfterSaleViewHolder.PicBean pic = JSON.parseObject(jsonString, AfterSaleViewHolder.PicBean.class);
            if (pic != null
                    && pic.getPic_url() != null && pic.getPic_url().size() > 0) {
                ImageLoader.getInstance().displayImage(pic.getPic_url().get(0).getUrl(), holder.im_bus);
            }
        }

        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 0) {
                    StoreDetailActivity.start(context, item.getBus_code(), "", "", true);
                } else {
                    StoreDetailActivity.start(context, item.getBus_code(), "", "");
                }
            }
        });

        holder.slide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != deletedItemListener) {
                    deletedItemListener.deleted(item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    /**
     * view.getWidth()获取的是屏幕中可以看到的大小.
     */
    public class RecViewholder extends RecyclerView.ViewHolder implements SlideSwapAction {
        FindCollectListBean.DataBean item;
        public TextView slide;
        public RelativeLayout rl;
        public RelativeLayout rl_new;
        public RelativeLayout rl_old;
        public RelativeLayout rl_peijian;
        public RelativeLayout rl_bus;

        public ImageView im_new;
        public ImageView im_old;
        public ImageView im_peijian;
        public ImageView im_bus;

        public TextView tv_new_1;
        public TextView tv_old_1;
        public TextView tv_peijian_1;
        public TextView tv_new_2;
        public TextView tv_old_2;
        public TextView tv_peijian_2;
        public TextView tv_new_3;
        public TextView tv_old_3;
        public TextView tv_peijian_3;
        public TextView tv_new_4;
        public TextView tv_old_4;
        public TextView tv_1;
        public TextView tv_2;
        public TextView tv_3;
        public TextView tv_4;
        public TextView tv_store;

        public RecViewholder(View itemView) {
            super(itemView);
            slide = (TextView) itemView.findViewById(R.id.item_slide);
            rl = (RelativeLayout) itemView.findViewById(R.id.rl);
            rl_new = (RelativeLayout) itemView.findViewById(R.id.rl_new);
            rl_old = (RelativeLayout) itemView.findViewById(R.id.rl_old);
            rl_peijian = (RelativeLayout) itemView.findViewById(R.id.rl_peijian);
            rl_bus = (RelativeLayout) itemView.findViewById(R.id.rl_bus);

            im_new = (ImageView) itemView.findViewById(R.id.im_new);
            im_old = (ImageView) itemView.findViewById(R.id.im_old);
            im_peijian = (ImageView) itemView.findViewById(R.id.im_peijian);
            im_bus = (ImageView) itemView.findViewById(R.id.im);

            tv_new_1 = (TextView) itemView.findViewById(R.id.tv_new_1);
            tv_old_1 = (TextView) itemView.findViewById(R.id.tv_old_1);
            tv_peijian_1 = (TextView) itemView.findViewById(R.id.tv_peijian_1);

            tv_new_2 = (TextView) itemView.findViewById(R.id.tv_new_2);
            tv_old_2 = (TextView) itemView.findViewById(R.id.tv_old_2);
            tv_peijian_2 = (TextView) itemView.findViewById(R.id.tv_peijian_2);

            tv_new_3 = (TextView) itemView.findViewById(R.id.tv_new_3);
            tv_old_3 = (TextView) itemView.findViewById(R.id.tv_old_3);
            tv_peijian_3 = (TextView) itemView.findViewById(R.id.tv_peijian_3);

            tv_new_4 = (TextView) itemView.findViewById(R.id.tv_new_4);
            tv_old_4 = (TextView) itemView.findViewById(R.id.tv_old_4);

            tv_1 = (TextView) itemView.findViewById(R.id.tv_1);
            tv_2 = (TextView) itemView.findViewById(R.id.tv_2);
            tv_3 = (TextView) itemView.findViewById(R.id.tv_3);
            tv_4 = (TextView) itemView.findViewById(R.id.tv_4);
            tv_store = (TextView) itemView.findViewById(R.id.tv_store);
        }

        @Override
        public float getActionWidth() {
            return dip2px(slide.getContext(), 100);
        }

        public void setData(FindCollectListBean.DataBean item) {
            this.item = item;
        }

        @Override
        public View ItemView() {
            return rl;
        }

    }

    /**
     * 根据手机分辨率从DP转成PX
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public interface DeletedItemListener {
        void deleted(FindBusListBean.DataBean resultBean);
    }

}
