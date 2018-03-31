package com.kest.ediscover.FriendPage2.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.kest.ediscover.CustomControl.Mylistview;
import com.kest.ediscover.FriendPage2.friendadapter.NewFriendAdapter;
import com.kest.ediscover.FriendPage2.friendclass.UserClass;
import com.kest.ediscover.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018\3\29 0029.
 * 新朋友页面
 */

public class NewFriendsActivity extends Activity implements View.OnClickListener{

    private TextView txt_title1_left,txt_title1_title;
    private Mylistview listView;
    private List<UserClass> Slist = new ArrayList<>();
    private ScrollView sc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newfriends_alyout);
        init();
    }

    /**初始化*/
    private void init(){

        this.findViewById(R.id.layout_title1_left).setOnClickListener(this);
        this.findViewById(R.id.img_title1_left).setVisibility(View.VISIBLE);
        this.findViewById(R.id.view_search).setOnClickListener(this);

        txt_title1_left = (TextView)this.findViewById(R.id.txt_title1_left);
        txt_title1_title = (TextView)this.findViewById(R.id.txt_title1_title);
        listView = (Mylistview)this.findViewById(R.id.newfriends_listview);
        sc = (ScrollView)this.findViewById(R.id.newfriend_scroll) ;

        assigment();
    }

    private void assigment(){

        txt_title1_left.setText("返回");
        txt_title1_title.setText("新朋友");
        sc.smoothScrollTo(0,20);

        for (int i = 0; i < 5; i++) {
            UserClass us = new UserClass();
            us.setUsername("张"+i);
            Slist.add(us);
        }

        listView.setAdapter(new NewFriendAdapter(Slist,this));

    }

    @Override
    public void onClick(View view) {
         switch(view.getId()){
             case R.id.layout_title1_left:  //返回
                 finish();
                 break;
             case R.id.view_search:  //搜索
                 startActivity(new Intent(NewFriendsActivity.this,SearchActivity.class));
                 break;
         }
    }
}
