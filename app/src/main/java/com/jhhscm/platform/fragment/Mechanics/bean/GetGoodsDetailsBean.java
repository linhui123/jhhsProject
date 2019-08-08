package com.jhhscm.platform.fragment.Mechanics.bean;

import java.util.List;

public class GetGoodsDetailsBean {

    /**
     * result : {"wAppKvs":[{"key_group_value":"综述","key_group_name":"goods_sum"},{"key_group_value":"参数","key_group_name":"goods_parameter"},{"key_group_value":"图片","key_group_name":"goods_photo"}],"goodsDetails":{"fix_p3":"4","fix_p2":"3","fix_p5":"4","picGalleryUrlList":"[\"https://wajuejifiles.oss-cn-beijing.aliyuncs.com/C%3A%5CUsers%5CAdministrator%5CDesktop%5C%E5%B7%A5%E5%85%B7%E7%AE%B1%5Ctest.png\",\"https://wajuejifiles.oss-cn-beijing.aliyuncs.com/C%3A%5CUsers%5CAdministrator%5CDesktop%5C%E5%B7%A5%E5%85%B7%E7%AE%B1%5Ctest.png\",\"https://wajuejifiles.oss-cn-beijing.aliyuncs.com/C%3A%5CUsers%5CAdministrator%5CDesktop%5C%E5%B7%A5%E5%85%B7%E7%AE%B1%5Ctest.png\"]","fix_p4":"4","good_code":"1","picSmallUrl":"https://wajuejifiles.oss-cn-beijing.aliyuncs.com/1.jpeg","fix_p1":"2","counter_price":10,"h5_address":"http://192.168.0.234:8080/#/product/productDetail","word_detail":"固定的一个详情内容","name":"EC480D","fix_p7":"5","fix_p6":"4"}}
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
         * wAppKvs : [{"key_group_value":"综述","key_group_name":"goods_sum"},{"key_group_value":"参数","key_group_name":"goods_parameter"},{"key_group_value":"图片","key_group_name":"goods_photo"}]
         * goodsDetails : {"fix_p3":"4","fix_p2":"3","fix_p5":"4","picGalleryUrlList":"[\"https://wajuejifiles.oss-cn-beijing.aliyuncs.com/C%3A%5CUsers%5CAdministrator%5CDesktop%5C%E5%B7%A5%E5%85%B7%E7%AE%B1%5Ctest.png\",\"https://wajuejifiles.oss-cn-beijing.aliyuncs.com/C%3A%5CUsers%5CAdministrator%5CDesktop%5C%E5%B7%A5%E5%85%B7%E7%AE%B1%5Ctest.png\",\"https://wajuejifiles.oss-cn-beijing.aliyuncs.com/C%3A%5CUsers%5CAdministrator%5CDesktop%5C%E5%B7%A5%E5%85%B7%E7%AE%B1%5Ctest.png\"]","fix_p4":"4","good_code":"1","picSmallUrl":"https://wajuejifiles.oss-cn-beijing.aliyuncs.com/1.jpeg","fix_p1":"2","counter_price":10,"h5_address":"http://192.168.0.234:8080/#/product/productDetail","word_detail":"固定的一个详情内容","name":"EC480D","fix_p7":"5","fix_p6":"4"}
         */

        private GoodsDetailsBean goodsDetails;
        private List<WAppKvsBean> wAppKvs;

        public GoodsDetailsBean getGoodsDetails() {
            return goodsDetails;
        }

        public void setGoodsDetails(GoodsDetailsBean goodsDetails) {
            this.goodsDetails = goodsDetails;
        }

        public List<WAppKvsBean> getWAppKvs() {
            return wAppKvs;
        }

        public void setWAppKvs(List<WAppKvsBean> wAppKvs) {
            this.wAppKvs = wAppKvs;
        }

        public static class GoodsDetailsBean {
            /**
             * fix_p3 : 4
             * fix_p2 : 3
             * fix_p5 : 4
             * picGalleryUrlList : ["https://wajuejifiles.oss-cn-beijing.aliyuncs.com/C%3A%5CUsers%5CAdministrator%5CDesktop%5C%E5%B7%A5%E5%85%B7%E7%AE%B1%5Ctest.png","https://wajuejifiles.oss-cn-beijing.aliyuncs.com/C%3A%5CUsers%5CAdministrator%5CDesktop%5C%E5%B7%A5%E5%85%B7%E7%AE%B1%5Ctest.png","https://wajuejifiles.oss-cn-beijing.aliyuncs.com/C%3A%5CUsers%5CAdministrator%5CDesktop%5C%E5%B7%A5%E5%85%B7%E7%AE%B1%5Ctest.png"]
             * fix_p4 : 4
             * good_code : 1
             * picSmallUrl : https://wajuejifiles.oss-cn-beijing.aliyuncs.com/1.jpeg
             * fix_p1 : 2
             * counter_price : 10
             * h5_address : http://192.168.0.234:8080/#/product/productDetail
             * word_detail : 固定的一个详情内容
             * name : EC480D
             * fix_p7 : 5
             * fix_p6 : 4
             */

            private String fix_p3;
            private String fix_p2;
            private String fix_p5;
            private String picGalleryUrlList;
            private String fix_p4;
            private String good_code;
            private String picSmallUrl;
            private String fix_p1;
            private int counter_price;
            private String h5_address;
            private String word_detail;
            private String name;
            private String fix_p7;
            private String fix_p6;

            public String getFix_p3() {
                return fix_p3;
            }

            public void setFix_p3(String fix_p3) {
                this.fix_p3 = fix_p3;
            }

            public String getFix_p2() {
                return fix_p2;
            }

            public void setFix_p2(String fix_p2) {
                this.fix_p2 = fix_p2;
            }

            public String getFix_p5() {
                return fix_p5;
            }

            public void setFix_p5(String fix_p5) {
                this.fix_p5 = fix_p5;
            }

            public String getPicGalleryUrlList() {
                return picGalleryUrlList;
            }

            public void setPicGalleryUrlList(String picGalleryUrlList) {
                this.picGalleryUrlList = picGalleryUrlList;
            }

            public String getFix_p4() {
                return fix_p4;
            }

            public void setFix_p4(String fix_p4) {
                this.fix_p4 = fix_p4;
            }

            public String getGood_code() {
                return good_code;
            }

            public void setGood_code(String good_code) {
                this.good_code = good_code;
            }

            public String getPicSmallUrl() {
                return picSmallUrl;
            }

            public void setPicSmallUrl(String picSmallUrl) {
                this.picSmallUrl = picSmallUrl;
            }

            public String getFix_p1() {
                return fix_p1;
            }

            public void setFix_p1(String fix_p1) {
                this.fix_p1 = fix_p1;
            }

            public int getCounter_price() {
                return counter_price;
            }

            public void setCounter_price(int counter_price) {
                this.counter_price = counter_price;
            }

            public String getH5_address() {
                return h5_address;
            }

            public void setH5_address(String h5_address) {
                this.h5_address = h5_address;
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

            public String getFix_p7() {
                return fix_p7;
            }

            public void setFix_p7(String fix_p7) {
                this.fix_p7 = fix_p7;
            }

            public String getFix_p6() {
                return fix_p6;
            }

            public void setFix_p6(String fix_p6) {
                this.fix_p6 = fix_p6;
            }
        }

        public static class WAppKvsBean {
            /**
             * key_group_value : 综述
             * key_group_name : goods_sum
             */

            private String key_group_value;
            private String key_group_name;

            public String getKey_group_value() {
                return key_group_value;
            }

            public void setKey_group_value(String key_group_value) {
                this.key_group_value = key_group_value;
            }

            public String getKey_group_name() {
                return key_group_name;
            }

            public void setKey_group_name(String key_group_name) {
                this.key_group_name = key_group_name;
            }
        }
    }
}
