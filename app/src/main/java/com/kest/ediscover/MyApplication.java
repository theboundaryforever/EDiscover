package com.kest.ediscover;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;


/**
 * Created by Administrator on 2018\3\24 0024.
 */

public class MyApplication extends Application{

    private static Context context;
    private static Toast toast = null;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        EMOptions emOptions = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        emOptions.setAcceptInvitationAlways(true);
        //初始化环信IM
        EaseUI.getInstance().init(context,emOptions);

    }

    /**获取ApplicationContext*/
    public static Context getContext(){
        return context;
    }

    /**弹出Toast*/
    public static void setToast(String content){
        if (toast != null) {
            toast.setText(content);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();
        } else {
            toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
            toast.show();
        }
    }


}
