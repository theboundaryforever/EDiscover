package com.kest.ediscover.CustomControl;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.kest.ediscover.R;

/**
 * Created by Administrator on 2018\3\30 0030.
 */

public class Dialog1 extends PopupWindow implements View.OnClickListener {

    private View view;
    private Context context;
    private DialogOnItemClickListener myOnClickListener;

    private EditText ed_content;

    public Dialog1(Context context) {
        super(context);
        this.context = context;
        init();
        setPopupWindow();
    }

    /**初始化*/
    private void init(){

        view = LayoutInflater.from(context).inflate(R.layout.dialog1,null);
        ed_content = (EditText)view.findViewById(R.id.ed_content);

        view.findViewById(R.id.im_delete).setOnClickListener(this);
        view.findViewById(R.id.tv_cancel).setOnClickListener(this);
        view.findViewById(R.id.tv_confirm).setOnClickListener(this);

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
                int height = view.findViewById(R.id.layout_dialog1).getTop();
                int width = view.findViewById(R.id.layout_dialog1).getWidth();
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
    public void setEditText(String content){
        ed_content.setText(content);
    }

    public String getEditText(){
        return ed_content.getText().toString().trim();
    }

    /**定义成一个接口，回调出去*/
    public interface DialogOnItemClickListener {
       void setOnItemClick(View v);
    }

    public void setOnItemClickListener(DialogOnItemClickListener listener){
        this.myOnClickListener = listener;
    }

    @Override
    public void onClick(View view) {
        if(myOnClickListener != null){
            myOnClickListener.setOnItemClick(view);
        }
    }
}
