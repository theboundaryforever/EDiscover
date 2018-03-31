package com.kest.ediscover.FriendPage2.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.easeui.domain.UserClass2;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.easeui.widget.EaseContactList;
import com.kest.ediscover.ChatPage.Activity.ChatListActivity;
import com.kest.ediscover.ChatPage.Activity.GroupChatActivity;
import com.kest.ediscover.MyApplication;
import com.kest.ediscover.MyHttputils.Conntent;
import com.kest.ediscover.MyHttputils.HttpUtils;
import com.kest.ediscover.R;
import com.kest.ediscover.WebAppActivity;
import com.kest.ediscover.HomePage.Activity.HomeActivity;
import com.kest.ediscover.utils.SharePreferenceUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018\3\27 0027.
 * 朋友列表界面
 */

public class FriendListActivity extends FragmentActivity implements View.OnClickListener,HttpUtils.ICallback{

    private EaseContactListFragment easeContactListFragment = new EaseContactListFragment();
    private SharePreferenceUtil sp;
    private EaseContactList easeContactList;
    private List<UserClass2> Ulist = new ArrayList<>();


    protected ListView listView;

    private TextView txt_title;
    private RelativeLayout layout_newfriends;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friendlist_layout);
        init();
    }

    /**初始化*/
    private void init(){

        sp = SharePreferenceUtil.getInstance(this);

        this.findViewById(R.id.purse_btn).setOnClickListener(this);
        this.findViewById(R.id.Chatlist_btn).setOnClickListener(this);
        this.findViewById(R.id.city_btn2).setOnClickListener(this);
        this.findViewById(R.id.allbuy_btn).setOnClickListener(this);
        this.findViewById(R.id.layout_newfriends).setOnClickListener(this);
        this.findViewById(R.id.view_search).setOnClickListener(this);
        this.findViewById(R.id.layout_megroup).setOnClickListener(this);
        this.findViewById(R.id.layout_creategroup).setOnClickListener(this);

        txt_title = (TextView)this.findViewById(R.id.txt_title1_title);

        easeContactList = (EaseContactList)this.findViewById(R.id.friendlist_contaclist);

        listView = easeContactList.getListView();

        assigment();
        getSelectFriendlist();
    }

    /**查寻通讯录*/
    private void getSelectFriendlist(){
        Map<String,Object> map = new HashMap<>();
        map.put("token",sp.getToken());
        map.put("action","user_contacts");
        HttpUtils.getInstance().post(Conntent.HTTPURL+Conntent.EASEMOBFRIENDLIST,map,this);
    }

    /**赋值*/
    private void assigment(){

        txt_title.setText("通讯录");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("通讯录-item",Ulist.get(i).getUsername());
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.purse_btn:   //首页
                startActivity(new Intent(FriendListActivity.this, HomeActivity.class));
                finish();
                break;
            case R.id.allbuy_btn:  //E发现
                Intent intent1 = new Intent(FriendListActivity.this, WebAppActivity.class);
                intent1.putExtra("address","http://pay.allbuy.me/Home/BusinessAccount/index/token/" + sp.getToken());
                startActivity(intent1);
                break;
            case R.id.Chatlist_btn:  //E信
                startActivity(new Intent(FriendListActivity.this, ChatListActivity.class));
                finish();
                break;
            case R.id.city_btn2:  //我的
                Intent intent = new Intent(FriendListActivity.this, WebAppActivity.class);
                intent.putExtra("address","http://pay.allbuy.me/index.php?m=Home&c=Index&a=index&token=" + sp.getToken());
                startActivity(intent);
                break;
            case R.id.layout_newfriends:  //新朋友
                startActivity(new Intent(FriendListActivity.this,NewFriendsActivity.class));
                break;
            case R.id.view_search:  //搜索好友
                startActivity(new Intent(FriendListActivity.this,SearchActivity.class));
                break;
            case R.id.layout_creategroup:  //创建群
                startActivity(new Intent(FriendListActivity.this, SelectContactActivity.class));
                break;
            case R.id.layout_megroup:  //我加入的群
                startActivity(new Intent(FriendListActivity.this, GroupChatActivity.class));
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
                                easeContactList.init2(Ulist);
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
