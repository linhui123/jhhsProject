package com.jhhscm.platform.adater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jhhscm.platform.R;

public class LabourServiceAdater extends BaseAdapter{
    private Context mContext;
    public LabourServiceAdater(Context context) {
        this.mContext=context;
    }
    @Override
    public int getCount() {
        return 5;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.labour_service_item, null);
            holder.labour_service_item_txt = (TextView) convertView.findViewById(R.id.labour_service_item_txt);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //holder.hot_item_name.setText(stringData[position]);

        return convertView;
    }
    class ViewHolder{
        TextView labour_service_item_txt;
    }
}
