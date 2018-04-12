package com.hyphenate.easeui.widget.chatrow;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.dialog.MyAnimation;
import com.hyphenate.easeui.model.EaseDingMessageHelper;
import com.hyphenate.easeui.ui.RedEnvelopesDetailsActivity;
import com.hyphenate.easeui.widget.presenter.EaseChatRowPresenter;
import com.hyphenate.exceptions.HyphenateException;

import org.json.JSONObject;

/**
 * Created by Administrator on 2018\4\10 0010.
 */

public class EaseChatRedEnvelopsPresenter extends EaseChatRowPresenter implements View.OnClickListener{

    private AlertDialog dialog;
    private String redPackId;
    private View view;
    private MyAnimation myAnimation = new MyAnimation();

    @Override
    protected EaseChatRow onCreateChatRow(Context cxt, EMMessage message, int position, BaseAdapter adapter) {

        return new EaseChatRowRedEnvelops(cxt,message,position,adapter);
    }


    @Override
    public void onBubbleClick(EMMessage message) {
        super.onBubbleClick(message);
        Log.d("钱包气泡点击","666");
        if(message.direct()==EMMessage.Direct.RECEIVE){
            ToastAlertDialog(message);
        }else{
            Intent intent = new Intent(getContext(), RedEnvelopesDetailsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().startActivity(intent);
        }


    }

    @Override
    protected void handleReceiveMessage(EMMessage message) {
        if (!message.isAcked() && message.getChatType() == EMMessage.ChatType.Chat) {
            try {
                EMClient.getInstance().chatManager().ackMessageRead(message.getFrom(), message.getMsgId());
            } catch (HyphenateException e) {
                e.printStackTrace();
            }
            return;
        }

        // Send the group-ack cmd type msg if this msg is a ding-type msg.
        EaseDingMessageHelper.get().sendAckMessage(message);
    }

   /**
    * 红包弹窗
    **/
    private void ToastAlertDialog(EMMessage message){

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        view = View.inflate(getContext(),R.layout.easedialog3_layout,null);
        builder.setCancelable(true);
        TextView tv_name = (TextView)view.findViewById(R.id.tv_username);
        TextView tv_style = (TextView)view.findViewById(R.id.tv_style);
        TextView tv_redcontent = (TextView)view.findViewById(R.id.tv_redcontent);
        dialog = builder.create();
        dialog.show();
        dialog.getWindow().setContentView(view);
        dialog.getWindow().setGravity(Gravity.CENTER);

        view.findViewById(R.id.img_back).setOnClickListener(this);
        view.findViewById(R.id.img_open).setOnClickListener(this);

        if(message.ext().size()>0){
            try {
                if (message.ext().get("sign").equals("0")) {
                    tv_style.setText("E信红包");
                } else if (message.ext().get("sign").equals("1")) {
                    tv_style.setText("ED币红包");
                }

                Gson gson = new Gson();
                String jsonStr = gson.toJson(message.ext());
                Log.d("ROW赋值",jsonStr);
                JSONObject js = new JSONObject(jsonStr);
                redPackId = js.getString("redPackId");
                tv_redcontent.setText(js.getString("money_greeting"));

            }catch(Exception e){
                e.printStackTrace();
            }

        }

    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.img_back){  //返回
            dialog.dismiss();
            return;
        }else if(v.getId()==R.id.img_open){  //打开红包
            Log.d("红包点击事件","打开红包");
            myAnimation.setRepeatCount(2);
            view.findViewById(R.id.img_open).startAnimation(myAnimation);
            startintent();
            return;
        }
    }

    private void startintent(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                Intent intent = new Intent(getContext(), RedEnvelopesDetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
                Log.d("是不是动画结束","出来的");
                dialog.dismiss();
            }
        }).start();

    }

}
