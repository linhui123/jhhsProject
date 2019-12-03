package com.jhhscm.platform.fragment.vehicle;

import java.util.List;

public class GpsTrackDetailBean {

    /**
     * pagination : {"pageRecords":50,"totalRecords":2078,"previousPage":1,"hasNextPage":true,"nextPage":2,"startRecord":0,"totalPages":42,"hasPreviousPage":false,"currentPage":1}
     * tracks : [{"t4":0,"pt":1,"ft":0,"dt":1,"hx":137,"yl":101,"id":"500000","net":0,"sp":520,"ol":0,"lat":22568745,"s1":805310851,"s2":1280,"s3":0,"s4":0,"mlat":"22.565703","ac":1,"lng":"113921858","mlng":"113.926720","gt":"2015-12-14 18:54:58.0","gw":"G1","fdt":0,"lc":161446267,"pk":0,"t1":-321,"t2":350,"t3":-200}]
     */

    private PaginationBean pagination;
    private List<TracksBean> tracks;
    /**
     * gpsgjList : {"res":"true","result":[{"Time":"2019-11-05 07:02:23","Longitude":"117.56195","Latitude":"26.29507","ysLongitude":"117.55045","ysLatitude":"26.29273","Angle":"84","DwellTime":"24704","Velocity":"1","Miles":"2801.831"},{"Time":"2019-11-05 07:03:09","Longitude":"117.56196","Latitude":"26.29507","ysLongitude":"117.55047","ysLatitude":"26.29273","Angle":"58","DwellTime":"43","Velocity":"1","Miles":"2801.837"},{"Time":"2019-11-05 07:03:43","Longitude":"117.56196","Latitude":"26.29507","ysLongitude":"117.55047","ysLatitude":"26.29273","Angle":"24","DwellTime":"31","Velocity":"1","Miles":"2801.843"},{"Time":"2019-11-05 07:04:47","Longitude":"117.56196","Latitude":"26.29511","ysLongitude":"117.55047","ysLatitude":"26.29277","Angle":"345","DwellTime":"34","Velocity":"1","Miles":"2801.853"},{"Time":"2019-11-05 07:05:05","Longitude":"117.56196","Latitude":"26.29512","ysLongitude":"117.55047","ysLatitude":"26.29278","Angle":"177","DwellTime":"12","Velocity":"1","Miles":"2801.857"},{"Time":"2019-11-05 07:11:02","Longitude":"117.56195","Latitude":"26.29511","ysLongitude":"117.55045","ysLatitude":"26.29277","Angle":"241","DwellTime":"354","Velocity":"1","Miles":"2801.906"},{"Time":"2019-11-05 07:19:13","Longitude":"117.56196","Latitude":"26.29511","ysLongitude":"117.55047","ysLatitude":"26.29277","Angle":"315","DwellTime":"458","Velocity":"2","Miles":"2801.943"},{"Time":"2019-11-05 07:19:44","Longitude":"117.56196","Latitude":"26.29511","ysLongitude":"117.55047","ysLatitude":"26.29277","Angle":"0","DwellTime":"0","Velocity":"1","Miles":"2801.947"},{"Time":"2019-11-05 07:20:23","Longitude":"117.56195","Latitude":"26.29512","ysLongitude":"117.55045","ysLatitude":"26.29278","Angle":"358","DwellTime":"9","Velocity":"2","Miles":"2801.953"},{"Time":"2019-11-05 07:23:17","Longitude":"117.56195","Latitude":"26.29512","ysLongitude":"117.55045","ysLatitude":"26.29278","Angle":"182","DwellTime":"55","Velocity":"2","Miles":"2801.988"},{"Time":"2019-11-05 07:24:34","Longitude":"117.56209","Latitude":"26.29471","ysLongitude":"117.55060","ysLatitude":"26.29237","Angle":"152","DwellTime":"0","Velocity":"2","Miles":"2802.035"},{"Time":"2019-11-05 07:25:04","Longitude":"117.56219","Latitude":"26.29454","ysLongitude":"117.55070","ysLatitude":"26.29220","Angle":"167","DwellTime":"0","Velocity":"2","Miles":"2802.058"},{"Time":"2019-11-05 07:25:35","Longitude":"117.56216","Latitude":"26.29432","ysLongitude":"117.55067","ysLatitude":"26.29198","Angle":"196","DwellTime":"0","Velocity":"2","Miles":"2802.082"},{"Time":"2019-11-05 07:25:41","Longitude":"117.56215","Latitude":"26.29429","ysLongitude":"117.55065","ysLatitude":"26.29195","Angle":"200","DwellTime":"0","Velocity":"2","Miles":"2802.086"},{"Time":"2019-11-05 07:25:47","Longitude":"117.56212","Latitude":"26.29426","ysLongitude":"117.55063","ysLatitude":"26.29192","Angle":"197","DwellTime":"0","Velocity":"2","Miles":"2802.090"},{"Time":"2019-11-05 07:25:50","Longitude":"117.56212","Latitude":"26.29424","ysLongitude":"117.55063","ysLatitude":"26.29190","Angle":"200","DwellTime":"0","Velocity":"2","Miles":"2802.093"},{"Time":"2019-11-05 07:26:21","Longitude":"117.56205","Latitude":"26.29406","ysLongitude":"117.55055","ysLatitude":"26.29172","Angle":"203","DwellTime":"0","Velocity":"2","Miles":"2802.116"},{"Time":"2019-11-05 07:26:30","Longitude":"117.56202","Latitude":"26.29401","ysLongitude":"117.55052","ysLatitude":"26.29167","Angle":"199","DwellTime":"0","Velocity":"2","Miles":"2802.122"},{"Time":"2019-11-05 07:26:45","Longitude":"117.56198","Latitude":"26.29391","ysLongitude":"117.55048","ysLatitude":"26.29157","Angle":"213","DwellTime":"0","Velocity":"2","Miles":"2802.133"},{"Time":"2019-11-05 07:27:16","Longitude":"117.56183","Latitude":"26.29374","ysLongitude":"117.55033","ysLatitude":"26.29140","Angle":"223","DwellTime":"0","Velocity":"2","Miles":"2802.156"},{"Time":"2019-11-05 07:27:46","Longitude":"117.56164","Latitude":"26.29361","ysLongitude":"117.55015","ysLatitude":"26.29127","Angle":"0","DwellTime":"0","Velocity":"2","Miles":"2802.178"},{"Time":"2019-11-05 07:28:01","Longitude":"117.56167","Latitude":"26.29357","ysLongitude":"117.55017","ysLatitude":"26.29123","Angle":"138","DwellTime":"0","Velocity":"2","Miles":"2802.185"},{"Time":"2019-11-05 07:33:56","Longitude":"117.56187","Latitude":"26.29351","ysLongitude":"117.55038","ysLatitude":"26.29117","Angle":"349","DwellTime":"349","Velocity":"1","Miles":"2802.277"},{"Time":"2019-11-05 07:39:38","Longitude":"117.56187","Latitude":"26.29351","ysLongitude":"117.55038","ysLatitude":"26.29117","Angle":"256","DwellTime":"300","Velocity":"2","Miles":"2802.336"},{"Time":"2019-11-05 07:39:47","Longitude":"117.56187","Latitude":"26.29351","ysLongitude":"117.55038","ysLatitude":"26.29117","Angle":"294","DwellTime":"6","Velocity":"1","Miles":"2802.338"},{"Time":"2019-11-05 07:40:39","Longitude":"117.56187","Latitude":"26.29351","ysLongitude":"117.55038","ysLatitude":"26.29117","Angle":"235","DwellTime":"10","Velocity":"1","Miles":"2802.351"},{"Time":"2019-11-05 07:47:26","Longitude":"117.56190","Latitude":"26.29347","ysLongitude":"117.55040","ysLatitude":"26.29113","Angle":"0","DwellTime":"404","Velocity":"1","Miles":"2802.416"},{"Time":"2019-11-05 07:48:13","Longitude":"117.56190","Latitude":"26.29351","ysLongitude":"117.55040","ysLatitude":"26.29117","Angle":"8","DwellTime":"0","Velocity":"1","Miles":"2802.429"},{"Time":"2019-11-05 07:51:56","Longitude":"117.56190","Latitude":"26.29349","ysLongitude":"117.55040","ysLatitude":"26.29115","Angle":"32","DwellTime":"162","Velocity":"1","Miles":"2802.451"},{"Time":"2019-11-05 07:52:08","Longitude":"117.56190","Latitude":"26.29349","ysLongitude":"117.55040","ysLatitude":"26.29115","Angle":"96","DwellTime":"0","Velocity":"1","Miles":"2802.453"},{"Time":"2019-11-05 07:55:11","Longitude":"117.56192","Latitude":"26.29351","ysLongitude":"117.55042","ysLatitude":"26.29117","Angle":"40","DwellTime":"180","Velocity":"1","Miles":"2802.478"},{"Time":"2019-11-05 07:55:48","Longitude":"117.56192","Latitude":"26.29352","ysLongitude":"117.55042","ysLatitude":"26.29118","Angle":"55","DwellTime":"34","Velocity":"1","Miles":"2802.483"},{"Time":"2019-11-05 07:55:57","Longitude":"117.56193","Latitude":"26.29349","ysLongitude":"117.55043","ysLatitude":"26.29115","Angle":"0","DwellTime":"0","Velocity":"1","Miles":"2802.487"},{"Time":"2019-11-05 07:56:12","Longitude":"117.56192","Latitude":"26.29344","ysLongitude":"117.55042","ysLatitude":"26.29110","Angle":"90","DwellTime":"0","Velocity":"2","Miles":"2802.492"},{"Time":"2019-11-05 08:03:58","Longitude":"117.56183","Latitude":"26.29346","ysLongitude":"117.55033","ysLatitude":"26.29112","Angle":"0","DwellTime":"463","Velocity":"1","Miles":"2802.552"},{"Time":"2019-11-05 08:04:37","Longitude":"117.56192","Latitude":"26.29354","ysLongitude":"117.55042","ysLatitude":"26.29120","Angle":"42","DwellTime":"8","Velocity":"3","Miles":"2802.565"},{"Time":"2019-11-05 08:19:12","Longitude":"117.56190","Latitude":"26.29349","ysLongitude":"117.55040","ysLatitude":"26.29115","Angle":"158","DwellTime":"758","Velocity":"2","Miles":"2802.704"},{"Time":"2019-11-05 08:21:33","Longitude":"117.56190","Latitude":"26.29349","ysLongitude":"117.55040","ysLatitude":"26.29115","Angle":"244","DwellTime":"26","Velocity":"1","Miles":"2802.722"},{"Time":"2019-11-05 08:22:49","Longitude":"117.56190","Latitude":"26.29347","ysLongitude":"117.55040","ysLatitude":"26.29113","Angle":"194","DwellTime":"43","Velocity":"1","Miles":"2802.731"},{"Time":"2019-11-05 08:24:24","Longitude":"117.56190","Latitude":"26.29349","ysLongitude":"117.55040","ysLatitude":"26.29115","Angle":"9","DwellTime":"61","Velocity":"1","Miles":"2802.739"},{"Time":"2019-11-05 08:30:01","Longitude":"117.56190","Latitude":"26.29347","ysLongitude":"117.55040","ysLatitude":"26.29113","Angle":"306","DwellTime":"307","Velocity":"1","Miles":"2802.786"}]}
     */

    private String gpsgjList;

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

    public String getGpsgjList() {
        return gpsgjList;
    }

    public void setGpsgjList(String gpsgjList) {
        this.gpsgjList = gpsgjList;
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


    public static class GpsgjListBean {
        /**
         * res : true
         * result : [{"Time":"2019-11-05 07:02:23","Longitude":"117.56195","Latitude":"26.29507","ysLongitude":"117.55045","ysLatitude":"26.29273","Angle":"84","DwellTime":"24704","Velocity":"1","Miles":"2801.831"},{"Time":"2019-11-05 07:03:09","Longitude":"117.56196","Latitude":"26.29507","ysLongitude":"117.55047","ysLatitude":"26.29273","Angle":"58","DwellTime":"43","Velocity":"1","Miles":"2801.837"},{"Time":"2019-11-05 07:03:43","Longitude":"117.56196","Latitude":"26.29507","ysLongitude":"117.55047","ysLatitude":"26.29273","Angle":"24","DwellTime":"31","Velocity":"1","Miles":"2801.843"},{"Time":"2019-11-05 07:04:47","Longitude":"117.56196","Latitude":"26.29511","ysLongitude":"117.55047","ysLatitude":"26.29277","Angle":"345","DwellTime":"34","Velocity":"1","Miles":"2801.853"},{"Time":"2019-11-05 07:05:05","Longitude":"117.56196","Latitude":"26.29512","ysLongitude":"117.55047","ysLatitude":"26.29278","Angle":"177","DwellTime":"12","Velocity":"1","Miles":"2801.857"},{"Time":"2019-11-05 07:11:02","Longitude":"117.56195","Latitude":"26.29511","ysLongitude":"117.55045","ysLatitude":"26.29277","Angle":"241","DwellTime":"354","Velocity":"1","Miles":"2801.906"},{"Time":"2019-11-05 07:19:13","Longitude":"117.56196","Latitude":"26.29511","ysLongitude":"117.55047","ysLatitude":"26.29277","Angle":"315","DwellTime":"458","Velocity":"2","Miles":"2801.943"},{"Time":"2019-11-05 07:19:44","Longitude":"117.56196","Latitude":"26.29511","ysLongitude":"117.55047","ysLatitude":"26.29277","Angle":"0","DwellTime":"0","Velocity":"1","Miles":"2801.947"},{"Time":"2019-11-05 07:20:23","Longitude":"117.56195","Latitude":"26.29512","ysLongitude":"117.55045","ysLatitude":"26.29278","Angle":"358","DwellTime":"9","Velocity":"2","Miles":"2801.953"},{"Time":"2019-11-05 07:23:17","Longitude":"117.56195","Latitude":"26.29512","ysLongitude":"117.55045","ysLatitude":"26.29278","Angle":"182","DwellTime":"55","Velocity":"2","Miles":"2801.988"},{"Time":"2019-11-05 07:24:34","Longitude":"117.56209","Latitude":"26.29471","ysLongitude":"117.55060","ysLatitude":"26.29237","Angle":"152","DwellTime":"0","Velocity":"2","Miles":"2802.035"},{"Time":"2019-11-05 07:25:04","Longitude":"117.56219","Latitude":"26.29454","ysLongitude":"117.55070","ysLatitude":"26.29220","Angle":"167","DwellTime":"0","Velocity":"2","Miles":"2802.058"},{"Time":"2019-11-05 07:25:35","Longitude":"117.56216","Latitude":"26.29432","ysLongitude":"117.55067","ysLatitude":"26.29198","Angle":"196","DwellTime":"0","Velocity":"2","Miles":"2802.082"},{"Time":"2019-11-05 07:25:41","Longitude":"117.56215","Latitude":"26.29429","ysLongitude":"117.55065","ysLatitude":"26.29195","Angle":"200","DwellTime":"0","Velocity":"2","Miles":"2802.086"},{"Time":"2019-11-05 07:25:47","Longitude":"117.56212","Latitude":"26.29426","ysLongitude":"117.55063","ysLatitude":"26.29192","Angle":"197","DwellTime":"0","Velocity":"2","Miles":"2802.090"},{"Time":"2019-11-05 07:25:50","Longitude":"117.56212","Latitude":"26.29424","ysLongitude":"117.55063","ysLatitude":"26.29190","Angle":"200","DwellTime":"0","Velocity":"2","Miles":"2802.093"},{"Time":"2019-11-05 07:26:21","Longitude":"117.56205","Latitude":"26.29406","ysLongitude":"117.55055","ysLatitude":"26.29172","Angle":"203","DwellTime":"0","Velocity":"2","Miles":"2802.116"},{"Time":"2019-11-05 07:26:30","Longitude":"117.56202","Latitude":"26.29401","ysLongitude":"117.55052","ysLatitude":"26.29167","Angle":"199","DwellTime":"0","Velocity":"2","Miles":"2802.122"},{"Time":"2019-11-05 07:26:45","Longitude":"117.56198","Latitude":"26.29391","ysLongitude":"117.55048","ysLatitude":"26.29157","Angle":"213","DwellTime":"0","Velocity":"2","Miles":"2802.133"},{"Time":"2019-11-05 07:27:16","Longitude":"117.56183","Latitude":"26.29374","ysLongitude":"117.55033","ysLatitude":"26.29140","Angle":"223","DwellTime":"0","Velocity":"2","Miles":"2802.156"},{"Time":"2019-11-05 07:27:46","Longitude":"117.56164","Latitude":"26.29361","ysLongitude":"117.55015","ysLatitude":"26.29127","Angle":"0","DwellTime":"0","Velocity":"2","Miles":"2802.178"},{"Time":"2019-11-05 07:28:01","Longitude":"117.56167","Latitude":"26.29357","ysLongitude":"117.55017","ysLatitude":"26.29123","Angle":"138","DwellTime":"0","Velocity":"2","Miles":"2802.185"},{"Time":"2019-11-05 07:33:56","Longitude":"117.56187","Latitude":"26.29351","ysLongitude":"117.55038","ysLatitude":"26.29117","Angle":"349","DwellTime":"349","Velocity":"1","Miles":"2802.277"},{"Time":"2019-11-05 07:39:38","Longitude":"117.56187","Latitude":"26.29351","ysLongitude":"117.55038","ysLatitude":"26.29117","Angle":"256","DwellTime":"300","Velocity":"2","Miles":"2802.336"},{"Time":"2019-11-05 07:39:47","Longitude":"117.56187","Latitude":"26.29351","ysLongitude":"117.55038","ysLatitude":"26.29117","Angle":"294","DwellTime":"6","Velocity":"1","Miles":"2802.338"},{"Time":"2019-11-05 07:40:39","Longitude":"117.56187","Latitude":"26.29351","ysLongitude":"117.55038","ysLatitude":"26.29117","Angle":"235","DwellTime":"10","Velocity":"1","Miles":"2802.351"},{"Time":"2019-11-05 07:47:26","Longitude":"117.56190","Latitude":"26.29347","ysLongitude":"117.55040","ysLatitude":"26.29113","Angle":"0","DwellTime":"404","Velocity":"1","Miles":"2802.416"},{"Time":"2019-11-05 07:48:13","Longitude":"117.56190","Latitude":"26.29351","ysLongitude":"117.55040","ysLatitude":"26.29117","Angle":"8","DwellTime":"0","Velocity":"1","Miles":"2802.429"},{"Time":"2019-11-05 07:51:56","Longitude":"117.56190","Latitude":"26.29349","ysLongitude":"117.55040","ysLatitude":"26.29115","Angle":"32","DwellTime":"162","Velocity":"1","Miles":"2802.451"},{"Time":"2019-11-05 07:52:08","Longitude":"117.56190","Latitude":"26.29349","ysLongitude":"117.55040","ysLatitude":"26.29115","Angle":"96","DwellTime":"0","Velocity":"1","Miles":"2802.453"},{"Time":"2019-11-05 07:55:11","Longitude":"117.56192","Latitude":"26.29351","ysLongitude":"117.55042","ysLatitude":"26.29117","Angle":"40","DwellTime":"180","Velocity":"1","Miles":"2802.478"},{"Time":"2019-11-05 07:55:48","Longitude":"117.56192","Latitude":"26.29352","ysLongitude":"117.55042","ysLatitude":"26.29118","Angle":"55","DwellTime":"34","Velocity":"1","Miles":"2802.483"},{"Time":"2019-11-05 07:55:57","Longitude":"117.56193","Latitude":"26.29349","ysLongitude":"117.55043","ysLatitude":"26.29115","Angle":"0","DwellTime":"0","Velocity":"1","Miles":"2802.487"},{"Time":"2019-11-05 07:56:12","Longitude":"117.56192","Latitude":"26.29344","ysLongitude":"117.55042","ysLatitude":"26.29110","Angle":"90","DwellTime":"0","Velocity":"2","Miles":"2802.492"},{"Time":"2019-11-05 08:03:58","Longitude":"117.56183","Latitude":"26.29346","ysLongitude":"117.55033","ysLatitude":"26.29112","Angle":"0","DwellTime":"463","Velocity":"1","Miles":"2802.552"},{"Time":"2019-11-05 08:04:37","Longitude":"117.56192","Latitude":"26.29354","ysLongitude":"117.55042","ysLatitude":"26.29120","Angle":"42","DwellTime":"8","Velocity":"3","Miles":"2802.565"},{"Time":"2019-11-05 08:19:12","Longitude":"117.56190","Latitude":"26.29349","ysLongitude":"117.55040","ysLatitude":"26.29115","Angle":"158","DwellTime":"758","Velocity":"2","Miles":"2802.704"},{"Time":"2019-11-05 08:21:33","Longitude":"117.56190","Latitude":"26.29349","ysLongitude":"117.55040","ysLatitude":"26.29115","Angle":"244","DwellTime":"26","Velocity":"1","Miles":"2802.722"},{"Time":"2019-11-05 08:22:49","Longitude":"117.56190","Latitude":"26.29347","ysLongitude":"117.55040","ysLatitude":"26.29113","Angle":"194","DwellTime":"43","Velocity":"1","Miles":"2802.731"},{"Time":"2019-11-05 08:24:24","Longitude":"117.56190","Latitude":"26.29349","ysLongitude":"117.55040","ysLatitude":"26.29115","Angle":"9","DwellTime":"61","Velocity":"1","Miles":"2802.739"},{"Time":"2019-11-05 08:30:01","Longitude":"117.56190","Latitude":"26.29347","ysLongitude":"117.55040","ysLatitude":"26.29113","Angle":"306","DwellTime":"307","Velocity":"1","Miles":"2802.786"}]
         */

        private String res;
        private List<ResultBean> result;

        public String getRes() {
            return res;
        }

        public void setRes(String res) {
            this.res = res;
        }

        public List<ResultBean> getResult() {
            return result;
        }

        public void setResult(List<ResultBean> result) {
            this.result = result;
        }

        public static class ResultBean {
            /**
             * Time : 2019-11-05 07:02:23
             * Longitude : 117.56195
             * Latitude : 26.29507
             * ysLongitude : 117.55045
             * ysLatitude : 26.29273
             * Angle : 84
             * DwellTime : 24704
             * Velocity : 1
             * Miles : 2801.831
             */

            private String Time;
            private String Longitude;
            private String Latitude;
            private String ysLongitude;
            private String ysLatitude;
            private String Angle;
            private String DwellTime;
            private String Velocity;
            private String Miles;

            public String getTime() {
                return Time;
            }

            public void setTime(String Time) {
                this.Time = Time;
            }

            public String getLongitude() {
                return Longitude;
            }

            public void setLongitude(String Longitude) {
                this.Longitude = Longitude;
            }

            public String getLatitude() {
                return Latitude;
            }

            public void setLatitude(String Latitude) {
                this.Latitude = Latitude;
            }

            public String getYsLongitude() {
                return ysLongitude;
            }

            public void setYsLongitude(String ysLongitude) {
                this.ysLongitude = ysLongitude;
            }

            public String getYsLatitude() {
                return ysLatitude;
            }

            public void setYsLatitude(String ysLatitude) {
                this.ysLatitude = ysLatitude;
            }

            public String getAngle() {
                return Angle;
            }

            public void setAngle(String Angle) {
                this.Angle = Angle;
            }

            public String getDwellTime() {
                return DwellTime;
            }

            public void setDwellTime(String DwellTime) {
                this.DwellTime = DwellTime;
            }

            public String getVelocity() {
                return Velocity;
            }

            public void setVelocity(String Velocity) {
                this.Velocity = Velocity;
            }

            public String getMiles() {
                return Miles;
            }

            public void setMiles(String Miles) {
                this.Miles = Miles;
            }
        }
    }
}
