package com.jhhscm.platform.fragment.lessee;

import java.io.Serializable;
import java.util.List;

public class LesseeBean implements Serializable {

    /**
     * wBankleaseFileList : [{"type":0,"fileType":1,"fileUrl":"123"},{"type":0,"fileType":1,"fileUrl":"123"}]
     * wBankLeasePerson : {"name":"姓名","sex":1,"idCard":"123","idCardAddress":"123","phone":"1358189494","postalAddress":"通讯地址","postalCode":"通讯地址","company":"工作单位","companyAddress":"工作单位","marryType":1,"spouseName":"配偶姓名","spouseCard":"配偶身份证","spousePhone":"配偶手机号","spouseCompany":"配偶工作单位","spouseCompanyAddress":"工作单位"}
     * wBankLeaseItems : [{"name":"租赁物","brandId":"123","fixP17":"15X5","factoryTime":"2019-02-01","machineNum":"机器编号","machinePrice":"12.00","machineAssess":"15.00"}]
     * wBankLeaseSurety : [{"name":"担保人姓名","idCard":"44445894849","idCardUrl":"44445894849","houseUrl":"44445894849","marryUrl":"44445894849","creditUrl":"44445894849","moneyUrl":"44445894849"}]
     */

    private WBankLeasePersonBean wBankLeasePerson;
    private List<WBankleaseFileListBean> wBankleaseFileList;
    private List<WBankLeaseItemsBean> wBankLeaseItems;
    private WBankLeaseSuretyBean wBankLeaseSurety;

    public WBankLeasePersonBean getWBankLeasePerson() {
        return wBankLeasePerson;
    }

    public void setWBankLeasePerson(WBankLeasePersonBean wBankLeasePerson) {
        this.wBankLeasePerson = wBankLeasePerson;
    }

    public List<WBankleaseFileListBean> getWBankleaseFileList() {
        return wBankleaseFileList;
    }

    public void setWBankleaseFileList(List<WBankleaseFileListBean> wBankleaseFileList) {
        this.wBankleaseFileList = wBankleaseFileList;
    }

    public List<WBankLeaseItemsBean> getWBankLeaseItems() {
        return wBankLeaseItems;
    }

    public void setWBankLeaseItems(List<WBankLeaseItemsBean> wBankLeaseItems) {
        this.wBankLeaseItems = wBankLeaseItems;
    }

    public WBankLeaseSuretyBean getwBankLeaseSurety() {
        return wBankLeaseSurety;
    }

    public void setwBankLeaseSurety(WBankLeaseSuretyBean wBankLeaseSurety) {
        this.wBankLeaseSurety = wBankLeaseSurety;
    }

    public static class WBankLeasePersonBean implements Serializable {
        /**
         * name : 姓名
         * sex : 1
         * idCard : 123
         * idCardAddress : 123
         * phone : 1358189494
         * postalAddress : 通讯地址
         * postalCode : 通讯地址
         * company : 工作单位
         * companyAddress : 工作单位
         * marryType : 1
         * spouseName : 配偶姓名
         * spouseCard : 配偶身份证
         * spousePhone : 配偶手机号
         * spouseCompany : 配偶工作单位
         * spouseCompanyAddress : 工作单位
         * aPhone（紧急联系人电话）
         * aName（紧急联系人姓名）
         */
        private String aPhone;
        private String aName;
        private String name;
        private int sex;
        private String idCard;
        private String idCardAddress;
        private String phone;
        private String postalAddress;
        private String postalCode;
        private String company;
        private String companyAddress;
        private int marryType;
        private String spouseName;
        private String spouseCard;
        private String spousePhone;
        private String spouseCompany;
        private String spouseCompanyAddress;
        private String userCode;

        public String getUserCode() {
            return userCode;
        }

        public void setUserCode(String userCode) {
            this.userCode = userCode;
        }

        public String getaPhone() {
            return aPhone;
        }

        public void setaPhone(String aPhone) {
            this.aPhone = aPhone;
        }

        public String getaName() {
            return aName;
        }

        public void setaName(String aName) {
            this.aName = aName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getIdCardAddress() {
            return idCardAddress;
        }

        public void setIdCardAddress(String idCardAddress) {
            this.idCardAddress = idCardAddress;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPostalAddress() {
            return postalAddress;
        }

        public void setPostalAddress(String postalAddress) {
            this.postalAddress = postalAddress;
        }

        public String getPostalCode() {
            return postalCode;
        }

        public void setPostalCode(String postalCode) {
            this.postalCode = postalCode;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getCompanyAddress() {
            return companyAddress;
        }

        public void setCompanyAddress(String companyAddress) {
            this.companyAddress = companyAddress;
        }

        public int getMarryType() {
            return marryType;
        }

        public void setMarryType(int marryType) {
            this.marryType = marryType;
        }

        public String getSpouseName() {
            return spouseName;
        }

        public void setSpouseName(String spouseName) {
            this.spouseName = spouseName;
        }

        public String getSpouseCard() {
            return spouseCard;
        }

        public void setSpouseCard(String spouseCard) {
            this.spouseCard = spouseCard;
        }

        public String getSpousePhone() {
            return spousePhone;
        }

        public void setSpousePhone(String spousePhone) {
            this.spousePhone = spousePhone;
        }

        public String getSpouseCompany() {
            return spouseCompany;
        }

        public void setSpouseCompany(String spouseCompany) {
            this.spouseCompany = spouseCompany;
        }

        public String getSpouseCompanyAddress() {
            return spouseCompanyAddress;
        }

        public void setSpouseCompanyAddress(String spouseCompanyAddress) {
            this.spouseCompanyAddress = spouseCompanyAddress;
        }
    }

    public static class WBankleaseFileListBean implements Serializable {
        /**
         * type : 0
         * fileType : 1
         * fileUrl : 123
         */

        private int type;
        private String fileType;
        private String fileUrl;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getFileType() {
            return fileType;
        }

        public void setFileType(String fileType) {
            this.fileType = fileType;
        }

        public String getFileUrl() {
            return fileUrl;
        }

        public void setFileUrl(String fileUrl) {
            this.fileUrl = fileUrl;
        }
    }

    public static class WBankLeaseItemsBean implements Serializable {
        /**
         * name : 租赁物
         * brandId : 123
         * fixP17 : 15X5
         * factoryTime : 2019-02-01
         * machineNum : 机器编号
         * machinePrice : 12.00
         * machineAssess : 15.00
         * itemUrl
         * m1 购买价格
         * m2 发动机号
         * m3 车架号
         * m4 小时数
         * m5 施工区域
         * m6 施工类型
         */

        private String name;
        private String brandId;
        private String brandName;
        private String fixP17;
        private String factoryTime;
        private String machineNum;
        private String machinePrice;
        private String machineAssess;
        private String itemUrl;
        private String m1;
        private String m2;
        private String m3;
        private String m4;
        private String m5;
        private String m6;

        public String getM1() {
            return m1;
        }

        public void setM1(String m1) {
            this.m1 = m1;
        }

        public String getM2() {
            return m2;
        }

        public void setM2(String m2) {
            this.m2 = m2;
        }

        public String getM3() {
            return m3;
        }

        public void setM3(String m3) {
            this.m3 = m3;
        }

        public String getM4() {
            return m4;
        }

        public void setM4(String m4) {
            this.m4 = m4;
        }

        public String getM5() {
            return m5;
        }

        public void setM5(String m5) {
            this.m5 = m5;
        }

        public String getM6() {
            return m6;
        }

        public void setM6(String m6) {
            this.m6 = m6;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public String getItemUrl() {
            return itemUrl;
        }

        public void setItemUrl(String itemUrl) {
            this.itemUrl = itemUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBrandId() {
            return brandId;
        }

        public void setBrandId(String brandId) {
            this.brandId = brandId;
        }

        public String getFixP17() {
            return fixP17;
        }

        public void setFixP17(String fixP17) {
            this.fixP17 = fixP17;
        }

        public String getFactoryTime() {
            return factoryTime;
        }

        public void setFactoryTime(String factoryTime) {
            this.factoryTime = factoryTime;
        }

        public String getMachineNum() {
            return machineNum;
        }

        public void setMachineNum(String machineNum) {
            this.machineNum = machineNum;
        }

        public String getMachinePrice() {
            return machinePrice;
        }

        public void setMachinePrice(String machinePrice) {
            this.machinePrice = machinePrice;
        }

        public String getMachineAssess() {
            return machineAssess;
        }

        public void setMachineAssess(String machineAssess) {
            this.machineAssess = machineAssess;
        }
    }

    public static class WBankLeaseSuretyBean implements Serializable {
        /**
         * name : 担保人姓名
         * idCard : 44445894849
         * idCardUrl : 44445894849
         * houseUrl : 44445894849
         * marryUrl : 44445894849
         * creditUrl : 44445894849
         * moneyUrl : 44445894849
         * phone   （手机号）
         * m1   紧急联系人
         * m2   紧急联系人手机号
         */

        private String name;
        private String phone;
        private String idCard;
        private String idCardUrl;
        private String houseUrl;
        private String marryUrl;
        private String creditUrl;
        private String moneyUrl;
        private String m1;
        private String m2;

        public String getM1() {
            return m1;
        }

        public void setM1(String m1) {
            this.m1 = m1;
        }

        public String getM2() {
            return m2;
        }

        public void setM2(String m2) {
            this.m2 = m2;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getIdCardUrl() {
            return idCardUrl;
        }

        public void setIdCardUrl(String idCardUrl) {
            this.idCardUrl = idCardUrl;
        }

        public String getHouseUrl() {
            return houseUrl;
        }

        public void setHouseUrl(String houseUrl) {
            this.houseUrl = houseUrl;
        }

        public String getMarryUrl() {
            return marryUrl;
        }

        public void setMarryUrl(String marryUrl) {
            this.marryUrl = marryUrl;
        }

        public String getCreditUrl() {
            return creditUrl;
        }

        public void setCreditUrl(String creditUrl) {
            this.creditUrl = creditUrl;
        }

        public String getMoneyUrl() {
            return moneyUrl;
        }

        public void setMoneyUrl(String moneyUrl) {
            this.moneyUrl = moneyUrl;
        }
    }
}
