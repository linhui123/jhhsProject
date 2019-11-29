package com.jhhscm.platform.fragment.Mechanics.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GetGoodsDetailsBean implements Serializable {

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

    public static class ResultBean implements Serializable {
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

        public static class GoodsDetailsBean implements Serializable {

            /**
             * fix_p_12 : 5
             * fix_p_10 : 2
             * picGalleryUrlList : ["http://wajueji.oss-cn-shenzhen.aliyuncs.com/good/c232752bf386458fb710bdf046a5c2c1.jpeg?Expires=1880951144&OSSAccessKeyId=LTAI4F3Gt8M6rbEl&Signature=Qj%2FUzulndg5p%2BQvJ0pX6T%2FQYso0%3D","http://wajueji.oss-cn-shenzhen.aliyuncs.com/good/f81613d1e99c42e8996c73502e948e77.jpeg?Expires=1880951146&OSSAccessKeyId=LTAI4F3Gt8M6rbEl&Signature=srP%2BEAhPxFlXLDLgavOSNsjTwkI%3D","http://wajueji.oss-cn-shenzhen.aliyuncs.com/good/cea282e616c14c2d983cc32a3f0e5c9f.jpeg?Expires=1880951148&OSSAccessKeyId=LTAI4F3Gt8M6rbEl&Signature=XaTjDudFiByXfCOVrkuK9cUiXxo%3D","http://wajueji.oss-cn-shenzhen.aliyuncs.com/good/c1b3948be93646ea879edf61493e4e94.jpeg?Expires=1880951151&OSSAccessKeyId=LTAI4F3Gt8M6rbEl&Signature=AOkwM2FclJM3SVkaAqkDrJkG050%3D"]
             * fix_p_11 : 2
             * good_code : 68923551cc5940d6a11fab34a8ceaa91
             * fix_p_17 : 1651X
             * counter_price : 0.0
             * fix_p_1 : 51
             * fix_p_2 : 20
             * merchant_id : 1
             * fix_p_5 : 184
             * fix_p_9 : 3
             * brandId : 4
             * name : 联调06
             * id : 99
             */

            private String fix_p_12;
            private String fix_p_10;
            private String picGalleryUrlList;
            private String fix_p_11;
            private String good_code;
            private String fix_p_17;
            private String counter_price;
            private String fix_p_1;
            private String fix_p_2;
            private String merchant_id;
            private String fix_p_5;
            private String fix_p_9;
            private int brandId;
            private String name;
            private int id;
            /**
             * fix_p3 : 4
             * fix_p2 : 3
             * fix_p5 : 4
             * fix_p4 : 4
             * picSmallUrl : https://wajuejifiles.oss-cn-beijing.aliyuncs.com/1.jpeg
             * fix_p1 : 2
             * counter_price : 10
             * h5_address : http://192.168.0.234:8080/#/product/productDetail
             * word_detail : 固定的一个详情内容
             * fix_p7 : 5
             * fix_p6 : 4
             */

            private String picSmallUrl;
            private String h5_address;
            private String word_detail;
            private String merchant_text;
            private String fix_p_11_text;
            private String fix_p_10_text;

            public String getFix_p_10_text() {
                return fix_p_10_text;
            }

            public void setFix_p_10_text(String fix_p_10_text) {
                this.fix_p_10_text = fix_p_10_text;
            }

            public String getFix_p_11_text() {
                return fix_p_11_text;
            }

            public void setFix_p_11_text(String fix_p_11_text) {
                this.fix_p_11_text = fix_p_11_text;
            }

            public String getMerchant_text() {
                return merchant_text;
            }

            public void setMerchant_text(String merchant_text) {
                this.merchant_text = merchant_text;
            }

            public String getFix_p_12() {
                return fix_p_12;
            }

            public void setFix_p_12(String fix_p_12) {
                this.fix_p_12 = fix_p_12;
            }

            public String getFix_p_10() {
                return fix_p_10;
            }

            public void setFix_p_10(String fix_p_10) {
                this.fix_p_10 = fix_p_10;
            }

            public String getPicGalleryUrlList() {
                return picGalleryUrlList;
            }

            public void setPicGalleryUrlList(String picGalleryUrlList) {
                this.picGalleryUrlList = picGalleryUrlList;
            }

            public String getFix_p_11() {
                return fix_p_11;
            }

            public void setFix_p_11(String fix_p_11) {
                this.fix_p_11 = fix_p_11;
            }

            public String getGood_code() {
                return good_code;
            }

            public void setGood_code(String good_code) {
                this.good_code = good_code;
            }

            public String getFix_p_17() {
                return fix_p_17;
            }

            public void setFix_p_17(String fix_p_17) {
                this.fix_p_17 = fix_p_17;
            }

            public String getCounter_price() {
                return counter_price;
            }

            public void setCounter_price(String counter_price) {
                this.counter_price = counter_price;
            }

            public String getFix_p_1() {
                return fix_p_1;
            }

            public void setFix_p_1(String fix_p_1) {
                this.fix_p_1 = fix_p_1;
            }

            public String getFix_p_2() {
                return fix_p_2;
            }

            public void setFix_p_2(String fix_p_2) {
                this.fix_p_2 = fix_p_2;
            }

            public String getMerchant_id() {
                return merchant_id;
            }

            public void setMerchant_id(String merchant_id) {
                this.merchant_id = merchant_id;
            }

            public String getFix_p_5() {
                return fix_p_5;
            }

            public void setFix_p_5(String fix_p_5) {
                this.fix_p_5 = fix_p_5;
            }

            public String getFix_p_9() {
                return fix_p_9;
            }

            public void setFix_p_9(String fix_p_9) {
                this.fix_p_9 = fix_p_9;
            }

            public int getBrandId() {
                return brandId;
            }

            public void setBrandId(int brandId) {
                this.brandId = brandId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getPicSmallUrl() {
                return picSmallUrl;
            }

            public void setPicSmallUrl(String picSmallUrl) {
                this.picSmallUrl = picSmallUrl;
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
        }

        public static class WAppKvsBean implements Serializable {
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
