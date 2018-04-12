package com.kest.ediscover.ChatPage.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.kest.ediscover.FriendPage2.Activity.SearchActivity;
import com.kest.ediscover.MyApplication;
import com.kest.ediscover.R;
import com.kest.ediscover.WebAppActivity;
import com.kest.ediscover.FriendPage2.Activity.FriendListActivity;
import com.kest.ediscover.HomePage.Activity.HomeActivity;
import com.kest.ediscover.utils.SharePreferenceUtil;

import java.util.List;

/**
 * Created by Administrator on 2018\3\26 0026.
 * E信
 */
public class ChatListActivity extends FragmentActivity implements View.OnClickListener{

    private EaseConversationListFragment easeConversationListFragment;
    private SharePreferenceUtil sp;

    private TextView txt_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatlist_layout);
        init();
    }

    /**初始化*/
    private void init(){

        this.findViewById(R.id.purse_btn).setOnClickListener(this);
        this.findViewById(R.id.daybuy_btn2).setOnClickListener(this);
        this.findViewById(R.id.city_btn2).setOnClickListener(this);
        this.findViewById(R.id.allbuy_btn).setOnClickListener(this);
        this.findViewById(R.id.view_search).setOnClickListener(this);

        txt_title = (TextView)this.findViewById(R.id.txt_title1_title);
        sp = SharePreferenceUtil.getInstance(this);


        assigment();
    }

    /**赋值*/
    private void assigment(){

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        easeConversationListFragment = new EaseConversationListFragment();
        transaction.add(R.id.chatlist_framelayout,easeConversationListFragment);
        transaction.commit();
        txt_title.setText("E信");

        easeConversationListFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {
                Log.d("会话点击事件", EaseConstant.EXTRA_USER_ID + " " + conversation.conversationId() + conversation.getType().name());
                startActivity(new Intent(ChatListActivity.this, ChatActivity
                        .class).putExtra(EaseConstant.EXTRA_USER_ID, conversation.conversationId()).putExtra("chatType",1
                ));
            }
        });


        EMMessageListener emMessageListener = new EMMessageListener() {
            @Override
            public void onMessageReceived(List<EMMessage> list) {
                refreshUIWithMessage();
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> list) {

            }

            @Override
            public void onMessageRead(List<EMMessage> list) {

            }

            @Override
            public void onMessageDelivered(List<EMMessage> list) {

            }

            @Override
            public void onMessageRecalled(List<EMMessage> list) {

            }

            @Override
            public void onMessageChanged(EMMessage emMessage, Object o) {

            }
        };

        EMClient.getInstance().chatManager().addMessageListener(emMessageListener);

    }



    private void refreshUIWithMessage() {
        runOnUiThread(new Runnable() {
            public void run() {
                    if (easeConversationListFragment != null) {
                        easeConversationListFragment.refresh();
                    }
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.purse_btn:  //首页
                startActivity(new Intent(ChatListActivity.this, HomeActivity.class));
                break;
            case R.id.allbuy_btn:  //E发现
                Intent intent1 = new Intent(ChatListActivity.this, WebAppActivity.class);
                intent1.putExtra("address","http://pay.allbuy.me/Home/BusinessAccount/index/token/" + sp.getToken());
                startActivity(intent1);
                break;
            case R.id.daybuy_btn2:   //朋友
                startActivity(new Intent(ChatListActivity.this, FriendListActivity.class));
                break;
            case R.id.city_btn2:  //我的
                Intent intent = new Intent(ChatListActivity.this, WebAppActivity.class);
                intent.putExtra("address","http://pay.allbuy.me/index.php?m=Home&c=Index&a=index&token=" + sp.getToken());
                startActivity(intent);
                break;
            case R.id.view_search: //搜索好友
                startActivity(new Intent(ChatListActivity.this,SearchActivity.class));
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
}
