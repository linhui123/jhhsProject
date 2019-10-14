package com.jhhscm.platform.fragment.repayment;

import java.util.List;

public class ContractListBean {


    /**
     * data : [{"offlineCode":"线下合同号4949415641","code":"20190916154070001098166629","modeRepay":"0","loanMoney":600000,"leaseName":"测试2","schemeName":"合同模板名称","leaseCode":"20190916154460000870162292","startTime":"2019-09-27","id":94,"state":2,"endTime":"2019-11-27","machinePrice":12.5},{"code":"20190916154410000287176657","leaseName":"测试2","companyName":"授修改信","schemeName":"合同模板名称","offlineCode":"线下合同号修改4949415641","modeRepay":"1","loanMoney":6.6,"leaseCode":"20190916154460000870162292","startTime":"2019-09-29","id":95,"state":2,"endTime":"2021-03-29","machinePrice":12.5}]
     * page : {"total":2,"list":[{"offlineCode":"线下合同号4949415641","code":"20190916154070001098166629","modeRepay":"0","loanMoney":600000,"leaseName":"测试2","schemeName":"合同模板名称","leaseCode":"20190916154460000870162292","startTime":"2019-09-27","id":94,"state":2,"endTime":"2019-11-27","machinePrice":12.5,"companyName":"授修改信"},{"code":"20190916154410000287176657","leaseName":"测试2","companyName":"授修改信","schemeName":"合同模板名称","offlineCode":"线下合同号修改4949415641","modeRepay":"1","loanMoney":6.6,"leaseCode":"20190916154460000870162292","startTime":"2019-09-29","id":95,"state":2,"endTime":"2021-03-29","machinePrice":12.5}],"pageNum":1,"pageSize":100,"size":2,"startRow":1,"endRow":2,"pages":1,"prePage":0,"nextPage":0,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":8,"navigatepageNums":[1],"navigateFirstPage":1,"navigateLastPage":1,"firstPage":1,"lastPage":1}
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
         * total : 2
         * list : [{"offlineCode":"线下合同号4949415641","code":"20190916154070001098166629","modeRepay":"0","loanMoney":600000,"leaseName":"测试2","schemeName":"合同模板名称","leaseCode":"20190916154460000870162292","startTime":"2019-09-27","id":94,"state":2,"endTime":"2019-11-27","machinePrice":12.5},{"code":"20190916154410000287176657","leaseName":"测试2","companyName":"授修改信","schemeName":"合同模板名称","offlineCode":"线下合同号修改4949415641","modeRepay":"1","loanMoney":6.6,"leaseCode":"20190916154460000870162292","startTime":"2019-09-29","id":95,"state":2,"endTime":"2021-03-29","machinePrice":12.5}]
         * pageNum : 1
         * pageSize : 100
         * size : 2
         * startRow : 1
         * endRow : 2
         * pages : 1
         * prePage : 0
         * nextPage : 0
         * isFirstPage : true
         * isLastPage : true
         * hasPreviousPage : false
         * hasNextPage : false
         * navigatePages : 8
         * navigatepageNums : [1]
         * navigateFirstPage : 1
         * navigateLastPage : 1
         * firstPage : 1
         * lastPage : 1
         */

        private int total;
        private int pageNum;
        private int pageSize;
        private int size;
        private int startRow;
        private int endRow;
        private int pages;
        private int prePage;
        private int nextPage;
        private boolean isFirstPage;
        private boolean isLastPage;
        private boolean hasPreviousPage;
        private boolean hasNextPage;
        private int navigatePages;
        private int navigateFirstPage;
        private int navigateLastPage;
        private int firstPage;
        private int lastPage;
        private List<ListBean> list;
        private List<Integer> navigatepageNums;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getStartRow() {
            return startRow;
        }

        public void setStartRow(int startRow) {
            this.startRow = startRow;
        }

        public int getEndRow() {
            return endRow;
        }

        public void setEndRow(int endRow) {
            this.endRow = endRow;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public int getPrePage() {
            return prePage;
        }

        public void setPrePage(int prePage) {
            this.prePage = prePage;
        }

        public int getNextPage() {
            return nextPage;
        }

        public void setNextPage(int nextPage) {
            this.nextPage = nextPage;
        }

        public boolean isIsFirstPage() {
            return isFirstPage;
        }

        public void setIsFirstPage(boolean isFirstPage) {
            this.isFirstPage = isFirstPage;
        }

        public boolean isIsLastPage() {
            return isLastPage;
        }

        public void setIsLastPage(boolean isLastPage) {
            this.isLastPage = isLastPage;
        }

        public boolean isHasPreviousPage() {
            return hasPreviousPage;
        }

        public void setHasPreviousPage(boolean hasPreviousPage) {
            this.hasPreviousPage = hasPreviousPage;
        }

        public boolean isHasNextPage() {
            return hasNextPage;
        }

        public void setHasNextPage(boolean hasNextPage) {
            this.hasNextPage = hasNextPage;
        }

        public int getNavigatePages() {
            return navigatePages;
        }

        public void setNavigatePages(int navigatePages) {
            this.navigatePages = navigatePages;
        }

        public int getNavigateFirstPage() {
            return navigateFirstPage;
        }

        public void setNavigateFirstPage(int navigateFirstPage) {
            this.navigateFirstPage = navigateFirstPage;
        }

        public int getNavigateLastPage() {
            return navigateLastPage;
        }

        public void setNavigateLastPage(int navigateLastPage) {
            this.navigateLastPage = navigateLastPage;
        }

        public int getFirstPage() {
            return firstPage;
        }

        public void setFirstPage(int firstPage) {
            this.firstPage = firstPage;
        }

        public int getLastPage() {
            return lastPage;
        }

        public void setLastPage(int lastPage) {
            this.lastPage = lastPage;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public List<Integer> getNavigatepageNums() {
            return navigatepageNums;
        }

        public void setNavigatepageNums(List<Integer> navigatepageNums) {
            this.navigatepageNums = navigatepageNums;
        }

        public static class ListBean {
            /**
             * offlineCode : 线下合同号4949415641
             * code : 20190916154070001098166629
             * modeRepay : 0
             * loanMoney : 600000.0
             * leaseName : 测试2
             * schemeName : 合同模板名称
             * leaseCode : 20190916154460000870162292
             * startTime : 2019-09-27
             * id : 94
             * state : 2
             * endTime : 2019-11-27
             * machinePrice : 12.5
             * companyName : 授修改信
             */

            private String offlineCode;
            private String code;
            private String modeRepay;
            private double loanMoney;
            private String leaseName;
            private String schemeName;
            private String leaseCode;
            private String startTime;
            private int id;
            private int state;
            private String endTime;
            private double machinePrice;
            private String companyName;

            public String getOfflineCode() {
                return offlineCode;
            }

            public void setOfflineCode(String offlineCode) {
                this.offlineCode = offlineCode;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getModeRepay() {
                return modeRepay;
            }

            public void setModeRepay(String modeRepay) {
                this.modeRepay = modeRepay;
            }

            public double getLoanMoney() {
                return loanMoney;
            }

            public void setLoanMoney(double loanMoney) {
                this.loanMoney = loanMoney;
            }

            public String getLeaseName() {
                return leaseName;
            }

            public void setLeaseName(String leaseName) {
                this.leaseName = leaseName;
            }

            public String getSchemeName() {
                return schemeName;
            }

            public void setSchemeName(String schemeName) {
                this.schemeName = schemeName;
            }

            public String getLeaseCode() {
                return leaseCode;
            }

            public void setLeaseCode(String leaseCode) {
                this.leaseCode = leaseCode;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public double getMachinePrice() {
                return machinePrice;
            }

            public void setMachinePrice(double machinePrice) {
                this.machinePrice = machinePrice;
            }

            public String getCompanyName() {
                return companyName;
            }

            public void setCompanyName(String companyName) {
                this.companyName = companyName;
            }
        }
    }

    public static class DataBean {
        /**
         * offlineCode : 线下合同号4949415641
         * code : 20190916154070001098166629
         * modeRepay : 0
         * loanMoney : 600000.0
         * leaseName : 测试2
         * schemeName : 合同模板名称
         * leaseCode : 20190916154460000870162292
         * startTime : 2019-09-27
         * id : 94
         * state : 2
         * endTime : 2019-11-27
         * machinePrice : 12.5
         * companyName : 授修改信
         */

        private String offlineCode;
        private String code;
        private String modeRepay;
        private double loanMoney;
        private String leaseName;
        private String schemeName;
        private String leaseCode;
        private String startTime;
        private String id;
        private int state;
        private String endTime;
        private double machinePrice;
        private String companyName;

        public String getOfflineCode() {
            return offlineCode;
        }

        public void setOfflineCode(String offlineCode) {
            this.offlineCode = offlineCode;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getModeRepay() {
            return modeRepay;
        }

        public void setModeRepay(String modeRepay) {
            this.modeRepay = modeRepay;
        }

        public double getLoanMoney() {
            return loanMoney;
        }

        public void setLoanMoney(double loanMoney) {
            this.loanMoney = loanMoney;
        }

        public String getLeaseName() {
            return leaseName;
        }

        public void setLeaseName(String leaseName) {
            this.leaseName = leaseName;
        }

        public String getSchemeName() {
            return schemeName;
        }

        public void setSchemeName(String schemeName) {
            this.schemeName = schemeName;
        }

        public String getLeaseCode() {
            return leaseCode;
        }

        public void setLeaseCode(String leaseCode) {
            this.leaseCode = leaseCode;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public double getMachinePrice() {
            return machinePrice;
        }

        public void setMachinePrice(double machinePrice) {
            this.machinePrice = machinePrice;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }
    }
}
