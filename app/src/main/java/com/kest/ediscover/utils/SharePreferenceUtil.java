package com.kest.ediscover.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by dk on 2017/12/18.
 */

public class SharePreferenceUtil {
    private static final String PREFS_NAME = "MyPrefsFile";

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    private static SharePreferenceUtil instance;

    private SharePreferenceUtil() {

    }

    public static SharePreferenceUtil getInstance(Context context) {
        if (null == instance) {
            instance = new SharePreferenceUtil();
            instance.sp = context.getSharedPreferences(PREFS_NAME,
                    Context.MODE_PRIVATE);
            instance.editor = instance.sp.edit();
        }
        return instance;
    }

    public void setcid(String id) {
        editor.putString("cid", id);
        editor.commit();
    }

    public String getcid() {
        return sp.getString("cid", "");
    }

    public void setDeviceId(String id) {
        editor.putString("deviceId", id);
        editor.commit();
    }

    public String getDeviceId() {
        return sp.getString("deviceId", "");
    }


    public void setLoginStaus(boolean loginStatus) {
        editor.putBoolean("status", loginStatus);
        editor.commit();
    }

    public boolean getLoginStatus() {
        return sp.getBoolean("status", false);
    }


    public void setLoginName(String name) {
        editor.putString("login_name", name);
        editor.commit();
    }

    public String getLoginName() {
        return sp.getString("login_name", "");
    }


    public void setPassword(String password) {
        editor.putString("password", password);
        editor.commit();
    }

    public String getPassword() {
        return sp.getString("password", "");
    }


    public void setToken(String token) {
        editor.putString("token", token);
        editor.commit();
    }

    public String getToken() {
        return sp.getString("token", "");
    }

    public void setImgUrl(String url) {
        editor.putString("imgurl", url);
        editor.commit();
    }

    public String getImgUrl() {
        return sp.getString("imgurl", "");
    }


    public void setUserName(String userName) {
        editor.putString("user_name", userName);
        editor.commit();
    }

    public String getUserName() {
        return sp.getString("user_name", "");
    }

    public void setMobile(String mobile) {
        editor.putString("mobile", mobile);
        editor.commit();
    }

    public String getMobile() {
        return sp.getString("mobile", "");
    }

    public void setDepartment(String userName) {
        editor.putString("department", userName);
        editor.commit();
    }

    public void setShareUrl(String url) {
        editor.putString("share_url", url);
        editor.commit();
    }

    public String getShareUrl() {
        return sp.getString("share_url", "");
    }
    public void setShareTitle(String url) {
        editor.putString("share_title", url);
        editor.commit();
    }

    public String getShareTitle() {
        return sp.getString("share_title", "");
    }


    public String getDepartment() {
        return sp.getString("department", "");
    }

    public void setDepartmentId(String depId) {
        editor.putString("departmentID", depId);
        editor.commit();
    }

    public String getDepartmentId() {
        return sp.getString("departmentID", "");
    }

    public void setLevel(String level) {
        editor.putString("level", level);
        editor.commit();
    }

    public String getLevel() {
        return sp.getString("level", "");
    }


    public void setTel(String tel) {
        editor.putString("tel", tel);
        editor.commit();
    }

    public String getTel() {
        return sp.getString("tel", "");
    }

    public void setPhone(String phone) {
        editor.putString("mobile_phone", phone);
        editor.commit();
    }

    public String getPhone() {
        return sp.getString("mobile_phone", "");
    }

    public void setEmail(String email) {
        editor.putString("email", email);
        editor.commit();
    }

    public String getEmail() {
        return sp.getString("email", "");
    }

    public void setRemark(String remark) {
        editor.putString("remark", remark);
        editor.commit();
    }

    public String getRemark() {
        return sp.getString("remark", "");
    }

    public void setType(String type) {
        editor.putString("type", type);
        editor.commit();
    }

    public String getType() {
        return sp.getString("type", "");
    }

}

