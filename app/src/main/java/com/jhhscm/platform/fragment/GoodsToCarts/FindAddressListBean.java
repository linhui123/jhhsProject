package com.jhhscm.platform.fragment.GoodsToCarts;

import com.jhhscm.platform.fragment.my.labour.FindLabourListBean;

import java.io.Serializable;
import java.util.List;

public class FindAddressListBean implements Serializable{


    /**
     * result : {"data":[{"address_detail":"","user_code":"4000000034777111","province":"北京市","city":"市辖区","name":"小崔","county":"通州区","tel":"15927112992","id":1,"is_default":0},{"address_detail":"详细地址","user_code":"4000000034777111","province":"北京市","city":"市辖区","name":"小崔","county":"通州区","tel":"15927112992","id":4,"is_default":0},{"address_detail":"详细地址","user_code":"4000000034777111","province":"北京市","city":"市辖区","name":"小崔","county":"通州区","tel":"15927112992","id":5,"is_default":0},{"address_detail":"详细地址","user_code":"4000000034777111","province":"北京市","city":"市辖区","name":"小崔","county":"通州区","tel":"15927112992","id":6,"is_default":0},{"address_detail":"修改详细地址","user_code":"4000000034777111","province":"北京市","city":"市辖区","name":"修改小崔","county":"通州区","tel":"15927112992","id":7,"is_default":0},{"address_detail":"详细地址","user_code":"4000000034777111","province":"北京市","city":"市辖区","name":"小崔","county":"通州区","tel":"15927112992","id":11,"is_default":0},{"address_detail":"详细地址","user_code":"4000000034777111","province":"北京市","city":"市辖区","name":"小崔","county":"通州区","tel":"15927112992","id":12,"is_default":1}],"page":{"total":7,"startRow":1,"size":7,"navigateFirstPageNums":1,"prePage":0,"endRow":7,"pageSize":10,"pageNum":1,"navigateLastPageNums":1,"navigatePageNums":[1]}}
     */

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable{
        /**
         * data : [{"address_detail":"","user_code":"4000000034777111","province":"北京市","city":"市辖区","name":"小崔","county":"通州区","tel":"15927112992","id":1,"is_default":0},{"address_detail":"详细地址","user_code":"4000000034777111","province":"北京市","city":"市辖区","name":"小崔","county":"通州区","tel":"15927112992","id":4,"is_default":0},{"address_detail":"详细地址","user_code":"4000000034777111","province":"北京市","city":"市辖区","name":"小崔","county":"通州区","tel":"15927112992","id":5,"is_default":0},{"address_detail":"详细地址","user_code":"4000000034777111","province":"北京市","city":"市辖区","name":"小崔","county":"通州区","tel":"15927112992","id":6,"is_default":0},{"address_detail":"修改详细地址","user_code":"4000000034777111","province":"北京市","city":"市辖区","name":"修改小崔","county":"通州区","tel":"15927112992","id":7,"is_default":0},{"address_detail":"详细地址","user_code":"4000000034777111","province":"北京市","city":"市辖区","name":"小崔","county":"通州区","tel":"15927112992","id":11,"is_default":0},{"address_detail":"详细地址","user_code":"4000000034777111","province":"北京市","city":"市辖区","name":"小崔","county":"通州区","tel":"15927112992","id":12,"is_default":1}]
         * page : {"total":7,"startRow":1,"size":7,"navigateFirstPageNums":1,"prePage":0,"endRow":7,"pageSize":10,"pageNum":1,"navigateLastPageNums":1,"navigatePageNums":[1]}
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
             * total : 7
             * startRow : 1
             * size : 7
             * navigateFirstPageNums : 1
             * prePage : 0
             * endRow : 7
             * pageSize : 10
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

        public static class DataBean implements Serializable {

            /**
             * address_detail : 详细地址
             * county_id : 384
             * user_code : 4000000034777111
             * province : 北京市
             * province_id : 1
             * city : 市辖区
             * name : 小崔
             * county : 通州区
             * tel : 15927112992
             * id : 4
             * is_default : 0
             * city_id : 32
             */

            private String address_detail;
            private String county_id;
            private String user_code;
            private String province;
            private String province_id;
            private String city;
            private String name;
            private String county;
            private String tel;
            private String id;
            private String is_default;
            private String city_id;

            public String getAddress_detail() {
                return address_detail;
            }

            public void setAddress_detail(String address_detail) {
                this.address_detail = address_detail;
            }

            public String getCounty_id() {
                return county_id;
            }

            public void setCounty_id(String county_id) {
                this.county_id = county_id;
            }

            public String getUser_code() {
                return user_code;
            }

            public void setUser_code(String user_code) {
                this.user_code = user_code;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getProvince_id() {
                return province_id;
            }

            public void setProvince_id(String province_id) {
                this.province_id = province_id;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCounty() {
                return county;
            }

            public void setCounty(String county) {
                this.county = county;
            }

            public String getTel() {
                return tel;
            }

            public void setTel(String tel) {
                this.tel = tel;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getIs_default() {
                return is_default;
            }

            public void setIs_default(String is_default) {
                this.is_default = is_default;
            }

            public String getCity_id() {
                return city_id;
            }

            public void setCity_id(String city_id) {
                this.city_id = city_id;
            }
        }
    }
}
