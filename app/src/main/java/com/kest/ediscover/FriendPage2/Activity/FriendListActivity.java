package com.kest.ediscover.FriendPage2.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.easeui.domain.UserClass2;
import com.hyphenate.easeui.widget.EaseContactList;
import com.kest.ediscover.ChatPage.Activity.ChatListActivity;
import com.kest.ediscover.FriendPage2.friendfragment.FriendListFragment;
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

import butterknife.OnTouch;

/**
 * Created by Administrator on 2018\3\27 0027.
 * 朋友列表界面
 */

public class FriendListActivity extends FragmentActivity implements View.OnClickListener,HttpUtils.ICallback{

    private FriendListFragment friendListFragment = new FriendListFragment();
    private FragmentTransaction fragmentTransaction;
    private SharePreferenceUtil sp;
    private List<UserClass2> Ulist = new ArrayList<>();


    private TextView txt_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friendlist_layout);
        init();

    }

    /**初始化*/
    private void init(){

        sp = SharePreferenceUtil.getInstance(this);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();

        this.findViewById(R.id.purse_btn).setOnClickListener(this);
        this.findViewById(R.id.Chatlist_btn).setOnClickListener(this);
        this.findViewById(R.id.city_btn2).setOnClickListener(this);
        this.findViewById(R.id.allbuy_btn).setOnClickListener(this);
        this.findViewById(R.id.view_search).setOnClickListener(this);

        txt_title = (TextView)this.findViewById(R.id.txt_title1_title);


        assigment();
    }



    /**赋值*/
    private void assigment(){

        txt_title.setText("通讯录");

        fragmentTransaction.add(R.id.friendlist_framelayout,friendListFragment).commit();


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
            case R.id.view_search:  //搜索好友
                startActivity(new Intent(FriendListActivity.this,SearchActivity.class));
                break;

        }
    }

    //记录用户首次点击返回键的时间
    private long firstTime=0;
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                long secondTime=System.currentTimeMillis();
                if(secondTime-firstTime>2000){
                    MyApplication.setToast("再按一次退出程序");
                    firstTime=secondTime;
                    return true;
                }else{
                    System.exit(0);
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
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
