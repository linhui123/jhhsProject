package com.jhhscm.platform.views.selector.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.View;
import android.view.ViewGroup;

import com.jhhscm.platform.R;
import com.jhhscm.platform.adater.AbsAdapter;
import com.jhhscm.platform.adater.AbsViewHolder;
import com.jhhscm.platform.databinding.ItemImageSelectorAddBinding;
import com.jhhscm.platform.databinding.ItemImageSelectorBinding;
import com.jhhscm.platform.event.ImageSelectorEvent;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.StringUtils;
import com.jhhscm.platform.views.selector.ImageSelectorItem;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 类说明：
 */
public class ImageSelectorAdapter extends AbsAdapter<ImageSelectorItem> {
    private final static int ITEM_IMAGE = 0;
    private final static int ITEM_ADD = 1;
    private final static int ITEM_SHOW = 2;//只显
    private int mCode;
    private int mItemWidth;
    private Context context;

    public ImageSelectorAdapter(Context context, int itemWidth, int code) {
        super(context);
        this.context = context;
        mItemWidth = itemWidth;
        mCode = code;
    }

    public void defaultInit() {
        List<ImageSelectorItem> items = new ArrayList<>();
        items.add(ImageSelectorItem.newAddImageItem());
        setData(items);
    }

    public List<ImageSelectorItem> getItems() {
        return mData;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        ImageSelectorItem item = getItem(position);
        if (item.isAddFlag()) {
            return ITEM_ADD;
        } else {
            if (!item.isSHow()) {
                return ITEM_IMAGE;
            } else {
                return ITEM_SHOW;
            }
        }
    }

    @Override
    protected AbsViewHolder onCreateViewHolder(ViewGroup parent, int itemType) {
        switch (itemType) {
            case ITEM_ADD:
                return new ImageSelectorAddHolder(mInflater.inflate(R.layout.item_image_selector_add, parent, false));
            case ITEM_SHOW:
                return new ImageSelectorShowHolder(mInflater.inflate(R.layout.item_image_selector, parent, false));
            default:
                return new ImageSelectorHolder(mInflater.inflate(R.layout.item_image_selector, parent, false));
        }
    }

    private class ImageSelectorAddHolder extends AbsViewHolder<ImageSelectorItem> {
        private ItemImageSelectorAddBinding mItemBinding;

        public ImageSelectorAddHolder(View convertView) {
            super(convertView);
            mItemBinding = DataBindingUtil.bind(convertView);
            ViewGroup.LayoutParams params = mItemBinding.flImage.getLayoutParams();
            int head = (int) (((double) mItemWidth) / 1.33);
            if (params == null) {
                params = new ViewGroup.LayoutParams(mItemWidth, head);
            } else {
                params.width = mItemWidth;
                params.height = head;
            }
            mItemBinding.flImage.setLayoutParams(params);

        }

        @Override
        protected void onBindView(ImageSelectorItem item) {
            if (getItems().size() - 1 == 0) {
                mItemBinding.tvSubscript.setText("添加图片");
            } else {
                mItemBinding.tvSubscript.setText(context.getString(R.string.image_subscript, getItems().size() - 1));
            }
        }
    }

    private class ImageSelectorHolder extends AbsViewHolder<ImageSelectorItem> {
        private ItemImageSelectorBinding mItemBinding;

        public ImageSelectorHolder(View convertView) {
            super(convertView);
            mItemBinding = DataBindingUtil.bind(convertView);
            ViewGroup.LayoutParams params = mItemBinding.flImage.getLayoutParams();
            int head = (int) (((double) mItemWidth) / 1.33);
            if (params == null) {
                params = new ViewGroup.LayoutParams(mItemWidth, head);
            } else {
                params.width = mItemWidth;
                params.height = head;
            }
            mItemBinding.flImage.setLayoutParams(params);
        }

        @Override
        protected void onBindView(final ImageSelectorItem item) {
            ImageLoader.getInstance().displayImage(StringUtils.filterNullAndTrim(item.imageUrl), mItemBinding.ivImage);

            mItemBinding.ivPressed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (item.imageUrl != null) {
                        EventBusUtil.post(ImageSelectorEvent.newInstance(mCode, ImageSelectorEvent.EVENT_DEL, item.imageUrl));
                        getItems().remove(item);
                        notifyDataSetChanged();
                    }
                }
            });
        }
    }

    private class ImageSelectorShowHolder extends AbsViewHolder<ImageSelectorItem> {
        private ItemImageSelectorBinding mItemBinding;

        public ImageSelectorShowHolder(View convertView) {
            super(convertView);
            mItemBinding = DataBindingUtil.bind(convertView);
            ViewGroup.LayoutParams params = mItemBinding.flImage.getLayoutParams();
            int head = (int) (((double) mItemWidth) / 1.33);
            if (params == null) {
                params = new ViewGroup.LayoutParams(mItemWidth, head);
            } else {
                params.width = mItemWidth;
                params.height = head;
            }
            mItemBinding.flImage.setLayoutParams(params);
        }

        @Override
        protected void onBindView(final ImageSelectorItem item) {
            ImageLoader.getInstance().displayImage(StringUtils.filterNullAndTrim(item.imageUrl), mItemBinding.ivImage);
            mItemBinding.ivPressed.setVisibility(View.GONE);
        }
    }
}
