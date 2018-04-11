package com.kest.ediscover.my.account;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.kest.ediscover.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/4/9 0009.
 */
//账户明细
public class AccountDetailActivity extends AppCompatActivity {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    //筛选
    @BindView(R.id.tv_race)
    TextView tv_race;
    @BindView(R.id.rv_account)
    RecyclerView rv_account;
    private List<String> strings=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_settings_account);
        ButterKnife.bind(this);
        tv_title.setText("账户明细");
        tv_race.setVisibility(View.VISIBLE);
        initData();
    }
    @OnClick({R.id.iv_back,R.id.tv_race})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_race:
                showPop();
                break;
        }
    }

    private void initData(){
        for(int i=0;i<15;i++){
            strings.add("A"+i);
        }
        AccountTypeAdapter testAdapter=new AccountTypeAdapter(strings);
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
        initPop(view);

    }
    private List<String>types=new ArrayList<>();
    private void initPop(View view){
        String[]strings={"全部","红包","转账","充值提现","二维码收付款","退款"};
        Collections.addAll(types,strings);
        RecyclerView rv_account_type=view.findViewById(R.id.rv_account_type);
        GridLayoutManager manager=new GridLayoutManager(this,3);
        rv_account_type.setLayoutManager(manager);
        com.kest.ediscover.my.account.adapter.AccountTypeAdapter typeAdapter=new com.kest.ediscover.my.account.adapter.AccountTypeAdapter(types);
        rv_account_type.setAdapter(typeAdapter);

    }
}
