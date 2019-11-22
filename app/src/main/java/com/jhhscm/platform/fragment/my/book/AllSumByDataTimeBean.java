package com.jhhscm.platform.fragment.my.book;

import java.util.List;

public class AllSumByDataTimeBean {


    /**
     * data : [{"price_1":300,"price_2":600,"data_time":"2019-11-10T16:00:00.000+0000","price_3":900,"detail":[{"in_type":11,"price_1":100,"price_2":200,"out_type":0,"price_3":300,"data_time":"2019-11-10T16:00:00.000+0000","out_type_name":"不限","data_content":"12212","data_code":"20191119095230001740792477"},{"in_type":0,"in_type_name":"不限","price_1":100,"price_2":200,"out_type":22,"price_3":300,"data_time":"2019-11-10T16:00:00.000+0000","data_content":"12212","data_code":"20191119095250001964199885"},{"in_type":0,"in_type_name":"不限","price_1":100,"price_2":200,"out_type":22,"price_3":300,"data_time":"2019-11-10T16:00:00.000+0000","data_content":"12212","data_code":"20191119095280000048126687"}]},{"price_1":100,"price_2":200,"data_time":"2019-11-11T16:00:00.000+0000","price_3":300,"detail":[{"in_type":0,"in_type_name":"不限","price_1":100,"price_2":200,"out_type":22,"price_3":300,"data_time":"2019-11-11T16:00:00.000+0000","data_content":"12212","data_code":"20191119095470001800733586"}]},{"price_1":100,"price_2":200,"data_time":"2019-11-13T16:00:00.000+0000","price_3":300,"detail":[{"in_type":0,"in_type_name":"不限","price_1":100,"price_2":200,"out_type":22,"price_3":300,"data_time":"2019-11-13T16:00:00.000+0000","data_content":"12212","data_code":"20191119095480000167457350"}]}]
     * page : {"total":3,"startRow":1,"size":3,"navigateFirstPageNums":1,"prePage":0,"endRow":3,"pageSize":5,"pageNum":1,"navigateLastPageNums":1,"navigatePageNums":[1]}
     */

    private PageBean page;
    private List<DataBean> data;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class PageBean {
        /**
         * total : 3
         * startRow : 1
         * size : 3
         * navigateFirstPageNums : 1
         * prePage : 0
         * endRow : 3
         * pageSize : 5
         * pageNum : 1
         * navigateLastPageNums : 1
         * navigatePageNums : [1]
         */

        private int total;
        private int startRow;
        private int size;
        private int navigateFirstPageNums;
        private int prePage;
        private int endRow;
        private int pageSize;
        private int pageNum;
        private int navigateLastPageNums;
        private List<Integer> navigatePageNums;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getStartRow() {
            return startRow;
        }

        public void setStartRow(int startRow) {
            this.startRow = startRow;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getNavigateFirstPageNums() {
            return navigateFirstPageNums;
        }

        public void setNavigateFirstPageNums(int navigateFirstPageNums) {
            this.navigateFirstPageNums = navigateFirstPageNums;
        }

        public int getPrePage() {
            return prePage;
        }

        public void setPrePage(int prePage) {
            this.prePage = prePage;
        }

        public int getEndRow() {
            return endRow;
        }

        public void setEndRow(int endRow) {
            this.endRow = endRow;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getNavigateLastPageNums() {
            return navigateLastPageNums;
        }

        public void setNavigateLastPageNums(int navigateLastPageNums) {
            this.navigateLastPageNums = navigateLastPageNums;
        }

        public List<Integer> getNavigatePageNums() {
            return navigatePageNums;
        }

        public void setNavigatePageNums(List<Integer> navigatePageNums) {
            this.navigatePageNums = navigatePageNums;
        }
    }

    public static class DataBean {
        /**
         * price_1 : 300.0
         * price_2 : 600.0
         * data_time : 2019-11-10T16:00:00.000+0000
         * price_3 : 900.0
         * detail : [{"in_type":11,"price_1":100,"price_2":200,"out_type":0,"price_3":300,"data_time":"2019-11-10T16:00:00.000+0000","out_type_name":"不限","data_content":"12212","data_code":"20191119095230001740792477"},{"in_type":0,"in_type_name":"不限","price_1":100,"price_2":200,"out_type":22,"price_3":300,"data_time":"2019-11-10T16:00:00.000+0000","data_content":"12212","data_code":"20191119095250001964199885"},{"in_type":0,"in_type_name":"不限","price_1":100,"price_2":200,"out_type":22,"price_3":300,"data_time":"2019-11-10T16:00:00.000+0000","data_content":"12212","data_code":"20191119095280000048126687"}]
         */

        private double price_1;
        private double price_2;
        private String data_time;
        private double price_3;
        private List<DetailBean> detail;

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

        public String getData_time() {
            return data_time;
        }

        public void setData_time(String data_time) {
            this.data_time = data_time;
        }

        public double getPrice_3() {
            return price_3;
        }

        public void setPrice_3(double price_3) {
            this.price_3 = price_3;
        }

        public List<DetailBean> getDetail() {
            return detail;
        }

        public void setDetail(List<DetailBean> detail) {
            this.detail = detail;
        }

        public static class DetailBean {
            /**
             * in_type : 11
             * price_1 : 100.0
             * price_2 : 200.0
             * out_type : 0
             * price_3 : 300.0
             * data_time : 2019-11-10T16:00:00.000+0000
             * out_type_name : 不限
             * data_content : 12212
             * data_code : 20191119095230001740792477
             * in_type_name : 不限
             */

            private int in_type;
            private double price_1;
            private double price_2;
            private int out_type;
            private double price_3;
            private String data_time;
            private String out_type_name;
            private String data_content;
            private String data_code;
            private String in_type_name;
            private int data_type;

            public int getData_type() {
                return data_type;
            }

            public void setData_type(int data_type) {
                this.data_type = data_type;
            }

            public int getIn_type() {
                return in_type;
            }

            public void setIn_type(int in_type) {
                this.in_type = in_type;
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

            public int getOut_type() {
                return out_type;
            }

            public void setOut_type(int out_type) {
                this.out_type = out_type;
            }

            public double getPrice_3() {
                return price_3;
            }

            public void setPrice_3(double price_3) {
                this.price_3 = price_3;
            }

            public String getData_time() {
                return data_time;
            }

            public void setData_time(String data_time) {
                this.data_time = data_time;
            }

            public String getOut_type_name() {
                return out_type_name;
            }

            public void setOut_type_name(String out_type_name) {
                this.out_type_name = out_type_name;
            }

            public String getData_content() {
                return data_content;
            }

            public void setData_content(String data_content) {
                this.data_content = data_content;
            }

            public String getData_code() {
                return data_code;
            }

            public void setData_code(String data_code) {
                this.data_code = data_code;
            }

            public String getIn_type_name() {
                return in_type_name;
            }

            public void setIn_type_name(String in_type_name) {
                this.in_type_name = in_type_name;
            }
        }
    }
}
