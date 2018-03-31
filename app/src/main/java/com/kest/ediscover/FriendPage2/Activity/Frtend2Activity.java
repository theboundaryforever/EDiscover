package com.kest.ediscover.FriendPage2.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.easeui.widget.EaseConversationList;
import com.kest.ediscover.MainActivity;
import com.kest.ediscover.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/22 0022.
 * 朋友模块
 */

public class Frtend2Activity extends Activity implements View.OnClickListener{

    private View view;

    private TextView txt_message,txt_maillist,txt_me;
    private ImageView img_line,img_line1,img_line2,img_line3;
    private ViewPager viewPager;
    private List<View> Vlist = new ArrayList<>();
    private RadioButton purse_btn,daybuy_btn2;
    private int bmpw;  //图片宽度
    private int offset;  //偏移量

    private EaseConversationList easeConversationList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_friend2);
        initView();

    }

    /**初始化*/
    private void initView(){

        txt_message = (TextView)this.findViewById(R.id.friend_message_txt);  //消息列表模块
        txt_maillist = (TextView)this.findViewById(R.id.friend_maillist_txt);  //通讯录列表模块
        txt_me = (TextView)this.findViewById(R.id.friend_me_txt);  //我的模块
        viewPager = (ViewPager)this.findViewById(R.id.friend_viewpager); //内容展示快Viewpager
        img_line1 = (ImageView)this.findViewById(R.id.frrend_line1);  //图片
        img_line2 = (ImageView)this.findViewById(R.id.frrend_line2);  //图片
        img_line3 = (ImageView)this.findViewById(R.id.frrend_line3);  //图片
        purse_btn = (RadioButton)this.findViewById(R.id.purse_btn);  //首页按钮
        daybuy_btn2 = (RadioButton)this.findViewById(R.id.daybuy_btn2);  //朋友按钮

        txt_message.setOnClickListener(this);
        txt_maillist.setOnClickListener(this);
        txt_me.setOnClickListener(this);
        purse_btn.setOnClickListener(this);
        daybuy_btn2.setOnClickListener(this);

        assigment();
    }

    /**赋值*/
    private void assigment(){

        LayoutInflater layoutInflater = LayoutInflater.from(this);

        View view1 = layoutInflater.inflate(R.layout.test1,null);
        View view2 = layoutInflater.inflate(R.layout.test2,null);
        View view3 = layoutInflater.inflate(R.layout.test3,null);

       //  easeConversationList = (EaseConversationList)this.findViewById(R.id.easelist);
        //初始化，参数为会话列表集合


        Vlist.add(view1);
        Vlist.add(view2);
        Vlist.add(view3);

        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.friend_message_txt:  //消息
                viewPager.setCurrentItem(0);
                break;
            case R.id.friend_maillist_txt:  //通讯录
                viewPager.setCurrentItem(1);
                break;
            case R.id.friend_me_txt:  //我的
                viewPager.setCurrentItem(2);
                break;
            case R.id.purse_btn:  //首页按钮
                startActivity(new Intent(Frtend2Activity.this, MainActivity.class));
                break;
            case R.id.daybuy_btn2:  //朋友按钮

                break;
        }
    }

    //记录用户首次点击返回键的时间
    private long firstTime=0;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                long secondTime=System.currentTimeMillis();
                if(secondTime-firstTime>2000){
                    Toast.makeText(Frtend2Activity.this,"再按一次退出程序", Toast.LENGTH_SHORT).show();
                    firstTime=secondTime;
                    return true;
                }else{
                    System.exit(0);
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }

    /**ViewPager适配器*/
    private class MyPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            if (Vlist != null && Vlist.size() > 0) {
                return Vlist.size();
            } else {
                return 0;
            }
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(Vlist.get(position));
            return Vlist.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }

    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }


        @Override
        public void onPageSelected(int position) {

            if(position==0){
                txt_message.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                txt_maillist.setTextColor(getResources().getColor(R.color.result_view));
                txt_me.setTextColor(getResources().getColor(R.color.result_view));
                img_line1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                img_line2.setBackgroundColor(getResources().getColor(R.color.color_white_f1));
                img_line3.setBackgroundColor(getResources().getColor(R.color.color_white_f1));
            }else if(position==1){
                txt_message.setTextColor(getResources().getColor(R.color.result_view));
                txt_maillist.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                txt_me.setTextColor(getResources().getColor(R.color.result_view));
                img_line1.setBackgroundColor(getResources().getColor(R.color.color_white_f1));
                img_line2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                img_line3.setBackgroundColor(getResources().getColor(R.color.color_white_f1));
            }else if(position==2){
                txt_message.setTextColor(getResources().getColor(R.color.result_view));
                txt_maillist.setTextColor(getResources().getColor(R.color.result_view));
                txt_me.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                img_line1.setBackgroundColor(getResources().getColor(R.color.color_white_f1));
                img_line2.setBackgroundColor(getResources().getColor(R.color.color_white_f1));
                img_line3.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            }


        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

}
