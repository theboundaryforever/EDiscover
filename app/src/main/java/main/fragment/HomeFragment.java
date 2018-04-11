package main.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kest.ediscover.my.PaymentActivity;
import com.kest.ediscover.QRCodeActivity;
import com.kest.ediscover.R;
import com.kest.ediscover.ScanActivity;
import com.kest.ediscover.finance.FinanceFBiz;
import com.kest.ediscover.utils.DebugLog;
import com.kest.ediscover.utils.SharePreferenceUtil;

/**
 * Created by Administrator on 2018/4/7 0007.
 */
//首页
public class HomeFragment extends Fragment implements View.OnClickListener {
    private AppBarLayout appBarLayout;
    private View toolbar1, toolbar2;
    /*toolbar2*/
    private ImageView img_shaomiao, img_fukuang, img_shoukuan, img_soushuo, img_zhaoxiang;
    /*toolbar1*/
    private LinearLayout layout_sousuo, setting_btn;
    private TextView aaa, bbb, txt_username;
    private ImageView txt_tianjia;
    private String url;
    private SharePreferenceUtil sp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_home, null);
        }
        if (view != null) {
            init(view);
        }
        return view;
    }

    /**
     * 初始化
     */
    private void init(View view) {
        view.findViewById(R.id.img_shaomiao).setOnClickListener(this);
        view.findViewById(R.id.img_fukuang).setOnClickListener(this);
        view.findViewById(R.id.img_shoukuan).setOnClickListener(this);
        view.findViewById(R.id.layout_saomiao).setOnClickListener(this);
        view.findViewById(R.id.layout_fukuan).setOnClickListener(this);
        view.findViewById(R.id.layout_shoukuan).setOnClickListener(this);
        view.findViewById(R.id.layout_yve).setOnClickListener(this);
        view.findViewById(R.id.layout_ed).setOnClickListener(this);
        view.findViewById(R.id.layout_topup).setOnClickListener(this);
        view.findViewById(R.id.layout_gfcc).setOnClickListener(this);
        view.findViewById(R.id.layout_transfer).setOnClickListener(this);
        view.findViewById(R.id.layout_chargetphone).setOnClickListener(this);
        view.findViewById(R.id.layout_game).setOnClickListener(this);


        appBarLayout = view.findViewById(R.id.appbarlayout);
        toolbar1 = view.findViewById(R.id.toolbar1);
        toolbar2 = view.findViewById(R.id.toolbar2);
        /*toolbar2*/
        img_shaomiao = view.findViewById(R.id.img_shaomiao);
        img_fukuang = view.findViewById(R.id.img_fukuang);
        img_shoukuan = view.findViewById(R.id.img_shoukuan);
        img_soushuo = view.findViewById(R.id.img_soushuo);
        img_zhaoxiang = view.findViewById(R.id.img_zhaoxiang);
        /*toolbar1*/
        layout_sousuo = view.findViewById(R.id.layout_sousuo);
        setting_btn = view.findViewById(R.id.setting_btn);
        aaa = view.findViewById(R.id.aaa);
        bbb = view.findViewById(R.id.bbb);
        txt_username = view.findViewById(R.id.txt_username);
        txt_tianjia = view.findViewById(R.id.txt_tianjia);
        assigment();
    }

    /**
     * 赋值
     */
    private void assigment() {
        sp = SharePreferenceUtil.getInstance(getContext());
        txt_username.setText(sp.getUserName());
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Log.d("我的", "verticalOffset=" + verticalOffset + ",ads=" + Math.abs(verticalOffset) + "," + appBarLayout.getTotalScrollRange());
                if (verticalOffset == 0) {
                    //完全展开
                    toolbar1.setVisibility(View.VISIBLE);
                    toolbar2.setVisibility(View.GONE);
                    setToolbar1Alpha(255);
                } else if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) {
                    //完全折叠
                    toolbar1.setVisibility(View.GONE);
                    toolbar2.setVisibility(View.VISIBLE);
                    setToolbar2Alpha(255);
                } else {
                    //0-150上下滑
                    if (toolbar1.getVisibility() == View.VISIBLE) {
                        //操作tollbar1
                        int alpha = 300 - 155 - Math.abs(verticalOffset);
                        Log.d("我的，alpha", "" + alpha);
                        setToolbar1Alpha(alpha);
                    } else if (toolbar2.getVisibility() == View.VISIBLE) {
                        //操作toolbar2
                        if (Math.abs(verticalOffset) > 0 && Math.abs(verticalOffset) < 150) {
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
        switch (view.getId()) {

            case R.id.img_shaomiao:  //便捷栏扫描
                Intent intent = new Intent(getContext(), ScanActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.img_fukuang:  //便捷栏付款

                startActivity(new Intent(getContext(), PaymentActivity.class));
                break;
            case R.id.img_shoukuan:  //便捷栏收款
                final FinanceFBiz financeFBiz = new FinanceFBiz(getContext());
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
                Intent intent5 = new Intent(getContext(), ScanActivity.class);
                startActivityForResult(intent5, 1);
                break;
            case R.id.layout_fukuan:  //模块付款
                startActivity(new Intent(getContext(), PaymentActivity.class));
                break;
            case R.id.layout_shoukuan:  //模块收款
                final FinanceFBiz financeFBiz1 = new FinanceFBiz(getContext());
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
             /*   Intent intent1 = new Intent(HomeActivity.this, WebAppActivity.class);
                intent1.putExtra("address", "http://pay.allbuy.me/Home/Index/balance/token/" + sp.getToken());
                startActivity(intent1);*/
                break;
            case R.id.layout_ed:  //ED币账户

                break;
            case R.id.layout_topup:  //充值
              /*  Intent intent2 = new Intent(HomeActivity.this, WebAppActivity.class);
                intent2.putExtra("address", "http://pay.allbuy.me/Home/Index/recharge/token/" + sp.getToken());
                startActivity(intent2);*/
                break;
            case R.id.layout_gfcc:  //GFCC

                break;
            case R.id.layout_transfer:  //转账
            /*    Intent intent3 = new Intent(HomeActivity.this, WebAppActivity.class);
                intent3.putExtra("address", "http://pay.allbuy.me/index.php?m=Home&c=Pay&a=deal_payment_code&token=" + sp.getToken());
                startActivity(intent3);*/
                break;
            case R.id.layout_chargetphone:  //充话费
              /*  Intent intent4 = new Intent(HomeActivity.this, WebAppActivity.class);
                intent4.putExtra("address", "http://pay.allbuy.me/index.php?m=Home&c=user&a=rechange_phone&token=" + sp.getToken());
                startActivity(intent4);*/
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
                    Intent intent = new Intent(getActivity(), QRCodeActivity.class);
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
