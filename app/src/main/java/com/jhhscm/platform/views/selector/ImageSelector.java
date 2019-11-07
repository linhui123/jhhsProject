package com.jhhscm.platform.views.selector;

import android.Manifest;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.jhhscm.platform.R;
import com.jhhscm.platform.bean.PbImage;
import com.jhhscm.platform.bean.UploadImage;
import com.jhhscm.platform.event.ImageSelectorEvent;
import com.jhhscm.platform.event.ImageSelectorUpdataEvent;
import com.jhhscm.platform.permission.YXPermission;
import com.jhhscm.platform.photopicker.PhotoPickerActivity;
import com.jhhscm.platform.tool.DisplayUtils;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.views.ExtendGridView;
import com.jhhscm.platform.views.selector.adapter.ImageSelectorAdapter;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;


import java.util.ArrayList;
import java.util.List;

/**
 * 类说明：图片选择器
 */
public class ImageSelector extends LinearLayout {
    private GridView mGvImages;
    private ImageSelectorAdapter mAdapter;
    private int mNumColums = 3;//默认4列
    private int mSelectMaxImageSize = 9;//图片最大选择数
    private int mItemWidth = 90;
    private int mHorizontalSpacing = 10;
    private int mVerticalSpacing = 10;
    private int mLeftMargin = 0;
    private boolean mIsClothesSelect = false;//是否衣柜选择
    private String mUserId;
    private boolean isWithCarmera = false;
    private boolean isUpdata = false;
    private int position = 0;//辅助标识

    public ImageSelector(Context context) {
        super(context);
        initViews();
    }

    public ImageSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.ImageSelector);
        try {
            mLeftMargin = DisplayUtils.getPXByDP(context, a.getInt(R.styleable.ImageSelector_left_margin, 0));
            mNumColums = a.getInt(R.styleable.ImageSelector_colum_num, 3);
            mSelectMaxImageSize = a.getInt(R.styleable.ImageSelector_image_num, 9);
            isWithCarmera = a.getBoolean(R.styleable.ImageSelector_with_camera, false);
            isUpdata = a.getBoolean(R.styleable.ImageSelector_is_update, false);
        } finally {
            a.recycle();
        }
        initViews();
    }

    public void setClothesSelect(boolean clothesSelect) {
        this.mIsClothesSelect = clothesSelect;
    }

    public void setUserId(String userId) {
        this.mUserId = userId;
    }

    /**
     * 获取选择图片集
     */
    public List<UploadImage> getUploadImageList() {
        List<UploadImage> images = new ArrayList<>();
        List<ImageSelectorItem> items = mAdapter.getItems();
        for (int i = 0; i < items.size(); i++) {
            ImageSelectorItem item = items.get(i);
            if (!item.isAddFlag()) {
                images.add(new UploadImage(item.imageUrl, item.catalogues, item.allfilePath, item.imageToken));
            }
        }
        return images;
    }

    /**
     * 设置图片集信息
     *
     * @param
     */
    public void setPbImageList(List<PbImage> images) {
        if (images == null) return;
        List<ImageSelectorItem> items = new ArrayList<>();
        for (PbImage image : images) {
            ImageSelectorItem item = new ImageSelectorItem();
            item.imageUrl = image.getmUrl();
            item.imageToken = image.getmToken();
            items.add(item);
        }
        if (items.size() <= 0 || items.size() < mSelectMaxImageSize) {
            items.add(ImageSelectorItem.newAddImageItem());
        }
        mAdapter.setData(items);
    }

//    /**
//     * 设置图片集信息
//     *
//     * @param
//     */
//    public void setPbImageList(ArrayList<String> images) {
//        if (images == null) return;
//        List<ImageSelectorItem> items = new ArrayList<>();
//        for (String image : images) {
//            ImageSelectorItem item = new ImageSelectorItem();
//            item.imageUrl = image;
//            items.add(item);
//        }
//        if (items.size() <= 0 || items.size() < mSelectMaxImageSize) {
//            items.add(ImageSelectorItem.newAddImageItem());
//        }
//        mAdapter.setData(items);
//    }

    /**
     * 设置图片集信息
     *
     * @param
     */
    public void setPbImageList(ArrayList<String> images) {
        if (images == null) return;
        mAdapter = new ImageSelectorAdapter(getContext(), mItemWidth, this.hashCode());
        mGvImages.setAdapter(mAdapter);
        List<ImageSelectorItem> items = new ArrayList<>();
        for (String image : images) {
            ImageSelectorItem item = new ImageSelectorItem();
            item.imageUrl = image;
            items.add(item);
        }
        if (items.size() <= 0 || items.size() < mSelectMaxImageSize) {
            items.add(ImageSelectorItem.newAddImageItem());
        }
        mAdapter.setData(items);
    }

    private List<ImageSelectorItem> imageSelectorItems;

    public void setImageList(List<ImageSelectorItem> items) {
        if (items == null) return;
        if (items.size() <= 0 || items.size() < mSelectMaxImageSize) {
            items.add(ImageSelectorItem.newAddImageItem());
        }
        imageSelectorItems = items;
        mAdapter.setData(items);
    }


    public void setImageToken(UploadImage image) {
        List<ImageSelectorItem> items = mAdapter.getItems();
        for (int i = 0; i < items.size(); i++) {
            ImageSelectorItem item = items.get(i);
            if (!item.isAddFlag()) {
                if (item.equals(image.getImageUrl())) {
                    item.imageToken = image.getImageToken();
                    item.allfilePath = image.getAllfilePath();
                    item.catalogues = image.getCatalogues();
                }
            }
        }
    }

    private void showAlertNotice() {
        final AlertDialog alertDialog = new AlertDialog(getContext(), getContext().getString(R.string.permission_denied_msg), new AlertDialog.CallbackListener() {
            @Override
            public void clickYes() {

            }
        });
        alertDialog.show();
    }

    private void initViews() {
        EventBusUtil.registerEvent(this);
        mHorizontalSpacing = DisplayUtils.getPXByDP(getContext(), 8);
        mVerticalSpacing = DisplayUtils.getPXByDP(getContext(), 8);
        mItemWidth = (DisplayUtils.getDeviceWidth(getContext()) - (mNumColums - 1) * mHorizontalSpacing - getPaddingLeft() - getPaddingRight() - mLeftMargin) / mNumColums;

        mGvImages = new ExtendGridView(getContext());
        mGvImages.setCacheColorHint(getContext().getResources().getColor(R.color.full_transparent));
//        mGvImages.setSelector(getContext().getResources().getDrawable(R.drawable.selector_image_selector_border));
        mGvImages.setNumColumns(mNumColums);
        mGvImages.setHorizontalSpacing(mHorizontalSpacing);
        mGvImages.setVerticalSpacing(mVerticalSpacing);
        mGvImages.setGravity(Gravity.CENTER);

        mAdapter = new ImageSelectorAdapter(getContext(), mItemWidth, ImageSelector.this.hashCode());
        mGvImages.setAdapter(mAdapter);
        mAdapter.defaultInit();

        mGvImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                YXPermission.getInstance(getContext()).request(new AcpOptions.Builder()
                        .setDeniedCloseBtn(getContext().getString(R.string.permission_dlg_close_txt))
                        .setDeniedSettingBtn(getContext().getString(R.string.permission_dlg_settings_txt))
                        .setDeniedMessage(getContext().getString(R.string.permission_denied_txt, "相机"))
                        .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA).build(), new AcpListener() {
                    @Override
                    public void onGranted() {
                        ImageSelectorItem item = mAdapter.getItem(position);
                        if (item.isAddFlag()) {
                            doCheckAdd();
                        } else {
                            ImageSelectorPreviewActivity.startActivity(getContext(), ImageSelector.this.hashCode(), getPreImages(), position);
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions) {

                    }
                });

//                String perms[] = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
//                if (RuntimePermission.hasPermissions(getContext(), perms)) {
//                    ImageSelectorItem item = mAdapter.getItem(position);
//                    if (item.isAddFlag()) {
//                        doCheckAdd();
//                    } else {
//                        ImageSelectorPreviewActivity.startActivity(getContext(), ImageSelector.this.hashCode(), getPreImages(), position);
//                    }
//                } else {
//                    showAlertNotice();
//                }
            }
        });

        addView(mGvImages, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    private ChoiceDialog mAddDialog;
    private final static int CODE_ALBUM = 1;

    private void doCheckAdd() {
        if (mIsClothesSelect) {
            if (mAddDialog == null) {
                mAddDialog = new ChoiceDialog(getContext());
                List<ChoiceDialog.ChoiceItem> items = new ArrayList<>();
                items.add(new ChoiceDialog.ChoiceItem(CODE_ALBUM, getContext().getString(R.string.dialog_choice_album)));
                mAddDialog.setChoiceItems(items);
                mAddDialog.setCallbackListener(new ChoiceDialog.CallbackListener() {
                    @Override
                    public void onItemClick(ChoiceDialog.ChoiceItem item) {
                        switch (item.getCode()) {
                            case CODE_ALBUM:
                                PhotoPickerActivity.startActivity(getContext(), ImageSelector.this.hashCode(), getPhotoPickerList(), getPhotoPickerMaxCount(), isWithCarmera);
                                break;
                        }
                    }
                });
            }
            mAddDialog.show();
        } else {
            PhotoPickerActivity.startActivity(getContext(), ImageSelector.this.hashCode(), getPhotoPickerList(), mSelectMaxImageSize, isWithCarmera);
        }
    }

    private boolean isClothesImage(ImageSelectorItem item) {
        return item.imageId > 0;
    }

    private ArrayList<String> getPhotoPickerList() {
        ArrayList<String> images = new ArrayList<>();
        List<ImageSelectorItem> items = mAdapter.getItems();
        for (int i = 0; i < items.size(); i++) {
            ImageSelectorItem item = items.get(i);
            if (!item.isAddFlag()) {
                //本地图片
                if (!isClothesImage(item)) {
                    images.add(item.imageUrl);
                    Log.e("item ","item.imageUrl "+item.imageUrl);
                }
            }
        }

        return images;
    }

    private int getPhotoPickerMaxCount() {
        int count = 0;
        List<ImageSelectorItem> items = mAdapter.getItems();
        for (int i = 0; i < items.size(); i++) {
            ImageSelectorItem item = items.get(i);
            if (!item.isAddFlag()) {
                if (isClothesImage(item)) {
                    count++;
                }
            }
        }
        return mSelectMaxImageSize - count;
    }

    private ArrayList<ImageSelectorItem> getPreImages() {
        ArrayList<ImageSelectorItem> preItems = new ArrayList<>();
        List<ImageSelectorItem> items = mAdapter.getItems();
        for (int i = 0; i < items.size(); i++) {
            ImageSelectorItem item = items.get(i);
            if (!item.isAddFlag()) {
                preItems.add(item);
            }
        }
        return preItems;
    }

    public void onEventMainThread(ImageSelectorEvent event) {
        if (event.getCode() == hashCode()) {
            switch (event.getType()) {
                case ImageSelectorEvent.EVENT_ADD:
                    doPhotoPickerAddEvent(event.getImages());
                    break;
                case ImageSelectorEvent.EVENT_DEL:
                    doPhotoPickerDelEvent(event.getDelImage());
                    break;
            }
        }
    }

    private void doPhotoPickerAddEvent(ArrayList<String> images) {
        if (images == null) return;
        List<ImageSelectorItem> newItems = new ArrayList<>();
        List<ImageSelectorItem> items = mAdapter.getItems();
        for (int i = 0; i < items.size(); i++) {
            ImageSelectorItem item = items.get(i);
            if (!item.isAddFlag()) {
                if (isClothesImage(item)) {
                    newItems.add(item);
                } else {
                    for (int j = 0; j < images.size(); j++) {
                        String image = images.get(j);
                        if (item.equals(image)) {
                            newItems.add(item);
                            images.remove(j);
                            break;
                        }
                    }
                }
            }
        }
        for (int i = 0; i < images.size(); i++) {
            String image = images.get(i);
            ImageSelectorItem item = ImageSelectorItem.convert(image);
            newItems.add(item);
        }
        if (newItems.size() <= 0 || newItems.size() < mSelectMaxImageSize) {
            newItems.add(ImageSelectorItem.newAddImageItem());
        }
        mAdapter.setData(newItems);
        EventBusUtil.post(new ImageSelectorUpdataEvent(this, newItems, position));
    }

    private void doPhotoPickerDelEvent(String delImage) {
        if (delImage == null) return;
        for (int i = 0; i < mAdapter.getCount(); i++) {
            ImageSelectorItem item = mAdapter.getItem(i);
            if (!item.isAddFlag()) {
                if (item.equals(delImage)) {
                    mAdapter.remove(i);
                    break;
                }
            }
        }

        if (mAdapter.getItems().size() == mSelectMaxImageSize - 1) {
            if (mAdapter.getItems().size() > 0 && !mAdapter.getItem(mAdapter.getItems().size() - 1).isAddFlag()) {
                mAdapter.add(ImageSelectorItem.newAddImageItem());
            }
        }
        if (mAdapter.getItems().size() == 0) {
            mAdapter.add(ImageSelectorItem.newAddImageItem());
        }
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
