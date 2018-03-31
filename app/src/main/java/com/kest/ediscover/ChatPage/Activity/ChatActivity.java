package com.kest.ediscover.ChatPage.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.hyphenate.easeui.ui.EaseChatFragment;
import com.kest.ediscover.R;

/**
 * Created by Administrator on 2018\3\26 0026.
 * 会话列表
 */

public class ChatActivity extends FragmentActivity {

    private EaseChatFragment chatFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatlayout);

        chatFragment = new EaseChatFragment();
        //传入参数
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.chatlist_frament, chatFragment).commit();
    }

    @Override
    public void onStateNotSaved() {
        super.onStateNotSaved();
    }
}
