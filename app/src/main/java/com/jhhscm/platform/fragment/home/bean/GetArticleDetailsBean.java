package com.jhhscm.platform.fragment.home.bean;

public class GetArticleDetailsBean {

    /**
     * data : {"content_text":"震惊，挖掘机居然还有这样的秘密","title":"挖掘机30个不得不说的秘密！！！","content":"<p>挖掘机30个不得不说的秘密！！！<\/p>","release_time":"2019-08-31 00:00:00"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * content_text : 震惊，挖掘机居然还有这样的秘密
         * title : 挖掘机30个不得不说的秘密！！！
         * content : <p>挖掘机30个不得不说的秘密！！！</p>
         * release_time : 2019-08-31 00:00:00
         */

        private String content_text;
        private String title;
        private String content;
        private String release_time;

        public String getContent_text() {
            return content_text;
        }

        public void setContent_text(String content_text) {
            this.content_text = content_text;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getRelease_time() {
            return release_time;
        }

        public void setRelease_time(String release_time) {
            this.release_time = release_time;
        }
    }
}
