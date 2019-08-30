package com.jhhscm.platform.fragment.home.bean;

import java.util.List;

public class GetPageArticleListBean {

    /**
     * data : [{"content_text":"挖掘机保养不得不注意的20个细节","content_url":"http://wajueji.oss-cn-shenzhen.aliyuncs.com/article/27c8e241b77744e996d71dca7cb1b14c.jpg?Expires=1882490504&OSSAccessKeyId=LTAI4F3Gt8M6rbEl&Signature=%2BXiyzEARk8uzfaqatxF6l%2FwkZN0%3D","id":123,"title":"挖掘机保养不得不注意的20个细节","release_time":"2019-08-31 00:00:00"}]
     * page : {"total":1,"startRow":1,"size":1,"navigateFirstPageNums":1,"prePage":0,"endRow":1,"pageSize":5,"pageNum":1,"navigateLastPageNums":1,"navigatePageNums":[1]}
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
         * total : 1
         * startRow : 1
         * size : 1
         * navigateFirstPageNums : 1
         * prePage : 0
         * endRow : 1
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
         * content_text : 挖掘机保养不得不注意的20个细节
         * content_url : http://wajueji.oss-cn-shenzhen.aliyuncs.com/article/27c8e241b77744e996d71dca7cb1b14c.jpg?Expires=1882490504&OSSAccessKeyId=LTAI4F3Gt8M6rbEl&Signature=%2BXiyzEARk8uzfaqatxF6l%2FwkZN0%3D
         * id : 123
         * title : 挖掘机保养不得不注意的20个细节
         * release_time : 2019-08-31 00:00:00
         */

        private String content_text;
        private String content_url;
        private String id;
        private String title;
        private String release_time;

        public String getContent_text() {
            return content_text;
        }

        public void setContent_text(String content_text) {
            this.content_text = content_text;
        }

        public String getContent_url() {
            return content_url;
        }

        public void setContent_url(String content_url) {
            this.content_url = content_url;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getRelease_time() {
            return release_time;
        }

        public void setRelease_time(String release_time) {
            this.release_time = release_time;
        }
    }
}
