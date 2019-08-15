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
import com.jhhscm.platform.activity.MechanicsH5Activity;
import com.jhhscm.platform.fragment.GoodsToCarts.GetCartGoodsByUserCodeBean;
import com.jhhscm.platform.fragment.GoodsToCarts.adapter.RecOtherTypeAdapter;
import com.jhhscm.platform.fragment.Mechanics.bean.GetGoodsByBrandBean;
import com.jhhscm.platform.fragment.Mechanics.bean.GetGoodsPageListBean;
import com.jhhscm.platform.tool.UrlUtils;
import com.jhhscm.platform.views.slideswaphelper.SlideSwapAction;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.jhhscm.platform.fragment.Mechanics.MechanicsItem.getGoodsPageListBean;

public class CompairsonAdapter extends RecyclerView.Adapter<CompairsonAdapter.RecViewholder> {

    private Context context;
    private List<GetGoodsPageListBean.DataBean> data = new ArrayList<>();
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

    public void setList(List<GetGoodsPageListBean.DataBean> list, boolean refresh) {
        if (refresh) {
            data.clear();
        }
        data.addAll(list);
        notifyDataSetChanged();
    }

    public void setData(GetGoodsPageListBean.DataBean resultBean) {
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

        if (data.get(position).getCounter_price()!=null){
            holder.tvPrice.setText(wan(data.get(position).getCounter_price()));
        }else {
            holder.tvPrice.setText("暂无报价");
        }

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
                    selectedListener.select(data);
                }
            }
        });


        holder.slide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != deletedItemListener) {
                    deletedItemListener.deleted(data, position);
                }
            }
        });

        holder.tvDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = UrlUtils.XJXQ + "&good_code=" + data.get(position).getGood_code();
                MechanicsH5Activity.start(context, url, "新机详情",
                        data.get(position).getGood_code(), data.get(position).getName(), data.get(position).getPic_url(),1);
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
        GetGoodsPageListBean.DataBean item;
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

        public void setData(GetGoodsPageListBean.DataBean item) {
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
        void deleted(List<GetGoodsPageListBean.DataBean> resultBeans, int position);
    }

    public interface CountChangeListener {
        void changeCount(List<GetGoodsPageListBean.DataBean> resultBeans, int position);
    }

    public interface SelectedListener {
        void select(List<GetGoodsPageListBean.DataBean> resultBeans);
    }

    private String wan(String toal) {
        DecimalFormat df = new DecimalFormat("#.00");
//        toal = df.format(Double.parseDouble(toal) / 10000);
        //保留2位小数
        BigDecimal b = new BigDecimal(Double.parseDouble(toal));
        if (b.compareTo(new BigDecimal(Double.parseDouble("0"))) == 0) {
            toal = "暂无报价";
        } else {
            toal = "￥" + b.setScale(2, BigDecimal.ROUND_DOWN).toString() + "万";
        }
        return toal;
    }
}

