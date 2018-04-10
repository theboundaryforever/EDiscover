package com.kest.ediscover.CustomControl;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.kest.ediscover.R;

/**
 * Created by Administrator on 2018\4\4 0004.
 */

public class Dialog4 extends PopupWindow implements View.OnClickListener {

    private View view;
    private Context context;
    private DialogOnItemClickListener myOnClickListener;

    private TextView tv_bottom_txt1,tv_bottom_txt2,tv_bottom_txt3;

    public Dialog4(Context context) {
        super(context);
        this.context = context;
        init();
        setPopupWindow();
    }

    /**初始化*/
    private void init(){

        view = LayoutInflater.from(context).inflate(R.layout.dialog4,null);
        tv_bottom_txt1 = (TextView)view.findViewById(R.id.tv_bottom_txt1);
        tv_bottom_txt2 = (TextView)view.findViewById(R.id.tv_bottom_txt2);
        tv_bottom_txt3 = (TextView)view.findViewById(R.id.tv_bottom_txt3);

        view.findViewById(R.id.tv_bottom_txt1).setOnClickListener(this);
        view.findViewById(R.id.tv_bottom_txt2).setOnClickListener(this);
        view.findViewById(R.id.tv_bottom_txt3).setOnClickListener(this);

    }

    /**设置窗口属性*/
    private void setPopupWindow(){

        this.setContentView(view);  // 设置View
        this.setWidth(ActionBar.LayoutParams.MATCH_PARENT);  // 设置弹出窗口的宽
        this.setHeight(ActionBar.LayoutParams.MATCH_PARENT);  // 设置弹出窗口的高
        this.setFocusable(true);  // 设置弹出窗口(true:允许)
        this.setAnimationStyle(R.style.mypopwindow_anim_style);  // 设置动画
        this.setBackgroundDrawable(new ColorDrawable(0xb0000000)); // 设置背景透明

        view.setOnTouchListener(new View.OnTouchListener() {  //触摸事件监听（如果触摸位置在窗口外，则退出窗口）
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int height = view.findViewById(R.id.layout_dialog4).getTop();
                int width = view.findViewById(R.id.layout_dialog4).getWidth();
                int y = (int)motionEvent.getY();
                int x = (int)motionEvent.getX();
                if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    if(y<height){
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    /**给EditText设值*/
    public void setText1(String content){
        tv_bottom_txt1.setText(content);
    }

    public String getText1(){
        return   tv_bottom_txt1.getText().toString().trim();
    }

    public void setText2(String name){
        tv_bottom_txt2.setText(name);
    }

    public String getText2(){
        return tv_bottom_txt2.getText().toString().trim();
    }

    public void setText3(String content){
        tv_bottom_txt3.setText(content);
    }

    public String getText3(){
        return tv_bottom_txt3.getText().toString().trim();
    }

    public void setText1VisibilityGONE(){tv_bottom_txt1.setVisibility(View.GONE);}

    public void setText1VisibilityVISIBLE(){tv_bottom_txt1.setVisibility(View.VISIBLE);}

    /**定义成一个接口，回调出去*/
    public interface DialogOnItemClickListener {
        void setOnItemClick4(View v);
    }

    public void setOnItemClickListener(DialogOnItemClickListener listener){
        this.myOnClickListener = listener;
    }

    @Override
    public void onClick(View view) {
        if(myOnClickListener != null){
            myOnClickListener.setOnItemClick4(view);
        }
    }
}
