package com.jhhscm.platform.event;

import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.views.selector.ImageSelector;
import com.jhhscm.platform.views.selector.ImageSelectorItem;

import java.util.ArrayList;
import java.util.List;

public class ImageSelectorUpdataEvent implements EventBusUtil.IEvent {
    private List<ImageSelectorItem> images;
    private ImageSelector imageSelector;
    private int position;

    public ImageSelectorUpdataEvent(ImageSelector imageSelector, List<ImageSelectorItem> images, int position) {
        this.imageSelector = imageSelector;
        this.position = position;
        this.images = images;
    }


    public ImageSelectorUpdataEvent(ImageSelector imageSelector, int position) {
        this.imageSelector = imageSelector;
        this.position = position;
    }

    public ImageSelectorUpdataEvent(List<ImageSelectorItem> images, int position) {
        this.images = images;
        this.position = position;
    }

    public ImageSelectorUpdataEvent(List<ImageSelectorItem> images) {
        this.images = images;
    }

    public List<ImageSelectorItem> getImages() {
        return images;
    }

    public void setImages(List<ImageSelectorItem> images) {
        this.images = images;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public ImageSelector getImageSelector() {
        return imageSelector;
    }

    public void setImageSelector(ImageSelector imageSelector) {
        this.imageSelector = imageSelector;
    }
}
