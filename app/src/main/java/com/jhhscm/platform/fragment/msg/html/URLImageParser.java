package com.jhhscm.platform.fragment.msg.html;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.jhhscm.platform.MyApplication;
import com.jhhscm.platform.tool.DisplayUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class URLImageParser implements Html.ImageGetter {
    TextView mTextView;

    public URLImageParser(TextView textView) {
        this.mTextView = textView;
    }

    @Override
    public Drawable getDrawable(String source) {
        final URLDrawable urlDrawable = new URLDrawable();
        ImageLoader.getInstance().loadImage(source, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                int width= DisplayUtils.getDeviceWidth(MyApplication.getInstance())-DisplayUtils.dip2px(MyApplication.getInstance(),32);
                int height=(int)(((float)width/(float)loadedImage.getWidth())*(float)loadedImage.getHeight());
                int widths = loadedImage.getWidth();
                int heights = loadedImage.getHeight();
                //设置想要的大小
                int newWidth=width;
                int newHeight=height;

                //计算压缩的比率
                float scaleWidth=((float)newWidth)/widths;
                float scaleHeight=((float)newHeight)/heights;

                //获取想要缩放的matrix
                Matrix matrix = new Matrix();
                matrix.postScale(scaleWidth,scaleWidth);

                //获取新的bitmap
                loadedImage=Bitmap.createBitmap(loadedImage,0,0,widths,heights,matrix,true);
                loadedImage.getWidth();
                loadedImage.getHeight();
                urlDrawable.bitmap= loadedImage;
                urlDrawable.setBounds(0, 0, loadedImage.getWidth(), loadedImage.getHeight());
                mTextView.invalidate();
                mTextView.setText(mTextView.getText()); // 解决图文重叠
            }
        });
        return urlDrawable;
    }
}

