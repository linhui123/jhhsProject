package com.jhhscm.platform.fragment.vehicle;

import java.io.Serializable;
import java.util.List;

public class GpsDetailBean {
    private String gpsList;

    public String getGpsList() {
        return gpsList;
    }

    public void setGpsList(String gpsList) {
        this.gpsList = gpsList;
    }
    //    private List<GpsListBean> gpsList;
//
//    public List<GpsListBean> getGpsList() {
//        return gpsList;
//    }
//
//    public void setGpsList(List<GpsListBean> gpsList) {
//        this.gpsList = gpsList;
//    }

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
         * "res": "true"
         * desc
         */
        private String desc;
        private String res;
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
        private List<ResultBean> result;

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getRes() {
            return res;
        }

        public void setRes(String res) {
            this.res = res;
        }

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

        public List<ResultBean> getResult() {
            return result;
        }

        public void setResult(List<ResultBean> result) {
            this.result = result;
        }


        public static class ResultBean implements Serializable{
            /**
             * SystemNo : 13841125399
             * VehNoF : 朱少文现代385XT02
             * SimID : 1064841125399
             * Time : 2019-11-05 12:40:46
             * Longitude : 117.56444
             * Latitude : 26.29381
             * JPLongitude : 117.55295
             * JPLatitude : 26.29147
             * Angle : 12
             * Velocity : 0
             * VehStatus : 2
             * Miles : 2803.563
             * Oil : 0
             * Temperature : 0
             * DtStatus : 0
             * alarmmsg :
             * TodayMile : 1.88902
             * YyDate :
             * IsOverdue : False
             */

            private String SystemNo;
            private String VehNoF;
            private String SimID;
            private String Time;
            private String Longitude;
            private String Latitude;
            private String JPLongitude;
            private String JPLatitude;
            private String Angle;
            private String Velocity;
            private String VehStatus;
            private String Miles;
            private String Oil;
            private String Temperature;
            private String DtStatus;
            private String alarmmsg;
            private String TodayMile;
            private String YyDate;
            private String IsOverdue;

            public String getSystemNo() {
                return SystemNo;
            }

            public void setSystemNo(String SystemNo) {
                this.SystemNo = SystemNo;
            }

            public String getVehNoF() {
                return VehNoF;
            }

            public void setVehNoF(String VehNoF) {
                this.VehNoF = VehNoF;
            }

            public String getSimID() {
                return SimID;
            }

            public void setSimID(String SimID) {
                this.SimID = SimID;
            }

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

            public String getJPLongitude() {
                return JPLongitude;
            }

            public void setJPLongitude(String JPLongitude) {
                this.JPLongitude = JPLongitude;
            }

            public String getJPLatitude() {
                return JPLatitude;
            }

            public void setJPLatitude(String JPLatitude) {
                this.JPLatitude = JPLatitude;
            }

            public String getAngle() {
                return Angle;
            }

            public void setAngle(String Angle) {
                this.Angle = Angle;
            }

            public String getVelocity() {
                return Velocity;
            }

            public void setVelocity(String Velocity) {
                this.Velocity = Velocity;
            }

            public String getVehStatus() {
                return VehStatus;
            }

            public void setVehStatus(String VehStatus) {
                this.VehStatus = VehStatus;
            }

            public String getMiles() {
                return Miles;
            }

            public void setMiles(String Miles) {
                this.Miles = Miles;
            }

            public String getOil() {
                return Oil;
            }

            public void setOil(String Oil) {
                this.Oil = Oil;
            }

            public String getTemperature() {
                return Temperature;
            }

            public void setTemperature(String Temperature) {
                this.Temperature = Temperature;
            }

            public String getDtStatus() {
                return DtStatus;
            }

            public void setDtStatus(String DtStatus) {
                this.DtStatus = DtStatus;
            }

            public String getAlarmmsg() {
                return alarmmsg;
            }

            public void setAlarmmsg(String alarmmsg) {
                this.alarmmsg = alarmmsg;
            }

            public String getTodayMile() {
                return TodayMile;
            }

            public void setTodayMile(String TodayMile) {
                this.TodayMile = TodayMile;
            }

            public String getYyDate() {
                return YyDate;
            }

            public void setYyDate(String YyDate) {
                this.YyDate = YyDate;
            }

            public String getIsOverdue() {
                return IsOverdue;
            }

            public void setIsOverdue(String IsOverdue) {
                this.IsOverdue = IsOverdue;
            }
        }
    }
}
