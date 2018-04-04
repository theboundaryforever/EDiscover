package com.kest.ediscover.my.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.kest.ediscover.R;

/**
 * Created by Administrator on 2018/4/3 0003.
 */
/*举报*/
public class ReportActivity  extends AppCompatActivity {
    //网络诈骗
    RelativeLayout rl_report_net;
    //信息贩卖
    RelativeLayout rl_info;
    //网络传销
    RelativeLayout rl_mlm;
    //黄赌毒
    RelativeLayout rl_pornography;
    //其它
    RelativeLayout rl_other;
    ImageView iv_back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_report);
        initView();
        setListener();
    }
    private void initView(){
        rl_report_net=findViewById(R.id.rl_report_net);
        rl_info=findViewById(R.id.rl_info);
        rl_mlm=findViewById(R.id.rl_mlm);
        rl_pornography=findViewById(R.id.rl_pornography);
        rl_other=findViewById(R.id.rl_other);

    }
    private void setListener(){
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rl_report_net.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReportActivity.this,ReportNetActivity.class));
            }
        });
        rl_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReportActivity.this,ReportInfoActivity.class));
            }
        });
        rl_mlm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReportActivity.this,ReportMLMActivity.class));
            }
        });
        rl_pornography.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReportActivity.this,ReportPornographyActivity.class));
            }
        });
        rl_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReportActivity.this,ReportOtherActivity.class));
            }
        });
    }
}
