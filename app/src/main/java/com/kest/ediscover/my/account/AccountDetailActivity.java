package com.kest.ediscover.my.account;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.kest.ediscover.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/9 0009.
 */
//账户明细
public class AccountDetailActivity extends AppCompatActivity {
    ImageView iv_back;
    TextView tv_title;
    //筛选
    TextView tv_race;
    RecyclerView rv_detail;
    RecyclerView rv_account;
    private List<String> strings=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_settings_account);
        initView();
        initData();
    }
    private void initView(){
        rv_account=findViewById(R.id.rv_account);
        iv_back=findViewById(R.id.iv_back);
        tv_title=findViewById(R.id.tv_title);
        tv_race=findViewById(R.id.tv_race);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_title.setText("账户明细");
        tv_race.setVisibility(View.VISIBLE);
        tv_race.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPop();
            }
        });
    }
    private void initData(){
        for(int i=0;i<15;i++){
            strings.add("A"+i);
        }
        TestAdapter testAdapter=new TestAdapter(strings);
        rv_account.setAdapter(testAdapter);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_account.setLayoutManager(manager);

    }
    private void showPop(){
        View view= LayoutInflater.from(this).inflate(R.layout.fragment_settings_account_type,null);
        View parentView =LayoutInflater.from(this).inflate(R.layout.fragment_settings_detail_bill,null);
        final PopupWindow popupWindow=new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        if(view.getBackground()!=null){
            view.getBackground().setAlpha(80);
        }
        popupWindow.showAtLocation(parentView, Gravity.BOTTOM,0,0);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(popupWindow!=null && popupWindow.isShowing()){
                    popupWindow.dismiss();
                }
            }
        });
        TextView tv_all=view.findViewById(R.id.tv_all);
        tv_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AccountDetailActivity.this,"toast",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
