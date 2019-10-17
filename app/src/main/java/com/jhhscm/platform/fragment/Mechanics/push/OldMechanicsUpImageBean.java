package com.jhhscm.platform.fragment.Mechanics.push;

public class OldMechanicsUpImageBean {

    /**
     * errno : 0
     * data : {"code":"0","allfilePath":"http://wajueji.oss-cn-shenzhen.aliyuncs.com/oldGood/c9180b9140e54e45b3ed8d3f16bbc92b.png?Expires=1880595756&OSSAccessKeyId=LTAI4F3Gt8M6rbEl&Signature=Tk83n1tmcnjLvQsW%2BgV90caE4mY%3D","catalogues":"oldGood/c9180b9140e54e45b3ed8d3f16bbc92b.png"}
     * errmsg : 成功
     */

    private String code;
    private String message;
    private String errno;
    private DataBean data;
    private String errmsg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrno() {
        return errno;
    }

    public void setErrno(String errno) {
        this.errno = errno;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public static class DataBean {
        /**
         * code : 0
         * allfilePath : http://wajueji.oss-cn-shenzhen.aliyuncs.com/oldGood/c9180b9140e54e45b3ed8d3f16bbc92b.png?Expires=1880595756&OSSAccessKeyId=LTAI4F3Gt8M6rbEl&Signature=Tk83n1tmcnjLvQsW%2BgV90caE4mY%3D
         * catalogues : oldGood/c9180b9140e54e45b3ed8d3f16bbc92b.png
         */
        private String msg;
        private String code;
        private String allfilePath;
        private String catalogues;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

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
}
