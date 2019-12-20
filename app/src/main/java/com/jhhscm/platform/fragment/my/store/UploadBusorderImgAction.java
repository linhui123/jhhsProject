package com.jhhscm.platform.fragment.my.store;

import android.content.Context;
import android.os.Environment;

import com.jhhscm.platform.fragment.Mechanics.push.OldMechanicsUpImageBean;
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

public class UploadBusorderImgAction extends AHttpService<OldMechanicsUpImageBean> {

    private String imagePath;
    public final long UPLOAD_IMAGE_SIZE_LIMIT = 100* 1024;//1M
    private File uploadImageFile;
    private String mToken;

    public static UploadBusorderImgAction newInstance(Context context, File file, String token) {
        return new UploadBusorderImgAction(context, file,token);
    }

    public UploadBusorderImgAction(Context context, File file, String token) {
        super(context);
        this.uploadImageFile = file;
        this.mToken=token;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)//表单类型
                .addFormDataPart(Fields.Token, ConfigUtils.getCurrentUser(context).getToken());
        RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/jpg"), uploadImageFile);
        builder.addFormDataPart("file", uploadImageFile.getName(), photoRequestBody);
        List<MultipartBody.Part> parts = builder.build().parts();

        return apiService.uploadBusorderImages(parts);
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
            photos.put("file", imageFile);
            return imageFile;
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

