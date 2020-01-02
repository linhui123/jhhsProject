package com.jhhscm.platform.event;

import com.jhhscm.platform.tool.EventBusUtil;

public class BrandResultEvent implements EventBusUtil.IEvent {
    private String brand_id;//品牌
    private String brand_name;//品牌
    private String fix_p_9;//型号
    private String fix_p_9_name;//型号
    private int type = 0;//1 配件页面; 0新机列表; 2 配件页面品类

    public BrandResultEvent(String brand_id, String brand_name, int type) {
        this.brand_id = brand_id;
        this.brand_name = brand_name;
        this.type = type;
    }


    public BrandResultEvent(String brand_id, String brand_name) {
        this.brand_id = brand_id;
        this.brand_name = brand_name;
    }

    public BrandResultEvent(String brand_id, String fix_p_9, String fix_p_9_name) {
        this.brand_id = brand_id;
        this.fix_p_9 = fix_p_9;
        this.fix_p_9_name = fix_p_9_name;
    }

    public BrandResultEvent(String brand_id, String brand_name, String fix_p_9, String fix_p_9_name) {
        this.brand_id = brand_id;
        this.fix_p_9 = fix_p_9;
        this.brand_name = brand_name;
        this.fix_p_9_name = fix_p_9_name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getFix_p_9_name() {
        return fix_p_9_name;
    }

    public void setFix_p_9_name(String fix_p_9_name) {
        this.fix_p_9_name = fix_p_9_name;
    }

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public String getFix_p_9() {
        return fix_p_9;
    }

    public void setFix_p_9(String fix_p_9) {
        this.fix_p_9 = fix_p_9;
    }
}
