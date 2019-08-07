package com.jhhscm.platform.tool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;
import android.os.StatFs;
import android.util.Base64;
import android.util.Log;

import com.jhhscm.platform.MyApplication;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import top.zibin.luban.Luban;

/**
 * 类说明：
 */
public class FileUtils {

    public static String getPhotoImageUri(String image) {
        return "file://" + image;
    }

    public static String getPhotoImagePath(String imageUri) {
        File imageFile = null;
        try {
            imageFile = new File(new URI(imageUri));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (imageFile != null) {
            return imageFile.getAbsolutePath();
        }
        int index = imageUri.indexOf("file://");
        if (index > 0) {
            return imageUri.substring(index);
        }
        return imageUri;
    }

    //获取存储空间剩余信息
    public static long getAvailaleSize() {
        try {
            File path = null;
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {//判断是否有sd
                path = Environment.getExternalStorageDirectory(); //取得sdcard文件路径
            } else {
                path = Environment.getDataDirectory(); //取得手机内部存储路径
            }
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            return availableBlocks * blockSize;//返回的是字节
        } catch (Exception ex) {
            ex.printStackTrace();
            return Long.MAX_VALUE;
        }
    }

    //获取分享图片路径（待定）
    public static String getShareFile(String fileName) {
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return fileName;
        }

        String dbPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        File dbDir = new File(dbPath);
        if (!dbDir.exists()) {
            dbDir.mkdirs();
        }
        File dbFile = new File(dbDir, fileName);
        if (!dbFile.exists()) {
            try {
                dbFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return dbFile.getAbsolutePath();
    }

    //获取文件或文件夹(及其下所有子目录子文件)的总容量
    public static long getFileOrDirectorySize(File f) throws Exception {
        long size = 0;
        for (File file : f.listFiles()) {
            if (file.isDirectory()) {
                size = size + getFileOrDirectorySize(file);
            } else {
                size = size + file.length();
            }
        }
        return size;
    }

    //删除文件或文件夹(及其下所有子目录中的文件,但是保持目录结构)
    public static void delFileOrDirectory(File f) {
        for (File file : f.listFiles()) {
            if (file.isDirectory()) {
                delFileOrDirectory(file);
            } else {
                file.delete();
            }
        }
    }

    /**
     * 把bitmap转换成String
     *
     * @param filePath
     * @return
     */
    public static String bitmapToString(String filePath) {
        Bitmap bm = getSmallBitmap(filePath, DisplayUtils.getDeviceWidth(MyApplication.getInstance()), DisplayUtils.getDeviceHeight(MyApplication.getInstance()));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
        byte[] b = baos.toByteArray();

        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    /**
     * 拍照图片压缩
     *
     * @param filePath
     */
    public static void compress(String filePath) {
        Bitmap bm = getSmallBitmap(filePath, DisplayUtils.getDeviceWidth(MyApplication.getInstance()), DisplayUtils.getDeviceHeight(MyApplication.getInstance()));

        File file = new File(filePath);//将要保存图片的路径
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file));
            bm.compress(Bitmap.CompressFormat.JPEG, 60, bos);
            bos.flush();
            bos.close();
            bos = null;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bm != null && !bm.isRecycled()) {
                // 回收并且置为null
                bm.recycle();
                bm = null;
            }
        }
    }

    /**
     * 获取图片格式
     *
     * @param uri
     * @return
     */
    private synchronized static String getImageType(String uri) {
        String[] array = uri.split("\\.");
        return "." + array[array.length - 1].toLowerCase();
    }

    public static void compressImageFile(String filePath, File imageFile) {
        Log.i("----filePath000", filePath);
        Bitmap bm = getSmallBitmap(filePath, DisplayUtils.getDeviceWidth(MyApplication.getInstance()), DisplayUtils.getDeviceHeight(MyApplication.getInstance()));

        BufferedOutputStream bos = null;
        try {
            String imageType = getImageType(filePath);
            bos = new BufferedOutputStream(new FileOutputStream(imageFile));
            if (imageType.equals(".jpg") || imageType.equals(".jpeg")) {
                bm.compress(Bitmap.CompressFormat.JPEG, 40, bos);
            } else {
                bm.compress(Bitmap.CompressFormat.PNG, 100, bos);
            }
            bos.flush();
            bos.close();
            bos = null;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bm != null && !bm.isRecycled()) {
                // 回收并且置为null
                bm.recycle();
                bm = null;
            }
        }
    }

    /**
     * 计算图片的缩放值
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }


    /**
     * 根据路径获得突破并压缩返回bitmap用于显示
     *
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath, int reqWidth, int reqHeight) {
        Log.i("----filePath---", filePath);
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inDither = false;    /*不进行图片抖动处理*/
        options.inPreferredConfig = null;  /*设置让解码器以最佳方式解码*/
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 根据路径删除图片
     *
     * @param path
     */
    public static void deleteTempFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }

    public static int getBitmapDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    public static Bitmap rotateBitmap(Bitmap bm, int degree) {
        Bitmap returnBm = null;

        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }

    /**
     * 得到图片临时存储路径
     * @return
     */
    public static String getPhotoImgPath(){
        String savePath = "";
        // 判断是否挂载了SD卡
        String storageState = Environment
                .getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            savePath = Environment
                    .getExternalStorageDirectory()
                    + "/Test/Camera/";// 存放照片的文件夹
            File savedir = new File(savePath);
            if (!savedir.exists()) {
                savedir.mkdirs();
            }
        }
        return savePath;
    }


    public File imageFile(Context context, String imagePath) {
        final long UPLOAD_IMAGE_SIZE_LIMIT = 1024 * 1024;//1M
        File imageFile = null;
        try {
            imageFile = new File(new URI(imagePath));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        if (imageFile == null || !imageFile.exists()) {
            ToastUtils.show(context, "上传文件不存在！");
            return null;
        }
        try {
            //1M以上图片进行压缩处理
            if (imageFile.length() > UPLOAD_IMAGE_SIZE_LIMIT) {
                String imageType = getImageType(imageFile.getAbsolutePath());
                Luban.get(MyApplication.getInstance())
                        .load(imageFile)
                        .putGear(Luban.THIRD_GEAR)
                        .setFilename(imageFile.getAbsolutePath())
                        .asObservable()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnError(new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        })
                        .onErrorResumeNext(new Func1<Throwable, Observable<? extends File>>() {
                            @Override
                            public Observable<? extends File> call(Throwable throwable) {
                                return Observable.empty();
                            }
                        })
                        .subscribe(new Action1<File>() {
                            @Override
                            public void call(File file) {
                                // TODO 压缩成功后调用，返回压缩后的图片文件
//                                return file;
//                                return photos;
                            }
                        });    //启动压缩
                return null;
            } else {
            return imageFile;
            }
        } catch (Throwable e) {
//            delUploadImageFile();
            return null;
        }
    }

//    private void delUploadImageFile() {
//        if (uploadImageFile != null) {
//            FileUtils.deleteTempFile(uploadImageFile.getAbsolutePath());
//            uploadImageFile = null;
//        }
//    }

    private static File createTempImageFile(String imageType) throws IOException {
        // Create an image file name
        String imageFileName = "temp" + UUID.randomUUID();
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (!storageDir.exists()) {
            if (!storageDir.mkdir()) {
                throw new IOException();
            }
        }
        File image = new File(storageDir, imageFileName + imageType);
        return image;
    }

}
