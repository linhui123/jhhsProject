package com.jhhscm.platform.fragment.my.book;

import java.io.Serializable;

public class DetailToolBean implements Serializable {

    /**
     * data : {"id":1,"userCode":"1111111111","dataCode":"20191119095230001740792477","dataType":0,"price1":100,"price2":200,"price3":300,"inType":11,"outType":0,"dataContent":"12212","picSmallUrl":"[11,11xx]","desc":"22,22xx","dataTime":"2019-11-11 00:00:00","addTime":"2019-11-19 09:52:19","updateTime":"2019-11-19 09:52:19","deleted":0}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * id : 1
         * userCode : 1111111111
         * dataCode : 20191119095230001740792477
         * dataType : 0
         * price1 : 100.0
         * price2 : 200.0
         * price3 : 300.0
         * inType : 11
         * outType : 0
         * dataContent : 12212
         * picSmallUrl : [11,11xx]
         * desc : 22,22xx
         * dataTime : 2019-11-11 00:00:00
         * addTime : 2019-11-19 09:52:19
         * updateTime : 2019-11-19 09:52:19
         * deleted : 0
         */

        private int id;
        private String userCode;
        private String data_code;
        private int dataType;
        private double price_1;
        private double price_2;
        private double price_3;
        private int in_type;
        private int out_type;
        private String data_content;
        private String pic_small_url;
        private String desc;
        private String data_time;
        private String addTime;
        private String updateTime;
        private int deleted;
        private String in_type_name;
        private String out_type_name;

        public String getIn_type_name() {
            return in_type_name;
        }

        public void setIn_type_name(String in_type_name) {
            this.in_type_name = in_type_name;
        }

        public String getOut_type_name() {
            return out_type_name;
        }

        public void setOut_type_name(String out_type_name) {
            this.out_type_name = out_type_name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUserCode() {
            return userCode;
        }

        public void setUserCode(String userCode) {
            this.userCode = userCode;
        }

        public String getData_code() {
            return data_code;
        }

        public void setData_code(String data_code) {
            this.data_code = data_code;
        }

        public int getDataType() {
            return dataType;
        }

        public void setDataType(int dataType) {
            this.dataType = dataType;
        }

        public double getPrice_1() {
            return price_1;
        }

        public void setPrice_1(double price_1) {
            this.price_1 = price_1;
        }

        public double getPrice_2() {
            return price_2;
        }

        public void setPrice_2(double price_2) {
            this.price_2 = price_2;
        }

        public double getPrice_3() {
            return price_3;
        }

        public void setPrice_3(double price_3) {
            this.price_3 = price_3;
        }

        public int getIn_type() {
            return in_type;
        }

        public void setIn_type(int in_type) {
            this.in_type = in_type;
        }

        public int getOut_type() {
            return out_type;
        }

        public void setOut_type(int out_type) {
            this.out_type = out_type;
        }

        public String getData_content() {
            return data_content;
        }

        public void setData_content(String data_content) {
            this.data_content = data_content;
        }

        public String getPic_small_url() {
            return pic_small_url;
        }

        public void setPic_small_url(String pic_small_url) {
            this.pic_small_url = pic_small_url;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getData_time() {
            return data_time;
        }

        public void setData_time(String data_time) {
            this.data_time = data_time;
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
