package com.hyphenate.easeui.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.dialog.EaseDialog1;
import com.hyphenate.easeui.dialog.EaseDialog2;
import com.meibo.common.MyHttputils.Conntent;
import com.meibo.common.MyHttputils.HttpUtils;
import com.meibo.common.MyHttputils.Utils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018\4\6 0006.
 * 发红包页面
 */

public class RedEnvelopesActivity extends Activity implements View.OnClickListener,EaseDialog1.DialogOnItemClickListener,EaseDialog2.DialogOnItemClickListener2,HttpUtils.ICallback {

    private EditText ed_moneynumber,ed_moneycontent;
    private TextView tv_money,tv_title;
    private Button btn_redenveopes;

    private EaseDialog1 easeDialog1;
    private EaseDialog2 easeDialog2;
    private String Password,toChatUsername;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    if(Password!=null){
                        easeDialog2.dismiss();
                        RedEnvelopesAPI();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.redenveopes_layout);
        init();
    }

    /**初始化*/
    private void init(){

        this.findViewById(R.id.img_easetitle1_left).setOnClickListener(this);
        this.findViewById(R.id.img_easetitle1_left).setVisibility(View.VISIBLE);
        this.findViewById(R.id.btn_redenveopes).setOnClickListener(this);


        ed_moneynumber = (EditText) this.findViewById(R.id.ed_moneynumber);
        ed_moneycontent = (EditText) this.findViewById(R.id.ed_moneycontent1);
        tv_money = (TextView) this.findViewById(R.id.tv_money);
        btn_redenveopes = (Button) this.findViewById(R.id.btn_redenveopes);
        tv_title = (TextView)this.findViewById(R.id.txt_easetitle1_title);

        easeDialog1 = new EaseDialog1(this);
        easeDialog1.setOnItemClickListener(this);
        easeDialog2 = new EaseDialog2(this);
        easeDialog2.setOnItemClickListener2(this);

        toChatUsername = getIntent().getStringExtra("toChatUsername");

        asigment();
    }

    /**赋值*/
    private void asigment(){

        tv_title.setText("发红包");

        ed_moneynumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("看看",charSequence+"");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("看看2",charSequence+"");
                if(charSequence.toString().contains(".")){
                    if(charSequence.length()-1-charSequence.toString().indexOf(".")>2){
                        charSequence = charSequence.toString().subSequence(0,charSequence.toString().indexOf(".")+3);
                        ed_moneynumber.setText(charSequence);
                        ed_moneynumber.setSelection(charSequence.length());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("看看3",editable.toString());
                if(editable.toString().trim().length()>0) {
                    if(!editable.toString().trim().equals(".")) {
                        Double count = Double.parseDouble(ed_moneynumber.getText().toString().trim());
                        if(count!=0){
                            tv_money.setText(ed_moneynumber.getText().toString());
                            btn_redenveopes.setBackgroundResource(R.drawable.shape_easered_bg);
                        }else{
                            tv_money.setText("0.00");
                            btn_redenveopes.setBackgroundResource(R.drawable.shape_66easered_bg);
                            Utils.setToast("金额不能为0",RedEnvelopesActivity.this);
                        }
                    }else{
                        ed_moneynumber.setText("");
                        tv_money.setText("0.00");
                        btn_redenveopes.setBackgroundResource(R.drawable.shape_66easered_bg);
                        Utils.setToast("第一位不能为小数点",RedEnvelopesActivity.this);
                    }
                }else{
                    tv_money.setText("0.00");
                    btn_redenveopes.setBackgroundResource(R.drawable.shape_66easered_bg);
                }
            }
        });

    }


    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.img_easetitle1_left){  //返回
            finish();
            return;
        }else if(view.getId()==R.id.btn_redenveopes){  //塞钱进红包

            if(ed_moneynumber.getText().toString().trim().length()>0){
                Double count =  Double.parseDouble(ed_moneynumber.getText().toString().trim());
                if(count!=0){
                    easeDialog1.setTextmoney(count+"");
                    easeDialog1.showAtLocation(this.findViewById(R.id.layout_redenvelopes), Gravity.CENTER,0,0);
                }else{
                    Utils.setToast("金额不能为0",this);
                }
            }else{
                Utils.setToast("金额不能为空",this);
            }
        }
    }

    @Override
    public void setOnItemClick(View v) {
        easeDialog1.dismiss();
        if(v.getId()==R.id.img_back){  //返回
            easeDialog1.dismiss();
            return;
        }else if(v.getId()==R.id.btn_cancel){  //确认支付
            easeDialog2.setTextmoney(easeDialog1.getTextmonet());
            easeDialog2.showAtLocation(this.findViewById(R.id.layout_redenvelopes), Gravity.CENTER,0,0);
            Passwordlinener();
            return;
        }
    }

    @Override
    public void setOnItemClick2(View v) {
        easeDialog2.dismiss();
        if(v.getId()==R.id.img_back){  //返回
            easeDialog2.dismiss();
            easeDialog1.setTextmoney(Double.parseDouble(ed_moneynumber.getText().toString().trim())+"");
            easeDialog1.showAtLocation(this.findViewById(R.id.layout_redenvelopes), Gravity.CENTER,0,0);
            return;
        }else if(v.getId()==R.id.btn_cancel){  //确认支付

        }
    }

    /**输入密码监听*/
    private void Passwordlinener(){
        easeDialog2.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()==0){
                    easeDialog2.tv_password1.setText("");
                    easeDialog2.tv_password2.setText("");
                    easeDialog2.tv_password3.setText("");
                    easeDialog2.tv_password4.setText("");
                    easeDialog2.tv_password5.setText("");
                    easeDialog2.tv_password6.setText("");
                }else if(charSequence.length()==1){
                    if(charSequence.length()<2){
                        easeDialog2.tv_password2.setText("");
                        easeDialog2.tv_password3.setText("");
                        easeDialog2.tv_password4.setText("");
                        easeDialog2.tv_password5.setText("");
                        easeDialog2.tv_password6.setText("");
                    }
                    easeDialog2.tv_password1.setText(""+charSequence);
                }else if(charSequence.length()==2){
                    if(charSequence.length()<3){
                        easeDialog2.tv_password3.setText("");
                        easeDialog2.tv_password4.setText("");
                        easeDialog2.tv_password5.setText("");
                        easeDialog2.tv_password6.setText("");
                    }
                    Log.d("输入支付密码","第二个值"+charSequence.toString().substring(charSequence.length()-1));
                    easeDialog2.tv_password2.setText(""+charSequence.toString().substring(charSequence.length()-1));
                }else if(charSequence.length()==3){
                    if(charSequence.length()<4){
                        easeDialog2.tv_password4.setText("");
                        easeDialog2.tv_password5.setText("");
                        easeDialog2.tv_password6.setText("");
                    }
                    Log.d("输入支付密码","第三个值"+charSequence.toString().substring(charSequence.length()-1));
                    easeDialog2.tv_password3.setText(""+charSequence.toString().substring(charSequence.length()-1));
                }else if(charSequence.length()==4){
                    if(charSequence.length()<5){
                        easeDialog2.tv_password5.setText("");
                        easeDialog2.tv_password6.setText("");
                    }
                    Log.d("输入支付密码","第四个值"+charSequence.toString().substring(charSequence.length()-1));
                    easeDialog2.tv_password4.setText(""+charSequence.toString().substring(charSequence.length()-1));
                }else if(charSequence.length()==5){
                    if(charSequence.length()<6){
                        easeDialog2.tv_password6.setText("");
                    }
                    Log.d("输入支付密码","第五个值"+charSequence.toString().substring(charSequence.length()-1));
                    easeDialog2.tv_password5.setText(""+charSequence.toString().substring(charSequence.length()-1));
                }else if(charSequence.length()==6){
                    Log.d("输入支付密码","第六个值"+charSequence.toString().substring(charSequence.length()-1));
                    easeDialog2.tv_password6.setText(""+charSequence.toString().substring(charSequence.length()-1));
                    Password = Utils.md5(charSequence.toString().trim());
                    AuthenticationCipher();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    /**验证支付密码*/
    private void AuthenticationCipher(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(500);
                Message message = handler.obtainMessage();
                message.what = 1;
                handler.sendMessage(message);
            }
        }).start();

    }

    /**调用发红包接口（扣钱）*/
    private void RedEnvelopesAPI(){

        Map<String,Object> map = new HashMap<>();
        map.put("token","RmrLpX5ypP8YDfkfav3ZjUyxWeXe5tcS");
        map.put("action","bonus_send");
        map.put("receive_id",toChatUsername);  //接收红包用户环信id
        map.put("send_type",1);  //1=私聊红包，2=群聊随机红包，3=私聊E币红包，4=群聊随机E币红包
        map.put("money",Double.parseDouble(easeDialog2.getTextmonet()));  //红包金额
        map.put("points",0);  //红包E币
        map.put("count","1");  //红包数量
        if(ed_moneycontent.getText().toString().trim().length()>0){
            map.put("message",ed_moneycontent.getText().toString().trim());  //红包信息
        }else {
            map.put("message", "大吉大利，恭喜发财");  //红包信息
        }
        map.put("trade_password",Password);  //交易密码
        Log.d("发红包接口参数值","receive_id:"+toChatUsername+",money:"+Double.parseDouble(easeDialog2.getTextmonet())+",message:"+ed_moneycontent.getText()+",trade_password:"+Password);
        HttpUtils.getInstance().post(Conntent.CURRENCY,map,this);

    }

    /**发红包(扩展消息)*/
    private void RedEnvelopes(String bonus_id){
        try {
            EMMessage emMessage = EMMessage.createTxtSendMessage("此消息为红包消息", toChatUsername);
            emMessage.setAttribute("Money", easeDialog2.getTextmonet());
            emMessage.setAttribute("redPackId", bonus_id);
            emMessage.setAttribute("money_greeting", "大吉大利，恭喜发财");
            emMessage.setAttribute("redPack", true);
            emMessage.setAttribute("userId", "");
            emMessage.setAttribute("isEDCoin", "0");
            emMessage.setAttribute("sign", "0");
            EMClient.getInstance().chatManager().sendMessage(emMessage);
            finish();
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSuccess(String url, String result) {
        if(url.equals(Conntent.CURRENCY)){
            Log.d("发红包接口返回值",""+result);
            try{
               JSONObject js = new JSONObject(result);
               if(js.getInt("returnCode")==10000){
                   JSONObject jo = js.getJSONObject("data");
                   if(jo!=null){
                       RedEnvelopes(jo.getString("bonus_id"));
                   }
               }else{
                   Utils.setToast(js.getString("returnMsg"),RedEnvelopesActivity.this);
               }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
