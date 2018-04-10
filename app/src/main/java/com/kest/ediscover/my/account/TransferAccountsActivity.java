package com.kest.ediscover.my.account;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.kest.ediscover.R;

/**
 * Created by Administrator on 2018/4/9 0009.
 */
//转账详情
public class TransferAccountsActivity extends AppCompatActivity {
    ImageView iv_back;
    TextView tv_title;
    //付款金额
    TextView tv_money;
    //退款记录
    TextView tv_reback;
    //退款时间
    TextView tv_reback_time;
    //退款方式
    TextView tv_reback_type;
    //退款账号
    TextView tv_reback_account;
    //当前状态
    TextView tv_sure;
    //收款方
    TextView tv_self;
    //转账说明
    TextView tv_transfer_explain;
    //转账时间
    TextView tv_transfer_time;
    //支付方式
    TextView tv_pay_type;
    //转账单账号
    TextView tv_account_num;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_settings_detail_transfer);
        iv_back=findViewById(R.id.iv_back);
        tv_title=findViewById(R.id.tv_title);
        tv_title.setText("转账详情");
    }
}
