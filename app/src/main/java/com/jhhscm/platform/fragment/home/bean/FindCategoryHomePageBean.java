package com.jhhscm.platform.fragment.home.bean;

import java.io.Serializable;
import java.util.List;

public class FindCategoryHomePageBean {

    private List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable {
        /**
         * icon_url : https://wajuejifiles.oss-cn-beijing.aliyuncs.com/1.jpeg
         * name : 履带
         * id : 11
         * pic_url :
         * keyword :
         */

        private String icon_url;
        private String name;
        private String id;
        private String pic_url;
        private String keyword;

        public String getIcon_url() {
            return icon_url;
        }

        public void setIcon_url(String icon_url) {
            this.icon_url = icon_url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }
    }
}
