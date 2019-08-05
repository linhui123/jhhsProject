package com.jhhscm.platform.http.action;

import android.content.Context;

import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.ApiService;
import com.jhhscm.platform.http.Fields;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.UploadImageResponse;
import com.jhhscm.platform.tool.ConfigUtils;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * Created by Administrator on 2018/10/18/018.
 */

public class UploadAvatarAction extends AHttpService<BaseEntity<UploadImageResponse>> {

    private File uploadImageFile;

    public static UploadAvatarAction newInstance(Context context, File file) {
        return new UploadAvatarAction(context, file);
    }

    public UploadAvatarAction(Context context, File file) {
        super(context);
        this.uploadImageFile = file;
    }

    @Override
    protected Call newRetrofitCall(ApiService apiService) {
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("application/otcet-stream"), uploadImageFile);

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", uploadImageFile.getName(), requestFile);


//        MultipartBody.Builder builder = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)//表单类型
//                .addFormDataPart(Fields.Token, ConfigUtils.getCurrentUser(context).getToken());
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)//表单类型
                .addFormDataPart(Fields.Token, "");
        RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/jpg"), uploadImageFile);
        builder.addFormDataPart("file", uploadImageFile.getName(), photoRequestBody);
        List<MultipartBody.Part> parts = builder.build().parts();

//        return apiService.CustomerUploadImages(parts);
        return null;
    }
}
