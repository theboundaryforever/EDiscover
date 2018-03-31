package com.kest.ediscover.FriendPage2.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;

import com.hyphenate.easeui.domain.UserClass2;
import com.kest.ediscover.CustomControl.Mylistview;
import com.kest.ediscover.FriendPage2.friendadapter.NewFriendAdapter;
import com.kest.ediscover.FriendPage2.friendclass.UserClass;
import com.kest.ediscover.MyApplication;
import com.kest.ediscover.MyHttputils.Conntent;
import com.kest.ediscover.MyHttputils.HttpUtils;
import com.kest.ediscover.R;
import com.kest.ediscover.utils.SharePreferenceUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018\3\30 0030.
 * 搜索好友界面
 */

public class SearchActivity extends Activity implements View.OnClickListener,HttpUtils.ICallback{

    private Mylistview search_listview;
    private List<String> Slist = new ArrayList<>();
    private List<UserClass> Ulist = new ArrayList<>();
    private ScrollView sc;
    private SharePreferenceUtil sp;
    private EditText ed_content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);
        init();
    }

    /**初始化*/
    private void init(){

        this.findViewById(R.id.tv_cancel).setOnClickListener(this);
        this.findViewById(R.id.im_search).setOnClickListener(this);
        this.findViewById(R.id.im_delete).setOnClickListener(this);

        search_listview = (Mylistview)this.findViewById(R.id.search_listview);
        sc = (ScrollView)this.findViewById(R.id.search_scroll);
        ed_content = (EditText)this.findViewById(R.id.ed_content);

        sp = SharePreferenceUtil.getInstance(this);

        assigment();
    }

    /**赋值*/
    private void assigment(){

        sc.smoothScrollTo(0,20);

        for (int i = 0; i < 15; i++) {
            Slist.add(""+i);
        }



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_cancel:  //取消
                finish();
                break;
            case R.id.im_search:  //搜索
                if(ed_content.getText().toString().trim().length()>0) {
                    Selectfriend();
                }else{
                    MyApplication.setToast("搜索条件不能为空");
                }
                break;
            case R.id.im_delete:  //删除
                ed_content.setText("");
                break;
        }
    }

    /**搜索好友*/
    private void Selectfriend(){

        Map<String,Object> map = new HashMap<>();
        map.put("token",sp.getToken());
        map.put("action","user_search");
        map.put("searchText",ed_content.getText().toString());
        HttpUtils.getInstance().post(Conntent.CURRENCY,map,this);
    }

    @Override
    public void onSuccess(String url, String result) {
        try{
            if(result.length()>0) {
                Log.d("搜索页面返回值", "" + result);
                JSONObject js = new JSONObject(result);
                if (js.getString("action").equals("user_search")) {  //搜索用户
                    if (js.getInt("returnCode") == 10000) {
                        Ulist.removeAll(Ulist);
                    Log.d("搜索用户返回值", "" + result);
                    JSONArray ja = js.getJSONArray("userList");
                    if (ja.length() > 0) {
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject jo = ja.getJSONObject(i);
                            UserClass us = new UserClass();
                            us.setUser_id(jo.getString("user_id"));
                            us.setUsername(jo.getString("username"));
                            us.setHx_username(jo.getString("hx_username"));
                            us.setNickname(jo.getString("nickname"));
                            us.setImg(jo.getString("img"));
                            us.setIs_friend(jo.getInt("is_friend"));
                            Ulist.add(us);
                        }
                        search_listview.setAdapter(new NewFriendAdapter(Ulist, this));
                        search_listview.setVisibility(View.VISIBLE);
                        this.findViewById(R.id.tv_null).setVisibility(View.GONE);
                    }else{
                        search_listview.setVisibility(View.GONE);
                        this.findViewById(R.id.tv_null).setVisibility(View.VISIBLE);
                    }
                }
              }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
