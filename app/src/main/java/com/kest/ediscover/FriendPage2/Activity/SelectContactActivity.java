package com.kest.ediscover.FriendPage2.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroupOptions;
import com.hyphenate.easeui.adapter.EaseContactAdapter3;
import com.hyphenate.easeui.domain.UserClass2;
import com.hyphenate.easeui.widget.EaseContactList;
import com.kest.ediscover.CustomControl.Dialog1;
import com.kest.ediscover.CustomControl.Dialog2;
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

public class SelectContactActivity extends Activity implements View.OnClickListener,HttpUtils.ICallback,Dialog2.DialogOnItemClickListener {

    private RecyclerView recyclerView;
    private List<Integer> Ilist = new ArrayList<>();
    private SharePreferenceUtil sp;
    private List<UserClass2> Ulist = new ArrayList<>();
    private ListView listView;
    private EaseContactAdapter3 adapter3;
    private Dialog2 dialog2;

    private TextView tv_title,txt_title_right;
    private EaseContactList selectContact_contaclist;
    private SelectCintactAdapter selectCintactAdapter;

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
        this.findViewById(R.id.txt_title1_right).setOnClickListener(this);

        recyclerView = (RecyclerView)this.findViewById(R.id.selectcontact_recyclerview);
        tv_title = (TextView)this.findViewById(R.id.txt_title1_title);
        txt_title_right = (TextView)this.findViewById(R.id.txt_title1_right);
        selectContact_contaclist = (EaseContactList)this.findViewById(R.id.selectContact_contaclist);
        listView = selectContact_contaclist.getListView();
        adapter3 = selectContact_contaclist.getAdapter3();
        dialog2 = new Dialog2(this);
        dialog2.setOnItemClickListener(this);

        assigment();
    }

    /**赋值*/
    private void assigment(){

        for (int i = 0; i < 5; i++) {
            Ilist.add(i);
        }
        tv_title.setText("选择联系人");
        txt_title_right.setText("完成");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        selectCintactAdapter = new SelectCintactAdapter(this, LayoutInflater.from(this),new ArrayList<UserClass2>());
        recyclerView.setAdapter(selectCintactAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("listview点击的",""+i);
                if(Ulist.get(i).getSign().equals("0")){
                    Ulist.get(i).setSign("1");
                } else if(Ulist.get(i).getSign().equals("1")){
                    Ulist.get(i).setSign("0");
                }
                adapter3.setList3(Ulist);
                selectCintactAdapter.setList(Ulist);
            }
        });

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
            case R.id.txt_title1_right:  //完成
                dialog2.showAtLocation(this.findViewById(R.id.layout_selectcontent), Gravity.CENTER,0,0);
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
                                us.setSign("0");
                                Ulist.add(us);
                            }
                            if (Ulist.size() > 0) {
                                selectContact_contaclist.init3(Ulist);
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

    @Override
    public void setOnItemClick(View v) {
        dialog2.dismiss();
        switch(v.getId()){
            case R.id.tv_cancel:  //取消
                dialog2.dismiss();
                break;
            case R.id.tv_confirm:  //确认
                if(dialog2.getEditTextname().length()>0){
                    CreateGroup(dialog2.getEditTextname(),dialog2.getEditTextcontent());
                }else{
                    MyApplication.setToast("群名称不能为空");
                }
                break;
        }
    }

    /**创建群聊方法*/
    private void CreateGroup(final String groupname,final String groupcontent){

        new Thread(new Runnable() {
            @Override
            public void run() {

                final String groupName = groupname;
                String desc = groupcontent;
                List<UserClass2> Ulist2 = new ArrayList<>();
                for (int i = 0; i < Ulist.size(); i++) {
                    if(Ulist.get(i).getSign().equals("1")){
                        Ulist2.add(Ulist.get(i));
                    }
                }
                String[] members = new String[Ulist2.size()];
                for (int i = 0; i < Ulist2.size(); i++) {
                    members[i] = Ulist2.get(i).getHx_username();
                }
                try{
                    EMGroupOptions options = new EMGroupOptions();
                    options.maxUsers = 200;  //设置群人数
                    options.inviteNeedConfirm = false;

                    String reason = SelectContactActivity.this.getString(R.string.invite_join_group);
                    reason = EMClient.getInstance().getCurrentUser() + reason + groupName;

                    EMClient.getInstance().groupManager().createGroup(groupName,desc,members,reason,options);
                    startActivity(new Intent(SelectContactActivity.this,FriendListActivity.class));
                    finish();
                }catch(Exception e){
                    e.printStackTrace();
                    MyApplication.setToast("创建群组异常,请联系客服");
                }
            }
        }).start();

    }

}
