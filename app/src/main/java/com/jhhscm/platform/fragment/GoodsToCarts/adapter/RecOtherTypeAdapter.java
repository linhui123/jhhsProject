package com.jhhscm.platform.fragment.GoodsToCarts.adapter;

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
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.views.slideswaphelper.SlideSwapAction;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by WANG on 18/4/24.
 */

public class RecOtherTypeAdapter extends RecyclerView.Adapter<RecOtherTypeAdapter.RecViewholder> {

    private Context context;
    private List<GetCartGoodsByUserCodeBean.ResultBean> data = new ArrayList<>();
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

    public RecOtherTypeAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setList(List<GetCartGoodsByUserCodeBean.ResultBean> list, boolean refresh) {
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
    public RecViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.layout_item, parent, false);
        return new RecViewholder(view);
    }

    @Override
    public void onBindViewHolder(final RecViewholder holder, final int position) {
        holder.setData(data.get(position));
        final int count = Integer.parseInt(data.get(position).getNumber());
        holder.tvNum.setText(data.get(position).getNumber());
        ImageLoader.getInstance().displayImage(data.get(position).getPicUrl(), holder.im);
        holder.tvTitle.setText(data.get(position).getGoodsName());
        holder.tvPrice.setText("￥" + data.get(position).getPrice());

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

        holder.imJia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(holder.tvNum.getText().toString()) + 1;
                holder.tvNum.setText(num + "");
                data.get(position).setNumber(num + "");
                if (null != changeListener) {
                    changeListener.changeCount(data, position);
                }
            }
        });

        holder.imJian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(holder.tvNum.getText().toString()) > 1) {
                    int num = Integer.parseInt(holder.tvNum.getText().toString()) - 1;
                    holder.tvNum.setText(num + "");
                    data.get(position).setNumber(num + "");
                    if (null != changeListener) {
                        changeListener.changeCount(data, position);
                    }
                } else {
                    ToastUtil.show(context, "商品数量不能为空！");
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
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    /**
     * view.getWidth()获取的是屏幕中可以看到的大小.
     */
    public class RecViewholder extends RecyclerView.ViewHolder implements SlideSwapAction {
        GetCartGoodsByUserCodeBean.ResultBean item;
        public TextView slide;
        public RelativeLayout relativeLayout;
        public ImageView im;
        public ImageView imJia;
        public ImageView imJian;
        public TextView tvTitle;
        public ImageView tvSelect;
        public TextView tvNum;
        public TextView tvPrice;

        public RecViewholder(View itemView) {
            super(itemView);
            slide = (TextView) itemView.findViewById(R.id.item_slide);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.rl);

            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvSelect = (ImageView) itemView.findViewById(R.id.tv_select);
            tvNum = (TextView) itemView.findViewById(R.id.tv_num);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);

            im = (ImageView) itemView.findViewById(R.id.im);
            imJia = (ImageView) itemView.findViewById(R.id.im_jia);
            imJian = (ImageView) itemView.findViewById(R.id.im_jian);
        }

        @Override
        public float getActionWidth() {
            return dip2px(slide.getContext(), 100);
        }

        public void setData(GetCartGoodsByUserCodeBean.ResultBean item) {
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
        void deleted(GetCartGoodsByUserCodeBean.ResultBean resultBean);
    }

    public interface CountChangeListener {
        void changeCount(List<GetCartGoodsByUserCodeBean.ResultBean> resultBeans, int position);
    }

    public interface SelectedListener {
        void select(List<GetCartGoodsByUserCodeBean.ResultBean> resultBeans);
    }
}
