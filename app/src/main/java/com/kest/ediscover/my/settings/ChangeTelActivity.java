package com.kest.ediscover.my.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.kest.ediscover.R;

/**
 * Created by Administrator on 2018/4/3 0003.
 */

/*换绑手机*/
public class ChangeTelActivity extends AppCompatActivity {
    ImageView iv_back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_change_tel);
        initView();
        setListener();
    }
    private void initView(){
        iv_back=findViewById(R.id.iv_back);

    }
    private void setListener(){
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
