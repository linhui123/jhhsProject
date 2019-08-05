package com.jhhscm.platform.views.dlflipviewpage.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jhhscm.platform.views.CircleImageView;
import com.jhhscm.platform.views.dlflipviewpage.bean.DLGridViewBean;
import com.jhhscm.platform.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class DLGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<DLGridViewBean> mList;

    public DLGridViewAdapter(Context context, List<DLGridViewBean> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder;
        if (convertView == null) {
            mHolder = new ViewHolder();
//            convertView = View.inflate(mContext, R.layout.item_main_mode,null);
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_main_mode, parent, false);
            mHolder.iv_img = (CircleImageView) convertView.findViewById(R.id.iv_img);
            mHolder.tv_text = (TextView) convertView.findViewById(R.id.tv_text);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        DLGridViewBean bean = mList.get(position);
        if (bean != null) {
            mHolder.tv_text.setText(bean.getText());
//            try {
                ImageLoader.getInstance().displayImage(bean.getImg(), mHolder.iv_img);
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                mHolder.iv_img.setImageResource(R.mipmap.ic_home_jixie);
//            }
        }
        return convertView;
    }

    private class ViewHolder {
        private CircleImageView iv_img;
        private TextView tv_text;
        // private RelativeLayout rl_item;
    }
}
