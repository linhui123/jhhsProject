package com.jhhscm.platform.photopicker.bean;

import java.util.List;

/**
 * 文件夹
 */
public class Folder {
    public String name;
    public String path;
    public Image cover;
    public List<Image> images;
    public boolean isCheck = false;

    @Override
    public boolean equals(Object o) {
        try {
            Folder other = (Folder) o;
            return this.path.equalsIgnoreCase(other.path);
        }catch (ClassCastException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
