package com.kest.ediscover.MyHttputils;

import com.google.gson.Gson;

/**
 * Created by Administrator on 2018/3/23 0023.
 */

public class GsonUtils {

    private static volatile Gson gson;
    public static Gson getInstance() {
        if(gson == null) {
            gson = new Gson();
        }
        return gson;
    }
}
