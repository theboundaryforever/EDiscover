package com.kest.ediscover.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kest.ediscover.QRCodeActivity;
import com.kest.ediscover.R;
import com.kest.ediscover.ScanActivity;
import com.kest.ediscover.WebAppActivity;
import com.kest.ediscover.account.SettingActivity;
import com.kest.ediscover.finance.FinanceFBiz;
import com.kest.ediscover.utils.DebugLog;
import com.kest.ediscover.utils.SharePreferenceUtil;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * TODO 首页
 */
public class FirstPageFragment extends Fragment {

    public static final int REQUEST_CODE = 1;
    private Unbinder unbinder;
    String url;
    SharePreferenceUtil sp;
    @BindView(R.id.usernameTxt)
    TextView userName;

    public FirstPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_firstpage, container, false);
        unbinder = ButterKnife.bind(this, view);
        sp = SharePreferenceUtil.getInstance(getActivity());
        userName.setText("用户名:" + sp.getUserName());

        return view;
    }

    @OnClick(R.id.qrcode_dencode)
    void scanCode() {

        Intent intent = new Intent(getActivity(), ScanActivity.class);

        startActivityForResult(intent, REQUEST_CODE);
    }

    @OnClick(R.id.qrcode_encode)
    void newCode() {
        final FinanceFBiz financeFBiz = new FinanceFBiz(getActivity());
        new Thread() {
            public void run() {
                Message message = Message.obtain();
                message.what = 1;
                url = financeFBiz.getPayCode();
                DebugLog.i("url:====" + url);
                handler.sendMessage(message);

            }
        }.start();

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

    @OnClick(R.id.setting_btn)
    void setting() {
        startActivity(new Intent(getActivity(), SettingActivity.class));
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { //RESULT_OK = -1
            Bundle bundle = data.getExtras();
            Intent intent = new Intent(getActivity(), WebAppActivity.class);
            intent.putExtra("address", bundle.getString("result"));
            getActivity().startActivity(intent);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.share_btn)
    void share() {
        Intent textIntent = new Intent(Intent.ACTION_SEND);
        textIntent.setType("text/plain");
        textIntent.putExtra(Intent.EXTRA_TEXT, "APP下载地址 http://pay.allbuy.me/down.html" );
        startActivity(Intent.createChooser(textIntent, "分享"));
    }

    /*
    @OnClick(R.id.news_btn)
    void news() {
        Intent intent = new Intent(getActivity(), WebAppActivity.class);
        intent.putExtra("address", "http://pay.allbuy.me/index.php?m=Home&c=UserPushMessage&a=get_list");
        startActivity(intent);
    }
*/
    @OnClick(R.id.money_btn)
    void goTOMoney() {
        Intent intent = new Intent(getActivity(), WebAppActivity.class);
        intent.putExtra("address", "http://pay.allbuy.me/Home/Index/balance/token/" + sp.getToken());
        startActivity(intent);
    }

    @OnClick(R.id.bank_card_btn)
    void goTOBackCard() {
        Intent intent = new Intent(getActivity(), WebAppActivity.class);
        intent.putExtra("address", "http://pay.allbuy.me/Home/Index/have_bank/token/" + sp.getToken());
        startActivity(intent);
    }

    @OnClick(R.id.news_btn)
    void newsPage() {
        Intent intent = new Intent(getActivity(), WebAppActivity.class);
        intent.putExtra("address", "http://pay.allbuy.me/index.php?m=Home&c=User&a=gcyg&token=" + sp.getToken());
        startActivity(intent);
    }
    /*
    @OnClick(R.id.back_card_btn)
    void goToAccount(){
        Intent intent = new Intent(getActivity(), WebAppActivity.class);
        intent.putExtra("address", "http://pay.allbuy.me/Home/Index/balance_detail/token/"+ sp.getToken());
    }*/

    @OnClick(R.id.charge_btn)
    void goToCharge() {
        Intent intent = new Intent(getActivity(), WebAppActivity.class);
        intent.putExtra("address", "http://pay.allbuy.me/Home/Index/recharge/token/" + sp.getToken());
        startActivity(intent);
    }

    @OnClick(R.id.exchange)
    void exchange(){
        Intent intent = new Intent(getActivity(), WebAppActivity.class);
        intent.putExtra("address", "http://pay.allbuy.me/index.php?m=Home&c=index&a=balance#!//index.php?m=Home&c=Index&a=e_platform&token=" + sp.getToken());
        startActivity(intent);
    }

    @OnClick(R.id.zhuanzhang)
    void zhuanzhang(){
        Intent intent = new Intent(getActivity(), WebAppActivity.class);
        intent.putExtra("address", "http://pay.allbuy.me/index.php?m=Home&c=Pay&a=deal_payment_code&token=" + sp.getToken());
        startActivity(intent);
    }

    @OnClick(R.id.quanxiangjinfu)
    void quanxiangjinfu(){
        Intent intent = new Intent(getActivity(), WebAppActivity.class);
        intent.putExtra("address", "http://pay.allbuy.me/index.php?m=Home&c=User&a=allbuyservice&token=" + sp.getToken());
        startActivity(intent);
    }

    @OnClick(R.id.huafeichongzhi)
    void huafeichongzhi(){
        Intent intent = new Intent(getActivity(), WebAppActivity.class);
        intent.putExtra("address", "http://pay.allbuy.me/index.php?m=Home&c=user&a=rechange_phone&token=" + sp.getToken());
        startActivity(intent);
    }
}
