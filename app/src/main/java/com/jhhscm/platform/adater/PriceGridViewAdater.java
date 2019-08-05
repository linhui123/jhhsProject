package com.jhhscm.platform.adater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhhscm.platform.R;

public class PriceGridViewAdater extends BaseAdapter{
    private Context mContext;
    //private String stringData[]={"10万以下","10-20万","20-30万","30-50万","50万以上","小挖\n6吨以下","小挖\n6-9吨","中挖\n10-19吨","中挖\n20-29吨","大挖\n30吨以上","小松","三一重工","卡特彼勒","神钢","查看全部"};

    public PriceGridViewAdater(Context context) {
        this.mContext=context;
    }

    @Override
    public int getCount() {
        return 3;
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
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.price_grid_item, null);
            holder.hot_item_name = (TextView) convertView.findViewById(R.id.hot_item_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //holder.hot_item_name.setText(stringData[position]);

        return convertView;
    }
    class ViewHolder{
        TextView hot_item_type;
       TextView hot_item_name;
       ImageView hot_item_img;
    }
}
