package com.jhhscm.platform.fragment.home.bean;

import java.util.List;

public class FindLabourReleaseHomePageBean {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * labour_code : 1
         * name : 招聘挖掘机小能手
         * id : 1
         */

        private String labour_code;
        private String name;
        private String id;

        public String getLabour_code() {
            return labour_code;
        }

        public void setLabour_code(String labour_code) {
            this.labour_code = labour_code;
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
    }
}
