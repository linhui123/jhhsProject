package com.jhhscm.platform.adater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jhhscm.platform.R;
import com.jhhscm.platform.tool.CornerTransform;


public class NewCarGridViewAdater extends BaseAdapter{
    private Context mContext;
    CornerTransform transformation;
    private String url="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561956323436&di=63d2742860ab5212b5c76c7524d71ac4&imgtype=0&src=http%3A%2F%2Fproduct.21-sun.com%2Fuploadfiles%2Flgwjj%2F1322544268229.jpg";
    public NewCarGridViewAdater(Context context) {
        this.mContext=context;

    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      ViewHolder holder;
        if (convertView == null) {
            transformation = new CornerTransform(mContext, dip2px(mContext, 8));
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.new_car_gridview_item, null);
            holder.price_item_text = (TextView) convertView.findViewById(R.id.speed_item_text);
            holder.more_new_item_img=(ImageView)convertView.findViewById(R.id.more_new_item_img);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

       // holder.speed_item_text.setText(stringData[position]);
     /*   Glide.with(mContext)
                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561956323436&di=63d2742860ab5212b5c76c7524d71ac4&imgtype=0&src=http%3A%2F%2Fproduct.21-sun.com%2Fuploadfiles%2Flgwjj%2F1322544268229.jpg")
                .placeholder(R.mipmap.pic_load)
                .into(holder.more_new_item_img);*/
        //只是绘制左上角和右上角圆角
        transformation.setExceptCorner(false, false, true, true);

        Glide.with(mContext).
                load(url).
                asBitmap().
                skipMemoryCache(true).
                diskCacheStrategy(DiskCacheStrategy.NONE).
                transform(transformation).
                into(holder.more_new_item_img);

        return convertView;
    }
    class ViewHolder{

        TextView price_item_text;
        ImageView more_new_item_img;
    }
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
