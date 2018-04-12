package com.kest.ediscover.my.person;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kest.ediscover.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 用户名
 * Created by Administrator on 2018/4/3 0003.
 */
public class PersonInfoNameActivity extends AppCompatActivity {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.iv_cancel)
    ImageView iv_cancel;
    @BindView(R.id.ll_default)
    LinearLayout ll_default;
    @BindView(R.id.ll_ok)
    LinearLayout ll_ok;
    Unbinder unbinder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_person_name);
        unbinder=ButterKnife.bind(this);
    }
    @OnClick({R.id.iv_back,R.id.iv_cancel,R.id.ll_ok})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_cancel:
                break;
            case R.id.ll_ok:
                    break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
