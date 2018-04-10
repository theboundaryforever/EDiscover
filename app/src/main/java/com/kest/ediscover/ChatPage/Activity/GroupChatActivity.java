package com.kest.ediscover.ChatPage.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.kest.ediscover.ChatPage.Adapter.GroupChatAdapter;
import com.kest.ediscover.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018\3\31 0031.
 * 群聊列表页面
 */
public class GroupChatActivity  extends Activity implements View.OnClickListener{

    private TextView tv_title;
    private ListView listView;
    private List<String> Slist = new ArrayList<>();
    private List<EMGroup> groupslist;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case 1:
                    assigment();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groupchat_layout);
        init();
    }

    /**初始化*/
    private void init(){

         this.findViewById(R.id.img_title1_left).setVisibility(View.VISIBLE);
         this.findViewById(R.id.img_title1_left).setOnClickListener(this);

        tv_title = (TextView)this.findViewById(R.id.txt_title1_title);
        listView = (ListView)this.findViewById(R.id.groupchat_listview);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    groupslist = EMClient.getInstance().groupManager().getJoinedGroupsFromServer();//需异步处理
                    Message message = handler.obtainMessage();
                    message.what = 1;
                    handler.sendMessage(message);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**赋值*/
    private void assigment(){

        tv_title.setText("我加入的群聊");



        listView.setAdapter(new GroupChatAdapter(this,R.layout.groupchat_item,groupslist));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("群聊列表","进入:"+groupslist.get(i).getGroupName());
                Intent intent = new Intent(GroupChatActivity.this,ChatActivity.class);
                intent.putExtra("chatType",2);
                intent.putExtra("userId",groupslist.get(i).getGroupId());
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_title1_left:  //返回
                finish();
                break;

        }
    }
}
