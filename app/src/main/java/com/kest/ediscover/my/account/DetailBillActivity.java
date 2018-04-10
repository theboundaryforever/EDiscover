package com.kest.ediscover.my.account;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.kest.ediscover.R;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.util.List;

/**
 * Created by Administrator on 2018/4/9 0009.
 */
//账单详情
public class DetailBillActivity extends AppCompatActivity {
    ImageView iv_back;
    TextView tv_title;
    TextView tv_race;
    //收款金额
    TextView tv_money;
    //当前状态
    TextView tv_sure;
    //来自于谁
    TextView tv_from;
    //转账说明
    TextView tv_explain;
    //专账时间
    TextView tv_transfer_time;
    //收钱时间
    TextView tv_collect_time;
    //支付方式
    TextView tv_pay_type;
    //账单账号
    TextView tv_account_num;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_settings_detail_bill);
        iv_back=findViewById(R.id.iv_back);
        tv_title=findViewById(R.id.tv_title);
        tv_race=findViewById(R.id.tv_race);
        tv_race.setVisibility(View.VISIBLE);
        tv_title.setText("账户明细");
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_race.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPop();

            }
        });

    }

    private void showPop(){
        View view= LayoutInflater.from(this).inflate(R.layout.fragment_settings_account_type,null);
        View parentView =LayoutInflater.from(this).inflate(R.layout.fragment_settings_detail_bill,null);
      PopupWindow  popupWindow=new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(false);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        if(view.getBackground()!=null){
            view.getBackground().setAlpha(80);
        }
        popupWindow.showAtLocation(parentView, Gravity.BOTTOM,0,0);

    }


}
