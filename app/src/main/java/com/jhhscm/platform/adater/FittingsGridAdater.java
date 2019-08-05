package com.jhhscm.platform.adater;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhhscm.platform.R;

public class FittingsGridAdater extends BaseAdapter{
    private Context mContext;
    public FittingsGridAdater(Context context) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fittings_grid_item, null);
            holder.fittings_grid_name = (TextView) convertView.findViewById(R.id.fittings_grid_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //holder.hot_item_name.setText(stringData[position]);

        return convertView;
    }
    class ViewHolder{
        TextView fittings_grid_name;
        TextView fittings_grid_title;
        ImageView fittings_grid_img;
    }
}
