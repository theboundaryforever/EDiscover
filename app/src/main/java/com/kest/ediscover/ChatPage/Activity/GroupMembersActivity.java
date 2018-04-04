package com.kest.ediscover.ChatPage.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.kest.ediscover.BaseActivitys;
import com.kest.ediscover.ChatPage.Adapter.GroupDetailsAdapter;
import com.kest.ediscover.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018\4\3 0003.
 * 查看群成员页面
 */

public class GroupMembersActivity extends BaseActivitys{

    private RecyclerView recyclerView;
    private GroupDetailsAdapter groupDetailsAdapter;
    private List<String> Slist = new ArrayList<>();

    private String groupId;
    private TextView tv_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groupmembers_layout);
        init();
    }

    /**初始化*/
    private void init(){

        this.findViewById(R.id.img_title1_left).setVisibility(View.VISIBLE);
        this.findViewById(R.id.img_title1_left).setOnClickListener(this);

        recyclerView = (RecyclerView)this.findViewById(R.id.groupmembers_recyclerview);
        tv_title = (TextView)this.findViewById(R.id.txt_title1_title);
        groupDetailsAdapter = new GroupDetailsAdapter(this,R.layout.groupdetails_item);
        groupId = getIntent().getStringExtra("groupId");

        assigment();
    }

    /**赋值*/
    private void assigment(){

        for (int i = 0; i < 68; i++) {
            Slist.add(""+i);
        }

        EMGroup group = EMClient.getInstance().groupManager().getGroup(groupId);

        tv_title.setText("群成员 ("+group.getMemberCount()+")");

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,5);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(groupDetailsAdapter);
        groupDetailsAdapter.setList(Slist);

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch(view.getId()) {
            case R.id.img_title1_left: //返回
                finish();
                break;
        }
    }
}
