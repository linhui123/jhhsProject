package com.jhhscm.platform.fragment.msg.html;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;

/**
 * Created by Administrator on 2019/3/14/014.
 */

public class URLDrawable extends BitmapDrawable {
    public Bitmap bitmap;

    @Override
    public void draw(Canvas canvas) {
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, 0, 0, getPaint());
        }
    }
}

