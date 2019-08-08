package com.jhhscm.platform.fragment.Mechanics.bean;

public class GetOldDetailsBean {

    /**
     * result : {"goodsDetails":{"good_code":"19","city":"河北省","counter_price":0,"h5_address":"http://192.168.0.234:8080/#/product/oldProductDetail","factory_time":"2019-02-01","picUrl":"https://wajuejifiles.oss-cn-beijing.aliyuncs.com/C%3A%5CUsers%5CAdministrator%5CDesktop%5C%E5%B7%A5%E5%85%B7%E7%AE%B1%5Ctest.png","province":"河北省","word_detail":"固定的一个详情内容","name":"EC666","detail":"这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数","old_time":2000}}
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
         * goodsDetails : {"good_code":"19","city":"河北省","counter_price":0,"h5_address":"http://192.168.0.234:8080/#/product/oldProductDetail","factory_time":"2019-02-01","picUrl":"https://wajuejifiles.oss-cn-beijing.aliyuncs.com/C%3A%5CUsers%5CAdministrator%5CDesktop%5C%E5%B7%A5%E5%85%B7%E7%AE%B1%5Ctest.png","province":"河北省","word_detail":"固定的一个详情内容","name":"EC666","detail":"这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数","old_time":2000}
         */

        private GoodsDetailsBean goodsDetails;

        public GoodsDetailsBean getGoodsDetails() {
            return goodsDetails;
        }

        public void setGoodsDetails(GoodsDetailsBean goodsDetails) {
            this.goodsDetails = goodsDetails;
        }

        public static class GoodsDetailsBean {
            /**
             * good_code : 19
             * city : 河北省
             * counter_price : 0
             * h5_address : http://192.168.0.234:8080/#/product/oldProductDetail
             * factory_time : 2019-02-01
             * picUrl : https://wajuejifiles.oss-cn-beijing.aliyuncs.com/C%3A%5CUsers%5CAdministrator%5CDesktop%5C%E5%B7%A5%E5%85%B7%E7%AE%B1%5Ctest.png
             * province : 河北省
             * word_detail : 固定的一个详情内容
             * name : EC666
             * detail : 这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数这是文字参数
             * old_time : 2000
             */

            private String good_code;
            private String city;
            private String counter_price;
            private String h5_address;
            private String factory_time;
            private String picUrl;
            private String province;
            private String word_detail;
            private String name;
            private String detail;
            private String old_time;

            public String getGood_code() {
                return good_code;
            }

            public void setGood_code(String good_code) {
                this.good_code = good_code;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getCounter_price() {
                return counter_price;
            }

            public void setCounter_price(String counter_price) {
                this.counter_price = counter_price;
            }

            public String getH5_address() {
                return h5_address;
            }

            public void setH5_address(String h5_address) {
                this.h5_address = h5_address;
            }

            public String getFactory_time() {
                return factory_time;
            }

            public void setFactory_time(String factory_time) {
                this.factory_time = factory_time;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getWord_detail() {
                return word_detail;
            }

            public void setWord_detail(String word_detail) {
                this.word_detail = word_detail;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }

            public String getOld_time() {
                return old_time;
            }

            public void setOld_time(String old_time) {
                this.old_time = old_time;
            }
        }
    }
}
