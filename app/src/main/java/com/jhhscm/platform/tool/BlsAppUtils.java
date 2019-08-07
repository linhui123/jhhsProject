package com.jhhscm.platform.tool;

import android.graphics.Bitmap;

import com.jhhscm.platform.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
/**
 * Created by Administrator on 2018/10/18/018.
 */

public class BlsAppUtils {

    public static DisplayImageOptions getCaseBaseDisplayImageOptions(){
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true)
                .showImageForEmptyUri(R.drawable.case_default_bg)
                .showImageOnFail(R.drawable.case_default_bg).showImageOnLoading(R.drawable.case_default_bg)
                .cacheOnDisk(true).build();
        return displayImageOptions;
    }

    public static DisplayImageOptions getDisplayImageOptions(){
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true)
                .showImageForEmptyUri(R.drawable.menu_default_bg)
                .showImageOnFail(R.drawable.menu_default_bg).showImageOnLoading(R.drawable.menu_default_bg)
                .cacheOnDisk(true).build();
        return displayImageOptions;
    }

    public static DisplayImageOptions getDisplayMaterialOptions(){
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true)
                .showImageForEmptyUri(R.drawable.material_default_bg)
                .showImageOnFail(R.drawable.material_default_bg).showImageOnLoading(R.drawable.material_default_bg)
                .cacheOnDisk(true).build();
        return displayImageOptions;
    }
    public static DisplayImageOptions getDisplayShopping(){
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true)
                .showImageForEmptyUri(R.drawable.shopping_default_bg)
                .showImageOnFail(R.drawable.shopping_default_bg).showImageOnLoading(R.drawable.shopping_default_bg)
                .cacheOnDisk(true).build();
        return displayImageOptions;
    }
}
