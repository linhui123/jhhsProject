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
import com.jhhscm.platform.fragment.GoodsToCarts.GetCartGoodsByUserCodeBean;
import com.jhhscm.platform.fragment.GoodsToCarts.adapter.RecOtherTypeAdapter;
import com.jhhscm.platform.fragment.Mechanics.bean.GetGoodsByBrandBean;
import com.jhhscm.platform.views.slideswaphelper.SlideSwapAction;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class CompairsonAdapter extends RecyclerView.Adapter<CompairsonAdapter.RecViewholder> {

    private Context context;
    private List<GetGoodsByBrandBean.ResultBean> data = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private DeletedItemListener deletedItemListener;
    private CountChangeListener changeListener;
    private SelectedListener selectedListener;

    public void setDeletedItemListener(DeletedItemListener deletedItemListener) {
        this.deletedItemListener = deletedItemListener;
    }

    public void setChangeListener(CountChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    public void setSelectedListener(SelectedListener selectedListener) {
        this.selectedListener = selectedListener;
    }

    public CompairsonAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setList(List<GetGoodsByBrandBean.ResultBean> list, boolean refresh) {
        if (refresh) {
            data.clear();
        }
        data.addAll(list);
        notifyDataSetChanged();
    }

    public void setData(GetGoodsByBrandBean.ResultBean resultBean) {
        data.add(resultBean);
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
    public RecViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_compairson_select, parent, false);
        return new RecViewholder(view);
    }

    @Override
    public void onBindViewHolder(final RecViewholder holder, final int position) {
        holder.setData(data.get(position));
        holder.tvTitle.setText(data.get(position).getName());
        holder.tvPrice.setText("￥" + data.get(position).getCounterPrice());
        if (data.get(position).isSelect()) {
            holder.tvSelect.setImageResource(R.mipmap.ic_shoping_s1);
        } else {
            holder.tvSelect.setImageResource(R.mipmap.ic_shoping_s);
        }

        holder.tvSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.get(position).isSelect()) {
                    data.get(position).setSelect(false);
                    holder.tvSelect.setImageResource(R.mipmap.ic_shoping_s);
                } else {
                    data.get(position).setSelect(true);
                    holder.tvSelect.setImageResource(R.mipmap.ic_shoping_s1);
                }
                if (null != selectedListener) {
                    selectedListener.select(data.get(position));
                }
            }
        });


        holder.slide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != deletedItemListener) {
                    deletedItemListener.deleted(data.get(position));
                }
            }
        });

        holder.tvDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        GetGoodsByBrandBean.ResultBean item;
        public TextView slide;
        public RelativeLayout relativeLayout;
        public ImageView tvSelect;
        public TextView tvTitle;
        public TextView tvPrice;
        public TextView tvDetail;

        public RecViewholder(View itemView) {
            super(itemView);
            slide = (TextView) itemView.findViewById(R.id.item_slide);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.rl);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvDetail = (TextView) itemView.findViewById(R.id.tv_detail);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
            tvSelect = (ImageView) itemView.findViewById(R.id.tv_select);
        }

        @Override
        public float getActionWidth() {
            return dip2px(slide.getContext(), 100);
        }

        public void setData(GetGoodsByBrandBean.ResultBean item) {
            this.item = item;
        }

        @Override
        public View ItemView() {
            return relativeLayout;
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
        void deleted(GetGoodsByBrandBean.ResultBean resultBean);
    }

    public interface CountChangeListener {
        void changeCount(List<GetGoodsByBrandBean.ResultBean> resultBeans, int position);
    }

    public interface SelectedListener {
        void select(GetGoodsByBrandBean.ResultBean resultBean);
    }
}

