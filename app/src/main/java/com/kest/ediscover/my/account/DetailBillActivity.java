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

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/4/9 0009.
 */
//账单详情
public class DetailBillActivity extends AppCompatActivity {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_race)
    TextView tv_race;
    //收款金额
    @BindView(R.id.tv_money)
    TextView tv_money;
    //当前状态
    @BindView(R.id.tv_sure)
    TextView tv_sure;

    //来自于谁
    @BindView(R.id.tv_from)
    TextView tv_from;
    //转账说明
    @BindView(R.id.tv_explain)
    TextView tv_explain;
    //专账时间
    @BindView(R.id.tv_transfer_time)
    TextView tv_transfer_time;
    //收钱时间
    @BindView(R.id.tv_collect_time)
    TextView tv_collect_time;
    //支付方式
    @BindView(R.id.tv_pay_type)
    TextView tv_pay_type;
    //账单账号
    @BindView(R.id.tv_account_num)
    TextView tv_account_num;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_settings_detail_bill);
        tv_race.setVisibility(View.VISIBLE);
        tv_title.setText("账户明细");
    }
        @OnClick({})
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
