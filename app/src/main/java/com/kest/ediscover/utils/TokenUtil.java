package com.kest.ediscover.utils;

import android.content.Context;

import com.kest.ediscover.network.MessageDispatcher;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dk on 2017/12/23.
 */

public class TokenUtil {

    private Context context;

    private SharePreferenceUtil sp = null;

    private static final String Refresh_Token_Url = MessageDispatcher.BASE_API_URI + "index.php?m=AppApi&c=Users&a=refresh_login";

    public TokenUtil(Context context) {
        this.context = context;
        sp = SharePreferenceUtil.getInstance(context);

    }

    public boolean refreshToken(String bid, String name, String password, String trpassword) {
        DebugLog.i("开始绑定账号");

        Map<String, Object> params = new HashMap<String, Object>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        params.put("requestDate", df.format(new Date()));
        params.put("token", sp.getToken());

        params.put("bid", bid);
        params.put("name", name);
        params.put("password", password);
        params.put("trpassword", trpassword);

        String response = MessageDispatcher.JsonPost(Refresh_Token_Url, params);
        JSONObject jsonObject = null;
        try {
            DebugLog.i("绑定账号请求返回：" + response);
            jsonObject = new JSONObject(response);
            if (jsonObject.optInt("returnCode") == 10000) {
                return true;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
}
