package com.kest.ediscover.my.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kest.ediscover.R;

/**
 * 违规行为条目
 * Created by Administrator on 2018/4/9 0009.
 */
public class ReportIllegalActivity extends AppCompatActivity {
    ImageView iv_back;
    TextView tv_title;
    //网络诈骗
    RelativeLayout rl_illegel;
    //信息贩卖
    RelativeLayout rl_info;
    //网络传销
    RelativeLayout rl_mlm;
    //黄赌毒
    RelativeLayout rl_pornography;
    //民事侵权
    RelativeLayout rl_tort;
    //其它
    RelativeLayout rl_other;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_report_illegal);
        initView();
    }
    private void initView(){
        iv_back=findViewById(R.id.iv_back);
        tv_title=findViewById(R.id.tv_title);
        tv_title.setText("违规行为");
        rl_illegel=findViewById(R.id.rl_illegel);
        rl_info=findViewById(R.id.rl_info);
        rl_mlm=findViewById(R.id.rl_mlm);
        rl_pornography=findViewById(R.id.rl_pornography);
        rl_tort=findViewById(R.id.rl_tort);
        rl_other=findViewById(R.id.rl_other);
    }
}
