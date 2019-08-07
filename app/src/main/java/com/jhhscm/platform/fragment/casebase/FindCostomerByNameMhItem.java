package com.jhhscm.platform.fragment.casebase;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/8/27/027.
 */

public class FindCostomerByNameMhItem {

    public FindCostomerByNameMhEntity.CUSTOMERDETAILSBean customerdetailsBean;
    public String mType;

    public static FindCostomerByNameMhItem convert(FindCostomerByNameMhEntity.CUSTOMERDETAILSBean customerdetailsBean1, String type) {
        FindCostomerByNameMhItem item =new FindCostomerByNameMhItem();
        item.customerdetailsBean=customerdetailsBean1;
        item.mType=type;
        return item;
    }

    public static List<FindCostomerByNameMhItem> convert(List<FindCostomerByNameMhEntity.CUSTOMERDETAILSBean> patientsEntities, String type) {
        List<FindCostomerByNameMhItem> items = new ArrayList<>();
        for (int i = 0; i < patientsEntities.size(); i++) {
            items.add(convert(patientsEntities.get(i),type));
        }
        return items;
    }
}
