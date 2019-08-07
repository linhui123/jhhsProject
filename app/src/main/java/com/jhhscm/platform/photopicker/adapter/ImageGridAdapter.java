package com.jhhscm.platform.photopicker.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.jhhscm.platform.R;
import com.jhhscm.platform.adater.AbsAdapter;
import com.jhhscm.platform.adater.AbsViewHolder;
import com.jhhscm.platform.databinding.ItemPhotopickerSelectImageBinding;
import com.jhhscm.platform.photopicker.bean.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * 类说明：
 * 作者：huangqiuxin on 2016/5/23 13:19
 * 邮箱：648859026@qq.com
 */
public class ImageGridAdapter extends AbsAdapter<Image> {
//    private LayoutInflater mInflater;
    private boolean showCamera = true;
    private boolean showSelectIndicator = true;

    private List<Image> mImages = new ArrayList<>();
    private List<Image> mSelectedImages = new ArrayList<>();

    private int mItemSize;
    private GridView.LayoutParams mItemLayoutParams;
//    private DisplayImageOptions options;

    public ImageGridAdapter(Context context, boolean showCamera, int itemSize) {
        super(context);
//        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.showCamera = showCamera;
        this.mItemSize = itemSize;
        mItemLayoutParams = new GridView.LayoutParams(mItemSize, mItemSize);

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

    /**
     * 显示选择指示器
     *
     * @param b
     */
    public void showSelectIndicator(boolean b) {
        showSelectIndicator = b;
    }

    public void setShowCamera(boolean b) {
        if (showCamera == b) return;

        showCamera = b;
        notifyDataSetChanged();
    }

    public boolean isShowCamera() {
        return showCamera;
    }

    /**
     * 选择某个图片，改变选择状态
     *
     * @param image
     */
    public void select(Image image) {
        boolean isCheck = false;
        if (mSelectedImages.contains(image)) {
            mSelectedImages.remove(image);
        } else {
            mSelectedImages.add(image);
            isCheck = true;
        }
        for (int i = 0; i < mImages.size(); i++) {
            Image temp = mImages.get(i);
            if (temp.equals(image)) {
                temp.isCheck = isCheck;
            }
        }
        notifyDataSetChanged();
    }

    public void removeImage(String image) {
        for (int i = 0; i < mSelectedImages.size(); i++) {
            if (mSelectedImages.get(i).path.equals(image)) {
                mSelectedImages.remove(i);
                break;
            }
        }
        for (int i = 0; i < mImages.size(); i++) {
            Image temp = mImages.get(i);
            if (temp.path.equals(image)) {
                temp.isCheck = false;
            }
        }
        notifyDataSetChanged();
    }

    /**
     * 通过图片路径设置默认选择
     *
     * @param resultList
     */
    public void setDefaultSelected(ArrayList<String> resultList) {
        mSelectedImages.clear();
        for (String path : resultList) {
            Image image = getImageByPath(path);
            if (image != null) {
                mSelectedImages.add(image);
            }
        }
        for (int i = 0; i < mImages.size(); i++) {
            Image temp = mImages.get(i);
            if (mSelectedImages.contains(temp)) {
                temp.isCheck = true;
            }
        }
        notifyDataSetChanged();
    }

    private Image getImageByPath(String path) {
        if (mImages != null && mImages.size() > 0) {
            for (Image image : mImages) {
                if (image.path.equalsIgnoreCase(path)) {
                    return image;
                }
            }
        }
        return null;
    }


    /**
     * 设置数据集
     *
     * @param images
     */
    public void setData(List<Image> images) {
        mSelectedImages.clear();

        if (images != null && images.size() > 0) {
            mImages = images;
        } else {
            mImages.clear();
        }
        notifyDataSetChanged();
    }

    /**
     * 重置每个Column的Size
     *
     * @param columnWidth
     */
    public void setItemSize(int columnWidth) {
        if (mItemSize == columnWidth) {
            return;
        }
        mItemSize = columnWidth;
        mItemLayoutParams = new GridView.LayoutParams(mItemSize, mItemSize);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return showCamera ? mImages.size() + 1 : mImages.size();
    }

    @Override
    public Image getItem(int i) {
        if (showCamera) {
            if (i == 0) {
                return null;
            }
            return mImages.get(i - 1);
        } else {
            return mImages.get(i);
        }
    }

    @Override
    protected AbsViewHolder onCreateViewHolder(ViewGroup parent, int itemType) {
        return new ImageViewHolder(mInflater.inflate(R.layout.item_photopicker_select_image, parent, false));
    }

    class ImageViewHolder extends AbsViewHolder<Image>{
        private ItemPhotopickerSelectImageBinding mBinding;
        public ImageViewHolder(View convertView) {
            super(convertView);
            GridView.LayoutParams lp = (GridView.LayoutParams) convertView.getLayoutParams();
            if (lp.height != mItemSize) {
                convertView.setLayoutParams(mItemLayoutParams);
            }
            mBinding = DataBindingUtil.bind(convertView);
        }

        @Override
        protected void onBindView(Image item) {
            if (item == null) {
                mBinding.flCamera.setVisibility(View.VISIBLE);
                return;
            } else {
                mBinding.flCamera.setVisibility(View.GONE);
            }
            // 处理单选和多选状态
            if (showSelectIndicator) {
                mBinding.chkBtn.setVisibility(View.VISIBLE);
                mBinding.chkBtn.setChecked(item.isCheck);
                mBinding.vMask.setVisibility(item.isCheck ? View.VISIBLE : View.GONE);
            } else {
                mBinding.chkBtn.setVisibility(View.GONE);
            }

//            ImageLoader.getInstance().display(recommend.path, mBinding.ivAlbum, mItemSize, mItemSize);
        }
    }

//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        ImageViewHolder holder;
//        if (view == null) {
//            view = mInflater.inflate(R.layout.item_photopicker_select_image, viewGroup, false);
//            holder = new ImageViewHolder(view);
//            view.setTag(holder);
//        } else {
//            holder = (ImageViewHolder) view.getTag();
//        }
//        holder.onBindView(getItem(i));
//        /** Fixed View Size */
//        GridView.LayoutParams lp = (GridView.LayoutParams) view.getLayoutParams();
//        if (lp.height != mItemSize) {
//            view.setLayoutParams(mItemLayoutParams);
//        }
//        return view;
//    }

//    class ImageViewHolder {
//        private ItemPhotopickerSelectImageBinding mBinding;
//
//        public ImageViewHolder(View convertView) {
//            mBinding = DataBindingUtil.bind(convertView);
//        }
//
//        public void onBindView(Image recommend) {
//            if (recommend == null) {
//                mBinding.flCamera.setVisibility(View.VISIBLE);
//                return;
//            } else {
//                mBinding.flCamera.setVisibility(View.GONE);
//            }
//            // 处理单选和多选状态
//            if (showSelectIndicator) {
//                mBinding.chkBtn.setVisibility(View.VISIBLE);
//                mBinding.chkBtn.setChecked(recommend.isCheck);
//                mBinding.vMask.setVisibility(recommend.isCheck ? View.VISIBLE : View.GONE);
//            } else {
//                mBinding.chkBtn.setVisibility(View.GONE);
//            }
//
//            ImageLoader.getInstance().displayImage(recommend.path, mBinding.ivAlbum, options);
//        }
//    }
}
