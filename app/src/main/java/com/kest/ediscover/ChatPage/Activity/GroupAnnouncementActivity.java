package com.kest.ediscover.ChatPage.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.kest.ediscover.BaseActivitys;
import com.kest.ediscover.R;
import com.kest.ediscover.utils.SharePreferenceUtil;

/**
 * Created by Administrator on 2018\4\3 0003.
 * 群公告页面
 */

public class GroupAnnouncementActivity extends BaseActivitys{

    private EditText editText;
    private TextView tv_text,tv_title,tv_title_right,tv_content;

    private CharSequence wordNum;
    private SharePreferenceUtil sp;
    private String groupId,countent,Sgroupname,groupname;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case 1:
                    if(Sgroupname.equals(groupname)){
                        editText.setVisibility(View.GONE);
                        tv_text.setVisibility(View.GONE);
                        tv_content.setVisibility(View.VISIBLE);
                        tv_title_right.setVisibility(View.INVISIBLE);
                        tv_content.setText(countent);
                    }else{
                        editText.setText(countent);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groupannouncement_layout);
        init();
    }

    /**初始化*/
    private void init(){

        this.findViewById(R.id.img_title1_left).setVisibility(View.VISIBLE);
        this.findViewById(R.id.img_title1_left).setOnClickListener(this);
        this.findViewById(R.id.txt_title1_right).setOnClickListener(this);

        editText = (EditText)this.findViewById(R.id.ed_content);
        tv_text = (TextView)this.findViewById(R.id.tv_text);
        tv_title = (TextView)this.findViewById(R.id.txt_title1_title);
        tv_title_right = (TextView)this.findViewById(R.id.txt_title1_right);
        tv_content = (TextView)this.findViewById(R.id.tv_content);


        assigmen();
    }


    /**赋值*/
    private void assigmen(){

        groupId = getIntent().getStringExtra("groupId");
        sp = SharePreferenceUtil.getInstance(this);

        tv_title.setText("群公告");
        tv_title_right.setText("完成");
        Sgroupname = sp.getHX_username();

        getGroupAnnouncement();

        editText.addTextChangedListener(new TextWatcher() {  //及时监听输入框内字数
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                wordNum = charSequence;
            }

            @Override
            public void afterTextChanged(Editable editable) {
                tv_text.setText(wordNum.length()+"/40");
            }
        });

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch(view.getId()){
            case R.id.img_title1_left:  //返回
                finish();
                break;
            case R.id.txt_title1_right:  //确认
                setGroupAnnouncement();
                break;
        }
    }

    /**设置群公告*/
    private void setGroupAnnouncement(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().groupManager().updateGroupAnnouncement(groupId, editText.getText().toString());
                    finish();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**获取群公告*/
    private void getGroupAnnouncement(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    countent = EMClient.getInstance().groupManager().fetchGroupAnnouncement(groupId).toString();
                    EMGroup group = EMClient.getInstance().groupManager().getGroup(groupId);
                    groupname = group.getGroupName();
                    Log.d("群公告",countent);
                    Message mes = handler.obtainMessage();
                    mes.what = 1;
                    handler.sendMessage(mes);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
