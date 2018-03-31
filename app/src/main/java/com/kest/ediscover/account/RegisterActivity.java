package com.kest.ediscover.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kest.ediscover.BaseActivity;
import com.kest.ediscover.Bean.Response;
import com.kest.ediscover.MyApplication;
import com.kest.ediscover.R;
import com.kest.ediscover.utils.DebugLog;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.kest.ediscover.R.id.sendCodeBtn;

/**
 * TODO 用户注册页面
 */
public class RegisterActivity extends BaseActivity {

    Activity context;
    String code="";
    Response response ;
    private int flag = 60;
    private Timer timer;

    @BindView(R.id.usernameTxt)
    EditText userName;
    @BindView(R.id.phoneTxt)
    EditText phoneNumber;
    @BindView(R.id.smsCodeTxt)
    EditText smsCode;
    @BindView(R.id.passwordTxt)
    EditText password;
    @BindView(R.id.againPasswordTxt)
    EditText againPassword;

    @BindView(R.id.seepassword_btn)
    TextView seePasswordBtn;

    @BindView(R.id.seepassword_btn2)
    TextView seePasswordBtn2;

    @BindView(R.id.sendCodeBtn)
    TextView getCodeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        context = this;
        ButterKnife.bind(this);
    }

    boolean canSeePassword = false;

    @OnClick(R.id.see_password)
    void seePassword(){
        if (canSeePassword){
            seePasswordBtn.setBackgroundResource(R.mipmap.eye);
            password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }else{
            seePasswordBtn.setBackgroundResource(R.mipmap.eye_selected);
            password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
        canSeePassword = !canSeePassword;
    }

    boolean canSeePassword2 = false;

    @OnClick(R.id.see_password_again)
    void seePassword2(){
        if (canSeePassword2){
            seePasswordBtn2.setBackgroundResource(R.mipmap.eye);
            againPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }else{
            seePasswordBtn2.setBackgroundResource(R.mipmap.eye_selected);
            againPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
        canSeePassword2 = !canSeePassword2;
    }


    @OnClick(R.id.register_btn)
    void register(){

        if (userName.getText().toString().length()<1 || phoneNumber.getText().toString().length()<0
                || smsCode.getText().toString().length()<0 || password.getText().toString().length()<0
                || againPassword.getText().toString().length()<0 ){
            MyApplication.setToast("请把信息填写完整");
            return;
        }else{
            if(userName.getText().toString().length()<6||userName.getText().toString().length()>9){
                MyApplication.setToast("账户名长度有误，最小长度6，最大长度8");
                return;
            }
            if(smsCode.getText().toString().length()!=4){
                MyApplication.setToast("验证码长度有误,验证码长度为4位");
                return;
            }
            if(password.getText().toString().length()<6){
                MyApplication.setToast("密码长度有误,最少6位");
                return;
            }
            if(password.getText().toString().equals("123456")||password.getText().toString().equals("1234567")
               || password.getText().toString().equals("12345678") || password.getText().toString().equals("123456789")
               || password.getText().toString().equals("654321") || password.getText().toString().equals("234567")
               || password.getText().toString().equals("345678") || password.getText().toString().equals("456789")){
                MyApplication.setToast("请不要使用类似123456这样的连续数字为密码");
                return;
            }
            if(!againPassword.getText().toString().equals(password.getText().toString())){
                MyApplication.setToast("两次密码不相同，请重新输入");
                return;
            }

        }

        new Thread() {
            public void run() {
                Message message = Message.obtain();
                AccountBiz accountBiz = new AccountBiz(context);
                if(accountBiz.register(userName.getText().toString(),phoneNumber.getText().toString(), smsCode.getText().toString(),
                        password.getText().toString(), "1")){
                    message.what = 1;
                }else message.what = 0;
                handler.sendMessage(message);
            }
        }.start();
    }

    @OnClick(sendCodeBtn)
    void sendCode() {
        if ("".equals(phoneNumber.getText().toString())) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        new Thread() {
            public void run() {
                Message message = Message.obtain();
                AccountBiz accountBiz = new AccountBiz(context);
                response = accountBiz.getSmsCode(phoneNumber.getText().toString(), "register");
                message.what = 2;
                handler.sendMessage(message);
            }
        }.start();

        getCodeTextView.setText("" + "60秒");
        getCodeTextView.setClickable(false);
        flag = 60;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                flag--;
                timerHandler.sendEmptyMessage(flag);
            }
        }, new Date(), 1000);

    }


    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(RegisterActivity.this, "注册成功",
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(context, LoginActivity.class));
                    context.finish();
                    break;
                case 2:
                    Toast.makeText(RegisterActivity.this, response.getErrorMessage(),
                            Toast.LENGTH_SHORT).show();
                    break;

                case 0:
                    Toast.makeText(RegisterActivity.this, "注册失败",
                            Toast.LENGTH_SHORT).show();
                    //(new ShowDialogHelper(LoginActivity.this)).showDialog("提示", "当前网络不可用");
                    break;
                default:
                    break;
            }
        };
    };


    private Handler timerHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {

            if (msg.what == -1) {
                getCodeTextView.setText("获取验证码");
                getCodeTextView.setClickable(true);
                if (null != timer) {
                    timer.cancel();
                    timer = null;
                }
            } else {
                getCodeTextView.setText("" + msg.what + "秒后获取");
            }
        }
    };

}
