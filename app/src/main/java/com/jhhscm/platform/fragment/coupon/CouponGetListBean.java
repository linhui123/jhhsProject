package com.jhhscm.platform.fragment.coupon;

import java.util.List;

public class CouponGetListBean {

    /**
     * result : {"data":[{"id":9,"code":"20191030151740001636663019","type":1,"name":"12","startTime":"2019-10-30 00:00:00","endTime":"2019-10-31 00:00:00","min":0,"limit":0,"discount":22,"desc":"44","tag":"","total":0,"status":0,"useValue":"[]","addTime":"2019-10-30 15:17:38","updateTime":"2019-10-30 15:17:38","deleted":0},{"id":8,"code":"20191030151180001388097777","type":0,"name":"123","startTime":"2019-10-30 15:10:01","endTime":"2019-10-31 00:00:00","min":0,"limit":0,"discount":21,"desc":"","tag":"","total":0,"status":0,"useValue":"[]","addTime":"2019-10-30 15:11:23","updateTime":"2019-10-30 15:11:23","deleted":0}]}
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
             * id : 9
             * code : 20191030151740001636663019
             * type : 1
             * name : 12
             * startTime : 2019-10-30 00:00:00
             * endTime : 2019-10-31 00:00:00
             * min : 0.0
             * limit : 0
             * discount : 22.0
             * desc : 44
             * tag :
             * total : 0
             * status : 0
             * useValue : []
             * addTime : 2019-10-30 15:17:38
             * updateTime : 2019-10-30 15:17:38
             * deleted : 0
             */

            private int id;
            private String code;
            private int type;
            private String name;
            private String startTime;
            private String endTime;
            private double min;
            private int limit;
            private double discount;
            private String desc;
            private String tag;
            private int total;
            private int status;
            private String useValue;
            private String addTime;
            private String updateTime;
            private int deleted;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public double getMin() {
                return min;
            }

            public void setMin(double min) {
                this.min = min;
            }

            public int getLimit() {
                return limit;
            }

            public void setLimit(int limit) {
                this.limit = limit;
            }

            public double getDiscount() {
                return discount;
            }

            public void setDiscount(double discount) {
                this.discount = discount;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getUseValue() {
                return useValue;
            }

            public void setUseValue(String useValue) {
                this.useValue = useValue;
            }

            public String getAddTime() {
                return addTime;
            }

            public void setAddTime(String addTime) {
                this.addTime = addTime;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public int getDeleted() {
                return deleted;
            }

            public void setDeleted(int deleted) {
                this.deleted = deleted;
            }
        }
    }
}
