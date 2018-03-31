package com.kest.ediscover.widget;

/**
 * Created by dk on 2018/1/10.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.kest.ediscover.MainActivity;
import com.kest.ediscover.R;
import com.kest.ediscover.utils.SharePreferenceUtil;

public class MorePopWindow extends PopupWindow {
    private View conentView;
    private LinearLayout shareBtn;
    private LinearLayout firstPageBtn;
    MorePopWindow tt;

    SharePreferenceUtil sp;

    public MorePopWindow(final Activity context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.more_popup_dialog, null);
        tt = this;
        sp = SharePreferenceUtil.getInstance(context);
        conentView.findViewById(R.id.first_page_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, MainActivity.class));
                tt.dismiss();
            }
        });

        conentView.findViewById(R.id.share_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                //intent.setType("text/html");

                Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(
                        context.getContentResolver(), BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher), null, null));
                //intent.putExtra(Intent.EXTRA_TEXT, sp.getShareTitle() + "  " + sp.getShareUrl());


                //intent.putExtra(Intent.EXTRA_HTML_TEXT, sp.getShareUrl());
                String text = sp.getShareUrl();
                String title = sp.getShareTitle();
                if (text.contains("pay.allbuy.me")){
                    text = " http://pay.allbuy.me/down.html";
                    title = "APP 下载地址";
                }
                intent.putExtra(Intent.EXTRA_TEXT,text);
                intent.putExtra(Intent.EXTRA_TITLE,title);
                //context.startActivity(intent);



                context.startActivity(Intent.createChooser(intent, "分享到"));
                tt.dismiss();
            }
        });


        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        int w = context.getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(w / 3 + 50);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimationPreview);

    }

    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            this.showAsDropDown(parent, parent.getLayoutParams().width / 2, 18);
        } else {
            this.dismiss();
        }
    }
}
