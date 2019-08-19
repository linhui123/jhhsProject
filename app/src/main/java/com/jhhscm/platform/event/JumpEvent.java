package com.jhhscm.platform.event;

import com.jhhscm.platform.fragment.Mechanics.bean.FindBrandBean;
import com.jhhscm.platform.fragment.Mechanics.bean.GetGoodsByBrandBean;
import com.jhhscm.platform.fragment.home.AdBean;
import com.jhhscm.platform.tool.EventBusUtil;

public class JumpEvent implements EventBusUtil.IEvent {
    private AdBean.ResultBean resultBean;
    private String type;
    private FindBrandBean.ResultBean brand;
    private String brand_id;//品牌
    private String brand_name;//品牌

    public JumpEvent(String type, AdBean.ResultBean resultBean) {
        this.type = type;
        this.resultBean = resultBean;
    }

    public JumpEvent(String type,String brand_id,String brand_name) {
        this.type = type;
        this.brand_id = brand_id;
        this.brand_name = brand_name;
    }

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public FindBrandBean.ResultBean getBrand() {
        return brand;
    }

    public void setBrand(FindBrandBean.ResultBean brand) {
        this.brand = brand;
    }

    public AdBean.ResultBean getResultBean() {
        return resultBean;
    }

    public void setResultBean(AdBean.ResultBean resultBean) {
        this.resultBean = resultBean;
    }

    public JumpEvent(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}