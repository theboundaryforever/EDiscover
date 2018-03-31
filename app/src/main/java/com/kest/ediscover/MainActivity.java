package com.kest.ediscover;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.kest.ediscover.fragment.DaybuyFragment;
import com.kest.ediscover.fragment.FirstPageFragment;
import com.kest.ediscover.fragment.FriendFragment;
import com.kest.ediscover.fragment.MineFragment;
import com.kest.ediscover.ChatPage.Activity.ChatListActivity;
import com.kest.ediscover.FriendPage2.Activity.FriendListActivity;
import com.kest.ediscover.HomePage.Activity.HomeActivity;
import com.kest.ediscover.utils.DebugLog;
import com.kest.ediscover.utils.SharePreferenceUtil;

public class MainActivity extends BaseActivity {


    RadioGroup radioGroup;


    RadioButton bottomPurse;
    //@BindView(R.id.daybuy_btn)
    //RadioButton bottomDaybuy;
    //@BindView(R.id.allbuy_btn)
    //RadioButton bottomAllbuy;
    //@BindView(R.id.city_btn)
    //RadioButton bottomAllbuy;

    private long exitTime = 0;
    Activity context;
    Intent intent;
    SharePreferenceUtil sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        sp = SharePreferenceUtil.getInstance(this);
        //PushManager.getInstance().initialize(this.getApplicationContext(), com.kest.ediscover.service.PushService.class);
       // PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), PushIntentService.class);


        OpenCarmer();

        //ButterKnife.bind(this);
        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        bottomPurse = (RadioButton) findViewById(R.id.purse_btn);
        RadioButton bottomDaybuy = (RadioButton) findViewById(R.id.daybuy_btn);
        RadioButton bottomAllbuy = (RadioButton) findViewById(R.id.allbuy_btn);
        RadioButton bottomCity = (RadioButton) findViewById(R.id.city_btn);
        bottomPurse.setChecked(true);



        final FirstPageFragment purseFragment = new FirstPageFragment();
        final DaybuyFragment daybuyFragment = new DaybuyFragment();
        final FriendFragment allbuyFragment = new FriendFragment();
        final MineFragment cityFragment = new MineFragment();
        final FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.content_layout, purseFragment);
        transaction.add(R.id.content_layout, daybuyFragment);
        transaction.add(R.id.content_layout, allbuyFragment);
        transaction.add(R.id.content_layout, cityFragment);
        transaction.hide(daybuyFragment);
        transaction.hide(allbuyFragment);
        transaction.hide(cityFragment);
        transaction.commit();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction transaction = manager.beginTransaction();

                switch (checkedId) {
                    case R.id.purse_btn: //原生首页模块
                        transaction.show(purseFragment);
                        transaction.hide(daybuyFragment);
                        transaction.hide(allbuyFragment);
                        transaction.hide(cityFragment);
                        break;
                    case R.id.allbuy_btn:  //H5易发现模块
                        //Intent intent = new Intent(context, WebAppActivity.class);
                        //intent.putExtra("address"," http://m2.allbuy.me/");

                        transaction.hide(purseFragment);
                        transaction.show(daybuyFragment);
                        transaction.hide(allbuyFragment);
                        transaction.hide(cityFragment);
                        //startActivity(intent);
                        intent = new Intent(context, WebAppActivity.class);
                        intent.putExtra("address","http://pay.allbuy.me/Home/BusinessAccount/index/token/" + sp.getToken());
                        startActivity(intent);
                        break;
                    case R.id.daybuy_btn:  //朋友模块
                        transaction.hide(purseFragment);
                        transaction.hide(daybuyFragment);
                        transaction.show(allbuyFragment);
                        transaction.hide(cityFragment);
                        intent = new Intent(context, WebAppActivity.class);
                        intent.putExtra("address","http://pay.allbuy.me/index.php?m=Home&c=Index&a=login_im&token=" + sp.getToken());
                        DebugLog.i("===="+"http://pay.allbuy.me/index.php?m=Home&c=Index&a=login_im&token=" + sp.getToken());
                        startActivity(intent);
                        break;
                    case R.id.city_btn:  //H5我的模块

                        transaction.hide(purseFragment);
                        transaction.hide(daybuyFragment);
                        transaction.hide(allbuyFragment);
                        transaction.show(cityFragment);
                        intent = new Intent(context, WebAppActivity.class);
                        intent.putExtra("address","http://pay.allbuy.me/index.php?m=Home&c=Index&a=index&token=" + sp.getToken());
                        startActivity(intent);
                        break;
                    case R.id.daybuy_btn2:  //E信

                        startActivity(new Intent(MainActivity.this, ChatListActivity.class));
                        break;
                    case R.id.Contacts_btn:  //原生朋友模块

                        startActivity(new Intent(MainActivity.this, FriendListActivity.class));
                        break;
                    case R.id.city_btn2:  //我的

                        startActivity(new Intent(MainActivity.this, HomeActivity.class));
                        break;

                }
                transaction.commit();
            }
        });
    }



    @Override
    protected void onRestart() {
        super.onRestart();
        bottomPurse.setChecked(true);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

    private void OpenCarmer() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)  //打开相机权限
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)   //可读
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)  //可写
                        != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE
                        , Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }

}
