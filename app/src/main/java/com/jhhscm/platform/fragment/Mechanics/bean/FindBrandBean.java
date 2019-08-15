package com.jhhscm.platform.fragment.Mechanics.bean;

import java.util.List;

public class FindBrandBean {


    private List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * name : 斗山
         * id : 2
         * pic_url : https://wajuejifiles.oss-cn-beijing.aliyuncs.com/1.jpeg
         * floor_price : 0
         * desc : 挖掘机品牌
         */

        private String name;
        private String id;
        private String pic_url;
        private String floor_price;
        private String desc;
        private boolean select;

        public ResultBean(String name, String id) {
            this.name = name;
            this.id = id;
        }

        public boolean isSelect() {
            return select;
        }

        public void setSelect(boolean select) {
            this.select = select;
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

        public String getFloor_price() {
            return floor_price;
        }

        public void setFloor_price(String floor_price) {
            this.floor_price = floor_price;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
