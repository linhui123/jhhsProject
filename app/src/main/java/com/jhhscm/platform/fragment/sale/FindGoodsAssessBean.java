package com.jhhscm.platform.fragment.sale;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FindGoodsAssessBean implements Serializable{
    /**
     * code : 200
     * good_code : 1000000313193788
     * fix_p_14 : 1
     * counter_price : 12.0
     * name : app
     * id : 170
     * pic_url : http://wajueji.oss-cn-shenzhen.aliyuncs.com/oldGood/fc23b1a7600540d99a7b08bf12f2309b.jpg?Expires=1881053955&OSSAccessKeyId=LTAI4F3Gt8M6rbEl&Signature=g0ZkAWC3zU46IxCHOKAvsC1WIKI%3D
     * old_time : 12
     * factory_time : 2019
     */

    private String code;
    private String good_code;
    private String fix_p_14;
    private String counter_price;
    private String name;
    private String id;
    private String pic_url;
    private String old_time;
    private String factory_time;
    private String city;
    private String province;
    private String fix_p_13;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGood_code() {
        return good_code;
    }

    public void setGood_code(String good_code) {
        this.good_code = good_code;
    }

    public String getFix_p_14() {
        return fix_p_14;
    }

    public void setFix_p_14(String fix_p_14) {
        this.fix_p_14 = fix_p_14;
    }

    public String getCounter_price() {
        return counter_price;
    }

    public void setCounter_price(String counter_price) {
        this.counter_price = counter_price;
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

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getOld_time() {
        return old_time;
    }

    public void setOld_time(String old_time) {
        this.old_time = old_time;
    }

    public String getFactory_time() {
        return factory_time;
    }

    public void setFactory_time(String factory_time) {
        this.factory_time = factory_time;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getFix_p_13() {
        return fix_p_13;
    }

    public void setFix_p_13(String fix_p_13) {
        this.fix_p_13 = fix_p_13;
    }
}
