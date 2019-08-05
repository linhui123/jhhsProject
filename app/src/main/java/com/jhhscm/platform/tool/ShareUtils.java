package com.jhhscm.platform.tool;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.jhhscm.platform.MyApplication;
import com.jhhscm.platform.R;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.utils.SLog;

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


}
