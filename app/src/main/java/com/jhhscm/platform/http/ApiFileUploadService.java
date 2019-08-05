package com.jhhscm.platform.http;




import com.jhhscm.platform.http.bean.UploadImageResponse;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

/**
 * 类说明：
 * 作者：huangqiuxin on 2016/5/23 10:22
 * 邮箱：648859026@qq.com
 */
public  interface ApiFileUploadService {
//    http://oyyxdev2.91uda.com/api?resprotocol=json&class=System&method=UploadPicture
    String UPLOAD_IMAGE_URL = "appCase/uploadImg";
    @Multipart
    @POST(UPLOAD_IMAGE_URL)
    Call<UploadImageResponse> uploadImages(@PartMap Map<String, RequestBody> imgs);
}
