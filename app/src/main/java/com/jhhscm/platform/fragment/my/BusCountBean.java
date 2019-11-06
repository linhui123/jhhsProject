package com.jhhscm.platform.fragment.my;

import java.util.List;

public class BusCountBean {

    /**
     * result : {"data":[{"users_num":1,"goods_num":4,"order_num":0}]}
     */

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
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
             * users_num : 1
             * goods_num : 4
             * order_num : 0
             */

            private int users_num;
            private int goods_num;
            private int order_num;

            public int getUsers_num() {
                return users_num;
            }

            public void setUsers_num(int users_num) {
                this.users_num = users_num;
            }

            public int getGoods_num() {
                return goods_num;
            }

            public void setGoods_num(int goods_num) {
                this.goods_num = goods_num;
            }

            public int getOrder_num() {
                return order_num;
            }

            public void setOrder_num(int order_num) {
                this.order_num = order_num;
            }
        }
    }
}
