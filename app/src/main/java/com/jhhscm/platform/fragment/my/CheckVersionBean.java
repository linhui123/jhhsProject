package com.jhhscm.platform.fragment.my;

public class CheckVersionBean {

    /**
     * is_must_update : 1
     * is_update : 1
     */

    private int is_must_update;
    private int is_update;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getIs_must_update() {
        return is_must_update;
    }

    public void setIs_must_update(int is_must_update) {
        this.is_must_update = is_must_update;
    }

    public int getIs_update() {
        return is_update;
    }

    public void setIs_update(int is_update) {
        this.is_update = is_update;
    }
}
