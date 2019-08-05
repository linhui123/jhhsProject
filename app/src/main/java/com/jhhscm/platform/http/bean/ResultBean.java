package com.jhhscm.platform.http.bean;

public class ResultBean {
    /**
     * result : 0
     */

    private String result;
    /**
     * data : 创建订单成功！
     */

    private String data;
    /**
     * count : 2
     */

    private int count;
    /**
     * prepay_id : wx31175219225241d8da75f4ea1156217700
     */

    private String prepay_id;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getPrepay_id() {
        return prepay_id;
    }

    public void setPrepay_id(String prepay_id) {
        this.prepay_id = prepay_id;
    }
}
