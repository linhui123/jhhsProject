package com.jhhscm.platform.shoppingcast.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jhhscm.platform.R;
import com.jhhscm.platform.fragment.GoodsToCarts.GetCartGoodsByUserCodeBean;
import com.jhhscm.platform.shoppingcast.callback.OnClickAddCloseListenter;
import com.jhhscm.platform.shoppingcast.callback.OnClickDeleteListenter;
import com.jhhscm.platform.shoppingcast.callback.OnClickListenterModel;
import com.jhhscm.platform.shoppingcast.callback.OnClickSideListenter;
import com.jhhscm.platform.shoppingcast.callback.OnViewItemClickListener;
import com.jhhscm.platform.shoppingcast.entity.CartInfo;
import com.jhhscm.platform.shoppingcast.widget.FrontViewToMove;
import com.jhhscm.platform.shoppingcast.widget.ZQRoundOvalImageView;
import com.jhhscm.platform.views.OvalImageView;
import com.nostra13.universalimageloader.core.ImageLoader;


import java.util.List;

import static com.jhhscm.platform.tool.DisplayUtils.dip2px;

/**
 * Created by zhangqie on 2016/11/26.
 */

public class CartExpandAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ListView listView;
    private List<GetCartGoodsByUserCodeBean.ResultBean> list;

    public CartExpandAdapter(Context context, ListView listView, List<GetCartGoodsByUserCodeBean.ResultBean> list) {
        super();
        this.context = context;
        this.listView = listView;
        this.list = list;
    }

    @Override
    public Object getChild(int arg0, int arg1) {
        // TODO Auto-generated method stub
        return list.get(arg0).getGoodsList().get(arg1);
    }

    @Override
    public long getChildId(int arg0, int arg1) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getChildView(final int groupPosition, final int position,
                             boolean arg2, View convertView, ViewGroup parent) {
        final ViewHolder1 viewHolder1;
        convertView = LayoutInflater.from(context).inflate(R.layout.layout_item, null);
        viewHolder1 = new ViewHolder1(convertView, groupPosition, position);

        //关键语句，使用自己写的类来对frontView的ontouch事件复写，实现视图滑动效果
        new FrontViewToMove(viewHolder1.frontView, listView, dip2px(context, 100));
        viewHolder1.textView.setText(list.get(groupPosition).getGoodsList().get(position).getGoodsName());
        viewHolder1.checkBox.setChecked(list.get(groupPosition).getGoodsList().get(position).isIscheck());
        viewHolder1.tvMoney.setText("¥ " + list.get(groupPosition).getGoodsList().get(position).getPrice());
        viewHolder1.btnNum.setText(list.get(groupPosition).getGoodsList().get(position).getNumber() + "");
//        viewHolder1.zqRoundOvalImageView.setType(ZQRoundOvalImageView.TYPE_ROUND);
//        viewHolder1.zqRoundOvalImageView.setRoundRadius(8);
//        Glide.with(context).load(list.get(groupPosition).getItems().get(position).getImage())
//                .placeholder(R.mipmap.image_error)
//                .error(R.mipmap.image_error).into(viewHolder1.zqRoundOvalImageView);

        ImageLoader.getInstance().displayImage(list.get(groupPosition).getGoodsList().get(position).getPicUrl()
                , viewHolder1.zqRoundOvalImageView);
        viewHolder1.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListenterModel.onItemClick(viewHolder1.checkBox.isChecked(), v, groupPosition, position);
            }
        });
        viewHolder1.button.setOnClickListener(new View.OnClickListener() {
            // 为button绑定事件，可以用此按钮来实现删除事件
            @Override
            public void onClick(View v) {
                onClickDeleteListenter.onItemClick(v, groupPosition, position);
                new FrontViewToMove(viewHolder1.frontView, listView, 200).generateRevealAnimate(viewHolder1.frontView, 0);
            }
        });
        return convertView;
    }

    class ViewHolder1 implements View.OnClickListener {
        private int groupPosition;
        private int position;
        private TextView textView;
        private View frontView;
        private Button button;
        private CheckBox checkBox;
        private OvalImageView zqRoundOvalImageView;
        private TextView tvMoney;
        private ImageView btnAdd;
        private TextView btnNum;
        private ImageView btnClose;

        public ViewHolder1(View view, int groupPosition, int position) {
            this.groupPosition = groupPosition;
            this.position = position;
            zqRoundOvalImageView = (OvalImageView) view.findViewById(R.id.im);
            textView = (TextView) view.findViewById(R.id.tv_title);
            checkBox = (CheckBox) view.findViewById(R.id.item_chlid_check);
            button = (Button) view.findViewById(R.id.btn_delete);
            frontView = view.findViewById(R.id.id_front);
            tvMoney = (TextView) view.findViewById(R.id.tv_price);
            btnAdd = (ImageView) view.findViewById(R.id.im_jia);
            btnAdd.setOnClickListener(this);
            btnNum = (TextView) view.findViewById(R.id.tv_num);
            btnClose = (ImageView) view.findViewById(R.id.im_jian);
            btnClose.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.im_jia:
                    onClickAddCloseListenter.onItemClick(v, 2, groupPosition, position, Integer.valueOf(btnNum.getText().toString()));
                    break;
                case R.id.im_jian:
                    onClickAddCloseListenter.onItemClick(v, 1, groupPosition, position, Integer.valueOf(btnNum.getText().toString()));
                    break;
            }
        }
    }

    // CheckBox1接口的方法
    private OnViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    // CheckBox2接口的方法
    private OnClickListenterModel onClickListenterModel = null;

    public void setOnClickListenterModel(OnClickListenterModel listener) {
        this.onClickListenterModel = listener;
    }

    // 删除接口的方法
    private OnClickDeleteListenter onClickDeleteListenter = null;

    public void setOnClickDeleteListenter(OnClickDeleteListenter listener) {
        this.onClickDeleteListenter = listener;
    }

    // 数量接口的方法
    private OnClickAddCloseListenter onClickAddCloseListenter = null;

    public void setOnClickAddCloseListenter(OnClickAddCloseListenter listener) {
        this.onClickAddCloseListenter = listener;
    }

    // 滑动触发接口的方法
    private OnClickSideListenter onClickSideListenter = null;

    public void setOnClickSideListenter(OnClickSideListenter listener) {
        this.onClickSideListenter = listener;
    }

    @Override
    public int getChildrenCount(int arg0) {
        // TODO Auto-generated method stub
        return (list != null && list.size() > 0) ? list.get(arg0).getGoodsList().size() : 0;
    }

    @Override
    public Object getGroup(int arg0) {
        // TODO Auto-generated method stub
        return list.get(arg0);
    }

    @Override
    public int getGroupCount() {
        // TODO Auto-generated method stub
        return (list != null && list.size() > 0) ? list.size() : 0;
    }

    @Override
    public long getGroupId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.cart_list_group_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (groupPosition == 0) {
            viewHolder.textTopBar.setVisibility(View.GONE);
        }
        GetCartGoodsByUserCodeBean.ResultBean dataBean = (GetCartGoodsByUserCodeBean.ResultBean) getGroup(groupPosition);
        viewHolder.textView.setText(dataBean.getBus_name());
        viewHolder.checkBox.setChecked(dataBean.isIscheck());
        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(viewHolder.checkBox.isChecked(), v, groupPosition);
            }
        });
        viewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "点击标题", Toast.LENGTH_LONG).show();

            }
        });
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isChildSelectable(int arg0, int arg1) {
        // TODO Auto-generated method stub
        return false;
    }

    class ViewHolder {
        CheckBox checkBox;
        TextView textView;
        TextView textTopBar;

        public ViewHolder(View view) {
            textView = (TextView) view.findViewById(R.id.shop_name);
            checkBox = (CheckBox) view.findViewById(R.id.check_box);
            textTopBar = (TextView) view.findViewById(R.id.item_group_topbar);
        }
    }
}
