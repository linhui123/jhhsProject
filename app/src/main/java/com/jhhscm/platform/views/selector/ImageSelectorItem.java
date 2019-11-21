package com.jhhscm.platform.views.selector;

import java.io.Serializable;

/**
 * 类说明：
 */
public class ImageSelectorItem implements Serializable {
    public int imageId = 0;
    public String imageUrl;
    public String imageToken;
    public String catalogues;
    public String allfilePath;
    public boolean addFlag = false;
    public boolean isSHow = false;

    public static ImageSelectorItem convert(String imagePath) {
        ImageSelectorItem item = new ImageSelectorItem();
        item.imageUrl = imagePath;
        return item;
    }

    public static ImageSelectorItem newAddImageItem() {
        ImageSelectorItem item = new ImageSelectorItem();
        item.addFlag = true;
        return item;
    }

    public boolean isAddFlag() {
        return addFlag;
    }

    public boolean isSHow() {
        return isSHow;
    }

    public void setSHow(boolean SHow) {
        isSHow = SHow;
    }

    @Override
    public boolean equals(Object o) {
        if (o != null) {
            if (o instanceof ImageSelectorItem) {
                ImageSelectorItem other = (ImageSelectorItem) o;
                if (imageId > 0) {
                    if (imageId == other.imageId) {
                        return true;
                    }
                } else {
                    if (imageUrl != null && imageUrl.equals(other.imageUrl)) {
                        return true;
                    }
                }
            } else if (o instanceof Integer) {
                int other = (int) o;
                if (imageId == other) {
                    return true;
                }
            } else if (o instanceof String) {
                String other = (String) o;
                if (imageUrl != null && imageUrl.equals(other)) {
                    return true;
                }
            }
        }
        return false;
    }
}
