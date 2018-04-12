package com.kest.ediscover.HomePage.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.kest.ediscover.BaseActivity;
import com.kest.ediscover.ChatPage.Activity.ChatListActivity;
import com.kest.ediscover.QRCodeActivity;
import com.kest.ediscover.R;
import com.kest.ediscover.ScanActivity;
import com.kest.ediscover.WebAppActivity;
import com.kest.ediscover.finance.FinanceFBiz;
import com.kest.ediscover.FriendPage2.Activity.FriendListActivity;
import com.kest.ediscover.utils.DebugLog;
import com.kest.ediscover.utils.SharePreferenceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018\3\28 0028.
 *
 * 首页
 */

public class HomeActivity extends BaseActivity implements View.OnClickListener{

    private AppBarLayout appBarLayout;
    private View toolbar1,toolbar2;
    /*toolbar2*/
    private ImageView img_shaomiao,img_fukuang,img_shoukuan,img_soushuo,img_zhaoxiang;
    /*toolbar1*/
    private LinearLayout layout_sousuo,setting_btn;
    private TextView aaa,bbb,txt_username;
    private ImageView txt_tianjia;
    private String url;
    private SharePreferenceUtil sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test1);
        init();
    }

    /**初始化*/
    private void init(){

        this.findViewById(R.id.purse_btn).setOnClickListener(this);
        this.findViewById(R.id.daybuy_btn2).setOnClickListener(this);
        this.findViewById(R.id.Chatlist_btn).setOnClickListener(this);
        this.findViewById(R.id.img_shaomiao).setOnClickListener(this);
        this.findViewById(R.id.img_fukuang).setOnClickListener(this);
        this.findViewById(R.id.img_shoukuan).setOnClickListener(this);
        this.findViewById(R.id.layout_saomiao).setOnClickListener(this);
        this.findViewById(R.id.layout_fukuan).setOnClickListener(this);
        this.findViewById(R.id.layout_shoukuan).setOnClickListener(this);
        this.findViewById(R.id.layout_yve).setOnClickListener(this);
        this.findViewById(R.id.layout_ed).setOnClickListener(this);
        this.findViewById(R.id.layout_topup).setOnClickListener(this);
        this.findViewById(R.id.layout_gfcc).setOnClickListener(this);
        this.findViewById(R.id.layout_transfer).setOnClickListener(this);
        this.findViewById(R.id.layout_chargetphone).setOnClickListener(this);
        this.findViewById(R.id.layout_game).setOnClickListener(this);
        this.findViewById(R.id.allbuy_btn).setOnClickListener(this);
        this.findViewById(R.id.city_btn2).setOnClickListener(this);


        appBarLayout = (AppBarLayout)this.findViewById(R.id.appbarlayout);
        toolbar1 = (View)this.findViewById(R.id.toolbar1);
        toolbar2 = (View)this.findViewById(R.id.toolbar2);
        /*toolbar2*/
        img_shaomiao = (ImageView)this.findViewById(R.id.img_shaomiao);
        img_fukuang = (ImageView)this.findViewById(R.id.img_fukuang);
        img_shoukuan = (ImageView)this.findViewById(R.id.img_shoukuan);
        img_soushuo = (ImageView)this.findViewById(R.id.img_soushuo);
        img_zhaoxiang = (ImageView)this.findViewById(R.id.img_zhaoxiang);
        /*toolbar1*/
        layout_sousuo = (LinearLayout)this.findViewById(R.id.layout_sousuo);
        setting_btn = (LinearLayout)this.findViewById(R.id.setting_btn);
        aaa = (TextView) this.findViewById(R.id.aaa);
        bbb = (TextView)this.findViewById(R.id.bbb);
        txt_username = (TextView)this.findViewById(R.id.txt_username);
        txt_tianjia = (ImageView) this.findViewById(R.id.txt_tianjia);



        assigment();
    }

    /**赋值*/
    private void assigment(){

        sp = SharePreferenceUtil.getInstance(this);
        EMClient.getInstance().chatManager().loadAllConversations();
        EMClient.getInstance().groupManager().loadAllGroups();

        txt_username.setText(sp.getUserName());

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Log.d("我的","verticalOffset="+verticalOffset+",ads="+Math.abs(verticalOffset)+","+appBarLayout.getTotalScrollRange());
                if(verticalOffset == 0){
                    //完全展开
                    toolbar1.setVisibility(View.VISIBLE);
                    toolbar2.setVisibility(View.GONE);
                    setToolbar1Alpha(255);
                }else if(Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()){
                    //完全折叠
                    toolbar1.setVisibility(View.GONE);
                    toolbar2.setVisibility(View.VISIBLE);
                    setToolbar2Alpha(255);
                }else{
                    //0-150上下滑
                    if(toolbar1.getVisibility() == View.VISIBLE){
                        //操作tollbar1
                        int alpha = 300 - 155- Math.abs(verticalOffset);
                        Log.d("我的，alpha",""+alpha);
                        setToolbar1Alpha(alpha);
                    }else if(toolbar2.getVisibility() == View.VISIBLE){
                        //操作toolbar2
                        if(Math.abs(verticalOffset) > 0 && Math.abs(verticalOffset) < 150){
                            toolbar1.setVisibility(View.VISIBLE);
                            toolbar2.setVisibility(View.GONE);
                            setToolbar1Alpha(255);
                        }
                        int alpha = (int) (255 * (Math.abs(verticalOffset) / 100f));
                        setToolbar2Alpha(alpha);
                    }
                }

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.allbuy_btn:  //E发现
                Intent intent7 = new Intent(HomeActivity.this, WebAppActivity.class);
                intent7.putExtra("address","http://pay.allbuy.me/Home/BusinessAccount/index/token/" + sp.getToken());
                startActivity(intent7);
                break;
            case R.id.daybuy_btn2:   //朋友
                startActivity(new Intent(HomeActivity.this, FriendListActivity.class));
                break;
            case R.id.Chatlist_btn:  //E信
                startActivity(new Intent(HomeActivity.this, ChatListActivity.class));
                break;
            case R.id.city_btn2:  //我的
            /*    Intent intent6 = new Intent(HomeActivity.this, WebAppActivity.class);
                intent6.putExtra("address","http://pay.allbuy.me/index.php?m=Home&c=Index&a=index&token=" + sp.getToken());
              */

                break;
            case R.id.img_shaomiao:  //便捷栏扫描
                Intent intent = new Intent(HomeActivity.this, ScanActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.img_fukuang:  //便捷栏付款

                break;
            case R.id.img_shoukuan:  //便捷栏收款
                final FinanceFBiz financeFBiz = new FinanceFBiz(this);
                new Thread() {
                    public void run() {
                        Message message = Message.obtain();
                        message.what = 1;
                        url = financeFBiz.getPayCode();
                        DebugLog.i("url:====" + url);
                        handler.sendMessage(message);
                    }
                }.start();
                break;
            case R.id.layout_saomiao:  //模块扫描
                Intent intent5 = new Intent(HomeActivity.this, ScanActivity.class);
                startActivityForResult(intent5, 1);
                break;
            case R.id.layout_fukuan:  //模块付款


                break;
            case R.id.layout_shoukuan:  //模块收款
                final FinanceFBiz financeFBiz1 = new FinanceFBiz(this);
                new Thread() {
                    public void run() {
                        Message message = Message.obtain();
                        message.what = 1;
                        url = financeFBiz1.getPayCode();
                        DebugLog.i("url:====" + url);
                        handler.sendMessage(message);
                    }
                }.start();
                break;
            case R.id.layout_yve:  //模块余额
                Intent intent1 = new Intent(HomeActivity.this, WebAppActivity.class);
                intent1.putExtra("address", "http://pay.allbuy.me/Home/Index/balance/token/" + sp.getToken());
                startActivity(intent1);
                break;
            case R.id.layout_ed:  //ED币账户

                break;
            case R.id.layout_topup:  //充值
                Intent intent2 = new Intent(HomeActivity.this, WebAppActivity.class);
                intent2.putExtra("address", "http://pay.allbuy.me/Home/Index/recharge/token/" + sp.getToken());
                startActivity(intent2);
                break;
            case R.id.layout_gfcc:  //GFCC

                break;
            case R.id.layout_transfer:  //转账
                Intent intent3 = new Intent(HomeActivity.this, WebAppActivity.class);
                intent3.putExtra("address", "http://pay.allbuy.me/index.php?m=Home&c=Pay&a=deal_payment_code&token=" + sp.getToken());
                startActivity(intent3);
                break;
            case R.id.layout_chargetphone:  //充话费
                Intent intent4 = new Intent(HomeActivity.this, WebAppActivity.class);
                intent4.putExtra("address", "http://pay.allbuy.me/index.php?m=Home&c=user&a=rechange_phone&token=" + sp.getToken());
                startActivity(intent4);
                break;
            case R.id.layout_game:  //游戏

                break;
        }
    }

    private void setToolbar1Alpha(int alpha) {
        txt_tianjia.getDrawable().setAlpha(alpha);
        aaa.setTextColor(Color.argb(alpha, 255, 255, 255));
        bbb.setTextColor(Color.argb(alpha, 255, 255, 255));
        txt_username.setTextColor(Color.argb(alpha, 255, 255, 255));
       // layout_sousuo.getD
       // setting_btn.getDrawable().setAlpha(alpha);
    }

    private void setToolbar2Alpha(int alpha) {
        img_shaomiao.getDrawable().setAlpha(alpha);
        img_fukuang.getDrawable().setAlpha(alpha);
        img_shoukuan.getDrawable().setAlpha(alpha);
        img_soushuo.getDrawable().setAlpha(alpha);
        img_zhaoxiang.getDrawable().setAlpha(alpha);
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    Intent intent = new Intent(HomeActivity.this, QRCodeActivity.class);
                    intent.putExtra("url", url);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }

        ;
    };

}
