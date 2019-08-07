package com.jhhscm.platform.photopicker.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.View;
import android.view.ViewGroup;

import com.jhhscm.platform.R;
import com.jhhscm.platform.adater.AbsAdapter;
import com.jhhscm.platform.adater.AbsViewHolder;
import com.jhhscm.platform.databinding.ItemPhotopickerFolderBinding;
import com.jhhscm.platform.photopicker.bean.Folder;
import com.jhhscm.platform.tool.StringUtils;
import com.nostra13.universalimageloader.core.ImageLoader;


/**
 * 类说明：
 * 作者：huangqiuxin on 2016/5/23 12:42
 * 邮箱：648859026@qq.com
 */
public class FolderAdapter extends AbsAdapter<Folder> {
    private Context context;
//    private DisplayImageOptions options;

    private int lastSelected = 0;

    public FolderAdapter(Context context) {
        super(context);
        this.context = context;
        // 使用DisplayImageOption.Builder()创建DisplayImageOptions
//        this.options = new DisplayImageOptions.Builder()
//                .showStubImage(R.mipmap.default_error) // 设置图片下载期间显示的图片
//                .showImageForEmptyUri(R.mipmap.default_error) // 设置图片Uri为空或是错误的时候显示的图片
//                .showImageOnFail(R.mipmap.default_error) // 设置图片加载或解码过程中发生错误显示的图片
//                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
//                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
//                // .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
//                .bitmapConfig(Bitmap.Config.ARGB_8888)
//                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
//                .build(); // 创建配置过的DisplayImageOption对象
    }

    public void setSelectIndex(int index) {
        if (lastSelected == index) return;

        lastSelected = index;
        for (int i=0;i<mData.size();i++){
            mData.get(i).isCheck = false;
        }
        try{
            mData.get(index-1).isCheck = true;
        }catch (Exception e){}
        notifyDataSetChanged();
    }

    public int getSelectIndex() {
        return lastSelected;
    }

    @Override
    public int getCount() {
        return mData.size() + 1;
    }

    @Override
    public Folder getItem(int position) {
        if (position == 0) {
            return null;
        }
        return mData.get(position - 1);
    }

    private int getTotalImageSize() {
        int result = 0;
        if (mData != null && mData.size() > 0) {
            for (Folder f : mData) {
                result += f.images.size();
            }
        }
        return result;
    }

    @Override
    protected AbsViewHolder onCreateViewHolder(ViewGroup parent, int itemType) {
        return new FolderViewHolder(mInflater.inflate(R.layout.item_photopicker_folder, parent, false));
    }

    private class FolderViewHolder extends AbsViewHolder<Folder> {
        private ItemPhotopickerFolderBinding mItemBinding;

        public FolderViewHolder(View convertView) {
            super(convertView);
            mItemBinding = DataBindingUtil.bind(convertView);
        }

        @Override
        protected void onBindView(Folder item) {
            if (item == null) {
                mItemBinding.tvName.setText(R.string.photopicker_all_image);
                mItemBinding.tvSize.setText(context.getString(R.string.photopicker_size, getTotalImageSize()));
                mItemBinding.ivCheck.setVisibility(lastSelected == 0 ? View.VISIBLE : View.INVISIBLE);
                if (mData.size() > 0) {
                    Folder f = mData.get(0);
//                    ImageLoader.getInstance().displayImage(f.cover.path, mItemBinding.ivCover);
                }
            } else {
                mItemBinding.tvName.setText(StringUtils.filterNullAndTrim(item.name));
                mItemBinding.tvSize.setText(context.getString(R.string.photopicker_size, item.images.size()));
                mItemBinding.ivCheck.setVisibility(item.isCheck ? View.VISIBLE : View.INVISIBLE);
                ImageLoader.getInstance().displayImage(item.cover.path, mItemBinding.ivCover);
            }
        }
    }

}
