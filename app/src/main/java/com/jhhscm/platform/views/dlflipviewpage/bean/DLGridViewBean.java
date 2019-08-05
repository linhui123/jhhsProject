package com.jhhscm.platform.views.dlflipviewpage.bean;

/**
 * 默认的GridView的实体类
 * 寄存显示页面的数据
 * @author  dlong
 * created at 2019/3/21 10:36 AM
 */
public class DLGridViewBean{
    /** 文字 */
    private String text;
    /** 图片 */
    private String img;
    /** 携带的不明物体 */
    private Object object;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
