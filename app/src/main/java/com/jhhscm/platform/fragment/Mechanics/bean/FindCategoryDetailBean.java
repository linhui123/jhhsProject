package com.jhhscm.platform.fragment.Mechanics.bean;

import java.util.List;

public class FindCategoryDetailBean {

    /**
     * data : {"pic_gallery_url_list":["https://wajuejifiles.oss-cn-beijing.aliyuncs.com/C%3A%5CUsers%5CAdministrator%5CDesktop%5C%E5%B7%A5%E5%85%B7%E7%AE%B1%5Ctest.png","https://wajuejifiles.oss-cn-beijing.aliyuncs.com/C%3A%5CUsers%5CAdministrator%5CDesktop%5C%E5%B7%A5%E5%85%B7%E7%AE%B1%5Ctest.png","https://wajuejifiles.oss-cn-beijing.aliyuncs.com/C%3A%5CUsers%5CAdministrator%5CDesktop%5C%E5%B7%A5%E5%85%B7%E7%AE%B1%5Ctest.png"],"province":"河北省","sale_num":0,"city":"石家庄市","freight_price":3,"word_detail":"这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数","counter_price":5,"name":"斗齿01","h5_address":"http://192.168.0.234:8080/#/parts/partsDetail21","id":21,"detail":"这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数","fix_p_8":"E312(3252)"}
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
         * pic_gallery_url_list : ["https://wajuejifiles.oss-cn-beijing.aliyuncs.com/C%3A%5CUsers%5CAdministrator%5CDesktop%5C%E5%B7%A5%E5%85%B7%E7%AE%B1%5Ctest.png","https://wajuejifiles.oss-cn-beijing.aliyuncs.com/C%3A%5CUsers%5CAdministrator%5CDesktop%5C%E5%B7%A5%E5%85%B7%E7%AE%B1%5Ctest.png","https://wajuejifiles.oss-cn-beijing.aliyuncs.com/C%3A%5CUsers%5CAdministrator%5CDesktop%5C%E5%B7%A5%E5%85%B7%E7%AE%B1%5Ctest.png"]
         * province : 河北省
         * sale_num : 0
         * city : 石家庄市
         * freight_price : 3.0
         * word_detail : 这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数
         * counter_price : 5.0
         * name : 斗齿01
         * h5_address : http://192.168.0.234:8080/#/parts/partsDetail21
         * id : 21
         * detail : 这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数
         * fix_p_8 : E312(3252)
         */

        private String province;
        private String sale_num;
        private String city;
        private double freight_price;
        private String word_detail;
        private double counter_price;
        private String name;
        private String h5_address;
        private String id;
        private String detail;
        private String fix_p_8;
        private List<String> pic_gallery_url_list;

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getSale_num() {
            return sale_num;
        }

        public void setSale_num(String sale_num) {
            this.sale_num = sale_num;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public double getFreight_price() {
            return freight_price;
        }

        public void setFreight_price(double freight_price) {
            this.freight_price = freight_price;
        }

        public String getWord_detail() {
            return word_detail;
        }

        public void setWord_detail(String word_detail) {
            this.word_detail = word_detail;
        }

        public double getCounter_price() {
            return counter_price;
        }

        public void setCounter_price(double counter_price) {
            this.counter_price = counter_price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getH5_address() {
            return h5_address;
        }

        public void setH5_address(String h5_address) {
            this.h5_address = h5_address;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getFix_p_8() {
            return fix_p_8;
        }

        public void setFix_p_8(String fix_p_8) {
            this.fix_p_8 = fix_p_8;
        }

        public List<String> getPic_gallery_url_list() {
            return pic_gallery_url_list;
        }

        public void setPic_gallery_url_list(List<String> pic_gallery_url_list) {
            this.pic_gallery_url_list = pic_gallery_url_list;
        }
    }
}
