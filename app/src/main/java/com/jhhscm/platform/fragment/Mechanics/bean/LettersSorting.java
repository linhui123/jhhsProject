package com.jhhscm.platform.fragment.Mechanics.bean;

import java.util.Comparator;

public class LettersSorting implements Comparator<FindBrandBean.ResultBean> {

    @Override
    public int compare(FindBrandBean.ResultBean lettersModel, FindBrandBean.ResultBean t1) {
        //给我两个对象，我只比较他的字母
        return lettersModel.getLetter().compareTo(t1.getLetter());
    }
}
