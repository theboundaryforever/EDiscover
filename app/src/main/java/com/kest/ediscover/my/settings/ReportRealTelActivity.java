package com.kest.ediscover.my.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.kest.ediscover.R;

/**
 * Created by Administrator on 2018/4/3 0003.
 */
/*举报人手机*/
public class ReportRealTelActivity extends AppCompatActivity {
    TextView tv_title;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_report_name);
        tv_title.setText("举报人手机");
    }
}