package com.jhhscm.platform.http.bean;

import java.io.Serializable;

/**
 * 类说明：
 * 作者：huangqiuxin on 2016/5/24 17:56
 * 邮箱：648859026@qq.com
 */
public class UploadImageResponse implements Serializable {
    /**
     * allfilePath : http://blsfiles.oss-cn-qingdao.aliyuncs.com/ccustomerhead/b1561371fd4a49df83bfaf039962b547.jpg?Expires=1868858110&OSSAccessKeyId=LTAIcoyXo1U2nZ2l&Signature=SwC6HCdxnvig7P%2F4TYnI4QXihys%3D
     * catalogues : ccustomerhead/b1561371fd4a49df83bfaf039962b547.jpg
     */

    private String allfilePath;
    private String catalogues;

    public String getAllfilePath() {
        return allfilePath;
    }

    public void setAllfilePath(String allfilePath) {
        this.allfilePath = allfilePath;
    }

    public String getCatalogues() {
        return catalogues;
    }

    public void setCatalogues(String catalogues) {
        this.catalogues = catalogues;
    }
}
