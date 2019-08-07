package com.jhhscm.platform.views.selector;

import com.jhhscm.platform.bean.UploadImage;

import java.util.List;

/**
 * 类说明：
 * 作者：huangqiuxin on 2016/6/8 15:21
 * 邮箱：648859026@qq.com
 */
public interface IAUploadImageView extends IAView{
    String getContent();
    List<UploadImage> getUploadImageList();
    void setImageToken(UploadImage uploadImage);
    String getDesignerId();
}
