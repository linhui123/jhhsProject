package com.jhhscm.platform.views.selector;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.base.AbsToolbarActivity;
import com.jhhscm.platform.databinding.FragmentPhotopreviewBinding;
import com.jhhscm.platform.event.DelPhotoEvent;
import com.jhhscm.platform.event.ImageSelectorEvent;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.photopicker.ConfirmDialog;
import com.jhhscm.platform.tool.EventBusUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;


/**
 * 选择预览图片
 */
public class ImageSelectorPreviewActivity extends AbsToolbarActivity {
    private final static String EXTRA_IMAGES = "mImages";
    private final static String EXTRA_INDEX = "mIndex";
    private final static String EXTRA_CODE = "mCode";
    private int mCode = 0;
    private ArrayList<ImageSelectorItem> mImages;
    private int mIndex;
    private ConfirmDialog mDelDialog;

    public static void startActivity(Context context, int code, ArrayList<ImageSelectorItem> images, int index) {
        Intent intent = new Intent(context, ImageSelectorPreviewActivity.class);
        intent.putExtra(EXTRA_CODE, code);
        intent.putExtra(EXTRA_IMAGES, images);
        intent.putExtra(EXTRA_INDEX, index);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mCode = getIntent().getIntExtra(EXTRA_CODE, 0);
        mIndex = getIntent().getIntExtra(EXTRA_INDEX, 0);
        mImages = (ArrayList<ImageSelectorItem>) getIntent().getSerializableExtra(EXTRA_IMAGES);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Bundle onPutArguments() {
        Bundle args = new Bundle();
        args.putInt(EXTRA_CODE, mCode);
        args.putInt(EXTRA_INDEX, mIndex);
        args.putSerializable(EXTRA_IMAGES, mImages);
        return args;
    }

    @Override
    protected boolean enableHomeButton() {
        return true;
    }

    @Override
    protected boolean enableDisplayHomeAsUp() {
        return true;
    }

    @Override
    protected boolean enableOperateText() {
        return false;
    }

    @Override
    protected boolean enableDeleteButton() {
        return true;
    }

    @Override
    protected String getToolBarTitle() {
        return (mIndex + 1) + "/" + mImages.size();
    }
    @Override
    protected AbsFragment onCreateContentView() {
        return new PhotoFragment();
    }

    @Override
    protected void onToolbarRightClick(Context context) {
        super.onOneKeyShareClick(context);
        showDelDialog();
    }

    @Override
    protected void onDeleteClick(Context context) {
        super.onDeleteClick(context);
        showDelDialog();
    }

    private void showDelDialog() {
        if (mDelDialog == null) {
            mDelDialog = new ConfirmDialog(this, getString(R.string.dialog_toast_image_del), getString(R.string.photopicker_del_cancel), getString(R.string.photopicker_del_sure));
        }
        mDelDialog.setCallbackListener(new ConfirmDialog.CallbackListener() {
            @Override
            public void clickNo() {

            }

            @Override
            public void clickYes() {
                EventBusUtil.post(new DelPhotoEvent());
            }
        });
        mDelDialog.show();
    }


    public static class PhotoFragment extends AbsFragment<FragmentPhotopreviewBinding> {
//        private FragmentPhotopreviewBinding mDataBinding;
        private PhotoPagerAdapter mAdapter;
        private int mCode = 0;
        private ArrayList<ImageSelectorItem> mImages;
        private int mIndex;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EventBusUtil.registerEvent(this);
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            EventBusUtil.unregisterEvent(this);
        }

        public void onEventMainThread(DelPhotoEvent photoEvent) {
            int index = mDataBinding.viewPager.getCurrentItem();
            ImageSelectorItem image = mImages.get(index);
            //删除
            mAdapter.remove(index);
            EventBusUtil.post(ImageSelectorEvent.newInstance(mCode, ImageSelectorEvent.EVENT_DEL, image.imageUrl));
//            EventBusUtil.post(new AmendCaseBaseEvent());
            if (mAdapter.getCount() <= 0) {
                getActivity().finish();
                return;
            }
            ((ImageSelectorPreviewActivity) getActivity()).setToolBarTitle((mDataBinding.viewPager.getCurrentItem() + 1) + "/" + mAdapter.getCount());
        }

        @Override
        protected FragmentPhotopreviewBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
            return FragmentPhotopreviewBinding.inflate(inflater,container,attachToRoot);
        }

        @Override
        protected void setupViews() {
            initViews();
        }

//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//            mDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_photopreview, container, false);
//            initViews();
//            return mDataBinding.getRoot();
//        }

        private void initViews() {
            mCode = getArguments().getInt(EXTRA_CODE);
            mImages = (ArrayList<ImageSelectorItem>) getArguments().getSerializable(EXTRA_IMAGES);
            mIndex = getArguments().getInt(EXTRA_INDEX);

            mDataBinding.viewPager.setOffscreenPageLimit(1);
            mDataBinding.viewPager.addOnPageChangeListener(createOnPageChangeListener());
            mAdapter = new PhotoPagerAdapter();
            mDataBinding.viewPager.setAdapter(mAdapter);
            if (mImages != null) {
                mAdapter.setData(mImages);
                mDataBinding.viewPager.setCurrentItem(mIndex);
                ((ImageSelectorPreviewActivity) getActivity()).setToolBarTitle((mIndex + 1) + "/" + mImages.size());
            }
        }

        private ViewPager.OnPageChangeListener createOnPageChangeListener() {
            return new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    ((ImageSelectorPreviewActivity) getActivity()).setToolBarTitle((mDataBinding.viewPager.getCurrentItem() + 1) + "/" + mAdapter.getCount());

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            };
        }

        private class PhotoPagerAdapter extends PagerAdapter {

            private ArrayList<ImageSelectorItem> mImages = new ArrayList<>();

            public ImageSelectorItem getImageUrl(int position) {
                if (position < mImages.size()) {
                    return mImages.get(position);
                }
                return null;
            }

            public void setData(ArrayList<ImageSelectorItem> images) {
                mImages = images;
                notifyDataSetChanged();
            }

            public ArrayList<ImageSelectorItem> getData() {
                return mImages;
            }

            public void remove(int index) {
                mImages.remove(index);
                notifyDataSetChanged();
            }

            public void clear() {
                mImages.clear();
                notifyDataSetChanged();
            }

            @Override
            public int getItemPosition(Object object) {
                return POSITION_NONE;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public int getCount() {
                return mImages.size();
            }

            @Override
            public View instantiateItem(ViewGroup container, int position) {
                PhotoView photoView = new PhotoView(container.getContext());
                String url = mImages.get(position).imageUrl;
                ImageLoader.getInstance().displayImage(url, photoView);
                photoView.setTag(R.id.tag_url, url);
                container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                return photoView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        }

    }
}
