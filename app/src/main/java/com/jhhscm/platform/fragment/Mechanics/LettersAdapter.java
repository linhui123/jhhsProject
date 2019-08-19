package com.jhhscm.platform.fragment.Mechanics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.MechanicsByBrandActivity;
import com.jhhscm.platform.event.BrandResultEvent;
import com.jhhscm.platform.event.FinishEvent;
import com.jhhscm.platform.event.JumpEvent;
import com.jhhscm.platform.fragment.Mechanics.bean.FindBrandBean;
import com.jhhscm.platform.tool.EventBusUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class LettersAdapter extends BaseAdapter {
    private int type = 1;// 1 选择品牌； 2选择机型
    //上下文
    private Context mContext;
    //数据集
    private List<FindBrandBean.ResultBean> mList;
    //布局加载器
    private LayoutInflater mInflater;
    //实体类
    private FindBrandBean.ResultBean model;

    public LettersAdapter(Context mContext, List<FindBrandBean.ResultBean> mList, int t) {
        this.mContext = mContext;
        this.mList = mList;
        this.type = t;
        //获取系统服务
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder vHolder = null;
        if (view == null) {
            vHolder = new ViewHolder();
            //加载布局
            view = mInflater.inflate(R.layout.item_mechanics_brand, null);
            vHolder.tvLetters = (TextView) view.findViewById(R.id.tvLetters);
            vHolder.imageView = (ImageView) view.findViewById(R.id.im);
            vHolder.tvName = (TextView) view.findViewById(R.id.tv_title);
            vHolder.line = (View) view.findViewById(R.id.line);
            vHolder.rl = (RelativeLayout) view.findViewById(R.id.rl);
            view.setTag(vHolder);
        } else {
            vHolder = (ViewHolder) view.getTag();
        }
        //选中下标
        model = mList.get(i);
        //获取首字母显示人
        int firstPosition = getNmaeForPosition(i);
        //第一个
        int index = getPositionForNmae(firstPosition);
        //需要显示字母
        if (index == i) {
            vHolder.line.setVisibility(View.GONE);
            vHolder.tvLetters.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(model.getPic_url(), vHolder.imageView);
            vHolder.tvLetters.setText(model.getLetter());
        } else {
            vHolder.tvLetters.setVisibility(View.GONE);
            vHolder.line.setVisibility(View.VISIBLE);
        }
        vHolder.tvName.setText(model.getName());
        vHolder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 1) {// 1 选择品牌；
                    EventBusUtil.post(new BrandResultEvent(mList.get(i).getId(), mList.get(i).getName()));
                    EventBusUtil.post(new FinishEvent());
                } else if (type == 2) {//2 跳转新机列表;
                    EventBusUtil.post(new FinishEvent());
                    EventBusUtil.post(new JumpEvent("MECHANICAL", mList.get(i).getId(), mList.get(i).getName()));
                } else if (type == 3) {//3选择机型返回
                    MechanicsByBrandActivity.start(mContext, mList.get(i).getId(), 0);
                }
            }
        });
        return view;
    }

    /**
     * 缓存优化
     */
    class ViewHolder {
        private ImageView imageView;
        private RelativeLayout rl;
        private TextView tvLetters;
        private TextView tvName;
        private View line;
    }

    /**
     * 通过首字母获取该首字母要显示的第一个人的下标
     *
     * @param position
     * @return
     */
    public int getPositionForNmae(int position) {
        for (int i = 0; i < getCount(); i++) {
            String letter = mList.get(i).getLetter();
            //首字母显示
            char firstChar = letter.toUpperCase().charAt(0);
            if (firstChar == position) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 根据名称拿到下标
     *
     * @param position
     * @return
     */
    private int getNmaeForPosition(int position) {
        return mList.get(position).getLetter().charAt(0);
    }

}

