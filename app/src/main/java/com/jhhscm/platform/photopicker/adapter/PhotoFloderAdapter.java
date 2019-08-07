package com.jhhscm.platform.photopicker.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.View;
import android.view.ViewGroup;

import com.jhhscm.platform.R;
import com.jhhscm.platform.adater.AbsAdapter;
import com.jhhscm.platform.adater.AbsViewHolder;
import com.jhhscm.platform.databinding.ItemPhotopickerFloderBinding;
import com.jhhscm.platform.photopicker.bean.PhotoFloder;
import com.jhhscm.platform.photopicker.util.ImageLoader;
import com.jhhscm.platform.photopicker.util.OtherUtils;
import com.jhhscm.platform.tool.FileUtils;
import com.jhhscm.platform.tool.StringUtils;


/**
 * 类说明：
 */
public class PhotoFloderAdapter extends AbsAdapter<PhotoFloder> {
    private int mWidth;

    public PhotoFloderAdapter(Context context) {
        super(context);
        mWidth = OtherUtils.dip2px(context, 90);
    }

    @Override
    protected AbsViewHolder onCreateViewHolder(ViewGroup parent, int itemType) {
        return new PhotoFloderViewHolder(mInflater.inflate(R.layout.item_photopicker_floder, parent, false));
    }

    public class PhotoFloderViewHolder extends AbsViewHolder<PhotoFloder> {
        private ItemPhotopickerFloderBinding mItemBinding;

        public PhotoFloderViewHolder(View convertView) {
            super(convertView);
            mItemBinding = DataBindingUtil.bind(convertView);
        }

        @Override
        protected void onBindView(PhotoFloder item) {
            mItemBinding.tvName.setText(StringUtils.filterNullAndTrim(item.getName()));
            mItemBinding.ivCheck.setVisibility(item.isSelected() ? View.VISIBLE : View.INVISIBLE);
            mItemBinding.tvSize.setText(convertView.getContext().getString(R.string.photopicker_size, item.getPhotoList().size()));
            ImageLoader.getInstance().display(FileUtils.getPhotoImagePath(item.getPhotoList().get(0).getPath()), mItemBinding.ivCover,
                    mWidth, mWidth);
        }
    }
}
