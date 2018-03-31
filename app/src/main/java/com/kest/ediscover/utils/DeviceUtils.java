package com.kest.ediscover.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * Created by dk on 2017/12/18.
 */

public class DeviceUtils {
    public static String getDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = tm.getDeviceId();
        if (deviceId == null) {
            return "";
        } else {
            return deviceId;
        }
    }
}
