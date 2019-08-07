package com.jhhscm.platform.event;

import com.jhhscm.platform.tool.EventBusUtil;

import java.util.ArrayList;

/**
 * 类说明：选择图片事件
 * 作者：huangqiuxin on 2016/5/19 18:36
 * 邮箱：648859026@qq.com
 */
public class ImageSelectorEvent implements EventBusUtil.IEvent {
    public static final int EVENT_ADD = 100;//新增
    public static final int EVENT_DEL = 200;//删除
    public static final int EVENT_SHOW_DEL = 500;//删除
    public static final int EVENT_CLOTHESPRESS_ADD = 300;//衣柜新增
    public static final int EVENT_CLOTHESPRESS_DEL = 400;//衣柜删除
    private int code;
    private int type;
    private String delImage;
    private ArrayList<String> images;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDelImage() {
        return delImage;
    }

    public void setDelImage(String delImage) {
        this.delImage = delImage;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public static ImageSelectorEvent newInstance(int code) {
        ImageSelectorEvent event = new ImageSelectorEvent();
        event.setCode(code);
        return event;
    }

    public static ImageSelectorEvent newInstance(int code, int type) {
        ImageSelectorEvent event = new ImageSelectorEvent();
        event.setCode(code);
        event.setType(type);
        return event;
    }

    public static ImageSelectorEvent newInstance(int code, int type, String delImage) {
        ImageSelectorEvent event = new ImageSelectorEvent();
        event.setCode(code);
        event.setType(type);
        event.setDelImage(delImage);
        return event;
    }

    public static ImageSelectorEvent newInstance(int code, int type, ArrayList<String> images) {
        ImageSelectorEvent event = new ImageSelectorEvent();
        event.setCode(code);
        event.setType(type);
        event.setImages(images);
        return event;
    }

    public static ImageSelectorEvent newInstance(int type, ArrayList<String> images) {
        ImageSelectorEvent event = new ImageSelectorEvent();
        event.setType(type);
        event.setImages(images);
        return event;
    }

}
