package com.jhhscm.platform.fragment.vehicle;

import java.io.Serializable;
import java.util.List;

public class GpsDetailBean {

    private List<GpsListBean> gpsList;

    public List<GpsListBean> getGpsList() {
        return gpsList;
    }

    public void setGpsList(List<GpsListBean> gpsList) {
        this.gpsList = gpsList;
    }

    public static class GpsListBean implements Serializable {
        /**
         * vid : 丈八水-陕AL3790
         * hx : 191
         * mlat : 34.212107
         * yl : 8888
         * lng : 108850824
         * lc : 32163335
         * mlng : 108.861869
         * pk : 18017
         * ol : 0
         * lat : 34207475
         */

        private String vid;//车牌号
        private int hx;//方向
        private String mlat;
        private int yl;//油量
        private int lng;
        private int lc;//里程
        private String mlng;
        private int pk;//停车时长
        private int ol;//在线状态
        private int lat;
        private String ps;//地理位置
        private String sp;//速度
        private String s1;//状态  0为无效,1为有效。 s1:1位	ACC状态	0表示ACC关闭1表示ACC开启。
        private String t1;//温度
        private String gt;//GPS上传时间
        private String id;//设备号
        private String ac;//音频类型
        private String ov;//电压
        public String getVid() {
            return vid;
        }

        public void setVid(String vid) {
            this.vid = vid;
        }

        public int getHx() {
            return hx;
        }

        public void setHx(int hx) {
            this.hx = hx;
        }

        public String getMlat() {
            return mlat;
        }

        public void setMlat(String mlat) {
            this.mlat = mlat;
        }

        public int getYl() {
            return yl;
        }

        public void setYl(int yl) {
            this.yl = yl;
        }

        public int getLng() {
            return lng;
        }

        public void setLng(int lng) {
            this.lng = lng;
        }

        public int getLc() {
            return lc;
        }

        public void setLc(int lc) {
            this.lc = lc;
        }

        public String getMlng() {
            return mlng;
        }

        public void setMlng(String mlng) {
            this.mlng = mlng;
        }

        public int getPk() {
            return pk;
        }

        public void setPk(int pk) {
            this.pk = pk;
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

        public String getPs() {
            return ps;
        }

        public void setPs(String ps) {
            this.ps = ps;
        }

        public String getSp() {
            return sp;
        }

        public void setSp(String sp) {
            this.sp = sp;
        }

        public String getS1() {
            return s1;
        }

        public void setS1(String s1) {
            this.s1 = s1;
        }

        public String getT1() {
            return t1;
        }

        public void setT1(String t1) {
            this.t1 = t1;
        }

        public String getGt() {
            return gt;
        }

        public void setGt(String gt) {
            this.gt = gt;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAc() {
            return ac;
        }

        public void setAc(String ac) {
            this.ac = ac;
        }

        public String getOv() {
            return ov;
        }

        public void setOv(String ov) {
            this.ov = ov;
        }
    }
}
