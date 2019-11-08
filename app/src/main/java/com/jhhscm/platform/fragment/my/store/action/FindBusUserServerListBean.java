package com.jhhscm.platform.fragment.my.store.action;

import java.util.List;

public class FindBusUserServerListBean {


    /**
     * result : {"data":[{"brandName":"XXX品牌","brand_id":"XXX品牌ID","fix_p_17":"XXX型号","goodsnum":"设备序列号","gpsnum":"GPS序列号"}]}
     * sum : 0
     */

    private ResultBean result;
    private String sum;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public static class ResultBean {
        private List<DataBean> data;

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * brandName : XXX品牌
             * brand_id : XXX品牌ID
             * fix_p_17 : XXX型号
             * goodsnum : 设备序列号
             * gpsnum : GPS序列号
             */

            private String brandName;
            private String brand_id;
            private String fix_p_17;
            private String goodsnum;
            private String gpsnum;

            public String getBrandName() {
                return brandName;
            }

            public void setBrandName(String brandName) {
                this.brandName = brandName;
            }

            public String getBrand_id() {
                return brand_id;
            }

            public void setBrand_id(String brand_id) {
                this.brand_id = brand_id;
            }

            public String getFix_p_17() {
                return fix_p_17;
            }

            public void setFix_p_17(String fix_p_17) {
                this.fix_p_17 = fix_p_17;
            }

            public String getGoodsnum() {
                return goodsnum;
            }

            public void setGoodsnum(String goodsnum) {
                this.goodsnum = goodsnum;
            }

            public String getGpsnum() {
                return gpsnum;
            }

            public void setGpsnum(String gpsnum) {
                this.gpsnum = gpsnum;
            }
        }
    }
}
