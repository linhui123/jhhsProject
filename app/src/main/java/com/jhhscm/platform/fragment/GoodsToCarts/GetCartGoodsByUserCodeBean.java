package com.jhhscm.platform.fragment.GoodsToCarts;

import java.io.Serializable;
import java.util.List;

public class GetCartGoodsByUserCodeBean implements Serializable {

    private List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable {
        /**
         * goods_code : 2019101140000187528775
         * goodsList : [{"number":1,"picUrl":"http://wajueji.oss-cn-shenzhen.aliyuncs.com/category_water/0ee098189fb74011bd022cc2de23ab9b.png?Expires=1886146600&OSSAccessKeyId=LTAI4F3Gt8M6rbEl&Signature=ck9Qvy7UVolayGh%2BhIXSwSrg%2BfQ%3D","price":457,"goodsCode":"2019101140000187528775","id":161,"goodsName":"950空调滤芯"}]
         * bus_name : 小崔商户
         * bus_code : 4000000034777111
         */

        private String goods_code;
        private String bus_name;
        private String bus_code;
        private String freight_price;//运费
        private String sum;//小计
        private List<GoodsListBean> goodsList;
        private boolean ischeck = false;

        public String getSum() {
            return sum;
        }

        public void setSum(String sum) {
            this.sum = sum;
        }

        public String getFreight_price() {
            return freight_price;
        }

        public void setFreight_price(String freight_price) {
            this.freight_price = freight_price;
        }

        public boolean isIscheck() {
            return ischeck;
        }

        public void setIscheck(boolean ischeck) {
            this.ischeck = ischeck;
        }

        public String getGoods_code() {
            return goods_code;
        }

        public void setGoods_code(String goods_code) {
            this.goods_code = goods_code;
        }

        public String getBus_name() {
            return bus_name;
        }

        public void setBus_name(String bus_name) {
            this.bus_name = bus_name;
        }

        public String getBus_code() {
            return bus_code;
        }

        public void setBus_code(String bus_code) {
            this.bus_code = bus_code;
        }

        public List<GoodsListBean> getGoodsList() {
            return goodsList;
        }

        public void setGoodsList(List<GoodsListBean> goodsList) {
            this.goodsList = goodsList;
        }

        public static class GoodsListBean implements Serializable {
            /**
             * number : 1
             * picUrl : http://wajueji.oss-cn-shenzhen.aliyuncs.com/category_water/0ee098189fb74011bd022cc2de23ab9b.png?Expires=1886146600&OSSAccessKeyId=LTAI4F3Gt8M6rbEl&Signature=ck9Qvy7UVolayGh%2BhIXSwSrg%2BfQ%3D
             * price : 457.0
             * goodsCode : 2019101140000187528775
             * id : 161
             * goodsName : 950空调滤芯
             */

            private int number = 1;
            private String picUrl;
            private String price;
            private String goodsCode;
            private String id;
            private String goodsName;
            private boolean ischeck = false;

            public boolean isIscheck() {
                return ischeck;
            }

            public void setIscheck(boolean ischeck) {
                this.ischeck = ischeck;
            }

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getGoodsCode() {
                return goodsCode;
            }

            public void setGoodsCode(String goodsCode) {
                this.goodsCode = goodsCode;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }
        }
    }
}
