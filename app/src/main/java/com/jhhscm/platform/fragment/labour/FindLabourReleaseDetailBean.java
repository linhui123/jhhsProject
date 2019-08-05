package com.jhhscm.platform.fragment.labour;

public class FindLabourReleaseDetailBean {


    /**
     * data : {"work_num":"10","labour_code":"6","city":"新疆维吾尔自治区","settl_time":"日结","num":1,"end_time":"","work_pre":"兼职：短期","salay_money":"薪资5000-6000元/月","province":"河北省","name":"招聘挖掘机小能手","id":6,"work_time":"二把手","position":"项目位置9","job":"电工","other_desc":"其他说明12","m_type":"微挖"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * work_num : 10
         * labour_code : 6
         * city : 新疆维吾尔自治区
         * settl_time : 日结
         * num : 1
         * end_time :
         * work_pre : 兼职：短期
         * salay_money : 薪资5000-6000元/月
         * province : 河北省
         * name : 招聘挖掘机小能手
         * id : 6
         * work_time : 二把手
         * position : 项目位置9
         * job : 电工
         * other_desc : 其他说明12
         * m_type : 微挖
         */

        private String work_num;
        private String labour_code;
        private String city;
        private String settl_time;
        private int num;
        private String end_time;
        private String work_pre;
        private String salay_money;
        private String province;
        private String name;
        private String id;
        private String work_time;
        private String position;
        private String job;
        private String other_desc;
        private String m_type;
        /**
         * province_text : 北京市
         * work_pre_text : 1
         * city_text : 晋城市
         * update_time : 2019-08-05 16:13:12
         * job_text : 操作手
         * m_type_text : 微挖
         * contact_msg : 15927112992
         * contact : 小崔
         * id : 11
         * settl_time_text : 日结
         * work_time_text : 学徒工
         * salay_money_text : 薪资面议
         */

        private String province_text;
        private String work_pre_text;
        private String city_text;
        private String update_time;
        private String job_text;
        private String m_type_text;
        private String contact_msg;
        private String contact;
        private String settl_time_text;
        private String work_time_text;
        private String salay_money_text;
        /**
         * good_work : 1
         * id : 11
         * good_work_text : 矿山
         */

        private String good_work;
        private String good_work_text;

        private String work_type;
        private String work_type_text;

        private String other_req;

        public String getOther_req() {
            return other_req;
        }

        public void setOther_req(String other_req) {
            this.other_req = other_req;
        }

        public String getWork_type() {
            return work_type;
        }

        public void setWork_type(String work_type) {
            this.work_type = work_type;
        }

        public String getWork_type_text() {
            return work_type_text;
        }

        public void setWork_type_text(String work_type_text) {
            this.work_type_text = work_type_text;
        }

        public String getWork_num() {
            return work_num;
        }

        public void setWork_num(String work_num) {
            this.work_num = work_num;
        }

        public String getLabour_code() {
            return labour_code;
        }

        public void setLabour_code(String labour_code) {
            this.labour_code = labour_code;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getSettl_time() {
            return settl_time;
        }

        public void setSettl_time(String settl_time) {
            this.settl_time = settl_time;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getWork_pre() {
            return work_pre;
        }

        public void setWork_pre(String work_pre) {
            this.work_pre = work_pre;
        }

        public String getSalay_money() {
            return salay_money;
        }

        public void setSalay_money(String salay_money) {
            this.salay_money = salay_money;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getWork_time() {
            return work_time;
        }

        public void setWork_time(String work_time) {
            this.work_time = work_time;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public String getOther_desc() {
            return other_desc;
        }

        public void setOther_desc(String other_desc) {
            this.other_desc = other_desc;
        }

        public String getM_type() {
            return m_type;
        }

        public void setM_type(String m_type) {
            this.m_type = m_type;
        }

        public String getProvince_text() {
            return province_text;
        }

        public void setProvince_text(String province_text) {
            this.province_text = province_text;
        }

        public String getWork_pre_text() {
            return work_pre_text;
        }

        public void setWork_pre_text(String work_pre_text) {
            this.work_pre_text = work_pre_text;
        }

        public String getCity_text() {
            return city_text;
        }

        public void setCity_text(String city_text) {
            this.city_text = city_text;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getJob_text() {
            return job_text;
        }

        public void setJob_text(String job_text) {
            this.job_text = job_text;
        }

        public String getM_type_text() {
            return m_type_text;
        }

        public void setM_type_text(String m_type_text) {
            this.m_type_text = m_type_text;
        }

        public String getContact_msg() {
            return contact_msg;
        }

        public void setContact_msg(String contact_msg) {
            this.contact_msg = contact_msg;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getSettl_time_text() {
            return settl_time_text;
        }

        public void setSettl_time_text(String settl_time_text) {
            this.settl_time_text = settl_time_text;
        }

        public String getWork_time_text() {
            return work_time_text;
        }

        public void setWork_time_text(String work_time_text) {
            this.work_time_text = work_time_text;
        }

        public String getSalay_money_text() {
            return salay_money_text;
        }

        public void setSalay_money_text(String salay_money_text) {
            this.salay_money_text = salay_money_text;
        }

        public String getGood_work() {
            return good_work;
        }

        public void setGood_work(String good_work) {
            this.good_work = good_work;
        }

        public String getGood_work_text() {
            return good_work_text;
        }

        public void setGood_work_text(String good_work_text) {
            this.good_work_text = good_work_text;
        }
    }
}
