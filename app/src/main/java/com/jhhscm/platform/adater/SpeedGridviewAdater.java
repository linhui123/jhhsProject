package com.jhhscm.platform.adater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhhscm.platform.R;

public class SpeedGridviewAdater extends BaseAdapter{
    private Context mContext;
    private String stringData[]={"设备交易","融资","售后","配件","劳务"};

    public SpeedGridviewAdater(Context context) {
        this.mContext=context;
    }

    @Override
    public int getCount() {
        return stringData.length;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.speed_gridview_item, null);
            holder.speed_item_img = (ImageView) convertView.findViewById(R.id.speed_item_img);
            holder.speed_item_text = (TextView) convertView.findViewById(R.id.speed_item_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.speed_item_text.setText(stringData[position]);

        return convertView;
    }
    class ViewHolder{
       ImageView speed_item_img;
       TextView speed_item_text;
    }
}
