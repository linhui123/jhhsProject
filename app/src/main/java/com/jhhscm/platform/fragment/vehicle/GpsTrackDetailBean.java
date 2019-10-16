package com.jhhscm.platform.fragment.vehicle;

import java.util.List;

public class GpsTrackDetailBean {

    /**
     * pagination : {"pageRecords":50,"totalRecords":2078,"previousPage":1,"hasNextPage":true,"nextPage":2,"startRecord":0,"totalPages":42,"hasPreviousPage":false,"currentPage":1}
     * tracks : [{"t4":0,"pt":1,"ft":0,"dt":1,"hx":137,"yl":101,"id":"500000","net":0,"sp":520,"ol":0,"lat":22568745,"s1":805310851,"s2":1280,"s3":0,"s4":0,"mlat":"22.565703","ac":1,"lng":"113921858","mlng":"113.926720","gt":"2015-12-14 18:54:58.0","gw":"G1","fdt":0,"lc":161446267,"pk":0,"t1":-321,"t2":350,"t3":-200}]
     */

    private PaginationBean pagination;
    private List<TracksBean> tracks;

    public PaginationBean getPagination() {
        return pagination;
    }

    public void setPagination(PaginationBean pagination) {
        this.pagination = pagination;
    }

    public List<TracksBean> getTracks() {
        return tracks;
    }

    public void setTracks(List<TracksBean> tracks) {
        this.tracks = tracks;
    }

    public static class PaginationBean {
        /**
         * pageRecords : 50
         * totalRecords : 2078
         * previousPage : 1
         * hasNextPage : true
         * nextPage : 2
         * startRecord : 0
         * totalPages : 42
         * hasPreviousPage : false
         * currentPage : 1
         */

        private int pageRecords;
        private int totalRecords;
        private int previousPage;
        private boolean hasNextPage;
        private int nextPage;
        private int startRecord;
        private int totalPages;
        private boolean hasPreviousPage;
        private int currentPage;

        public int getPageRecords() {
            return pageRecords;
        }

        public void setPageRecords(int pageRecords) {
            this.pageRecords = pageRecords;
        }

        public int getTotalRecords() {
            return totalRecords;
        }

        public void setTotalRecords(int totalRecords) {
            this.totalRecords = totalRecords;
        }

        public int getPreviousPage() {
            return previousPage;
        }

        public void setPreviousPage(int previousPage) {
            this.previousPage = previousPage;
        }

        public boolean isHasNextPage() {
            return hasNextPage;
        }

        public void setHasNextPage(boolean hasNextPage) {
            this.hasNextPage = hasNextPage;
        }

        public int getNextPage() {
            return nextPage;
        }

        public void setNextPage(int nextPage) {
            this.nextPage = nextPage;
        }

        public int getStartRecord() {
            return startRecord;
        }

        public void setStartRecord(int startRecord) {
            this.startRecord = startRecord;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public boolean isHasPreviousPage() {
            return hasPreviousPage;
        }

        public void setHasPreviousPage(boolean hasPreviousPage) {
            this.hasPreviousPage = hasPreviousPage;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }
    }

    public static class TracksBean {
        /**
         * t4 : 0
         * pt : 1
         * ft : 0
         * dt : 1
         * hx : 137
         * yl : 101
         * id : 500000
         * net : 0
         * sp : 520
         * ol : 0
         * lat : 22568745
         * s1 : 805310851
         * s2 : 1280
         * s3 : 0
         * s4 : 0
         * mlat : 22.565703
         * ac : 1
         * lng : 113921858
         * mlng : 113.926720
         * gt : 2015-12-14 18:54:58.0
         * gw : G1
         * fdt : 0
         * lc : 161446267
         * pk : 0
         * t1 : -321
         * t2 : 350
         * t3 : -200
         */

        private int t4;
        private int pt;
        private int ft;
        private int dt;
        private int hx;
        private int yl;
        private String id;
        private int net;
        private int sp;
        private int ol;
        private int lat;
        private int s1;
        private int s2;
        private int s3;
        private int s4;
        private String mlat;
        private int ac;
        private String lng;
        private String mlng;
        private String gt;
        private String gw;
        private int fdt;
        private int lc;
        private int pk;
        private int t1;
        private int t2;
        private int t3;

        public int getT4() {
            return t4;
        }

        public void setT4(int t4) {
            this.t4 = t4;
        }

        public int getPt() {
            return pt;
        }

        public void setPt(int pt) {
            this.pt = pt;
        }

        public int getFt() {
            return ft;
        }

        public void setFt(int ft) {
            this.ft = ft;
        }

        public int getDt() {
            return dt;
        }

        public void setDt(int dt) {
            this.dt = dt;
        }

        public int getHx() {
            return hx;
        }

        public void setHx(int hx) {
            this.hx = hx;
        }

        public int getYl() {
            return yl;
        }

        public void setYl(int yl) {
            this.yl = yl;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getNet() {
            return net;
        }

        public void setNet(int net) {
            this.net = net;
        }

        public int getSp() {
            return sp;
        }

        public void setSp(int sp) {
            this.sp = sp;
        }

        public int getOl() {
            return ol;
        }

        public void setOl(int ol) {
            this.ol = ol;
        }

        public int getLat() {
            return lat;
        }

        public void setLat(int lat) {
            this.lat = lat;
        }

        public int getS1() {
            return s1;
        }

        public void setS1(int s1) {
            this.s1 = s1;
        }

        public int getS2() {
            return s2;
        }

        public void setS2(int s2) {
            this.s2 = s2;
        }

        public int getS3() {
            return s3;
        }

        public void setS3(int s3) {
            this.s3 = s3;
        }

        public int getS4() {
            return s4;
        }

        public void setS4(int s4) {
            this.s4 = s4;
        }

        public String getMlat() {
            return mlat;
        }

        public void setMlat(String mlat) {
            this.mlat = mlat;
        }

        public int getAc() {
            return ac;
        }

        public void setAc(int ac) {
            this.ac = ac;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getMlng() {
            return mlng;
        }

        public void setMlng(String mlng) {
            this.mlng = mlng;
        }

        public String getGt() {
            return gt;
        }

        public void setGt(String gt) {
            this.gt = gt;
        }

        public String getGw() {
            return gw;
        }

        public void setGw(String gw) {
            this.gw = gw;
        }

        public int getFdt() {
            return fdt;
        }

        public void setFdt(int fdt) {
            this.fdt = fdt;
        }

        public int getLc() {
            return lc;
        }

        public void setLc(int lc) {
            this.lc = lc;
        }

        public int getPk() {
            return pk;
        }

        public void setPk(int pk) {
            this.pk = pk;
        }

        public int getT1() {
            return t1;
        }

        public void setT1(int t1) {
            this.t1 = t1;
        }

        public int getT2() {
            return t2;
        }

        public void setT2(int t2) {
            this.t2 = t2;
        }

        public int getT3() {
            return t3;
        }

        public void setT3(int t3) {
            this.t3 = t3;
        }
    }
}
