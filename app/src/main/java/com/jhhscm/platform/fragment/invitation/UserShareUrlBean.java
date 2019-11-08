package com.jhhscm.platform.fragment.invitation;

public class UserShareUrlBean {


    /**
     * result : {"url":"www.xx.xx?xx/xx?isbus=0&user_code=1000000330781973"}
     */

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * url : www.xx.xx?xx/xx?isbus=0&user_code=1000000330781973
         */

        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
