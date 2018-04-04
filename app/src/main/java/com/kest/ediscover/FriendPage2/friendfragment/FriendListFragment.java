package com.kest.ediscover.FriendPage2.friendfragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.hyphenate.easeui.domain.UserClass2;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.kest.ediscover.ChatPage.Activity.GroupChatActivity;
import com.kest.ediscover.FriendPage2.Activity.NewFriendsActivity;
import com.kest.ediscover.FriendPage2.Activity.SelectContactActivity;
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
 * Created by Administrator on 2018\4\2 0002.
 * 通讯录
 */

public class FriendListFragment extends EaseContactListFragment implements View.OnClickListener,HttpUtils.ICallback{


    private SharePreferenceUtil sp;
    private List<UserClass2> Ulist = new ArrayList<>();

    @Override
    protected void initView() {
        super.initView();
        @SuppressLint("InflateParams") View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.friendlist_send1, null);

        headerView.findViewById(R.id.layout_newfriends).setOnClickListener(this);
        headerView.findViewById(R.id.layout_creategroup).setOnClickListener(this);
        headerView.findViewById(R.id.layout_megroup).setOnClickListener(this);

        listView.addHeaderView(headerView);
        sp = SharePreferenceUtil.getInstance(getActivity());
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
        switch (view.getId()){
            case R.id.layout_newfriends:  //新朋友
                startActivity(new Intent(getActivity(), NewFriendsActivity.class));
                break;
            case R.id.layout_creategroup:  //创建群聊
                startActivity(new Intent(getActivity(), SelectContactActivity.class));
                break;
            case R.id.layout_megroup:  //我加入的群
                startActivity(new Intent(getActivity(), GroupChatActivity.class));
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
                                contactListLayout.init2(Ulist);
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
