package com.kest.ediscover.ChatPage.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

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

        assigment();
    }

    /**赋值*/
    private void assigment(){

        for (int i = 0; i < 3; i++) {
            Slist.add(i+"");
        }

        tv_title.setText("我加入的群聊");
        listView.setAdapter(new GroupChatAdapter(this,R.layout.groupchat_item,Slist));

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
