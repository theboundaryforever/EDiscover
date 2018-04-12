package com.hyphenate.easeui.widget.chatrow;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.model.EaseDingMessageHelper;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018\4\10 0010.
 */

public class EaseChatRowRedEnvelops extends EaseChatRow{

    private TextView tv_title,tv_style;

    public EaseChatRowRedEnvelops(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    /**
     * 根据条件调用不同的布局文件
     **/
    @Override
    protected void onInflateView() {

        if(message.ext().size()>0){
            Log.d("调用界面",""+message.ext()+",map值"+message.ext().get("sign"));
            if(message.ext().get("sign").equals("0")) {
                inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
                        R.layout.ease_row_received_redenvelopes_messaget : R.layout.ease_row_sent_redenvelopes_messaget, this);
            }else{
                inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
                        R.layout.ease_row_received_message : R.layout.ease_row_sent_message, this);
            }
        }else{
            inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
                    R.layout.ease_row_received_message : R.layout.ease_row_sent_message, this);
        }

    }

    /**
     * 布局控件初始化
     **/
    @Override
    protected void onFindViewById() {

        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_style = (TextView)findViewById(R.id.tv_style);

    }

    /**
     * 页面刷新
     **/
    @Override
    protected void onViewUpdate(EMMessage msg) {
        switch (msg.status()) {
            case CREATE:
                onMessageCreate();
                break;
            case SUCCESS:
                onMessageSuccess();
                break;
            case FAIL:
                onMessageError();
                break;
            case INPROGRESS:
                onMessageInProgress();
                break;
        }
    }

    /**
     * 布局赋值
     **/
    @Override
    protected void onSetUpView() {

        if(message.ext().size()>0) {
            try{
                Gson gson = new Gson();
                String jsonStr = gson.toJson(message.ext());
                Log.d("ROW赋值",jsonStr);
                JSONObject js = new JSONObject(jsonStr);
                tv_title.setText(js.getString("money_greeting"));
                tv_style.setText("E信红包");
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public void onAckUserUpdate(final int count) {
        if (ackedView != null) {
            ackedView.post(new Runnable() {
                @Override
                public void run() {
                    ackedView.setVisibility(VISIBLE);
                    ackedView.setText(String.format(getContext().getString(R.string.group_ack_read_count), count));
                }
            });
        }
    }

    private void onMessageCreate() {
        progressBar.setVisibility(View.VISIBLE);
        statusView.setVisibility(View.GONE);
    }

    private void onMessageSuccess() {
        progressBar.setVisibility(View.GONE);
        statusView.setVisibility(View.GONE);

        // Show "1 Read" if this msg is a ding-type msg.
        if (EaseDingMessageHelper.get().isDingMessage(message) && ackedView != null) {
            ackedView.setVisibility(VISIBLE);
            List<String> userList = EaseDingMessageHelper.get().getAckUsers(message);
            int count = userList == null ? 0 : userList.size();
            ackedView.setText(String.format(getContext().getString(R.string.group_ack_read_count), count));
        }

        // Set ack-user list change listener.
        EaseDingMessageHelper.get().setUserUpdateListener(message, userUpdateListener);
    }

    private void onMessageError() {
        progressBar.setVisibility(View.GONE);
        statusView.setVisibility(View.VISIBLE);
    }

    private void onMessageInProgress() {
        progressBar.setVisibility(View.VISIBLE);
        statusView.setVisibility(View.GONE);
    }

    private EaseDingMessageHelper.IAckUserUpdateListener userUpdateListener =
            new EaseDingMessageHelper.IAckUserUpdateListener() {
                @Override
                public void onUpdate(List<String> list) {
                    onAckUserUpdate(list.size());
                }
            };


}
