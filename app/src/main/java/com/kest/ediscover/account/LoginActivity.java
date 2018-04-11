package com.kest.ediscover.account;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.kest.ediscover.MyApplication;
import com.kest.ediscover.MyHttputils.Conntent;
import com.kest.ediscover.MyHttputils.HttpUtils;
import com.kest.ediscover.R;
import com.kest.ediscover.HomePage.Activity.HomeActivity;
import com.kest.ediscover.service.PushIntentService;
import com.kest.ediscover.service.PushService;
import com.kest.ediscover.utils.DebugLog;
import com.kest.ediscover.utils.NetworkUtil;
import com.kest.ediscover.utils.SharePreferenceUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import main.MainHomeActivity;

import com.igexin.sdk.PushManager;
import com.kest.ediscover.utils.ThreadPoolManager;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO 用户登录页面
 */
public class LoginActivity extends AppCompatActivity implements HttpUtils.ICallback{

    Activity context;
    SharePreferenceUtil sp;
    private static final int REQUEST_PERMISSION = 0;
    @BindView(R.id.mobile)
    EditText mobileEditText;
    @BindView(R.id.password)
    EditText passwordEditText;
    @BindView(R.id.seepassword_btn)
    TextView seePasswordBtn;
    @BindView(R.id.login_btn)
     Button login_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        sp = SharePreferenceUtil.getInstance(this);
        context = this;
        PackageManager pkgManager = getPackageManager();

        // 读写 sd card 权限非常重要, android6.0默认禁止的, 建议初始化之前就弹窗让用户赋予该权限
        boolean sdCardWritePermission =
                pkgManager.checkPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, getPackageName()) == PackageManager.PERMISSION_GRANTED;

        // read phone state用于获取 imei 设备信息
        boolean phoneSatePermission =
                pkgManager.checkPermission(android.Manifest.permission.READ_PHONE_STATE, getPackageName()) == PackageManager.PERMISSION_GRANTED;

        if (Build.VERSION.SDK_INT >= 23 && !sdCardWritePermission || !phoneSatePermission) {
            requestPermission();
        } else {
            PushManager.getInstance().initialize(this, PushService.class);
            DebugLog.i("个推初始化1");
        }


        // 注册 intentService 后 PushDemoReceiver 无效, sdk 会使用 DemoIntentService 传递数据,
        // AndroidManifest 对应保留一个即可(如果注册 DemoIntentService, 可以去掉 PushDemoReceiver, 如果注册了
        // IntentService, 必须在 AndroidManifest 中声明)
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), PushIntentService.class);
        cid = PushManager.getInstance().getClientid(this);
        //Toast.makeText(this, "clientId="+cid, Toast.LENGTH_LONG).show();
        DebugLog.i("个推初始化==clientid="+cid);
    /*    login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });*/
    }
    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE},
                REQUEST_PERMISSION);
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    if ((Boolean) msg.obj) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("token",sp.getToken());
                        map.put("action","my_info");
                        HttpUtils.getInstance().post(Conntent.HTTPURL+Conntent.EASEMOB,map,LoginActivity.this);
                    } else {
                        Toast.makeText(LoginActivity.this, "用户名或密码错误",
                                Toast.LENGTH_SHORT).show();
                        //(new ShowDialogHelper(LoginActivity.this)).showDialog("提示", "用户名或密码错误");
                    }
                    break;

                case -1:
                    Toast.makeText(LoginActivity.this, "前网络不可用",
                            Toast.LENGTH_SHORT).show();
                    //(new ShowDialogHelper(LoginActivity.this)).showDialog("提示", "当前网络不可用");
                    break;
                default:
                    break;
            }
        }

        ;
    };
/*
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults) {
        if (requestCode == REQUEST_PHONE_STATE && grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            TelephonyManager TelephonyMgr = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
            cid = TelephonyMgr.getDeviceId();
        }
    }*/
    String cid="";
  //登录
    void login(){
        /*
        //Android6.0需要动态获取权限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            toast("需要动态获取权限");
            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_PHONE_STATE);
        }else{
            TelephonyManager TelephonyMgr = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
            cid = TelephonyMgr.getDeviceId();
        }*/
        //final String cid = DeviceUtils.getDeviceId(context);
       if(mobileEditText.getText().toString().length()>5) {
           if(passwordEditText.getText().toString().length()>5){
               ThreadPoolManager.getNormalPool().execute(new Runnable() {
                   @Override
                   public void run() {
                       Message message = Message.obtain();
                       if (NetworkUtil.checkNetState(LoginActivity.this)) {
                           message.what = 0;
                           AccountBiz userBiz = new AccountBiz(LoginActivity.this);
                           message.obj = userBiz.login(mobileEditText.getText()
                                   .toString(), passwordEditText.getText().toString(), cid);
                       } else {
                           message.what = -1;
                       }
                       handler.sendMessage(message);

                   }
               });

           }else{
               MyApplication.setToast("账号长度有误,最少6位");
           }
       }else{
           MyApplication.setToast("账号长度有误,最少6位");
       }
    }

    boolean canSeePassword = false;
    @OnClick({R.id.register_btn,R.id.login_btn,R.id.lostpassword_btn})
    void onClick(View view) {
        switch (view.getId()){
            case R.id.register_btn:
                //注册
                startActivity(new Intent(context, RegisterActivity.class));
                break;
            case R.id.lostpassword_btn:
                //忘记密码
                startActivity(new Intent(context, LostPasswordActivity.class));
                break;
            case R.id.login_btn:
                login();
                break;
            case R.id.see_password:
                if (canSeePassword){
                    seePasswordBtn.setBackgroundResource(R.mipmap.eye);
                    passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }else{
                    seePasswordBtn.setBackgroundResource(R.mipmap.eye_selected);
                    passwordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                canSeePassword = !canSeePassword;
                break;
        }
    }


    //登录的方法
    public void easelogin(String username,String passwork){
        EMClient.getInstance().login(username.trim(), passwork.trim(), new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.d("环信账户登录","登录成功");
                EMClient.getInstance().chatManager().loadAllConversations();
                EMClient.getInstance().groupManager().loadAllGroups();
               // startActivity(new Intent(context, HomeActivity.class));

            startActivity(new Intent(context, MainHomeActivity.class));
                context.finish();
            }

            @Override
            public void onError(int i, String s) {
                Log.d("环信账户登录","登录失败"+s);
            }

            @Override
            public void onProgress(int i, String s) {
                Log.d("环信账户登录","登录异常"+s);
            }
        });
    }

    @Override
    public void onSuccess(String url, String result) {
        if(url.equals(Conntent.HTTPURL+Conntent.EASEMOB)){
           Log.d("IM用户信息返回值","result="+result);
           if(result.length()>0){
               try{
                   JSONObject js = new JSONObject(result);
                   if(js.getInt("returnCode")==10000){
                       easelogin(js.getString("hx_username"),js.getString("hx_password"));
                       sp.setHx_username(js.getString("hx_username"));
                   }else{
                       MyApplication.setToast(js.getString("returnMsg"));
                   }
               }catch(Exception e){
                   e.printStackTrace();
               }
           }
        }
    }
}
