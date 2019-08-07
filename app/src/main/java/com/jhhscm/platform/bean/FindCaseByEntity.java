package com.jhhscm.platform.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/10/18/018.
 */

public class FindCaseByEntity implements Serializable {


    /**
     * photos : [{"NAME":"手术前","NODE_ID":3,"EXAMPLE_LIBRARY_ID":14,"photo":[{"EXAMPLE_LIBRARY_NODEID":3,"IMG_URL":"1110101","NODE":3,"PHOTO_ID":16,"CASE_ID":14}]},{"NAME":"手术当天","NODE_ID":4,"EXAMPLE_LIBRARY_ID":14,"photo":[{"EXAMPLE_LIBRARY_NODEID":4,"IMG_URL":"1110101","NODE":4,"PHOTO_ID":17,"CASE_ID":14}]},{"NAME":"手术后10天","NODE_ID":1,"EXAMPLE_LIBRARY_ID":14,"photo":[{"EXAMPLE_LIBRARY_NODEID":1,"IMG_URL":"1110101","NODE":1,"PHOTO_ID":14,"CASE_ID":14}]},{"NAME":"手术后20天","NODE_ID":2,"EXAMPLE_LIBRARY_ID":14,"photo":[{"EXAMPLE_LIBRARY_NODEID":2,"IMG_URL":"1110101","NODE":2,"PHOTO_ID":15,"CASE_ID":14},{"EXAMPLE_LIBRARY_NODEID":2,"IMG_URL":"1110101","NODE":2,"PHOTO_ID":18,"CASE_ID":14},{"EXAMPLE_LIBRARY_NODEID":2,"IMG_URL":"145456","NODE":2,"PHOTO_ID":19,"CASE_ID":14}]}]
     * EXAMPLE_LIBRARY_ID : 14
     * CASE_NAME : 老毕的相册
     */

    private String EXAMPLE_LIBRARY_ID;
    private String CASE_NAME;
    private String REMARK;
    private List<PhotosBean> photos;

    public String getREMARK() {
        return REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }

    public String getEXAMPLE_LIBRARY_ID() {
        return EXAMPLE_LIBRARY_ID;
    }

    public void setEXAMPLE_LIBRARY_ID(String EXAMPLE_LIBRARY_ID) {
        this.EXAMPLE_LIBRARY_ID = EXAMPLE_LIBRARY_ID;
    }

    public String getCASE_NAME() {
        return CASE_NAME;
    }

    public void setCASE_NAME(String CASE_NAME) {
        this.CASE_NAME = CASE_NAME;
    }

    public List<PhotosBean> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotosBean> photos) {
        this.photos = photos;
    }

    public static class PhotosBean implements Serializable {
        /**
         * NAME : 手术前
         * NODE_ID : 3
         * EXAMPLE_LIBRARY_ID : 14
         * photo : [{"EXAMPLE_LIBRARY_NODEID":3,"IMG_URL":"1110101","NODE":3,"PHOTO_ID":16,"CASE_ID":14}]
         */

        private String NAME;
        private String NODE_ID;
        private String EXAMPLE_LIBRARY_ID;
        private List<PhotoBean> photo;

        public String getNAME() {
            return NAME;
        }

        public void setNAME(String NAME) {
            this.NAME = NAME;
        }

        public String getNODE_ID() {
            return NODE_ID;
        }

        public void setNODE_ID(String NODE_ID) {
            this.NODE_ID = NODE_ID;
        }

        public String getEXAMPLE_LIBRARY_ID() {
            return EXAMPLE_LIBRARY_ID;
        }

        public void setEXAMPLE_LIBRARY_ID(String EXAMPLE_LIBRARY_ID) {
            this.EXAMPLE_LIBRARY_ID = EXAMPLE_LIBRARY_ID;
        }

        public List<PhotoBean> getPhoto() {
            return photo;
        }

        public void setPhoto(List<PhotoBean> photo) {
            this.photo = photo;
        }

        public static class PhotoBean implements Serializable {
            /**
             * EXAMPLE_LIBRARY_NODEID : 3
             * IMG_URL : 1110101
             * NODE : 3
             * PHOTO_ID : 16
             * CASE_ID : 14
             */

            private String EXAMPLE_LIBRARY_NODEID;
            private String IMG_URL;
            private String NODE;
            private String PHOTO_ID;
            private String CASE_ID;

            public String getEXAMPLE_LIBRARY_NODEID() {
                return EXAMPLE_LIBRARY_NODEID;
            }

            public void setEXAMPLE_LIBRARY_NODEID(String EXAMPLE_LIBRARY_NODEID) {
                this.EXAMPLE_LIBRARY_NODEID = EXAMPLE_LIBRARY_NODEID;
            }

            public String getIMG_URL() {
                return IMG_URL;
            }

            public void setIMG_URL(String IMG_URL) {
                this.IMG_URL = IMG_URL;
            }

            public String getNODE() {
                return NODE;
            }

            public void setNODE(String NODE) {
                this.NODE = NODE;
            }

            public String getPHOTO_ID() {
                return PHOTO_ID;
            }

            public void setPHOTO_ID(String PHOTO_ID) {
                this.PHOTO_ID = PHOTO_ID;
            }

            public String getCASE_ID() {
                return CASE_ID;
            }

            public void setCASE_ID(String CASE_ID) {
                this.CASE_ID = CASE_ID;
            }
        }
    }
}
