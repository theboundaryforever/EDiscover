package com.kest.ediscover.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.kest.ediscover.R;

/**
 * Created by dk on 2017/12/15.
 */
public class ResizableRadioButton extends RadioButton {
    private static final String TAG = "ResizableRadioButton";
    private Drawable drawableTop;
    private int topWith;
    private int topHeight;

    public ResizableRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public ResizableRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    public ResizableRadioButton(Context context) {
        super(context);
        initView(context, null);
    }

    private void initView(Context context, AttributeSet attrs) {
        if (attrs != null) {
            float scale = context.getResources().getDisplayMetrics().density;
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ResizableRadioButton);
//            int n = a.getIndexCount();
//            for (int i = 0; i < n; i++) {
//                int attr = a.getIndex(i);
//                switch (attr) {
//                    case R.styleable.ResizableRadioButton_drawableTop:
//                        drawableTop = a.getDrawable(attr);
//                        break;
//                    case R.styleable.ResizableRadioButton_drawableTopWidth:
//                        LogUtils.i(TAG, "dp width=" + a.getDimension(attr, 10));
//                        topWith = (int)a.getDimension(attr, 10);//(int) (a.getDimension(attr, 10) * scale + 0.5f);
//                        break;
//                    case R.styleable.ResizableRadioButton_drawableTopHeight:
//                        topHeight = (int) a.getDimension(attr, 10) ;//(a.getDimension(attr, 10) * scale + 0.5f);
//                        break;
//                }
//            }
            drawableTop = a.getDrawable(R.styleable.ResizableRadioButton_drawableTop);
            topWith = (int) a.getDimension(R.styleable.ResizableRadioButton_drawableTopWidth, 10);
            topHeight = (int) a.getDimension(R.styleable.ResizableRadioButton_drawableTopHeight, 10);
            a.recycle();
            setCompoundDrawablesWithIntrinsicBounds(null, drawableTop, null, null);
        }
    }

    // 设置Drawable定义的大小
    @Override
    public void setCompoundDrawablesWithIntrinsicBounds(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        if (top != null) {
            top.setBounds(0, 0, topWith <= 0 ? top.getIntrinsicWidth() : topWith, topHeight <= 0 ? top.getMinimumHeight() : topHeight);
        }
        setCompoundDrawables(left, top, right, bottom);
    }
}
