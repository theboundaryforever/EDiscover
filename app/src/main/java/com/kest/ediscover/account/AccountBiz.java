package com.kest.ediscover.account;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;

import com.kest.ediscover.Bean.AccountBean;
import com.kest.ediscover.Bean.PlatformBean;
import com.kest.ediscover.Bean.Response;
import com.kest.ediscover.network.MessageDispatcher;
import com.kest.ediscover.utils.DebugLog;
import com.kest.ediscover.utils.SharePreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dk on 2017/12/18.
 */

public class AccountBiz {

    private Context context;

    private SharePreferenceUtil sp = null;

    /**登录*/
    public static final String USER_LOGIN_URI = MessageDispatcher.BASE_API_URI + "index.php?m=AppApi&c=Index&a=login";

    private static final String Get_Platform_List_Url = MessageDispatcher.BASE_API_URI + "index.php?m=AppApi&c=BusinessAccount&a=get_list";
    private static final String Get_Follow_Paltform_List_Url = MessageDispatcher.BASE_API_URI +"index.php?m=AppApi&c=UserFollowAccount&a=get_list";
    private static final String Get_Account_List_Url = MessageDispatcher.BASE_API_URI + "index.php?m=AppApi&c=UserPlatform&a=get_list";
    private static final String BindAccount_Url = MessageDispatcher.BASE_API_URI + "index.php?m=AppApi&c=UserPlatform&a=add_new";
    /**刷新token*/
    private static final String Refresh_Token_Url = MessageDispatcher.BASE_API_URI + "index.php?m=AppApi&c=Users&a=refresh_login";
    private static final String Delete_Account_Url = MessageDispatcher.BASE_API_URI + "index.php?m=AppApi&c=UserPlatform&a=delete";

    /**短信验证码*/
    private static final String Get_Sms_Code = MessageDispatcher.BASE_API_URI + "index.php?m=AppApi&c=Sms&a=send_code";
    /**注册*/
    private static final String Register_Url = MessageDispatcher.BASE_API_URI + "index.php?m=AppApi&c=Index&a=register";
    /**找回密码*/
    private static final String Find_Password_Url = MessageDispatcher.BASE_API_URI + "index.php?m=AppApi&c=Index&a=find_password";


    private static final  String Un_Follew_Url = MessageDispatcher.BASE_API_URI + "index.php?m=AppApi&c=UserFollowAccount&a=unfollow";




    public AccountBiz(Context context) {
        this.context = context;
        sp = SharePreferenceUtil.getInstance(context);

    }

    public boolean login(String phoneNum, String password,String cid) {

        Map<String, Object> params = new HashMap<String, Object>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        params.put("requestDate", df.format(new Date()));
        params.put("name", phoneNum);
        params.put("password", password);
        //params.put("cid", "1");
        if (cid!="" && cid != null && cid !="null") {
            params.put("cid", cid);
        }

        try {
            String response = MessageDispatcher.JsonPost(USER_LOGIN_URI, params);
            JSONObject jsonObject = new JSONObject(response);

            DebugLog.i("登录返回"+response);

            //JSONObject responseBody = new JSONObject(jsonObject.optString("returnBody"));
            if (jsonObject.optString("returnCode").equals("10000")) {


                sp.setLoginStaus(true);
                //sp.setToken(obj.getString("token"));
                sp.setMobile(jsonObject.optString("mobile"));
                sp.setUserName(jsonObject.optString("uname"));
                sp.setToken(jsonObject.optString("token"));
                sp.setImgUrl(jsonObject.optString("img"));

                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public List<PlatformBean> getPlatformtList(String name, String currrentPage, String pageSize) {

        List<PlatformBean> list = new ArrayList<>();
        Map<String, Object> params = new HashMap<String, Object>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        params.put("requestDate", df.format(new Date()));
        params.put("token", sp.getToken());

        if (name != "") {
            params.put("name", name);
        }
        params.put("currrentPage", currrentPage);
        params.put("pageSize", pageSize);
        //params.put("cid", DeviceUtils.getDeviceId(context));

        String response = MessageDispatcher.JsonPost(Get_Follow_Paltform_List_Url, params);
        JSONObject jsonObject = null;
        try {
            DebugLog.i("获取平台请求返回：" + response);

            jsonObject = new JSONObject(response);

            if (jsonObject.optString("returnCode").equals("10000")) {
                JSONArray array = jsonObject.optJSONArray("pageList");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject o = array.getJSONObject(i);
                    String temp_id = o.getString("id");
                    String temp_bid = o.optString("bid");
                    String temp_name = o.optString("name");
                    list.add(new PlatformBean(temp_id, temp_bid, temp_name));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    public List<AccountBean> getAccountList(String name, String currrentPage, String pageSize) {
        DebugLog.i("开始获取AccountList");
        List<AccountBean> list = new ArrayList<>();
        Map<String, Object> params = new HashMap<String, Object>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        params.put("requestDate", df.format(new Date()));
        params.put("token", sp.getToken());
        if (name != "") {
            params.put("name", name);
        }
        params.put("currrentPage", currrentPage);
        params.put("pageSize", pageSize);

        String response = MessageDispatcher.JsonPost(Get_Account_List_Url, params);
        JSONObject jsonObject = null;
        try {
            DebugLog.i("获取AccountList 请求返回：" + response);
            jsonObject = new JSONObject(response);


            if (jsonObject.optString("returnCode").equals("10000")) {
                JSONArray array = jsonObject.optJSONArray("pageList");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject o = array.getJSONObject(i);
                    list.add(new AccountBean(o.optString("id"), o.optString("bid"), o.optString("name"), o.optString("busname"), o.optString("url"), o.optString("ptoken")));
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return list;
    }

    public boolean bindAccount(String bid, String name, String password) {
        DebugLog.i("开始绑定账号");

        Map<String, Object> params = new HashMap<String, Object>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        params.put("requestDate", df.format(new Date()));
        params.put("token", sp.getToken());

        params.put("bid", bid);
        params.put("name", name);
        params.put("password", password);
       // params.put("trpassword", trpassword);


        String response = MessageDispatcher.JsonPost(BindAccount_Url, params);
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

    public boolean deleteAccount(String id) {

        Map<String, Object> params = new HashMap<String, Object>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        params.put("requestDate", df.format(new Date()));
        params.put("token", sp.getToken());

        params.put("id", id);

        String response = MessageDispatcher.JsonPost(Delete_Account_Url, params);
        JSONObject jsonObject = null;
        try {
            DebugLog.i("删除账号请求返回：" + response);
            jsonObject = new JSONObject(response);
            if (jsonObject.optInt("returnCode") == 10000) {
                return true;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean deletePlatform(String id) {
        DebugLog.i("开始取消关注平台");

        Map<String, Object> params = new HashMap<String, Object>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        params.put("requestDate", df.format(new Date()));
        params.put("token", sp.getToken());

        params.put("id", id);

        String response = MessageDispatcher.JsonPost(Un_Follew_Url, params);
        JSONObject jsonObject = null;
        try {
            DebugLog.i("取消关注平台请求返回：" + response);
            jsonObject = new JSONObject(response);
            if (jsonObject.optInt("returnCode") == 10000) {
                return true;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }


    public Response getSmsCode(String mobile, String flag) {// Register=未注册，forget=已注册
        Response res = new Response();
        Map<String, Object> params = new HashMap<String, Object>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        params.put("requestDate", df.format(new Date()));

        params.put("mobile", mobile);
        params.put("flag", flag);


        String response = MessageDispatcher.JsonPost(Get_Sms_Code, params);
        JSONObject jsonObject = null;
        try {
            DebugLog.i("获取短信验证码：" + response);
            jsonObject = new JSONObject(response);
            res.setCode(jsonObject.optInt("returnCode"));
            res.setErrorMessage(jsonObject.optString("returnMsg"));
            if (jsonObject.optInt("returnCode") == 10000) {
                res.setData(jsonObject.optString("code"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return res;
    }

    public boolean register(String name, String mobile, String code, String password, String cid) {

        Map<String, Object> params = new HashMap<String, Object>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        params.put("requestDate", df.format(new Date()));

        params.put("name", name);
        params.put("mobile", mobile);
        params.put("code", code);
        params.put("password", password);
        params.put("cide", "001");


        String response = MessageDispatcher.JsonPost(Register_Url, params);
        JSONObject jsonObject = null;
        try {
            DebugLog.i("注册接口返回：" + response);
            jsonObject = new JSONObject(response);
            if (jsonObject.optInt("returnCode") == 10000) {
                return true;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean findPassword( String mobile, String code, String password, String cid) {

        Map<String, Object> params = new HashMap<String, Object>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        params.put("requestDate", df.format(new Date()));

        params.put("mobile", mobile);
        params.put("code", code);
        params.put("password", password);
        params.put("cide", "001");


        String response = MessageDispatcher.JsonPost(Find_Password_Url, params);
        JSONObject jsonObject = null;
        try {
            DebugLog.i("找回密码接口返回：" + response);
            jsonObject = new JSONObject(response);
            if (jsonObject.optInt("returnCode") == 10000) {
                return true;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
    public String refreshToken( String token) {

        Map<String, Object> params = new HashMap<String, Object>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        params.put("requestDate", df.format(new Date()));

        params.put("token", token);


        String response = MessageDispatcher.JsonPost(Refresh_Token_Url, params);
        JSONObject jsonObject = null;
        try {
            DebugLog.i("刷新token成功：" + response);
            jsonObject = new JSONObject(response);
            if (jsonObject.optInt("returnCode") == 10000) {
               return  jsonObject.optString("token");
            }else{
                return "error";
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "error";
    }



}
