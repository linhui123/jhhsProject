package com.jhhscm.platform.tool;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.View;

import com.jhhscm.platform.MyApplication;
import com.jhhscm.platform.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.utils.SLog;

import java.io.ByteArrayOutputStream;

/**
 * Created by intel on 2018/11/12.
 */

public class ShareUtils {

    public static void shareText(Context context, String text, SHARE_MEDIA share_media, UMShareListener shareListener) {
        new ShareAction((Activity) context).withText(text)
                .setPlatform(share_media)
                .setCallback(shareListener).share();
    }

    public static void shareImageLocal(Context context, SHARE_MEDIA share_media, int imgID, UMShareListener shareListener) {
//        用户设置的图片大小最好不要超过250k，缩略图不要超过18k，如果超过太多（最好不要分享1M以上的图片，压缩效率会很低），图片会被压缩。
        UMImage imagelocal = new UMImage(context, imgID);
        imagelocal.setThumb(new UMImage(context, R.mipmap.ic_launcher));
        new ShareAction((Activity) context).withMedia(imagelocal)
                .setPlatform(share_media)
                .setCallback(shareListener).share();
    }

    public static void shareImageLocal(Context context, SHARE_MEDIA share_media, Bitmap imgBitmap, UMShareListener shareListener) {
//        用户设置的图片大小最好不要超过250k，缩略图不要超过18k，如果超过太多（最好不要分享1M以上的图片，压缩效率会很低），图片会被压缩。
        UMImage imagelocal = new UMImage(context, imgBitmap);
        imagelocal.setThumb(new UMImage(context, R.mipmap.ic_launcher));
        new ShareAction((Activity) context).withMedia(imagelocal)
                .setPlatform(share_media)
                .setCallback(shareListener).share();
    }

    public static void shareImageNet(Context context, SHARE_MEDIA share_media, String imgURL, UMShareListener shareListener) {
        UMImage imageurl = new UMImage(context, imgURL);
        imageurl.setThumb(new UMImage(context, imgURL));
        new ShareAction((Activity) context).withMedia(imageurl)
                .withText("123")
                .setPlatform(share_media)
                .setCallback(shareListener).share();
    }

    public static void shareUrl(Context context, String url, String title, String description, SHARE_MEDIA share_media, UMShareListener shareListener, String imgUrl) {
        UMWeb web = new UMWeb(url);
        web.setTitle(title);
        web.setThumb(new UMImage(context, imgUrl));
        web.setDescription(description);
        new ShareAction((Activity) context).withMedia(web)
                .setPlatform(share_media)
                .setCallback(shareListener).share();
    }

    public static void shareUrls(Context context, String url, SHARE_MEDIA share_media, UMShareListener shareListener) {
        UMImage imageurl = new UMImage(context, url);
        imageurl.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
        imageurl.compressStyle = UMImage.CompressStyle.QUALITY;//质量压缩，适合长图的分享
        UMImage thumb = new UMImage(context, url);
        imageurl.setThumb(thumb);
        new ShareAction((Activity) context).withMedia(imageurl)
                .setPlatform(share_media)
                .setCallback(shareListener).share();
    }

    public static void safeShowDialog(Dialog var0) {
        try {
            if (var0 != null && !var0.isShowing()) {
                SLog.I("启动了对话框");
                var0.show();
            }
        } catch (Exception var2) {
            SLog.I("异常了");
            SLog.error(var2);
        }

    }

    public static void safeCloseDialog(Dialog var0) {
        try {
            if (var0 != null && var0.isShowing()) {
                var0.dismiss();
                var0 = null;
            }
        } catch (Exception var2) {
            SLog.error(var2);
        }

    }

    public static UMShareListener getShareListener(final Context context) {

        return new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
//                safeShowDialog(dialog);
            }

            @Override
            public void onResult(SHARE_MEDIA share_media) {
                ToastUtils.show(context, "分享成功");
//                safeCloseDialog(dialog);
            }

            @Override
            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                ToastUtils.show(context, "分享失败");
//                safeCloseDialog(dialog);
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {
//                ToastUtils.show(context,"取消分享");
//                safeCloseDialog(dialog);
            }
        };
    }

    public static UMShareListener getShareListener(final Context context, final Dialog dialog) {

        return new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                safeShowDialog(dialog);
            }

            @Override
            public void onResult(SHARE_MEDIA share_media) {
//                ToastUtils.show(context,"分享成功");
                safeCloseDialog(dialog);
            }

            @Override
            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
//                ToastUtils.show(context,"分享失败");
                safeCloseDialog(dialog);
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {
//                ToastUtils.show(context,"取消分享");
                safeCloseDialog(dialog);
            }
        };
    }

    public static void putTextIntoClip(Context context, String text) {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", text);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);

    }


    /**
     * 分享url地址
     *
     * @param url   地址
     * @param title 标题
     * @param desc  描述
     */
    public static void shareUrlToWx(final Context context, final String url, final String title, final String desc, final String iconUrl, final int flag) {
        if (((MyApplication) context).getApi() != null && !((MyApplication) context).getApi().isWXAppInstalled()) {
            ToastUtil.show(context, "您还未安装微信客户端,无法使用该功能！");
            return;
        }
        if (iconUrl != null && iconUrl.length() > 0) {
            ImageLoader.getInstance().loadImage(iconUrl, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);
                    WXWebpageObject webpage = new WXWebpageObject();
                    webpage.webpageUrl = url;
                    WXMediaMessage msg = new WXMediaMessage(webpage);
                    msg.title = title;
                    msg.description = desc;
                    //这里替换一张自己工程里的图片资源
                    msg.thumbData = bmpToByteArray(loadedImage, 32);
                    SendMessageToWX.Req req = new SendMessageToWX.Req();
                    req.transaction = String.valueOf(System.currentTimeMillis());
                    req.message = msg;
                    req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
                    ((MyApplication) context).getApi().sendReq(req);
                }
            });
        } else {
            WXWebpageObject webpage = new WXWebpageObject();
            webpage.webpageUrl = url;
            WXMediaMessage msg = new WXMediaMessage(webpage);
            msg.title = title;
            msg.description = desc;
            //这里替换一张自己工程里的图片资源
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
            bitmap = changeColor(bitmap);
            msg.thumbData = bmpToByteArray(bitmap, 32);
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = String.valueOf(System.currentTimeMillis());
            req.message = msg;
            req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
            ((MyApplication) context).getApi().sendReq(req);
        }

    }

    /**
     * 分享url地址
     *
     * @param url   地址
     * @param title 标题
     * @param desc  描述
     */
    public static void shareUrlToWx(final Context context, final String url, final String title, final String desc, final Bitmap iconUrl, final int flag) {
        if (((MyApplication) context).getApi() != null && !((MyApplication) context).getApi().isWXAppInstalled()) {
            ToastUtil.show(context, "您还未安装微信客户端,无法使用该功能！");
            return;
        }
        if (iconUrl != null) {
            WXWebpageObject webpage = new WXWebpageObject();
            webpage.webpageUrl = url;
            WXMediaMessage msg = new WXMediaMessage(webpage);
            msg.title = title;
            msg.description = desc;
            //这里替换一张自己工程里的图片资源
            msg.thumbData = bmpToByteArray(iconUrl, 32);
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = String.valueOf(System.currentTimeMillis());
            req.message = msg;
            req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
            ((MyApplication) context).getApi().sendReq(req);
        } else {
            WXWebpageObject webpage = new WXWebpageObject();
            webpage.webpageUrl = url;
            WXMediaMessage msg = new WXMediaMessage(webpage);
            msg.title = title;
            msg.description = desc;
            //这里替换一张自己工程里的图片资源
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
            bitmap = changeColor(bitmap);
            msg.thumbData = bmpToByteArray(bitmap, 32);
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = String.valueOf(System.currentTimeMillis());
            req.message = msg;
            req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
            ((MyApplication) context).getApi().sendReq(req);
        }

    }


    /**
     * Bitmap转换成byte[]并且进行压缩,压缩到不大于maxkb
     *
     * @param bitmap
     * @param maxKb
     * @return
     */
    public static byte[] bmpToByteArray(Bitmap bitmap, int maxKb) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
        int options = 100;
        while (output.toByteArray().length > maxKb && options != 10) {
            output.reset(); //清空output
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, output);//这里压缩options%，把压缩后的数据存放到output中
            options -= 10;
        }
        return output.toByteArray();
    }

    //bitmap中的透明色用白色替换
    public static Bitmap changeColor(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int[] colorArray = new int[w * h];
        int n = 0;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int color = getMixtureWhite(bitmap.getPixel(j, i));
                colorArray[n++] = color;
            }
        }
        return Bitmap.createBitmap(colorArray, w, h, Bitmap.Config.ARGB_8888);
    }

    //获取和白色混合颜色
    private static int getMixtureWhite(int color) {
        int alpha = Color.alpha(color);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.rgb(getSingleMixtureWhite(red, alpha), getSingleMixtureWhite(green, alpha),
                getSingleMixtureWhite(blue, alpha));
    }

    // 获取单色的混合值
    private static int getSingleMixtureWhite(int color, int alpha) {
        int newColor = color * alpha / 255 + 255 - alpha;
        return newColor > 255 ? 255 : newColor;
    }
}
