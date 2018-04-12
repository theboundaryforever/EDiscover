package com.kest.ediscover.my.account;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.kest.ediscover.R;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/4/9 0009.
 */
//转账详情
public class TransferAccountsActivity extends AppCompatActivity {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    //付款金额
    @BindView(R.id.tv_money)
    TextView tv_money;
    //退款记录
    @BindView(R.id.tv_reback)
    TextView tv_reback;
    //退款时间
    @BindView(R.id.tv_reback_time)
    TextView tv_reback_time;
    //退款方式
    @BindView(R.id.tv_reback_type)
    TextView tv_reback_type;
    //退款账号
    @BindView(R.id.tv_reback_account)
    TextView tv_reback_account;
    //当前状态
    @BindView(R.id.tv_sure)
    TextView tv_sure;
    //收款方
    @BindView(R.id.tv_self)
    TextView tv_self;
    //转账说明
    @BindView(R.id.tv_transfer_explain)
    TextView tv_transfer_explain;
    //转账时间
    @BindView(R.id.tv_transfer_time)
    TextView tv_transfer_time;
    //支付方式
    @BindView(R.id.tv_pay_type)
    TextView tv_pay_type;
    //转账单账号
    @BindView(R.id.tv_account_num)
    TextView tv_account_num;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_settings_detail_transfer);
        tv_title.setText("转账详情");
    }
}
