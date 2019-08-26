package com.jhhscm.platform.tool;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.CellIdentityCdma;
import android.telephony.CellIdentityGsm;
import android.telephony.CellIdentityLte;
import android.telephony.CellIdentityWcdma;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellSignalStrengthCdma;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.CellSignalStrengthLte;
import android.telephony.CellSignalStrengthWcdma;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class AMapUtil {
    private static final String HtmlBlack = "#000000";
    private static final String HtmlGray = "#808080";

    private AMapUtil() {
    }

    public String getHtmlBlack() {
        return HtmlBlack;
    }


    public String getHtmlGray() {
        return HtmlGray;
    }


    public static List getTowerInfo(Context context) {
        int mcc = -1;
        int mnc = -1;
        int lac = -1;
        int cellId = -1;
        int rssi = -1;
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm != null) {
            String operator = tm.getNetworkOperator();
            mcc = Integer.parseInt(operator.substring(0, 3));

            List<String> list = new ArrayList<>();
            @SuppressLint("MissingPermission")
            List<CellInfo> infos = tm.getAllCellInfo();

            if (operator != null) {
                for (CellInfo info : infos) {
                    if (info instanceof CellInfoCdma) {
                        CellIdentityCdma cellIdentityCdma = ((CellInfoCdma) info).getCellIdentity();
                        mnc = cellIdentityCdma.getSystemId();
                        lac = cellIdentityCdma.getNetworkId();
                        cellId = cellIdentityCdma.getBasestationId();
                        CellSignalStrengthCdma cellSignalStrengthCdma = ((CellInfoCdma) info).getCellSignalStrength();
                    } else if (info instanceof CellInfoGsm) {
                        CellIdentityGsm cellIdentityGsm = ((CellInfoGsm) info).getCellIdentity();
                        mnc = cellIdentityGsm.getMnc();
                        lac = cellIdentityGsm.getLac();
                        cellId = cellIdentityGsm.getCid();
                        CellSignalStrengthGsm cellSignalStrengthGsm = ((CellInfoGsm) info).getCellSignalStrength();
                        rssi = cellSignalStrengthGsm.getDbm();
                    } else if (info instanceof CellInfoLte) {
                        CellIdentityLte cellIdentityLte = ((CellInfoLte) info).getCellIdentity();
                        mnc = cellIdentityLte.getMnc();
                        lac = cellIdentityLte.getTac();
                        cellId = cellIdentityLte.getCi();
                        CellSignalStrengthLte cellSignalStrengthLte = ((CellInfoLte) info).getCellSignalStrength();
                        rssi = cellSignalStrengthLte.getDbm();
                    } else if (info instanceof CellInfoWcdma) {
                        CellIdentityWcdma cellIdentityWcdma = null;
                        CellSignalStrengthWcdma cellSignalStrengthWcdma = null;

                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
                            cellIdentityWcdma = ((CellInfoWcdma) info).getCellIdentity();
                            mnc = cellIdentityWcdma.getMnc();
                            lac = cellIdentityWcdma.getLac();
                            cellId = cellIdentityWcdma.getCid();
                            cellSignalStrengthWcdma = ((CellInfoWcdma) info).getCellSignalStrength();
                            rssi = cellSignalStrengthWcdma.getDbm();
                        }
                    } else {
                        Log.e("获取基站失败", "get CellInfo error");
                        return null;
                    }
                    String tower = (mcc + "#" + mnc + "#" + lac
                            + "#" + cellId + "#" + rssi);

                    Log.w("基站", tower);

                    list.add(tower);
                }

                if (list.size() > 6) {
                    list = list.subList(0, 5);
                } else if (list.size() < 3) {
                    int need = 3 - list.size();
                    for (int i = 0; i < need; i++) {
                        list.add("");
                    }
                }
                return list;
            }
        }
        return null;
    }
}
