package com.kest.ediscover.ChatPage.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.kest.ediscover.R;
import com.kest.ediscover.WebAppActivity;
import com.kest.ediscover.FriendPage2.Activity.FriendListActivity;
import com.kest.ediscover.HomePage.Activity.HomeActivity;
import com.kest.ediscover.utils.SharePreferenceUtil;

/**
 * Created by Administrator on 2018\3\26 0026.
 * E信
 */
public class ChatListActivity extends FragmentActivity implements View.OnClickListener{

    private EaseConversationListFragment easeConversationListFragment;
    private SharePreferenceUtil sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatlist_layout);
        init();
    }

    /**初始化*/
    private void init(){

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        easeConversationListFragment = new EaseConversationListFragment();
        transaction.add(R.id.chatlist_framelayout,easeConversationListFragment);
        transaction.commit();

        sp = SharePreferenceUtil.getInstance(this);

        this.findViewById(R.id.purse_btn).setOnClickListener(this);
        this.findViewById(R.id.daybuy_btn2).setOnClickListener(this);
        this.findViewById(R.id.city_btn2).setOnClickListener(this);
        this.findViewById(R.id.allbuy_btn).setOnClickListener(this);


        easeConversationListFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {
                Log.d("会话点击事件", EaseConstant.EXTRA_USER_ID + " " + conversation.conversationId() + conversation.getType().name());
                startActivity(new Intent(ChatListActivity.this, ChatActivity
                        .class).putExtra(EaseConstant.EXTRA_USER_ID, conversation.conversationId()));
            }
        });

        //startActivity(new Intent(ChatListActivity.this, ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, getIntent().getExtras()));
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
        }
    }
}
