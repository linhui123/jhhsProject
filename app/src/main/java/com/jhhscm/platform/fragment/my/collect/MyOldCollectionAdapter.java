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

import com.jhhscm.platform.R;
import com.jhhscm.platform.views.slideswaphelper.SlideSwapAction;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MyOldCollectionAdapter extends RecyclerView.Adapter<MyOldCollectionAdapter.RecViewholder> {

    private Context context;
    private List<FindCollectListBean.DataBean> data = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private MyOldCollectionAdapter.DeletedItemListener deletedItemListener;

    public void setDeletedItemListener(MyOldCollectionAdapter.DeletedItemListener deletedItemListener) {
        this.deletedItemListener = deletedItemListener;
    }

    public MyOldCollectionAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setList(List<FindCollectListBean.DataBean> list, boolean refresh) {
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
    public MyOldCollectionAdapter.RecViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_my_collect, parent, false);
        return new MyOldCollectionAdapter.RecViewholder(view);
    }

    @Override
    public void onBindViewHolder(final MyOldCollectionAdapter.RecViewholder holder, final int position) {
        holder.rl_old.setVisibility(View.VISIBLE);
        ImageLoader.getInstance().displayImage(data.get(position).getPic_url(), holder.im_old);
        holder.tv_old_1.setText(data.get(position).getName());
        holder.tv_old_4.setText("询价");
        String time = data.get(position).getFactory_time() == null ? "" : data.get(position).getFactory_time() + "年 | ";
        String Old_time = data.get(position).getOld_time() == null ? "" : data.get(position).getOld_time() + "小时 | ";
        String Province = data.get(position).getProvince() == null ? "" : data.get(position).getProvince() + "-";
        String City = data.get(position).getCity() == null ? "" : data.get(position).getCity();
        holder.tv_old_2.setText(Old_time + Province + City);

        String Counter_price = data.get(position).getOriginal_price() == null ? "" : wan(data.get(position).getOriginal_price()) + "  ";
        String Retail_price = data.get(position).getOriginal_price() == null ? "" : "首付" + wan(data.get(position).getOriginal_price());

        holder.tv_old_3.setText(Counter_price + Retail_price);

        holder.slide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != deletedItemListener) {
                    deletedItemListener.deleted(data.get(position));
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

        public ImageView im_new;
        public ImageView im_old;
        public ImageView im_peijian;

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

        public RecViewholder(View itemView) {
            super(itemView);
            slide = (TextView) itemView.findViewById(R.id.item_slide);
            rl = (RelativeLayout) itemView.findViewById(R.id.rl);
            rl_new = (RelativeLayout) itemView.findViewById(R.id.rl_new);
            rl_old = (RelativeLayout) itemView.findViewById(R.id.rl_old);
            rl_peijian = (RelativeLayout) itemView.findViewById(R.id.rl_peijian);
            im_new = (ImageView) itemView.findViewById(R.id.im_new);
            im_old = (ImageView) itemView.findViewById(R.id.im_old);
            im_peijian = (ImageView) itemView.findViewById(R.id.im_peijian);

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
        void deleted(FindCollectListBean.DataBean resultBean);
    }

    private String wan(String toal) {
        DecimalFormat df = new DecimalFormat("#.0000");
        toal = df.format(Double.parseDouble(toal) / 10000);
        //保留2位小数
        BigDecimal b = new BigDecimal(Double.parseDouble(toal));
        toal = b.setScale(2, BigDecimal.ROUND_DOWN).toString() + "万";
        return toal;
    }
}

