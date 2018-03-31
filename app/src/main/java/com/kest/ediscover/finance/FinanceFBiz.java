package com.kest.ediscover.finance;

import android.content.Context;

import com.kest.ediscover.network.MessageDispatcher;
import com.kest.ediscover.utils.DebugLog;
import com.kest.ediscover.utils.SharePreferenceUtil;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dk on 2017/12/23.
 */

public class FinanceFBiz {

    private Context context;

    private SharePreferenceUtil sp = null;
    private static final String Get_Pay_Code_URI = MessageDispatcher.BASE_API_URI + "index.php?m=AppApi&c=Users&a=generate_payment_code";

    public FinanceFBiz(Context context) {
        this.context = context;
        sp = SharePreferenceUtil.getInstance(context);
    }

    public String getPayCode() {
        String payUrl = "";
        Map<String, Object> params = new HashMap<String, Object>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        params.put("requestDate", df.format(new Date()));
        params.put("token", sp.getToken());
        //params.put("requestNumber", requestNumber);

        try {
            String response = MessageDispatcher.JsonPost(Get_Pay_Code_URI, params);
            JSONObject jsonObject = new JSONObject(response);

            DebugLog.i("获取收款吗"+response);

            //JSONObject responseBody = new JSONObject(jsonObject.optString("returnBody"));
            if (jsonObject.optString("returnCode").equals("10000")) {

                payUrl = jsonObject.optString("url");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return payUrl;

    }
}
