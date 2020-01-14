package com.jhhscm.platform.fragment.Mechanics.bean;

import java.util.List;

public class GoodsCatatoryListBean {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * secend_brand_list : [{"name":"测试","id":"13","pic_url":""}]
         * name : 油品
         * id : 8
         * pic_url :
         */
        private boolean isSelect = false;
        private String name;
        private String id;
        private String pic_url;
        private List<SecendBrandListBean> secend_brand_list;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
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

        public List<SecendBrandListBean> getSecend_brand_list() {
            return secend_brand_list;
        }

        public void setSecend_brand_list(List<SecendBrandListBean> secend_brand_list) {
            this.secend_brand_list = secend_brand_list;
        }

        public static class SecendBrandListBean {
            /**
             * name : 测试
             * id : 13
             * pic_url :
             */
            private boolean isSelect = false;
            private String name;
            private String id;
            private String pic_url;

            public boolean isSelect() {
                return isSelect;
            }

            public void setSelect(boolean select) {
                isSelect = select;
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
        }
    }
}
