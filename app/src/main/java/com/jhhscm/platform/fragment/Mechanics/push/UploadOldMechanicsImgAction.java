package com.jhhscm.platform.fragment.Mechanics.push;

import android.content.Context;
import android.os.Environment;

import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.Fields;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.FileUtils;
import com.jhhscm.platform.tool.ToastUtils;


import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * Created by Administrator on 2018/10/18/018.
 */

public class UploadOldMechanicsImgAction extends AHttpService<OldMechanicsUpImageBean> {

    private String imagePath;
    public final long UPLOAD_IMAGE_SIZE_LIMIT = 1024 * 1024;//1M
    private File uploadImageFile;
    private String mToken;

    public static UploadOldMechanicsImgAction newInstance(Context context, File file, String token) {
        return new UploadOldMechanicsImgAction(context, file,token);
    }

    public UploadOldMechanicsImgAction(Context context, File file, String token) {
        super(context);
        this.uploadImageFile = file;
        this.mToken=token;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
//        File im=imageFile();
//        MultipartFile file

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)//表单类型
                .addFormDataPart(Fields.Token, ConfigUtils.getCurrentUser(context).getToken());
        RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/jpg"), uploadImageFile);
        builder.addFormDataPart("file", uploadImageFile.getName(), photoRequestBody);
        List<MultipartBody.Part> parts = builder.build().parts();

        return apiService.uploadImages(parts);
    }

    public File imageFile() {
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
            final Map<String, File> photos = new HashMap<>();
            //1M以上图片进行压缩处理
//            if (imageFile.length() > UPLOAD_IMAGE_SIZE_LIMIT) {
//                String imageType = getImageType(imageFile.getAbsolutePath());
//                final String filePath = imageFile.getAbsolutePath();
//                try {
//                    uploadImageFile = createTempImageFile(imageType);//将要保存图片的路径
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                photos.put("upload_picture\"; filename=\"" + uploadImageFile.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), uploadImageFile));
//                Luban.get(BLSApp.getInstance())
//                        .load(imageFile)
//                        .putGear(Luban.THIRD_GEAR)
//                        .setFilename(uploadImageFile.getAbsolutePath())
//                        .asObservable()
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .doOnError(new Action1<Throwable>() {
//                            @Override
//                            public void call(Throwable throwable) {
//                                throwable.printStackTrace();
//                            }
//                        })
//                        .onErrorResumeNext(new Func1<Throwable, Observable<? extends File>>() {
//                            @Override
//                            public Observable<? extends File> call(Throwable throwable) {
//                                return Observable.empty();
//                            }
//                        })
//                        .subscribe(new Action1<File>() {
//                            @Override
//                            public void call(File file) {
//                                // TODO 压缩成功后调用，返回压缩后的图片文件
//                                photos.put("upload_picture\"; filename=\"" + file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), uploadImageFile));
////                                return photos;
//
//                            }
//                        });    //启动压缩
//            } else {
            photos.put("file", imageFile);
            return imageFile;
//            }
        } catch (Throwable e) {
            delUploadImageFile();
            return null;
        }
    }

    private void delUploadImageFile() {
        if (uploadImageFile != null) {
            FileUtils.deleteTempFile(uploadImageFile.getAbsolutePath());
            uploadImageFile = null;
        }
    }

    private synchronized static String getImageType(String uri) {
        String[] array = uri.split("\\.");
        return "." + array[array.length - 1].toLowerCase();
    }

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
