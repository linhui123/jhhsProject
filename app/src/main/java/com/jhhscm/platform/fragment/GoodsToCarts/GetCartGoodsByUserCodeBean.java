package com.jhhscm.platform.fragment.GoodsToCarts;

import java.io.Serializable;
import java.util.List;

public class GetCartGoodsByUserCodeBean implements Serializable{

    private List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable {
        /**
         * number : 5
         * picUrl : https://wajuejifiles.oss-cn-beijing.aliyuncs.com/C%3A%5CUsers%5CAdministrator%5CDesktop%5C%E5%B7%A5%E5%85%B7%E7%AE%B1%5Ctest.png
         * price : 2
         * goodsCode : 1
         * id : 1
         * goodsName : EC380D
         */

        private String number;
        private String picUrl;
        private String price;
        private String goodsCode;
        private String id;
        private String goodsName;
        private boolean isSelect;
        public ResultBean() {

        }
        public ResultBean(boolean isSelect, String goodsCode, String id) {
            this.isSelect = isSelect;
            this.goodsCode = goodsCode;
            this.id = id;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
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
