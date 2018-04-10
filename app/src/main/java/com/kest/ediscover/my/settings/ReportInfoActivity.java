package com.kest.ediscover.my.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kest.ediscover.R;

/**
 * Created by Administrator on 2018/4/3 0003.
 */

/*举报信息贩卖*/
public class ReportInfoActivity  extends AppCompatActivity{
    TextView tv_title;
    RelativeLayout rl_illegal;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_report2);
        rl_illegal=findViewById(R.id.rl_illegal);
        rl_illegal.setVerticalGravity(View.GONE);
        tv_title.setText("举报信息贩卖");
    }
}
