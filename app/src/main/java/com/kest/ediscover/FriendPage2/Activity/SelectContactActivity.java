package com.kest.ediscover.FriendPage2.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.hyphenate.easeui.domain.UserClass2;
import com.hyphenate.easeui.widget.EaseContactList;
import com.kest.ediscover.FriendPage2.friendadapter.SelectCintactAdapter;
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
 * Created by Administrator on 2018\3\31 0031.
 * 选择联系人界面
 */

public class SelectContactActivity extends Activity implements View.OnClickListener,HttpUtils.ICallback{

    private RecyclerView recyclerView;
    private List<Integer> Ilist = new ArrayList<>();
    private SharePreferenceUtil sp;
    private List<UserClass2> Ulist = new ArrayList<>();

    private TextView tv_title;
    private EaseContactList selectContact_contaclist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectcontact_layout);
        init();
    }

    /**初始化*/
    private void init(){
        sp = SharePreferenceUtil.getInstance(this);

        this.findViewById(R.id.img_title1_left).setVisibility(View.VISIBLE);
        this.findViewById(R.id.img_title1_left).setOnClickListener(this);

        recyclerView = (RecyclerView)this.findViewById(R.id.selectcontact_recyclerview);
        tv_title = (TextView)this.findViewById(R.id.txt_title1_title);
        selectContact_contaclist = (EaseContactList)this.findViewById(R.id.selectContact_contaclist);



        assigment();
    }

    /**赋值*/
    private void assigment(){

        for (int i = 0; i < 5; i++) {
            Ilist.add(i);
        }
        tv_title.setText("选择联系人");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new SelectCintactAdapter(this, LayoutInflater.from(this),Ilist));

        getSelectFriendlist();
    }

    /**查寻通讯录*/
    private void getSelectFriendlist(){
        Map<String,Object> map = new HashMap<>();
        map.put("token",sp.getToken());
        map.put("action","user_contacts");
        HttpUtils.getInstance().post(Conntent.HTTPURL+Conntent.EASEMOBFRIENDLIST,map,this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.img_title1_left:  //返回
                finish();
                break;
        }
    }

    @Override
    public void onSuccess(String url, String result) {
        if(url.equals(Conntent.HTTPURL+Conntent.EASEMOBFRIENDLIST)){
            if(result.length()>0){
                Log.d("通讯录返回值",""+result);
                try{
                    JSONObject js = new JSONObject(result);
                    if(js.getInt("returnCode")==10000){
                        if(js.getString("action").equals("user_contacts")) {
                            JSONArray ja = js.getJSONArray("userList");
                            for (int i = 0; i < ja.length(); i++) {
                                JSONObject jo = ja.getJSONObject(i);
                                UserClass2 us = new UserClass2();
                                us.setInitialLetter(us.getInitialLetter());
                                us.setHx_username(jo.getString("hx_username"));
                                us.setNickname(jo.getString("nickname"));
                                us.setUser_id(jo.getString("user_id"));
                                us.setUsername(jo.getString("username"));
                                us.setImg(jo.getString("img"));
                                Ulist.add(us);
                            }
                            if (Ulist.size() > 0) {
                                selectContact_contaclist.init2(Ulist);
                            }
                        }
                    }else{
                        MyApplication.setToast(js.getString("returnMsg"));
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
